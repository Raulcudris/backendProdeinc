package com.system.modules.users.dataproviders.jpa;

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

import com.system.crosscutting.domain.model.EntyCityDto;
import com.system.crosscutting.domain.model.EntyRechomeestadistDto;
import com.system.crosscutting.domain.model.EntyRecmaesusuarimaDto;
import com.system.crosscutting.domain.model.EntyRecmaesusuarimaResponse;
import com.system.crosscutting.domain.model.EntyResumEstadistDto;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.messages.SearchMessages;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyRechomeestadist;
import com.system.crosscutting.persistence.entity.EntyRecmaesusuarima;
import com.system.crosscutting.persistence.entity.EntySispaisamaestro;
import com.system.crosscutting.persistence.entity.EntySispaisbestados;
import com.system.crosscutting.persistence.entity.EntySispaisciudadma;
import com.system.crosscutting.persistence.entity.EntySispaisciudadmd;
import com.system.crosscutting.persistence.repository.EntyRechomeestadistRepository;
import com.system.crosscutting.persistence.repository.EntyRecmaesusuarimaRepository;
import com.system.crosscutting.persistence.repository.EntySispaisamaestroRepository;
import com.system.crosscutting.persistence.repository.EntySispaisbestadosRepository;
import com.system.crosscutting.persistence.repository.EntySispaisciudadmaRepository;
import com.system.crosscutting.persistence.repository.EntySispaisciudadmdRepository;
import com.system.crosscutting.utils.DateUtility;
import com.system.modules.users.dataproviders.IjpaEntyRecmaesusuarimaDataProviders;

@DataProvider
public class JpaEntyRecmaesusuarimaDataProviders implements IjpaEntyRecmaesusuarimaDataProviders {

    @Autowired
    private EntyRecmaesusuarimaRepository repository;

    @Autowired
    private EntyRechomeestadistRepository repositoryEstadist;

    @Autowired
    @Qualifier("entyRecmaesusuarimaEntityToDtoTranslate")
    private Translator<EntyRecmaesusuarima, EntyRecmaesusuarimaDto> entityToDtoTranslate;

    @Autowired
    @Qualifier("entyRecmaesusuarimaDtoToEntityTranslate")
    private Translator<EntyRecmaesusuarimaDto, EntyRecmaesusuarima> dtoToEntityTranslate;

    @Autowired
    @Qualifier("entyRechomeestadistEntityToDtoTranslate")
    private Translator<EntyRechomeestadist, EntyRechomeestadistDto> entityEstadistToDtoTranslate;

    @Autowired
    @Qualifier("entyRechomeestadistDtoToEntityTranslate")
    private Translator<EntyRechomeestadistDto, EntyRechomeestadist> dtoEstadistToEntityTranslate;

    @Autowired
    private EntySispaisciudadmdRepository repositoryCity;

    @Autowired
    private EntySispaisciudadmaRepository repositoryMainCity;

    @Autowired
    private EntySispaisbestadosRepository repositoryStates;

    @Autowired
    private EntySispaisamaestroRepository repositoryCountry;

    private static final Logger logger = LogManager.getLogger(JpaEntyRecmaesusuarimaDataProviders.class);

