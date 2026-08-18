package com.system.modules.workcontrol.usecase;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.dataproviders.IjpaEvidenciaDataProviders;
import com.system.modules.workcontrol.dataproviders.IjpaReferenciaEvidenciaDataProviders;

@Service
public class EntyReferenciaEvidenciaService {

    private static final String ESTADO_ACTIVO = "1";
    private static final String ESTADO_INACTIVO = "2";
    private static final String TIPO_REGISTRO_NORMAL = "1";

    private static final int MAX_KEY = 30;
    private static final int MAX_TIPO_REGISTRO = 50;
    private static final int MAX_IDENTIF_REGISTRO = 50;
    private static final int MAX_OBSERVACION = 500;

    private final IjpaReferenciaEvidenciaDataProviders dataProviders;
    private final IjpaEvidenciaDataProviders evidenciaDataProviders;

    public EntyReferenciaEvidenciaService(
            final IjpaReferenciaEvidenciaDataProviders dataProviders,
            final IjpaEvidenciaDataProviders evidenciaDataProviders
    ) {
        this.dataProviders = dataProviders;
        this.evidenciaDataProviders = evidenciaDataProviders;
    }

    @Transactional
    public EntyEvirefmdreferenciaResponse createResponse(
            final EntyEvirefmdreferenciaDto request
    ) throws EBusinessException {
        EntyEvirefmdreferenciaDto normalized =
                normalizeForCreate(request);

        EntyEvirefmdreferenciaDto saved =
                dataProviders.save(normalized);

        return buildSingleResponse(
                saved,
                "Referencia de evidencia creada correctamente.",
                saved.getEviIdentifkeyEvid()
        );
    }

    public EntyEvirefmdreferenciaResponse getByEvidenciaResponse(
            final String evidenciaKey
    ) throws EBusinessException {
        validarTexto(
                evidenciaKey,
                "El código de evidencia es obligatorio."
        );

        return dataProviders.findByEvidenciaResponse(
                evidenciaKey.trim().toUpperCase()
        );
    }

    public EntyEvirefmdreferenciaResponse getByRegistroResponse(
            final String tipoRegistro,
            final String identificadorRegistro
    ) throws EBusinessException {
        validarTexto(
                tipoRegistro,
                "El tipo de registro es obligatorio."
        );

        validarTexto(
                identificadorRegistro,
                "El identificador del registro es obligatorio."
        );

        return dataProviders.findByRegistroResponse(
                tipoRegistro.trim().toUpperCase(),
                identificadorRegistro.trim().toUpperCase()
        );
    }

    public EntyEvirefmdreferenciaResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyEvirefmdreferenciaResponse getAll(
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

    public EntyEvirefmdreferenciaResponse getResponse(
            final Integer id
    ) throws EBusinessException {
        EntyEvirefmdreferenciaDto dto =
                dataProviders.get(id);

        if (dto.getEviPrimarykeyRefe() == null) {
            return buildListResponse(
                    new ArrayList<>(),
                    "No se encontró la referencia de evidencia.",
                    "NA"
            );
        }

        return buildSingleResponse(
                dto,
                "Referencia de evidencia consultada correctamente.",
                dto.getEviIdentifkeyEvid()
        );
    }

    @Transactional
    public EntyEvirefmdreferenciaResponse updateResponse(
            final Integer id,
            final EntyEvirefmdreferenciaDto request
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID de la referencia es obligatorio."
            );
        }

        EntyEvirefmdreferenciaDto current =
                dataProviders.get(id);

        if (current.getEviPrimarykeyRefe() == null) {
            throw new IllegalArgumentException(
                    "No existe la referencia con ID: " + id
            );
        }

        EntyEvirefmdreferenciaDto normalized =
                normalizeForUpdate(request, current);

        EntyEvirefmdreferenciaDto updated =
                dataProviders.update(id, normalized);

