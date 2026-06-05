package com.system.modules.evidencia.dataproviders.jpa;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import com.system.modules.evidencia.dataproviders.IjpaTipoEvidenciaDataProviders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEvitipmatipoevidencia;
import com.system.crosscutting.persistence.repository.EntyEvitipmatipoevidenciaRepository;

@DataProvider
public class JpaTipoEvidenciaDataProviders implements IjpaTipoEvidenciaDataProviders {

    @Autowired
    private EntyEvitipmatipoevidenciaRepository repository;

    @Autowired
    @Qualifier("entyEvitipmatipoevidenciaEntityToDtoTranslate")
    private Translator<EntyEvitipmatipoevidencia, EntyEvitipmatipoevidenciaDto> entityToDtoTranslate;

    @Autowired
    @Qualifier("entyEvitipmatipoevidenciaDtoToEntityTranslate")
    private Translator<EntyEvitipmatipoevidenciaDto, EntyEvitipmatipoevidencia> dtoToEntityTranslate;

    private static final Logger logger = LogManager.getLogger(JpaTipoEvidenciaDataProviders.class);

    @Override
    public EntyEvitipmatipoevidenciaResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyEvitipmatipoevidenciaResponse getAll(
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
            Page<EntyEvitipmatipoevidencia> responsePage;

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
                    case "TIPO":
                        responsePage = repository.searchByIdentifKey(safeFilter, pageable);
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

            List<EntyEvitipmatipoevidenciaDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyEvitipmatipoevidenciaResponse response = new EntyEvitipmatipoevidenciaResponse();

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
    public EntyEvitipmatipoevidenciaDto get(Integer id) throws EBusinessException {
        try {
            EntyEvitipmatipoevidencia entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyEvitipmatipoevidenciaDto();
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
    public EntyEvitipmatipoevidenciaDto save(
            EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException {
        try {
            EntyEvitipmatipoevidencia entity = dtoToEntityTranslate.translate(dto);
            EntyEvitipmatipoevidencia saved = repository.save(entity);

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
    public List<EntyEvitipmatipoevidenciaDto> save(
            List<EntyEvitipmatipoevidenciaDto> dtos
    ) throws EBusinessException {
        try {
            List<EntyEvitipmatipoevidencia> entities = new ArrayList<>();

            for (EntyEvitipmatipoevidenciaDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }

            List<EntyEvitipmatipoevidenciaDto> result = new ArrayList<>();

            for (EntyEvitipmatipoevidencia entity : repository.saveAll(entities)) {
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
    public EntyEvitipmatipoevidenciaDto update(
            Integer id,
            EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException {
        try {
            EntyEvitipmatipoevidencia old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyEvitipmatipoevidenciaDto();
            }

            old.setEviIdentifkeyTiev(dto.getEviIdentifkeyTiev());
            old.setEviDescripcionTiev(dto.getEviDescripcionTiev());
            old.setEviEstadoregTiev(dto.getEviEstadoregTiev());

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
            EntyEvitipmatipoevidencia entity = repository.findById(id).orElse(null);

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

    private EntyEvitipmatipoevidenciaDto mapToDto(
            EntyEvitipmatipoevidencia entity
    ) {
        EntyEvitipmatipoevidenciaDto dto = new EntyEvitipmatipoevidenciaDto();

        try {
            if (entity == null) {
                return dto;
            }

            return entityToDtoTranslate.translate(entity);

        } catch (Exception e) {
            logger.error(
                    "Error mapeando tipo de evidencia a DTO. ID: {}",
                    entity != null ? entity.getEviPrimarykeyTiev() : null,
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