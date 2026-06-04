package com.system.modules.equiposmaquinaria.dataproviders.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import com.system.modules.equiposmaquinaria.dataproviders.IjpaEquipoDataProviders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyEquinvmaequiposDto;
import com.system.crosscutting.domain.model.EntyEquinvmaequiposResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEquinvmaequipos;
import com.system.crosscutting.persistence.repository.EntyEquinvmaequiposRepository;

@DataProvider
public class JpaEquipoDataProviders implements IjpaEquipoDataProviders {

    @Autowired
    private EntyEquinvmaequiposRepository repository;

    @Autowired
    @Qualifier("entyEquinvmaequiposEntityToDtoTranslate")
    private Translator<EntyEquinvmaequipos, EntyEquinvmaequiposDto> entityToDtoTranslate;

    @Autowired
    @Qualifier("entyEquinvmaequiposDtoToEntityTranslate")
    private Translator<EntyEquinvmaequiposDto, EntyEquinvmaequipos> dtoToEntityTranslate;

    private static final Logger logger = LogManager.getLogger(JpaEquipoDataProviders.class);

    @Override
    public EntyEquinvmaequiposResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyEquinvmaequiposResponse getAll(
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
            Page<EntyEquinvmaequipos> responsePage;

            if (safeFilter.isEmpty()
                    && !"STATUS".equals(safeParameter)
                    && !"ESTADO".equals(safeParameter)
                    && !"ESTADO_OPERATIVO".equals(safeParameter)
                    && !"DISPONIBLES".equals(safeParameter)) {
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
                    case "EQUIPO":
                        responsePage = repository.searchByIdentifKey(safeFilter, pageable);
                        break;

                    case "TIPO":
                    case "TIPO_EQUIPO":
                    case "TIEQ":
                        responsePage = repository.searchByTipoEquipo(safeFilter, pageable);
                        break;

                    case "PROVEEDOR":
                    case "PRV":
                        responsePage = repository.searchByProveedor(safeFilter, pageable);
                        break;

                    case "PLACA":
                        responsePage = repository.searchByPlaca(safeFilter, pageable);
                        break;

                    case "SERIAL":
                        responsePage = repository.searchBySerial(safeFilter, pageable);
                        break;

                    case "ESTADO_OPERATIVO":
                    case "OPERATIVO":
                        responsePage = repository.searchByEstadoOperativo(safeFilter, pageable);
                        break;

                    case "DISPONIBLES":
                    case "DISPONIBLE":
                        responsePage = repository.searchByEstadoOperativo("1", pageable);
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

            List<EntyEquinvmaequiposDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyEquinvmaequiposResponse response = new EntyEquinvmaequiposResponse();
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
    public EntyEquinvmaequiposDto get(Integer id) throws EBusinessException {
        try {
            EntyEquinvmaequipos entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyEquinvmaequiposDto();
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
    public EntyEquinvmaequiposDto save(
            EntyEquinvmaequiposDto dto
    ) throws EBusinessException {
        try {
            EntyEquinvmaequipos entity = dtoToEntityTranslate.translate(dto);
            EntyEquinvmaequipos saved = repository.save(entity);

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
    public List<EntyEquinvmaequiposDto> save(
            List<EntyEquinvmaequiposDto> dtos
    ) throws EBusinessException {
        try {
            List<EntyEquinvmaequipos> entities = new ArrayList<>();

            for (EntyEquinvmaequiposDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }

            List<EntyEquinvmaequiposDto> result = new ArrayList<>();

            for (EntyEquinvmaequipos entity : repository.saveAll(entities)) {
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
    public EntyEquinvmaequiposDto update(
            Integer id,
            EntyEquinvmaequiposDto dto
    ) throws EBusinessException {
        try {
            EntyEquinvmaequipos old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyEquinvmaequiposDto();
            }

            old.setEquIdentifkeyEqui(dto.getEquIdentifkeyEqui());
            old.setEquIdentifkeyTieq(dto.getEquIdentifkeyTieq());
            old.setPrvIdentifkeyMprv(dto.getPrvIdentifkeyMprv());
            old.setEquCodinternoEqui(dto.getEquCodinternoEqui());
            old.setEquNombreEqui(dto.getEquNombreEqui());
            old.setEquMarcaEqui(dto.getEquMarcaEqui());
            old.setEquModeloEqui(dto.getEquModeloEqui());
            old.setEquPlacaEqui(dto.getEquPlacaEqui());
            old.setEquSerialEqui(dto.getEquSerialEqui());
            old.setEquEstadooperEqui(dto.getEquEstadooperEqui());
            old.setEquEstadoregEqui(dto.getEquEstadoregEqui());

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
            EntyEquinvmaequipos entity = repository.findById(id).orElse(null);

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

    private EntyEquinvmaequiposDto mapToDto(
            EntyEquinvmaequipos entity
    ) {
        EntyEquinvmaequiposDto dto = new EntyEquinvmaequiposDto();

        try {
            if (entity == null) {
                return dto;
            }

            return entityToDtoTranslate.translate(entity);

        } catch (Exception e) {
            logger.error(
                    "Error mapeando equipo a DTO. ID: {}",
                    entity != null ? entity.getEquPrimarykeyEqui() : null,
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