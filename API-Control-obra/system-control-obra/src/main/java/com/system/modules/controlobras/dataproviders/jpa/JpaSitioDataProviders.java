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

import com.system.crosscutting.domain.model.EntyOrssitmdsitioDto;
import com.system.crosscutting.domain.model.EntyOrssitmdsitioResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrssitmdsitio;
import com.system.crosscutting.persistence.repository.EntyOrssitmdsitioRepository;
import com.system.modules.controlobras.dataproviders.IjpaSitioDataProviders;

@DataProvider
public class JpaSitioDataProviders implements IjpaSitioDataProviders {

    @Autowired
    private EntyOrssitmdsitioRepository repository;

    @Autowired
    @Qualifier("entyOrssitmdsitioEntityToDtoTranslate")
    private Translator<EntyOrssitmdsitio, EntyOrssitmdsitioDto> entityToDtoTranslate;

    @Autowired
    @Qualifier("entyOrssitmdsitioDtoToEntityTranslate")
    private Translator<EntyOrssitmdsitioDto, EntyOrssitmdsitio> dtoToEntityTranslate;

    private static final Logger logger = LogManager.getLogger(JpaSitioDataProviders.class);

    @Override
    public EntyOrssitmdsitioResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrssitmdsitioResponse getAll(
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
            Page<EntyOrssitmdsitio> responsePage;

            if (safeFilter.isEmpty()
                    && !"STATUS".equals(safeParameter)
                    && !"ESTADO".equals(safeParameter)) {
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
                    case "SITIO":
                        responsePage = repository.searchByIdentifKey(safeFilter, pageable);
                        break;

                    case "ORDEN":
                    case "ORDEN_SERVICIO":
                    case "ORS":
                        responsePage = repository.searchByOrden(safeFilter, pageable);
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

            List<EntyOrssitmdsitioDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyOrssitmdsitioResponse response = new EntyOrssitmdsitioResponse();
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

    public EntyOrssitmdsitioResponse getByOrden(
            int currentPage,
            int pageSize,
            String ordenKey
    ) throws EBusinessException {
        return getAll(currentPage, pageSize, "ORDEN", ordenKey);
    }

    @Override
    public EntyOrssitmdsitioDto get(Integer id) throws EBusinessException {
        try {
            EntyOrssitmdsitio entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyOrssitmdsitioDto();
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
    public EntyOrssitmdsitioDto save(
            EntyOrssitmdsitioDto dto
    ) throws EBusinessException {
        try {
            EntyOrssitmdsitio entity = dtoToEntityTranslate.translate(dto);
            EntyOrssitmdsitio saved = repository.save(entity);

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
    public List<EntyOrssitmdsitioDto> save(
            List<EntyOrssitmdsitioDto> dtos
    ) throws EBusinessException {
        try {
            List<EntyOrssitmdsitio> entities = new ArrayList<>();

            for (EntyOrssitmdsitioDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }

            List<EntyOrssitmdsitioDto> result = new ArrayList<>();

            for (EntyOrssitmdsitio entity : repository.saveAll(entities)) {
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
    public EntyOrssitmdsitioDto update(
            Integer id,
            EntyOrssitmdsitioDto dto
    ) throws EBusinessException {
        try {
            EntyOrssitmdsitio old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyOrssitmdsitioDto();
            }

            old.setOrsIdentifkeySitr(dto.getOrsIdentifkeySitr());
            old.setOrsIdentifkeyOrde(dto.getOrsIdentifkeyOrde());
            old.setOrsNombreSitr(dto.getOrsNombreSitr());
            old.setOrsDescripcionSitr(dto.getOrsDescripcionSitr());
            old.setOrsUbicacionSitr(dto.getOrsUbicacionSitr());
            old.setOrsLatitudSitr(dto.getOrsLatitudSitr());
            old.setOrsLongitudSitr(dto.getOrsLongitudSitr());
            old.setOrsEstadoregSitr(dto.getOrsEstadoregSitr());

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
            EntyOrssitmdsitio entity = repository.findById(id).orElse(null);

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

    private EntyOrssitmdsitioDto mapToDto(
            EntyOrssitmdsitio entity
    ) {
        EntyOrssitmdsitioDto dto = new EntyOrssitmdsitioDto();

        try {
            if (entity == null) {
                return dto;
            }

            return entityToDtoTranslate.translate(entity);

        } catch (Exception e) {
            logger.error(
                    "Error mapeando sitio de trabajo a DTO. ID: {}",
                    entity != null ? entity.getOrsPrimarykeySitr() : null,
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