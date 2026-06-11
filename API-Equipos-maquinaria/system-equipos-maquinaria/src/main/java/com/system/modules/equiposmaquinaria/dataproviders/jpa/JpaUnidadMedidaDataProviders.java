package com.system.modules.equiposmaquinaria.dataproviders.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyPrvinvmdunidamedequipoDto;
import com.system.crosscutting.domain.model.EntyPrvinvmdunidamedequipoResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyPrvinvmdunidamedequipo;
import com.system.crosscutting.persistence.repository.EntyPrvinvmdunidamedequipoRepository;
import com.system.crosscutting.translate.EntyPrvinvmdunidamedequipoDtoToEntityTranslate;
import com.system.crosscutting.translate.EntyPrvinvmdunidamedequipoEntityToDtoTranslate;
import com.system.modules.equiposmaquinaria.dataproviders.IjpaUnidadMedidaDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaUnidadMedidaDataProviders extends JpaDataProviderSupport
        implements IjpaUnidadMedidaDataProviders {

    private final EntyPrvinvmdunidamedequipoRepository repository;
    private final EntyPrvinvmdunidamedequipoDtoToEntityTranslate dtoToEntityTranslate;
    private final EntyPrvinvmdunidamedequipoEntityToDtoTranslate entityToDtoTranslate;

    @Override
    public EntyPrvinvmdunidamedequipoResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyPrvinvmdunidamedequipoResponse getAll(
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
            Page<EntyPrvinvmdunidamedequipo> page;

            switch (safeParameter(parameter)) {
                case "KEY":
                    page = repository.searchByIdentifKey(search, pageable);
                    break;

                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;

                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyPrvinvmdunidamedequipoDto> data = new ArrayList<>();

            for (EntyPrvinvmdunidamedequipo entity : page.getContent()) {
                data.add(entityToDtoTranslate.translate(entity));
            }

            EntyPrvinvmdunidamedequipoResponse response = new EntyPrvinvmdunidamedequipoResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-equipos-maquinaria");
            response.setRspData(data);
            response.setRspPagination(buildPagination(pageNumber + 1, size, page));

            return response;

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando unidades de medida", e);
        }
    }

    /*
     * Método heredado del contrato genérico.
     * La entidad prvinvmdunidamedequipo no tiene ID numérico.
     * Para este módulo se debe usar findByKey(String unidadKey).
     */
    @Override
    public EntyPrvinvmdunidamedequipoDto get(
            Integer id
    ) throws EBusinessException {
        return new EntyPrvinvmdunidamedequipoDto();
    }

    @Override
    public EntyPrvinvmdunidamedequipoDto save(
            EntyPrvinvmdunidamedequipoDto dto
    ) throws EBusinessException {
        try {
            if (dto.getPrvTipunidamedUnme() == null || dto.getPrvTipunidamedUnme().isBlank()) {
                return new EntyPrvinvmdunidamedequipoDto();
            }

            String unidadKey = dto.getPrvTipunidamedUnme().trim().toUpperCase();
            dto.setPrvTipunidamedUnme(unidadKey);

            if (dto.getPrvDescmedidaUnme() == null || dto.getPrvDescmedidaUnme().isBlank()) {
                dto.setPrvDescmedidaUnme("Sin descripcion");
            }

            if (dto.getPrvEstadoregUnme() == null || dto.getPrvEstadoregUnme().isBlank()) {
                dto.setPrvEstadoregUnme("1");
            }

            EntyPrvinvmdunidamedequipo entity = dtoToEntityTranslate.translate(dto);
            EntyPrvinvmdunidamedequipo saved = repository.save(entity);

            return entityToDtoTranslate.translate(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando unidad de medida", e);
        }
    }

    @Override
    public List<EntyPrvinvmdunidamedequipoDto> save(
            List<EntyPrvinvmdunidamedequipoDto> dtos
    ) throws EBusinessException {
        List<EntyPrvinvmdunidamedequipoDto> result = new ArrayList<>();

        for (EntyPrvinvmdunidamedequipoDto dto : dtos) {
            result.add(save(dto));
        }

        return result;
    }

    /*
     * Método heredado del contrato genérico.
     * No se usa para unidad de medida porque esta tabla no tiene Integer id.
     */
    @Override
    public EntyPrvinvmdunidamedequipoDto update(
            Integer id,
            EntyPrvinvmdunidamedequipoDto dto
    ) throws EBusinessException {
        return new EntyPrvinvmdunidamedequipoDto();
    }

    /*
     * Método heredado del contrato genérico.
     * No se usa para unidad de medida porque esta tabla no tiene Integer id.
     */
    @Override
    public void delete(
            Integer id
    ) throws EBusinessException {
        // No aplica para esta entidad.
    }

    @Override
    public EntyPrvinvmdunidamedequipoDto findByKey(
            String unidadKey
    ) throws EBusinessException {
        try {
            if (unidadKey == null || unidadKey.isBlank()) {
                return new EntyPrvinvmdunidamedequipoDto();
            }

            EntyPrvinvmdunidamedequipo entity = repository
                    .findByPrvTipunidamedUnme(unidadKey.trim().toUpperCase())
                    .orElse(null);

            if (entity == null) {
                return new EntyPrvinvmdunidamedequipoDto();
            }

            return entityToDtoTranslate.translate(entity);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando unidad de medida por key", e);
        }
    }

    @Override
    public EntyPrvinvmdunidamedequipoDto updateByKey(
            String unidadKey,
            EntyPrvinvmdunidamedequipoDto dto
    ) throws EBusinessException {
        try {
            if (unidadKey == null || unidadKey.isBlank()) {
                return new EntyPrvinvmdunidamedequipoDto();
            }

            String key = unidadKey.trim().toUpperCase();

            EntyPrvinvmdunidamedequipo current = repository
                    .findByPrvTipunidamedUnme(key)
                    .orElse(null);

            if (current == null) {
                return new EntyPrvinvmdunidamedequipoDto();
            }

            dto.setPrvTipunidamedUnme(key);

            if (dto.getPrvDescmedidaUnme() == null || dto.getPrvDescmedidaUnme().isBlank()) {
                dto.setPrvDescmedidaUnme(current.getPrvDescmedidaUnme());
            }

            if (dto.getPrvEstadoregUnme() == null || dto.getPrvEstadoregUnme().isBlank()) {
                dto.setPrvEstadoregUnme(current.getPrvEstadoregUnme());
            }

            EntyPrvinvmdunidamedequipo entity = dtoToEntityTranslate.translate(dto);
            EntyPrvinvmdunidamedequipo saved = repository.save(entity);

            return entityToDtoTranslate.translate(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando unidad de medida por key", e);
        }
    }

    @Override
    public void deleteByKey(
            String unidadKey
    ) throws EBusinessException {
        try {
            if (unidadKey == null || unidadKey.isBlank()) {
                return;
            }

            String key = unidadKey.trim().toUpperCase();

            EntyPrvinvmdunidamedequipo current = repository
                    .findByPrvTipunidamedUnme(key)
                    .orElse(null);

            if (current != null) {
                repository.delete(current);
            }

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando unidad de medida por key", e);
        }
    }

    @Override
    public List<EntyPrvinvmdunidamedequipoDto> findByEstado(
            String estado
    ) throws EBusinessException {
        try {
            return translateList(repository.findByPrvEstadoregUnme(estado));
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando unidades de medida por estado", e);
        }
    }

    private List<EntyPrvinvmdunidamedequipoDto> translateList(
            List<EntyPrvinvmdunidamedequipo> entities
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