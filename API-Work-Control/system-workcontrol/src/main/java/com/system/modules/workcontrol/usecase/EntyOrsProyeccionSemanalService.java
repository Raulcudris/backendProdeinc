package com.system.modules.workcontrol.usecase;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsProyeccionSemanalApiResponse;
import com.system.crosscutting.domain.model.EntyOrsProyeccionSemanalDetalleDto;
import com.system.crosscutting.domain.model.EntyOrsProyeccionSemanalPersistenciaResponse;
import com.system.crosscutting.domain.model.EntyOrsProyeccionSemanalRequestDto;
import com.system.crosscutting.domain.model.EntyOrsProyeccionSemanalResponseDto;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.persistence.entity.EntyOrsordmaordenservicio;
import com.system.crosscutting.persistence.entity.EntyOrsordmdproyecsemana;
import com.system.crosscutting.persistence.repository.EntyOrsordmaordenservicioRepository;
import com.system.crosscutting.persistence.repository.EntyOrsordmdproyecsemanaRepository;

@Service
public class EntyOrsProyeccionSemanalService {

    private static final DateTimeFormatter INPUT_DATE_FORMATTER =
            DateTimeFormatter
                    .ofPattern("dd-MM-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter OUTPUT_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final DateTimeFormatter OUTPUT_DAY_CODE_FORMATTER =
            DateTimeFormatter.ofPattern("yyMMdd");

    private static final String ESTADO_ABIERTO = "1";

    private static final String TIPO_REGISTRO_ORIGINAL = "1";

    private final EntyOrsordmaordenservicioRepository ordenRepository;

    private final EntyOrsordmdproyecsemanaRepository proyeccionRepository;

    public EntyOrsProyeccionSemanalService(
            final EntyOrsordmaordenservicioRepository ordenRepository,
            final EntyOrsordmdproyecsemanaRepository proyeccionRepository
    ) {
        this.ordenRepository = ordenRepository;
        this.proyeccionRepository = proyeccionRepository;
    }

    public EntyOrsProyeccionSemanalApiResponse calcular(
            final EntyOrsProyeccionSemanalRequestDto request
    ) {
        validarRequest(request);

        String ordenKey = request.getOrsIdentifkeyOrde()
                .trim()
                .toUpperCase();

        LocalDate fechaInicio = parseFechaObligatoria(
                request.getOrsPlanfechiniOrde(),
                "fecha inicial de la orden"
        );

        LocalDate fechaFin = parseFechaObligatoria(
                request.getOrsPlanfechfinOrde(),
                "fecha final de la orden"
        );

        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException(
                    "La fecha inicial no puede ser mayor que la fecha final."
            );
        }

        Set<LocalDate> fechasExcluidas = parseFechasExcluidas(
                request.getOrsDiasnhabilesOrde(),
                fechaInicio,
                fechaFin
        );

        EntyOrsProyeccionSemanalResponseDto parent =
                construirRespuestaPadre(
                        request,
                        fechaInicio,
                        fechaFin,
                        fechasExcluidas
                );

        parent.setVerDetails(
                calcularDetalleSemanal(
                        ordenKey,
                        fechaInicio,
                        fechaFin,
                        fechasExcluidas
                )
        );

        EntyOrsProyeccionSemanalApiResponse response =
                new EntyOrsProyeccionSemanalApiResponse();

        response.setRspValue("OK");
        response.setRspMessage("OK");
        response.setRspParentKey("NA");
        response.setRspAppKey("WORK-CONTROL");
        response.getRspData().add(parent);

        return response;
    }

