package com.system.modules.workcontrol.usecase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaDto;
import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsplamaplandetrabajo;
import com.system.crosscutting.persistence.entity.EntyOrsordmdproyecsemana;
import com.system.crosscutting.persistence.repository.EntyOrsplamaplandetrabajoRepository;
import com.system.crosscutting.persistence.repository.EntyOrsordmaordenservicioRepository;
import com.system.crosscutting.persistence.repository.EntyOrsordmdproyecsemanaRepository;
import com.system.modules.workcontrol.dataproviders.IjpaPlanTrabajoSemanaDataProviders;

@Service
public class EntyPlanTrabajoSemanaService {

    private static final String ESTADO_ABIERTO = "1";

    private static final String ESTADO_CERRADO = "2";

    private static final String ESTADO_CANCELADO = "3";

    private static final String TIPO_REGISTRO_ORIGINAL = "1";

    private static final int MAX_KEY = 30;

    private final IjpaPlanTrabajoSemanaDataProviders dataProviders;

    private final EntyOrsordmaordenservicioRepository ordenRepository;

    private final EntyOrsplamaplandetrabajoRepository planTrabajoRepository;

    private final EntyOrsordmdproyecsemanaRepository semanaRepository;

    public EntyPlanTrabajoSemanaService(
            final IjpaPlanTrabajoSemanaDataProviders dataProviders,
            final EntyOrsordmaordenservicioRepository ordenRepository,
            final EntyOrsplamaplandetrabajoRepository planTrabajoRepository,
            final EntyOrsordmdproyecsemanaRepository semanaRepository
    ) {
        this.dataProviders = dataProviders;
        this.ordenRepository = ordenRepository;
        this.planTrabajoRepository = planTrabajoRepository;
        this.semanaRepository = semanaRepository;
    }

    @Transactional
    public EntyOrsplamdplantrabsemanaResponse createResponse(
            final EntyOrsplamdplantrabsemanaDto request
    ) throws EBusinessException {
        EntyOrsplamdplantrabsemanaDto normalized =
                normalizeForCreate(request);

        EntyOrsplamdplantrabsemanaDto saved =
                dataProviders.save(normalized);

        return buildSingleResponse(
                saved,
                "Plan semanal creado correctamente.",
                saved.getOrsIdentifkeyOrde()
        );
    }

