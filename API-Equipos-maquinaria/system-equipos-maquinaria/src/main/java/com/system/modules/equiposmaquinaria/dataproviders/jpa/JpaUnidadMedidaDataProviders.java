package com.system.modules.equiposmaquinaria.dataproviders.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import com.system.modules.equiposmaquinaria.dataproviders.IjpaUnidadMedidaDataProviders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyEqumedmaunidadmedidaDto;
import com.system.crosscutting.domain.model.EntyEqumedmaunidadmedidaResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEqumedmaunidadmedida;
import com.system.crosscutting.persistence.repository.EntyEqumedmaunidadmedidaRepository;

@DataProvider
public class JpaUnidadMedidaDataProviders implements IjpaUnidadMedidaDataProviders {

    @Autowired
    private EntyEqumedmaunidadmedidaRepository repository;

    @Autowired
    @Qualifier("entyEqumedmaunidadmedidaEntityToDtoTranslate")
    private Translator<EntyEqumedmaunidadmedida, EntyEqumedmaunidadmedidaDto> entityToDtoTranslate;

    @Autowired
    @Qualifier("entyEqumedmaunidadmedidaDtoToEntityTranslate")
    private Translator<EntyEqumedmaunidadmedidaDto, EntyEqumedmaunidadmedida> dtoToEntityTranslate;

    private static final Logger logger = LogManager.getLogger(JpaUnidadMedidaDataProviders.class);

    @Override
    public EntyEqumedmaunidadmedidaResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyEqumedmaunidadmedidaResponse getAll(
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
            Page<EntyEqumedmaunidadmedida> responsePage;

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
                    case "CODIGO_FUNCIONAL":
                    case "KEY":
                        responsePage = repository.searchByIdentifKey(safeFilter, pageable);
                        break;

                    case "CODIGO":
                    case "CODE":
                    case "ABREVIATURA":
                        responsePage = repository.searchByCodigo(safeFilter, pageable);
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

            List<EntyEqumedmaunidadmedidaDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyEqumedmaunidadmedidaResponse response = new EntyEqumedmaunidadmedidaResponse();

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

    @Override
    public EntyEqumedmaunidadmedidaDto get(Integer id) throws EBusinessException {
        try {
            EntyEqumedmaunidadmedida entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyEqumedmaunidadmedidaDto();
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
    public EntyEqumedmaunidadmedidaDto save(
            EntyEqumedmaunidadmedidaDto dto
    ) throws EBusinessException {
        try {
            EntyEqumedmaunidadmedida entity = dtoToEntityTranslate.translate(dto);
            EntyEqumedmaunidadmedida saved = repository.save(entity);

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
    public List<EntyEqumedmaunidadmedidaDto> save(
            List<EntyEqumedmaunidadmedidaDto> dtos
    ) throws EBusinessException {
        try {
            List<EntyEqumedmaunidadmedida> entities = new ArrayList<>();

            for (EntyEqumedmaunidadmedidaDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }

            List<EntyEqumedmaunidadmedidaDto> result = new ArrayList<>();

            for (EntyEqumedmaunidadmedida entity : repository.saveAll(entities)) {
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
    public EntyEqumedmaunidadmedidaDto update(
            Integer id,
            EntyEqumedmaunidadmedidaDto dto
    ) throws EBusinessException {
        try {
            EntyEqumedmaunidadmedida old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyEqumedmaunidadmedidaDto();
            }

            old.setEquIdentifkeyUnme(dto.getEquIdentifkeyUnme());
            old.setEquCodigoUnme(dto.getEquCodigoUnme());
            old.setEquDescripcionUnme(dto.getEquDescripcionUnme());
            old.setEquEstadoregUnme(dto.getEquEstadoregUnme());

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
            EntyEqumedmaunidadmedida entity = repository.findById(id).orElse(null);

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

    private EntyEqumedmaunidadmedidaDto mapToDto(
            EntyEqumedmaunidadmedida entity
    ) {
        EntyEqumedmaunidadmedidaDto dto = new EntyEqumedmaunidadmedidaDto();

        try {
            if (entity == null) {
                return dto;
            }

            return entityToDtoTranslate.translate(entity);

        } catch (Exception e) {
            logger.error(
                    "Error mapeando unidad de medida a DTO. ID: {}",
                    entity != null ? entity.getEquPrimarykeyUnme() : null,
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