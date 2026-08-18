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

import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyEvirefmdreferencia;
import com.system.crosscutting.persistence.repository.EntyEvirefmdreferenciaRepository;
import com.system.modules.workcontrol.dataproviders.IjpaReferenciaEvidenciaDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaReferenciaEvidenciaDataProviders extends JpaDataProviderSupport
        implements IjpaReferenciaEvidenciaDataProviders {

    private final EntyEvirefmdreferenciaRepository repository;

    @Override
    public EntyEvirefmdreferenciaResponse getAll()
            throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyEvirefmdreferenciaResponse getAll(
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
            Page<EntyEvirefmdreferencia> page;

            switch (safeParameter(parameter)) {
                case "ID":
                    page = repository.searchByPrimaryKey(
                            parseInteger(search),
                            pageable
                    );
                    break;

                case "EVIDENCIA":
                    page = repository.searchByEvidencia(search, pageable);
                    break;

                case "REGISTRO":
                    page = repository.searchByRegistro(search, pageable);
                    break;

                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;

                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyEvirefmdreferenciaDto> data = page
                    .getContent()
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            return buildResponse(
                    data,
                    "Referencias de evidencia consultadas correctamente.",
                    "NA",
                    buildPagination(pageNumber + 1, size, page)
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando referencias de evidencia.",
                    e
            );
        }
    }

    @Override
    public EntyEvirefmdreferenciaDto get(
            final Integer id
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyEvirefmdreferenciaDto();
            }

            return repository
                    .findById(id)
                    .map(this::toDto)
                    .orElseGet(EntyEvirefmdreferenciaDto::new);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando referencia de evidencia.",
                    e
            );
        }
    }

    @Override
    public EntyEvirefmdreferenciaDto save(
            final EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException {
        try {
            EntyEvirefmdreferencia entity =
                    toEntity(normalizeForCreate(dto));

            return toDto(repository.save(entity));

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error creando referencia de evidencia.",
                    e
            );
        }
    }

    @Override
    public List<EntyEvirefmdreferenciaDto> save(
            final List<EntyEvirefmdreferenciaDto> dtos
    ) throws EBusinessException {
        try {
            if (dtos == null || dtos.isEmpty()) {
                return new ArrayList<>();
            }

            List<EntyEvirefmdreferencia> entities = dtos
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
                    "Error creando referencias de evidencia.",
                    e
            );
        }
    }

    @Override
    public EntyEvirefmdreferenciaDto update(
            final Integer id,
            final EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyEvirefmdreferenciaDto();
            }

            EntyEvirefmdreferencia current =
                    repository.findById(id).orElse(null);

            if (current == null) {
                return new EntyEvirefmdreferenciaDto();
            }

            EntyEvirefmdreferencia incoming =
                    toEntity(normalizeForUpdate(dto));

            incoming.setEviPrimarykeyRefe(id);

            BeanUtils.copyProperties(incoming, current);

            return toDto(repository.save(current));

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error actualizando referencia de evidencia.",
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
                    "Error eliminando referencia de evidencia.",
                    e
            );
        }
    }

    @Override
    public boolean existsByReferenciaKey(
            final String referenciaKey
    ) throws EBusinessException {
        try {
            if (referenciaKey == null || referenciaKey.trim().isEmpty()) {
                return false;
            }

            return repository.existsByEviIdentifkeyRefe(
                    referenciaKey.trim().toUpperCase()
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error validando referencia de evidencia.",
                    e
            );
        }
    }

    @Override
    public List<EntyEvirefmdreferenciaDto> findByEvidencia(
            final String evidenciaKey
    ) throws EBusinessException {
        try {
            if (evidenciaKey == null || evidenciaKey.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByEviIdentifkeyEvidOrderByEviPrimarykeyRefeAsc(
                            evidenciaKey.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando referencias por evidencia.",
                    e
            );
        }
    }

    @Override
    public List<EntyEvirefmdreferenciaDto> findByTipoRegistro(
            final String tipoRegistro
    ) throws EBusinessException {
        try {
            if (tipoRegistro == null || tipoRegistro.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByEviTiporegistroRefeOrderByEviPrimarykeyRefeDesc(
                            tipoRegistro.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando referencias por tipo de registro.",
                    e
            );
        }
    }

    @Override
    public List<EntyEvirefmdreferenciaDto> findByRegistro(
            final String tipoRegistro,
            final String identificadorRegistro
    ) throws EBusinessException {
        try {
            if (tipoRegistro == null
                    || tipoRegistro.trim().isEmpty()
                    || identificadorRegistro == null
                    || identificadorRegistro.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByEviTiporegistroRefeAndEviIdentifregistroRefeOrderByEviPrimarykeyRefeDesc(
                            tipoRegistro.trim().toUpperCase(),
                            identificadorRegistro.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando referencias por registro.",
                    e
            );
        }
    }

    @Override
    public EntyEvirefmdreferenciaResponse findByEvidenciaResponse(
            final String evidenciaKey
    ) throws EBusinessException {
        return buildResponse(
                findByEvidencia(evidenciaKey),
                "Referencias por evidencia consultadas correctamente.",
                evidenciaKey != null ? evidenciaKey.trim().toUpperCase() : "NA",
                null
        );
    }

    @Override
    public EntyEvirefmdreferenciaResponse findByRegistroResponse(
            final String tipoRegistro,
            final String identificadorRegistro
    ) throws EBusinessException {
        return buildResponse(
                findByRegistro(tipoRegistro, identificadorRegistro),
                "Referencias por registro consultadas correctamente.",
                identificadorRegistro != null
                        ? identificadorRegistro.trim().toUpperCase()
                        : "NA",
                null
        );
    }

    private EntyEvirefmdreferenciaDto normalizeForCreate(
            final EntyEvirefmdreferenciaDto dto
    ) {
        EntyEvirefmdreferenciaDto normalized =
                dto != null ? dto : new EntyEvirefmdreferenciaDto();

        normalized.setEviPrimarykeyRefe(null);
        normalized.setEviIdentifkeyRefe(
                upper(normalized.getEviIdentifkeyRefe())
        );
        normalized.setEviIdentifkeyEvid(
                upper(normalized.getEviIdentifkeyEvid())
        );
        normalized.setEviTiporegistroRefe(
                upper(normalized.getEviTiporegistroRefe())
        );
        normalized.setEviIdentifregistroRefe(
                upper(normalized.getEviIdentifregistroRefe())
        );
        normalized.setEviObservacionRefe(
                clean(normalized.getEviObservacionRefe())
        );
        normalized.setEviTiporegistRefe(
                upper(normalized.getEviTiporegistRefe())
        );
        normalized.setEviEstadoregRefe(
                upper(normalized.getEviEstadoregRefe())
        );

        if (normalized.getEviTiporegistRefe() == null) {
            normalized.setEviTiporegistRefe("1");
        }

        if (normalized.getEviEstadoregRefe() == null) {
            normalized.setEviEstadoregRefe("1");
        }

        return normalized;
    }

    private EntyEvirefmdreferenciaDto normalizeForUpdate(
            final EntyEvirefmdreferenciaDto dto
    ) {
        EntyEvirefmdreferenciaDto normalized =
                dto != null ? dto : new EntyEvirefmdreferenciaDto();

        normalized.setEviIdentifkeyRefe(
                upper(normalized.getEviIdentifkeyRefe())
        );
        normalized.setEviIdentifkeyEvid(
                upper(normalized.getEviIdentifkeyEvid())
        );
        normalized.setEviTiporegistroRefe(
                upper(normalized.getEviTiporegistroRefe())
        );
        normalized.setEviIdentifregistroRefe(
                upper(normalized.getEviIdentifregistroRefe())
        );
        normalized.setEviObservacionRefe(
                clean(normalized.getEviObservacionRefe())
        );
        normalized.setEviTiporegistRefe(
                upper(normalized.getEviTiporegistRefe())
        );
        normalized.setEviEstadoregRefe(
                upper(normalized.getEviEstadoregRefe())
        );

        if (normalized.getEviTiporegistRefe() == null) {
            normalized.setEviTiporegistRefe("1");
        }

        if (normalized.getEviEstadoregRefe() == null) {
            normalized.setEviEstadoregRefe("1");
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

    private EntyEvirefmdreferenciaDto toDto(
            final EntyEvirefmdreferencia entity
    ) {
        return toDto(entity, EntyEvirefmdreferenciaDto.class);
    }

    private EntyEvirefmdreferencia toEntity(
            final EntyEvirefmdreferenciaDto dto
    ) {
        return toEntity(dto, EntyEvirefmdreferencia.class);
    }

    private EntyEvirefmdreferenciaResponse buildResponse(
            final List<EntyEvirefmdreferenciaDto> data,
            final String message,
            final String parentKey,
            final PaginationResponse pagination
    ) {
        EntyEvirefmdreferenciaResponse response =
                new EntyEvirefmdreferenciaResponse();

        response.setRspValue("OK");
        response.setRspMessage(message);
        response.setRspParentKey(parentKey != null ? parentKey : "NA");
        response.setRspAppKey("WORK-CONTROL");
        response.setRspData(data != null ? data : new ArrayList<>());
        response.setRspPagination(pagination);

        return response;
    }
}