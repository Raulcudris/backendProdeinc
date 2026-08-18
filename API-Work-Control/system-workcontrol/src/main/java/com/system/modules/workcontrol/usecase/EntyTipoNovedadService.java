package com.system.modules.workcontrol.usecase;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsconfnovedadtiposDto;
import com.system.crosscutting.domain.model.EntyOrsconfnovedadtiposResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.dataproviders.IjpaTipoNovedadDataProviders;

@Service
public class EntyTipoNovedadService {

    private static final String ESTADO_ACTIVO = "1";

    private static final String ESTADO_INACTIVO = "2";

    private static final int MAX_TIPO = 40;

    private static final int MAX_DESCRIPCION = 150;

    private final IjpaTipoNovedadDataProviders dataProviders;

    public EntyTipoNovedadService(
            final IjpaTipoNovedadDataProviders dataProviders
    ) {
        this.dataProviders = dataProviders;
    }

    @Transactional
    public EntyOrsconfnovedadtiposResponse createResponse(
            final EntyOrsconfnovedadtiposDto request
    ) throws EBusinessException {
        EntyOrsconfnovedadtiposDto normalized =
                normalizeForCreate(request);

        EntyOrsconfnovedadtiposDto saved =
                dataProviders.save(normalized);

        return buildSingleResponse(
                saved,
                "Tipo de novedad creado correctamente.",
                saved.getOrsTiponovedadNovt()
        );
    }

    public EntyOrsconfnovedadtiposResponse getByEstadoResponse(
            final String estado
    ) throws EBusinessException {
        validarTexto(
                estado,
                "El estado es obligatorio."
        );

        return dataProviders.findByEstadoResponse(
                estado.trim().toUpperCase()
        );
    }

    public EntyOrsconfnovedadtiposResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyOrsconfnovedadtiposResponse getAll(
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

    public EntyOrsconfnovedadtiposResponse getResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsconfnovedadtiposDto dto =
                dataProviders.get(id);

        if (dto.getOrsPrimarykeyNovt() == null) {
            return buildListResponse(
                    new ArrayList<>(),
                    "No se encontró el tipo de novedad.",
                    "NA"
            );
        }

        return buildSingleResponse(
                dto,
                "Tipo de novedad consultado correctamente.",
                dto.getOrsTiponovedadNovt()
        );
    }

