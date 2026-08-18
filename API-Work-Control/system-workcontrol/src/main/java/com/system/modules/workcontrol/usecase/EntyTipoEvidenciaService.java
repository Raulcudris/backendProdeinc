package com.system.modules.workcontrol.usecase;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.dataproviders.IjpaTipoEvidenciaDataProviders;

@Service
public class EntyTipoEvidenciaService {

    private static final String ESTADO_ACTIVO = "1";
    private static final String ESTADO_INACTIVO = "2";
    private static final String TIPO_REGISTRO_NORMAL = "1";

    private static final int MAX_KEY = 30;
    private static final int MAX_DESCRIPCION = 150;

    private final IjpaTipoEvidenciaDataProviders dataProviders;

    public EntyTipoEvidenciaService(
            final IjpaTipoEvidenciaDataProviders dataProviders
    ) {
        this.dataProviders = dataProviders;
    }

    @Transactional
    public EntyEvitipmatipoevidenciaResponse createResponse(
            final EntyEvitipmatipoevidenciaDto request
    ) throws EBusinessException {
        EntyEvitipmatipoevidenciaDto normalized =
                normalizeForCreate(request);

        EntyEvitipmatipoevidenciaDto saved =
                dataProviders.save(normalized);

        return buildSingleResponse(
                saved,
                "Tipo de evidencia creado correctamente.",
                saved.getEviIdentifkeyTiev()
        );
    }

    public EntyEvitipmatipoevidenciaResponse getByEstadoResponse(
            final String estado
    ) throws EBusinessException {
        validarTexto(estado, "El estado es obligatorio.");

        return dataProviders.findByEstadoResponse(
                estado.trim().toUpperCase()
        );
    }

    public EntyEvitipmatipoevidenciaResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyEvitipmatipoevidenciaResponse getAll(
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

    public EntyEvitipmatipoevidenciaResponse getResponse(
            final Integer id
    ) throws EBusinessException {
        EntyEvitipmatipoevidenciaDto dto =
                dataProviders.get(id);

        if (dto.getEviPrimarykeyTiev() == null) {
            return buildListResponse(
                    new ArrayList<>(),
                    "No se encontró el tipo de evidencia.",
                    "NA"
            );
        }

        return buildSingleResponse(
                dto,
                "Tipo de evidencia consultado correctamente.",
                dto.getEviIdentifkeyTiev()
        );
    }

    @Transactional
    public EntyEvitipmatipoevidenciaResponse updateResponse(
            final Integer id,
            final EntyEvitipmatipoevidenciaDto request
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID del tipo de evidencia es obligatorio."
            );
        }

        EntyEvitipmatipoevidenciaDto current =
                dataProviders.get(id);

        if (current.getEviPrimarykeyTiev() == null) {
            throw new IllegalArgumentException(
                    "No existe el tipo de evidencia con ID: " + id
            );
        }

        EntyEvitipmatipoevidenciaDto normalized =
                normalizeForUpdate(request, current);

        EntyEvitipmatipoevidenciaDto updated =
                dataProviders.update(id, normalized);

