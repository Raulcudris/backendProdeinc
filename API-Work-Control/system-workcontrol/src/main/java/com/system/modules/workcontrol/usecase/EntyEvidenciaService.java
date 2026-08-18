package com.system.modules.workcontrol.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyEvievimaevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvievimaevidenciaResponse;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.dataproviders.IjpaEvidenciaDataProviders;
import com.system.modules.workcontrol.dataproviders.IjpaReferenciaEvidenciaDataProviders;
import com.system.modules.workcontrol.dataproviders.IjpaTipoEvidenciaDataProviders;

@Service
public class EntyEvidenciaService {

    private static final String ESTADO_ACTIVO = "1";
    private static final String ESTADO_INACTIVO = "2";
    private static final String TIPO_REGISTRO_NORMAL = "1";

    private static final int MAX_KEY = 30;
    private static final int MAX_NOMBRE_ARCHIVO = 200;
    private static final int MAX_DESCRIPCION = 500;
    private static final int MAX_URL = 700;

    private static final BigDecimal LATITUD_MIN = new BigDecimal("-90");
    private static final BigDecimal LATITUD_MAX = new BigDecimal("90");
    private static final BigDecimal LONGITUD_MIN = new BigDecimal("-180");
    private static final BigDecimal LONGITUD_MAX = new BigDecimal("180");

    private final IjpaEvidenciaDataProviders dataProviders;
    private final IjpaTipoEvidenciaDataProviders tipoEvidenciaDataProviders;
    private final IjpaReferenciaEvidenciaDataProviders referenciaDataProviders;

    public EntyEvidenciaService(
            final IjpaEvidenciaDataProviders dataProviders,
            final IjpaTipoEvidenciaDataProviders tipoEvidenciaDataProviders,
            final IjpaReferenciaEvidenciaDataProviders referenciaDataProviders
    ) {
        this.dataProviders = dataProviders;
        this.tipoEvidenciaDataProviders = tipoEvidenciaDataProviders;
        this.referenciaDataProviders = referenciaDataProviders;
    }

    @Transactional
    public EntyEvievimaevidenciaResponse createResponse(
            final EntyEvievimaevidenciaDto request
    ) throws EBusinessException {
        EntyEvievimaevidenciaDto normalized =
                normalizeForCreate(request);

        EntyEvievimaevidenciaDto saved =
                dataProviders.save(normalized);

        return buildSingleResponse(
                saved,
                "Evidencia creada correctamente.",
                saved.getEviIdentifkeyEvid()
        );
    }

    @Transactional
    public EntyEvievimaevidenciaResponse createAndReferenceResponse(
            final EntyEvievimaevidenciaDto request,
            final String tipoRegistro,
            final String identificadorRegistro,
            final String observacionReferencia
    ) throws EBusinessException {
        validarTexto(
                tipoRegistro,
                "El tipo de registro de referencia es obligatorio."
        );

        validarTexto(
                identificadorRegistro,
                "El identificador del registro de referencia es obligatorio."
        );

        EntyEvievimaevidenciaDto normalized =
                normalizeForCreate(request);

        EntyEvievimaevidenciaDto saved =
                dataProviders.save(normalized);

        EntyEvirefmdreferenciaDto referencia =
                new EntyEvirefmdreferenciaDto();

        referencia.setEviIdentifkeyRefe(generarReferenciaKey());
        referencia.setEviIdentifkeyEvid(saved.getEviIdentifkeyEvid());
        referencia.setEviTiporegistroRefe(limpiarMayuscula(tipoRegistro));
        referencia.setEviIdentifregistroRefe(
                limpiarMayuscula(identificadorRegistro)
        );
        referencia.setEviObservacionRefe(
                limpiarTexto(observacionReferencia)
        );
        referencia.setEviTiporegistRefe(TIPO_REGISTRO_NORMAL);
        referencia.setEviEstadoregRefe(ESTADO_ACTIVO);

        referenciaDataProviders.save(referencia);

        return buildSingleResponse(
                saved,
                "Evidencia creada y referenciada correctamente.",
                saved.getEviIdentifkeyEvid()
        );
    }

