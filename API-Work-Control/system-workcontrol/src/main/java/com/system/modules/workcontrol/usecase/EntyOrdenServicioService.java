package com.system.modules.workcontrol.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import javax.transaction.Transactional;

import com.system.crosscutting.domain.model.PaginationResponse;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioDto;
import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.dataproviders.IjpaOrdenServicioDataProviders;

@Service
public class EntyOrdenServicioService {

    private static final int MAX_IDENTIFKEY_ORDE = 50;
    private static final int MAX_AUTORIZACION_SEBS = 50;
    private static final int MAX_SERVICEVENT_ORDE = 200;
    private static final int MAX_SERVICLUGAR_ORDE = 250;
    private static final int MAX_SERVICOBJETO_ORDE = 1000;
    private static final int MAX_PROVEEDOR_KEY = 50;
    private static final int MAX_REPRESENTANTE_KEY = 50;
    private static final int MAX_TIPO_REGISTRO = 2;
    private static final int MAX_ESTADO_REGISTRO = 2;

    private final IjpaOrdenServicioDataProviders dataProviders;

    public EntyOrdenServicioService(
            final IjpaOrdenServicioDataProviders dataProviders
    ) {
        this.dataProviders = dataProviders;
    }

    public Map<String, Object> status() {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("service", "work-control");
        response.put("module", "ordenes-servicio");
        response.put("status", "UP");
        response.put("message", "Microservicio work-control disponible.");
        response.put("basePath", "/api/workcontrol");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("version", "1.0.0");

        Map<String, String> endpoints = new LinkedHashMap<>();
        endpoints.put("statusRoot", "GET /api/workcontrol");
        endpoints.put("status", "GET /api/workcontrol/status");
        endpoints.put("createOrden", "POST /api/workcontrol/create");
        endpoints.put("getByKey", "GET /api/workcontrol/by-key?ordenKey=ORDE-0001");
        endpoints.put("getByEstado", "GET /api/workcontrol/by-estado?estado=1");
        endpoints.put("getAll", "GET /api/workcontrol/all");
        endpoints.put("pages", "GET /api/workcontrol/pages?currentPage=1&pageSize=10&parameter=TEXT&filter=");
        endpoints.put("getById", "GET /api/workcontrol/{id}");
        endpoints.put("update", "PUT /api/workcontrol/{id}");
        endpoints.put("inactivar", "PATCH /api/workcontrol/{id}/inactivar");
        endpoints.put("delete", "DELETE /api/workcontrol/{id}");

        response.put("endpoints", endpoints);

        return response;
    }

