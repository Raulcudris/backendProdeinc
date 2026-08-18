package com.system.modules.workcontrol.dataproviders.jpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsplamdreporteoperacionDto;
import com.system.crosscutting.domain.model.EntyOrsplamdreporteoperacionResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsplamdreporteoperacion;
import com.system.crosscutting.persistence.repository.EntyOrsplamdreporteoperacionRepository;
import com.system.modules.workcontrol.contracts.IjpaReporteOperacionDataProviders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaReporteOperacionDataProviders
        extends JpaDataProviderSupport
        implements IjpaReporteOperacionDataProviders {

    @Autowired
    private EntyOrsplamdreporteoperacionRepository repository;

    @Override
    public EntyOrsplamdreporteoperacionResponse getAll() throws EBusinessException {
        try {
            EntyOrsplamdreporteoperacionResponse response = new EntyOrsplamdreporteoperacionResponse();
            response.setRspMessage("Reportes de operación consultados correctamente");
            response.setRspValue("OK");
            response.setRspData(translateList(repository.findAll()));
            return response;
        } catch (Exception e) {
            throw buildException("Error consultando reportes de operación", e);
        }
    }

    @Override
    public EntyOrsplamdreporteoperacionResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        try {
            Pageable pageable = PageRequest.of(
                    safeCurrentPage(currentPage),
                    safePageSize(pageSize),
                    Sort.by(Sort.Direction.DESC, "orsPrimarykeyRope")
            );

            Page<EntyOrsplamdreporteoperacion> page;
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

                case "PSEM":
                case "PROYECCION":
                    page = repository.searchByProyeccionSemana(value, pageable);
                    break;

                case "PLSE":
                case "PLAN_SEMANAL":
                    page = repository.searchByPlanSemanal(value, pageable);
                    break;

                case "PUNTO":
                    page = repository.searchByPunto(value, pageable);
                    break;

                case "ESTADO":
                    page = repository.searchByStatus(value, pageable);
                    break;

                case "TEXT":
                default:
                    page = repository.searchByText(value, pageable);
                    break;
            }

            EntyOrsplamdreporteoperacionResponse response = new EntyOrsplamdreporteoperacionResponse();
            response.setRspMessage("Reportes de operación consultados correctamente");
            response.setRspValue("OK");
            response.setRspData(translateList(page.getContent()));
            response.setRspPagination(buildPagination(currentPage, pageSize, page));
            return response;

        } catch (Exception e) {
            throw buildException("Error consultando reportes de operación paginados", e);
        }
    }

    @Override
    public EntyOrsplamdreporteoperacionDto get(final Integer id) throws EBusinessException {
        try {
            EntyOrsplamdreporteoperacion entity = repository.findById(id)
                    .orElseThrow(() -> buildException("No existe reporte de operación con id: " + id, null));

            return toDto(entity, EntyOrsplamdreporteoperacionDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error consultando reporte de operación por id", e);
        }
    }

    @Override
    public EntyOrsplamdreporteoperacionDto save(final EntyOrsplamdreporteoperacionDto dto)
            throws EBusinessException {
        try {
            aplicarDefaults(dto);

            EntyOrsplamdreporteoperacion entity =
                    toEntity(dto, EntyOrsplamdreporteoperacion.class);

            EntyOrsplamdreporteoperacion saved = repository.save(entity);

            return toDto(saved, EntyOrsplamdreporteoperacionDto.class);

        } catch (Exception e) {
            throw buildException("Error guardando reporte de operación", e);
        }
    }

    @Override
    public List<EntyOrsplamdreporteoperacionDto> save(
            final List<EntyOrsplamdreporteoperacionDto> dtoList
    ) throws EBusinessException {
        try {
            List<EntyOrsplamdreporteoperacionDto> result = new ArrayList<>();

            for (EntyOrsplamdreporteoperacionDto dto : dtoList) {
                result.add(save(dto));
            }

            return result;

        } catch (Exception e) {
            throw buildException("Error guardando lista de reportes de operación", e);
        }
    }

    @Override
    public EntyOrsplamdreporteoperacionDto update(
            final Integer id,
            final EntyOrsplamdreporteoperacionDto dto
    ) throws EBusinessException {
        try {
            EntyOrsplamdreporteoperacion current = repository.findById(id)
                    .orElseThrow(() -> buildException("No existe reporte de operación con id: " + id, null));

            aplicarDefaults(dto);

            EntyOrsplamdreporteoperacion entity =
                    toEntity(dto, EntyOrsplamdreporteoperacion.class);

            entity.setOrsPrimarykeyRope(current.getOrsPrimarykeyRope());

            EntyOrsplamdreporteoperacion saved = repository.save(entity);

            return toDto(saved, EntyOrsplamdreporteoperacionDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error actualizando reporte de operación", e);
        }
    }

    @Override
    public void delete(final Integer id) throws EBusinessException {
        try {
            if (!repository.existsById(id)) {
                throw buildException("No existe reporte de operación con id: " + id, null);
            }

            repository.deleteById(id);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error eliminando reporte de operación", e);
        }
    }

    @Override
    public EntyOrsplamdreporteoperacionDto changestatus(
            final Integer id,
            final String status
    ) throws EBusinessException {
        try {
            EntyOrsplamdreporteoperacion entity = repository.findById(id)
                    .orElseThrow(() -> buildException("No existe reporte de operación con id: " + id, null));

            entity.setOrsEstadoregRope(status);

            EntyOrsplamdreporteoperacion saved = repository.save(entity);

            return toDto(saved, EntyOrsplamdreporteoperacionDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error cambiando estado del reporte de operación", e);
        }
    }

    @Override
    public EntyOrsplamdreporteoperacionDto findByKey(final String reporteOperacionKey)
            throws EBusinessException {
        try {
            EntyOrsplamdreporteoperacion entity = repository.findByOrsIdentifkeyRope(reporteOperacionKey)
                    .orElseThrow(() -> buildException(
                            "No existe reporte de operación con key: " + reporteOperacionKey,
                            null
                    ));

            return toDto(entity, EntyOrsplamdreporteoperacionDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error consultando reporte de operación por key", e);
        }
    }

    @Override
    public List<EntyOrsplamdreporteoperacionDto> findByOrden(final String ordenKey)
            throws EBusinessException {
        try {
            return translateList(repository.findByOrsIdentifkeyOrde(ordenKey));
        } catch (Exception e) {
            throw buildException("Error consultando reportes de operación por orden", e);
        }
    }

    @Override
    public List<EntyOrsplamdreporteoperacionDto> findByProyeccionSemana(
            final String proyeccionSemanaKey
    ) throws EBusinessException {
        try {
            return translateList(repository.findByOrsIdentifkeyPsem(proyeccionSemanaKey));
        } catch (Exception e) {
            throw buildException("Error consultando reportes de operación por proyección semanal", e);
        }
    }

    @Override
    public List<EntyOrsplamdreporteoperacionDto> findByPlanSemanal(final String planSemanalKey)
            throws EBusinessException {
        try {
            return translateList(repository.findByOrsIdentifkeyPlse(planSemanalKey));
        } catch (Exception e) {
            throw buildException("Error consultando reportes de operación por plan semanal", e);
        }
    }

    @Override
    public List<EntyOrsplamdreporteoperacionDto> findByPunto(final String puntoKey)
            throws EBusinessException {
        try {
            return translateList(repository.findByOrsIdentifkeyPunt(puntoKey));
        } catch (Exception e) {
            throw buildException("Error consultando reportes de operación por punto", e);
        }
    }

    @Override
    public List<EntyOrsplamdreporteoperacionDto> findByProveedor(final String proveedorKey)
            throws EBusinessException {
        try {
            return translateList(repository.findByPrvIdentifkeyMprv(proveedorKey));
        } catch (Exception e) {
            throw buildException("Error consultando reportes de operación por proveedor", e);
        }
    }

    @Override
    public List<EntyOrsplamdreporteoperacionDto> findByFechaReporte(final LocalDate fechaReporte)
            throws EBusinessException {
        try {
            return translateList(repository.findByOrsFechareportRope(fechaReporte));
        } catch (Exception e) {
            throw buildException("Error consultando reportes de operación por fecha", e);
        }
    }

    @Override
    public List<EntyOrsplamdreporteoperacionDto> findByEstado(final String estado)
            throws EBusinessException {
        try {
            return translateList(repository.findByOrsEstadoregRope(estado));
        } catch (Exception e) {
            throw buildException("Error consultando reportes de operación por estado", e);
        }
    }

    private void aplicarDefaults(final EntyOrsplamdreporteoperacionDto dto) {
        if (dto == null) {
            return;
        }

        if (dto.getOrsTiporegistRope() == null) {
            dto.setOrsTiporegistRope("1");
        }

        if (dto.getOrsEstadoregRope() == null) {
            dto.setOrsEstadoregRope("1");
        }

        if (dto.getOrsFirmasuministroRope() == null) {
            dto.setOrsFirmasuministroRope("0");
        }

        if (dto.getOrsFirmaseguimientoRope() == null) {
            dto.setOrsFirmaseguimientoRope("0");
        }
    }

    private List<EntyOrsplamdreporteoperacionDto> translateList(
            final List<EntyOrsplamdreporteoperacion> entities
    ) {
        List<EntyOrsplamdreporteoperacionDto> result = new ArrayList<>();

        for (EntyOrsplamdreporteoperacion entity : entities) {
            result.add(toDto(entity, EntyOrsplamdreporteoperacionDto.class));
        }

        return result;
    }
}