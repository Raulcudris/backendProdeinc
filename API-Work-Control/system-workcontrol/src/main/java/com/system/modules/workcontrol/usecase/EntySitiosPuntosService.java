package com.system.modules.workcontrol.usecase;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.system.crosscutting.domain.model.EntyOrsordmdsitiospuntosDto;
import com.system.crosscutting.domain.model.EntyOrsordmdsitiospuntosResponse;
import com.system.crosscutting.domain.model.PaginationResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyOrsordmaordenservicioRepository;
import com.system.modules.workcontrol.dataproviders.IjpaSitiosPuntosDataProviders;

@Service
public class EntySitiosPuntosService {

    private static final String ESTADO_ABIERTO = "1";

    private static final String ESTADO_CERRADO = "2";

    private static final String ESTADO_CANCELADO = "3";

    private static final String TIPO_REGISTRO_ORIGINAL = "1";

    private static final int MAX_KEY = 30;

    private static final int MAX_NOMBRE = 150;

    private static final int MAX_CODIGO_DANE = 14;

    private final IjpaSitiosPuntosDataProviders dataProviders;

    private final EntyOrsordmaordenservicioRepository ordenRepository;

    public EntySitiosPuntosService(
            final IjpaSitiosPuntosDataProviders dataProviders,
            final EntyOrsordmaordenservicioRepository ordenRepository
    ) {
        this.dataProviders = dataProviders;
        this.ordenRepository = ordenRepository;
    }

    @Transactional
    public EntyOrsordmdsitiospuntosResponse createResponse(
            final EntyOrsordmdsitiospuntosDto request
    ) throws EBusinessException {
        EntyOrsordmdsitiospuntosDto normalized =
                normalizeForCreate(request);

        EntyOrsordmdsitiospuntosDto saved =
                dataProviders.save(normalized);

        return buildSingleResponse(
                saved,
                "Sitio o punto creado correctamente.",
                saved.getOrsIdentifkeyOrde()
        );
    }

