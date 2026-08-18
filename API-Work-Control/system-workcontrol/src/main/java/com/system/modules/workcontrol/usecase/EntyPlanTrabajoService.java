package com.system.modules.workcontrol.usecase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoDto;
import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyOrsordmaordenservicioRepository;
import com.system.crosscutting.persistence.repository.EntyOrsordmdsitiospuntosRepository;
import com.system.modules.workcontrol.dataproviders.IjpaPlanTrabajoDataProviders;

@Service
public class EntyPlanTrabajoService {

    private static final String ESTADO_ABIERTO = "1";

    private static final String ESTADO_CERRADO = "2";

    private static final String ESTADO_CANCELADO = "3";

    private static final String TIPO_REGISTRO_ORIGINAL = "1";

    private static final int MAX_KEY = 30;

    private static final int MAX_ACTIVIDAD = 250;

    private static final int MAX_INVENTARIO = 10;

    private final IjpaPlanTrabajoDataProviders dataProviders;

    private final EntyOrsordmaordenservicioRepository ordenRepository;

    private final EntyOrsordmdsitiospuntosRepository puntoRepository;

    public EntyPlanTrabajoService(
            final IjpaPlanTrabajoDataProviders dataProviders,
            final EntyOrsordmaordenservicioRepository ordenRepository,
            final EntyOrsordmdsitiospuntosRepository puntoRepository
    ) {
        this.dataProviders = dataProviders;
        this.ordenRepository = ordenRepository;
        this.puntoRepository = puntoRepository;
    }

    @Transactional
    public EntyOrsplamaplandetrabajoResponse createResponse(
            final EntyOrsplamaplandetrabajoDto request
    ) throws EBusinessException {
        EntyOrsplamaplandetrabajoDto normalized =
                normalizeForCreate(request);

        EntyOrsplamaplandetrabajoDto saved =
                dataProviders.save(normalized);

        return buildSingleResponse(
                saved,
                "Plan de trabajo proyectado creado correctamente.",
                saved.getOrsIdentifkeyOrde()
        );
    }