        return buildSingleResponse(
                updated,
                "Referencia de evidencia actualizada correctamente.",
                updated.getEviIdentifkeyEvid()
        );
    }

    @Transactional
    public EntyEvirefmdreferenciaResponse activarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyEvirefmdreferenciaDto current =
                getCurrentOrFail(id);

        current.setEviEstadoregRefe(ESTADO_ACTIVO);

        EntyEvirefmdreferenciaDto updated =
                dataProviders.update(id, current);

        return buildSingleResponse(
                updated,
                "Referencia de evidencia activada correctamente.",
                updated.getEviIdentifkeyEvid()
        );
    }

    @Transactional
    public EntyEvirefmdreferenciaResponse inactivarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyEvirefmdreferenciaDto current =
                getCurrentOrFail(id);

        current.setEviEstadoregRefe(ESTADO_INACTIVO);

        EntyEvirefmdreferenciaDto updated =
                dataProviders.update(id, current);

        return buildSingleResponse(
                updated,
                "Referencia de evidencia inactivada correctamente.",
                updated.getEviIdentifkeyEvid()
        );
    }

    @Transactional
    public EntyEvirefmdreferenciaResponse deleteResponse(
            final Integer id
    ) throws EBusinessException {
        EntyEvirefmdreferenciaDto current =
                getCurrentOrFail(id);

        dataProviders.delete(id);

        return buildSingleResponse(
                current,
                "Referencia de evidencia eliminada correctamente.",
                current.getEviIdentifkeyEvid()
        );
    }

    private EntyEvirefmdreferenciaDto getCurrentOrFail(
            final Integer id
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID de la referencia es obligatorio."
            );
        }

        EntyEvirefmdreferenciaDto current =
                dataProviders.get(id);

        if (current.getEviPrimarykeyRefe() == null) {
            throw new IllegalArgumentException(
                    "No existe la referencia con ID: " + id
            );
        }

        return current;
    }

    private EntyEvirefmdreferenciaDto normalizeForCreate(
            final EntyEvirefmdreferenciaDto request
    ) throws EBusinessException {
        validarBase(request);

        EntyEvirefmdreferenciaDto dto =
                normalizeCommon(request);

        dto.setEviPrimarykeyRefe(null);

        if (dto.getEviIdentifkeyRefe() == null) {
            dto.setEviIdentifkeyRefe(generarReferenciaKey());
        }

        if (dataProviders.existsByReferenciaKey(
                dto.getEviIdentifkeyRefe()
        )) {
            throw new IllegalArgumentException(
                    "Ya existe una referencia con el código: "
                            + dto.getEviIdentifkeyRefe()
            );
        }

        dto.setEviTiporegistRefe(TIPO_REGISTRO_NORMAL);
        dto.setEviEstadoregRefe(ESTADO_ACTIVO);

        return dto;
    }

    private EntyEvirefmdreferenciaDto normalizeForUpdate(
            final EntyEvirefmdreferenciaDto request,
            final EntyEvirefmdreferenciaDto current
    ) throws EBusinessException {
        validarBase(request);

        EntyEvirefmdreferenciaDto dto =
                normalizeCommon(request);

        dto.setEviPrimarykeyRefe(current.getEviPrimarykeyRefe());
        dto.setEviIdentifkeyRefe(current.getEviIdentifkeyRefe());

        if (dto.getEviTiporegistRefe() == null) {
            dto.setEviTiporegistRefe(
                    current.getEviTiporegistRefe() != null
                            ? current.getEviTiporegistRefe()
                            : TIPO_REGISTRO_NORMAL
            );
        }

        if (dto.getEviEstadoregRefe() == null) {
            dto.setEviEstadoregRefe(
                    current.getEviEstadoregRefe() != null
                            ? current.getEviEstadoregRefe()
                            : ESTADO_ACTIVO
            );
        }

        return dto;
    }

    private EntyEvirefmdreferenciaDto normalizeCommon(
            final EntyEvirefmdreferenciaDto request
    ) {
        EntyEvirefmdreferenciaDto dto =
                new EntyEvirefmdreferenciaDto();

        dto.setEviPrimarykeyRefe(request.getEviPrimarykeyRefe());
        dto.setEviIdentifkeyRefe(
                limpiarMayuscula(request.getEviIdentifkeyRefe())
        );
        dto.setEviIdentifkeyEvid(
                limpiarMayuscula(request.getEviIdentifkeyEvid())
        );
        dto.setEviTiporegistroRefe(
                limpiarMayuscula(request.getEviTiporegistroRefe())
        );
        dto.setEviIdentifregistroRefe(
                limpiarMayuscula(request.getEviIdentifregistroRefe())
        );
        dto.setEviObservacionRefe(
                limpiarTexto(request.getEviObservacionRefe())
        );
        dto.setEviTiporegistRefe(
                limpiarMayuscula(request.getEviTiporegistRefe())
        );
        dto.setEviEstadoregRefe(
                limpiarMayuscula(request.getEviEstadoregRefe())
        );

        validarLongitud(dto.getEviIdentifkeyRefe(), MAX_KEY, "código referencia");
        validarLongitud(dto.getEviIdentifkeyEvid(), MAX_KEY, "código evidencia");
        validarLongitud(dto.getEviTiporegistroRefe(), MAX_TIPO_REGISTRO, "tipo registro");
        validarLongitud(dto.getEviIdentifregistroRefe(), MAX_IDENTIF_REGISTRO, "identificador registro");
        validarLongitud(dto.getEviObservacionRefe(), MAX_OBSERVACION, "observación");

        return dto;
    }

    private void validarBase(
            final EntyEvirefmdreferenciaDto request
    ) throws EBusinessException {
        if (request == null) {
            throw new IllegalArgumentException(
                    "La solicitud de referencia de evidencia es obligatoria."
            );
        }

        validarTexto(
                request.getEviIdentifkeyEvid(),
                "El código de evidencia es obligatorio."
        );

        validarTexto(
                request.getEviTiporegistroRefe(),
                "El tipo de registro es obligatorio."
        );

        validarTexto(
                request.getEviIdentifregistroRefe(),
                "El identificador del registro es obligatorio."
        );

        String evidenciaKey = request.getEviIdentifkeyEvid()
                .trim()
                .toUpperCase();

        if (!evidenciaDataProviders.existsByEvidenciaKey(evidenciaKey)) {
            throw new IllegalArgumentException(
                    "No existe la evidencia: " + evidenciaKey
            );
        }
    }

    private String generarReferenciaKey()
            throws EBusinessException {
        long total = 0L;

        EntyEvirefmdreferenciaResponse response =
                dataProviders.getAll(1, 1, "TEXT", "");

        if (response.getRspPagination() != null) {
            total = response.getRspPagination().getTotalResults();
        }

        long consecutivo = total + 1;
        String generated;

        do {
            generated = "REFE-" + String.format("%06d", consecutivo);
            consecutivo++;
        } while (dataProviders.existsByReferenciaKey(generated));

        return generated;
    }

    private EntyEvirefmdreferenciaResponse buildSingleResponse(
            final EntyEvirefmdreferenciaDto dto,
            final String message,
            final String parentKey
    ) {
        List<EntyEvirefmdreferenciaDto> data =
                new ArrayList<>();

        if (dto != null) {
            data.add(dto);
        }

        return buildListResponse(data, message, parentKey);
    }

    private EntyEvirefmdreferenciaResponse buildListResponse(
            final List<EntyEvirefmdreferenciaDto> data,
            final String message,
            final String parentKey
    ) {
        List<EntyEvirefmdreferenciaDto> safeData =
                data != null ? data : new ArrayList<>();

        EntyEvirefmdreferenciaResponse response =
                new EntyEvirefmdreferenciaResponse();

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