    public EntyOrsordmdsitiospuntosResponse getByOrdenResponse(
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

    public EntyOrsordmdsitiospuntosResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyOrsordmdsitiospuntosResponse getAll(
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

    public EntyOrsordmdsitiospuntosResponse getResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsordmdsitiospuntosDto dto =
                dataProviders.get(id);

        if (dto.getOrsPrimarykeyPunt() == null) {
            return buildListResponse(
                    new ArrayList<>(),
                    "No se encontró el sitio o punto.",
                    "NA"
            );
        }

        return buildSingleResponse(
                dto,
                "Sitio o punto consultado correctamente.",
                dto.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsordmdsitiospuntosResponse updateResponse(
            final Integer id,
            final EntyOrsordmdsitiospuntosDto request
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID del sitio o punto es obligatorio."
            );
        }

        EntyOrsordmdsitiospuntosDto current =
                dataProviders.get(id);

        if (current.getOrsPrimarykeyPunt() == null) {
            throw new IllegalArgumentException(
                    "No existe el sitio o punto con ID: " + id
            );
        }

        EntyOrsordmdsitiospuntosDto normalized =
                normalizeForUpdate(request, current);

        EntyOrsordmdsitiospuntosDto updated =
                dataProviders.update(id, normalized);

        return buildSingleResponse(
                updated,
                "Sitio o punto actualizado correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsordmdsitiospuntosResponse cerrarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsordmdsitiospuntosDto current =
                getCurrentOrFail(id);

        current.setOrsEstadoregPunt(ESTADO_CERRADO);

        EntyOrsordmdsitiospuntosDto updated =
                dataProviders.update(id, current);

        return buildSingleResponse(
                updated,
                "Sitio o punto cerrado correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsordmdsitiospuntosResponse cancelarResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsordmdsitiospuntosDto current =
                getCurrentOrFail(id);

        current.setOrsEstadoregPunt(ESTADO_CANCELADO);

        EntyOrsordmdsitiospuntosDto updated =
                dataProviders.update(id, current);

        return buildSingleResponse(
                updated,
                "Sitio o punto cancelado correctamente.",
                updated.getOrsIdentifkeyOrde()
        );
    }

    @Transactional
    public EntyOrsordmdsitiospuntosResponse deleteResponse(
            final Integer id
    ) throws EBusinessException {
        EntyOrsordmdsitiospuntosDto current =
                getCurrentOrFail(id);

        dataProviders.delete(id);

        return buildSingleResponse(
                current,
                "Sitio o punto eliminado correctamente.",
                current.getOrsIdentifkeyOrde()
        );
    }

    private EntyOrsordmdsitiospuntosDto getCurrentOrFail(
            final Integer id
    ) throws EBusinessException {
        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID del sitio o punto es obligatorio."
            );
        }

        EntyOrsordmdsitiospuntosDto current =
                dataProviders.get(id);

        if (current.getOrsPrimarykeyPunt() == null) {
            throw new IllegalArgumentException(
                    "No existe el sitio o punto con ID: " + id
            );
        }

        return current;
    }

    private EntyOrsordmdsitiospuntosDto normalizeForCreate(
            final EntyOrsordmdsitiospuntosDto request
    ) throws EBusinessException {
        validarRequestBase(request);

        EntyOrsordmdsitiospuntosDto dto =
                normalizeCommon(request);

        dto.setOrsPrimarykeyPunt(null);

        if (dto.getOrsIdentifkeyPunt() == null
                || dto.getOrsIdentifkeyPunt().trim().isEmpty()) {
            dto.setOrsIdentifkeyPunt(
                    generarPuntoKey(dto.getOrsIdentifkeyOrde())
            );
        }

        dto.setOrsIdentifkeyPunt(
                dto.getOrsIdentifkeyPunt().trim().toUpperCase()
        );

        if (dataProviders.existsByPuntoKey(dto.getOrsIdentifkeyPunt())) {
            throw new IllegalArgumentException(
                    "Ya existe un sitio o punto con el código: "
                            + dto.getOrsIdentifkeyPunt()
            );
        }

        dto.setOrsTiporegistPunt(TIPO_REGISTRO_ORIGINAL);
        dto.setOrsEstadoregPunt(ESTADO_ABIERTO);

        return dto;
    }

    private EntyOrsordmdsitiospuntosDto normalizeForUpdate(
            final EntyOrsordmdsitiospuntosDto request,
            final EntyOrsordmdsitiospuntosDto current
    ) {
        validarRequestBase(request);

        EntyOrsordmdsitiospuntosDto dto =
                normalizeCommon(request);

        dto.setOrsPrimarykeyPunt(current.getOrsPrimarykeyPunt());
        dto.setOrsIdentifkeyPunt(current.getOrsIdentifkeyPunt());

        if (dto.getOrsTiporegistPunt() == null
                || dto.getOrsTiporegistPunt().trim().isEmpty()) {
            dto.setOrsTiporegistPunt(
                    current.getOrsTiporegistPunt() != null
                            ? current.getOrsTiporegistPunt()
                            : TIPO_REGISTRO_ORIGINAL
            );
        }

        if (dto.getOrsEstadoregPunt() == null
                || dto.getOrsEstadoregPunt().trim().isEmpty()) {
            dto.setOrsEstadoregPunt(
                    current.getOrsEstadoregPunt() != null
                            ? current.getOrsEstadoregPunt()
                            : ESTADO_ABIERTO
            );
        }

        return dto;
    }

    private EntyOrsordmdsitiospuntosDto normalizeCommon(
            final EntyOrsordmdsitiospuntosDto request
    ) {
        EntyOrsordmdsitiospuntosDto dto =
                new EntyOrsordmdsitiospuntosDto();

        dto.setOrsPrimarykeyPunt(request.getOrsPrimarykeyPunt());
        dto.setOrsIdentifkeyPunt(limpiarMayuscula(request.getOrsIdentifkeyPunt()));
        dto.setOrsIdentifkeyOrde(limpiarMayuscula(request.getOrsIdentifkeyOrde()));
        dto.setOrsNombresitioPunt(limpiarTexto(request.getOrsNombresitioPunt()));
        dto.setSisCodproSipr(limpiarTexto(request.getSisCodproSipr()));
        dto.setOrsGeolatitudePunt(request.getOrsGeolatitudePunt());
        dto.setOrsGeolongitudePunt(request.getOrsGeolongitudePunt());
        dto.setOrsPathimagenPunt(limpiarTexto(request.getOrsPathimagenPunt()));
        dto.setOrsTiporegistPunt(limpiarMayuscula(request.getOrsTiporegistPunt()));
        dto.setOrsEstadoregPunt(limpiarMayuscula(request.getOrsEstadoregPunt()));

        validarLongitud(dto.getOrsIdentifkeyPunt(), MAX_KEY, "código del punto");
        validarLongitud(dto.getOrsIdentifkeyOrde(), MAX_KEY, "código de la orden");
        validarLongitud(dto.getOrsNombresitioPunt(), MAX_NOMBRE, "nombre del sitio");
        validarLongitud(dto.getSisCodproSipr(), MAX_CODIGO_DANE, "código DANE");

        validarCoordenadas(dto);

        return dto;
    }

    private void validarRequestBase(
            final EntyOrsordmdsitiospuntosDto request
    ) {
        if (request == null) {
            throw new IllegalArgumentException(
                    "La solicitud del sitio o punto es obligatoria."
            );
        }

        validarTexto(
                request.getOrsIdentifkeyOrde(),
                "El código de la orden de servicio es obligatorio."
        );

        validarTexto(
                request.getOrsNombresitioPunt(),
                "El nombre del sitio o punto es obligatorio."
        );

        String ordenKey = request.getOrsIdentifkeyOrde()
                .trim()
                .toUpperCase();

        if (!ordenRepository.existsByOrsIdentifkeyOrde(ordenKey)) {
            throw new IllegalArgumentException(
                    "No existe la orden de servicio: " + ordenKey
            );
        }
    }

    private void validarCoordenadas(
            final EntyOrsordmdsitiospuntosDto dto
    ) {
        if (dto.getOrsGeolatitudePunt() != null
                && (dto.getOrsGeolatitudePunt() < -90
                || dto.getOrsGeolatitudePunt() > 90)) {
            throw new IllegalArgumentException(
                    "La latitud debe estar entre -90 y 90."
            );
        }

        if (dto.getOrsGeolongitudePunt() != null
                && (dto.getOrsGeolongitudePunt() < -180
                || dto.getOrsGeolongitudePunt() > 180)) {
            throw new IllegalArgumentException(
                    "La longitud debe estar entre -180 y 180."
            );
        }
    }

    private String generarPuntoKey(
            final String ordenKey
    ) throws EBusinessException {
        List<EntyOrsordmdsitiospuntosDto> existentes =
                dataProviders.findByOrden(ordenKey);

        String cleanOrder = ordenKey
                .toUpperCase()
                .replaceAll("[^A-Z0-9]", "");

        int maxOrderLength = 30 - "PUNT-".length() - "-000".length();

        if (cleanOrder.length() > maxOrderLength) {
            cleanOrder = cleanOrder.substring(0, maxOrderLength);
        }

        int consecutivo = existentes.size() + 1;

        String generated;

        do {
            generated = "PUNT-"
                    + cleanOrder
                    + "-"
                    + String.format("%03d", consecutivo);

            consecutivo++;
        } while (dataProviders.existsByPuntoKey(generated));

        return generated;
    }

    private EntyOrsordmdsitiospuntosResponse buildSingleResponse(
            final EntyOrsordmdsitiospuntosDto dto,
            final String message,
            final String parentKey
    ) {
        List<EntyOrsordmdsitiospuntosDto> data =
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

    private EntyOrsordmdsitiospuntosResponse buildListResponse(
            final List<EntyOrsordmdsitiospuntosDto> data,
            final String message,
            final String parentKey
    ) {
        List<EntyOrsordmdsitiospuntosDto> safeData =
                data != null ? data : new ArrayList<>();

        EntyOrsordmdsitiospuntosResponse response =
                new EntyOrsordmdsitiospuntosResponse();

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