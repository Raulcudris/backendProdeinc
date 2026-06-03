package com.system.modules.documentosobra.dataproviders.jpa;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.PersistenceException;
import com.system.modules.documentosobra.dataproviders.IjpaDocumentoObraDataProviders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.system.crosscutting.domain.model.EntyDocdocmadocumentoDto;
import com.system.crosscutting.domain.model.EntyDocdocmadocumentoResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyDocdocmadocumento;
import com.system.crosscutting.persistence.repository.EntyDocdocmadocumentoRepository;

@DataProvider
public class JpaDocumentoObraDataProviders implements IjpaDocumentoObraDataProviders {

    @Autowired
    private EntyDocdocmadocumentoRepository repository;

    @Autowired
    @Qualifier("entyDocdocmadocumentoEntityToDtoTranslate")
    private Translator<EntyDocdocmadocumento, EntyDocdocmadocumentoDto> entityToDtoTranslate;

    @Autowired
    @Qualifier("entyDocdocmadocumentoDtoToEntityTranslate")
    private Translator<EntyDocdocmadocumentoDto, EntyDocdocmadocumento> dtoToEntityTranslate;

    private static final Logger logger = LogManager.getLogger(JpaDocumentoObraDataProviders.class);

    @Override
    public EntyDocdocmadocumentoResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyDocdocmadocumentoResponse getAll(
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
            Page<EntyDocdocmadocumento> responsePage;

            if (safeFilter.isEmpty()
                    && !"STATUS".equals(safeParameter)
                    && !"ESTADO".equals(safeParameter)
                    && !"VENCIDOS".equals(safeParameter)) {
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
                    case "DOCUMENTO":
                        responsePage = repository.searchByIdentifKey(safeFilter, pageable);
                        break;

                    case "TIPO":
                    case "TIPO_DOCUMENTO":
                    case "TIDO":
                        responsePage = repository.searchByTipoDocumento(safeFilter, pageable);
                        break;

                    case "STATUS":
                    case "ESTADO":
                        if (safeFilter.isEmpty() || "ALL".equalsIgnoreCase(safeFilter)) {
                            responsePage = repository.findAll(pageable);
                        } else {
                            responsePage = repository.searchByStatus(safeFilter, pageable);
                        }
                        break;

                    case "VENCIDOS":
                    case "VENCIDO":
                        responsePage = repository.searchByVencidos(LocalDate.now(), pageable);
                        break;

                    case "TEXT":
                    case "SEARCH":
                    default:
                        responsePage = repository.searchByText(safeFilter, pageable);
                        break;
                }
            }

            List<EntyDocdocmadocumentoDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyDocdocmadocumentoResponse response = new EntyDocdocmadocumentoResponse();

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

    public EntyDocdocmadocumentoResponse getByReferencia(
            int currentPage,
            int pageSize,
            String tipoReferencia,
            String referenciaId
    ) throws EBusinessException {
        try {
            int safeCurrentPage = currentPage <= 0 ? 0 : currentPage - 1;
            int safePageSize = pageSize <= 0 ? 10 : pageSize;

            Pageable pageable = PageRequest.of(safeCurrentPage, safePageSize);

            Page<EntyDocdocmadocumento> responsePage = repository.searchByReferencia(
                    tipoReferencia == null ? "" : tipoReferencia.trim(),
                    referenciaId == null ? "" : referenciaId.trim(),
                    pageable
            );

            List<EntyDocdocmadocumentoDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyDocdocmadocumentoResponse response = new EntyDocdocmadocumentoResponse();

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
    public EntyDocdocmadocumentoDto get(Integer id) throws EBusinessException {
        try {
            EntyDocdocmadocumento entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyDocdocmadocumentoDto();
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
    public EntyDocdocmadocumentoDto save(
            EntyDocdocmadocumentoDto dto
    ) throws EBusinessException {
        try {
            EntyDocdocmadocumento entity = dtoToEntityTranslate.translate(dto);
            EntyDocdocmadocumento saved = repository.save(entity);

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
    public List<EntyDocdocmadocumentoDto> save(
            List<EntyDocdocmadocumentoDto> dtos
    ) throws EBusinessException {
        try {
            List<EntyDocdocmadocumento> entities = new ArrayList<>();

            for (EntyDocdocmadocumentoDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }

            List<EntyDocdocmadocumentoDto> result = new ArrayList<>();

            for (EntyDocdocmadocumento entity : repository.saveAll(entities)) {
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
    public EntyDocdocmadocumentoDto update(
            Integer id,
            EntyDocdocmadocumentoDto dto
    ) throws EBusinessException {
        try {
            EntyDocdocmadocumento old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyDocdocmadocumentoDto();
            }

            old.setDocIdentifkeyDocu(dto.getDocIdentifkeyDocu());
            old.setDocIdentifkeyTido(dto.getDocIdentifkeyTido());
            old.setDocNombreDocu(dto.getDocNombreDocu());
            old.setDocDescripcionDocu(dto.getDocDescripcionDocu());
            old.setDocEntidadDocu(dto.getDocEntidadDocu());
            old.setDocFechaexpDocu(dto.getDocFechaexpDocu());
            old.setDocFechavenceDocu(dto.getDocFechavenceDocu());
            old.setDocUrlarchivoDocu(dto.getDocUrlarchivoDocu());
            old.setDocTiporeferenDocu(dto.getDocTiporeferenDocu());
            old.setDocReferenciaidDocu(dto.getDocReferenciaidDocu());
            old.setDocEstadoregDocu(dto.getDocEstadoregDocu());

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
            EntyDocdocmadocumento entity = repository.findById(id).orElse(null);

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

    private EntyDocdocmadocumentoDto mapToDto(
            EntyDocdocmadocumento entity
    ) {
        EntyDocdocmadocumentoDto dto = new EntyDocdocmadocumentoDto();

        try {
            if (entity == null) {
                return dto;
            }

            return entityToDtoTranslate.translate(entity);

        } catch (Exception e) {
            logger.error(
                    "Error mapeando documento de obra a DTO. ID: {}",
                    entity != null ? entity.getDocPrimarykeyDocu() : null,
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