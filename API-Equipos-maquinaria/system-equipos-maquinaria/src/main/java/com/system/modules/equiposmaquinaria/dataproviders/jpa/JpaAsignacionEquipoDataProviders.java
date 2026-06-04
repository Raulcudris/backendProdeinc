package com.system.modules.equiposmaquinaria.dataproviders.jpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import com.system.modules.equiposmaquinaria.dataproviders.IjpaAsignacionEquipoDataProviders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyEquasimdasignequipoDto;
import com.system.crosscutting.domain.model.EntyEquasimdasignequipoResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEquasimdasignequipo;
import com.system.crosscutting.persistence.repository.EntyEquasimdasignequipoRepository;

@DataProvider
public class JpaAsignacionEquipoDataProviders implements IjpaAsignacionEquipoDataProviders {

    @Autowired
    private EntyEquasimdasignequipoRepository repository;

    @Autowired
    @Qualifier("entyEquasimdasignequipoEntityToDtoTranslate")
    private Translator<EntyEquasimdasignequipo, EntyEquasimdasignequipoDto> entityToDtoTranslate;

    @Autowired
    @Qualifier("entyEquasimdasignequipoDtoToEntityTranslate")
    private Translator<EntyEquasimdasignequipoDto, EntyEquasimdasignequipo> dtoToEntityTranslate;

    private static final Logger logger = LogManager.getLogger(JpaAsignacionEquipoDataProviders.class);

    @Override
    public EntyEquasimdasignequipoResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyEquasimdasignequipoResponse getAll(
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
            Page<EntyEquasimdasignequipo> responsePage;

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
                    case "ASIGNACION":
                        responsePage = repository.searchByIdentifKey(safeFilter, pageable);
                        break;

                    case "EQUIPO":
                    case "EQUI":
                        responsePage = repository.searchByEquipo(safeFilter, pageable);
                        break;

                    case "ORDEN":
                    case "ORDEN_SERVICIO":
                    case "ORS":
                        responsePage = repository.searchByOrdenServicio(safeFilter, pageable);
                        break;

                    case "PLAN":
                    case "PLAN_TRABAJO":
                    case "PLTR":
                        responsePage = repository.searchByPlanTrabajo(safeFilter, pageable);
                        break;

                    case "RESPONSABLE":
                    case "OPERADOR":
                        responsePage = repository.searchByResponsable(safeFilter, pageable);
                        break;

                    case "STATUS":
                    case "ESTADO":
                        if (safeFilter.isEmpty() || "ALL".equalsIgnoreCase(safeFilter)) {
                            responsePage = repository.findAll(pageable);
                        } else {
                            responsePage = repository.searchByStatus(safeFilter, pageable);
                        }
                        break;

                    case "FECHA":
                    case "FECHA_ASIGNACION":
                        responsePage = repository.searchByFechaAsignacionBetween(
                                LocalDate.now(),
                                LocalDate.now(),
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

            List<EntyEquasimdasignequipoDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyEquasimdasignequipoResponse response = new EntyEquasimdasignequipoResponse();
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

    public EntyEquasimdasignequipoResponse getByEquipo(
            int currentPage,
            int pageSize,
            String equipoKey
    ) throws EBusinessException {
        return getAll(currentPage, pageSize, "EQUIPO", equipoKey);
    }

    public EntyEquasimdasignequipoResponse getByOrden(
            int currentPage,
            int pageSize,
            String ordenKey
    ) throws EBusinessException {
        return getAll(currentPage, pageSize, "ORDEN", ordenKey);
    }

    public EntyEquasimdasignequipoResponse getByResponsable(
            int currentPage,
            int pageSize,
            String responsable
    ) throws EBusinessException {
        return getAll(currentPage, pageSize, "RESPONSABLE", responsable);
    }

    @Override
    public EntyEquasimdasignequipoDto get(Integer id) throws EBusinessException {
        try {
            EntyEquasimdasignequipo entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyEquasimdasignequipoDto();
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
    public EntyEquasimdasignequipoDto save(
            EntyEquasimdasignequipoDto dto
    ) throws EBusinessException {
        try {
            EntyEquasimdasignequipo entity = dtoToEntityTranslate.translate(dto);
            EntyEquasimdasignequipo saved = repository.save(entity);

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
    public List<EntyEquasimdasignequipoDto> save(
            List<EntyEquasimdasignequipoDto> dtos
    ) throws EBusinessException {
        try {
            List<EntyEquasimdasignequipo> entities = new ArrayList<>();

            for (EntyEquasimdasignequipoDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }

            List<EntyEquasimdasignequipoDto> result = new ArrayList<>();

            for (EntyEquasimdasignequipo entity : repository.saveAll(entities)) {
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
    public EntyEquasimdasignequipoDto update(
            Integer id,
            EntyEquasimdasignequipoDto dto
    ) throws EBusinessException {
        try {
            EntyEquasimdasignequipo old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyEquasimdasignequipoDto();
            }

            old.setEquIdentifkeyAseq(dto.getEquIdentifkeyAseq());
            old.setEquIdentifkeyEqui(dto.getEquIdentifkeyEqui());
            old.setOrsIdentifkeyOrde(dto.getOrsIdentifkeyOrde());
            old.setOrsIdentifkeyPltr(dto.getOrsIdentifkeyPltr());
            old.setEquResponsableAseq(dto.getEquResponsableAseq());
            old.setEquFechaasigAseq(dto.getEquFechaasigAseq());
            old.setEquFechadevolAseq(dto.getEquFechadevolAseq());
            old.setEquObservacionAseq(dto.getEquObservacionAseq());
            old.setEquEstadoregAseq(dto.getEquEstadoregAseq());

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
            EntyEquasimdasignequipo entity = repository.findById(id).orElse(null);

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

    private EntyEquasimdasignequipoDto mapToDto(
            EntyEquasimdasignequipo entity
    ) {
        EntyEquasimdasignequipoDto dto = new EntyEquasimdasignequipoDto();

        try {
            if (entity == null) {
                return dto;
            }

            return entityToDtoTranslate.translate(entity);

        } catch (Exception e) {
            logger.error(
                    "Error mapeando asignación de equipo a DTO. ID: {}",
                    entity != null ? entity.getEquPrimarykeyAseq() : null,
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