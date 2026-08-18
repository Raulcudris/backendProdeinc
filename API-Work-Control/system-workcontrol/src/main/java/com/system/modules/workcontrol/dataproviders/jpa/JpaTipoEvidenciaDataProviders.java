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

import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyEvitipmatipoevidencia;
import com.system.crosscutting.persistence.repository.EntyEvitipmatipoevidenciaRepository;
import com.system.modules.workcontrol.dataproviders.IjpaTipoEvidenciaDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaTipoEvidenciaDataProviders extends JpaDataProviderSupport
        implements IjpaTipoEvidenciaDataProviders {

    private final EntyEvitipmatipoevidenciaRepository repository;

    @Override
    public EntyEvitipmatipoevidenciaResponse getAll()
            throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyEvitipmatipoevidenciaResponse getAll(
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
            Page<EntyEvitipmatipoevidencia> page;

            switch (safeParameter(parameter)) {
                case "ID":
                    page = repository.searchByPrimaryKey(
                            parseInteger(search),
                            pageable
                    );
                    break;

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

            List<EntyEvitipmatipoevidenciaDto> data = page
                    .getContent()
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            return buildResponse(
                    data,
                    "Tipos de evidencia consultados correctamente.",
                    "NA",
                    buildPagination(pageNumber + 1, size, page)
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando tipos de evidencia.",
                    e
            );
        }
    }

    @Override
    public EntyEvitipmatipoevidenciaDto get(
            final Integer id
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyEvitipmatipoevidenciaDto();
            }

            return repository
                    .findById(id)
                    .map(this::toDto)
                    .orElseGet(EntyEvitipmatipoevidenciaDto::new);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando tipo de evidencia.",
                    e
            );
        }
    }

    @Override
    public EntyEvitipmatipoevidenciaDto save(
            final EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException {
        try {
            EntyEvitipmatipoevidencia entity =
                    toEntity(normalizeForCreate(dto));

            return toDto(repository.save(entity));

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error creando tipo de evidencia.",
                    e
            );
        }
    }

    @Override
    public List<EntyEvitipmatipoevidenciaDto> save(
            final List<EntyEvitipmatipoevidenciaDto> dtos
    ) throws EBusinessException {
        try {
            if (dtos == null || dtos.isEmpty()) {
                return new ArrayList<>();
            }

            List<EntyEvitipmatipoevidencia> entities = dtos
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
                    "Error creando tipos de evidencia.",
                    e
            );
        }
    }

    @Override
    public EntyEvitipmatipoevidenciaDto update(
            final Integer id,
            final EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyEvitipmatipoevidenciaDto();
            }

            EntyEvitipmatipoevidencia current =
                    repository.findById(id).orElse(null);

            if (current == null) {
                return new EntyEvitipmatipoevidenciaDto();
            }

            EntyEvitipmatipoevidencia incoming =
                    toEntity(normalizeForUpdate(dto));

            incoming.setEviPrimarykeyTiev(id);

            BeanUtils.copyProperties(incoming, current);

            return toDto(repository.save(current));

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error actualizando tipo de evidencia.",
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
                    "Error eliminando tipo de evidencia.",
                    e
            );
        }
    }

    @Override
    public boolean existsByTipoEvidenciaKey(
            final String tipoEvidenciaKey
    ) throws EBusinessException {
        try {
            if (tipoEvidenciaKey == null
                    || tipoEvidenciaKey.trim().isEmpty()) {
                return false;
            }

            return repository.existsByEviIdentifkeyTiev(
                    tipoEvidenciaKey.trim().toUpperCase()
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error validando tipo de evidencia.",
                    e
            );
        }
    }

    @Override
    public List<EntyEvitipmatipoevidenciaDto> findByEstado(
            final String estado
    ) throws EBusinessException {
        try {
            if (estado == null || estado.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByEviEstadoregTievOrderByEviDescripcionTievAsc(
                            estado.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando tipos de evidencia por estado.",
                    e
            );
        }
    }

    @Override
    public EntyEvitipmatipoevidenciaResponse findByEstadoResponse(
            final String estado
    ) throws EBusinessException {
        return buildResponse(
                findByEstado(estado),
                "Tipos de evidencia por estado consultados correctamente.",
                estado != null ? estado.trim().toUpperCase() : "NA",
                null
        );
    }

    private EntyEvitipmatipoevidenciaDto normalizeForCreate(
            final EntyEvitipmatipoevidenciaDto dto
    ) {
        EntyEvitipmatipoevidenciaDto normalized =
                dto != null ? dto : new EntyEvitipmatipoevidenciaDto();

        normalized.setEviPrimarykeyTiev(null);
        normalized.setEviIdentifkeyTiev(
                upper(normalized.getEviIdentifkeyTiev())
        );
        normalized.setEviDescripcionTiev(
                clean(normalized.getEviDescripcionTiev())
        );
        normalized.setEviTiporegistTiev(
                upper(normalized.getEviTiporegistTiev())
        );
        normalized.setEviEstadoregTiev(
                upper(normalized.getEviEstadoregTiev())
        );

        if (normalized.getEviTiporegistTiev() == null) {
            normalized.setEviTiporegistTiev("1");
        }

        if (normalized.getEviEstadoregTiev() == null) {
            normalized.setEviEstadoregTiev("1");
        }

        return normalized;
    }

    private EntyEvitipmatipoevidenciaDto normalizeForUpdate(
            final EntyEvitipmatipoevidenciaDto dto
    ) {
        EntyEvitipmatipoevidenciaDto normalized =
                dto != null ? dto : new EntyEvitipmatipoevidenciaDto();

        normalized.setEviIdentifkeyTiev(
                upper(normalized.getEviIdentifkeyTiev())
        );
        normalized.setEviDescripcionTiev(
                clean(normalized.getEviDescripcionTiev())
        );
        normalized.setEviTiporegistTiev(
                upper(normalized.getEviTiporegistTiev())
        );
        normalized.setEviEstadoregTiev(
                upper(normalized.getEviEstadoregTiev())
        );

        if (normalized.getEviTiporegistTiev() == null) {
            normalized.setEviTiporegistTiev("1");
        }

        if (normalized.getEviEstadoregTiev() == null) {
            normalized.setEviEstadoregTiev("1");
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

    private EntyEvitipmatipoevidenciaDto toDto(
            final EntyEvitipmatipoevidencia entity
    ) {
        return toDto(entity, EntyEvitipmatipoevidenciaDto.class);
    }

    private EntyEvitipmatipoevidencia toEntity(
            final EntyEvitipmatipoevidenciaDto dto
    ) {
        return toEntity(dto, EntyEvitipmatipoevidencia.class);
    }

    private EntyEvitipmatipoevidenciaResponse buildResponse(
            final List<EntyEvitipmatipoevidenciaDto> data,
            final String message,
            final String parentKey,
            final PaginationResponse pagination
    ) {
        EntyEvitipmatipoevidenciaResponse response =
                new EntyEvitipmatipoevidenciaResponse();

        response.setRspValue("OK");
        response.setRspMessage(message);
        response.setRspParentKey(parentKey != null ? parentKey : "NA");
        response.setRspAppKey("WORK-CONTROL");
        response.setRspData(data != null ? data : new ArrayList<>());
        response.setRspPagination(pagination);

        return response;
    }
}