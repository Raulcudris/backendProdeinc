package com.system.modules.documentosobra.dataproviders.jpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import com.system.modules.documentosobra.dataproviders.IjpaVencimientoDocumentoDataProviders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyDocvenmdvencimientoDto;
import com.system.crosscutting.domain.model.EntyDocvenmdvencimientoResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyDocvenmdvencimiento;
import com.system.crosscutting.persistence.repository.EntyDocvenmdvencimientoRepository;

@DataProvider
public class JpaVencimientoDocumentoDataProviders implements IjpaVencimientoDocumentoDataProviders {

    @Autowired
    private EntyDocvenmdvencimientoRepository repository;

    @Autowired
    @Qualifier("entyDocvenmdvencimientoEntityToDtoTranslate")
    private Translator<EntyDocvenmdvencimiento, EntyDocvenmdvencimientoDto> entityToDtoTranslate;

    @Autowired
    @Qualifier("entyDocvenmdvencimientoDtoToEntityTranslate")
    private Translator<EntyDocvenmdvencimientoDto, EntyDocvenmdvencimiento> dtoToEntityTranslate;

    private static final Logger logger = LogManager.getLogger(JpaVencimientoDocumentoDataProviders.class);

    @Override
    public EntyDocvenmdvencimientoResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyDocvenmdvencimientoResponse getAll(
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
            Page<EntyDocvenmdvencimiento> responsePage;

            if (safeFilter.isEmpty()
                    && !"STATUS".equals(safeParameter)
                    && !"ESTADO".equals(safeParameter)
                    && !"VENCIDOS".equals(safeParameter)
                    && !"PROXIMOS".equals(safeParameter)) {
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
                    case "VENCIMIENTO":
                        responsePage = repository.searchByIdentifKey(safeFilter, pageable);
                        break;

                    case "DOCUMENTO":
                    case "DOCU":
                        responsePage = repository.searchByDocumento(safeFilter, pageable);
                        break;

                    case "ESTADO_VENCIMIENTO":
                    case "ESTADOVENC":
                        responsePage = repository.searchByEstadoVencimiento(safeFilter, pageable);
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

                    case "PROXIMOS":
                    case "PROXIMO":
                        int dias = 30;
                        if (!safeFilter.isEmpty()) {
                            try {
                                dias = Integer.parseInt(safeFilter);
                            } catch (NumberFormatException e) {
                                dias = 30;
                            }
                        }

                        responsePage = repository.searchByProximos(
                                LocalDate.now(),
                                LocalDate.now().plusDays(dias),
                                pageable
                        );
                        break;

                    case "TEXT":
                    case "SEARCH":
                    default:
                        responsePage = repository.searchByText(safeFilter, pageable);
                        break;
                }
            }

            List<EntyDocvenmdvencimientoDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyDocvenmdvencimientoResponse response = new EntyDocvenmdvencimientoResponse();

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

    public EntyDocvenmdvencimientoResponse getByDocumento(
            int currentPage,
            int pageSize,
            String documentoKey
    ) throws EBusinessException {
        try {
            int safeCurrentPage = currentPage <= 0 ? 0 : currentPage - 1;
            int safePageSize = pageSize <= 0 ? 10 : pageSize;

            Pageable pageable = PageRequest.of(safeCurrentPage, safePageSize);

            Page<EntyDocvenmdvencimiento> responsePage = repository.searchByDocumento(
                    documentoKey == null ? "" : documentoKey.trim(),
                    pageable
            );

            List<EntyDocvenmdvencimientoDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyDocvenmdvencimientoResponse response = new EntyDocvenmdvencimientoResponse();

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

    public EntyDocvenmdvencimientoResponse getProximos(
            int currentPage,
            int pageSize,
            int dias
    ) throws EBusinessException {
        try {
            int safeCurrentPage = currentPage <= 0 ? 0 : currentPage - 1;
            int safePageSize = pageSize <= 0 ? 10 : pageSize;
            int safeDias = dias <= 0 ? 30 : dias;

            Pageable pageable = PageRequest.of(safeCurrentPage, safePageSize);

            Page<EntyDocvenmdvencimiento> responsePage = repository.searchByProximos(
                    LocalDate.now(),
                    LocalDate.now().plusDays(safeDias),
                    pageable
            );

            List<EntyDocvenmdvencimientoDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyDocvenmdvencimientoResponse response = new EntyDocvenmdvencimientoResponse();

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

    public EntyDocvenmdvencimientoResponse getVencidos(
            int currentPage,
            int pageSize
    ) throws EBusinessException {
        try {
            int safeCurrentPage = currentPage <= 0 ? 0 : currentPage - 1;
            int safePageSize = pageSize <= 0 ? 10 : pageSize;

            Pageable pageable = PageRequest.of(safeCurrentPage, safePageSize);

            Page<EntyDocvenmdvencimiento> responsePage = repository.searchByVencidos(
                    LocalDate.now(),
                    pageable
            );

            List<EntyDocvenmdvencimientoDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyDocvenmdvencimientoResponse response = new EntyDocvenmdvencimientoResponse();

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
    public EntyDocvenmdvencimientoDto get(Integer id) throws EBusinessException {
        try {
            EntyDocvenmdvencimiento entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyDocvenmdvencimientoDto();
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
    public EntyDocvenmdvencimientoDto save(
            EntyDocvenmdvencimientoDto dto
    ) throws EBusinessException {
        try {
            EntyDocvenmdvencimiento entity = dtoToEntityTranslate.translate(dto);
            EntyDocvenmdvencimiento saved = repository.save(entity);

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
    public List<EntyDocvenmdvencimientoDto> save(
            List<EntyDocvenmdvencimientoDto> dtos
    ) throws EBusinessException {
        try {
            List<EntyDocvenmdvencimiento> entities = new ArrayList<>();

            for (EntyDocvenmdvencimientoDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }

            List<EntyDocvenmdvencimientoDto> result = new ArrayList<>();

            for (EntyDocvenmdvencimiento entity : repository.saveAll(entities)) {
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
    public EntyDocvenmdvencimientoDto update(
            Integer id,
            EntyDocvenmdvencimientoDto dto
    ) throws EBusinessException {
        try {
            EntyDocvenmdvencimiento old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyDocvenmdvencimientoDto();
            }

            old.setDocIdentifkeyVedo(dto.getDocIdentifkeyVedo());
            old.setDocIdentifkeyDocu(dto.getDocIdentifkeyDocu());
            old.setDocFechavenceVedo(dto.getDocFechavenceVedo());
            old.setDocDiasalertaVedo(dto.getDocDiasalertaVedo());
            old.setDocEstadovencVedo(dto.getDocEstadovencVedo());
            old.setDocFecharenovaVedo(dto.getDocFecharenovaVedo());
            old.setDocObservacionVedo(dto.getDocObservacionVedo());
            old.setDocEstadoregVedo(dto.getDocEstadoregVedo());

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
            EntyDocvenmdvencimiento entity = repository.findById(id).orElse(null);

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

    private EntyDocvenmdvencimientoDto mapToDto(
            EntyDocvenmdvencimiento entity
    ) {
        EntyDocvenmdvencimientoDto dto = new EntyDocvenmdvencimientoDto();

        try {
            if (entity == null) {
                return dto;
            }

            return entityToDtoTranslate.translate(entity);

        } catch (Exception e) {
            logger.error(
                    "Error mapeando vencimiento documental a DTO. ID: {}",
                    entity != null ? entity.getDocPrimarykeyVedo() : null,
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