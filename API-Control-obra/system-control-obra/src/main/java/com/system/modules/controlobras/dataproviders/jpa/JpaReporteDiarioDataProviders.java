package com.system.modules.controlobras.dataproviders.jpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyOrsrdomdreporteDiarioDto;
import com.system.crosscutting.domain.model.EntyOrsrdomdreporteDiarioResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsrdomdreporteDiario;
import com.system.crosscutting.persistence.repository.EntyOrsrdomdreporteDiarioRepository;
import com.system.modules.controlobras.dataproviders.IjpaReporteDiarioDataProviders;

@DataProvider
public class JpaReporteDiarioDataProviders implements IjpaReporteDiarioDataProviders {

    @Autowired
    private EntyOrsrdomdreporteDiarioRepository repository;

    @Autowired
    @Qualifier("entyOrsrdomdreporteDiarioEntityToDtoTranslate")
    private Translator<EntyOrsrdomdreporteDiario, EntyOrsrdomdreporteDiarioDto> entityToDtoTranslate;

    @Autowired
    @Qualifier("entyOrsrdomdreporteDiarioDtoToEntityTranslate")
    private Translator<EntyOrsrdomdreporteDiarioDto, EntyOrsrdomdreporteDiario> dtoToEntityTranslate;

    private static final Logger logger = LogManager.getLogger(JpaReporteDiarioDataProviders.class);

