package com.system.modules.workcontrol.dataproviders.jpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioDto;
import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsplamdreportediario;
import com.system.crosscutting.persistence.repository.EntyOrsplamdreportediarioRepository;
import com.system.modules.workcontrol.dataproviders.IjpaReporteDiarioDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaReporteDiarioDataProviders extends JpaDataProviderSupport
        implements IjpaReporteDiarioDataProviders {

    private final EntyOrsplamdreportediarioRepository repository;

    @Override
    public EntyOrsplamdreportediarioResponse getAll()
            throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsplamdreportediarioResponse getAll(
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
            Page<EntyOrsplamdreportediario> page;

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

                case "ORDEN":
                    page = repository.searchByOrden(search, pageable);
                    break;

                case "PLAN_SEMANA":
                    page = repository.searchByPlanSemana(search, pageable);
                    break;

                case "SEMANA":
                    page = repository.searchBySemana(search, pageable);
                    break;

                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;

                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyOrsplamdreportediarioDto> data = page
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
                    "Error consultando reportes diarios.",
                    e
            );
        }
    }

    @Override
    public EntyOrsplamdreportediarioDto get(
            final Integer id
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyOrsplamdreportediarioDto();
            }

            return repository
                    .findById(id)
                    .map(this::toDto)
                    .orElseGet(EntyOrsplamdreportediarioDto::new);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando reporte diario.",
                    e
            );
        }
    }

    @Override
    public EntyOrsplamdreportediarioDto save(
            final EntyOrsplamdreportediarioDto dto
    ) throws EBusinessException {
        try {
            EntyOrsplamdreportediarioDto normalized =
                    normalizeForCreate(dto);

            EntyOrsplamdreportediario entity =
                    toEntity(normalized);

            EntyOrsplamdreportediario saved =
                    repository.save(entity);

            return toDto(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error creando reporte diario.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsplamdreportediarioDto> save(
            final List<EntyOrsplamdreportediarioDto> dtos
    ) throws EBusinessException {
        try {
            if (dtos == null || dtos.isEmpty()) {
                return new ArrayList<>();
            }

            List<EntyOrsplamdreportediario> entities = dtos
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
                    "Error creando reportes diarios.",
                    e
            );
        }
    }

    @Override
    public EntyOrsplamdreportediarioDto update(
            final Integer id,
            final EntyOrsplamdreportediarioDto dto
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyOrsplamdreportediarioDto();
            }

            EntyOrsplamdreportediario current =
                    repository.findById(id).orElse(null);

            if (current == null) {
                return new EntyOrsplamdreportediarioDto();
            }

            EntyOrsplamdreportediario incoming =
                    toEntity(dto);

            incoming.setOrsPrimarykeyPdia(id);

            if (incoming.getOrsIdentifkeyPdia() == null
                    || incoming.getOrsIdentifkeyPdia().trim().isEmpty()) {
                incoming.setOrsIdentifkeyPdia(
                        current.getOrsIdentifkeyPdia()
                );
            }

            BeanUtils.copyProperties(incoming, current);

            EntyOrsplamdreportediario saved =
                    repository.save(current);

            return toDto(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error actualizando reporte diario.",
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
                    "Error eliminando reporte diario.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsplamdreportediarioDto> findByOrden(
            final String ordenKey
    ) throws EBusinessException {
        try {
            if (ordenKey == null || ordenKey.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByOrsIdentifkeyOrdeOrderByOrsFechareportPdiaAsc(
                            ordenKey.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando reportes diarios por orden.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsplamdreportediarioDto> findByPlanSemana(
            final String planSemanaKey
    ) throws EBusinessException {
        try {
            if (planSemanaKey == null || planSemanaKey.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByOrsIdentifkeyPlseOrderByOrsFechareportPdiaAsc(
                            planSemanaKey.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando reportes diarios por plan semanal.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsplamdreportediarioDto> findBySemana(
            final String semanaKey
    ) throws EBusinessException {
        try {
            if (semanaKey == null || semanaKey.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByOrsIdentifkeyPsemOrderByOrsFechareportPdiaAsc(
                            semanaKey.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando reportes diarios por semana.",
                    e
            );
        }
    }

    @Override
    public boolean existsByReporteKey(
            final String reporteKey
    ) throws EBusinessException {
        try {
            if (reporteKey == null || reporteKey.trim().isEmpty()) {
                return false;
            }

            return repository.existsByOrsIdentifkeyPdia(
                    reporteKey.trim().toUpperCase()
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error validando código de reporte diario.",
                    e
            );
        }
    }

    @Override
    public boolean existsReporteValidoByPlanSemanaAndFecha(
            final String planSemanaKey,
            final LocalDate fecha
    ) throws EBusinessException {
        try {
            if (planSemanaKey == null
                    || planSemanaKey.trim().isEmpty()
                    || fecha == null) {
                return false;
            }

            return repository.existsReporteValidoByPlanSemanaAndFecha(
                    planSemanaKey.trim().toUpperCase(),
                    fecha
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error validando reporte diario duplicado.",
                    e
            );
        }
    }

    @Override
    public Long sumEjecutadoValidoByPlanSemana(
            final String planSemanaKey
    ) throws EBusinessException {
        try {
            if (planSemanaKey == null || planSemanaKey.trim().isEmpty()) {
                return 0L;
            }

            Long value = repository.sumEjecutadoValidoByPlanSemana(
                    planSemanaKey.trim().toUpperCase()
            );

            return value != null ? value : 0L;

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error sumando ejecución del plan semanal.",
                    e
            );
        }
    }

    @Override
    public EntyOrsplamdreportediarioResponse findByOrdenResponse(
            final String ordenKey
    ) throws EBusinessException {
        return buildResponse(
                findByOrden(ordenKey),
                "Reportes diarios por orden consultados correctamente.",
                ordenKey != null ? ordenKey.trim().toUpperCase() : "NA",
                null
        );
    }

    @Override
    public EntyOrsplamdreportediarioResponse findByPlanSemanaResponse(
            final String planSemanaKey
    ) throws EBusinessException {
        return buildResponse(
                findByPlanSemana(planSemanaKey),
                "Reportes diarios por plan semanal consultados correctamente.",
                planSemanaKey != null
                        ? planSemanaKey.trim().toUpperCase()
                        : "NA",
                null
        );
    }

    @Override
    public EntyOrsplamdreportediarioResponse findBySemanaResponse(
            final String semanaKey
    ) throws EBusinessException {
        return buildResponse(
                findBySemana(semanaKey),
                "Reportes diarios por semana consultados correctamente.",
                semanaKey != null ? semanaKey.trim().toUpperCase() : "NA",
                null
        );
    }

    private EntyOrsplamdreportediarioDto normalizeForCreate(
            final EntyOrsplamdreportediarioDto dto
    ) {
        EntyOrsplamdreportediarioDto normalized =
                dto != null ? dto : new EntyOrsplamdreportediarioDto();

        normalized.setOrsPrimarykeyPdia(null);

        normalized.setOrsIdentifkeyPdia(
                upper(normalized.getOrsIdentifkeyPdia())
        );
        normalized.setOrsIdentifkeyOrde(
                upper(normalized.getOrsIdentifkeyOrde())
        );
        normalized.setOrsIdentifkeyPlse(
                upper(normalized.getOrsIdentifkeyPlse())
        );
        normalized.setOrsIdentifkeyPsem(
                upper(normalized.getOrsIdentifkeyPsem())
        );
        normalized.setOrsTiporegistPdia(
                upper(normalized.getOrsTiporegistPdia())
        );
        normalized.setOrsEstadoregPdia(
                upper(normalized.getOrsEstadoregPdia())
        );

        if (normalized.getOrsTiporegistPdia() == null
                || normalized.getOrsTiporegistPdia().trim().isEmpty()) {
            normalized.setOrsTiporegistPdia("1");
        }

        if (normalized.getOrsEstadoregPdia() == null
                || normalized.getOrsEstadoregPdia().trim().isEmpty()) {
            normalized.setOrsEstadoregPdia("1");
        }

        return normalized;
    }

    private String upper(final String value) {
        if (value == null) {
            return null;
        }

        String clean = value.trim();

        return clean.isEmpty() ? null : clean.toUpperCase();
    }

    private EntyOrsplamdreportediarioDto toDto(
            final EntyOrsplamdreportediario entity
    ) {
        return toDto(entity, EntyOrsplamdreportediarioDto.class);
    }

    private EntyOrsplamdreportediario toEntity(
            final EntyOrsplamdreportediarioDto dto
    ) {
        return toEntity(dto, EntyOrsplamdreportediario.class);
    }

    private EntyOrsplamdreportediarioResponse buildResponse(
            final List<EntyOrsplamdreportediarioDto> data,
            final String message,
            final String parentKey,
            final PaginationResponse pagination
    ) {
        EntyOrsplamdreportediarioResponse response =
                new EntyOrsplamdreportediarioResponse();

        response.setRspValue("OK");
        response.setRspMessage(message);
        response.setRspParentKey(parentKey != null ? parentKey : "NA");
        response.setRspAppKey("WORK-CONTROL");
        response.setRspData(data != null ? data : new ArrayList<>());
        response.setRspPagination(pagination);

        return response;
    }
}