    public EntyOrsplamdplantrabsemanaResponse getByOrdenResponse(
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

    public EntyOrsplamdplantrabsemanaResponse getByPlanTrabajoResponse(
            final String planTrabajoKey
    ) throws EBusinessException {
        validarTexto(
                planTrabajoKey,
                "El código del plan de trabajo es obligatorio."
        );

        return dataProviders.findByPlanTrabajoResponse(
                planTrabajoKey.trim().toUpperCase()
        );
    }

    public EntyOrsplamdplantrabsemanaResponse getBySemanaResponse(
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

    public EntyOrsplamdplantrabsemanaResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyOrsplamdplantrabsemanaResponse getAll(
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

    public EntyOrsplamdplantrabsemanaResponse getResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsplamdplantrabsemanaDto dto =
                dataProviders.get(id);

        if (dto.getOrsPrimarykeyPlse() == null) {
            return buildListResponse(
                    new ArrayList<>(),
                    "No se encontró el plan semanal.",
                    "NA"
            );
        }

        return buildSingleResponse(
                dto,
                "Plan semanal consultado correctamente.",
                dto.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsplamdplantrabsemanaResponse updateResponse(
            final Integer id,
            final EntyOrsplamdplantrabsemanaDto request
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID del plan semanal es obligatorio."
            );
        }

        EntyOrsplamdplantrabsemanaDto current =
                dataProviders.get(id);

        if (current.getOrsPrimarykeyPlse() == null) {
            throw new IllegalArgumentException(
                    "No existe el plan semanal con ID: " + id
            );
        }

        EntyOrsplamdplantrabsemanaDto normalized =
                normalizeForUpdate(request, current);

        EntyOrsplamdplantrabsemanaDto updated =
                dataProviders.update(id, normalized);

        return buildSingleResponse(
                updated,
                "Plan semanal actualizado correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsplamdplantrabsemanaResponse cerrarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsplamdplantrabsemanaDto current =
                getCurrentOrFail(id);

        current.setOrsEstadoregPlse(ESTADO_CERRADO);

        EntyOrsplamdplantrabsemanaDto updated =
                dataProviders.update(id, current);

        return buildSingleResponse(
                updated,
                "Plan semanal cerrado correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsplamdplantrabsemanaResponse cancelarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsplamdplantrabsemanaDto current =
                getCurrentOrFail(id);

        current.setOrsEstadoregPlse(ESTADO_CANCELADO);

        EntyOrsplamdplantrabsemanaDto updated =
                dataProviders.update(id, current);

        return buildSingleResponse(
                updated,
                "Plan semanal cancelado correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsplamdplantrabsemanaResponse deleteResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsplamdplantrabsemanaDto current =
                getCurrentOrFail(id);

        dataProviders.delete(id);

        return buildSingleResponse(
                current,
                "Plan semanal eliminado correctamente.",
                current.getOrsIdentifkeyOrde()
        );
    }

    private EntyOrsplamdplantrabsemanaDto getCurrentOrFail(
            final Integer id
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID del plan semanal es obligatorio."
            );
        }

        EntyOrsplamdplantrabsemanaDto current =
                dataProviders.get(id);

        if (current.getOrsPrimarykeyPlse() == null) {
            throw new IllegalArgumentException(
                    "No existe el plan semanal con ID: " + id
            );
        }

        return current;
    }

    private EntyOrsplamdplantrabsemanaDto normalizeForCreate(
            final EntyOrsplamdplantrabsemanaDto request
    ) throws EBusinessException {
        validarRequestBase(request);

        EntyOrsplamdplantrabsemanaDto dto =
                normalizeCommon(request);

        dto.setOrsPrimarykeyPlse(null);

        if (dto.getOrsIdentifkeyPlse() == null
                || dto.getOrsIdentifkeyPlse().trim().isEmpty()) {
            dto.setOrsIdentifkeyPlse(
                    generarPlanSemanaKey(dto.getOrsIdentifkeyOrde())
            );
        }

        dto.setOrsIdentifkeyPlse(
                dto.getOrsIdentifkeyPlse().trim().toUpperCase()
        );

        if (dataProviders.existsByPlanSemanaKey(
                dto.getOrsIdentifkeyPlse()
        )) {
            throw new IllegalArgumentException(
                    "Ya existe un plan semanal con el código: "
                            + dto.getOrsIdentifkeyPlse()
            );
        }

        dto.setOrsTiporegistPlse(TIPO_REGISTRO_ORIGINAL);
        dto.setOrsEstadoregPlse(ESTADO_ABIERTO);

        if (dto.getOrsEjecutunidadPlse() == null) {
            dto.setOrsEjecutunidadPlse(0);
        }

        if (dto.getOrsValorejecutPlse() == null) {
            dto.setOrsValorejecutPlse(BigDecimal.ZERO);
        }

        return dto;
    }

    private EntyOrsplamdplantrabsemanaDto normalizeForUpdate(
            final EntyOrsplamdplantrabsemanaDto request,
            final EntyOrsplamdplantrabsemanaDto current
    ) {
        validarRequestBase(request);

        EntyOrsplamdplantrabsemanaDto dto =
                normalizeCommon(request);

        dto.setOrsPrimarykeyPlse(current.getOrsPrimarykeyPlse());
        dto.setOrsIdentifkeyPlse(current.getOrsIdentifkeyPlse());

        if (dto.getOrsTiporegistPlse() == null
                || dto.getOrsTiporegistPlse().trim().isEmpty()) {
            dto.setOrsTiporegistPlse(
                    current.getOrsTiporegistPlse() != null
                            ? current.getOrsTiporegistPlse()
                            : TIPO_REGISTRO_ORIGINAL
            );
        }

        if (dto.getOrsEstadoregPlse() == null
                || dto.getOrsEstadoregPlse().trim().isEmpty()) {
            dto.setOrsEstadoregPlse(
                    current.getOrsEstadoregPlse() != null
                            ? current.getOrsEstadoregPlse()
                            : ESTADO_ABIERTO
            );
        }

        return dto;
    }

    private EntyOrsplamdplantrabsemanaDto normalizeCommon(
            final EntyOrsplamdplantrabsemanaDto request
    ) {
        EntyOrsplamdplantrabsemanaDto dto =
                new EntyOrsplamdplantrabsemanaDto();

        dto.setOrsPrimarykeyPlse(request.getOrsPrimarykeyPlse());
        dto.setOrsIdentifkeyPlse(limpiarMayuscula(request.getOrsIdentifkeyPlse()));
        dto.setOrsIdentifkeyOrde(limpiarMayuscula(request.getOrsIdentifkeyOrde()));
        dto.setOrsIdentifkeyPltr(limpiarMayuscula(request.getOrsIdentifkeyPltr()));
        dto.setOrsIdentifkeyPsem(limpiarMayuscula(request.getOrsIdentifkeyPsem()));
        dto.setOrsCantidunidadPlse(request.getOrsCantidunidadPlse());
        dto.setOrsValorunidadPlse(request.getOrsValorunidadPlse());
        dto.setOrsValortotalPlse(request.getOrsValortotalPlse());
        dto.setOrsEjecutunidadPlse(request.getOrsEjecutunidadPlse());
        dto.setOrsValorejecutPlse(request.getOrsValorejecutPlse());
        dto.setOrsTiporegistPlse(limpiarMayuscula(request.getOrsTiporegistPlse()));
        dto.setOrsEstadoregPlse(limpiarMayuscula(request.getOrsEstadoregPlse()));

        validarLongitud(dto.getOrsIdentifkeyPlse(), MAX_KEY, "código del plan semanal");
        validarLongitud(dto.getOrsIdentifkeyOrde(), MAX_KEY, "código de la orden");
        validarLongitud(dto.getOrsIdentifkeyPltr(), MAX_KEY, "código del plan de trabajo");
        validarLongitud(dto.getOrsIdentifkeyPsem(), MAX_KEY, "código de la semana");

        validarCantidadYValores(dto);
        calcularValores(dto);

        return dto;
    }

    private void validarRequestBase(
            final EntyOrsplamdplantrabsemanaDto request
    ) {
        if (request == null) {
            throw new IllegalArgumentException(
                    "La solicitud del plan semanal es obligatoria."
            );
        }

        validarTexto(
                request.getOrsIdentifkeyOrde(),
                "El código de la orden de servicio es obligatorio."
        );

        validarTexto(
                request.getOrsIdentifkeyPltr(),
                "El código del plan de trabajo es obligatorio."
        );

        validarTexto(
                request.getOrsIdentifkeyPsem(),
                "El código de la semana proyectada es obligatorio."
        );

        String ordenKey = request.getOrsIdentifkeyOrde()
                .trim()
                .toUpperCase();

        String planTrabajoKey = request.getOrsIdentifkeyPltr()
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

        EntyOrsplamaplandetrabajo planTrabajo =
                planTrabajoRepository
                        .findByOrsIdentifkeyPltr(planTrabajoKey)
                        .orElse(null);

        if (planTrabajo == null) {
            throw new IllegalArgumentException(
                    "No existe el plan de trabajo: " + planTrabajoKey
            );
        }

        if (!ordenKey.equalsIgnoreCase(planTrabajo.getOrsIdentifkeyOrde())) {
            throw new IllegalArgumentException(
                    "El plan de trabajo " + planTrabajoKey
                            + " no pertenece a la orden " + ordenKey + "."
            );
        }

        validarSemanaPerteneceOrden(ordenKey, semanaKey);
    }

    private void validarSemanaPerteneceOrden(
            final String ordenKey,
            final String semanaKey
    ) {
        EntyOrsordmdproyecsemana semanaEncontrada =
                semanaRepository
                        .findByOrsIdentifkeyOrdeOrderByOrsNumerosemPsemAsc(
                                ordenKey
                        )
                        .stream()
                        .filter(semana -> semanaKey.equalsIgnoreCase(
                                semana.getOrsIdentifkeyPsem()
                        ))
                        .findFirst()
                        .orElse(null);

        if (semanaEncontrada == null) {
            throw new IllegalArgumentException(
                    "La semana " + semanaKey
                            + " no pertenece a la orden " + ordenKey + "."
            );
        }
    }

    private void validarCantidadYValores(
            final EntyOrsplamdplantrabsemanaDto dto
    ) {
        if (dto.getOrsCantidunidadPlse() != null
                && dto.getOrsCantidunidadPlse() < 0) {
            throw new IllegalArgumentException(
                    "La cantidad programada no puede ser negativa."
            );
        }

        if (dto.getOrsEjecutunidadPlse() != null
                && dto.getOrsEjecutunidadPlse() < 0) {
            throw new IllegalArgumentException(
                    "La cantidad ejecutada no puede ser negativa."
            );
        }

        if (dto.getOrsValorunidadPlse() != null
                && dto.getOrsValorunidadPlse()
                .compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El valor unitario no puede ser negativo."
            );
        }

        if (dto.getOrsValortotalPlse() != null
                && dto.getOrsValortotalPlse()
                .compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El valor total programado no puede ser negativo."
            );
        }

        if (dto.getOrsValorejecutPlse() != null
                && dto.getOrsValorejecutPlse()
                .compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El valor ejecutado no puede ser negativo."
            );
        }
    }

