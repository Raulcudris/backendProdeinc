package com.system.modules.workcontrol.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioDto;
import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsordmdproyecsemana;
import com.system.crosscutting.persistence.entity.EntyOrsplamdplantrabsemana;
import com.system.crosscutting.persistence.repository.EntyOrsordmaordenservicioRepository;
import com.system.crosscutting.persistence.repository.EntyOrsordmdproyecsemanaRepository;
import com.system.crosscutting.persistence.repository.EntyOrsplamdplantrabsemanaRepository;
import com.system.modules.workcontrol.dataproviders.IjpaReporteDiarioDataProviders;

@Service
public class EntyReporteDiarioService {

    private static final String ESTADO_ABIERTO = "1";

    private static final String ESTADO_CERRADO = "2";

    private static final String ESTADO_CANCELADO = "3";

    private static final String TIPO_REGISTRO_ORIGINAL = "1";

    private static final int MAX_KEY = 30;

    private static final int MAX_OBSERVACION = 150;

    private final IjpaReporteDiarioDataProviders dataProviders;

    private final EntyOrsordmaordenservicioRepository ordenRepository;

    private final EntyOrsplamdplantrabsemanaRepository planSemanaRepository;

    private final EntyOrsordmdproyecsemanaRepository semanaRepository;

    public EntyReporteDiarioService(
            final IjpaReporteDiarioDataProviders dataProviders,
            final EntyOrsordmaordenservicioRepository ordenRepository,
            final EntyOrsplamdplantrabsemanaRepository planSemanaRepository,
            final EntyOrsordmdproyecsemanaRepository semanaRepository
    ) {
        this.dataProviders = dataProviders;
        this.ordenRepository = ordenRepository;
        this.planSemanaRepository = planSemanaRepository;
        this.semanaRepository = semanaRepository;
    }

    @Transactional
    public EntyOrsplamdreportediarioResponse createResponse(
            final EntyOrsplamdreportediarioDto request
    ) throws EBusinessException {
        EntyOrsplamdreportediarioDto normalized =
                normalizeForCreate(request);

        EntyOrsplamdreportediarioDto saved =
                dataProviders.save(normalized);

        actualizarAcumuladoPlanSemana(saved.getOrsIdentifkeyPlse());

        return buildSingleResponse(
                saved,
                "Reporte diario creado correctamente.",
                saved.getOrsIdentifkeyOrde()
        );
    }

    public EntyOrsplamdreportediarioResponse getByOrdenResponse(
            final String ordenKey
    ) throws EBusinessException {
        validarTexto(
                ordenKey,
                "El código de la orden de servicio es obligatorio."
        );

        return dataProviders.findByOrdenResponse(
                ordenKey.trim().toUpperCase()
        );
    }

    public EntyOrsplamdreportediarioResponse getByPlanSemanaResponse(
            final String planSemanaKey
    ) throws EBusinessException {
        validarTexto(
                planSemanaKey,
                "El código del plan semanal es obligatorio."
        );

        return dataProviders.findByPlanSemanaResponse(
                planSemanaKey.trim().toUpperCase()
        );
    }

    public EntyOrsplamdreportediarioResponse getBySemanaResponse(
            final String semanaKey
    ) throws EBusinessException {
        validarTexto(
                semanaKey,
                "El código de la semana proyectada es obligatorio."
        );

        return dataProviders.findBySemanaResponse(
                semanaKey.trim().toUpperCase()
        );
    }

    public EntyOrsplamdreportediarioResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyOrsplamdreportediarioResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        return dataProviders.getAll(
                currentPage,
                pageSize,
                parameter,
                filter
        );
    }

    public EntyOrsplamdreportediarioResponse getResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsplamdreportediarioDto dto =
                dataProviders.get(id);

        if (dto.getOrsPrimarykeyPdia() == null) {
            return buildListResponse(
                    new ArrayList<>(),
                    "No se encontró el reporte diario.",
                    "NA"
            );
        }

        return buildSingleResponse(
                dto,
                "Reporte diario consultado correctamente.",
                dto.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsplamdreportediarioResponse updateResponse(
            final Integer id,
            final EntyOrsplamdreportediarioDto request
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID del reporte diario es obligatorio."
            );
        }

        EntyOrsplamdreportediarioDto current =
                dataProviders.get(id);

        if (current.getOrsPrimarykeyPdia() == null) {
            throw new IllegalArgumentException(
                    "No existe el reporte diario con ID: " + id
            );
        }

        String previousPlanSemanaKey = current.getOrsIdentifkeyPlse();

        EntyOrsplamdreportediarioDto normalized =
                normalizeForUpdate(request, current);

        EntyOrsplamdreportediarioDto updated =
                dataProviders.update(id, normalized);

        actualizarAcumuladoPlanSemana(previousPlanSemanaKey);
        actualizarAcumuladoPlanSemana(updated.getOrsIdentifkeyPlse());

        return buildSingleResponse(
                updated,
                "Reporte diario actualizado correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsplamdreportediarioResponse cerrarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsplamdreportediarioDto current =
                getCurrentOrFail(id);

        current.setOrsEstadoregPdia(ESTADO_CERRADO);

        EntyOrsplamdreportediarioDto updated =
                dataProviders.update(id, current);

        actualizarAcumuladoPlanSemana(updated.getOrsIdentifkeyPlse());

        return buildSingleResponse(
                updated,
                "Reporte diario cerrado correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsplamdreportediarioResponse cancelarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsplamdreportediarioDto current =
                getCurrentOrFail(id);

        current.setOrsEstadoregPdia(ESTADO_CANCELADO);

        EntyOrsplamdreportediarioDto updated =
                dataProviders.update(id, current);

        actualizarAcumuladoPlanSemana(updated.getOrsIdentifkeyPlse());

        return buildSingleResponse(
                updated,
                "Reporte diario cancelado correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsplamdreportediarioResponse deleteResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsplamdreportediarioDto current =
                getCurrentOrFail(id);

        String planSemanaKey = current.getOrsIdentifkeyPlse();

        dataProviders.delete(id);

        actualizarAcumuladoPlanSemana(planSemanaKey);

        return buildSingleResponse(
                current,
                "Reporte diario eliminado correctamente.",
                current.getOrsIdentifkeyOrde()
        );
    }

    private EntyOrsplamdreportediarioDto getCurrentOrFail(
            final Integer id
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID del reporte diario es obligatorio."
            );
        }

        EntyOrsplamdreportediarioDto current =
                dataProviders.get(id);

        if (current.getOrsPrimarykeyPdia() == null) {
            throw new IllegalArgumentException(
                    "No existe el reporte diario con ID: " + id
            );
        }

        return current;
    }

    private EntyOrsplamdreportediarioDto normalizeForCreate(
            final EntyOrsplamdreportediarioDto request
    ) throws EBusinessException {
        validarRequestBase(request, null);

        EntyOrsplamdreportediarioDto dto =
                normalizeCommon(request);

        dto.setOrsPrimarykeyPdia(null);

        if (dto.getOrsIdentifkeyPdia() == null
                || dto.getOrsIdentifkeyPdia().trim().isEmpty()) {
            dto.setOrsIdentifkeyPdia(
                    generarReporteKey(dto.getOrsIdentifkeyOrde())
            );
        }

        dto.setOrsIdentifkeyPdia(
                dto.getOrsIdentifkeyPdia().trim().toUpperCase()
        );

        if (dataProviders.existsByReporteKey(dto.getOrsIdentifkeyPdia())) {
            throw new IllegalArgumentException(
                    "Ya existe un reporte diario con el código: "
                            + dto.getOrsIdentifkeyPdia()
            );
        }

        dto.setOrsFechasistemaPdia(LocalDate.now());
        dto.setOrsTiporegistPdia(TIPO_REGISTRO_ORIGINAL);
        dto.setOrsEstadoregPdia(ESTADO_ABIERTO);

        return dto;
    }

    private EntyOrsplamdreportediarioDto normalizeForUpdate(
            final EntyOrsplamdreportediarioDto request,
            final EntyOrsplamdreportediarioDto current
    ) throws EBusinessException {
        validarRequestBase(request, current.getOrsPrimarykeyPdia());

        EntyOrsplamdreportediarioDto dto =
                normalizeCommon(request);

        dto.setOrsPrimarykeyPdia(current.getOrsPrimarykeyPdia());
        dto.setOrsIdentifkeyPdia(current.getOrsIdentifkeyPdia());

        dto.setOrsFechasistemaPdia(
                current.getOrsFechasistemaPdia() != null
                        ? current.getOrsFechasistemaPdia()
                        : LocalDate.now()
        );

        if (dto.getOrsTiporegistPdia() == null
                || dto.getOrsTiporegistPdia().trim().isEmpty()) {
            dto.setOrsTiporegistPdia(
                    current.getOrsTiporegistPdia() != null
                            ? current.getOrsTiporegistPdia()
                            : TIPO_REGISTRO_ORIGINAL
            );
        }

        if (dto.getOrsEstadoregPdia() == null
                || dto.getOrsEstadoregPdia().trim().isEmpty()) {
            dto.setOrsEstadoregPdia(
                    current.getOrsEstadoregPdia() != null
                            ? current.getOrsEstadoregPdia()
                            : ESTADO_ABIERTO
            );
        }

        return dto;
    }

    private EntyOrsplamdreportediarioDto normalizeCommon(
            final EntyOrsplamdreportediarioDto request
    ) {
        EntyOrsplamdreportediarioDto dto =
                new EntyOrsplamdreportediarioDto();

        dto.setOrsPrimarykeyPdia(request.getOrsPrimarykeyPdia());
        dto.setOrsIdentifkeyPdia(limpiarMayuscula(request.getOrsIdentifkeyPdia()));
        dto.setOrsIdentifkeyOrde(limpiarMayuscula(request.getOrsIdentifkeyOrde()));
        dto.setOrsIdentifkeyPlse(limpiarMayuscula(request.getOrsIdentifkeyPlse()));
        dto.setOrsIdentifkeyPsem(limpiarMayuscula(request.getOrsIdentifkeyPsem()));
        dto.setOrsObservacionPdia(limpiarTexto(request.getOrsObservacionPdia()));
        dto.setOrsFechareportPdia(request.getOrsFechareportPdia());
        dto.setOrsEjecutunidadPdia(request.getOrsEjecutunidadPdia());
        dto.setOrsFechasistemaPdia(request.getOrsFechasistemaPdia());
        dto.setOrsTiporegistPdia(limpiarMayuscula(request.getOrsTiporegistPdia()));
        dto.setOrsEstadoregPdia(limpiarMayuscula(request.getOrsEstadoregPdia()));

        validarLongitud(dto.getOrsIdentifkeyPdia(), MAX_KEY, "código del reporte diario");
        validarLongitud(dto.getOrsIdentifkeyOrde(), MAX_KEY, "código de la orden");
        validarLongitud(dto.getOrsIdentifkeyPlse(), MAX_KEY, "código del plan semanal");
        validarLongitud(dto.getOrsIdentifkeyPsem(), MAX_KEY, "código de la semana proyectada");
        validarLongitud(dto.getOrsObservacionPdia(), MAX_OBSERVACION, "observación");

        if (dto.getOrsEjecutunidadPdia() != null
                && dto.getOrsEjecutunidadPdia() < 0) {
            throw new IllegalArgumentException(
                    "La cantidad ejecutada no puede ser negativa."
            );
        }

        return dto;
    }

    private void validarRequestBase(
            final EntyOrsplamdreportediarioDto request,
            final Integer excludeId
    ) throws EBusinessException {
        if (request == null) {
            throw new IllegalArgumentException(
                    "La solicitud del reporte diario es obligatoria."
            );
        }

        validarTexto(
                request.getOrsIdentifkeyOrde(),
                "El código de la orden de servicio es obligatorio."
        );

        validarTexto(
                request.getOrsIdentifkeyPlse(),
                "El código del plan semanal es obligatorio."
        );

        validarTexto(
                request.getOrsIdentifkeyPsem(),
                "El código de la semana proyectada es obligatorio."
        );

        if (request.getOrsFechareportPdia() == null) {
            throw new IllegalArgumentException(
                    "La fecha del reporte diario es obligatoria."
            );
        }

        String ordenKey = request.getOrsIdentifkeyOrde()
                .trim()
                .toUpperCase();

        String planSemanaKey = request.getOrsIdentifkeyPlse()
                .trim()
                .toUpperCase();

        String semanaKey = request.getOrsIdentifkeyPsem()
                .trim()
                .toUpperCase();

        if (!ordenRepository.existsByOrsIdentifkeyOrde(ordenKey)) {
            throw new IllegalArgumentException(
                    "No existe la orden de servicio: " + ordenKey
            );
        }

        EntyOrsplamdplantrabsemana planSemana =
                planSemanaRepository
                        .findByOrsIdentifkeyPlse(planSemanaKey)
                        .orElse(null);

        if (planSemana == null) {
            throw new IllegalArgumentException(
                    "No existe el plan semanal: " + planSemanaKey
            );
        }

        if (!ordenKey.equalsIgnoreCase(planSemana.getOrsIdentifkeyOrde())) {
            throw new IllegalArgumentException(
                    "El plan semanal " + planSemanaKey
                            + " no pertenece a la orden " + ordenKey + "."
            );
        }

        if (!semanaKey.equalsIgnoreCase(planSemana.getOrsIdentifkeyPsem())) {
            throw new IllegalArgumentException(
                    "La semana " + semanaKey
                            + " no corresponde al plan semanal "
                            + planSemanaKey + "."
            );
        }

        validarFechaDentroSemana(
                ordenKey,
                semanaKey,
                request.getOrsFechareportPdia()
        );

        validarNoDuplicado(
                planSemanaKey,
                request.getOrsFechareportPdia(),
                excludeId
        );
    }

    private void validarFechaDentroSemana(
            final String ordenKey,
            final String semanaKey,
            final LocalDate fechaReporte
    ) {
        EntyOrsordmdproyecsemana semana =
                semanaRepository
                        .findByOrsIdentifkeyOrdeOrderByOrsNumerosemPsemAsc(
                                ordenKey
                        )
                        .stream()
                        .filter(row -> semanaKey.equalsIgnoreCase(
                                row.getOrsIdentifkeyPsem()
                        ))
                        .findFirst()
                        .orElse(null);

        if (semana == null) {
            throw new IllegalArgumentException(
                    "No existe la semana " + semanaKey
                            + " para la orden " + ordenKey + "."
            );
        }

        if (fechaReporte.isBefore(semana.getOrsSemfechiniPsem())
                || fechaReporte.isAfter(semana.getOrsSemfechfinPsem())) {
            throw new IllegalArgumentException(
                    "La fecha del reporte diario debe estar dentro del rango de la semana "
                            + semanaKey + "."
            );
        }
    }

    private void validarNoDuplicado(
            final String planSemanaKey,
            final LocalDate fechaReporte,
            final Integer excludeId
    ) throws EBusinessException {
        List<EntyOrsplamdreportediarioDto> reportes =
                dataProviders.findByPlanSemana(planSemanaKey);

        boolean duplicado = reportes
                .stream()
                .anyMatch(row ->
                        fechaReporte.equals(row.getOrsFechareportPdia())
                                && !ESTADO_CANCELADO.equals(row.getOrsEstadoregPdia())
                                && !Objects.equals(row.getOrsPrimarykeyPdia(), excludeId)
                );

        if (duplicado) {
            throw new IllegalArgumentException(
                    "Ya existe un reporte diario válido para el plan semanal "
                            + planSemanaKey
                            + " en la fecha "
                            + fechaReporte
                            + "."
            );
        }
    }

    private void actualizarAcumuladoPlanSemana(
            final String planSemanaKey
    ) throws EBusinessException {
        if (planSemanaKey == null || planSemanaKey.trim().isEmpty()) {
            return;
        }

        String key = planSemanaKey.trim().toUpperCase();

        EntyOrsplamdplantrabsemana planSemana =
                planSemanaRepository
                        .findByOrsIdentifkeyPlse(key)
                        .orElse(null);

        if (planSemana == null) {
            return;
        }

        Long totalEjecutado =
                dataProviders.sumEjecutadoValidoByPlanSemana(key);

        int ejecutado = totalEjecutado != null
                ? totalEjecutado.intValue()
                : 0;

        planSemana.setOrsEjecutunidadPlse(ejecutado);

        if (planSemana.getOrsValorunidadPlse() != null) {
            planSemana.setOrsValorejecutPlse(
                    planSemana.getOrsValorunidadPlse()
                            .multiply(BigDecimal.valueOf(ejecutado))
            );
        } else {
            planSemana.setOrsValorejecutPlse(BigDecimal.ZERO);
        }

        planSemanaRepository.save(planSemana);
    }

    private String generarReporteKey(
            final String ordenKey
    ) throws EBusinessException {
        List<EntyOrsplamdreportediarioDto> existentes =
                dataProviders.findByOrden(ordenKey);

        String cleanOrder = ordenKey
                .toUpperCase()
                .replaceAll("[^A-Z0-9]", "");

        int maxOrderLength = 30 - "PDIA-".length() - "-000".length();

        if (cleanOrder.length() > maxOrderLength) {
            cleanOrder = cleanOrder.substring(0, maxOrderLength);
        }

        int consecutivo = existentes.size() + 1;

        String generated;

        do {
            generated = "PDIA-"
                    + cleanOrder
                    + "-"
                    + String.format("%03d", consecutivo);

            consecutivo++;
        } while (dataProviders.existsByReporteKey(generated));

        return generated;
    }

    private EntyOrsplamdreportediarioResponse buildSingleResponse(
            final EntyOrsplamdreportediarioDto dto,
            final String message,
            final String parentKey
    ) {
        List<EntyOrsplamdreportediarioDto> data =
                new ArrayList<>();

        if (dto != null) {
            data.add(dto);
        }

        return buildListResponse(data, message, parentKey);
    }

    private EntyOrsplamdreportediarioResponse buildListResponse(
            final List<EntyOrsplamdreportediarioDto> data,
            final String message,
            final String parentKey
    ) {
        List<EntyOrsplamdreportediarioDto> safeData =
                data != null ? data : new ArrayList<>();

        EntyOrsplamdreportediarioResponse response =
                new EntyOrsplamdreportediarioResponse();

        response.setRspValue("OK");
        response.setRspMessage(message);
        response.setRspParentKey(parentKey != null ? parentKey : "NA");
        response.setRspAppKey("WORK-CONTROL");
        response.setRspData(safeData);
        response.setRspPagination(
                PaginationResponse
                        .builder()
                        .currentPage(1)
                        .totalPageSize(safeData.size())
                        .totalResults(Long.valueOf(safeData.size()))
                        .totalPages(safeData.isEmpty() ? 0 : 1)
                        .hasNextPage(false)
                        .hasPreviousPage(false)
                        .nextPageUrl("LocalHost")
                        .previousPageUrl("LocalHost")
                        .build()
        );

        return response;
    }

    private void validarTexto(
            final String value,
            final String message
    ) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validarLongitud(
            final String value,
            final int max,
            final String fieldName
    ) {
        if (value != null && value.length() > max) {
            throw new IllegalArgumentException(
                    "El campo " + fieldName
                            + " no puede superar "
                            + max
                            + " caracteres."
            );
        }
    }

    private String limpiarTexto(
            final String value
    ) {
        if (value == null) {
            return null;
        }

        String clean = value.trim();

        return clean.isEmpty() ? null : clean;
    }

    private String limpiarMayuscula(
            final String value
    ) {
        String clean = limpiarTexto(value);

        return clean == null ? null : clean.toUpperCase();
    }
}