    @Override
    public EntyRecmaesusuarimaResponse getAll() throws EBusinessException {
        try {
            List<EntyRecmaesusuarima> responses = (List<EntyRecmaesusuarima>) repository.findAll();

            int currentPage = 0;
            int totalPageSize = responses.size() <= 0 ? 10 : responses.size();

            Pageable pageable = PageRequest.of(currentPage, totalPageSize);
            Page<EntyRecmaesusuarima> responsePage = repository.findAll(pageable);

            List<EntyRecmaesusuarima> listPage = responsePage.getContent();

            List<EntyRecmaesusuarimaDto> content = listPage
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyRecmaesusuarimaResponse response = new EntyRecmaesusuarimaResponse();

            response.setRspMessage("OK");
            response.setRspValue("OK");

            currentPage = currentPage + 1;

            String nextPageUrl = "LocalHost";
            String previousPageUrl = "LocalHost";

            response.setRspPagination(
                    headResponse(
                            currentPage,
                            totalPageSize,
                            responsePage.getTotalElements(),
                            responsePage.getTotalPages(),
                            responsePage.hasNext(),
                            responsePage.hasPrevious(),
                            nextPageUrl,
                            previousPageUrl
                    )
            );

            response.setRspData(content);

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
    public EntyRecmaesusuarimaResponse getAll(
            int currentPage,
            int totalPageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        try {
            int safeCurrentPage = currentPage <= 0 ? 0 : currentPage - 1;
            int safePageSize = totalPageSize <= 0 ? 10 : totalPageSize;

            String safeParameter = parameter == null || parameter.trim().isEmpty()
                    ? "TEXT"
                    : parameter.trim().toUpperCase();

            String safeFilter = filter == null ? "" : filter.trim();

            Pageable pageable = PageRequest.of(safeCurrentPage, safePageSize);
            Page<EntyRecmaesusuarima> responsePage;

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
                        responsePage = repository.searchByRegisterKey(safeFilter, pageable);
                        break;

                    case "STATUS":
                        if (safeFilter.isEmpty() || "ALL".equalsIgnoreCase(safeFilter)) {
                            responsePage = repository.findAll(pageable);
                        } else {
                            responsePage = repository.searchByStatus(safeFilter, pageable);
                        }
                        break;

                    case "EMAIL":
                        responsePage = repository.searchByEmail(safeFilter, pageable);
                        break;

                    case "DOC":
                    case "DOCUMENT":
                        responsePage = repository.searchByDocument(safeFilter, pageable);
                        break;

                    case "PHONE":
                    case "TELEPHONE":
                        responsePage = repository.searchByPhone(safeFilter, pageable);
                        break;

                    case "TEXT":
                    case "SEARCH":
                    default:
                        responsePage = repository.searchByText(safeFilter, pageable);
                        break;
                }
            }

            List<EntyRecmaesusuarima> listPage = responsePage.getContent();

            List<EntyRecmaesusuarimaDto> content = listPage
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            EntyRecmaesusuarimaResponse response = new EntyRecmaesusuarimaResponse();

            response.setRspMessage("OK");
            response.setRspValue("OK");

            int responseCurrentPage = safeCurrentPage + 1;

            String nextPageUrl = "LocalHost";
            String previousPageUrl = "LocalHost";

            response.setRspPagination(
                    headResponse(
                            responseCurrentPage,
                            safePageSize,
                            responsePage.getTotalElements(),
                            responsePage.getTotalPages(),
                            responsePage.hasNext(),
                            responsePage.hasPrevious(),
                            nextPageUrl,
                            previousPageUrl
                    )
            );

            response.setRspData(content);

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
    public EntyRecmaesusuarimaDto get(Integer id) throws EBusinessException {
        try {
            EntyRecmaesusuarima entity = repository.findById(id).orElse(null);

            if (entity == null) {
                return new EntyRecmaesusuarimaDto();
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
    public EntyRecmaesusuarimaDto save(EntyRecmaesusuarimaDto dto) throws EBusinessException {
        try {
            EntyRecmaesusuarima entity = dtoToEntityTranslate.translate(dto);
            EntyRecmaesusuarima savedEntity = repository.save(entity);

            return mapToDto(savedEntity);

        } catch (PersistenceException | DataAccessException e) {
            throw ExceptionBuilder.builder()
                    .withMessage(SearchMessages.CREATE_ERROR_DESCRIPTION)
                    .withCode(SearchMessages.CREATE_ERROR_ID)
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    @Override
    public List<EntyRecmaesusuarimaDto> save(List<EntyRecmaesusuarimaDto> dtos) throws EBusinessException {
        try {
            List<EntyRecmaesusuarima> entities = new ArrayList<>();

            for (EntyRecmaesusuarimaDto dto : dtos) {
                entities.add(dtoToEntityTranslate.translate(dto));
            }

            List<EntyRecmaesusuarimaDto> result = new ArrayList<>();

            for (EntyRecmaesusuarima entity : repository.saveAll(entities)) {
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
    public EntyRecmaesusuarimaDto update(Integer id, EntyRecmaesusuarimaDto dto) throws EBusinessException {
        try {
            EntyRecmaesusuarima old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyRecmaesusuarimaDto();
            }

            old.setRecNiknamReus(dto.getRecNiknamReus());
            old.setRecNroideReus(dto.getRecNroideReus());
            old.setRecNombreReus(dto.getRecNombreReus());
            old.setRecApelidReus(dto.getRecApelidReus());
            old.setRecFecnacReus(dto.getRecFecnacReus());
            old.setRecSexusuReus(dto.getRecSexusuReus());
            old.setRecNomusuReus(dto.getRecNomusuReus());
            old.setRecImgvisReus(dto.getRecImgvisReus());
            old.setRecDirresReus(dto.getRecDirresReus());
            old.setRecTelefoReus(dto.getRecTelefoReus());
            old.setApjCorreoApgm(dto.getApjCorreoApgm());
            old.setSisCodpaiSipa(dto.getSisCodpaiSipa());
            old.setSisIdedptSidp(dto.getSisIdedptSidp());
            old.setSisCodproSipr(dto.getSisCodproSipr());
            old.setRecCodposReus(dto.getRecCodposReus());
            old.setRecGeolatReus(dto.getRecGeolatReus());
            old.setRecGeolonReus(dto.getRecGeolonReus());
            old.setRecEstregReus(dto.getRecEstregReus());

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
    public EntyRecmaesusuarimaDto updateImage(Integer id, EntyRecmaesusuarimaDto dto) throws EBusinessException {
        try {
            EntyRecmaesusuarima old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyRecmaesusuarimaDto();
            }

            old.setRecImgvisReus(dto.getRecImgvisReus());

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
            EntyRecmaesusuarima entity = repository.findById(id).orElse(null);

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

    private EntyRecmaesusuarimaDto mapToDto(EntyRecmaesusuarima regEnty) {
        EntyRecmaesusuarimaDto dto = new EntyRecmaesusuarimaDto();

        try {
            if (regEnty == null) {
                return dto;
            }

            dto = entityToDtoTranslate.translate(regEnty);

            dto.setResumEstadist(mapResumEstadistDto(regEnty.getRecNroregReus()));
            dto.setCity(queryEntyCityDto(regEnty));

        } catch (Exception e) {
            logger.error(
                    "Error mapeando usuario a DTO. ID usuario: {}",
                    regEnty != null ? regEnty.getRecIdeunikeyReus() : null,
                    e
            );
        }

        return dto;
    }

    /**
     * Estadística de contratos y comentarios.
     */
    private EntyResumEstadistDto mapResumEstadistDto(String idRegistro) {
        EntyResumEstadistDto resum = new EntyResumEstadistDto();

        try {
            if (idRegistro == null || idRegistro.trim().isEmpty()) {
                return resum;
            }

            logger.info("Iniciando mapeo de estadísticas para ID: {}", idRegistro);

            List<EntyRechomeestadist> tmpEstadist = repositoryEstadist.findByRecIdenumkeyRhes(idRegistro);

            if (tmpEstadist == null || tmpEstadist.isEmpty()) {
                logger.info("No se encontraron estadísticas para ID: {}", idRegistro);
                return resum;
            }

            logger.info("Registros estadísticos encontrados: {}", tmpEstadist.size());

            for (EntyRechomeestadist dto : tmpEstadist) {
                if (dto == null) {
                    continue;
                }

                String key = dto.getRecKeylocateRhes();
                Float count = dto.getRecRegcountRhes();
                String comment = dto.getRecNotdescripRhes();

                if (key == null || key.trim().isEmpty()) {
                    continue;
                }

                switch (key) {
                    case "CONTRACTS-COMPLETED-TOTAL":
                        resum.setRecContractTotal(safeInt(count));
                        break;

                    case "CONTRACTS-COMPLIANT":
                        resum.setRecContractOkey(safeInt(count));
                        break;

                    case "CONTRACTS-NON-CONFORMING":
                        resum.setRecContractDown(safeInt(count));
                        break;

                    case "COMMENT-ISSUED":
                        resum.setRecConcept(comment);

                        if (dto.getRecDateregistRhes() != null && DateUtility.getDateTime() != null) {
                            String resumenTiempo = DateUtility.getPeriodDateComments(
                                    dto.getRecDateregistRhes(),
                                    DateUtility.getDateTime().dateTime
                            );

                            resum.setRecDateConcept(resumenTiempo);
                        }

                        break;

                    case "WEIGHTED-SCORE":
                        resum.setRecQualification(count != null ? count : 0F);
                        break;

                    case "COMMENTS-RECEIVED-TOTAL":
                        resum.setRecCommentsTotal(safeInt(count));
                        break;

                    case "FAVORITES-RECEIVED-TOTAL":
                        resum.setRecFavorites(safeInt(count));
                        break;

                    default:
                        logger.warn("Clave estadística desconocida ignorada: {}", key);
                        break;
                }
            }

        } catch (Exception e) {
            logger.error("Error al mapear resumen de estadísticas para ID: {}", idRegistro, e);
        }

        return resum;
    }

    private Integer safeInt(Float value) {
        return value != null ? value.intValue() : 0;
    }

    /**
     * Ciudad, departamento y país del usuario.
     */
    private EntyCityDto queryEntyCityDto(EntyRecmaesusuarima regEnty) {
        EntyCityDto dto = new EntyCityDto();

        try {
            if (regEnty == null) {
                return dto;
            }

            EntyCityDto tmpCity = mapCityDto(regEnty.getSisCodproSipr());
            EntyCityDto tmpCountry = mapCountryDto(regEnty.getSisCodpaiSipa());
            EntyCityDto tmpState = mapStateDto(regEnty.getSisIdedptSidp());

            dto = EntyCityDto.builder()
                    .sisCodproSipr(tmpCity.getSisCodproSipr())
                    .sisNombreSipr(tmpCity.getSisNombreSipr())
                    .sisProclaSipr(tmpCity.getSisProclaSipr())
                    .sisCodmunSimu(tmpCity.getSisCodmunSimu())
                    .sisNombreSimu(tmpCity.getSisNombreSimu())
                    .sisIdedptSidp(tmpState.getSisIdedptSidp())
                    .sisCoddptSidp(tmpState.getSisCoddptSidp())
                    .sisNombreSidp(fcNomeState(tmpCity, tmpState))
                    .recUnikeySipa(tmpCountry.getRecUnikeySipa())
                    .sisCodpaiSipa(tmpCountry.getSisCodpaiSipa())
                    .sisNombreSipa(tmpCountry.getSisNombreSipa())
                    .build();

        } catch (Exception e) {
            logger.error(
                    "Error consultando ciudad del usuario. ID usuario: {}, país: {}, departamento: {}, provincia: {}",
                    regEnty != null ? regEnty.getRecIdeunikeyReus() : null,
                    regEnty != null ? regEnty.getSisCodpaiSipa() : null,
                    regEnty != null ? regEnty.getSisIdedptSidp() : null,
                    regEnty != null ? regEnty.getSisCodproSipr() : null,
                    e
            );
        }

        return dto;
    }

    /**
     * Nombre del departamento/ciudad principal cuando se necesite.
     */
    private String fcNomeState(EntyCityDto regCity, EntyCityDto state) {
        String stateName = state != null && state.getSisNombreSidp() != null
                ? state.getSisNombreSidp()
                : "N/A";

        if (regCity == null) {
            return stateName;
        }

        String procla = regCity.getSisProclaSipr();
        String cityName = regCity.getSisNombreSimu();

        if ("1".equals(procla)) {
            return stateName;
        }

        if (cityName == null || cityName.trim().isEmpty() || "N/A".equalsIgnoreCase(cityName)) {
            return stateName;
        }

        return cityName + " - " + stateName;
    }

    private EntyCityDto mapCityDto(String id) {
        EntyCityDto dto = new EntyCityDto();

        try {
            if (id == null || id.trim().isEmpty()) {
                logger.warn("No se puede mapear ciudad porque sisCodproSipr viene vacío.");
                return dto;
            }

            EntySispaisciudadmd tmpCity = repositoryCity.findByCodCity(id.trim());

            if (tmpCity == null) {
                logger.warn("No se encontró ciudad/provincia para sisCodproSipr: {}", id);
                return dto;
            }

            EntySispaisciudadma tmpMainCity = null;

            if (tmpCity.getSisCodmunSimu() != null && !tmpCity.getSisCodmunSimu().trim().isEmpty()) {
                tmpMainCity = repositoryMainCity.findByKeyCity(tmpCity.getSisCodmunSimu());
            }

            dto.setSisCodproSipr(tmpCity.getSisCodproSipr());
            dto.setSisNombreSipr(tmpCity.getSisNombreSipr());
            dto.setSisProclaSipr(tmpCity.getSisProclaSipr());

            dto.setSisCodmunSimu(tmpCity.getSisCodmunSimu());

            if (tmpMainCity != null) {
                dto.setSisNombreSimu(tmpMainCity.getSisNombreSimu());
            } else {
                dto.setSisNombreSimu("N/A");

                logger.warn(
                        "No se encontró municipio principal para sisCodmunSimu: {}",
                        tmpCity.getSisCodmunSimu()
                );
            }

            dto.setSisIdedptSidp(tmpCity.getSisIdedptSidp());
            dto.setSisCodpaiSipa(tmpCity.getSisCodpaiSipa());

        } catch (Exception e) {
            logger.error("Error mapeando ciudad/provincia con sisCodproSipr: {}", id, e);
        }

        return dto;
    }

    private EntyCityDto mapStateDto(String id) {
        EntyCityDto dto = new EntyCityDto();

        try {
            if (id == null || id.trim().isEmpty()) {
                logger.warn("No se puede mapear departamento porque sisIdedptSidp viene vacío.");
                return dto;
            }

            EntySispaisbestados tmpState = repositoryStates.findByCodState(id.trim());

            if (tmpState == null) {
                logger.warn("No se encontró departamento para sisIdedptSidp: {}", id);
                return dto;
            }

            dto.setSisIdedptSidp(tmpState.getSisIdedptSidp());
            dto.setSisCoddptSidp(tmpState.getSisCoddptSidp());
            dto.setSisNombreSidp(tmpState.getSisNombreSidp());

        } catch (Exception e) {
            logger.error("Error mapeando departamento con sisIdedptSidp: {}", id, e);
        }

        return dto;
    }

    private EntyCityDto mapCountryDto(String id) {
        EntyCityDto dto = new EntyCityDto();

        try {
            if (id == null || id.trim().isEmpty()) {
                logger.warn("No se puede mapear país porque sisCodpaiSipa viene vacío.");
                return dto;
            }

            EntySispaisamaestro tmpCountry = repositoryCountry.findByCodCountry(id.trim());

            if (tmpCountry == null) {
                logger.warn("No se encontró país para sisCodpaiSipa: {}", id);
                return dto;
            }

            dto.setRecUnikeySipa(tmpCountry.getRecUnikeySipa());
            dto.setSisCodpaiSipa(tmpCountry.getSisCodpaiSipa());
            dto.setSisNombreSipa(tmpCountry.getSisNombreSipa());

        } catch (Exception e) {
            logger.error("Error mapeando país con sisCodpaiSipa: {}", id, e);
        }

        return dto;
    }

    public static PaginationResponse headResponse(
            int currentPage,
            int totalPageSize,
            long totalResults,
            int totalPages,
            boolean hasNextPage,
            boolean hasPreviousPage,
            String nextpageUrl,
            String previousPageUrl
    ) {
        return PaginationResponse.builder()
                .currentPage(currentPage)
                .totalPageSize(totalPageSize)
                .totalResults(totalResults)
                .totalPages(totalPages)
                .hasNextPage(hasNextPage)
                .hasPreviousPage(hasPreviousPage)
                .nextPageUrl(nextpageUrl)
                .previousPageUrl(previousPageUrl)
                .build();
    }
}