        return buildSingleResponse(
                updated,
                "Tipo de evidencia actualizado correctamente.",
                updated.getEviIdentifkeyTiev()
        );
    }

    @Transactional
    public EntyEvitipmatipoevidenciaResponse activarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyEvitipmatipoevidenciaDto current =
                getCurrentOrFail(id);

        current.setEviEstadoregTiev(ESTADO_ACTIVO);

        EntyEvitipmatipoevidenciaDto updated =
                dataProviders.update(id, current);

        return buildSingleResponse(
                updated,
                "Tipo de evidencia activado correctamente.",
                updated.getEviIdentifkeyTiev()
        );
    }

    @Transactional
    public EntyEvitipmatipoevidenciaResponse inactivarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyEvitipmatipoevidenciaDto current =
                getCurrentOrFail(id);

        current.setEviEstadoregTiev(ESTADO_INACTIVO);

        EntyEvitipmatipoevidenciaDto updated =
                dataProviders.update(id, current);

        return buildSingleResponse(
                updated,
                "Tipo de evidencia inactivado correctamente.",
                updated.getEviIdentifkeyTiev()
        );
    }

    @Transactional
    public EntyEvitipmatipoevidenciaResponse deleteResponse(
            final Integer id
    ) throws EBusinessException {
        EntyEvitipmatipoevidenciaDto current =
                getCurrentOrFail(id);

        dataProviders.delete(id);

        return buildSingleResponse(
                current,
                "Tipo de evidencia eliminado correctamente.",
                current.getEviIdentifkeyTiev()
        );
    }

    private EntyEvitipmatipoevidenciaDto getCurrentOrFail(
            final Integer id
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID del tipo de evidencia es obligatorio."
            );
        }

        EntyEvitipmatipoevidenciaDto current =
                dataProviders.get(id);

        if (current.getEviPrimarykeyTiev() == null) {
            throw new IllegalArgumentException(
                    "No existe el tipo de evidencia con ID: " + id
            );
        }

        return current;
    }

    private EntyEvitipmatipoevidenciaDto normalizeForCreate(
            final EntyEvitipmatipoevidenciaDto request
    ) throws EBusinessException {
        validarBase(request);

        EntyEvitipmatipoevidenciaDto dto =
                normalizeCommon(request);

        dto.setEviPrimarykeyTiev(null);
        dto.setEviTiporegistTiev(TIPO_REGISTRO_NORMAL);
        dto.setEviEstadoregTiev(ESTADO_ACTIVO);

        if (dataProviders.existsByTipoEvidenciaKey(
                dto.getEviIdentifkeyTiev()
        )) {
            throw new IllegalArgumentException(
                    "Ya existe el tipo de evidencia: "
                            + dto.getEviIdentifkeyTiev()
            );
        }

        return dto;
    }

    private EntyEvitipmatipoevidenciaDto normalizeForUpdate(
            final EntyEvitipmatipoevidenciaDto request,
            final EntyEvitipmatipoevidenciaDto current
    ) throws EBusinessException {
        validarBase(request);

        EntyEvitipmatipoevidenciaDto dto =
                normalizeCommon(request);

        dto.setEviPrimarykeyTiev(current.getEviPrimarykeyTiev());

        if (dto.getEviTiporegistTiev() == null) {
            dto.setEviTiporegistTiev(
                    current.getEviTiporegistTiev() != null
                            ? current.getEviTiporegistTiev()
                            : TIPO_REGISTRO_NORMAL
            );
        }

        if (dto.getEviEstadoregTiev() == null) {
            dto.setEviEstadoregTiev(
                    current.getEviEstadoregTiev() != null
                            ? current.getEviEstadoregTiev()
                            : ESTADO_ACTIVO
            );
        }

        if (!dto.getEviIdentifkeyTiev()
                .equalsIgnoreCase(current.getEviIdentifkeyTiev())
                && dataProviders.existsByTipoEvidenciaKey(
                dto.getEviIdentifkeyTiev()
        )) {
            throw new IllegalArgumentException(
                    "Ya existe el tipo de evidencia: "
                            + dto.getEviIdentifkeyTiev()
            );
        }

        return dto;
    }

    private EntyEvitipmatipoevidenciaDto normalizeCommon(
            final EntyEvitipmatipoevidenciaDto request
    ) {
        EntyEvitipmatipoevidenciaDto dto =
                new EntyEvitipmatipoevidenciaDto();

        dto.setEviPrimarykeyTiev(request.getEviPrimarykeyTiev());
        dto.setEviIdentifkeyTiev(
                limpiarMayuscula(request.getEviIdentifkeyTiev())
        );
        dto.setEviDescripcionTiev(
                limpiarTexto(request.getEviDescripcionTiev())
        );
        dto.setEviTiporegistTiev(
                limpiarMayuscula(request.getEviTiporegistTiev())
        );
        dto.setEviEstadoregTiev(
                limpiarMayuscula(request.getEviEstadoregTiev())
        );

        validarLongitud(
                dto.getEviIdentifkeyTiev(),
                MAX_KEY,
                "código del tipo de evidencia"
        );
        validarLongitud(
                dto.getEviDescripcionTiev(),
                MAX_DESCRIPCION,
                "descripción"
        );

        return dto;
    }

    private void validarBase(
            final EntyEvitipmatipoevidenciaDto request
    ) {
        if (request == null) {
            throw new IllegalArgumentException(
                    "La solicitud del tipo de evidencia es obligatoria."
            );
        }

        validarTexto(
                request.getEviIdentifkeyTiev(),
                "El código del tipo de evidencia es obligatorio."
        );

        validarTexto(
                request.getEviDescripcionTiev(),
                "La descripción del tipo de evidencia es obligatoria."
        );
    }

    private EntyEvitipmatipoevidenciaResponse buildSingleResponse(
            final EntyEvitipmatipoevidenciaDto dto,
            final String message,
            final String parentKey
    ) {
        List<EntyEvitipmatipoevidenciaDto> data =
                new ArrayList<>();

        if (dto != null) {
            data.add(dto);
        }

        return buildListResponse(data, message, parentKey);
    }

    private EntyEvitipmatipoevidenciaResponse buildListResponse(
            final List<EntyEvitipmatipoevidenciaDto> data,
            final String message,
            final String parentKey
    ) {
        List<EntyEvitipmatipoevidenciaDto> safeData =
                data != null ? data : new ArrayList<>();

        EntyEvitipmatipoevidenciaResponse response =
                new EntyEvitipmatipoevidenciaResponse();

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