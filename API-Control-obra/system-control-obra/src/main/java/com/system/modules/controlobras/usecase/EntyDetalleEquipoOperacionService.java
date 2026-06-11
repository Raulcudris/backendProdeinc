package com.system.modules.controlobras.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsplamddetalleequipooperacionDto;
import com.system.crosscutting.domain.model.EntyOrsplamddetalleequipooperacionResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.contracts.IjpaDetalleEquipoOperacionDataProviders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntyDetalleEquipoOperacionService {

    private static final String ESTADO_ACTIVO = "1";
    private static final String ESTADO_PENDIENTE_FIRMA = "0";
    private static final String TIPO_REGISTRO_ORIGINAL = "1";

    private static final String TIPO_CONTROL_HOROMETRO = "HOROMETRO";
    private static final String TIPO_CONTROL_KILOMETRAJE = "KILOMETRAJE";
    private static final String TIPO_CONTROL_DIA = "DIA";

    @Autowired
    private IjpaDetalleEquipoOperacionDataProviders dataProvider;

    public EntyOrsplamddetalleequipooperacionResponse getAll() throws EBusinessException {
        return dataProvider.getAll();
    }

    public EntyOrsplamddetalleequipooperacionResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        return dataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsplamddetalleequipooperacionDto get(final Integer id)
            throws EBusinessException {
        return dataProvider.get(id);
    }

    public EntyOrsplamddetalleequipooperacionDto saveBefore(
            final EntyOrsplamddetalleequipooperacionDto dto
    ) throws EBusinessException {

        aplicarReglasNegocio(dto);
        return dataProvider.save(dto);
    }

    public List<EntyOrsplamddetalleequipooperacionDto> saveBefore(
            final List<EntyOrsplamddetalleequipooperacionDto> dtoList
    ) throws EBusinessException {

        if (dtoList != null) {
            for (EntyOrsplamddetalleequipooperacionDto dto : dtoList) {
                aplicarReglasNegocio(dto);
            }
        }

        return dataProvider.save(dtoList);
    }

    public EntyOrsplamddetalleequipooperacionDto updateBefore(
            final Integer id,
            final EntyOrsplamddetalleequipooperacionDto dto
    ) throws EBusinessException {

        aplicarReglasNegocio(dto);
        return dataProvider.update(id, dto);
    }

    public EntyOrsplamddetalleequipooperacionDto changestatus(
            final Integer id,
            final String status
    ) throws EBusinessException {
        return dataProvider.changestatus(id, status);
    }

    public void deleteBefore(final Integer id) throws EBusinessException {
        dataProvider.delete(id);
    }

    public EntyOrsplamddetalleequipooperacionDto findByKey(
            final String detalleEquipoOperacionKey
    ) throws EBusinessException {
        return dataProvider.findByKey(detalleEquipoOperacionKey);
    }

    public List<EntyOrsplamddetalleequipooperacionDto> findByReporteOperacion(
            final String reporteOperacionKey
    ) throws EBusinessException {
        return dataProvider.findByReporteOperacion(reporteOperacionKey);
    }

    public List<EntyOrsplamddetalleequipooperacionDto> findByOrden(
            final String ordenKey
    ) throws EBusinessException {
        return dataProvider.findByOrden(ordenKey);
    }

    public List<EntyOrsplamddetalleequipooperacionDto> findByProyeccionSemana(
            final String proyeccionSemanaKey
    ) throws EBusinessException {
        return dataProvider.findByProyeccionSemana(proyeccionSemanaKey);
    }

    public List<EntyOrsplamddetalleequipooperacionDto> findByPlanSemanal(
            final String planSemanalKey
    ) throws EBusinessException {
        return dataProvider.findByPlanSemanal(planSemanalKey);
    }

    public List<EntyOrsplamddetalleequipooperacionDto> findByPunto(
            final String puntoKey
    ) throws EBusinessException {
        return dataProvider.findByPunto(puntoKey);
    }

    public List<EntyOrsplamddetalleequipooperacionDto> findByEquipo(
            final String equipoKey
    ) throws EBusinessException {
        return dataProvider.findByEquipo(equipoKey);
    }

    public List<EntyOrsplamddetalleequipooperacionDto> findByTipoEquipo(
            final String tipoEquipoKey
    ) throws EBusinessException {
        return dataProvider.findByTipoEquipo(tipoEquipoKey);
    }

    public List<EntyOrsplamddetalleequipooperacionDto> findByFechaTrabajo(
            final LocalDate fechaTrabajo
    ) throws EBusinessException {
        return dataProvider.findByFechaTrabajo(fechaTrabajo);
    }

    public List<EntyOrsplamddetalleequipooperacionDto> findByEstado(
            final String estado
    ) throws EBusinessException {
        return dataProvider.findByEstado(estado);
    }

    private void aplicarReglasNegocio(
            final EntyOrsplamddetalleequipooperacionDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw new EBusinessException("El detalle de equipo operación no puede ser nulo.");
        }

        aplicarDefaults(dto);
        normalizarTipoControl(dto);
        validarCamposObligatorios(dto);
        calcularCantidadEjecutada(dto);
        calcularValorEjecutado(dto);
    }

    private void aplicarDefaults(final EntyOrsplamddetalleequipooperacionDto dto) {

        if (dto.getOrsTiporegistDeop() == null) {
            dto.setOrsTiporegistDeop(TIPO_REGISTRO_ORIGINAL);
        }

        if (dto.getOrsEstadoregDeop() == null) {
            dto.setOrsEstadoregDeop(ESTADO_ACTIVO);
        }

        if (dto.getOrsFirmasuministroDeop() == null) {
            dto.setOrsFirmasuministroDeop(ESTADO_PENDIENTE_FIRMA);
        }

        if (dto.getOrsFirmaseguimientoDeop() == null) {
            dto.setOrsFirmaseguimientoDeop(ESTADO_PENDIENTE_FIRMA);
        }
    }

    private void normalizarTipoControl(
            final EntyOrsplamddetalleequipooperacionDto dto
    ) {

        if (dto.getOrsTipocontrolDeop() != null) {
            dto.setOrsTipocontrolDeop(dto.getOrsTipocontrolDeop().trim().toUpperCase());
        }

        if (dto.getOrsUnidadDeop() != null) {
            dto.setOrsUnidadDeop(dto.getOrsUnidadDeop().trim().toUpperCase());
        }
    }

    private void validarCamposObligatorios(
            final EntyOrsplamddetalleequipooperacionDto dto
    ) throws EBusinessException {

        if (esVacio(dto.getOrsIdentifkeyDeop())) {
            throw new EBusinessException("El código único del detalle de equipo es obligatorio.");
        }

        if (esVacio(dto.getOrsIdentifkeyRope())) {
            throw new EBusinessException("El código del reporte de operación es obligatorio.");
        }

        if (esVacio(dto.getOrsIdentifkeyOrde())) {
            throw new EBusinessException("El código de la orden de servicio es obligatorio.");
        }

        if (esVacio(dto.getOrsIdentifkeyPlse())) {
            throw new EBusinessException("El código del plan semanal es obligatorio.");
        }

        if (esVacio(dto.getPrvIdentifkeyInve())) {
            throw new EBusinessException("El código del equipo en inventario es obligatorio.");
        }

        if (esVacio(dto.getPrvTipoequipoTieq())) {
            throw new EBusinessException("El tipo de equipo es obligatorio.");
        }

        if (esVacio(dto.getOrsTipocontrolDeop())) {
            throw new EBusinessException("El tipo de control es obligatorio: HOROMETRO, KILOMETRAJE o DIA.");
        }

        if (dto.getOrsFechatrabajoDeop() == null) {
            throw new EBusinessException("La fecha de trabajo del equipo es obligatoria.");
        }

        if (dto.getOrsValorunidadDeop() == null) {
            throw new EBusinessException("El valor unitario del equipo es obligatorio.");
        }

        if (dto.getOrsValorunidadDeop().compareTo(BigDecimal.ZERO) <= 0) {
            throw new EBusinessException("El valor unitario del equipo debe ser mayor que cero.");
        }
    }

    private void calcularCantidadEjecutada(
            final EntyOrsplamddetalleequipooperacionDto dto
    ) throws EBusinessException {

        switch (dto.getOrsTipocontrolDeop()) {

            case TIPO_CONTROL_HOROMETRO:
                calcularHorasPorHorometro(dto);
                break;

            case TIPO_CONTROL_KILOMETRAJE:
                calcularKilometrosRecorridos(dto);
                break;

            case TIPO_CONTROL_DIA:
                validarDiaTrabajado(dto);
                break;

            default:
                throw new EBusinessException(
                        "Tipo de control no válido: " + dto.getOrsTipocontrolDeop()
                                + ". Valores permitidos: HOROMETRO, KILOMETRAJE, DIA."
                );
        }
    }

    private void calcularHorasPorHorometro(
            final EntyOrsplamddetalleequipooperacionDto dto
    ) throws EBusinessException {

        if (dto.getOrsHorometroiniDeop() == null) {
            throw new EBusinessException("El horómetro inicial es obligatorio.");
        }

        if (dto.getOrsHorometrofinDeop() == null) {
            throw new EBusinessException("El horómetro final es obligatorio.");
        }

        if (dto.getOrsHorometrofinDeop().compareTo(dto.getOrsHorometroiniDeop()) < 0) {
            throw new EBusinessException("El horómetro final no puede ser menor que el horómetro inicial.");
        }

        BigDecimal horasTrabajadas = dto.getOrsHorometrofinDeop()
                .subtract(dto.getOrsHorometroiniDeop());

        dto.setOrsHorastrabajadasDeop(horasTrabajadas);
        dto.setOrsKmrecorridoDeop(BigDecimal.ZERO);
        dto.setOrsDiatrabajadoDeop(BigDecimal.ZERO);
    }

    private void calcularKilometrosRecorridos(
            final EntyOrsplamddetalleequipooperacionDto dto
    ) throws EBusinessException {

        if (dto.getOrsKminicialDeop() == null) {
            throw new EBusinessException("El kilometraje inicial es obligatorio.");
        }

        if (dto.getOrsKmfinalDeop() == null) {
            throw new EBusinessException("El kilometraje final es obligatorio.");
        }

        if (dto.getOrsKmfinalDeop().compareTo(dto.getOrsKminicialDeop()) < 0) {
            throw new EBusinessException("El kilometraje final no puede ser menor que el kilometraje inicial.");
        }

        BigDecimal kilometrosRecorridos = dto.getOrsKmfinalDeop()
                .subtract(dto.getOrsKminicialDeop());

        dto.setOrsKmrecorridoDeop(kilometrosRecorridos);
        dto.setOrsHorastrabajadasDeop(BigDecimal.ZERO);
        dto.setOrsDiatrabajadoDeop(BigDecimal.ZERO);
    }

    private void validarDiaTrabajado(
            final EntyOrsplamddetalleequipooperacionDto dto
    ) throws EBusinessException {

        if (dto.getOrsDiatrabajadoDeop() == null) {
            throw new EBusinessException("El día trabajado es obligatorio.");
        }

        if (dto.getOrsDiatrabajadoDeop().compareTo(BigDecimal.ZERO) <= 0) {
            throw new EBusinessException("El día trabajado debe ser mayor que cero.");
        }

        dto.setOrsHorastrabajadasDeop(BigDecimal.ZERO);
        dto.setOrsKmrecorridoDeop(BigDecimal.ZERO);
    }

    private void calcularValorEjecutado(
            final EntyOrsplamddetalleequipooperacionDto dto
    ) throws EBusinessException {

        BigDecimal cantidadEjecutada;

        switch (dto.getOrsTipocontrolDeop()) {

            case TIPO_CONTROL_HOROMETRO:
                cantidadEjecutada = dto.getOrsHorastrabajadasDeop();
                break;

            case TIPO_CONTROL_KILOMETRAJE:
                cantidadEjecutada = dto.getOrsKmrecorridoDeop();
                break;

            case TIPO_CONTROL_DIA:
                cantidadEjecutada = dto.getOrsDiatrabajadoDeop();
                break;

            default:
                throw new EBusinessException("No se puede calcular el valor ejecutado. Tipo de control no válido.");
        }

        if (cantidadEjecutada == null) {
            throw new EBusinessException("No se pudo determinar la cantidad ejecutada del equipo.");
        }

        BigDecimal valorEjecutado = cantidadEjecutada.multiply(dto.getOrsValorunidadDeop());

        dto.setOrsValorejecutadoDeop(valorEjecutado);
    }

    private boolean esVacio(final String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}