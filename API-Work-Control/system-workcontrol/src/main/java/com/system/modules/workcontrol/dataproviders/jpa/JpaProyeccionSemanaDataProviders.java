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

import com.system.crosscutting.domain.model.EntyOrsordmdproyecsemanaDto;
import com.system.crosscutting.domain.model.EntyOrsordmdproyecsemanaResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsordmdproyecsemana;
import com.system.crosscutting.persistence.repository.EntyOrsordmdproyecsemanaRepository;
import com.system.modules.workcontrol.dataproviders.IjpaProyeccionSemanaDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaProyeccionSemanaDataProviders extends JpaDataProviderSupport
        implements IjpaProyeccionSemanaDataProviders {

    private static final String ESTADO_ABIERTO = "1";

    private final EntyOrsordmdproyecsemanaRepository repository;

    @Override
    public EntyOrsordmdproyecsemanaResponse getAll()
            throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsordmdproyecsemanaResponse getAll(
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
            Page<EntyOrsordmdproyecsemana> page;

            switch (safeParameter(parameter)) {
                case "ID":
                    page = repository.searchByPrimaryKey(
                            parseInteger(search),
                            pageable
                    );
                    break;

                case "KEY":
                    page = repository.searchByIdentifKey(
                            search,
                            pageable
                    );
                    break;

                case "ORDEN":
                    page = repository.searchByOrden(
                            search,
                            pageable
                    );
                    break;

                case "STATUS":
                    page = repository.searchByStatus(
                            search,
                            pageable
                    );
                    break;

                default:
                    page = repository.searchByText(
                            search,
                            pageable
                    );
                    break;
            }

            List<EntyOrsordmdproyecsemanaDto> data = page
                    .getContent()
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            return buildResponse(
                    data,
                    "OK",
                    "NA",
                    buildPagination(pageNumber + 1, size, page)
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando proyección semanal.",
                    e
            );
        }
    }

    @Override
    public EntyOrsordmdproyecsemanaDto get(
            final Integer id
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyOrsordmdproyecsemanaDto();
            }

            return repository
                    .findById(id)
                    .map(this::toDto)
                    .orElseGet(EntyOrsordmdproyecsemanaDto::new);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando proyección semanal.",
                    e
            );
        }
    }

    @Override
    public EntyOrsordmdproyecsemanaDto save(
            final EntyOrsordmdproyecsemanaDto dto
    ) throws EBusinessException {
        try {
            EntyOrsordmdproyecsemanaDto normalized =
                    normalizeForCreate(dto);

            EntyOrsordmdproyecsemana entity =
                    toEntity(normalized);

            EntyOrsordmdproyecsemana saved =
                    repository.save(entity);

            return toDto(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error creando proyección semanal.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsordmdproyecsemanaDto> save(
            final List<EntyOrsordmdproyecsemanaDto> dtos
    ) throws EBusinessException {
        try {
            if (dtos == null || dtos.isEmpty()) {
                return new ArrayList<>();
            }

            List<EntyOrsordmdproyecsemana> entities = dtos
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
                    "Error creando proyección semanal masiva.",
                    e
            );
        }
    }

    @Override
    public EntyOrsordmdproyecsemanaDto update(
            final Integer id,
            final EntyOrsordmdproyecsemanaDto dto
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyOrsordmdproyecsemanaDto();
            }

            EntyOrsordmdproyecsemana current = repository
                    .findById(id)
                    .orElse(null);

            if (current == null) {
                return new EntyOrsordmdproyecsemanaDto();
            }

            EntyOrsordmdproyecsemana incoming =
                    toEntity(dto);

            incoming.setOrsPrimarykeyPsem(id);

            if (incoming.getOrsIdentifkeyPsem() == null
                    || incoming.getOrsIdentifkeyPsem().trim().isEmpty()) {
                incoming.setOrsIdentifkeyPsem(
                        current.getOrsIdentifkeyPsem()
                );
            }

            BeanUtils.copyProperties(
                    incoming,
                    current
            );

            EntyOrsordmdproyecsemana saved =
                    repository.save(current);

            return toDto(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error actualizando proyección semanal.",
                    e
            );
        }
    }

    @Override
    public void delete(final Integer id) throws EBusinessException {
        try {
            if (id == null || !repository.existsById(id)) {
                return;
            }

            repository.deleteById(id);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error eliminando proyección semanal.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsordmdproyecsemanaDto> findByOrden(
            final String ordenKey
    ) throws EBusinessException {
        try {
            if (ordenKey == null || ordenKey.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByOrsIdentifkeyOrdeOrderByOrsNumerosemPsemAsc(
                            ordenKey.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando proyección semanal por orden.",
                    e
            );
        }
    }

    public boolean existsActiveByOrden(
            final String ordenKey
    ) throws EBusinessException {
        try {
            if (ordenKey == null || ordenKey.trim().isEmpty()) {
                return false;
            }

            return repository
                    .existsByOrsIdentifkeyOrdeAndOrsEstadoregPsem(
                            ordenKey.trim().toUpperCase(),
                            ESTADO_ABIERTO
                    );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error validando existencia de proyección semanal activa.",
                    e
            );
        }
    }

    public EntyOrsordmdproyecsemanaResponse findByOrdenResponse(
            final String ordenKey
    ) throws EBusinessException {
        List<EntyOrsordmdproyecsemanaDto> data =
                findByOrden(ordenKey);

        return buildResponse(
                data,
                "Proyección semanal consultada correctamente.",
                ordenKey != null ? ordenKey.trim().toUpperCase() : "NA",
                null
        );
    }

    private EntyOrsordmdproyecsemanaDto normalizeForCreate(
            final EntyOrsordmdproyecsemanaDto dto
    ) {
        EntyOrsordmdproyecsemanaDto normalized =
                dto != null ? dto : new EntyOrsordmdproyecsemanaDto();

        normalized.setOrsPrimarykeyPsem(null);

        if (normalized.getOrsIdentifkeyOrde() != null) {
            normalized.setOrsIdentifkeyOrde(
                    normalized.getOrsIdentifkeyOrde()
                            .trim()
                            .toUpperCase()
            );
        }

        if (normalized.getOrsIdentifkeyPsem() != null) {
            normalized.setOrsIdentifkeyPsem(
                    normalized.getOrsIdentifkeyPsem()
                            .trim()
                            .toUpperCase()
            );
        }

        if (normalized.getOrsTiporegistPsem() == null
                || normalized.getOrsTiporegistPsem().isBlank()) {
            normalized.setOrsTiporegistPsem("1");
        }

        if (normalized.getOrsEstadoregPsem() == null
                || normalized.getOrsEstadoregPsem().isBlank()) {
            normalized.setOrsEstadoregPsem("1");
        }

        return normalized;
    }

    private EntyOrsordmdproyecsemanaDto toDto(
            final EntyOrsordmdproyecsemana entity
    ) {
        return toDto(
                entity,
                EntyOrsordmdproyecsemanaDto.class
        );
    }

    private EntyOrsordmdproyecsemana toEntity(
            final EntyOrsordmdproyecsemanaDto dto
    ) {
        return toEntity(
                dto,
                EntyOrsordmdproyecsemana.class
        );
    }

    private EntyOrsordmdproyecsemanaResponse buildResponse(
            final List<EntyOrsordmdproyecsemanaDto> data,
            final String message,
            final String parentKey,
            final Object pagination
    ) {
        EntyOrsordmdproyecsemanaResponse response =
                new EntyOrsordmdproyecsemanaResponse();

        response.setRspMessage(message);
        response.setRspValue("OK");
        response.setRspParentKey(parentKey != null ? parentKey : "NA");
        response.setRspAppKey("WORK-CONTROL");
        response.setRspData(data != null ? data : new ArrayList<>());

        if (pagination != null) {
            response.setRspPagination(
                    (com.system.crosscutting.domain.model.PaginationResponse)
                            pagination
            );
        }

        return response;
    }
}