    @Override
    public EntyOrsrdomdreporteDiarioResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsrdomdreporteDiarioResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        try {
            int safeCurrentPage = currentPage <= 0 ? 0 : currentPage - 1;
            int safePageSize = pageSize <= 0 ? 10 : pageSize;

            String safeParameter = parameter == null || parameter.trim().isEmpty()
                    ? "TEXT"
                    : parameter.trim().toUpperCase();

            String safeFilter = filter == null ? "" : filter.trim();

            Pageable pageable = PageRequest.of(safeCurrentPage, safePageSize);
            Page<EntyOrsrdomdreporteDiario> responsePage;

            if (safeFilter.isEmpty()
                    && !"STATUS".equals(safeParameter)
                    && !"ESTADO".equals(safeParameter)
                    && !"HOY".equals(safeParameter)) {
                responsePage = repository.findAll(pageable);
            } else {
                switch (safeParameter) {
                    case "PKEY":
                        try {
                            Integer id = Integer.parseInt(safeFilter);
                            responsePage = repository.searchByPrimaryKey(id, pageable);
                        } catch (NumberFormatException e) {
                            responsePage = repository.searchByPrimaryKey(-1, pageable);
                        }
                        break;

                    case "REGKEY":
                    case "IDENTIFKEY":
                    case "CODIGO":
                    case "REPORTE":
                    case "DIARIO":
                        responsePage = repository.searchByIdentifKey(safeFilter, pageable);
                        break;

                    case "ORDEN":
                    case "ORDEN_SERVICIO":
                    case "ORS":
                        responsePage = repository.searchByOrden(safeFilter, pageable);
                        break;

                    case "PLAN":
                    case "PLAN_TRABAJO":
                    case "PLTR":
                        responsePage = repository.searchByPlan(safeFilter, pageable);
                        break;

                    case "SEMANA":
                    case "PLAN_SEMANAL":
                    case "PSPL":
                        responsePage = repository.searchByPlanSemanal(safeFilter, pageable);
                        break;

                    case "FECHA":
                        try {
                            LocalDate fecha = LocalDate.parse(safeFilter);
                            responsePage = repository.searchByFecha(fecha, pageable);
                        } catch (Exception e) {
                            responsePage = repository.searchByFecha(LocalDate.of(1900, 1, 1), pageable);
                        }
                        break;

                    case "HOY":
                        responsePage = repository.searchByFecha(LocalDate.now(), pageable);
                        break;

                    case "STATUS":
                    case "ESTADO":
                        if (safeFilter.isEmpty() || "ALL".equalsIgnoreCase(safeFilter)) {
                            responsePage = repository.findAll(pageable);
                        } else {
                            responsePage = repository.searchByStatus(safeFilter, pageable);
                        }
                        break;

                    case "TEXT":
                    case "SEARCH":
                    default:
                        responsePage = repository.searchByText(safeFilter, pageable);
                        break;
                }
            }

            List<EntyOrsrdomdreporteDiarioDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyOrsrdomdreporteDiarioResponse response = new EntyOrsrdomdreporteDiarioResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("NA");
            response.setRspData(content);
            response.setRspPagination(
                    headResponse(
                            safeCurrentPage + 1,
                            safePageSize,
                            responsePage.getTotalElements(),
                            responsePage.getTotalPages(),
                            responsePage.hasNext(),
                            responsePage.hasPrevious(),
                            "LocalHost",
                            "LocalHost"
                    )
            );

            return response;

        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.SEARCH_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.SEARCH_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    public EntyOrsrdomdreporteDiarioResponse getByOrden(
            int currentPage,
            int pageSize,
            String ordenKey
    ) throws EBusinessException {
        return getAll(currentPage, pageSize, "ORDEN", ordenKey);
    }

    public EntyOrsrdomdreporteDiarioResponse getByPlan(
            int currentPage,
            int pageSize,
            String planKey
    ) throws EBusinessException {
        return getAll(currentPage, pageSize, "PLAN", planKey);
    }

    public EntyOrsrdomdreporteDiarioResponse getByPlanSemanal(
            int currentPage,
            int pageSize,
            String planSemanalKey
    ) throws EBusinessException {
        return getAll(currentPage, pageSize, "SEMANA", planSemanalKey);
    }

    @Override
    public EntyOrsrdomdreporteDiarioDto get(Integer id) throws EBusinessException {
        try {
            EntyOrsrdomdreporteDiario entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyOrsrdomdreporteDiarioDto();
            }

            return mapToDto(entity);

        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.SEARCH_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.SEARCH_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    @Override
    public EntyOrsrdomdreporteDiarioDto save(
            EntyOrsrdomdreporteDiarioDto dto
    ) throws EBusinessException {
        try {
            EntyOrsrdomdreporteDiario entity = dtoToEntityTranslate.translate(dto);
            EntyOrsrdomdreporteDiario saved = repository.save(entity);

            return mapToDto(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.CREATE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.CREATE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    @Override
    public List<EntyOrsrdomdreporteDiarioDto> save(
            List<EntyOrsrdomdreporteDiarioDto> dtos
    ) throws EBusinessException {
        try {
            List<EntyOrsrdomdreporteDiario> entities = new ArrayList<>();

            for (EntyOrsrdomdreporteDiarioDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }

            List<EntyOrsrdomdreporteDiarioDto> result = new ArrayList<>();

            for (EntyOrsrdomdreporteDiario entity : repository.saveAll(entities)) {
                result.add(mapToDto(entity));
            }

            return result;

        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.CREATE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.CREATE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    @Override
    public EntyOrsrdomdreporteDiarioDto update(
            Integer id,
            EntyOrsrdomdreporteDiarioDto dto
    ) throws EBusinessException {
        try {
            EntyOrsrdomdreporteDiario old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyOrsrdomdreporteDiarioDto();
            }

            old.setOrsIdentifkeyRedi(dto.getOrsIdentifkeyRedi());
            old.setOrsIdentifkeyOrde(dto.getOrsIdentifkeyOrde());
            old.setOrsIdentifkeyPltr(dto.getOrsIdentifkeyPltr());
            old.setOrsIdentifkeyPspl(dto.getOrsIdentifkeyPspl());
            old.setOrsFechareporteRedi(dto.getOrsFechareporteRedi());
            old.setOrsActividadRedi(dto.getOrsActividadRedi());
            old.setOrsCantidadprogRedi(dto.getOrsCantidadprogRedi());
            old.setOrsCantidadejecRedi(dto.getOrsCantidadejecRedi());
            old.setOrsUnidadmedidaRedi(dto.getOrsUnidadmedidaRedi());
            old.setOrsResponsableRedi(dto.getOrsResponsableRedi());
            old.setOrsObservacionRedi(dto.getOrsObservacionRedi());
            old.setOrsEstadoregRedi(dto.getOrsEstadoregRedi());

            return mapToDto(repository.save(old));

        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.UPDATE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.UPDATE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    @Override
    public void delete(Integer id) throws EBusinessException {
        try {
            EntyOrsrdomdreporteDiario entity = repository.findById(id).orElse(null);

            if (entity != null) {
                repository.delete(entity);
            }

        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.DELETE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.DELETE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    private EntyOrsrdomdreporteDiarioDto mapToDto(
            EntyOrsrdomdreporteDiario entity
    ) {
        EntyOrsrdomdreporteDiarioDto dto = new EntyOrsrdomdreporteDiarioDto();

        try {
            if (entity == null) {
                return dto;
            }

            return entityToDtoTranslate.translate(entity);

        } catch (Exception e) {
            logger.error(
                    "Error mapeando reporte diario a DTO. ID: {}",
                    entity != null ? entity.getOrsPrimarykeyRedi() : null,
                    e
            );

            return dto;
        }
    }

    private PaginationResponse headResponse(
            int currentPage,
            int totalPageSize,
            long totalResults,
            int totalPages,
            boolean hasNextPage,
            boolean hasPreviousPage,
            String nextPageUrl,
            String previousPageUrl
    ) {
        return PaginationResponse.builder()
                .currentPage(currentPage)
                .totalPageSize(totalPageSize)
                .totalResults(totalResults)
                .totalPages(totalPages)
                .hasNextPage(hasNextPage)
                .hasPreviousPage(hasPreviousPage)
                .nextPageUrl(nextPageUrl)
                .previousPageUrl(previousPageUrl)
                .build();
    }
}