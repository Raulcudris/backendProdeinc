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

import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaDto;
import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsplamdplantrabsemana;
import com.system.crosscutting.persistence.repository.EntyOrsplamdplantrabsemanaRepository;
import com.system.modules.workcontrol.dataproviders.IjpaPlanTrabajoSemanaDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaPlanTrabajoSemanaDataProviders extends JpaDataProviderSupport
        implements IjpaPlanTrabajoSemanaDataProviders {

    private static final String ESTADO_ABIERTO = "1";

    private final EntyOrsplamdplantrabsemanaRepository repository;

    @Override
    public EntyOrsplamdplantrabsemanaResponse getAll()
            throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsplamdplantrabsemanaResponse getAll(
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
            Page<EntyOrsplamdplantrabsemana> page;

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

                case "PLAN":
                    page = repository.searchByPlanTrabajo(
                            search,
                            pageable
                    );
                    break;

                case "SEMANA":
                    page = repository.searchBySemana(
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

            List<EntyOrsplamdplantrabsemanaDto> data = page
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
                    "Error consultando plan de trabajo semanal.",
                    e
            );
        }
    }

    @Override
    public EntyOrsplamdplantrabsemanaDto get(
            final Integer id
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyOrsplamdplantrabsemanaDto();
            }

            return repository
                    .findById(id)
                    .map(this::toDto)
                    .orElseGet(EntyOrsplamdplantrabsemanaDto::new);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando plan de trabajo semanal.",
                    e
            );
        }
    }

    @Override
    public EntyOrsplamdplantrabsemanaDto save(
            final EntyOrsplamdplantrabsemanaDto dto
    ) throws EBusinessException {
        try {
            EntyOrsplamdplantrabsemanaDto normalized =
                    normalizeForCreate(dto);

            EntyOrsplamdplantrabsemana entity =
                    toEntity(normalized);

            EntyOrsplamdplantrabsemana saved =
                    repository.save(entity);

            return toDto(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error creando plan de trabajo semanal.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsplamdplantrabsemanaDto> save(
            final List<EntyOrsplamdplantrabsemanaDto> dtos
    ) throws EBusinessException {
        try {
            if (dtos == null || dtos.isEmpty()) {
                return new ArrayList<>();
            }

            List<EntyOrsplamdplantrabsemana> entities = dtos
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
                    "Error creando planes de trabajo semanales.",
                    e
            );
        }
    }

    @Override
    public EntyOrsplamdplantrabsemanaDto update(
            final Integer id,
            final EntyOrsplamdplantrabsemanaDto dto
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyOrsplamdplantrabsemanaDto();
            }

            EntyOrsplamdplantrabsemana current = repository
                    .findById(id)
                    .orElse(null);

            if (current == null) {
                return new EntyOrsplamdplantrabsemanaDto();
            }

            EntyOrsplamdplantrabsemana incoming =
                    toEntity(dto);

            incoming.setOrsPrimarykeyPlse(id);

            if (incoming.getOrsIdentifkeyPlse() == null
                    || incoming.getOrsIdentifkeyPlse().trim().isEmpty()) {
                incoming.setOrsIdentifkeyPlse(
                        current.getOrsIdentifkeyPlse()
                );
            }

            BeanUtils.copyProperties(
                    incoming,
                    current
            );

            EntyOrsplamdplantrabsemana saved =
                    repository.save(current);

            return toDto(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error actualizando plan de trabajo semanal.",
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
                    "Error eliminando plan de trabajo semanal.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsplamdplantrabsemanaDto> findByOrden(
            final String ordenKey
    ) throws EBusinessException {
        try {
            if (ordenKey == null || ordenKey.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByOrsIdentifkeyOrdeOrderByOrsPrimarykeyPlseAsc(
                            ordenKey.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando planes semanales por orden.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsplamdplantrabsemanaDto> findByPlanTrabajo(
            final String planTrabajoKey
    ) throws EBusinessException {
        try {
            if (planTrabajoKey == null || planTrabajoKey.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByOrsIdentifkeyPltrOrderByOrsPrimarykeyPlseAsc(
                            planTrabajoKey.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando planes semanales por plan de trabajo.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsplamdplantrabsemanaDto> findBySemana(
            final String semanaKey
    ) throws EBusinessException {
        try {
            if (semanaKey == null || semanaKey.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByOrsIdentifkeyPsemOrderByOrsPrimarykeyPlseAsc(
                            semanaKey.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando planes semanales por semana.",
                    e
            );
        }
    }

    @Override
    public boolean existsByPlanSemanaKey(
            final String planSemanaKey
    ) throws EBusinessException {
        try {
            if (planSemanaKey == null || planSemanaKey.trim().isEmpty()) {
                return false;
            }

            return repository.existsByOrsIdentifkeyPlse(
                    planSemanaKey.trim().toUpperCase()
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error validando código de plan semanal.",
                    e
            );
        }
    }

    @Override
    public boolean existsActiveByOrden(
            final String ordenKey
    ) throws EBusinessException {
        try {
            if (ordenKey == null || ordenKey.trim().isEmpty()) {
                return false;
            }

            return repository
                    .existsByOrsIdentifkeyOrdeAndOrsEstadoregPlse(
                            ordenKey.trim().toUpperCase(),
                            ESTADO_ABIERTO
                    );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error validando planes semanales activos por orden.",
                    e
            );
        }
    }

    @Override
    public EntyOrsplamdplantrabsemanaResponse findByOrdenResponse(
            final String ordenKey
    ) throws EBusinessException {
        List<EntyOrsplamdplantrabsemanaDto> data =
                findByOrden(ordenKey);

        return buildResponse(
                data,
                "Planes semanales consultados correctamente.",
                ordenKey != null ? ordenKey.trim().toUpperCase() : "NA",
                null
        );
    }

    @Override
    public EntyOrsplamdplantrabsemanaResponse findByPlanTrabajoResponse(
            final String planTrabajoKey
    ) throws EBusinessException {
        List<EntyOrsplamdplantrabsemanaDto> data =
                findByPlanTrabajo(planTrabajoKey);

        return buildResponse(
                data,
                "Planes semanales por plan de trabajo consultados correctamente.",
                planTrabajoKey != null
                        ? planTrabajoKey.trim().toUpperCase()
                        : "NA",
                null
        );
    }

    @Override
    public EntyOrsplamdplantrabsemanaResponse findBySemanaResponse(
            final String semanaKey
    ) throws EBusinessException {
        List<EntyOrsplamdplantrabsemanaDto> data =
                findBySemana(semanaKey);

        return buildResponse(
                data,
                "Planes semanales por semana consultados correctamente.",
                semanaKey != null ? semanaKey.trim().toUpperCase() : "NA",
                null
        );
    }

    private EntyOrsplamdplantrabsemanaDto normalizeForCreate(
            final EntyOrsplamdplantrabsemanaDto dto
    ) {
        EntyOrsplamdplantrabsemanaDto normalized =
                dto != null ? dto : new EntyOrsplamdplantrabsemanaDto();

        normalized.setOrsPrimarykeyPlse(null);

        if (normalized.getOrsIdentifkeyPlse() != null) {
            normalized.setOrsIdentifkeyPlse(
                    normalized.getOrsIdentifkeyPlse()
                            .trim()
                            .toUpperCase()
            );
        }

        if (normalized.getOrsIdentifkeyOrde() != null) {
            normalized.setOrsIdentifkeyOrde(
                    normalized.getOrsIdentifkeyOrde()
                            .trim()
                            .toUpperCase()
            );
        }

        if (normalized.getOrsIdentifkeyPltr() != null) {
            normalized.setOrsIdentifkeyPltr(
                    normalized.getOrsIdentifkeyPltr()
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

        if (normalized.getOrsTiporegistPlse() == null
                || normalized.getOrsTiporegistPlse().trim().isEmpty()) {
            normalized.setOrsTiporegistPlse("1");
        }

        if (normalized.getOrsEstadoregPlse() == null
                || normalized.getOrsEstadoregPlse().trim().isEmpty()) {
            normalized.setOrsEstadoregPlse("1");
        }

        return normalized;
    }

    private EntyOrsplamdplantrabsemanaDto toDto(
            final EntyOrsplamdplantrabsemana entity
    ) {
        return toDto(
                entity,
                EntyOrsplamdplantrabsemanaDto.class
        );
    }

    private EntyOrsplamdplantrabsemana toEntity(
            final EntyOrsplamdplantrabsemanaDto dto
    ) {
        return toEntity(
                dto,
                EntyOrsplamdplantrabsemana.class
        );
    }

    private EntyOrsplamdplantrabsemanaResponse buildResponse(
            final List<EntyOrsplamdplantrabsemanaDto> data,
            final String message,
            final String parentKey,
            final PaginationResponse pagination
    ) {
        EntyOrsplamdplantrabsemanaResponse response =
                new EntyOrsplamdplantrabsemanaResponse();

        response.setRspMessage(message);
        response.setRspValue("OK");
        response.setRspParentKey(parentKey != null ? parentKey : "NA");
        response.setRspAppKey("WORK-CONTROL");
        response.setRspData(data != null ? data : new ArrayList<>());
        response.setRspPagination(pagination);

        return response;
    }
}