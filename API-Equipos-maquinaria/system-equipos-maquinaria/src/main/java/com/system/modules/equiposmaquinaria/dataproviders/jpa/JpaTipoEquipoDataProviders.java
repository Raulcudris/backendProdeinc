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
    public EntyPrvinvmdequipmaquinariaResponse getAll()
            throws EBusinessException {
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
                    Integer id = parseInteger(search);
                    page = repository.searchByPrimaryKey(id, pageable);
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

            EntyPrvinvmdequipmaquinariaResponse response =
                    new EntyPrvinvmdequipmaquinariaResponse();

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
            if (id == null) {
                return new EntyPrvinvmdequipmaquinariaDto();
            }

            EntyPrvinvmdequipmaquinaria entity = repository.findById(id)
                    .orElse(null);

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
            if (dto.getPrvTipoequipoTieq() == null ||
                    dto.getPrvTipoequipoTieq().isBlank()) {
                return new EntyPrvinvmdequipmaquinariaDto();
            }

            dto.setPrvPrimarykeyTieq(null);
            normalizeDto(dto);

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
            if (id == null) {
                return new EntyPrvinvmdequipmaquinariaDto();
            }

            EntyPrvinvmdequipmaquinaria current = repository.findById(id)
                    .orElse(null);

            if (current == null) {
                return new EntyPrvinvmdequipmaquinariaDto();
            }

            dto.setPrvPrimarykeyTieq(id);

            if (dto.getPrvTipoequipoTieq() == null ||
                    dto.getPrvTipoequipoTieq().isBlank()) {
                dto.setPrvTipoequipoTieq(current.getPrvTipoequipoTieq());
            }

            if (dto.getPrvDescripcionTieq() == null ||
                    dto.getPrvDescripcionTieq().isBlank()) {
                dto.setPrvDescripcionTieq(current.getPrvDescripcionTieq());
            }

            if (dto.getPrvIdentifkeyUnme() == null ||
                    dto.getPrvIdentifkeyUnme().isBlank()) {
                dto.setPrvIdentifkeyUnme(current.getPrvIdentifkeyUnme());
            }

            if (dto.getPrvTiporegistTieq() == null ||
                    dto.getPrvTiporegistTieq().isBlank()) {
                dto.setPrvTiporegistTieq(current.getPrvTiporegistTieq());
            }

            if (dto.getPrvEstadoregTieq() == null ||
                    dto.getPrvEstadoregTieq().isBlank()) {
                dto.setPrvEstadoregTieq(current.getPrvEstadoregTieq());
            }

            normalizeDto(dto);

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
            if (id == null || !repository.existsById(id)) {
                return;
            }

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
            if (tipoEquipoKey == null || tipoEquipoKey.isBlank()) {
                return new EntyPrvinvmdequipmaquinariaDto();
            }

            String key = tipoEquipoKey.trim().toUpperCase();

            EntyPrvinvmdequipmaquinaria entity = repository
                    .findByPrvTipoequipoTieq(key)
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
            if (unidadKey == null || unidadKey.isBlank()) {
                return new ArrayList<>();
            }

            String key = unidadKey.trim().toUpperCase();

            return translateList(repository.findByPrvIdentifkeyUnme(key));

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

    private void normalizeDto(
            EntyPrvinvmdequipmaquinariaDto dto
    ) {
        if (dto.getPrvTipoequipoTieq() != null) {
            dto.setPrvTipoequipoTieq(dto.getPrvTipoequipoTieq().trim().toUpperCase());
        }

        if (dto.getPrvIdentifkeyUnme() != null &&
                !dto.getPrvIdentifkeyUnme().isBlank()) {
            dto.setPrvIdentifkeyUnme(dto.getPrvIdentifkeyUnme().trim().toUpperCase());
        }

        if (dto.getPrvDescripcionTieq() == null ||
                dto.getPrvDescripcionTieq().isBlank()) {
            dto.setPrvDescripcionTieq("Sin descripcion");
        }

        if (dto.getPrvIdentifkeyUnme() == null ||
                dto.getPrvIdentifkeyUnme().isBlank()) {
            dto.setPrvIdentifkeyUnme("HORA");
        }

        if (dto.getPrvTiporegistTieq() == null ||
                dto.getPrvTiporegistTieq().isBlank()) {
            dto.setPrvTiporegistTieq("1");
        }

        if (dto.getPrvEstadoregTieq() == null ||
                dto.getPrvEstadoregTieq().isBlank()) {
            dto.setPrvEstadoregTieq("1");
        }
    }
}