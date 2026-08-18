package com.system.modules.workcontrol.dataproviders.jpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriDto;
import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsconfnovedadhistori;
import com.system.crosscutting.persistence.repository.EntyOrsconfnovedadhistoriRepository;
import com.system.modules.workcontrol.dataproviders.IjpaNovedadHistoriDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaNovedadHistoriDataProviders extends JpaDataProviderSupport
        implements IjpaNovedadHistoriDataProviders {

    private final EntyOrsconfnovedadhistoriRepository repository;

    @Override
    public EntyOrsconfnovedadhistoriResponse getAll()
            throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsconfnovedadhistoriResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        try {
            int pageNumber = safeCurrentPage(currentPage);
            int size = safePageSize(pageSize);
            String search = safeFilter(filter);

            Pageable pageable = PageRequest.of(pageNumber, size);
            Page<EntyOrsconfnovedadhistori> page;

            switch (safeParameter(parameter)) {
                case "ID":
                    page = repository.searchByPrimaryKey(
                            parseInteger(search),
                            pageable
                    );
                    break;

                case "KEY":
                    page = repository.searchByIdentifKey(search, pageable);
                    break;

                case "ORDEN":
                    page = repository.searchByOrden(search, pageable);
                    break;

                case "TIPO":
                    page = repository.searchByTipo(search, pageable);
                    break;

                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;

                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyOrsconfnovedadhistoriDto> data = page
                    .getContent()
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            return buildResponse(
                    data,
                    "Novedades consultadas correctamente.",
                    "NA",
                    buildPagination(pageNumber + 1, size, page)
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando novedades.",
                    e
            );
        }
    }

    @Override
    public EntyOrsconfnovedadhistoriDto get(
            final Integer id
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyOrsconfnovedadhistoriDto();
            }

            return repository
                    .findById(id)
                    .map(this::toDto)
                    .orElseGet(EntyOrsconfnovedadhistoriDto::new);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando novedad.",
                    e
            );
        }
    }

    @Override
    public EntyOrsconfnovedadhistoriDto save(
            final EntyOrsconfnovedadhistoriDto dto
    ) throws EBusinessException {
        try {
            EntyOrsconfnovedadhistoriDto normalized =
                    normalizeForCreate(dto);

            EntyOrsconfnovedadhistori entity =
                    toEntity(normalized);

            EntyOrsconfnovedadhistori saved =
                    repository.save(entity);

            return toDto(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error creando novedad.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsconfnovedadhistoriDto> save(
            final List<EntyOrsconfnovedadhistoriDto> dtos
    ) throws EBusinessException {
        try {
            if (dtos == null || dtos.isEmpty()) {
                return new ArrayList<>();
            }

            List<EntyOrsconfnovedadhistori> entities = dtos
                    .stream()
                    .map(this::normalizeForCreate)
                    .map(this::toEntity)
                    .collect(Collectors.toList());

            return repository
                    .saveAll(entities)
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error creando novedades.",
                    e
            );
        }
    }

    @Override
    public EntyOrsconfnovedadhistoriDto update(
            final Integer id,
            final EntyOrsconfnovedadhistoriDto dto
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyOrsconfnovedadhistoriDto();
            }

            EntyOrsconfnovedadhistori current =
                    repository.findById(id).orElse(null);

            if (current == null) {
                return new EntyOrsconfnovedadhistoriDto();
            }

            EntyOrsconfnovedadhistori incoming =
                    toEntity(normalizeForUpdate(dto));

            incoming.setOrsPrimarykeyNove(id);

            if (incoming.getOrsIdentifkeyNove() == null
                    || incoming.getOrsIdentifkeyNove().trim().isEmpty()) {
                incoming.setOrsIdentifkeyNove(
                        current.getOrsIdentifkeyNove()
                );
            }

            BeanUtils.copyProperties(incoming, current);

            EntyOrsconfnovedadhistori saved =
                    repository.save(current);

            return toDto(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error actualizando novedad.",
                    e
            );
        }
    }

    @Override
    public void delete(
            final Integer id
    ) throws EBusinessException {
        try {
            if (id == null || !repository.existsById(id)) {
                return;
            }

            repository.deleteById(id);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error eliminando novedad.",
                    e
            );
        }
    }

    @Override
    public boolean existsByNovedadKey(
            final String novedadKey
    ) throws EBusinessException {
        try {
            if (novedadKey == null || novedadKey.trim().isEmpty()) {
                return false;
            }

            return repository.existsByOrsIdentifkeyNove(
                    novedadKey.trim().toUpperCase()
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error validando código de novedad.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsconfnovedadhistoriDto> findByOrden(
            final String ordenKey
    ) throws EBusinessException {
        try {
            if (ordenKey == null || ordenKey.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByOrsIdentifkeyOrdeOrderByOrsFechreportNoveDesc(
                            ordenKey.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando novedades por orden.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsconfnovedadhistoriDto> findByTipo(
            final String tipoNovedad
    ) throws EBusinessException {
        try {
            if (tipoNovedad == null || tipoNovedad.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByOrsTiponovedadNovtOrderByOrsFechreportNoveDesc(
                            tipoNovedad.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando novedades por tipo.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsconfnovedadhistoriDto> findByRegistroBase(
            final String registroBase
    ) throws EBusinessException {
        try {
            if (registroBase == null || registroBase.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByOrsRegistrbaseNoveOrderByOrsFechreportNoveDesc(
                            registroBase.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando novedades por registro base.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsconfnovedadhistoriDto> findByRegistroNovedad(
            final String registroNovedad
    ) throws EBusinessException {
        try {
            if (registroNovedad == null || registroNovedad.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByOrsRegistrnoveNoveOrderByOrsFechreportNoveDesc(
                            registroNovedad.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando novedades por registro novedad.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsconfnovedadhistoriDto> findByFecha(
            final LocalDate fechaReporte
    ) throws EBusinessException {
        try {
            if (fechaReporte == null) {
                return new ArrayList<>();
            }

            return repository
                    .findByOrsFechreportNoveOrderByOrsPrimarykeyNoveDesc(
                            fechaReporte
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando novedades por fecha.",
                    e
            );
        }
    }

    @Override
    public EntyOrsconfnovedadhistoriResponse findByOrdenResponse(
            final String ordenKey
    ) throws EBusinessException {
        return buildResponse(
                findByOrden(ordenKey),
                "Novedades por orden consultadas correctamente.",
                ordenKey != null ? ordenKey.trim().toUpperCase() : "NA",
                null
        );
    }

    @Override
    public EntyOrsconfnovedadhistoriResponse findByTipoResponse(
            final String tipoNovedad
    ) throws EBusinessException {
        return buildResponse(
                findByTipo(tipoNovedad),
                "Novedades por tipo consultadas correctamente.",
                tipoNovedad != null ? tipoNovedad.trim().toUpperCase() : "NA",
                null
        );
    }

    private EntyOrsconfnovedadhistoriDto normalizeForCreate(
            final EntyOrsconfnovedadhistoriDto dto
    ) {
        EntyOrsconfnovedadhistoriDto normalized =
                dto != null ? dto : new EntyOrsconfnovedadhistoriDto();

        normalized.setOrsPrimarykeyNove(null);
        normalized.setOrsIdentifkeyNove(
                upper(normalized.getOrsIdentifkeyNove())
        );
        normalized.setOrsIdentifkeyOrde(
                upper(normalized.getOrsIdentifkeyOrde())
        );
        normalized.setOrsTiponovedadNovt(
                upper(normalized.getOrsTiponovedadNovt())
        );
        normalized.setOrsRegistrbaseNove(
                upper(normalized.getOrsRegistrbaseNove())
        );
        normalized.setOrsRegistrnoveNove(
                upper(normalized.getOrsRegistrnoveNove())
        );
        normalized.setOrsEstadoregNove(
                upper(normalized.getOrsEstadoregNove())
        );

        if (normalized.getOrsEstadoregNove() == null
                || normalized.getOrsEstadoregNove().trim().isEmpty()) {
            normalized.setOrsEstadoregNove("1");
        }

        return normalized;
    }

    private EntyOrsconfnovedadhistoriDto normalizeForUpdate(
            final EntyOrsconfnovedadhistoriDto dto
    ) {
        EntyOrsconfnovedadhistoriDto normalized =
                dto != null ? dto : new EntyOrsconfnovedadhistoriDto();

        normalized.setOrsIdentifkeyNove(
                upper(normalized.getOrsIdentifkeyNove())
        );
        normalized.setOrsIdentifkeyOrde(
                upper(normalized.getOrsIdentifkeyOrde())
        );
        normalized.setOrsTiponovedadNovt(
                upper(normalized.getOrsTiponovedadNovt())
        );
        normalized.setOrsRegistrbaseNove(
                upper(normalized.getOrsRegistrbaseNove())
        );
        normalized.setOrsRegistrnoveNove(
                upper(normalized.getOrsRegistrnoveNove())
        );
        normalized.setOrsEstadoregNove(
                upper(normalized.getOrsEstadoregNove())
        );

        if (normalized.getOrsEstadoregNove() == null
                || normalized.getOrsEstadoregNove().trim().isEmpty()) {
            normalized.setOrsEstadoregNove("1");
        }

        return normalized;
    }

    private String upper(final String value) {
        if (value == null) {
            return null;
        }

        String clean = value.trim();

        return clean.isEmpty() ? null : clean.toUpperCase();
    }

    private EntyOrsconfnovedadhistoriDto toDto(
            final EntyOrsconfnovedadhistori entity
    ) {
        return toDto(entity, EntyOrsconfnovedadhistoriDto.class);
    }

    private EntyOrsconfnovedadhistori toEntity(
            final EntyOrsconfnovedadhistoriDto dto
    ) {
        return toEntity(dto, EntyOrsconfnovedadhistori.class);
    }

    private EntyOrsconfnovedadhistoriResponse buildResponse(
            final List<EntyOrsconfnovedadhistoriDto> data,
            final String message,
            final String parentKey,
            final PaginationResponse pagination
    ) {
        EntyOrsconfnovedadhistoriResponse response =
                new EntyOrsconfnovedadhistoriResponse();

        response.setRspValue("OK");
        response.setRspMessage(message);
        response.setRspParentKey(parentKey != null ? parentKey : "NA");
        response.setRspAppKey("WORK-CONTROL");
        response.setRspData(data != null ? data : new ArrayList<>());
        response.setRspPagination(pagination);

        return response;
    }
}