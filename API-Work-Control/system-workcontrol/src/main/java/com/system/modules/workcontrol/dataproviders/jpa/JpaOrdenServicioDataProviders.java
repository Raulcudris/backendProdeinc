package com.system.modules.workcontrol.dataproviders.jpa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioDto;
import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsordmaordenservicio;
import com.system.crosscutting.persistence.repository.EntyOrsordmaordenservicioRepository;
import com.system.modules.workcontrol.dataproviders.IjpaOrdenServicioDataProviders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class JpaOrdenServicioDataProviders extends JpaDataProviderSupport
        implements IjpaOrdenServicioDataProviders {

    private final EntyOrsordmaordenservicioRepository repository;

    public JpaOrdenServicioDataProviders(
            final EntyOrsordmaordenservicioRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public EntyOrsordmaordenservicioDto create(
            final EntyOrsordmaordenservicioDto dto
    ) {
        EntyOrsordmaordenservicio entity = toEntity(dto);
        EntyOrsordmaordenservicio saved = repository.save(entity);

        return toDto(saved);
    }

    @Override
    public Optional<EntyOrsordmaordenservicioDto> getByKey(
            final String ordenKey
    ) {
        return repository
                .findByOrsIdentifkeyOrde(ordenKey)
                .map(this::toDto);
    }

    @Override
    public List<EntyOrsordmaordenservicioDto> getByEstado(
            final String estado
    ) {
        return repository
                .findByOrsEstadoregOrde(estado)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByOrdenKey(final String ordenKey) {
        return repository.existsByOrsIdentifkeyOrde(ordenKey);
    }

    @Override
    public EntyOrsordmaordenservicioResponse getAll()
            throws EBusinessException {
        try {
            List<EntyOrsordmaordenservicioDto> rows = repository
                    .findAll(Sort.by(
                            Sort.Direction.DESC,
                            "orsPrimarykeyOrde"
                    ))
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            return buildResponse(
                    rows,
                    new PaginationResponse(),
                    "Órdenes de servicio consultadas correctamente."
            );
        } catch (Exception e) {
            throw buildException(
                    "Error consultando órdenes de servicio.",
                    e
            );
        }
    }

    @Override
    public EntyOrsordmaordenservicioResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        try {
            PageRequest pageable = PageRequest.of(
                    safeCurrentPage(currentPage),
                    safePageSize(pageSize),
                    Sort.by(Sort.Direction.DESC, "orsPrimarykeyOrde")
            );

            String safeFilter = safeFilter(filter);

            Page<EntyOrsordmaordenservicio> page;

            if (safeFilter.trim().isEmpty()) {
                page = repository.findAll(pageable);
            } else {
                page = repository.searchText(safeFilter.trim(), pageable);
            }

            List<EntyOrsordmaordenservicioDto> rows = page
                    .getContent()
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            return buildResponse(
                    rows,
                    buildPagination(currentPage, pageSize, page),
                    "Órdenes de servicio consultadas correctamente."
            );
        } catch (Exception e) {
            throw buildException(
                    "Error consultando órdenes de servicio paginadas.",
                    e
            );
        }
    }

    @Override
    public EntyOrsordmaordenservicioDto get(final Integer id)
            throws EBusinessException {
        try {
            return repository
                    .findById(id)
                    .map(this::toDto)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No existe la orden de servicio con ID: " + id
                    ));
        } catch (Exception e) {
            throw buildException(
                    "Error consultando la orden de servicio.",
                    e
            );
        }
    }

    @Override
    public EntyOrsordmaordenservicioDto save(
            final EntyOrsordmaordenservicioDto dto
    ) throws EBusinessException {
        try {
            return create(dto);
        } catch (Exception e) {
            throw buildException(
                    "Error guardando la orden de servicio.",
                    e
            );
        }
    }

    @Override
    public List<EntyOrsordmaordenservicioDto> save(
            final List<EntyOrsordmaordenservicioDto> dtoList
    ) throws EBusinessException {
        try {
            return dtoList
                    .stream()
                    .map(this::create)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw buildException(
                    "Error guardando órdenes de servicio.",
                    e
            );
        }
    }

    @Override
    public EntyOrsordmaordenservicioDto update(
            final Integer id,
            final EntyOrsordmaordenservicioDto dto
    ) throws EBusinessException {
        try {
            EntyOrsordmaordenservicio current = repository
                    .findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No existe la orden de servicio con ID: " + id
                    ));

            EntyOrsordmaordenservicio entity = toEntity(dto);

            entity.setOrsPrimarykeyOrde(current.getOrsPrimarykeyOrde());

            if (entity.getOrsIdentifkeyOrde() == null
                    || entity.getOrsIdentifkeyOrde().trim().isEmpty()) {
                entity.setOrsIdentifkeyOrde(current.getOrsIdentifkeyOrde());
            }

            EntyOrsordmaordenservicio saved = repository.save(entity);

            return toDto(saved);
        } catch (Exception e) {
            throw buildException(
                    "Error actualizando la orden de servicio.",
                    e
            );
        }
    }

    @Override
    public void delete(final Integer id) throws EBusinessException {
        try {
            if (!repository.existsById(id)) {
                throw new IllegalArgumentException(
                        "No existe la orden de servicio con ID: " + id
                );
            }

            repository.deleteById(id);
        } catch (Exception e) {
            throw buildException(
                    "Error eliminando la orden de servicio.",
                    e
            );
        }
    }

    private EntyOrsordmaordenservicioResponse buildResponse(
            final List<EntyOrsordmaordenservicioDto> rows,
            final PaginationResponse pagination,
            final String message
    ) {
        return EntyOrsordmaordenservicioResponse
                .builder()
                .rspValue("OK")
                .rspMessage(message)
                .rspParentKey("NA")
                .rspAppKey("WORK-CONTROL")
                .rspPagination(pagination)
                .rspData(rows)
                .build();
    }

    private EntyOrsordmaordenservicioDto toDto(
            final EntyOrsordmaordenservicio entity
    ) {
        if (entity == null) {
            return null;
        }

        EntyOrsordmaordenservicioDto dto =
                new EntyOrsordmaordenservicioDto();

        dto.setOrsPrimarykeyOrde(entity.getOrsPrimarykeyOrde());
        dto.setOrsIdentifkeyOrde(entity.getOrsIdentifkeyOrde());
        dto.setOrsAutorifechaOrde(entity.getOrsAutorifechaOrde());
        dto.setOrsCodservicioSebs(entity.getOrsCodservicioSebs());
        dto.setOrsServiceventOrde(entity.getOrsServiceventOrde());
        dto.setOrsServiclugarOrde(entity.getOrsServiclugarOrde());
        dto.setOrsServicobjetoOrde(entity.getOrsServicobjetoOrde());
        dto.setOrsPlanfechiniOrde(entity.getOrsPlanfechiniOrde());
        dto.setOrsPlanfechfinOrde(entity.getOrsPlanfechfinOrde());
        dto.setPrvIdentifkeyMprv(entity.getPrvIdentifkeyMprv());
        dto.setPrvIdentifkeyRelg(entity.getPrvIdentifkeyRelg());
        dto.setOrsValorbaseOrde(entity.getOrsValorbaseOrde());
        dto.setOrsValordeivaOrde(entity.getOrsValordeivaOrde());
        dto.setOrsValortotalOrde(entity.getOrsValortotalOrde());
        dto.setOrsTiporegistOrde(entity.getOrsTiporegistOrde());
        dto.setOrsEstadoregOrde(entity.getOrsEstadoregOrde());

        return dto;
    }

    private EntyOrsordmaordenservicio toEntity(
            final EntyOrsordmaordenservicioDto dto
    ) {
        if (dto == null) {
            return null;
        }

        EntyOrsordmaordenservicio entity =
                new EntyOrsordmaordenservicio();

        entity.setOrsPrimarykeyOrde(dto.getOrsPrimarykeyOrde());
        entity.setOrsIdentifkeyOrde(dto.getOrsIdentifkeyOrde());
        entity.setOrsAutorifechaOrde(dto.getOrsAutorifechaOrde());
        entity.setOrsCodservicioSebs(dto.getOrsCodservicioSebs());
        entity.setOrsServiceventOrde(dto.getOrsServiceventOrde());
        entity.setOrsServiclugarOrde(dto.getOrsServiclugarOrde());
        entity.setOrsServicobjetoOrde(dto.getOrsServicobjetoOrde());
        entity.setOrsPlanfechiniOrde(dto.getOrsPlanfechiniOrde());
        entity.setOrsPlanfechfinOrde(dto.getOrsPlanfechfinOrde());
        entity.setPrvIdentifkeyMprv(dto.getPrvIdentifkeyMprv());
        entity.setPrvIdentifkeyRelg(dto.getPrvIdentifkeyRelg());
        entity.setOrsValorbaseOrde(dto.getOrsValorbaseOrde());
        entity.setOrsValordeivaOrde(dto.getOrsValordeivaOrde());
        entity.setOrsValortotalOrde(dto.getOrsValortotalOrde());
        entity.setOrsTiporegistOrde(dto.getOrsTiporegistOrde());
        entity.setOrsEstadoregOrde(dto.getOrsEstadoregOrde());

        return entity;
    }
}