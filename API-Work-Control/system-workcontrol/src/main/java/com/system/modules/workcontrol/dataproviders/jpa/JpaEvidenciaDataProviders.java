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

import com.system.crosscutting.domain.model.EntyEvievimaevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvievimaevidenciaResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyEvievimaevidencia;
import com.system.crosscutting.persistence.repository.EntyEvievimaevidenciaRepository;
import com.system.modules.workcontrol.dataproviders.IjpaEvidenciaDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaEvidenciaDataProviders extends JpaDataProviderSupport
        implements IjpaEvidenciaDataProviders {

    private final EntyEvievimaevidenciaRepository repository;

    @Override
    public EntyEvievimaevidenciaResponse getAll()
            throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyEvievimaevidenciaResponse getAll(
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
            Page<EntyEvievimaevidencia> page;

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

            List<EntyEvievimaevidenciaDto> data = page
                    .getContent()
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            return buildResponse(
                    data,
                    "Evidencias consultadas correctamente.",
                    "NA",
                    buildPagination(pageNumber + 1, size, page)
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando evidencias.",
                    e
            );
        }
    }

    @Override
    public EntyEvievimaevidenciaDto get(
            final Integer id
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyEvievimaevidenciaDto();
            }

            return repository
                    .findById(id)
                    .map(this::toDto)
                    .orElseGet(EntyEvievimaevidenciaDto::new);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando evidencia.",
                    e
            );
        }
    }

    @Override
    public EntyEvievimaevidenciaDto findByEvidenciaKey(
            final String evidenciaKey
    ) throws EBusinessException {
        try {
            if (evidenciaKey == null || evidenciaKey.trim().isEmpty()) {
                return new EntyEvievimaevidenciaDto();
            }

            return repository
                    .findByEviIdentifkeyEvid(
                            evidenciaKey.trim().toUpperCase()
                    )
                    .map(this::toDto)
                    .orElseGet(EntyEvievimaevidenciaDto::new);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando evidencia por código.",
                    e
            );
        }
    }

    @Override
    public EntyEvievimaevidenciaDto save(
            final EntyEvievimaevidenciaDto dto
    ) throws EBusinessException {
        try {
            EntyEvievimaevidencia entity =
                    toEntity(normalizeForCreate(dto));

            return toDto(repository.save(entity));

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error creando evidencia.",
                    e
            );
        }
    }

    @Override
    public List<EntyEvievimaevidenciaDto> save(
            final List<EntyEvievimaevidenciaDto> dtos
    ) throws EBusinessException {
        try {
            if (dtos == null || dtos.isEmpty()) {
                return new ArrayList<>();
            }

            List<EntyEvievimaevidencia> entities = dtos
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
                    "Error creando evidencias.",
                    e
            );
        }
    }

    @Override
    public EntyEvievimaevidenciaDto update(
            final Integer id,
            final EntyEvievimaevidenciaDto dto
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyEvievimaevidenciaDto();
            }

            EntyEvievimaevidencia current =
                    repository.findById(id).orElse(null);

            if (current == null) {
                return new EntyEvievimaevidenciaDto();
            }

            EntyEvievimaevidencia incoming =
                    toEntity(normalizeForUpdate(dto));

            incoming.setEviPrimarykeyEvid(id);

            BeanUtils.copyProperties(incoming, current);

            return toDto(repository.save(current));

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error actualizando evidencia.",
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
                    "Error eliminando evidencia.",
                    e
            );
        }
    }

    @Override
    public boolean existsByEvidenciaKey(
            final String evidenciaKey
    ) throws EBusinessException {
        try {
            if (evidenciaKey == null || evidenciaKey.trim().isEmpty()) {
                return false;
            }

            return repository.existsByEviIdentifkeyEvid(
                    evidenciaKey.trim().toUpperCase()
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error validando evidencia.",
                    e
            );
        }
    }

    @Override
    public List<EntyEvievimaevidenciaDto> findByTipo(
            final String tipoEvidenciaKey
    ) throws EBusinessException {
        try {
            if (tipoEvidenciaKey == null
                    || tipoEvidenciaKey.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByEviIdentifkeyTievOrderByEviFechacapturaEvidDesc(
                            tipoEvidenciaKey.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando evidencias por tipo.",
                    e
            );
        }
    }

    @Override
    public List<EntyEvievimaevidenciaDto> findByEstado(
            final String estado
    ) throws EBusinessException {
        try {
            if (estado == null || estado.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByEviEstadoregEvidOrderByEviPrimarykeyEvidDesc(
                            estado.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando evidencias por estado.",
                    e
            );
        }
    }

    @Override
    public EntyEvievimaevidenciaResponse findByTipoResponse(
            final String tipoEvidenciaKey
    ) throws EBusinessException {
        return buildResponse(
                findByTipo(tipoEvidenciaKey),
                "Evidencias por tipo consultadas correctamente.",
                tipoEvidenciaKey != null
                        ? tipoEvidenciaKey.trim().toUpperCase()
                        : "NA",
                null
        );
    }

    @Override
    public EntyEvievimaevidenciaResponse findByEstadoResponse(
            final String estado
    ) throws EBusinessException {
        return buildResponse(
                findByEstado(estado),
                "Evidencias por estado consultadas correctamente.",
                estado != null ? estado.trim().toUpperCase() : "NA",
                null
        );
    }

    private EntyEvievimaevidenciaDto normalizeForCreate(
            final EntyEvievimaevidenciaDto dto
    ) {
        EntyEvievimaevidenciaDto normalized =
                dto != null ? dto : new EntyEvievimaevidenciaDto();

        normalized.setEviPrimarykeyEvid(null);
        normalized.setEviIdentifkeyEvid(
                upper(normalized.getEviIdentifkeyEvid())
        );
        normalized.setEviIdentifkeyTiev(
                upper(normalized.getEviIdentifkeyTiev())
        );
        normalized.setEviNombrearchivoEvid(
                clean(normalized.getEviNombrearchivoEvid())
        );
        normalized.setEviDescripcionEvid(
                clean(normalized.getEviDescripcionEvid())
        );
        normalized.setEviUrlarchivoEvid(
                clean(normalized.getEviUrlarchivoEvid())
        );
        normalized.setEviTiporegistEvid(
                upper(normalized.getEviTiporegistEvid())
        );
        normalized.setEviEstadoregEvid(
                upper(normalized.getEviEstadoregEvid())
        );

        if (normalized.getEviTiporegistEvid() == null) {
            normalized.setEviTiporegistEvid("1");
        }

        if (normalized.getEviEstadoregEvid() == null) {
            normalized.setEviEstadoregEvid("1");
        }

        return normalized;
    }

    private EntyEvievimaevidenciaDto normalizeForUpdate(
            final EntyEvievimaevidenciaDto dto
    ) {
        EntyEvievimaevidenciaDto normalized =
                dto != null ? dto : new EntyEvievimaevidenciaDto();

        normalized.setEviIdentifkeyEvid(
                upper(normalized.getEviIdentifkeyEvid())
        );
        normalized.setEviIdentifkeyTiev(
                upper(normalized.getEviIdentifkeyTiev())
        );
        normalized.setEviNombrearchivoEvid(
                clean(normalized.getEviNombrearchivoEvid())
        );
        normalized.setEviDescripcionEvid(
                clean(normalized.getEviDescripcionEvid())
        );
        normalized.setEviUrlarchivoEvid(
                clean(normalized.getEviUrlarchivoEvid())
        );
        normalized.setEviTiporegistEvid(
                upper(normalized.getEviTiporegistEvid())
        );
        normalized.setEviEstadoregEvid(
                upper(normalized.getEviEstadoregEvid())
        );

        if (normalized.getEviTiporegistEvid() == null) {
            normalized.setEviTiporegistEvid("1");
        }

        if (normalized.getEviEstadoregEvid() == null) {
            normalized.setEviEstadoregEvid("1");
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

    private EntyEvievimaevidenciaDto toDto(
            final EntyEvievimaevidencia entity
    ) {
        return toDto(entity, EntyEvievimaevidenciaDto.class);
    }

    private EntyEvievimaevidencia toEntity(
            final EntyEvievimaevidenciaDto dto
    ) {
        return toEntity(dto, EntyEvievimaevidencia.class);
    }

    private EntyEvievimaevidenciaResponse buildResponse(
            final List<EntyEvievimaevidenciaDto> data,
            final String message,
            final String parentKey,
            final PaginationResponse pagination
    ) {
        EntyEvievimaevidenciaResponse response =
                new EntyEvievimaevidenciaResponse();

        response.setRspValue("OK");
        response.setRspMessage(message);
        response.setRspParentKey(parentKey != null ? parentKey : "NA");
        response.setRspAppKey("WORK-CONTROL");
        response.setRspData(data != null ? data : new ArrayList<>());
        response.setRspPagination(pagination);

        return response;
    }
}