    public EntyOrsplamaplandetrabajoResponse getByOrdenResponse(
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

    public EntyOrsplamaplandetrabajoResponse getByPuntoResponse(
            final String puntoKey
    ) throws EBusinessException {
        validarTexto(
                puntoKey,
                "El código del sitio o punto es obligatorio."
        );

        return dataProviders.findByPuntoResponse(
                puntoKey.trim().toUpperCase()
        );
    }

    public EntyOrsplamaplandetrabajoResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyOrsplamaplandetrabajoResponse getAll(
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

    public EntyOrsplamaplandetrabajoResponse getResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsplamaplandetrabajoDto dto =
                dataProviders.get(id);

        if (dto.getOrsPrimarykeyPltr() == null) {
            return buildListResponse(
                    new ArrayList<>(),
                    "No se encontró el plan de trabajo proyectado.",
                    "NA"
            );
        }

        return buildSingleResponse(
                dto,
                "Plan de trabajo proyectado consultado correctamente.",
                dto.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsplamaplandetrabajoResponse updateResponse(
            final Integer id,
            final EntyOrsplamaplandetrabajoDto request
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID del plan de trabajo es obligatorio."
            );
        }

        EntyOrsplamaplandetrabajoDto current =
                dataProviders.get(id);

        if (current.getOrsPrimarykeyPltr() == null) {
            throw new IllegalArgumentException(
                    "No existe el plan de trabajo con ID: " + id
            );
        }

        EntyOrsplamaplandetrabajoDto normalized =
                normalizeForUpdate(request, current);

        EntyOrsplamaplandetrabajoDto updated =
                dataProviders.update(id, normalized);

        return buildSingleResponse(
                updated,
                "Plan de trabajo proyectado actualizado correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsplamaplandetrabajoResponse cerrarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsplamaplandetrabajoDto current =
                getCurrentOrFail(id);

        current.setOrsEstadoregPltr(ESTADO_CERRADO);

        EntyOrsplamaplandetrabajoDto updated =
                dataProviders.update(id, current);

        return buildSingleResponse(
                updated,
                "Plan de trabajo proyectado cerrado correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsplamaplandetrabajoResponse cancelarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsplamaplandetrabajoDto current =
                getCurrentOrFail(id);

        current.setOrsEstadoregPltr(ESTADO_CANCELADO);

        EntyOrsplamaplandetrabajoDto updated =
                dataProviders.update(id, current);

        return buildSingleResponse(
                updated,
                "Plan de trabajo proyectado cancelado correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsplamaplandetrabajoResponse deleteResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsplamaplandetrabajoDto current =
                getCurrentOrFail(id);

        dataProviders.delete(id);

        return buildSingleResponse(
                current,
                "Plan de trabajo proyectado eliminado correctamente.",
                current.getOrsIdentifkeyOrde()
        );
    }

    private EntyOrsplamaplandetrabajoDto getCurrentOrFail(
            final Integer id
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID del plan de trabajo es obligatorio."
            );
        }

        EntyOrsplamaplandetrabajoDto current =
                dataProviders.get(id);

        if (current.getOrsPrimarykeyPltr() == null) {
            throw new IllegalArgumentException(
                    "No existe el plan de trabajo con ID: " + id
            );
        }

        return current;
    }

    private EntyOrsplamaplandetrabajoDto normalizeForCreate(
            final EntyOrsplamaplandetrabajoDto request
    ) throws EBusinessException {
        validarRequestBase(request);

        EntyOrsplamaplandetrabajoDto dto =
                normalizeCommon(request);

        dto.setOrsPrimarykeyPltr(null);

        if (dto.getOrsIdentifkeyPltr() == null
                || dto.getOrsIdentifkeyPltr().trim().isEmpty()) {
            dto.setOrsIdentifkeyPltr(
                    generarPlanKey(dto.getOrsIdentifkeyOrde())
            );
        }

        dto.setOrsIdentifkeyPltr(
                dto.getOrsIdentifkeyPltr().trim().toUpperCase()
        );

        if (dataProviders.existsByPlanKey(dto.getOrsIdentifkeyPltr())) {
            throw new IllegalArgumentException(
                    "Ya existe un plan de trabajo con el código: "
                            + dto.getOrsIdentifkeyPltr()
            );
        }

        dto.setOrsTiporegistPltr(TIPO_REGISTRO_ORIGINAL);
        dto.setOrsEstadoregPltr(ESTADO_ABIERTO);

        return dto;
    }

    private EntyOrsplamaplandetrabajoDto normalizeForUpdate(
            final EntyOrsplamaplandetrabajoDto request,
            final EntyOrsplamaplandetrabajoDto current
    ) {
        validarRequestBase(request);

        EntyOrsplamaplandetrabajoDto dto =
                normalizeCommon(request);

        dto.setOrsPrimarykeyPltr(current.getOrsPrimarykeyPltr());
        dto.setOrsIdentifkeyPltr(current.getOrsIdentifkeyPltr());

        if (dto.getOrsTiporegistPltr() == null
                || dto.getOrsTiporegistPltr().trim().isEmpty()) {
            dto.setOrsTiporegistPltr(
                    current.getOrsTiporegistPltr() != null
                            ? current.getOrsTiporegistPltr()
                            : TIPO_REGISTRO_ORIGINAL
            );
        }

        if (dto.getOrsEstadoregPltr() == null
                || dto.getOrsEstadoregPltr().trim().isEmpty()) {
            dto.setOrsEstadoregPltr(
                    current.getOrsEstadoregPltr() != null
                            ? current.getOrsEstadoregPltr()
                            : ESTADO_ABIERTO
            );
        }

        return dto;
    }

    private EntyOrsplamaplandetrabajoDto normalizeCommon(
            final EntyOrsplamaplandetrabajoDto request
    ) {
        EntyOrsplamaplandetrabajoDto dto =
                new EntyOrsplamaplandetrabajoDto();

        dto.setOrsPrimarykeyPltr(request.getOrsPrimarykeyPltr());
        dto.setOrsIdentifkeyPltr(limpiarMayuscula(request.getOrsIdentifkeyPltr()));
        dto.setOrsIdentifkeyOrde(limpiarMayuscula(request.getOrsIdentifkeyOrde()));
        dto.setOrsIdentifkeyPunt(limpiarMayuscula(request.getOrsIdentifkeyPunt()));
        dto.setOrsDesactividadPltr(limpiarTexto(request.getOrsDesactividadPltr()));
        dto.setOrsIdentifkeyRseq(limpiarMayuscula(request.getOrsIdentifkeyRseq()));
        dto.setPrvIdentifkeyInve(limpiarMayuscula(request.getPrvIdentifkeyInve()));
        dto.setOrsCantidunidadRseq(request.getOrsCantidunidadRseq());
        dto.setOrsValorunidadRseq(request.getOrsValorunidadRseq());
        dto.setOrsValortotalRseq(request.getOrsValortotalRseq());
        dto.setOrsTiporegistPltr(limpiarMayuscula(request.getOrsTiporegistPltr()));
        dto.setOrsEstadoregPltr(limpiarMayuscula(request.getOrsEstadoregPltr()));

        validarLongitud(dto.getOrsIdentifkeyPltr(), MAX_KEY, "código del plan");
        validarLongitud(dto.getOrsIdentifkeyOrde(), MAX_KEY, "código de la orden");
        validarLongitud(dto.getOrsIdentifkeyPunt(), MAX_KEY, "código del punto");
        validarLongitud(dto.getOrsDesactividadPltr(), MAX_ACTIVIDAD, "actividad");
        validarLongitud(dto.getOrsIdentifkeyRseq(), MAX_KEY, "resumen de equipo");
        validarLongitud(dto.getPrvIdentifkeyInve(), MAX_INVENTARIO, "inventario");

        validarCantidadYValores(dto);
        calcularValorTotal(dto);

        return dto;
    }

    private void validarRequestBase(
            final EntyOrsplamaplandetrabajoDto request
    ) {
        if (request == null) {
            throw new IllegalArgumentException(
                    "La solicitud del plan de trabajo es obligatoria."
            );
        }

        validarTexto(
                request.getOrsIdentifkeyOrde(),
                "El código de la orden de servicio es obligatorio."
        );

        validarTexto(
                request.getOrsIdentifkeyPunt(),
                "El código del sitio o punto es obligatorio."
        );

        validarTexto(
                request.getOrsDesactividadPltr(),
                "La descripción de la actividad es obligatoria."
        );

        String ordenKey = request.getOrsIdentifkeyOrde()
                .trim()
                .toUpperCase();

        String puntoKey = request.getOrsIdentifkeyPunt()
                .trim()
                .toUpperCase();

        if (!ordenRepository.existsByOrsIdentifkeyOrde(ordenKey)) {
            throw new IllegalArgumentException(
                    "No existe la orden de servicio: " + ordenKey
            );
        }

        if (!puntoRepository.existsByOrsIdentifkeyPunt(puntoKey)) {
            throw new IllegalArgumentException(
                    "No existe el sitio o punto: " + puntoKey
            );
        }

        validarPuntoPerteneceOrden(ordenKey, puntoKey);
    }

    private void validarPuntoPerteneceOrden(
            final String ordenKey,
            final String puntoKey
    ) {
        boolean pertenece = puntoRepository
                .findByOrsIdentifkeyOrdeOrderByOrsPrimarykeyPuntAsc(ordenKey)
                .stream()
                .anyMatch(punto -> puntoKey.equalsIgnoreCase(
                        punto.getOrsIdentifkeyPunt()
                ));

        if (!pertenece) {
            throw new IllegalArgumentException(
                    "El punto " + puntoKey
                            + " no pertenece a la orden " + ordenKey + "."
            );
        }
    }

    private void validarCantidadYValores(
            final EntyOrsplamaplandetrabajoDto dto
    ) {
        if (dto.getOrsCantidunidadRseq() != null
                && dto.getOrsCantidunidadRseq() < 0) {
            throw new IllegalArgumentException(
                    "La cantidad de unidades no puede ser negativa."
            );
        }

        if (dto.getOrsValorunidadRseq() != null
                && dto.getOrsValorunidadRseq()
                .compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El valor unitario no puede ser negativo."
            );
        }

        if (dto.getOrsValortotalRseq() != null
                && dto.getOrsValortotalRseq()
                .compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El valor total no puede ser negativo."
            );
        }
    }

