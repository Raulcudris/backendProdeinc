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

import com.system.crosscutting.domain.model.EntyOrsplnmaplantrabajoDto;
import com.system.crosscutting.domain.model.EntyOrsplnmaplantrabajoResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsplnmaplantrabajo;
import com.system.crosscutting.persistence.repository.EntyOrsplnmaplantrabajoRepository;
import com.system.modules.controlobras.dataproviders.IjpaPlanTrabajoDataProviders;

@DataProvider
public class JpaPlanTrabajoDataProviders implements IjpaPlanTrabajoDataProviders {

    @Autowired
    private EntyOrsplnmaplantrabajoRepository repository;

    @Autowired
    @Qualifier("entyOrsplnmaplantrabajoEntityToDtoTranslate")
    private Translator<EntyOrsplnmaplantrabajo, EntyOrsplnmaplantrabajoDto> entityToDtoTranslate;

    @Autowired
    @Qualifier("entyOrsplnmaplantrabajoDtoToEntityTranslate")
    private Translator<EntyOrsplnmaplantrabajoDto, EntyOrsplnmaplantrabajo> dtoToEntityTranslate;

    private static final Logger logger = LogManager.getLogger(JpaPlanTrabajoDataProviders.class);

    @Override
    public EntyOrsplnmaplantrabajoResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsplnmaplantrabajoResponse getAll(
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
            Page<EntyOrsplnmaplantrabajo> responsePage;

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
                    case "PLAN":
                        responsePage = repository.searchByIdentifKey(safeFilter, pageable);
                        break;

                    case "ORDEN":
                    case "ORDEN_SERVICIO":
                    case "ORS":
                        responsePage = repository.searchByOrden(safeFilter, pageable);
                        break;

                    case "SITIO":
                    case "SITR":
                        responsePage = repository.searchBySitio(safeFilter, pageable);
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

            List<EntyOrsplnmaplantrabajoDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyOrsplnmaplantrabajoResponse response = new EntyOrsplnmaplantrabajoResponse();
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

    public EntyOrsplnmaplantrabajoResponse getByOrden(
            int currentPage,
            int pageSize,
            String ordenKey
    ) throws EBusinessException {
        return getAll(currentPage, pageSize, "ORDEN", ordenKey);
    }

    public EntyOrsplnmaplantrabajoResponse getBySitio(
            int currentPage,
            int pageSize,
            String sitioKey
    ) throws EBusinessException {
        return getAll(currentPage, pageSize, "SITIO", sitioKey);
    }

    @Override
    public EntyOrsplnmaplantrabajoDto get(Integer id) throws EBusinessException {
        try {
            EntyOrsplnmaplantrabajo entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyOrsplnmaplantrabajoDto();
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
    public EntyOrsplnmaplantrabajoDto save(
            EntyOrsplnmaplantrabajoDto dto
    ) throws EBusinessException {
        try {
            EntyOrsplnmaplantrabajo entity = dtoToEntityTranslate.translate(dto);
            EntyOrsplnmaplantrabajo saved = repository.save(entity);

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
    public List<EntyOrsplnmaplantrabajoDto> save(
            List<EntyOrsplnmaplantrabajoDto> dtos
    ) throws EBusinessException {
        try {
            List<EntyOrsplnmaplantrabajo> entities = new ArrayList<>();

            for (EntyOrsplnmaplantrabajoDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }

            List<EntyOrsplnmaplantrabajoDto> result = new ArrayList<>();

            for (EntyOrsplnmaplantrabajo entity : repository.saveAll(entities)) {
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
    public EntyOrsplnmaplantrabajoDto update(
            Integer id,
            EntyOrsplnmaplantrabajoDto dto
    ) throws EBusinessException {
        try {
            EntyOrsplnmaplantrabajo old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyOrsplnmaplantrabajoDto();
            }

            old.setOrsIdentifkeyPltr(dto.getOrsIdentifkeyPltr());
            old.setOrsIdentifkeyOrde(dto.getOrsIdentifkeyOrde());
            old.setOrsIdentifkeySitr(dto.getOrsIdentifkeySitr());
            old.setOrsActividadPltr(dto.getOrsActividadPltr());
            old.setOrsDescripcionPltr(dto.getOrsDescripcionPltr());
            old.setOrsUnidadmedidaPltr(dto.getOrsUnidadmedidaPltr());
            old.setOrsCantidadprogPltr(dto.getOrsCantidadprogPltr());
            old.setOrsFechainicioPltr(dto.getOrsFechainicioPltr());
            old.setOrsFechafinPltr(dto.getOrsFechafinPltr());
            old.setOrsEstadoregPltr(dto.getOrsEstadoregPltr());

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
            EntyOrsplnmaplantrabajo entity = repository.findById(id).orElse(null);

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

    private EntyOrsplnmaplantrabajoDto mapToDto(
            EntyOrsplnmaplantrabajo entity
    ) {
        EntyOrsplnmaplantrabajoDto dto = new EntyOrsplnmaplantrabajoDto();

        try {
            if (entity == null) {
                return dto;
            }

            return entityToDtoTranslate.translate(entity);

        } catch (Exception e) {
            logger.error(
                    "Error mapeando plan de trabajo a DTO. ID: {}",
                    entity != null ? entity.getOrsPrimarykeyPltr() : null,
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