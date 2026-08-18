package com.system.modules.workcontrol.dataproviders.jpa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsplamddetalleequipooperacionDto;
import com.system.crosscutting.domain.model.EntyOrsplamddetalleequipooperacionResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsplamddetalleequipooperacion;
import com.system.crosscutting.persistence.repository.EntyOrsplamddetalleequipooperacionRepository;
import com.system.modules.workcontrol.contracts.IjpaDetalleEquipoOperacionDataProviders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaDetalleEquipoOperacionDataProviders
        extends JpaDataProviderSupport
        implements IjpaDetalleEquipoOperacionDataProviders {

    @Autowired
    private EntyOrsplamddetalleequipooperacionRepository repository;

    @Override
    public EntyOrsplamddetalleequipooperacionResponse getAll() throws EBusinessException {
        try {
            EntyOrsplamddetalleequipooperacionResponse response =
                    new EntyOrsplamddetalleequipooperacionResponse();

            response.setRspMessage("Detalles de equipo operación consultados correctamente");
            response.setRspValue("OK");
            response.setRspData(translateList(repository.findAll()));
            return response;
        } catch (Exception e) {
            throw buildException("Error consultando detalles de equipo operación", e);
        }
    }

    @Override
    public EntyOrsplamddetalleequipooperacionResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        try {
            Pageable pageable = PageRequest.of(
                    safeCurrentPage(currentPage),
                    safePageSize(pageSize),
                    Sort.by(Sort.Direction.DESC, "orsPrimarykeyDeop")
            );

            Page<EntyOrsplamddetalleequipooperacion> page;
            String param = safeParameter(parameter);
            String value = safeFilter(filter);

            switch (param) {
                case "ID":
                case "KEY":
                    page = repository.searchByIdentifKey(value, pageable);
                    break;

                case "ROPE":
                case "REPORTE":
                    page = repository.searchByReporteOperacion(value, pageable);
                    break;

                case "ORDEN":
                    page = repository.searchByOrden(value, pageable);
                    break;

                case "PLSE":
                case "PLAN_SEMANAL":
                    page = repository.searchByPlanSemanal(value, pageable);
                    break;

                case "EQUIPO":
                    page = repository.searchByEquipo(value, pageable);
                    break;

                case "TIPO_EQUIPO":
                    page = repository.searchByTipoEquipo(value, pageable);
                    break;

                case "ESTADO":
                    page = repository.searchByStatus(value, pageable);
                    break;

                case "TEXT":
                default:
                    page = repository.searchByText(value, pageable);
                    break;
            }

            EntyOrsplamddetalleequipooperacionResponse response =
                    new EntyOrsplamddetalleequipooperacionResponse();

            response.setRspMessage("Detalles de equipo operación consultados correctamente");
            response.setRspValue("OK");
            response.setRspData(translateList(page.getContent()));
            response.setRspPagination(buildPagination(currentPage, pageSize, page));
            return response;

        } catch (Exception e) {
            throw buildException("Error consultando detalles de equipo operación paginados", e);
        }
    }

    @Override
    public EntyOrsplamddetalleequipooperacionDto get(final Integer id) throws EBusinessException {
        try {
            EntyOrsplamddetalleequipooperacion entity = repository.findById(id)
                    .orElseThrow(() -> buildException(
                            "No existe detalle de equipo operación con id: " + id,
                            null
                    ));

            return toDto(entity, EntyOrsplamddetalleequipooperacionDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error consultando detalle de equipo operación por id", e);
        }
    }

    @Override
    public EntyOrsplamddetalleequipooperacionDto save(
            final EntyOrsplamddetalleequipooperacionDto dto
    ) throws EBusinessException {
        try {
            calcularValores(dto);

            EntyOrsplamddetalleequipooperacion entity =
                    toEntity(dto, EntyOrsplamddetalleequipooperacion.class);

            EntyOrsplamddetalleequipooperacion saved = repository.save(entity);

            return toDto(saved, EntyOrsplamddetalleequipooperacionDto.class);

        } catch (Exception e) {
            throw buildException("Error guardando detalle de equipo operación", e);
        }
    }

    @Override
    public List<EntyOrsplamddetalleequipooperacionDto> save(
            final List<EntyOrsplamddetalleequipooperacionDto> dtoList
    ) throws EBusinessException {
        try {
            List<EntyOrsplamddetalleequipooperacionDto> result = new ArrayList<>();

            for (EntyOrsplamddetalleequipooperacionDto dto : dtoList) {
                result.add(save(dto));
            }

            return result;

        } catch (Exception e) {
            throw buildException("Error guardando lista de detalles de equipo operación", e);
        }
    }

    @Override
    public EntyOrsplamddetalleequipooperacionDto update(
            final Integer id,
            final EntyOrsplamddetalleequipooperacionDto dto
    ) throws EBusinessException {
        try {
            EntyOrsplamddetalleequipooperacion current = repository.findById(id)
                    .orElseThrow(() -> buildException(
                            "No existe detalle de equipo operación con id: " + id,
                            null
                    ));

            calcularValores(dto);

            EntyOrsplamddetalleequipooperacion entity =
                    toEntity(dto, EntyOrsplamddetalleequipooperacion.class);

            entity.setOrsPrimarykeyDeop(current.getOrsPrimarykeyDeop());

            EntyOrsplamddetalleequipooperacion saved = repository.save(entity);

            return toDto(saved, EntyOrsplamddetalleequipooperacionDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error actualizando detalle de equipo operación", e);
        }
    }

    @Override
    public void delete(final Integer id) throws EBusinessException {
        try {
            if (!repository.existsById(id)) {
                throw buildException("No existe detalle de equipo operación con id: " + id, null);
            }

            repository.deleteById(id);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error eliminando detalle de equipo operación", e);
        }
    }

    @Override
    public EntyOrsplamddetalleequipooperacionDto changestatus(
            final Integer id,
            final String status
    ) throws EBusinessException {
        try {
            EntyOrsplamddetalleequipooperacion entity = repository.findById(id)
                    .orElseThrow(() -> buildException(
                            "No existe detalle de equipo operación con id: " + id,
                            null
                    ));

            entity.setOrsEstadoregDeop(status);

            EntyOrsplamddetalleequipooperacion saved = repository.save(entity);

            return toDto(saved, EntyOrsplamddetalleequipooperacionDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error cambiando estado del detalle de equipo operación", e);
        }
    }

    @Override
    public EntyOrsplamddetalleequipooperacionDto findByKey(
            final String detalleEquipoOperacionKey
    ) throws EBusinessException {
        try {
            EntyOrsplamddetalleequipooperacion entity =
                    repository.findByOrsIdentifkeyDeop(detalleEquipoOperacionKey)
                            .orElseThrow(() -> buildException(
                                    "No existe detalle de equipo operación con key: "
                                            + detalleEquipoOperacionKey,
                                    null
                            ));

            return toDto(entity, EntyOrsplamddetalleequipooperacionDto.class);

        } catch (EBusinessException e) {
            throw e;
        } catch (Exception e) {
            throw buildException("Error consultando detalle de equipo operación por key", e);
        }
    }

    @Override
    public List<EntyOrsplamddetalleequipooperacionDto> findByReporteOperacion(
            final String reporteOperacionKey
    ) throws EBusinessException {
        try {
            return translateList(repository.findByOrsIdentifkeyRope(reporteOperacionKey));
        } catch (Exception e) {
            throw buildException("Error consultando detalles por reporte de operación", e);
        }
    }

    @Override
    public List<EntyOrsplamddetalleequipooperacionDto> findByOrden(final String ordenKey)
            throws EBusinessException {
        try {
            return translateList(repository.findByOrsIdentifkeyOrde(ordenKey));
        } catch (Exception e) {
            throw buildException("Error consultando detalles por orden", e);
        }
    }

    @Override
    public List<EntyOrsplamddetalleequipooperacionDto> findByProyeccionSemana(
            final String proyeccionSemanaKey
    ) throws EBusinessException {
        try {
            return translateList(repository.findByOrsIdentifkeyPsem(proyeccionSemanaKey));
        } catch (Exception e) {
            throw buildException("Error consultando detalles por proyección semanal", e);
        }
    }

    @Override
    public List<EntyOrsplamddetalleequipooperacionDto> findByPlanSemanal(
            final String planSemanalKey
    ) throws EBusinessException {
        try {
            return translateList(repository.findByOrsIdentifkeyPlse(planSemanalKey));
        } catch (Exception e) {
            throw buildException("Error consultando detalles por plan semanal", e);
        }
    }

    @Override
    public List<EntyOrsplamddetalleequipooperacionDto> findByPunto(final String puntoKey)
            throws EBusinessException {
        try {
            return translateList(repository.findByOrsIdentifkeyPunt(puntoKey));
        } catch (Exception e) {
            throw buildException("Error consultando detalles por punto", e);
        }
    }

    @Override
    public List<EntyOrsplamddetalleequipooperacionDto> findByEquipo(final String equipoKey)
            throws EBusinessException {
        try {
            return translateList(repository.findByPrvIdentifkeyInve(equipoKey));
        } catch (Exception e) {
            throw buildException("Error consultando detalles por equipo", e);
        }
    }

    @Override
    public List<EntyOrsplamddetalleequipooperacionDto> findByTipoEquipo(final String tipoEquipoKey)
            throws EBusinessException {
        try {
            return translateList(repository.findByPrvTipoequipoTieq(tipoEquipoKey));
        } catch (Exception e) {
            throw buildException("Error consultando detalles por tipo de equipo", e);
        }
    }

    @Override
    public List<EntyOrsplamddetalleequipooperacionDto> findByFechaTrabajo(
            final LocalDate fechaTrabajo
    ) throws EBusinessException {
        try {
            return translateList(repository.findByOrsFechatrabajoDeop(fechaTrabajo));
        } catch (Exception e) {
            throw buildException("Error consultando detalles por fecha de trabajo", e);
        }
    }

    @Override
    public List<EntyOrsplamddetalleequipooperacionDto> findByEstado(final String estado)
            throws EBusinessException {
        try {
            return translateList(repository.findByOrsEstadoregDeop(estado));
        } catch (Exception e) {
            throw buildException("Error consultando detalles por estado", e);
        }
    }

    @Override
    public BigDecimal sumHorasByPlanSemanal(final String planSemanalKey)
            throws EBusinessException {
        try {
            return repository.sumHorasByPlanSemanal(planSemanalKey);
        } catch (Exception e) {
            throw buildException("Error sumando horas por plan semanal", e);
        }
    }

    @Override
    public BigDecimal sumValorEjecutadoByPlanSemanal(final String planSemanalKey)
            throws EBusinessException {
        try {
            return repository.sumValorEjecutadoByPlanSemanal(planSemanalKey);
        } catch (Exception e) {
            throw buildException("Error sumando valor ejecutado por plan semanal", e);
        }
    }

    @Override
    public BigDecimal sumHorasByOrden(final String ordenKey)
            throws EBusinessException {
        try {
            return repository.sumHorasByOrden(ordenKey);
        } catch (Exception e) {
            throw buildException("Error sumando horas por orden", e);
        }
    }

    @Override
    public BigDecimal sumValorEjecutadoByOrden(final String ordenKey)
            throws EBusinessException {
        try {
            return repository.sumValorEjecutadoByOrden(ordenKey);
        } catch (Exception e) {
            throw buildException("Error sumando valor ejecutado por orden", e);
        }
    }

    private void calcularValores(final EntyOrsplamddetalleequipooperacionDto dto) {
        if (dto == null) {
            return;
        }

        BigDecimal valorUnidad = nvl(dto.getOrsValorunidadDeop());

        String unidad = dto.getOrsUnidadDeop() == null
                ? ""
                : dto.getOrsUnidadDeop().trim().toUpperCase();

        String tipoControl = dto.getOrsTipocontrolDeop() == null
                ? ""
                : dto.getOrsTipocontrolDeop().trim().toUpperCase();

        if ("HORA".equals(unidad) || "HOROMETRO".equals(tipoControl)) {
            BigDecimal inicial = nvl(dto.getOrsHorometroiniDeop());
            BigDecimal fin = nvl(dto.getOrsHorometrofinDeop());

            BigDecimal horas = fin.subtract(inicial);

            if (horas.compareTo(BigDecimal.ZERO) < 0) {
                horas = BigDecimal.ZERO;
            }

            dto.setOrsHorastrabajadasDeop(horas);
            dto.setOrsValorejecutadoDeop(horas.multiply(valorUnidad));
        }

        if ("DIA".equals(unidad) || "DÍA".equals(unidad) || "DIA".equals(tipoControl)) {
            BigDecimal diaTrabajado = nvl(dto.getOrsDiatrabajadoDeop());
            dto.setOrsValorejecutadoDeop(diaTrabajado.multiply(valorUnidad));
        }

        if ("KM".equals(unidad) || "KILOMETRAJE".equals(tipoControl)) {
            BigDecimal inicial = nvl(dto.getOrsKminicialDeop());
            BigDecimal fin = nvl(dto.getOrsKmfinalDeop());

            BigDecimal km = fin.subtract(inicial);

            if (km.compareTo(BigDecimal.ZERO) < 0) {
                km = BigDecimal.ZERO;
            }

            dto.setOrsKmrecorridoDeop(km);
            dto.setOrsValorejecutadoDeop(km.multiply(valorUnidad));
        }

        if (dto.getOrsEstadoregDeop() == null) {
            dto.setOrsEstadoregDeop("1");
        }

        if (dto.getOrsTiporegistDeop() == null) {
            dto.setOrsTiporegistDeop("1");
        }

        if (dto.getOrsFirmasuministroDeop() == null) {
            dto.setOrsFirmasuministroDeop("0");
        }

        if (dto.getOrsFirmaseguimientoDeop() == null) {
            dto.setOrsFirmaseguimientoDeop("0");
        }
    }

    private BigDecimal nvl(final BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private List<EntyOrsplamddetalleequipooperacionDto> translateList(
            final List<EntyOrsplamddetalleequipooperacion> entities
    ) {
        List<EntyOrsplamddetalleequipooperacionDto> result = new ArrayList<>();

        for (EntyOrsplamddetalleequipooperacion entity : entities) {
            result.add(toDto(entity, EntyOrsplamddetalleequipooperacionDto.class));
        }

        return result;
    }
}