package com.system.modules.evidencia.dataproviders.jpa;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import com.system.modules.evidencia.dataproviders.IjpaEvidenciaDataProviders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyEvievimaevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvievimaevidenciaResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEvievimaevidencia;
import com.system.crosscutting.persistence.repository.EntyEvievimaevidenciaRepository;

@DataProvider
public class JpaEvidenciaDataProviders implements IjpaEvidenciaDataProviders {

    @Autowired
    private EntyEvievimaevidenciaRepository repository;

    @Autowired
    @Qualifier("entyEvievimaevidenciaEntityToDtoTranslate")
    private Translator<EntyEvievimaevidencia, EntyEvievimaevidenciaDto> entityToDtoTranslate;

    @Autowired
    @Qualifier("entyEvievimaevidenciaDtoToEntityTranslate")
    private Translator<EntyEvievimaevidenciaDto, EntyEvievimaevidencia> dtoToEntityTranslate;

    private static final Logger logger = LogManager.getLogger(JpaEvidenciaDataProviders.class);

    @Override
    public EntyEvievimaevidenciaResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyEvievimaevidenciaResponse getAll(
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
            Page<EntyEvievimaevidencia> responsePage;

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
                    case "EVIDENCIA":
                        responsePage = repository.searchByIdentifKey(safeFilter, pageable);
                        break;

                    case "TIPO":
                    case "TIPO_EVIDENCIA":
                    case "TIEV":
                        responsePage = repository.searchByTipoEvidencia(safeFilter, pageable);
                        break;

                    case "USUARIO":
                    case "USER":
                        responsePage = repository.searchByUsuario(safeFilter, pageable);
                        break;

                    case "STATUS":
                    case "ESTADO":
                        if (safeFilter.isEmpty() || "ALL".equalsIgnoreCase(safeFilter)) {
                            responsePage = repository.findAll(pageable);
                        } else {
                            responsePage = repository.searchByStatus(safeFilter, pageable);
                        }
                        break;

                    case "HOY":
                        LocalDateTime inicioDia = LocalDateTime.now().toLocalDate().atStartOfDay();
                        LocalDateTime finDia = inicioDia.plusDays(1).minusSeconds(1);
                        responsePage = repository.searchByFechaCapturaBetween(inicioDia, finDia, pageable);
                        break;

                    case "TEXT":
                    case "SEARCH":
                    default:
                        responsePage = repository.searchByText(safeFilter, pageable);
                        break;
                }
            }

            List<EntyEvievimaevidenciaDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyEvievimaevidenciaResponse response = new EntyEvievimaevidenciaResponse();

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
    public EntyEvievimaevidenciaDto get(Integer id) throws EBusinessException {
        try {
            EntyEvievimaevidencia entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyEvievimaevidenciaDto();
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
    public EntyEvievimaevidenciaDto save(
            EntyEvievimaevidenciaDto dto
    ) throws EBusinessException {
        try {
            EntyEvievimaevidencia entity = dtoToEntityTranslate.translate(dto);
            EntyEvievimaevidencia saved = repository.save(entity);

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
    public List<EntyEvievimaevidenciaDto> save(
            List<EntyEvievimaevidenciaDto> dtos
    ) throws EBusinessException {
        try {
            List<EntyEvievimaevidencia> entities = new ArrayList<>();

            for (EntyEvievimaevidenciaDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }

            List<EntyEvievimaevidenciaDto> result = new ArrayList<>();

            for (EntyEvievimaevidencia entity : repository.saveAll(entities)) {
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
    public EntyEvievimaevidenciaDto update(
            Integer id,
            EntyEvievimaevidenciaDto dto
    ) throws EBusinessException {
        try {
            EntyEvievimaevidencia old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyEvievimaevidenciaDto();
            }

            old.setEviIdentifkeyEvid(dto.getEviIdentifkeyEvid());
            old.setEviIdentifkeyTiev(dto.getEviIdentifkeyTiev());
            old.setEviNombreEvid(dto.getEviNombreEvid());
            old.setEviDescripcionEvid(dto.getEviDescripcionEvid());
            old.setEviUrlarchivoEvid(dto.getEviUrlarchivoEvid());
            old.setEviLatitudEvid(dto.getEviLatitudEvid());
            old.setEviLongitudEvid(dto.getEviLongitudEvid());
            old.setEviFechacapturaEvid(dto.getEviFechacapturaEvid());
            old.setEviUsuariocreaEvid(dto.getEviUsuariocreaEvid());
            old.setEviEstadoregEvid(dto.getEviEstadoregEvid());

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
            EntyEvievimaevidencia entity = repository.findById(id).orElse(null);

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

    private EntyEvievimaevidenciaDto mapToDto(
            EntyEvievimaevidencia entity
    ) {
        EntyEvievimaevidenciaDto dto = new EntyEvievimaevidenciaDto();

        try {
            if (entity == null) {
                return dto;
            }

            return entityToDtoTranslate.translate(entity);

        } catch (Exception e) {
            logger.error(
                    "Error mapeando evidencia a DTO. ID: {}",
                    entity != null ? entity.getEviPrimarykeyEvid() : null,
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
