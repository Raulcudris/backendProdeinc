package com.system.modules.workcontrol.usecase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriDto;
import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyOrsordmaordenservicioRepository;
import com.system.modules.workcontrol.dataproviders.IjpaNovedadHistoriDataProviders;
import com.system.modules.workcontrol.dataproviders.IjpaTipoNovedadDataProviders;

@Service
public class EntyNovedadHistoriService {

    private static final String ESTADO_ACTIVO = "1";

    private static final String ESTADO_INACTIVO = "2";

    private static final int MAX_KEY = 30;

    private static final int MAX_TIPO = 40;

    private static final int MAX_REGISTRO = 50;

    private final IjpaNovedadHistoriDataProviders dataProviders;

    private final IjpaTipoNovedadDataProviders tipoNovedadDataProviders;

    private final EntyOrsordmaordenservicioRepository ordenRepository;

    public EntyNovedadHistoriService(
            final IjpaNovedadHistoriDataProviders dataProviders,
            final IjpaTipoNovedadDataProviders tipoNovedadDataProviders,
            final EntyOrsordmaordenservicioRepository ordenRepository
    ) {
        this.dataProviders = dataProviders;
        this.tipoNovedadDataProviders = tipoNovedadDataProviders;
        this.ordenRepository = ordenRepository;
    }

    @Transactional
    public EntyOrsconfnovedadhistoriResponse createResponse(
            final EntyOrsconfnovedadhistoriDto request
    ) throws EBusinessException {
        EntyOrsconfnovedadhistoriDto normalized =
                normalizeForCreate(request);

        EntyOrsconfnovedadhistoriDto saved =
                dataProviders.save(normalized);

        return buildSingleResponse(
                saved,
                "Novedad registrada correctamente.",
                saved.getOrsIdentifkeyOrde()
        );
    }

