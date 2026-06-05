package com.system.modules.evidencia.dataproviders.jpa;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import com.system.modules.evidencia.dataproviders.IjpaReferenciaEvidenciaDataProviders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEvirefmdreferencia;
import com.system.crosscutting.persistence.repository.EntyEvirefmdreferenciaRepository;

@DataProvider
public class JpaReferenciaEvidenciaDataProviders implements IjpaReferenciaEvidenciaDataProviders {

    @Autowired
    private EntyEvirefmdreferenciaRepository repository;

    @Autowired
    @Qualifier("entyEvirefmdreferenciaEntityToDtoTranslate")
    private Translator<EntyEvirefmdreferencia, EntyEvirefmdreferenciaDto> entityToDtoTranslate;

    @Autowired
    @Qualifier("entyEvirefmdreferenciaDtoToEntityTranslate")
    private Translator<EntyEvirefmdreferenciaDto, EntyEvirefmdreferencia> dtoToEntityTranslate;

    private static final Logger logger = LogManager.getLogger(JpaReferenciaEvidenciaDataProviders.class);

    @Override
    public EntyEvirefmdreferenciaResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyEvirefmdreferenciaResponse getAll(
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
            Page<EntyEvirefmdreferencia> responsePage;

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
                    case "REFERENCIA":
                        responsePage = repository.searchByIdentifKey(safeFilter, pageable);
                        break;

                    case "EVIDENCIA":
                    case "EVID":
                        responsePage = repository.searchByEvidencia(safeFilter, pageable);
                        break;

                    case "TIPO_REFERENCIA":
                    case "TIPO":
                    case "TIPOREFERENCIA":
                        responsePage = repository.searchByTipoReferencia(safeFilter, pageable);
                        break;

                    case "REFERENCIA_ID":
                    case "REFERENCIAID":
                    case "ID_REFERENCIA":
                        responsePage = repository.searchByReferenciaId(safeFilter, pageable);
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

            List<EntyEvirefmdreferenciaDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyEvirefmdreferenciaResponse response = new EntyEvirefmdreferenciaResponse();

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

    public EntyEvirefmdreferenciaResponse getByEvidencia(
            int currentPage,
            int pageSize,
            String evidenciaKey
    ) throws EBusinessException {
        return getAll(currentPage, pageSize, "EVIDENCIA", evidenciaKey);
    }

    public EntyEvirefmdreferenciaResponse getByReferencia(
            int currentPage,
            int pageSize,
            String tipoReferencia,
            String referenciaId
    ) throws EBusinessException {
        try {
            int safeCurrentPage = currentPage <= 0 ? 0 : currentPage - 1;
            int safePageSize = pageSize <= 0 ? 10 : pageSize;

            Pageable pageable = PageRequest.of(safeCurrentPage, safePageSize);

            Page<EntyEvirefmdreferencia> responsePage = repository.searchByReferencia(
                    tipoReferencia == null ? "" : tipoReferencia.trim(),
                    referenciaId == null ? "" : referenciaId.trim(),
                    pageable
            );

            List<EntyEvirefmdreferenciaDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyEvirefmdreferenciaResponse response = new EntyEvirefmdreferenciaResponse();

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
    public EntyEvirefmdreferenciaDto get(Integer id) throws EBusinessException {
        try {
            EntyEvirefmdreferencia entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyEvirefmdreferenciaDto();
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
    public EntyEvirefmdreferenciaDto save(
            EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException {
        try {
            EntyEvirefmdreferencia entity = dtoToEntityTranslate.translate(dto);
            EntyEvirefmdreferencia saved = repository.save(entity);

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
    public List<EntyEvirefmdreferenciaDto> save(
            List<EntyEvirefmdreferenciaDto> dtos
    ) throws EBusinessException {
        try {
            List<EntyEvirefmdreferencia> entities = new ArrayList<>();

            for (EntyEvirefmdreferenciaDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }

            List<EntyEvirefmdreferenciaDto> result = new ArrayList<>();

            for (EntyEvirefmdreferencia entity : repository.saveAll(entities)) {
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
    public EntyEvirefmdreferenciaDto update(
            Integer id,
            EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException {
        try {
            EntyEvirefmdreferencia old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyEvirefmdreferenciaDto();
            }

            old.setEviIdentifkeyEvre(dto.getEviIdentifkeyEvre());
            old.setEviIdentifkeyEvid(dto.getEviIdentifkeyEvid());
            old.setEviTiporeferenEvre(dto.getEviTiporeferenEvre());
            old.setEviReferenciaidEvre(dto.getEviReferenciaidEvre());
            old.setEviObservacionEvre(dto.getEviObservacionEvre());
            old.setEviEstadoregEvre(dto.getEviEstadoregEvre());

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
            EntyEvirefmdreferencia entity = repository.findById(id).orElse(null);

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

    private EntyEvirefmdreferenciaDto mapToDto(
            EntyEvirefmdreferencia entity
    ) {
        EntyEvirefmdreferenciaDto dto = new EntyEvirefmdreferenciaDto();

        try {
            if (entity == null) {
                return dto;
            }

            return entityToDtoTranslate.translate(entity);

        } catch (Exception e) {
            logger.error(
                    "Error mapeando referencia de evidencia a DTO. ID: {}",
                    entity != null ? entity.getEviPrimarykeyEvre() : null,
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