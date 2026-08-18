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

import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoDto;
import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsplamaplandetrabajo;
import com.system.crosscutting.persistence.repository.EntyOrsplamaplandetrabajoRepository;
import com.system.modules.workcontrol.dataproviders.IjpaPlanTrabajoDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaPlanTrabajoDataProviders extends JpaDataProviderSupport
        implements IjpaPlanTrabajoDataProviders {

    private static final String ESTADO_ABIERTO = "1";

    private final EntyOrsplamaplandetrabajoRepository repository;

    @Override
    public EntyOrsplamaplandetrabajoResponse getAll()
            throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsplamaplandetrabajoResponse getAll(
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
            Page<EntyOrsplamaplandetrabajo> page;

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

                case "PUNTO":
                    page = repository.searchByPunto(
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

            List<EntyOrsplamaplandetrabajoDto> data = page
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
                    "Error consultando plan de trabajo proyectado.",
                    e
            );
        }
    }

    @Override
    public EntyOrsplamaplandetrabajoDto get(
            final Integer id
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyOrsplamaplandetrabajoDto();
            }

            return repository
                    .findById(id)
                    .map(this::toDto)
                    .orElseGet(EntyOrsplamaplandetrabajoDto::new);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando plan de trabajo proyectado.",
                    e
            );
        }
    }

    @Override
    public EntyOrsplamaplandetrabajoDto save(
            final EntyOrsplamaplandetrabajoDto dto
    ) throws EBusinessException {
        try {
            EntyOrsplamaplandetrabajoDto normalized =
                    normalizeForCreate(dto);

            EntyOrsplamaplandetrabajo entity =
                    toEntity(normalized);

            EntyOrsplamaplandetrabajo saved =
                    repository.save(entity);

            return toDto(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error creando plan de trabajo proyectado.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsplamaplandetrabajoDto> save(
            final List<EntyOrsplamaplandetrabajoDto> dtos
    ) throws EBusinessException {
        try {
            if (dtos == null || dtos.isEmpty()) {
                return new ArrayList<>();
            }

            List<EntyOrsplamaplandetrabajo> entities = dtos
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
                    "Error creando planes de trabajo proyectado.",
                    e
            );
        }
    }

    @Override
    public EntyOrsplamaplandetrabajoDto update(
            final Integer id,
            final EntyOrsplamaplandetrabajoDto dto
    ) throws EBusinessException {
        try {
            if (id == null) {
                return new EntyOrsplamaplandetrabajoDto();
            }

            EntyOrsplamaplandetrabajo current = repository
                    .findById(id)
                    .orElse(null);

            if (current == null) {
                return new EntyOrsplamaplandetrabajoDto();
            }

            EntyOrsplamaplandetrabajo incoming =
                    toEntity(dto);

            incoming.setOrsPrimarykeyPltr(id);

            if (incoming.getOrsIdentifkeyPltr() == null
                    || incoming.getOrsIdentifkeyPltr().trim().isEmpty()) {
                incoming.setOrsIdentifkeyPltr(
                        current.getOrsIdentifkeyPltr()
                );
            }

            BeanUtils.copyProperties(
                    incoming,
                    current
            );

            EntyOrsplamaplandetrabajo saved =
                    repository.save(current);

            return toDto(saved);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error actualizando plan de trabajo proyectado.",
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
                    "Error eliminando plan de trabajo proyectado.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsplamaplandetrabajoDto> findByOrden(
            final String ordenKey
    ) throws EBusinessException {
        try {
            if (ordenKey == null || ordenKey.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByOrsIdentifkeyOrdeOrderByOrsPrimarykeyPltrAsc(
                            ordenKey.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando planes de trabajo por orden.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsplamaplandetrabajoDto> findByPunto(
            final String puntoKey
    ) throws EBusinessException {
        try {
            if (puntoKey == null || puntoKey.trim().isEmpty()) {
                return new ArrayList<>();
            }

            return repository
                    .findByOrsIdentifkeyPuntOrderByOrsPrimarykeyPltrAsc(
                            puntoKey.trim().toUpperCase()
                    )
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error consultando planes de trabajo por punto.",
                    e
            );
        }
    }

    @Override
    public boolean existsByPlanKey(
            final String planKey
    ) throws EBusinessException {
        try {
            if (planKey == null || planKey.trim().isEmpty()) {
                return false;
            }

            return repository.existsByOrsIdentifkeyPltr(
                    planKey.trim().toUpperCase()
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error validando código de plan de trabajo.",
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
                    .existsByOrsIdentifkeyOrdeAndOrsEstadoregPltr(
                            ordenKey.trim().toUpperCase(),
                            ESTADO_ABIERTO
                    );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException(
                    "Error validando planes activos por orden.",
                    e
            );
        }
    }

    @Override
    public EntyOrsplamaplandetrabajoResponse findByOrdenResponse(
            final String ordenKey
    ) throws EBusinessException {
        List<EntyOrsplamaplandetrabajoDto> data =
                findByOrden(ordenKey);

        return buildResponse(
                data,
                "Planes de trabajo consultados correctamente.",
                ordenKey != null ? ordenKey.trim().toUpperCase() : "NA",
                null
        );
    }

    @Override
    public EntyOrsplamaplandetrabajoResponse findByPuntoResponse(
            final String puntoKey
    ) throws EBusinessException {
        List<EntyOrsplamaplandetrabajoDto> data =
                findByPunto(puntoKey);

        return buildResponse(
                data,
                "Planes de trabajo por punto consultados correctamente.",
                puntoKey != null ? puntoKey.trim().toUpperCase() : "NA",
                null
        );
    }

    private EntyOrsplamaplandetrabajoDto normalizeForCreate(
            final EntyOrsplamaplandetrabajoDto dto
    ) {
        EntyOrsplamaplandetrabajoDto normalized =
                dto != null ? dto : new EntyOrsplamaplandetrabajoDto();

        normalized.setOrsPrimarykeyPltr(null);

        if (normalized.getOrsIdentifkeyPltr() != null) {
            normalized.setOrsIdentifkeyPltr(
                    normalized.getOrsIdentifkeyPltr()
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

        if (normalized.getOrsIdentifkeyPunt() != null) {
            normalized.setOrsIdentifkeyPunt(
                    normalized.getOrsIdentifkeyPunt()
                            .trim()
                            .toUpperCase()
            );
        }

        if (normalized.getOrsIdentifkeyRseq() != null) {
            normalized.setOrsIdentifkeyRseq(
                    normalized.getOrsIdentifkeyRseq()
                            .trim()
                            .toUpperCase()
            );
        }

        if (normalized.getPrvIdentifkeyInve() != null) {
            normalized.setPrvIdentifkeyInve(
                    normalized.getPrvIdentifkeyInve()
                            .trim()
                            .toUpperCase()
            );
        }

        if (normalized.getOrsTiporegistPltr() == null
                || normalized.getOrsTiporegistPltr().trim().isEmpty()) {
            normalized.setOrsTiporegistPltr("1");
        }

        if (normalized.getOrsEstadoregPltr() == null
                || normalized.getOrsEstadoregPltr().trim().isEmpty()) {
            normalized.setOrsEstadoregPltr("1");
        }

        return normalized;
    }

    private EntyOrsplamaplandetrabajoDto toDto(
            final EntyOrsplamaplandetrabajo entity
    ) {
        return toDto(
                entity,
                EntyOrsplamaplandetrabajoDto.class
        );
    }

    private EntyOrsplamaplandetrabajo toEntity(
            final EntyOrsplamaplandetrabajoDto dto
    ) {
        return toEntity(
                dto,
                EntyOrsplamaplandetrabajo.class
        );
    }

    private EntyOrsplamaplandetrabajoResponse buildResponse(
            final List<EntyOrsplamaplandetrabajoDto> data,
            final String message,
            final String parentKey,
            final PaginationResponse pagination
    ) {
        EntyOrsplamaplandetrabajoResponse response =
                new EntyOrsplamaplandetrabajoResponse();

        response.setRspMessage(message);
        response.setRspValue("OK");
        response.setRspParentKey(parentKey != null ? parentKey : "NA");
        response.setRspAppKey("WORK-CONTROL");
        response.setRspData(data != null ? data : new ArrayList<>());
        response.setRspPagination(pagination);

        return response;
    }
}