    @Transactional
    public EntyOrsProyeccionSemanalPersistenciaResponse guardar(
            final EntyOrsProyeccionSemanalRequestDto request
    ) {
        validarTexto(
                request != null ? request.getOrsIdentifkeyOrde() : null,
                "El código de la orden de servicio es obligatorio."
        );

        String ordenKey = request.getOrsIdentifkeyOrde()
                .trim()
                .toUpperCase();

        EntyOrsordmaordenservicio orden = ordenRepository
                .findByOrsIdentifkeyOrde(ordenKey)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la orden de servicio: " + ordenKey
                ));

        if (orden.getOrsPlanfechiniOrde() == null
                || orden.getOrsPlanfechfinOrde() == null) {
            throw new IllegalArgumentException(
                    "La orden " + ordenKey
                            + " no tiene fechas de inicio y fin configuradas."
            );
        }

        if (ordenKey.length() > 30) {
            throw new IllegalArgumentException(
                    "El código de orden no puede superar 30 caracteres para la proyección semanal."
            );
        }

        if (proyeccionRepository
                .existsByOrsIdentifkeyOrdeAndOrsEstadoregPsem(
                        ordenKey,
                        ESTADO_ABIERTO
                )) {
            throw new IllegalArgumentException(
                    "La orden " + ordenKey
                            + " ya tiene una proyección semanal activa."
            );
        }

        EntyOrsProyeccionSemanalRequestDto requestNormalizado =
                construirRequestDesdeOrden(request, orden);

        EntyOrsProyeccionSemanalApiResponse calculo =
                calcular(requestNormalizado);

        EntyOrsProyeccionSemanalResponseDto parent =
                calculo.getRspData().get(0);

        List<EntyOrsordmdproyecsemana> entities =
                parent.getVerDetails()
                        .stream()
                        .map(detail -> toEntity(ordenKey, detail))
                        .collect(Collectors.toList());

        List<EntyOrsordmdproyecsemana> saved =
                proyeccionRepository.saveAll(entities);

        return buildPersistenciaResponse(
                ordenKey,
                "Proyección semanal guardada correctamente.",
                saved
        );
    }

    public EntyOrsProyeccionSemanalPersistenciaResponse consultarPorOrden(
            final String ordenKey
    ) {
        validarTexto(
                ordenKey,
                "El código de la orden de servicio es obligatorio."
        );

        String key = ordenKey.trim().toUpperCase();

        List<EntyOrsordmdproyecsemana> rows =
                proyeccionRepository
                        .findByOrsIdentifkeyOrdeOrderByOrsNumerosemPsemAsc(
                                key
                        );

        return buildPersistenciaResponse(
                key,
                "Proyección semanal consultada correctamente.",
                rows
        );
    }

    public EntyOrsProyeccionSemanalPersistenciaResponse consultarPorOrdenPaginado(
            final String ordenKey,
            final int currentPage,
            final int pageSize
    ) {
        validarTexto(
                ordenKey,
                "El código de la orden de servicio es obligatorio."
        );

        String key = ordenKey.trim().toUpperCase();

        int safeCurrentPage = currentPage <= 0 ? 0 : currentPage - 1;
        int safePageSize = pageSize <= 0 ? 10 : pageSize;

        var page = proyeccionRepository
                .findByOrsIdentifkeyOrdeOrderByOrsNumerosemPsemAsc(
                        key,
                        PageRequest.of(safeCurrentPage, safePageSize)
                );

        EntyOrsProyeccionSemanalPersistenciaResponse response =
                buildPersistenciaResponse(
                        key,
                        "Proyección semanal consultada correctamente.",
                        page.getContent()
                );

        response.setRspPagination(
                PaginationResponse
                        .builder()
                        .currentPage(currentPage)
                        .totalPageSize(safePageSize)
                        .totalResults(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .hasNextPage(page.hasNext())
                        .hasPreviousPage(page.hasPrevious())
                        .nextPageUrl("LocalHost")
                        .previousPageUrl("LocalHost")
                        .build()
        );

        return response;
    }

    private EntyOrsProyeccionSemanalRequestDto construirRequestDesdeOrden(
            final EntyOrsProyeccionSemanalRequestDto request,
            final EntyOrsordmaordenservicio orden
    ) {
        EntyOrsProyeccionSemanalRequestDto normalizado =
                new EntyOrsProyeccionSemanalRequestDto();

        normalizado.setOrsIdentifkeyOrde(
                orden.getOrsIdentifkeyOrde().trim().toUpperCase()
        );
        normalizado.setOrsPlanfechiniOrde(
                formatFechaSalida(orden.getOrsPlanfechiniOrde())
        );
        normalizado.setOrsPlanfechfinOrde(
                formatFechaSalida(orden.getOrsPlanfechfinOrde())
        );
        normalizado.setOrsDiasnhabilesOrde(
                request.getOrsDiasnhabilesOrde()
        );

        return normalizado;
    }

    private EntyOrsordmdproyecsemana toEntity(
            final String ordenKey,
            final EntyOrsProyeccionSemanalDetalleDto detail
    ) {
        EntyOrsordmdproyecsemana entity =
                new EntyOrsordmdproyecsemana();

        entity.setOrsIdentifkeyPsem(
                generarIdentifKeyPsem(
                        ordenKey,
                        detail.getOrsNumerosemPsem()
                )
        );
        entity.setOrsIdentifkeyOrde(ordenKey);
        entity.setOrsNumerosemPsem(detail.getOrsNumerosemPsem());
        entity.setOrsTitulosemPsem(detail.getOrsTitulosemPsem());
        entity.setOrsSemfechiniPsem(
                parseFecha(
                        detail.getOrsSemfechiniPsem(),
                        "fecha inicio semana"
                )
        );
        entity.setOrsSemfechfinPsem(
                parseFecha(
                        detail.getOrsSemfechfinPsem(),
                        "fecha fin semana"
                )
        );
        entity.setOrsDiashabilesPsem(detail.getOrsDiashabilesPsem());
        entity.setOrsDiasnhabilesPsem(detail.getOrsDiasnhabilesPsem());
        entity.setOrsTiporegistPsem(TIPO_REGISTRO_ORIGINAL);
        entity.setOrsEstadoregPsem(ESTADO_ABIERTO);

        return entity;
    }

    private EntyOrsProyeccionSemanalPersistenciaResponse buildPersistenciaResponse(
            final String ordenKey,
            final String message,
            final List<EntyOrsordmdproyecsemana> rows
    ) {
        List<EntyOrsordmdproyecsemana> safeRows =
                rows != null ? rows : new ArrayList<>();

        EntyOrsProyeccionSemanalResponseDto parent =
                new EntyOrsProyeccionSemanalResponseDto();

        parent.setOrsIdentifkeyOrde(ordenKey);
        parent.setVerDetails(
                safeRows
                        .stream()
                        .map(this::toDetalleDto)
                        .collect(Collectors.toList())
        );

        if (!safeRows.isEmpty()) {
            parent.setOrsPlanfechiniOrde(
                    formatFechaSalida(
                            safeRows.get(0).getOrsSemfechiniPsem()
                    )
            );
            parent.setOrsPlanfechfinOrde(
                    formatFechaSalida(
                            safeRows.get(safeRows.size() - 1)
                                    .getOrsSemfechfinPsem()
                    )
            );
            parent.setOrsDiasnhabilesOrde(
                    safeRows
                            .stream()
                            .map(EntyOrsordmdproyecsemana::getOrsDiasnhabilesPsem)
                            .filter(value -> value != null
                                    && !value.trim().isEmpty())
                            .collect(Collectors.joining(","))
            );
        } else {
            parent.setOrsPlanfechiniOrde("");
            parent.setOrsPlanfechfinOrde("");
            parent.setOrsDiasnhabilesOrde("");
        }

        EntyOrsProyeccionSemanalPersistenciaResponse response =
                new EntyOrsProyeccionSemanalPersistenciaResponse();

        response.setRspValue("OK");
        response.setRspMessage(message);
        response.setRspParentKey(ordenKey);
        response.setRspAppKey("WORK-CONTROL");
        response.setRspPagination(
                buildPaginationResponse(
                        safeRows.size(),
                        safeRows.size()
                )
        );
        response.getRspData().add(parent);

        return response;
    }

    private EntyOrsProyeccionSemanalDetalleDto toDetalleDto(
            final EntyOrsordmdproyecsemana entity
    ) {
        EntyOrsProyeccionSemanalDetalleDto detail =
                new EntyOrsProyeccionSemanalDetalleDto();

        detail.setOrsIdentifkeyPsem(entity.getOrsIdentifkeyPsem());
        detail.setOrsNumerosemPsem(entity.getOrsNumerosemPsem());
        detail.setOrsTitulosemPsem(entity.getOrsTitulosemPsem());
        detail.setOrsSemfechiniPsem(
                formatFechaSalida(entity.getOrsSemfechiniPsem())
        );
        detail.setOrsSemfechfinPsem(
                formatFechaSalida(entity.getOrsSemfechfinPsem())
        );
        detail.setOrsDiashabilesPsem(entity.getOrsDiashabilesPsem());
        detail.setOrsDiasnhabilesPsem(entity.getOrsDiasnhabilesPsem());

        return detail;
    }

    private PaginationResponse buildPaginationResponse(
            final int pageSize,
            final int totalResults
    ) {
        return PaginationResponse
                .builder()
                .currentPage(1)
                .totalPageSize(pageSize)
                .totalResults(Long.valueOf(totalResults))
                .totalPages(totalResults > 0 ? 1 : 0)
                .hasNextPage(false)
                .hasPreviousPage(false)
                .nextPageUrl("LocalHost")
                .previousPageUrl("LocalHost")
                .build();
    }

    private void validarRequest(
            final EntyOrsProyeccionSemanalRequestDto request
    ) {
        if (request == null) {
            throw new IllegalArgumentException(
                    "La solicitud de proyección semanal es obligatoria."
            );
        }

        validarTexto(
                request.getOrsIdentifkeyOrde(),
                "El código de la orden de servicio es obligatorio."
        );

        validarTexto(
                request.getOrsPlanfechiniOrde(),
                "La fecha inicial de la orden es obligatoria."
        );

        validarTexto(
                request.getOrsPlanfechfinOrde(),
                "La fecha final de la orden es obligatoria."
        );
    }

    private EntyOrsProyeccionSemanalResponseDto construirRespuestaPadre(
            final EntyOrsProyeccionSemanalRequestDto request,
            final LocalDate fechaInicio,
            final LocalDate fechaFin,
            final Set<LocalDate> fechasExcluidas
    ) {
        EntyOrsProyeccionSemanalResponseDto parent =
                new EntyOrsProyeccionSemanalResponseDto();

        parent.setOrsIdentifkeyOrde(
                request.getOrsIdentifkeyOrde().trim().toUpperCase()
        );
        parent.setOrsPlanfechiniOrde(formatFechaSalida(fechaInicio));
        parent.setOrsPlanfechfinOrde(formatFechaSalida(fechaFin));
        parent.setOrsDiasnhabilesOrde(
                fechasExcluidas
                        .stream()
                        .sorted()
                        .map(this::formatCodigoDia)
                        .collect(Collectors.joining(","))
        );

        return parent;
    }

    private List<EntyOrsProyeccionSemanalDetalleDto> calcularDetalleSemanal(
            final String ordenKey,
            final LocalDate fechaInicio,
            final LocalDate fechaFin,
            final Set<LocalDate> fechasExcluidas
    ) {
        List<EntyOrsProyeccionSemanalDetalleDto> semanas =
                new ArrayList<>();

        LocalDate currentDate = fechaInicio;
        int numeroSemana = 1;

        while (!currentDate.isAfter(fechaFin)) {
            LocalDate semanaInicio = currentDate;

            LocalDate semanaFin = currentDate.with(
                    TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)
            );

            if (semanaFin.isAfter(fechaFin)) {
                semanaFin = fechaFin;
            }

            List<String> diasHabiles = new ArrayList<>();
            List<String> diasNoHabiles = new ArrayList<>();

            LocalDate cursorDia = semanaInicio;

            while (!cursorDia.isAfter(semanaFin)) {
                String codigoDia = formatCodigoDia(cursorDia);

                if (fechasExcluidas.contains(cursorDia)) {
                    diasNoHabiles.add(codigoDia);
                } else {
                    diasHabiles.add(codigoDia);
                }

                cursorDia = cursorDia.plusDays(1);
            }

            EntyOrsProyeccionSemanalDetalleDto detalle =
                    new EntyOrsProyeccionSemanalDetalleDto();

            detalle.setOrsIdentifkeyPsem(
                    generarIdentifKeyPsem(
                            ordenKey,
                            numeroSemana
                    )
            );
            detalle.setOrsNumerosemPsem(numeroSemana);
            detalle.setOrsTitulosemPsem(
                    construirTituloSemana(
                            numeroSemana,
                            semanaInicio,
                            semanaFin
                    )
            );
            detalle.setOrsSemfechiniPsem(formatFechaSalida(semanaInicio));
            detalle.setOrsSemfechfinPsem(formatFechaSalida(semanaFin));
            detalle.setOrsDiashabilesPsem(String.join(",", diasHabiles));
            detalle.setOrsDiasnhabilesPsem(String.join(",", diasNoHabiles));

            semanas.add(detalle);

            currentDate = semanaFin.plusDays(1);
            numeroSemana++;
        }

        return semanas;
    }

    private Set<LocalDate> parseFechasExcluidas(
            final String fechasExcluidasTexto,
            final LocalDate fechaInicio,
            final LocalDate fechaFin
    ) {
        Set<LocalDate> fechas = new LinkedHashSet<>();

        if (fechasExcluidasTexto == null
                || fechasExcluidasTexto.trim().isEmpty()) {
            return fechas;
        }

        String[] valores = fechasExcluidasTexto.split(",");

        for (String valor : valores) {
            String fechaTexto = valor == null ? "" : valor.trim();

            if (fechaTexto.isEmpty()) {
                continue;
            }

            LocalDate fechaExcluida =
                    parseFecha(fechaTexto, "fecha excluida");

            if (fechaExcluida.isBefore(fechaInicio)
                    || fechaExcluida.isAfter(fechaFin)) {
                continue;
            }

            fechas.add(fechaExcluida);
        }

        return fechas;
    }

    private LocalDate parseFechaObligatoria(
            final String value,
            final String campo
    ) {
        validarTexto(
                value,
                "La " + campo + " es obligatoria."
        );

        return parseFecha(value, campo);
    }

    private LocalDate parseFecha(
            final String value,
            final String campo
    ) {
        try {
            return LocalDate.parse(value.trim(), INPUT_DATE_FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(
                    "Formato inválido en " + campo
                            + ": " + value
                            + ". Formato esperado: dd-MM-yyyy."
            );
        }
    }

    private void validarTexto(
            final String value,
            final String mensaje
    ) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    private String construirTituloSemana(
            final int numeroSemana,
            final LocalDate fechaInicio,
            final LocalDate fechaFin
    ) {
        return "Semana "
                + numeroSemana
                + " del "
                + fechaInicio.getDayOfMonth()
                + " de "
                + nombreMes(fechaInicio)
                + " al "
                + fechaFin.getDayOfMonth()
                + " de "
                + nombreMes(fechaFin);
    }

    private String nombreMes(final LocalDate fecha) {
        switch (fecha.getMonth()) {
            case JANUARY:
                return "enero";
            case FEBRUARY:
                return "febrero";
            case MARCH:
                return "marzo";
            case APRIL:
                return "abril";
            case MAY:
                return "mayo";
            case JUNE:
                return "junio";
            case JULY:
                return "julio";
            case AUGUST:
                return "agosto";
            case SEPTEMBER:
                return "septiembre";
            case OCTOBER:
                return "octubre";
            case NOVEMBER:
                return "noviembre";
            case DECEMBER:
                return "diciembre";
            default:
                return "";
        }
    }

    private String formatFechaSalida(final LocalDate fecha) {
        if (fecha == null) {
            return "";
        }

        return fecha.format(OUTPUT_DATE_FORMATTER);
    }

    private String formatCodigoDia(final LocalDate fecha) {
        return fecha.format(OUTPUT_DAY_CODE_FORMATTER);
    }

    private String generarIdentifKeyPsem(
            final String ordenKey,
            final Integer numeroSemana
    ) {
        String cleanOrder = ordenKey
                .toUpperCase()
                .replaceAll("[^A-Z0-9]", "");

        String suffix = "-"
                + String.format(
                "%02d",
                numeroSemana != null ? numeroSemana : 0
        );

        int maxOrderLength = 30 - "PSEM-".length() - suffix.length();

        if (cleanOrder.length() > maxOrderLength) {
            cleanOrder = cleanOrder.substring(0, maxOrderLength);
        }

        return "PSEM-" + cleanOrder + suffix;
    }
}