    private void calcularValores(
            final EntyOrsplamdplantrabsemanaDto dto
    ) {
        if (dto.getOrsCantidunidadPlse() != null
                && dto.getOrsValorunidadPlse() != null) {
            BigDecimal cantidadProgramada =
                    BigDecimal.valueOf(dto.getOrsCantidunidadPlse());

            dto.setOrsValortotalPlse(
                    dto.getOrsValorunidadPlse()
                            .multiply(cantidadProgramada)
            );
        }

        if (dto.getOrsEjecutunidadPlse() != null
                && dto.getOrsValorunidadPlse() != null) {
            BigDecimal cantidadEjecutada =
                    BigDecimal.valueOf(dto.getOrsEjecutunidadPlse());

            dto.setOrsValorejecutPlse(
                    dto.getOrsValorunidadPlse()
                            .multiply(cantidadEjecutada)
            );
        }
    }

    private String generarPlanSemanaKey(
            final String ordenKey
    ) throws EBusinessException {
        List<EntyOrsplamdplantrabsemanaDto> existentes =
                dataProviders.findByOrden(ordenKey);

        String cleanOrder = ordenKey
                .toUpperCase()
                .replaceAll("[^A-Z0-9]", "");

        int maxOrderLength = 30 - "PLSE-".length() - "-000".length();

        if (cleanOrder.length() > maxOrderLength) {
            cleanOrder = cleanOrder.substring(0, maxOrderLength);
        }

        int consecutivo = existentes.size() + 1;

        String generated;

        do {
            generated = "PLSE-"
                    + cleanOrder
                    + "-"
                    + String.format("%03d", consecutivo);

            consecutivo++;
        } while (dataProviders.existsByPlanSemanaKey(generated));

        return generated;
    }

    private EntyOrsplamdplantrabsemanaResponse buildSingleResponse(
            final EntyOrsplamdplantrabsemanaDto dto,
            final String message,
            final String parentKey
    ) {
        List<EntyOrsplamdplantrabsemanaDto> data =
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

    private EntyOrsplamdplantrabsemanaResponse buildListResponse(
            final List<EntyOrsplamdplantrabsemanaDto> data,
            final String message,
            final String parentKey
    ) {
        List<EntyOrsplamdplantrabsemanaDto> safeData =
                data != null ? data : new ArrayList<>();

        EntyOrsplamdplantrabsemanaResponse response =
                new EntyOrsplamdplantrabsemanaResponse();

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