    @Transactional
    public EntyOrsconfnovedadtiposResponse updateResponse(
            final Integer id,
            final EntyOrsconfnovedadtiposDto request
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID del tipo de novedad es obligatorio."
            );
        }

        EntyOrsconfnovedadtiposDto current =
                dataProviders.get(id);

        if (current.getOrsPrimarykeyNovt() == null) {
            throw new IllegalArgumentException(
                    "No existe el tipo de novedad con ID: " + id
            );
        }

        EntyOrsconfnovedadtiposDto normalized =
                normalizeForUpdate(request, current);

        EntyOrsconfnovedadtiposDto updated =
                dataProviders.update(id, normalized);

        return buildSingleResponse(
                updated,
                "Tipo de novedad actualizado correctamente.",
                updated.getOrsTiponovedadNovt()
        );
    }

    @Transactional
    public EntyOrsconfnovedadtiposResponse inactivarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsconfnovedadtiposDto current =
                getCurrentOrFail(id);

        current.setOrsEstadoregNovt(ESTADO_INACTIVO);

        EntyOrsconfnovedadtiposDto updated =
                dataProviders.update(id, current);

        return buildSingleResponse(
                updated,
                "Tipo de novedad inactivado correctamente.",
                updated.getOrsTiponovedadNovt()
        );
    }

    @Transactional
    public EntyOrsconfnovedadtiposResponse activarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsconfnovedadtiposDto current =
                getCurrentOrFail(id);

        current.setOrsEstadoregNovt(ESTADO_ACTIVO);

        EntyOrsconfnovedadtiposDto updated =
                dataProviders.update(id, current);

        return buildSingleResponse(
                updated,
                "Tipo de novedad activado correctamente.",
                updated.getOrsTiponovedadNovt()
        );
    }

    @Transactional
    public EntyOrsconfnovedadtiposResponse deleteResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsconfnovedadtiposDto current =
                getCurrentOrFail(id);

        dataProviders.delete(id);

        return buildSingleResponse(
                current,
                "Tipo de novedad eliminado correctamente.",
                current.getOrsTiponovedadNovt()
        );
    }

    private EntyOrsconfnovedadtiposDto getCurrentOrFail(
            final Integer id
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID del tipo de novedad es obligatorio."
            );
        }

        EntyOrsconfnovedadtiposDto current =
                dataProviders.get(id);

        if (current.getOrsPrimarykeyNovt() == null) {
            throw new IllegalArgumentException(
                    "No existe el tipo de novedad con ID: " + id
            );
        }

        return current;
    }

    private EntyOrsconfnovedadtiposDto normalizeForCreate(
            final EntyOrsconfnovedadtiposDto request
    ) throws EBusinessException {
        validarBase(request);

        EntyOrsconfnovedadtiposDto dto =
                normalizeCommon(request);

        dto.setOrsPrimarykeyNovt(null);
        dto.setOrsEstadoregNovt(ESTADO_ACTIVO);

        if (dataProviders.existsByTipoNovedad(dto.getOrsTiponovedadNovt())) {
            throw new IllegalArgumentException(
                    "Ya existe el tipo de novedad: "
                            + dto.getOrsTiponovedadNovt()
            );
        }

        return dto;
    }

    private EntyOrsconfnovedadtiposDto normalizeForUpdate(
            final EntyOrsconfnovedadtiposDto request,
            final EntyOrsconfnovedadtiposDto current
    ) throws EBusinessException {
        validarBase(request);

        EntyOrsconfnovedadtiposDto dto =
                normalizeCommon(request);

        dto.setOrsPrimarykeyNovt(current.getOrsPrimarykeyNovt());

        if (dto.getOrsEstadoregNovt() == null
                || dto.getOrsEstadoregNovt().trim().isEmpty()) {
            dto.setOrsEstadoregNovt(
                    current.getOrsEstadoregNovt() != null
                            ? current.getOrsEstadoregNovt()
                            : ESTADO_ACTIVO
            );
        }

        if (!dto.getOrsTiponovedadNovt()
                .equalsIgnoreCase(current.getOrsTiponovedadNovt())
                && dataProviders.existsByTipoNovedad(
                dto.getOrsTiponovedadNovt()
        )) {
            throw new IllegalArgumentException(
                    "Ya existe el tipo de novedad: "
                            + dto.getOrsTiponovedadNovt()
            );
        }

        return dto;
    }

    private EntyOrsconfnovedadtiposDto normalizeCommon(
            final EntyOrsconfnovedadtiposDto request
    ) {
        EntyOrsconfnovedadtiposDto dto =
                new EntyOrsconfnovedadtiposDto();

        dto.setOrsPrimarykeyNovt(request.getOrsPrimarykeyNovt());
        dto.setOrsTiponovedadNovt(
                limpiarMayuscula(request.getOrsTiponovedadNovt())
        );
        dto.setOrsDescnovedadNovt(
                limpiarTexto(request.getOrsDescnovedadNovt())
        );
        dto.setOrsEstadoregNovt(
                limpiarMayuscula(request.getOrsEstadoregNovt())
        );

        validarLongitud(
                dto.getOrsTiponovedadNovt(),
                MAX_TIPO,
                "tipo de novedad"
        );
        validarLongitud(
                dto.getOrsDescnovedadNovt(),
                MAX_DESCRIPCION,
                "descripción"
        );

        return dto;
    }

    private void validarBase(
            final EntyOrsconfnovedadtiposDto request
    ) {
        if (request == null) {
            throw new IllegalArgumentException(
                    "La solicitud del tipo de novedad es obligatoria."
            );
        }

        validarTexto(
                request.getOrsTiponovedadNovt(),
                "El tipo de novedad es obligatorio."
        );
    }

    private EntyOrsconfnovedadtiposResponse buildSingleResponse(
            final EntyOrsconfnovedadtiposDto dto,
            final String message,
            final String parentKey
    ) {
        List<EntyOrsconfnovedadtiposDto> data =
                new ArrayList<>();

        if (dto != null) {
            data.add(dto);
        }

        return buildListResponse(data, message, parentKey);
    }

    private EntyOrsconfnovedadtiposResponse buildListResponse(
            final List<EntyOrsconfnovedadtiposDto> data,
            final String message,
            final String parentKey
    ) {
        List<EntyOrsconfnovedadtiposDto> safeData =
                data != null ? data : new ArrayList<>();

        EntyOrsconfnovedadtiposResponse response =
                new EntyOrsconfnovedadtiposResponse();

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