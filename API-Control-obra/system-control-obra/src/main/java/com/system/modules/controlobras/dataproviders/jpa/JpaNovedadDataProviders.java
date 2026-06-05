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

import com.system.crosscutting.domain.model.EntyOrsnovmdnovedadDto;
import com.system.crosscutting.domain.model.EntyOrsnovmdnovedadResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsnovmdnovedad;
import com.system.crosscutting.persistence.repository.EntyOrsnovmdnovedadRepository;
import com.system.modules.controlobras.dataproviders.IjpaNovedadDataProviders;

@DataProvider
public class JpaNovedadDataProviders implements IjpaNovedadDataProviders {

    @Autowired
    private EntyOrsnovmdnovedadRepository repository;

    @Autowired
    @Qualifier("entyOrsnovmdnovedadEntityToDtoTranslate")
    private Translator<EntyOrsnovmdnovedad, EntyOrsnovmdnovedadDto> entityToDtoTranslate;

    @Autowired
    @Qualifier("entyOrsnovmdnovedadDtoToEntityTranslate")
    private Translator<EntyOrsnovmdnovedadDto, EntyOrsnovmdnovedad> dtoToEntityTranslate;

    private static final Logger logger = LogManager.getLogger(JpaNovedadDataProviders.class);

    @Override
    public EntyOrsnovmdnovedadResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsnovmdnovedadResponse getAll(
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
            Page<EntyOrsnovmdnovedad> responsePage;

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
                    case "NOVEDAD":
                        responsePage = repository.searchByIdentifKey(safeFilter, pageable);
                        break;

                    case "REPORTE":
                    case "REPORTE_DIARIO":
                    case "REDI":
                        responsePage = repository.searchByReporte(safeFilter, pageable);
                        break;

                    case "TIPO":
                    case "TIPO_NOVEDAD":
                        responsePage = repository.searchByTipoNovedad(safeFilter, pageable);
                        break;

                    case "CRITICIDAD":
                        responsePage = repository.searchByCriticidad(safeFilter, pageable);
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

            List<EntyOrsnovmdnovedadDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyOrsnovmdnovedadResponse response = new EntyOrsnovmdnovedadResponse();
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

    public EntyOrsnovmdnovedadResponse getByReporte(
            int currentPage,
            int pageSize,
            String reporteKey
    ) throws EBusinessException {
        return getAll(currentPage, pageSize, "REPORTE", reporteKey);
    }

    @Override
    public EntyOrsnovmdnovedadDto get(Integer id) throws EBusinessException {
        try {
            EntyOrsnovmdnovedad entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyOrsnovmdnovedadDto();
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
    public EntyOrsnovmdnovedadDto save(
            EntyOrsnovmdnovedadDto dto
    ) throws EBusinessException {
        try {
            EntyOrsnovmdnovedad entity = dtoToEntityTranslate.translate(dto);
            EntyOrsnovmdnovedad saved = repository.save(entity);

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
    public List<EntyOrsnovmdnovedadDto> save(
            List<EntyOrsnovmdnovedadDto> dtos
    ) throws EBusinessException {
        try {
            List<EntyOrsnovmdnovedad> entities = new ArrayList<>();

            for (EntyOrsnovmdnovedadDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }

            List<EntyOrsnovmdnovedadDto> result = new ArrayList<>();

            for (EntyOrsnovmdnovedad entity : repository.saveAll(entities)) {
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
    public EntyOrsnovmdnovedadDto update(
            Integer id,
            EntyOrsnovmdnovedadDto dto
    ) throws EBusinessException {
        try {
            EntyOrsnovmdnovedad old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyOrsnovmdnovedadDto();
            }

            old.setOrsIdentifkeyNove(dto.getOrsIdentifkeyNove());
            old.setOrsIdentifkeyRedi(dto.getOrsIdentifkeyRedi());
            old.setOrsTiponovedadNove(dto.getOrsTiponovedadNove());
            old.setOrsDescripcionNove(dto.getOrsDescripcionNove());
            old.setOrsFechanovedadNove(dto.getOrsFechanovedadNove());
            old.setOrsCriticidadNove(dto.getOrsCriticidadNove());
            old.setOrsResponsableNove(dto.getOrsResponsableNove());
            old.setOrsRequiereaccionNove(dto.getOrsRequiereaccionNove());
            old.setOrsAcciontomadaNove(dto.getOrsAcciontomadaNove());
            old.setOrsEstadoregNove(dto.getOrsEstadoregNove());

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
            EntyOrsnovmdnovedad entity = repository.findById(id).orElse(null);

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

    private EntyOrsnovmdnovedadDto mapToDto(
            EntyOrsnovmdnovedad entity
    ) {
        EntyOrsnovmdnovedadDto dto = new EntyOrsnovmdnovedadDto();

        try {
            if (entity == null) {
                return dto;
            }

            return entityToDtoTranslate.translate(entity);

        } catch (Exception e) {
            logger.error(
                    "Error mapeando novedad a DTO. ID: {}",
                    entity != null ? entity.getOrsPrimarykeyNove() : null,
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