    public EntyEvievimaevidenciaResponse getByKeyResponse(
            final String evidenciaKey
    ) throws EBusinessException {
        validarTexto(
                evidenciaKey,
                "El código de la evidencia es obligatorio."
        );

        String key = evidenciaKey.trim().toUpperCase();

        EntyEvievimaevidenciaDto dto =
                dataProviders.findByEvidenciaKey(key);

        if (dto.getEviPrimarykeyEvid() == null) {
            return buildListResponse(
                    new ArrayList<>(),
                    "No se encontró la evidencia.",
                    key
            );
        }

        return buildSingleResponse(
                dto,
                "Evidencia consultada correctamente.",
                dto.getEviIdentifkeyEvid()
        );
    }

    public EntyEvievimaevidenciaResponse getByTipoResponse(
            final String tipoEvidenciaKey
    ) throws EBusinessException {
        validarTexto(
                tipoEvidenciaKey,
                "El tipo de evidencia es obligatorio."
        );

        return dataProviders.findByTipoResponse(
                tipoEvidenciaKey.trim().toUpperCase()
        );
    }

    public EntyEvievimaevidenciaResponse getByEstadoResponse(
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

    public EntyEvievimaevidenciaResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyEvievimaevidenciaResponse getAll(
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

    public EntyEvievimaevidenciaResponse getResponse(
            final Integer id
    ) throws EBusinessException {
        EntyEvievimaevidenciaDto dto =
                dataProviders.get(id);

        if (dto.getEviPrimarykeyEvid() == null) {
            return buildListResponse(
                    new ArrayList<>(),
                    "No se encontró la evidencia.",
                    "NA"
            );
        }

        return buildSingleResponse(
                dto,
                "Evidencia consultada correctamente.",
                dto.getEviIdentifkeyEvid()
        );
    }

    @Transactional
    public EntyEvievimaevidenciaResponse updateResponse(
            final Integer id,
            final EntyEvievimaevidenciaDto request
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID de la evidencia es obligatorio."
            );
        }

        EntyEvievimaevidenciaDto current =
                dataProviders.get(id);

        if (current.getEviPrimarykeyEvid() == null) {
            throw new IllegalArgumentException(
                    "No existe la evidencia con ID: " + id
            );
        }

        EntyEvievimaevidenciaDto normalized =
                normalizeForUpdate(request, current);

        EntyEvievimaevidenciaDto updated =
                dataProviders.update(id, normalized);

        return buildSingleResponse(
                updated,
                "Evidencia actualizada correctamente.",
                updated.getEviIdentifkeyEvid()
        );
    }

    @Transactional
    public EntyEvievimaevidenciaResponse activarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyEvievimaevidenciaDto current =
                getCurrentOrFail(id);

        current.setEviEstadoregEvid(ESTADO_ACTIVO);

        EntyEvievimaevidenciaDto updated =
                dataProviders.update(id, current);

