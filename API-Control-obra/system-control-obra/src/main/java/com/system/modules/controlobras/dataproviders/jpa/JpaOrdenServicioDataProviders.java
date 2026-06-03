package com.system.modules.controlobras.dataproviders.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import com.system.modules.controlobras.dataproviders.IjpaOrdenServicioDataProviders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioDto;
import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsordmaordenservicio;
import com.system.crosscutting.persistence.repository.EntyOrsordmaordenservicioRepository;

@DataProvider
public class JpaOrdenServicioDataProviders implements IjpaOrdenServicioDataProviders {

    @Autowired
    private EntyOrsordmaordenservicioRepository repository;

    @Autowired
    @Qualifier("entyOrsordmaordenservicioEntityToDtoTranslate")
    private Translator<EntyOrsordmaordenservicio, EntyOrsordmaordenservicioDto> entityToDtoTranslate;

    @Autowired
    @Qualifier("entyOrsordmaordenservicioDtoToEntityTranslate")
    private Translator<EntyOrsordmaordenservicioDto, EntyOrsordmaordenservicio> dtoToEntityTranslate;

    private static final Logger logger = LogManager.getLogger(JpaOrdenServicioDataProviders.class);

    @Override
    public EntyOrsordmaordenservicioResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsordmaordenservicioResponse getAll(
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
            Page<EntyOrsordmaordenservicio> responsePage;

            if (safeFilter.isEmpty() && !"STATUS".equals(safeParameter)) {
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
                    case "ORDEN":
                    case "ORDER":
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

            List<EntyOrsordmaordenservicioDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyOrsordmaordenservicioResponse response = new EntyOrsordmaordenservicioResponse();

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
    public EntyOrsordmaordenservicioDto get(Integer id) throws EBusinessException {
        try {
            EntyOrsordmaordenservicio entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyOrsordmaordenservicioDto();
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
    public EntyOrsordmaordenservicioDto save(
            EntyOrsordmaordenservicioDto dto
    ) throws EBusinessException {
        try {
            EntyOrsordmaordenservicio entity = dtoToEntityTranslate.translate(dto);
            EntyOrsordmaordenservicio saved = repository.save(entity);

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
    public List<EntyOrsordmaordenservicioDto> save(
            List<EntyOrsordmaordenservicioDto> dtos
    ) throws EBusinessException {
        try {
            List<EntyOrsordmaordenservicio> entities = new ArrayList<>();

            for (EntyOrsordmaordenservicioDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }

            List<EntyOrsordmaordenservicioDto> result = new ArrayList<>();

            for (EntyOrsordmaordenservicio entity : repository.saveAll(entities)) {
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
    public EntyOrsordmaordenservicioDto update(
            Integer id,
            EntyOrsordmaordenservicioDto dto
    ) throws EBusinessException {
        try {
            EntyOrsordmaordenservicio old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyOrsordmaordenservicioDto();
            }

            old.setOrsIdentifkeyOrde(dto.getOrsIdentifkeyOrde());
            old.setOrsAutorifechaOrde(dto.getOrsAutorifechaOrde());
            old.setOrsCodservicioSebs(dto.getOrsCodservicioSebs());
            old.setOrsServiceventOrde(dto.getOrsServiceventOrde());
            old.setOrsServiclugarOrde(dto.getOrsServiclugarOrde());
            old.setOrsServicobjetoOrde(dto.getOrsServicobjetoOrde());
            old.setOrsPlanfechiniOrde(dto.getOrsPlanfechiniOrde());
            old.setOrsPlanfechfinOrde(dto.getOrsPlanfechfinOrde());
            old.setPrvIdentifkeyMprv(dto.getPrvIdentifkeyMprv());
            old.setPrvIdentifkeyRelg(dto.getPrvIdentifkeyRelg());
            old.setOrdTipovalorTiva(dto.getOrdTipovalorTiva());
            old.setOrsValorbaseOrde(dto.getOrsValorbaseOrde());
            old.setCarValaboCamg(dto.getCarValaboCamg());
            old.setCarValsalCamg(dto.getCarValsalCamg());
            old.setOrsEstadoregOrde(dto.getOrsEstadoregOrde());

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
            EntyOrsordmaordenservicio entity = repository.findById(id).orElse(null);

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

    private EntyOrsordmaordenservicioDto mapToDto(
            EntyOrsordmaordenservicio entity
    ) {
        EntyOrsordmaordenservicioDto dto = new EntyOrsordmaordenservicioDto();

        try {
            if (entity == null) {
                return dto;
            }

            return entityToDtoTranslate.translate(entity);

        } catch (Exception e) {
            logger.error(
                    "Error mapeando orden de servicio a DTO. ID: {}",
                    entity != null ? entity.getOrsPrimarykeyOrde() : null,
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