    @Transactional
    public EntyOrsordmaordenservicioResponse createResponse(
            final EntyOrsordmaordenservicioDto request
    ) {
        EntyOrsordmaordenservicioDto created = create(request);

        return buildSingleResponse(
                created,
                "Orden de servicio creada correctamente.",
                created.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsordmaordenservicioResponse getByKeyResponse(
            final String ordenKey
    ) {
        EntyOrsordmaordenservicioDto row = getByKey(ordenKey);

        return buildSingleResponse(
                row,
                "Orden de servicio consultada correctamente.",
                row.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsordmaordenservicioResponse getByEstadoResponse(
            final String estado
    ) {
        List<EntyOrsordmaordenservicioDto> rows = getByEstado(estado);

        return buildListResponse(
                rows,
                "Órdenes de servicio consultadas correctamente por estado.",
                estado
        );
    }

    @Transactional
    public EntyOrsordmaordenservicioResponse getResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsordmaordenservicioDto row = get(id);

        return buildSingleResponse(
                row,
                "Orden de servicio consultada correctamente.",
                row.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsordmaordenservicioResponse updateResponse(
            final Integer id,
            final EntyOrsordmaordenservicioDto request
    ) throws EBusinessException {
        EntyOrsordmaordenservicioDto updated = update(id, request);

        return buildSingleResponse(
                updated,
                "Orden de servicio actualizada correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsordmaordenservicioResponse inactivarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsordmaordenservicioDto current = get(id);

        current.setOrsEstadoregOrde("2");

        EntyOrsordmaordenservicioDto updated = update(id, current);

        return buildSingleResponse(
                updated,
                "Orden de servicio inactivada correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsordmaordenservicioResponse deleteResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsordmaordenservicioDto current = get(id);

        delete(id);

        EntyOrsordmaordenservicioResponse response =
                EntyOrsordmaordenservicioResponse
                        .builder()
                        .rspValue("OK")
                        .rspMessage("Orden de servicio eliminada correctamente.")
                        .rspParentKey(current.getOrsIdentifkeyOrde())
                        .rspAppKey("WORK-CONTROL")
                        .rspPagination(buildPaginationResponse(1, 1, 0L))
                        .rspData(new ArrayList<>())
                        .build();

        return response;
    }

    private EntyOrsordmaordenservicioResponse buildSingleResponse(
            final EntyOrsordmaordenservicioDto row,
            final String message,
            final String parentKey
    ) {
        return EntyOrsordmaordenservicioResponse
                .builder()
                .rspValue("OK")
                .rspMessage(message)
                .rspParentKey(parentKey != null ? parentKey : "NA")
                .rspAppKey("WORK-CONTROL")
                .rspPagination(buildPaginationResponse(1, 1, row != null ? 1L : 0L))
                .rspData(
                        row != null
                                ? Collections.singletonList(row)
                                : new ArrayList<>()
                )
                .build();
    }

    private EntyOrsordmaordenservicioResponse buildListResponse(
            final List<EntyOrsordmaordenservicioDto> rows,
            final String message,
            final String parentKey
    ) {
        List<EntyOrsordmaordenservicioDto> safeRows =
                rows != null ? rows : new ArrayList<>();

        return EntyOrsordmaordenservicioResponse
                .builder()
                .rspValue("OK")
                .rspMessage(message)
                .rspParentKey(parentKey != null ? parentKey : "NA")
                .rspAppKey("WORK-CONTROL")
                .rspPagination(
                        buildPaginationResponse(
                                1,
                                safeRows.size() > 0 ? safeRows.size() : 1,
                                Long.valueOf(safeRows.size())
                        )
                )
                .rspData(safeRows)
                .build();
    }

    private PaginationResponse buildPaginationResponse(
            final int currentPage,
            final int pageSize,
            final Long totalResults
    ) {
        long safeTotalResults = totalResults != null ? totalResults : 0L;
        int safePageSize = pageSize <= 0 ? 1 : pageSize;

        int totalPages = safeTotalResults == 0
                ? 0
                : (int) Math.ceil((double) safeTotalResults / safePageSize);

        return PaginationResponse
                .builder()
                .currentPage(currentPage)
                .totalPageSize(safePageSize)
                .totalResults(safeTotalResults)
                .totalPages(totalPages)
                .hasNextPage(false)
                .hasPreviousPage(false)
                .nextPageUrl("LocalHost")
                .previousPageUrl("LocalHost")
                .build();
    }
    @Transactional
    public EntyOrsordmaordenservicioDto create(
            final EntyOrsordmaordenservicioDto request
    ) {
        validarCrearOrden(request);

        EntyOrsordmaordenservicioDto dto = normalizarCrearOrden(request);

        if (dataProviders.existsByOrdenKey(dto.getOrsIdentifkeyOrde())) {
            throw new IllegalArgumentException(
                    "Ya existe una orden de servicio con el código: "
                            + dto.getOrsIdentifkeyOrde()
                            + ". Usa otro código o consulta la orden existente."
            );
        }

        return dataProviders.create(dto);
    }

    @Transactional
    public EntyOrsordmaordenservicioDto getByKey(final String ordenKey) {
        validarTexto(ordenKey, "El código de la orden es obligatorio.");

        String key = ordenKey.trim().toUpperCase();

        return dataProviders
                .getByKey(key)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la orden de servicio: " + key
                ));
    }

    @Transactional
    public List<EntyOrsordmaordenservicioDto> getByEstado(
            final String estado
    ) {
        validarTexto(estado, "El estado es obligatorio.");
        validarLongitud(estado, MAX_ESTADO_REGISTRO, "estado");

        return dataProviders.getByEstado(estado.trim());
    }

    @Transactional
    public EntyOrsordmaordenservicioResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    @Transactional
    public EntyOrsordmaordenservicioResponse getAll(
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

    @Transactional
    public EntyOrsordmaordenservicioDto get(final Integer id)
            throws EBusinessException {
        return dataProviders.get(id);
    }

    @Transactional
    public EntyOrsordmaordenservicioDto update(
            final Integer id,
            final EntyOrsordmaordenservicioDto request
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID de la orden de servicio es obligatorio."
            );
        }

        validarCrearOrden(request);

        EntyOrsordmaordenservicioDto dto = normalizarActualizarOrden(request);

        return dataProviders.update(id, dto);
    }

    @Transactional
    public void delete(final Integer id) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID de la orden de servicio es obligatorio."
            );
        }

        dataProviders.delete(id);
    }

    private EntyOrsordmaordenservicioDto normalizarCrearOrden(
            final EntyOrsordmaordenservicioDto request
    ) {
        EntyOrsordmaordenservicioDto dto = normalizarBaseOrden(request);

        dto.setOrsIdentifkeyOrde(
                generarOrdenKeySiNoExiste(request.getOrsIdentifkeyOrde())
        );

        return dto;
    }

    private EntyOrsordmaordenservicioDto normalizarActualizarOrden(
            final EntyOrsordmaordenservicioDto request
    ) {
        EntyOrsordmaordenservicioDto dto = normalizarBaseOrden(request);

        dto.setOrsPrimarykeyOrde(request.getOrsPrimarykeyOrde());
        dto.setOrsIdentifkeyOrde(
                limpiarTextoMayuscula(request.getOrsIdentifkeyOrde())
        );

        return dto;
    }

    private EntyOrsordmaordenservicioDto normalizarBaseOrden(
            final EntyOrsordmaordenservicioDto request
    ) {
        EntyOrsordmaordenservicioDto dto =
                new EntyOrsordmaordenservicioDto();

        dto.setOrsAutorifechaOrde(
                request.getOrsAutorifechaOrde() != null
                        ? request.getOrsAutorifechaOrde()
                        : LocalDate.now()
        );

        dto.setOrsCodservicioSebs(
                limpiarTextoMayuscula(request.getOrsCodservicioSebs())
        );
        dto.setOrsServiceventOrde(
                limpiarTexto(request.getOrsServiceventOrde())
        );
        dto.setOrsServiclugarOrde(
                limpiarTexto(request.getOrsServiclugarOrde())
        );
        dto.setOrsServicobjetoOrde(
                limpiarTexto(request.getOrsServicobjetoOrde())
        );

        dto.setOrsPlanfechiniOrde(request.getOrsPlanfechiniOrde());
        dto.setOrsPlanfechfinOrde(request.getOrsPlanfechfinOrde());

        dto.setPrvIdentifkeyMprv(
                limpiarTextoMayuscula(request.getPrvIdentifkeyMprv())
        );
        dto.setPrvIdentifkeyRelg(
                limpiarTextoMayuscula(request.getPrvIdentifkeyRelg())
        );

        BigDecimal valorBase = valorSeguro(request.getOrsValorbaseOrde());
        BigDecimal valorIva = valorSeguro(request.getOrsValordeivaOrde());
        BigDecimal valorTotal = request.getOrsValortotalOrde();

        if (valorTotal == null) {
            valorTotal = valorBase.add(valorIva);
        }

        dto.setOrsValorbaseOrde(valorBase);
        dto.setOrsValordeivaOrde(valorIva);
        dto.setOrsValortotalOrde(valorTotal);

        dto.setOrsTiporegistOrde(
                limpiarTexto(request.getOrsTiporegistOrde()) != null
                        ? limpiarTexto(request.getOrsTiporegistOrde())
                        : "1"
        );

        dto.setOrsEstadoregOrde(
                limpiarTexto(request.getOrsEstadoregOrde()) != null
                        ? limpiarTexto(request.getOrsEstadoregOrde())
                        : "1"
        );

        return dto;
    }

    private void validarCrearOrden(
            final EntyOrsordmaordenservicioDto request
    ) {
        if (request == null) {
            throw new IllegalArgumentException(
                    "La información de la orden de servicio es obligatoria."
            );
        }

        validarTexto(
                request.getOrsServiceventOrde(),
                "El servicio/evento de la orden es obligatorio."
        );

        validarTexto(
                request.getOrsServiclugarOrde(),
                "El lugar de ejecución es obligatorio."
        );

        validarTexto(
                request.getOrsServicobjetoOrde(),
                "El objeto de la orden es obligatorio."
        );

        validarLongitud(
                request.getOrsIdentifkeyOrde(),
                MAX_IDENTIFKEY_ORDE,
                "código de orden"
        );

        validarLongitud(
                request.getOrsCodservicioSebs(),
                MAX_AUTORIZACION_SEBS,
                "código/autorización de servicio"
        );

        validarLongitud(
                request.getOrsServiceventOrde(),
                MAX_SERVICEVENT_ORDE,
                "servicio/evento"
        );

        validarLongitud(
                request.getOrsServiclugarOrde(),
                MAX_SERVICLUGAR_ORDE,
                "lugar de ejecución"
        );

        validarLongitud(
                request.getOrsServicobjetoOrde(),
                MAX_SERVICOBJETO_ORDE,
                "objeto de la orden"
        );

        validarLongitud(
                request.getPrvIdentifkeyMprv(),
                MAX_PROVEEDOR_KEY,
                "código de proveedor"
        );

        validarLongitud(
                request.getPrvIdentifkeyRelg(),
                MAX_REPRESENTANTE_KEY,
                "código de representante legal"
        );

        validarLongitud(
                request.getOrsTiporegistOrde(),
                MAX_TIPO_REGISTRO,
                "tipo de registro"
        );

        validarLongitud(
                request.getOrsEstadoregOrde(),
                MAX_ESTADO_REGISTRO,
                "estado de registro"
        );

        if (request.getOrsPlanfechiniOrde() == null) {
            throw new IllegalArgumentException(
                    "La fecha inicial del plan es obligatoria."
            );
        }

        if (request.getOrsPlanfechfinOrde() == null) {
            throw new IllegalArgumentException(
                    "La fecha final del plan es obligatoria."
            );
        }

        if (request.getOrsPlanfechiniOrde()
                .isAfter(request.getOrsPlanfechfinOrde())) {
            throw new IllegalArgumentException(
                    "La fecha inicial no puede ser mayor que la fecha final."
            );
        }

        if (request.getOrsValorbaseOrde() != null
                && request.getOrsValorbaseOrde()
                .compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El valor base no puede ser negativo."
            );
        }

        if (request.getOrsValordeivaOrde() != null
                && request.getOrsValordeivaOrde()
                .compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El valor de IVA no puede ser negativo."
            );
        }

        if (request.getOrsValortotalOrde() != null
                && request.getOrsValortotalOrde()
                .compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El valor total no puede ser negativo."
            );
        }
    }

    private void validarTexto(final String value, final String mensaje) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    private void validarLongitud(
            final String value,
            final int maxLength,
            final String campo
    ) {
        if (value == null || value.trim().isEmpty()) {
            return;
        }

        if (value.trim().length() > maxLength) {
            throw new IllegalArgumentException(
                    "El campo " + campo + " no puede superar "
                            + maxLength + " caracteres. Valor recibido con "
                            + value.trim().length() + " caracteres."
            );
        }
    }

    private String limpiarTexto(final String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return value.trim();
    }

    private String limpiarTextoMayuscula(final String value) {
        String limpio = limpiarTexto(value);

        if (limpio == null) {
            return null;
        }

        return limpio.toUpperCase();
    }

    private BigDecimal valorSeguro(final BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }

        return value;
    }

    private String generarOrdenKeySiNoExiste(final String ordenKey) {
        String key = limpiarTextoMayuscula(ordenKey);

        if (key != null) {
            return key;
        }

        return "ORDE-" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8)
                .toUpperCase();
    }
}