    public EntyOrsconfnovedadhistoriResponse getByOrdenResponse(
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

    public EntyOrsconfnovedadhistoriResponse getByTipoResponse(
            final String tipoNovedad
    ) throws EBusinessException {
        validarTexto(
                tipoNovedad,
                "El tipo de novedad es obligatorio."
        );

        return dataProviders.findByTipoResponse(
                tipoNovedad.trim().toUpperCase()
        );
    }

    public EntyOrsconfnovedadhistoriResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyOrsconfnovedadhistoriResponse getAll(
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

    public EntyOrsconfnovedadhistoriResponse getResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsconfnovedadhistoriDto dto =
                dataProviders.get(id);

        if (dto.getOrsPrimarykeyNove() == null) {
            return buildListResponse(
                    new ArrayList<>(),
                    "No se encontró la novedad.",
                    "NA"
            );
        }

        return buildSingleResponse(
                dto,
                "Novedad consultada correctamente.",
                dto.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsconfnovedadhistoriResponse updateResponse(
            final Integer id,
            final EntyOrsconfnovedadhistoriDto request
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID de la novedad es obligatorio."
            );
        }

        EntyOrsconfnovedadhistoriDto current =
                dataProviders.get(id);

        if (current.getOrsPrimarykeyNove() == null) {
            throw new IllegalArgumentException(
                    "No existe la novedad con ID: " + id
            );
        }

        EntyOrsconfnovedadhistoriDto normalized =
                normalizeForUpdate(request, current);

        EntyOrsconfnovedadhistoriDto updated =
                dataProviders.update(id, normalized);

        return buildSingleResponse(
                updated,
                "Novedad actualizada correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsconfnovedadhistoriResponse activarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsconfnovedadhistoriDto current =
                getCurrentOrFail(id);

        current.setOrsEstadoregNove(ESTADO_ACTIVO);

        EntyOrsconfnovedadhistoriDto updated =
                dataProviders.update(id, current);

        return buildSingleResponse(
                updated,
                "Novedad activada correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsconfnovedadhistoriResponse inactivarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsconfnovedadhistoriDto current =
                getCurrentOrFail(id);

        current.setOrsEstadoregNove(ESTADO_INACTIVO);

        EntyOrsconfnovedadhistoriDto updated =
                dataProviders.update(id, current);

        return buildSingleResponse(
                updated,
                "Novedad inactivada correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsconfnovedadhistoriResponse deleteResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsconfnovedadhistoriDto current =
                getCurrentOrFail(id);

        dataProviders.delete(id);

        return buildSingleResponse(
                current,
                "Novedad eliminada correctamente.",
                current.getOrsIdentifkeyOrde()
        );
    }

    private EntyOrsconfnovedadhistoriDto getCurrentOrFail(
            final Integer id
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID de la novedad es obligatorio."
            );
        }

        EntyOrsconfnovedadhistoriDto current =
                dataProviders.get(id);

        if (current.getOrsPrimarykeyNove() == null) {
            throw new IllegalArgumentException(
                    "No existe la novedad con ID: " + id
            );
        }

        return current;
    }

    private EntyOrsconfnovedadhistoriDto normalizeForCreate(
            final EntyOrsconfnovedadhistoriDto request
    ) throws EBusinessException {
        validarBase(request);

        EntyOrsconfnovedadhistoriDto dto =
                normalizeCommon(request);

        dto.setOrsPrimarykeyNove(null);

        if (dto.getOrsIdentifkeyNove() == null
                || dto.getOrsIdentifkeyNove().trim().isEmpty()) {
            dto.setOrsIdentifkeyNove(
                    generarNovedadKey(dto.getOrsIdentifkeyOrde())
            );
        }

        dto.setOrsIdentifkeyNove(
                dto.getOrsIdentifkeyNove().trim().toUpperCase()
        );

        if (dataProviders.existsByNovedadKey(dto.getOrsIdentifkeyNove())) {
            throw new IllegalArgumentException(
                    "Ya existe una novedad con el código: "
                            + dto.getOrsIdentifkeyNove()
            );
        }

        if (dto.getOrsFechreportNove() == null) {
            dto.setOrsFechreportNove(LocalDate.now());
        }

        dto.setOrsEstadoregNove(ESTADO_ACTIVO);

        return dto;
    }

    private EntyOrsconfnovedadhistoriDto normalizeForUpdate(
            final EntyOrsconfnovedadhistoriDto request,
            final EntyOrsconfnovedadhistoriDto current
    ) throws EBusinessException {
        validarBase(request);

        EntyOrsconfnovedadhistoriDto dto =
                normalizeCommon(request);

        dto.setOrsPrimarykeyNove(current.getOrsPrimarykeyNove());
        dto.setOrsIdentifkeyNove(current.getOrsIdentifkeyNove());

        if (dto.getOrsFechreportNove() == null) {
            dto.setOrsFechreportNove(
                    current.getOrsFechreportNove() != null
                            ? current.getOrsFechreportNove()
                            : LocalDate.now()
            );
        }

        if (dto.getOrsEstadoregNove() == null
                || dto.getOrsEstadoregNove().trim().isEmpty()) {
            dto.setOrsEstadoregNove(
                    current.getOrsEstadoregNove() != null
                            ? current.getOrsEstadoregNove()
                            : ESTADO_ACTIVO
            );
        }

        return dto;
    }

    private EntyOrsconfnovedadhistoriDto normalizeCommon(
            final EntyOrsconfnovedadhistoriDto request
    ) {
        EntyOrsconfnovedadhistoriDto dto =
                new EntyOrsconfnovedadhistoriDto();

        dto.setOrsPrimarykeyNove(request.getOrsPrimarykeyNove());
        dto.setOrsIdentifkeyNove(
                limpiarMayuscula(request.getOrsIdentifkeyNove())
        );
        dto.setOrsIdentifkeyOrde(
                limpiarMayuscula(request.getOrsIdentifkeyOrde())
        );
        dto.setOrsFechreportNove(request.getOrsFechreportNove());
        dto.setOrsTiponovedadNovt(
                limpiarMayuscula(request.getOrsTiponovedadNovt())
        );
        dto.setOrsRegistrbaseNove(
                limpiarMayuscula(request.getOrsRegistrbaseNove())
        );
        dto.setOrsRegistrnoveNove(
                limpiarMayuscula(request.getOrsRegistrnoveNove())
        );
        dto.setOrsEstadoregNove(
                limpiarMayuscula(request.getOrsEstadoregNove())
        );

        validarLongitud(
                dto.getOrsIdentifkeyNove(),
                MAX_KEY,
                "código de la novedad"
        );
        validarLongitud(
                dto.getOrsIdentifkeyOrde(),
                MAX_KEY,
                "código de la orden"
        );
        validarLongitud(
                dto.getOrsTiponovedadNovt(),
                MAX_TIPO,
                "tipo de novedad"
        );
        validarLongitud(
                dto.getOrsRegistrbaseNove(),
                MAX_REGISTRO,
                "registro base"
        );
        validarLongitud(
                dto.getOrsRegistrnoveNove(),
                MAX_REGISTRO,
                "registro novedad"
        );

        return dto;
    }

    private void validarBase(
            final EntyOrsconfnovedadhistoriDto request
    ) throws EBusinessException {
        if (request == null) {
            throw new IllegalArgumentException(
                    "La solicitud de la novedad es obligatoria."
            );
        }

        validarTexto(
                request.getOrsIdentifkeyOrde(),
                "El código de la orden de servicio es obligatorio."
        );

        validarTexto(
                request.getOrsTiponovedadNovt(),
                "El tipo de novedad es obligatorio."
        );

        String ordenKey = request.getOrsIdentifkeyOrde()
                .trim()
                .toUpperCase();

        String tipoNovedad = request.getOrsTiponovedadNovt()
                .trim()
                .toUpperCase();

        if (!ordenRepository.existsByOrsIdentifkeyOrde(ordenKey)) {
            throw new IllegalArgumentException(
                    "No existe la orden de servicio: " + ordenKey
            );
        }

        if (!tipoNovedadDataProviders.existsByTipoNovedad(tipoNovedad)) {
            throw new IllegalArgumentException(
                    "No existe el tipo de novedad: " + tipoNovedad
            );
        }
    }

    private String generarNovedadKey(
            final String ordenKey
    ) throws EBusinessException {
        List<EntyOrsconfnovedadhistoriDto> existentes =
                dataProviders.findByOrden(ordenKey);

        String cleanOrder = ordenKey
                .toUpperCase()
                .replaceAll("[^A-Z0-9]", "");

        int maxOrderLength = 30 - "NOVE-".length() - "-000".length();

        if (cleanOrder.length() > maxOrderLength) {
            cleanOrder = cleanOrder.substring(0, maxOrderLength);
        }

        int consecutivo = existentes.size() + 1;

        String generated;

        do {
            generated = "NOVE-"
                    + cleanOrder
                    + "-"
                    + String.format("%03d", consecutivo);

            consecutivo++;
        } while (dataProviders.existsByNovedadKey(generated));

        return generated;
    }

    private EntyOrsconfnovedadhistoriResponse buildSingleResponse(
            final EntyOrsconfnovedadhistoriDto dto,
            final String message,
            final String parentKey
    ) {
        List<EntyOrsconfnovedadhistoriDto> data =
                new ArrayList<>();

        if (dto != null) {
            data.add(dto);
        }

        return buildListResponse(data, message, parentKey);
    }

    private EntyOrsconfnovedadhistoriResponse buildListResponse(
            final List<EntyOrsconfnovedadhistoriDto> data,
            final String message,
            final String parentKey
    ) {
        List<EntyOrsconfnovedadhistoriDto> safeData =
                data != null ? data : new ArrayList<>();

        EntyOrsconfnovedadhistoriResponse response =
                new EntyOrsconfnovedadhistoriResponse();

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