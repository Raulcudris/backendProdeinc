package com.system.modules.workcontrol.dataproviders.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyOrsconfnovedadtiposDto;
import com.system.crosscutting.domain.model.EntyOrsconfnovedadtiposResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsconfnovedadtipos;
import com.system.crosscutting.persistence.repository.EntyOrsconfnovedadtiposRepository;
import com.system.modules.workcontrol.dataproviders.IjpaTipoNovedadDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaTipoNovedadDataProviders extends JpaDataProviderSupport
        implements IjpaTipoNovedadDataProviders {

    private final EntyOrsconfnovedadtiposRepository repository;

    @Override
    public EntyOrsconfnovedadtiposResponse getAll()
            throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsconfnovedadtiposResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        try {
            int pageNumber = safeCurrentPage(currentPage);
            int size = safePageSize(pageSize);
            String search = safeFilter(filter);

            Pageable pageable = PageRequest.of(pageNumber, size);
            Page<EntyOrsconfnovedadtipos> page;

            switch (safeParameter(parameter)) {
                case "ID":
                    page = repository.searchByPrimaryKey(
                            parseInteger(search),
                            pageable
                    );
                    break;

                case "TIPO":
                    page = repository.searchByTipo(search, pageable);
                    break;

                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;

                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyOrsconfnovedadtiposDto> data = page
                    .getContent()
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            return buildResponse(
                    data,
                    "Tipos de novedad consultados correctamente.",
                    "NA",
                    buildPagination(pageNumber + 1, size, page)
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando tipos de novedad.",
                    e
            );
        }
    }

    @Override
    public EntyOrsconfnovedadtiposDto get(
            final Integer id
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyOrsconfnovedadtiposDto();
            }

            return repository
                    .findById(id)
                    .map(this::toDto)
                    .orElseGet(EntyOrsconfnovedadtiposDto::new);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando tipo de novedad.",
                    e
            );
        }
    }

    @Override
    public EntyOrsconfnovedadtiposDto save(
            final EntyOrsconfnovedadtiposDto dto
    ) throws EBusinessException {
        try {
            EntyOrsconfnovedadtiposDto normalized =
                    normalizeForCreate(dto);

            EntyOrsconfnovedadtipos entity =
                    toEntity(normalized);

            EntyOrsconfnovedadtipos saved =
                    repository.save(entity);

            return toDto(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error creando tipo de novedad.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsconfnovedadtiposDto> save(
            final List<EntyOrsconfnovedadtiposDto> dtos
    ) throws EBusinessException {
        try {
            if (dtos == null || dtos.isEmpty()) {
                return new ArrayList<>();
            }

            List<EntyOrsconfnovedadtipos> entities = dtos
                    .stream()
                    .map(this::normalizeForCreate)
                    .map(this::toEntity)
                    .collect(Collectors.toList());

            return repository
                    .saveAll(entities)
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error creando tipos de novedad.",
                    e
            );
        }
    }

    @Override
    public EntyOrsconfnovedadtiposDto update(
            final Integer id,
            final EntyOrsconfnovedadtiposDto dto
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyOrsconfnovedadtiposDto();
            }

            EntyOrsconfnovedadtipos current =
                    repository.findById(id).orElse(null);

            if (current == null) {
                return new EntyOrsconfnovedadtiposDto();
            }

            EntyOrsconfnovedadtipos incoming =
                    toEntity(normalizeForUpdate(dto));

            incoming.setOrsPrimarykeyNovt(id);

            BeanUtils.copyProperties(incoming, current);

            EntyOrsconfnovedadtipos saved =
                    repository.save(current);

            return toDto(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error actualizando tipo de novedad.",
                    e
            );
        }
    }

    @Override
    public void delete(
            final Integer id
    ) throws EBusinessException {
        try {
            if (id == null || !repository.existsById(id)) {
                return;
            }

            repository.deleteById(id);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error eliminando tipo de novedad.",
                    e
            );
        }
    }

    @Override
    public boolean existsByTipoNovedad(
            final String tipoNovedad
    ) throws EBusinessException {
        try {
            if (tipoNovedad == null || tipoNovedad.trim().isEmpty()) {
                return false;
            }

            return repository.existsByOrsTiponovedadNovt(
                    tipoNovedad.trim().toUpperCase()
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error validando tipo de novedad.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsconfnovedadtiposDto> findByEstado(
            final String estado
    ) throws EBusinessException {
        try {
            if (estado == null || estado.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByOrsEstadoregNovt(estado.trim().toUpperCase())
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando tipos de novedad por estado.",
                    e
            );
        }
    }

    @Override
    public EntyOrsconfnovedadtiposResponse findByEstadoResponse(
            final String estado
    ) throws EBusinessException {
        return buildResponse(
                findByEstado(estado),
                "Tipos de novedad por estado consultados correctamente.",
                estado != null ? estado.trim().toUpperCase() : "NA",
                null
        );
    }

    private EntyOrsconfnovedadtiposDto normalizeForCreate(
            final EntyOrsconfnovedadtiposDto dto
    ) {
        EntyOrsconfnovedadtiposDto normalized =
                dto != null ? dto : new EntyOrsconfnovedadtiposDto();

        normalized.setOrsPrimarykeyNovt(null);
        normalized.setOrsTiponovedadNovt(
                upper(normalized.getOrsTiponovedadNovt())
        );
        normalized.setOrsDescnovedadNovt(
                clean(normalized.getOrsDescnovedadNovt())
        );
        normalized.setOrsEstadoregNovt(
                upper(normalized.getOrsEstadoregNovt())
        );

        if (normalized.getOrsEstadoregNovt() == null
                || normalized.getOrsEstadoregNovt().trim().isEmpty()) {
            normalized.setOrsEstadoregNovt("1");
        }

        return normalized;
    }

    private EntyOrsconfnovedadtiposDto normalizeForUpdate(
            final EntyOrsconfnovedadtiposDto dto
    ) {
        EntyOrsconfnovedadtiposDto normalized =
                dto != null ? dto : new EntyOrsconfnovedadtiposDto();

        normalized.setOrsTiponovedadNovt(
                upper(normalized.getOrsTiponovedadNovt())
        );
        normalized.setOrsDescnovedadNovt(
                clean(normalized.getOrsDescnovedadNovt())
        );
        normalized.setOrsEstadoregNovt(
                upper(normalized.getOrsEstadoregNovt())
        );

        if (normalized.getOrsEstadoregNovt() == null
                || normalized.getOrsEstadoregNovt().trim().isEmpty()) {
            normalized.setOrsEstadoregNovt("1");
        }

        return normalized;
    }

    private String clean(final String value) {
        if (value == null) {
            return null;
        }

        String clean = value.trim();

        return clean.isEmpty() ? null : clean;
    }

    private String upper(final String value) {
        String clean = clean(value);

        return clean == null ? null : clean.toUpperCase();
    }

    private EntyOrsconfnovedadtiposDto toDto(
            final EntyOrsconfnovedadtipos entity
    ) {
        return toDto(entity, EntyOrsconfnovedadtiposDto.class);
    }

    private EntyOrsconfnovedadtipos toEntity(
            final EntyOrsconfnovedadtiposDto dto
    ) {
        return toEntity(dto, EntyOrsconfnovedadtipos.class);
    }

    private EntyOrsconfnovedadtiposResponse buildResponse(
            final List<EntyOrsconfnovedadtiposDto> data,
            final String message,
            final String parentKey,
            final PaginationResponse pagination
    ) {
        EntyOrsconfnovedadtiposResponse response =
                new EntyOrsconfnovedadtiposResponse();

        response.setRspValue("OK");
        response.setRspMessage(message);
        response.setRspParentKey(parentKey != null ? parentKey : "NA");
        response.setRspAppKey("WORK-CONTROL");
        response.setRspData(data != null ? data : new ArrayList<>());
        response.setRspPagination(pagination);

        return response;
    }
}