        return buildSingleResponse(
                updated,
                "Evidencia activada correctamente.",
                updated.getEviIdentifkeyEvid()
        );
    }

    @Transactional
    public EntyEvievimaevidenciaResponse inactivarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyEvievimaevidenciaDto current =
                getCurrentOrFail(id);

        current.setEviEstadoregEvid(ESTADO_INACTIVO);

        EntyEvievimaevidenciaDto updated =
                dataProviders.update(id, current);

        return buildSingleResponse(
                updated,
                "Evidencia inactivada correctamente.",
                updated.getEviIdentifkeyEvid()
        );
    }

    @Transactional
    public EntyEvievimaevidenciaResponse deleteResponse(
            final Integer id
    ) throws EBusinessException {
        EntyEvievimaevidenciaDto current =
                getCurrentOrFail(id);

        dataProviders.delete(id);

        return buildSingleResponse(
                current,
                "Evidencia eliminada correctamente.",
                current.getEviIdentifkeyEvid()
        );
    }

    private EntyEvievimaevidenciaDto getCurrentOrFail(
            final Integer id
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID de la evidencia es obligatorio."
            );
        }

        EntyEvievimaevidenciaDto current =
                dataProviders.get(id);

        if (current.getEviPrimarykeyEvid() == null) {
            throw new IllegalArgumentException(
                    "No existe la evidencia con ID: " + id
            );
        }

        return current;
    }

    private EntyEvievimaevidenciaDto normalizeForCreate(
            final EntyEvievimaevidenciaDto request
    ) throws EBusinessException {
        validarBase(request);

        EntyEvievimaevidenciaDto dto =
                normalizeCommon(request);

        dto.setEviPrimarykeyEvid(null);

        if (dto.getEviIdentifkeyEvid() == null) {
            dto.setEviIdentifkeyEvid(generarEvidenciaKey());
        }

        if (dataProviders.existsByEvidenciaKey(
                dto.getEviIdentifkeyEvid()
        )) {
            throw new IllegalArgumentException(
                    "Ya existe una evidencia con el código: "
                            + dto.getEviIdentifkeyEvid()
            );
        }

        if (dto.getEviFechacapturaEvid() == null) {
            dto.setEviFechacapturaEvid(LocalDate.now());
        }

        dto.setEviTiporegistEvid(TIPO_REGISTRO_NORMAL);
        dto.setEviEstadoregEvid(ESTADO_ACTIVO);

        return dto;
    }

    private EntyEvievimaevidenciaDto normalizeForUpdate(
            final EntyEvievimaevidenciaDto request,
            final EntyEvievimaevidenciaDto current
    ) throws EBusinessException {
        validarBase(request);

        EntyEvievimaevidenciaDto dto =
                normalizeCommon(request);

        dto.setEviPrimarykeyEvid(current.getEviPrimarykeyEvid());
        dto.setEviIdentifkeyEvid(current.getEviIdentifkeyEvid());

        if (dto.getEviFechacapturaEvid() == null) {
            dto.setEviFechacapturaEvid(
                    current.getEviFechacapturaEvid() != null
                            ? current.getEviFechacapturaEvid()
                            : LocalDate.now()
            );
        }

        if (dto.getEviTiporegistEvid() == null) {
            dto.setEviTiporegistEvid(
                    current.getEviTiporegistEvid() != null
                            ? current.getEviTiporegistEvid()
                            : TIPO_REGISTRO_NORMAL
            );
        }

        if (dto.getEviEstadoregEvid() == null) {
            dto.setEviEstadoregEvid(
                    current.getEviEstadoregEvid() != null
                            ? current.getEviEstadoregEvid()
                            : ESTADO_ACTIVO
            );
        }

        return dto;
    }

    private EntyEvievimaevidenciaDto normalizeCommon(
            final EntyEvievimaevidenciaDto request
    ) {
        EntyEvievimaevidenciaDto dto =
                new EntyEvievimaevidenciaDto();

        dto.setEviPrimarykeyEvid(request.getEviPrimarykeyEvid());
        dto.setEviIdentifkeyEvid(
                limpiarMayuscula(request.getEviIdentifkeyEvid())
        );
        dto.setEviIdentifkeyTiev(
                limpiarMayuscula(request.getEviIdentifkeyTiev())
        );
        dto.setEviNombrearchivoEvid(
                limpiarTexto(request.getEviNombrearchivoEvid())
        );
        dto.setEviDescripcionEvid(
                limpiarTexto(request.getEviDescripcionEvid())
        );
        dto.setEviUrlarchivoEvid(
                limpiarTexto(request.getEviUrlarchivoEvid())
        );
        dto.setEviFechacapturaEvid(request.getEviFechacapturaEvid());
        dto.setEviLatitudEvid(request.getEviLatitudEvid());
        dto.setEviLongitudEvid(request.getEviLongitudEvid());
        dto.setEviTiporegistEvid(
                limpiarMayuscula(request.getEviTiporegistEvid())
        );
        dto.setEviEstadoregEvid(
                limpiarMayuscula(request.getEviEstadoregEvid())
        );

        validarLongitud(
                dto.getEviIdentifkeyEvid(),
                MAX_KEY,
                "código evidencia"
        );
        validarLongitud(
                dto.getEviIdentifkeyTiev(),
                MAX_KEY,
                "tipo evidencia"
        );
        validarLongitud(
                dto.getEviNombrearchivoEvid(),
                MAX_NOMBRE_ARCHIVO,
                "nombre archivo"
        );
        validarLongitud(
                dto.getEviDescripcionEvid(),
                MAX_DESCRIPCION,
                "descripción"
        );
        validarLongitud(
                dto.getEviUrlarchivoEvid(),
                MAX_URL,
                "URL archivo"
        );

        validarCoordenadas(dto);

        return dto;
    }

    private void validarBase(
            final EntyEvievimaevidenciaDto request
    ) throws EBusinessException {
        if (request == null) {
            throw new IllegalArgumentException(
                    "La solicitud de evidencia es obligatoria."
            );
        }

        validarTexto(
                request.getEviIdentifkeyTiev(),
                "El tipo de evidencia es obligatorio."
        );

        validarTexto(
                request.getEviUrlarchivoEvid(),
                "La URL del archivo es obligatoria."
        );

        String tipoKey = request.getEviIdentifkeyTiev()
                .trim()
                .toUpperCase();

        if (!tipoEvidenciaDataProviders.existsByTipoEvidenciaKey(tipoKey)) {
            throw new IllegalArgumentException(
                    "No existe el tipo de evidencia: " + tipoKey
            );
        }
    }

    private void validarCoordenadas(
            final EntyEvievimaevidenciaDto dto
    ) {
        if (dto.getEviLatitudEvid() != null
                && (dto.getEviLatitudEvid().compareTo(LATITUD_MIN) < 0
                || dto.getEviLatitudEvid().compareTo(LATITUD_MAX) > 0)) {
            throw new IllegalArgumentException(
                    "La latitud debe estar entre -90 y 90."
            );
        }

        if (dto.getEviLongitudEvid() != null
                && (dto.getEviLongitudEvid().compareTo(LONGITUD_MIN) < 0
                || dto.getEviLongitudEvid().compareTo(LONGITUD_MAX) > 0)) {
            throw new IllegalArgumentException(
                    "La longitud debe estar entre -180 y 180."
            );
        }
    }

    private String generarEvidenciaKey()
            throws EBusinessException {
        long total = 0L;

        EntyEvievimaevidenciaResponse response =
                dataProviders.getAll(1, 1, "TEXT", "");

        if (response.getRspPagination() != null) {
            total = response.getRspPagination().getTotalResults();
        }

        long consecutivo = total + 1;
        String generated;

        do {
            generated = "EVID-" + String.format("%06d", consecutivo);
            consecutivo++;
        } while (dataProviders.existsByEvidenciaKey(generated));

        return generated;
    }

    private String generarReferenciaKey()
            throws EBusinessException {
        long total = 0L;

        EntyEvirefmdreferenciaResponse response =
                referenciaDataProviders.getAll(1, 1, "TEXT", "");

        if (response.getRspPagination() != null) {
            total = response.getRspPagination().getTotalResults();
        }

        long consecutivo = total + 1;
        String generated;

        do {
            generated = "REFE-" + String.format("%06d", consecutivo);
            consecutivo++;
        } while (referenciaDataProviders.existsByReferenciaKey(generated));

        return generated;
    }

    private EntyEvievimaevidenciaResponse buildSingleResponse(
            final EntyEvievimaevidenciaDto dto,
            final String message,
            final String parentKey
    ) {
        List<EntyEvievimaevidenciaDto> data =
                new ArrayList<>();

        if (dto != null) {
            data.add(dto);
        }

        return buildListResponse(data, message, parentKey);
    }

    private EntyEvievimaevidenciaResponse buildListResponse(
            final List<EntyEvievimaevidenciaDto> data,
            final String message,
            final String parentKey
    ) {
        List<EntyEvievimaevidenciaDto> safeData =
                data != null ? data : new ArrayList<>();

        EntyEvievimaevidenciaResponse response =
                new EntyEvievimaevidenciaResponse();

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