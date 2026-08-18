package com.system.modules.workcontrol.dataproviders.jpa;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.PersistenceException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.system.crosscutting.domain.model.EntyOrsordmdsitiospuntosDto;
import com.system.crosscutting.domain.model.EntyOrsordmdsitiospuntosResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsordmdsitiospuntos;
import com.system.crosscutting.persistence.repository.EntyOrsordmdsitiospuntosRepository;
import com.system.modules.workcontrol.dataproviders.IjpaSitiosPuntosDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaSitiosPuntosDataProviders extends JpaDataProviderSupport
        implements IjpaSitiosPuntosDataProviders {

    private static final String ESTADO_ABIERTO = "1";

    private final EntyOrsordmdsitiospuntosRepository repository;

    @Override
    public EntyOrsordmdsitiospuntosResponse getAll()
            throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsordmdsitiospuntosResponse getAll(
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
            Page<EntyOrsordmdsitiospuntos> page;

            switch (safeParameter(parameter)) {
                case "ID":
                    page = repository.searchByPrimaryKey(
                            parseInteger(search),
                            pageable
                    );
                    break;

                case "KEY":
                    page = repository.searchByIdentifKey(
                            search,
                            pageable
                    );
                    break;

                case "ORDEN":
                    page = repository.searchByOrden(
                            search,
                            pageable
                    );
                    break;

                case "STATUS":
                    page = repository.searchByStatus(
                            search,
                            pageable
                    );
                    break;

                default:
                    page = repository.searchByText(
                            search,
                            pageable
                    );
                    break;
            }

            List<EntyOrsordmdsitiospuntosDto> data = page
                    .getContent()
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            return buildResponse(
                    data,
                    "OK",
                    "NA",
                    buildPagination(pageNumber + 1, size, page)
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando sitios o puntos de trabajo.",
                    e
            );
        }
    }

    @Override
    public EntyOrsordmdsitiospuntosDto get(
            final Integer id
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyOrsordmdsitiospuntosDto();
            }

            return repository
                    .findById(id)
                    .map(this::toDto)
                    .orElseGet(EntyOrsordmdsitiospuntosDto::new);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando sitio o punto de trabajo.",
                    e
            );
        }
    }

    @Override
    public EntyOrsordmdsitiospuntosDto save(
            final EntyOrsordmdsitiospuntosDto dto
    ) throws EBusinessException {
        try {
            EntyOrsordmdsitiospuntosDto normalized =
                    normalizeForCreate(dto);

            EntyOrsordmdsitiospuntos entity =
                    toEntity(normalized);

            EntyOrsordmdsitiospuntos saved =
                    repository.save(entity);

            return toDto(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error creando sitio o punto de trabajo.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsordmdsitiospuntosDto> save(
            final List<EntyOrsordmdsitiospuntosDto> dtos
    ) throws EBusinessException {
        try {
            if (dtos == null || dtos.isEmpty()) {
                return new ArrayList<>();
            }

            List<EntyOrsordmdsitiospuntos> entities = dtos
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
                    "Error creando sitios o puntos de trabajo.",
                    e
            );
        }
    }

    @Override
    public EntyOrsordmdsitiospuntosDto update(
            final Integer id,
            final EntyOrsordmdsitiospuntosDto dto
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyOrsordmdsitiospuntosDto();
            }

            EntyOrsordmdsitiospuntos current = repository
                    .findById(id)
                    .orElse(null);

            if (current == null) {
                return new EntyOrsordmdsitiospuntosDto();
            }

            EntyOrsordmdsitiospuntos incoming =
                    toEntity(dto);

            incoming.setOrsPrimarykeyPunt(id);

            if (incoming.getOrsIdentifkeyPunt() == null
                    || incoming.getOrsIdentifkeyPunt().trim().isEmpty()) {
                incoming.setOrsIdentifkeyPunt(
                        current.getOrsIdentifkeyPunt()
                );
            }

            BeanUtils.copyProperties(
                    incoming,
                    current
            );

            EntyOrsordmdsitiospuntos saved =
                    repository.save(current);

            return toDto(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error actualizando sitio o punto de trabajo.",
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
                    "Error eliminando sitio o punto de trabajo.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsordmdsitiospuntosDto> findByOrden(
            final String ordenKey
    ) throws EBusinessException {
        try {
            if (ordenKey == null || ordenKey.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByOrsIdentifkeyOrdeOrderByOrsPrimarykeyPuntAsc(
                            ordenKey.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando sitios o puntos por orden.",
                    e
            );
        }
    }

    @Override
    public boolean existsByPuntoKey(
            final String puntoKey
    ) throws EBusinessException {
        try {
            if (puntoKey == null || puntoKey.trim().isEmpty()) {
                return false;
            }

            return repository.existsByOrsIdentifkeyPunt(
                    puntoKey.trim().toUpperCase()
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error validando código de sitio o punto.",
                    e
            );
        }
    }

    @Override
    public boolean existsActiveByOrden(
            final String ordenKey
    ) throws EBusinessException {
        try {
            if (ordenKey == null || ordenKey.trim().isEmpty()) {
                return false;
            }

            return repository
                    .existsByOrsIdentifkeyOrdeAndOrsEstadoregPunt(
                            ordenKey.trim().toUpperCase(),
                            ESTADO_ABIERTO
                    );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error validando sitios o puntos activos por orden.",
                    e
            );
        }
    }

    @Override
    public EntyOrsordmdsitiospuntosResponse findByOrdenResponse(
            final String ordenKey
    ) throws EBusinessException {
        List<EntyOrsordmdsitiospuntosDto> data =
                findByOrden(ordenKey);

        return buildResponse(
                data,
                "Sitios o puntos consultados correctamente.",
                ordenKey != null ? ordenKey.trim().toUpperCase() : "NA",
                null
        );
    }

    private EntyOrsordmdsitiospuntosDto normalizeForCreate(
            final EntyOrsordmdsitiospuntosDto dto
    ) {
        EntyOrsordmdsitiospuntosDto normalized =
                dto != null ? dto : new EntyOrsordmdsitiospuntosDto();

        normalized.setOrsPrimarykeyPunt(null);

        if (normalized.getOrsIdentifkeyPunt() != null) {
            normalized.setOrsIdentifkeyPunt(
                    normalized.getOrsIdentifkeyPunt()
                            .trim()
                            .toUpperCase()
            );
        }

        if (normalized.getOrsIdentifkeyOrde() != null) {
            normalized.setOrsIdentifkeyOrde(
                    normalized.getOrsIdentifkeyOrde()
                            .trim()
                            .toUpperCase()
            );
        }

        if (normalized.getSisCodproSipr() != null) {
            normalized.setSisCodproSipr(
                    normalized.getSisCodproSipr().trim()
            );
        }

        if (normalized.getOrsTiporegistPunt() == null
                || normalized.getOrsTiporegistPunt().trim().isEmpty()) {
            normalized.setOrsTiporegistPunt("1");
        }

        if (normalized.getOrsEstadoregPunt() == null
                || normalized.getOrsEstadoregPunt().trim().isEmpty()) {
            normalized.setOrsEstadoregPunt("1");
        }

        return normalized;
    }

    private EntyOrsordmdsitiospuntosDto toDto(
            final EntyOrsordmdsitiospuntos entity
    ) {
        return toDto(
                entity,
                EntyOrsordmdsitiospuntosDto.class
        );
    }

    private EntyOrsordmdsitiospuntos toEntity(
            final EntyOrsordmdsitiospuntosDto dto
    ) {
        return toEntity(
                dto,
                EntyOrsordmdsitiospuntos.class
        );
    }

    private EntyOrsordmdsitiospuntosResponse buildResponse(
            final List<EntyOrsordmdsitiospuntosDto> data,
            final String message,
            final String parentKey,
            final PaginationResponse pagination
    ) {
        EntyOrsordmdsitiospuntosResponse response =
                new EntyOrsordmdsitiospuntosResponse();

        response.setRspMessage(message);
        response.setRspValue("OK");
        response.setRspParentKey(parentKey != null ? parentKey : "NA");
        response.setRspAppKey("WORK-CONTROL");
        response.setRspData(data != null ? data : new ArrayList<>());
        response.setRspPagination(pagination);

        return response;
    }
}