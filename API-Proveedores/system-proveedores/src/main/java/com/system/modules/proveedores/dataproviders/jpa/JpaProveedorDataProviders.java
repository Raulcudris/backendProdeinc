package com.system.modules.proveedores.dataproviders.jpa;
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
import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaDto;
import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyPrvmaeproveedoresma;
import com.system.crosscutting.persistence.repository.EntyPrvmaeproveedoresmaRepository;
import com.system.modules.proveedores.dataproviders.IjpaProveedorDataProviders;

@DataProvider
public class JpaProveedorDataProviders implements IjpaProveedorDataProviders {

    @Autowired
    private EntyPrvmaeproveedoresmaRepository repository;

    @Autowired
    @Qualifier("entyPrvmaeproveedoresmaEntityToDtoTranslate")
    private Translator<EntyPrvmaeproveedoresma, EntyPrvmaeproveedoresmaDto> entityToDtoTranslate;

    @Autowired
    @Qualifier("entyPrvmaeproveedoresmaDtoToEntityTranslate")
    private Translator<EntyPrvmaeproveedoresmaDto, EntyPrvmaeproveedoresma> dtoToEntityTranslate;

    private static final Logger logger = LogManager.getLogger(JpaProveedorDataProviders.class);

    @Override
    public EntyPrvmaeproveedoresmaResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyPrvmaeproveedoresmaResponse getAll(
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
            Page<EntyPrvmaeproveedoresma> responsePage;

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
                    case "PROVEEDOR":
                        responsePage = repository.searchByIdentifKey(safeFilter, pageable);
                        break;

                    case "NIT":
                    case "DOCUMENTO":
                        responsePage = repository.searchByNit(safeFilter, pageable);
                        break;

                    case "TIPO":
                    case "TIPO_PROVEEDOR":
                        responsePage = repository.searchByTipoProveedor(safeFilter, pageable);
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

            List<EntyPrvmaeproveedoresmaDto> content = responsePage.getContent()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyPrvmaeproveedoresmaResponse response = new EntyPrvmaeproveedoresmaResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-proveedores");
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
    public EntyPrvmaeproveedoresmaDto get(Integer id) throws EBusinessException {
        try {
            EntyPrvmaeproveedoresma entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyPrvmaeproveedoresmaDto();
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
    public EntyPrvmaeproveedoresmaDto save(
            EntyPrvmaeproveedoresmaDto dto
    ) throws EBusinessException {
        try {
            EntyPrvmaeproveedoresma entity = dtoToEntityTranslate.translate(dto);
            EntyPrvmaeproveedoresma saved = repository.save(entity);

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
    public List<EntyPrvmaeproveedoresmaDto> save(
            List<EntyPrvmaeproveedoresmaDto> dtos
    ) throws EBusinessException {
        try {
            List<EntyPrvmaeproveedoresma> entities = new ArrayList<>();

            for (EntyPrvmaeproveedoresmaDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }

            List<EntyPrvmaeproveedoresmaDto> result = new ArrayList<>();

            for (EntyPrvmaeproveedoresma entity : repository.saveAll(entities)) {
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
    public EntyPrvmaeproveedoresmaDto update(
            Integer id,
            EntyPrvmaeproveedoresmaDto dto
    ) throws EBusinessException {
        try {
            EntyPrvmaeproveedoresma old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyPrvmaeproveedoresmaDto();
            }

            old.setPrvIdentifkeyMprv(dto.getPrvIdentifkeyMprv());
            old.setPrvNumeronitMprv(dto.getPrvNumeronitMprv());
            old.setPrvRazonsocialMprv(dto.getPrvRazonsocialMprv());
            old.setPrvNombrecomercialMprv(dto.getPrvNombrecomercialMprv());
            old.setPrvTipoproveedorMprv(dto.getPrvTipoproveedorMprv());
            old.setPrvContactoMprv(dto.getPrvContactoMprv());
            old.setPrvTelefonoMprv(dto.getPrvTelefonoMprv());
            old.setPrvCorreoMprv(dto.getPrvCorreoMprv());
            old.setPrvDireccionMprv(dto.getPrvDireccionMprv());
            old.setPrvCiudadMprv(dto.getPrvCiudadMprv());
            old.setPrvDepartamentoMprv(dto.getPrvDepartamentoMprv());
            old.setPrvFecharegistroMprv(dto.getPrvFecharegistroMprv());
            old.setPrvObservacionMprv(dto.getPrvObservacionMprv());
            old.setPrvEstadoregMprv(dto.getPrvEstadoregMprv());

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
            EntyPrvmaeproveedoresma entity = repository.findById(id).orElse(null);

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

    private EntyPrvmaeproveedoresmaDto mapToDto(
            EntyPrvmaeproveedoresma entity
    ) {
        EntyPrvmaeproveedoresmaDto dto = new EntyPrvmaeproveedoresmaDto();

        try {
            if (entity == null) {
                return dto;
            }

            return entityToDtoTranslate.translate(entity);

        } catch (Exception e) {
            logger.error(
                    "Error mapeando proveedor a DTO. ID: {}",
                    entity != null ? entity.getPrvPrimarykeyMprv() : null,
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