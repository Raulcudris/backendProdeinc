package com.system.modules.controlobras.dataproviders.jpa;

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

import com.system.crosscutting.domain.model.EntyOrspspmdplansemanalDto;
import com.system.crosscutting.domain.model.EntyOrspspmdplansemanalResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrspspmdplansemanal;
import com.system.crosscutting.persistence.repository.EntyOrspspmdplansemanalRepository;
import com.system.modules.controlobras.dataproviders.IjpaPlanSemanalDataProviders;

@DataProvider
public class JpaPlanSemanalDataProviders implements IjpaPlanSemanalDataProviders {

    @Autowired
    private EntyOrspspmdplansemanalRepository repository;

    @Autowired
    @Qualifier("entyOrspspmdplansemanalEntityToDtoTranslate")
    private Translator<EntyOrspspmdplansemanal, EntyOrspspmdplansemanalDto> entityToDtoTranslate;

    @Autowired
    @Qualifier("entyOrspspmdplansemanalDtoToEntityTranslate")
    private Translator<EntyOrspspmdplansemanalDto, EntyOrspspmdplansemanal> dtoToEntityTranslate;

    private static final Logger logger = LogManager.getLogger(JpaPlanSemanalDataProviders.class);

    @Override
    public EntyOrspspmdplansemanalResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrspspmdplansemanalResponse getAll(
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
            Page<EntyOrspspmdplansemanal> responsePage;

            if (safeFilter.isEmpty()
                    && !"STATUS".equals(safeParameter)
                    && !"ESTADO".equals(safeParameter)
                    && !"SEMANA".equals(safeParameter)) {
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
                    case "PROYECCION":
                    case "PLAN_SEMANAL":
                        responsePage = repository.searchByIdentifKey(safeFilter, pageable);
                        break;

                    case "PLAN":
                    case "PLAN_TRABAJO":
                    case "PLTR":
                        responsePage = repository.searchByPlan(safeFilter, pageable);
                        break;

                    case "SEMANA":
                        try {
                            Integer semana = Integer.parseInt(safeFilter);
                            responsePage = repository.searchBySemana(semana, pageable);
                        } catch (NumberFormatException e) {
                            responsePage = repository.searchBySemana(-1, pageable);
                        }
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

            List<EntyOrspspmdplansemanalDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyOrspspmdplansemanalResponse response = new EntyOrspspmdplansemanalResponse();
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

    public EntyOrspspmdplansemanalResponse getByPlan(
            int currentPage,
            int pageSize,
            String planKey
    ) throws EBusinessException {
        return getAll(currentPage, pageSize, "PLAN", planKey);
    }

    @Override
    public EntyOrspspmdplansemanalDto get(Integer id) throws EBusinessException {
        try {
            EntyOrspspmdplansemanal entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyOrspspmdplansemanalDto();
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
    public EntyOrspspmdplansemanalDto save(
            EntyOrspspmdplansemanalDto dto
    ) throws EBusinessException {
        try {
            EntyOrspspmdplansemanal entity = dtoToEntityTranslate.translate(dto);
            EntyOrspspmdplansemanal saved = repository.save(entity);

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
    public List<EntyOrspspmdplansemanalDto> save(
            List<EntyOrspspmdplansemanalDto> dtos
    ) throws EBusinessException {
        try {
            List<EntyOrspspmdplansemanal> entities = new ArrayList<>();

            for (EntyOrspspmdplansemanalDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }

            List<EntyOrspspmdplansemanalDto> result = new ArrayList<>();

            for (EntyOrspspmdplansemanal entity : repository.saveAll(entities)) {
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
    public EntyOrspspmdplansemanalDto update(
            Integer id,
            EntyOrspspmdplansemanalDto dto
    ) throws EBusinessException {
        try {
            EntyOrspspmdplansemanal old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyOrspspmdplansemanalDto();
            }

            old.setOrsIdentifkeyPspl(dto.getOrsIdentifkeyPspl());
            old.setOrsIdentifkeyPltr(dto.getOrsIdentifkeyPltr());
            old.setOrsSemanaPspl(dto.getOrsSemanaPspl());
            old.setOrsFechainicioPspl(dto.getOrsFechainicioPspl());
            old.setOrsFechafinPspl(dto.getOrsFechafinPspl());
            old.setOrsCantidadprogPspl(dto.getOrsCantidadprogPspl());
            old.setOrsObservacionPspl(dto.getOrsObservacionPspl());
            old.setOrsEstadoregPspl(dto.getOrsEstadoregPspl());

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
            EntyOrspspmdplansemanal entity = repository.findById(id).orElse(null);

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

    private EntyOrspspmdplansemanalDto mapToDto(
            EntyOrspspmdplansemanal entity
    ) {
        EntyOrspspmdplansemanalDto dto = new EntyOrspspmdplansemanalDto();

        try {
            if (entity == null) {
                return dto;
            }

            return entityToDtoTranslate.translate(entity);

        } catch (Exception e) {
            logger.error(
                    "Error mapeando plan semanal a DTO. ID: {}",
                    entity != null ? entity.getOrsPrimarykeyPspl() : null,
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