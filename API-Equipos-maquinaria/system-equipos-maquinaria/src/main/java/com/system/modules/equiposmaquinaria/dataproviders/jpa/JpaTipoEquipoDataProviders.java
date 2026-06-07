package com.system.modules.equiposmaquinaria.dataproviders.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyPrvinvmdequipmaquinariaDto;
import com.system.crosscutting.domain.model.EntyPrvinvmdequipmaquinariaResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyPrvinvmdequipmaquinaria;
import com.system.crosscutting.persistence.repository.EntyPrvinvmdequipmaquinariaRepository;
import com.system.crosscutting.translate.EntyPrvinvmdequipmaquinariaDtoToEntityTranslate;
import com.system.crosscutting.translate.EntyPrvinvmdequipmaquinariaEntityToDtoTranslate;
import com.system.modules.equiposmaquinaria.dataproviders.IjpaTipoEquipoDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaTipoEquipoDataProviders extends JpaDataProviderSupport
        implements IjpaTipoEquipoDataProviders {

    private final EntyPrvinvmdequipmaquinariaRepository repository;
    private final EntyPrvinvmdequipmaquinariaDtoToEntityTranslate dtoToEntityTranslate;
    private final EntyPrvinvmdequipmaquinariaEntityToDtoTranslate entityToDtoTranslate;

    @Override
    public EntyPrvinvmdequipmaquinariaResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyPrvinvmdequipmaquinariaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        try {
            int pageNumber = safeCurrentPage(currentPage);
            int size = safePageSize(pageSize);
            String search = safeFilter(filter);

            Pageable pageable = PageRequest.of(pageNumber, size);
            Page<EntyPrvinvmdequipmaquinaria> page;

            switch (safeParameter(parameter)) {
                case "ID":
                    page = repository.searchByPrimaryKey(parseInteger(search), pageable);
                    break;

                case "KEY":
                    page = repository.searchByIdentifKey(search, pageable);
                    break;

                case "UNIDAD":
                    page = repository.searchByUnidad(search, pageable);
                    break;

                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;

                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyPrvinvmdequipmaquinariaDto> data = new ArrayList<>();

            for (EntyPrvinvmdequipmaquinaria entity : page.getContent()) {
                data.add(entityToDtoTranslate.translate(entity));
            }

            EntyPrvinvmdequipmaquinariaResponse response = new EntyPrvinvmdequipmaquinariaResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-equipos-maquinaria");
            response.setRspData(data);
            response.setRspPagination(buildPagination(pageNumber + 1, size, page));

            return response;

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando tipos de equipo", e);
        }
    }

    @Override
    public EntyPrvinvmdequipmaquinariaDto get(
            Integer id
    ) throws EBusinessException {
        try {
            EntyPrvinvmdequipmaquinaria entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyPrvinvmdequipmaquinariaDto();
            }

            return entityToDtoTranslate.translate(entity);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando tipo de equipo", e);
        }
    }

    @Override
    public EntyPrvinvmdequipmaquinariaDto save(
            EntyPrvinvmdequipmaquinariaDto dto
    ) throws EBusinessException {
        try {
            dto.setPrvPrimarykeyTieq(null);

            if (dto.getPrvTiporegistTieq() == null || dto.getPrvTiporegistTieq().isBlank()) {
                dto.setPrvTiporegistTieq("1");
            }

            if (dto.getPrvEstadoregTieq() == null || dto.getPrvEstadoregTieq().isBlank()) {
                dto.setPrvEstadoregTieq("1");
            }

            EntyPrvinvmdequipmaquinaria entity = dtoToEntityTranslate.translate(dto);
            EntyPrvinvmdequipmaquinaria saved = repository.save(entity);

            return entityToDtoTranslate.translate(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando tipo de equipo", e);
        }
    }

    @Override
    public List<EntyPrvinvmdequipmaquinariaDto> save(
            List<EntyPrvinvmdequipmaquinariaDto> dtos
    ) throws EBusinessException {
        List<EntyPrvinvmdequipmaquinariaDto> result = new ArrayList<>();

        for (EntyPrvinvmdequipmaquinariaDto dto : dtos) {
            result.add(save(dto));
        }

        return result;
    }

    @Override
    public EntyPrvinvmdequipmaquinariaDto update(
            Integer id,
            EntyPrvinvmdequipmaquinariaDto dto
    ) throws EBusinessException {
        try {
            EntyPrvinvmdequipmaquinaria old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyPrvinvmdequipmaquinariaDto();
            }

            dto.setPrvPrimarykeyTieq(id);

            if (dto.getPrvTiporegistTieq() == null || dto.getPrvTiporegistTieq().isBlank()) {
                dto.setPrvTiporegistTieq("1");
            }

            if (dto.getPrvEstadoregTieq() == null || dto.getPrvEstadoregTieq().isBlank()) {
                dto.setPrvEstadoregTieq("1");
            }

            EntyPrvinvmdequipmaquinaria entity = dtoToEntityTranslate.translate(dto);
            EntyPrvinvmdequipmaquinaria saved = repository.save(entity);

            return entityToDtoTranslate.translate(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando tipo de equipo", e);
        }
    }

    @Override
    public void delete(
            Integer id
    ) throws EBusinessException {
        try {
            repository.deleteById(id);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando tipo de equipo", e);
        }
    }

    @Override
    public EntyPrvinvmdequipmaquinariaDto findByKey(
            String tipoEquipoKey
    ) throws EBusinessException {
        try {
            EntyPrvinvmdequipmaquinaria entity = repository.findByPrvTipoequipoTieq(tipoEquipoKey)
                    .orElse(null);

            if (entity == null) {
                return new EntyPrvinvmdequipmaquinariaDto();
            }

            return entityToDtoTranslate.translate(entity);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando tipo de equipo por key", e);
        }
    }

    @Override
    public List<EntyPrvinvmdequipmaquinariaDto> findByUnidad(
            String unidadKey
    ) throws EBusinessException {
        try {
            return translateList(repository.findByPrvIdentifkeyUnme(unidadKey));

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando tipos de equipo por unidad", e);
        }
    }

    @Override
    public List<EntyPrvinvmdequipmaquinariaDto> findByEstado(
            String estado
    ) throws EBusinessException {
        try {
            return translateList(repository.findByPrvEstadoregTieq(estado));

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando tipos de equipo por estado", e);
        }
    }

    private List<EntyPrvinvmdequipmaquinariaDto> translateList(
            List<EntyPrvinvmdequipmaquinaria> entities
    ) {
        return entities.stream()
                .map(entity -> {
                    try {
                        return entityToDtoTranslate.translate(entity);
                    } catch (EBusinessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
}