package com.system.modules.controlobras.dataproviders.jpa;

import java.util.ArrayList;
import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsordmaactamodificacionDto;
import com.system.crosscutting.domain.model.EntyOrsordmaactamodificacionResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsordmaactamodificacion;
import com.system.crosscutting.persistence.repository.EntyOrsordmaactamodificacionRepository;
import com.system.modules.controlobras.contracts.IjpaActaModificacionDataProviders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaActaModificacionDataProviders
        extends JpaDataProviderSupport
        implements IjpaActaModificacionDataProviders {

    @Autowired
    private EntyOrsordmaactamodificacionRepository repository;

    @Override
    public EntyOrsordmaactamodificacionResponse getAll() throws EBusinessException {
        try {
            EntyOrsordmaactamodificacionResponse response = new EntyOrsordmaactamodificacionResponse();
            response.setRspMessage("Actas de modificación consultadas correctamente");
            response.setRspValue("OK");
            response.setRspData(translateList(repository.findAll()));
            return response;
        } catch (Exception e) {
            throw buildException("Error consultando actas de modificación", e);
        }
    }

    @Override
    public EntyOrsordmaactamodificacionResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        try {
            Pageable pageable = PageRequest.of(
                    safeCurrentPage(currentPage),
                    safePageSize(pageSize),
                    Sort.by(Sort.Direction.DESC, "orsPrimarykeyAcmo")
            );

            Page<EntyOrsordmaactamodificacion> page;
            String param = safeParameter(parameter);
            String value = safeFilter(filter);

            switch (param) {
                case "ID":
                case "KEY":
                    page = repository.searchByIdentifKey(value, pageable);
                    break;

                case "ORDEN":
                    page = repository.searchByOrden(value, pageable);
                    break;

                case "NUMERO_ACTA":
                case "ACTA":
                    page = repository.searchByNumeroActa(value, pageable);
                    break;

                case "ESTADO_ACTA":
                    page = repository.searchByEstadoActa(value, pageable);
                    break;

                case "ESTADO":
                    page = repository.searchByStatus(value, pageable);
                    break;

                case "TEXT":
                default:
                    page = repository.searchByText(value, pageable);
                    break;
            }

            EntyOrsordmaactamodificacionResponse response = new EntyOrsordmaactamodificacionResponse();
            response.setRspMessage("Actas de modificación consultadas correctamente");
            response.setRspValue("OK");
            response.setRspData(translateList(page.getContent()));
            response.setRspPagination(buildPagination(currentPage, pageSize, page));
            return response;

        } catch (Exception e) {
            throw buildException("Error consultando actas de modificación paginadas", e);
        }
    }

    @Override
    public EntyOrsordmaactamodificacionDto get(final Integer id) throws EBusinessException {
        try {
            EntyOrsordmaactamodificacion entity = repository.findById(id)
                    .orElseThrow(() -> buildException("No existe acta de modificación con id: " + id, null));

            return toDto(entity, EntyOrsordmaactamodificacionDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error consultando acta de modificación por id", e);
        }
    }

    @Override
    public EntyOrsordmaactamodificacionDto save(final EntyOrsordmaactamodificacionDto dto)
            throws EBusinessException {
        try {
            aplicarDefaults(dto);

            EntyOrsordmaactamodificacion entity =
                    toEntity(dto, EntyOrsordmaactamodificacion.class);

            EntyOrsordmaactamodificacion saved = repository.save(entity);

            return toDto(saved, EntyOrsordmaactamodificacionDto.class);

        } catch (Exception e) {
            throw buildException("Error guardando acta de modificación", e);
        }
    }

    @Override
    public List<EntyOrsordmaactamodificacionDto> save(
            final List<EntyOrsordmaactamodificacionDto> dtoList
    ) throws EBusinessException {
        try {
            List<EntyOrsordmaactamodificacionDto> result = new ArrayList<>();

            for (EntyOrsordmaactamodificacionDto dto : dtoList) {
                result.add(save(dto));
            }

            return result;

        } catch (Exception e) {
            throw buildException("Error guardando lista de actas de modificación", e);
        }
    }

    @Override
    public EntyOrsordmaactamodificacionDto update(
            final Integer id,
            final EntyOrsordmaactamodificacionDto dto
    ) throws EBusinessException {
        try {
            EntyOrsordmaactamodificacion current = repository.findById(id)
                    .orElseThrow(() -> buildException("No existe acta de modificación con id: " + id, null));

            aplicarDefaults(dto);

            EntyOrsordmaactamodificacion entity =
                    toEntity(dto, EntyOrsordmaactamodificacion.class);

            entity.setOrsPrimarykeyAcmo(current.getOrsPrimarykeyAcmo());

            EntyOrsordmaactamodificacion saved = repository.save(entity);

            return toDto(saved, EntyOrsordmaactamodificacionDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error actualizando acta de modificación", e);
        }
    }

    @Override
    public void delete(final Integer id) throws EBusinessException {
        try {
            if (!repository.existsById(id)) {
                throw buildException("No existe acta de modificación con id: " + id, null);
            }

            repository.deleteById(id);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error eliminando acta de modificación", e);
        }
    }

    @Override
    public EntyOrsordmaactamodificacionDto changestatus(
            final Integer id,
            final String status
    ) throws EBusinessException {
        try {
            EntyOrsordmaactamodificacion entity = repository.findById(id)
                    .orElseThrow(() -> buildException("No existe acta de modificación con id: " + id, null));

            entity.setOrsEstadoregAcmo(status);

            EntyOrsordmaactamodificacion saved = repository.save(entity);

            return toDto(saved, EntyOrsordmaactamodificacionDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error cambiando estado del acta de modificación", e);
        }
    }

    @Override
    public EntyOrsordmaactamodificacionDto findByKey(final String actaModificacionKey)
            throws EBusinessException {
        try {
            EntyOrsordmaactamodificacion entity = repository.findByOrsIdentifkeyAcmo(actaModificacionKey)
                    .orElseThrow(() -> buildException(
                            "No existe acta de modificación con key: " + actaModificacionKey,
                            null
                    ));

            return toDto(entity, EntyOrsordmaactamodificacionDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error consultando acta de modificación por key", e);
        }
    }

    @Override
    public List<EntyOrsordmaactamodificacionDto> findByOrden(final String ordenKey)
            throws EBusinessException {
        try {
            return translateList(repository.findByOrsIdentifkeyOrde(ordenKey));
        } catch (Exception e) {
            throw buildException("Error consultando actas de modificación por orden", e);
        }
    }

    @Override
    public List<EntyOrsordmaactamodificacionDto> findByEstadoActa(final String estadoActa)
            throws EBusinessException {
        try {
            return translateList(repository.findByOrsEstadoactaAcmo(estadoActa));
        } catch (Exception e) {
            throw buildException("Error consultando actas por estado de acta", e);
        }
    }

    @Override
    public List<EntyOrsordmaactamodificacionDto> findByEstado(final String estado)
            throws EBusinessException {
        try {
            return translateList(repository.findByOrsEstadoregAcmo(estado));
        } catch (Exception e) {
            throw buildException("Error consultando actas de modificación por estado", e);
        }
    }

    public EntyOrsordmaactamodificacionDto cambiarEstadoActa(
            final String actaModificacionKey,
            final String estadoActa
    ) throws EBusinessException {
        try {
            EntyOrsordmaactamodificacion entity = repository.findByOrsIdentifkeyAcmo(actaModificacionKey)
                    .orElseThrow(() -> buildException(
                            "No existe acta de modificación con key: " + actaModificacionKey,
                            null
                    ));

            entity.setOrsEstadoactaAcmo(estadoActa);

            EntyOrsordmaactamodificacion saved = repository.save(entity);

            return toDto(saved, EntyOrsordmaactamodificacionDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error cambiando estado del acta de modificación", e);
        }
    }

    private void aplicarDefaults(final EntyOrsordmaactamodificacionDto dto) {
        if (dto == null) {
            return;
        }

        if (dto.getOrsEstadoactaAcmo() == null) {
            dto.setOrsEstadoactaAcmo("BORRADOR");
        }

        if (dto.getOrsTiporegistAcmo() == null) {
            dto.setOrsTiporegistAcmo("1");
        }

        if (dto.getOrsEstadoregAcmo() == null) {
            dto.setOrsEstadoregAcmo("1");
        }
    }

    private List<EntyOrsordmaactamodificacionDto> translateList(
            final List<EntyOrsordmaactamodificacion> entities
    ) {
        List<EntyOrsordmaactamodificacionDto> result = new ArrayList<>();

        for (EntyOrsordmaactamodificacion entity : entities) {
            result.add(toDto(entity, EntyOrsordmaactamodificacionDto.class));
        }

        return result;
    }
}