    private void calcularValorTotal(
            final EntyOrsplamaplandetrabajoDto dto
    ) {
        if (dto.getOrsCantidunidadRseq() != null
                && dto.getOrsValorunidadRseq() != null) {
            BigDecimal cantidad =
                    BigDecimal.valueOf(dto.getOrsCantidunidadRseq());

            dto.setOrsValortotalRseq(
                    dto.getOrsValorunidadRseq().multiply(cantidad)
            );
        }
    }

    private String generarPlanKey(
            final String ordenKey
    ) throws EBusinessException {
        List<EntyOrsplamaplandetrabajoDto> existentes =
                dataProviders.findByOrden(ordenKey);

        String cleanOrder = ordenKey
                .toUpperCase()
                .replaceAll("[^A-Z0-9]", "");

        int maxOrderLength = 30 - "PLTR-".length() - "-000".length();

        if (cleanOrder.length() > maxOrderLength) {
            cleanOrder = cleanOrder.substring(0, maxOrderLength);
        }

        int consecutivo = existentes.size() + 1;

        String generated;

        do {
            generated = "PLTR-"
                    + cleanOrder
                    + "-"
                    + String.format("%03d", consecutivo);

            consecutivo++;
        } while (dataProviders.existsByPlanKey(generated));

        return generated;
    }

    private EntyOrsplamaplandetrabajoResponse buildSingleResponse(
            final EntyOrsplamaplandetrabajoDto dto,
            final String message,
            final String parentKey
    ) {
        List<EntyOrsplamaplandetrabajoDto> data =
                new ArrayList<>();

        if (dto != null) {
            data.add(dto);
        }

        return buildListResponse(
                data,
                message,
                parentKey
        );
    }

    private EntyOrsplamaplandetrabajoResponse buildListResponse(
            final List<EntyOrsplamaplandetrabajoDto> data,
            final String message,
            final String parentKey
    ) {
        List<EntyOrsplamaplandetrabajoDto> safeData =
                data != null ? data : new ArrayList<>();

        EntyOrsplamaplandetrabajoResponse response =
                new EntyOrsplamaplandetrabajoResponse();

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