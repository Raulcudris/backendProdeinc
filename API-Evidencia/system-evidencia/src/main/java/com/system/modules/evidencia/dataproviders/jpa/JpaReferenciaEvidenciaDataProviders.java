package com.system.modules.evidencia.dataproviders.jpa;

import java.util.List;
import java.util.stream.Collectors;

import com.system.crosscutting.domain.constants.TipoRegistroEvidenciaConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyEvirefmdreferencia;
import com.system.crosscutting.persistence.repository.EntyEvirefmdreferenciaRepository;
import com.system.modules.evidencia.dataproviders.IjpaReferenciaEvidenciaDataProviders;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JpaReferenciaEvidenciaDataProviders
        extends JpaDataProviderSupport
        implements IjpaReferenciaEvidenciaDataProviders {

    private final EntyEvirefmdreferenciaRepository repository;

    @Override
    public EntyEvirefmdreferenciaResponse getAll() throws EBusinessException {
        try {
            List<EntyEvirefmdreferenciaDto> data = repository.findAll()
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            return buildResponse(data, null);
        } catch (Exception e) {
            throw buildException("No fue posible consultar las referencias de evidencia.", e);
        }
    }

    @Override
    public EntyEvirefmdreferenciaResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        try {
            int page = safeCurrentPage(currentPage);
            int size = safePageSize(pageSize);
            String parameterValue = safeParameter(parameter);
            String filterValue = safeFilter(filter);

            Page<EntyEvirefmdreferencia> result;

            if ("EVIDENCIA".equals(parameterValue)) {
                result = repository.findByEviIdentifkeyEvidContainingIgnoreCase(
                        filterValue,
                        PageRequest.of(page, size)
                );
            } else if ("REGISTRO".equals(parameterValue)) {
                result = repository.findByEviIdentifregistroRefeContainingIgnoreCase(
                        filterValue,
                        PageRequest.of(page, size)
                );
            } else if ("TIPO_REGISTRO".equals(parameterValue)) {
                result = repository.findByEviTiporegistroRefeContainingIgnoreCase(
                        TipoRegistroEvidenciaConstants.normalize(filterValue),
                        PageRequest.of(page, size)
                );
            } else if ("ESTADO".equals(parameterValue)) {
                result = repository.findByEviEstadoregRefeContainingIgnoreCase(
                        filterValue,
                        PageRequest.of(page, size)
                );
            } else {
                result = repository.findByEviIdentifkeyRefeContainingIgnoreCaseOrEviIdentifkeyEvidContainingIgnoreCaseOrEviIdentifregistroRefeContainingIgnoreCaseOrEviObservacionRefeContainingIgnoreCase(
                        filterValue,
                        filterValue,
                        filterValue,
                        filterValue,
                        PageRequest.of(page, size)
                );
            }

            List<EntyEvirefmdreferenciaDto> data = result.getContent()
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

            return buildResponse(data, buildPagination(currentPage, size, result));
        } catch (Exception e) {
            throw buildException("No fue posible consultar las referencias de evidencia paginadas.", e);
        }
    }

    @Override
    public EntyEvirefmdreferenciaDto get(final Integer id)
            throws EBusinessException {
        try {
            EntyEvirefmdreferencia entity = repository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("No se encontró la referencia de evidencia."));

            return toDto(entity);
        } catch (Exception e) {
            throw buildException("No fue posible consultar la referencia de evidencia.", e);
        }
    }

    @Override
    public EntyEvirefmdreferenciaDto save(final EntyEvirefmdreferenciaDto dto)
            throws EBusinessException {
        try {
            EntyEvirefmdreferencia entity = toEntity(dto);
            EntyEvirefmdreferencia saved = repository.save(entity);

            return toDto(saved);
        } catch (Exception e) {
            throw buildException("No fue posible guardar la referencia de evidencia.", e);
        }
    }

    @Override
    public List<EntyEvirefmdreferenciaDto> save(
            final List<EntyEvirefmdreferenciaDto> dto
    ) throws EBusinessException {
        try {
            List<EntyEvirefmdreferencia> entities = dto.stream()
                    .map(this::toEntity)
                    .collect(Collectors.toList());

            return repository.saveAll(entities)
                    .stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw buildException("No fue posible guardar la lista de referencias de evidencia.", e);
        }
    }

    @Override
    public EntyEvirefmdreferenciaDto update(
            final Integer id,
            final EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException {
        try {
            EntyEvirefmdreferencia current = repository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("No se encontró la referencia de evidencia."));

            Integer primaryKey = current.getEviPrimarykeyRefe();

            BeanUtils.copyProperties(dto, current);
            current.setEviPrimarykeyRefe(primaryKey);

            EntyEvirefmdreferencia saved = repository.save(current);

            return toDto(saved);
        } catch (Exception e) {
            throw buildException("No fue posible actualizar la referencia de evidencia.", e);
        }
    }

    @Override
    public void delete(final Integer id) throws EBusinessException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw buildException("No fue posible eliminar la referencia de evidencia.", e);
        }
    }

    @Override
    public EntyEvirefmdreferenciaDto findByKey(final String referenciaKey)
            throws EBusinessException {
        try {
            return repository.findByEviIdentifkeyRefe(referenciaKey)
                    .map(this::toDto)
                    .orElse(null);
        } catch (Exception e) {
            throw buildException("No fue posible consultar la referencia por key.", e);
        }
    }

    @Override
    public EntyEvirefmdreferenciaResponse findByEvidencia(
            final String evidenciaKey
    ) throws EBusinessException {
        try {
            List<EntyEvirefmdreferenciaDto> data =
                    repository.findByEviIdentifkeyEvid(evidenciaKey)
                            .stream()
                            .map(this::toDto)
                            .collect(Collectors.toList());

            return buildResponse(data, null);
        } catch (Exception e) {
            throw buildException("No fue posible consultar referencias por evidencia.", e);
        }
    }

    @Override
    public EntyEvirefmdreferenciaResponse findByRegistro(
            final String registroKey
    ) throws EBusinessException {
        try {
            List<EntyEvirefmdreferenciaDto> data =
                    repository.findByEviIdentifregistroRefe(registroKey)
                            .stream()
                            .map(this::toDto)
                            .collect(Collectors.toList());

            return buildResponse(data, null);
        } catch (Exception e) {
            throw buildException("No fue posible consultar referencias por registro.", e);
        }
    }

    @Override
    public EntyEvirefmdreferenciaResponse findByTipoRegistro(
            final String tipoRegistro
    ) throws EBusinessException {
        try {
            String tipoNormalizado =
                    TipoRegistroEvidenciaConstants.normalize(tipoRegistro);

            List<EntyEvirefmdreferenciaDto> data =
                    repository.findByEviTiporegistroRefe(tipoNormalizado)
                            .stream()
                            .map(this::toDto)
                            .collect(Collectors.toList());

            return buildResponse(data, null);
        } catch (Exception e) {
            throw buildException("No fue posible consultar referencias por tipo de registro.", e);
        }
    }

    @Override
    public EntyEvirefmdreferenciaResponse findByTipoRegistroAndRegistro(
            final String tipoRegistro,
            final String registroKey
    ) throws EBusinessException {
        try {
            String tipoNormalizado =
                    TipoRegistroEvidenciaConstants.normalize(tipoRegistro);

            List<EntyEvirefmdreferenciaDto> data =
                    repository.findByEviTiporegistroRefeAndEviIdentifregistroRefe(
                                    tipoNormalizado,
                                    registroKey
                            )
                            .stream()
                            .map(this::toDto)
                            .collect(Collectors.toList());

            return buildResponse(data, null);
        } catch (Exception e) {
            throw buildException("No fue posible consultar referencias por tipo de registro y registro.", e);
        }
    }

    @Override
    public EntyEvirefmdreferenciaResponse findByEstado(final String estado)
            throws EBusinessException {
        try {
            List<EntyEvirefmdreferenciaDto> data =
                    repository.findByEviEstadoregRefe(estado)
                            .stream()
                            .map(this::toDto)
                            .collect(Collectors.toList());

            return buildResponse(data, null);
        } catch (Exception e) {
            throw buildException("No fue posible consultar referencias por estado.", e);
        }
    }

    @Override
    public EntyEvirefmdreferenciaDto changestatus(
            final Integer id,
            final String estado
    ) throws EBusinessException {
        try {
            EntyEvirefmdreferencia entity = repository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("No se encontró la referencia de evidencia."));

            entity.setEviEstadoregRefe(estado);

            return toDto(repository.save(entity));
        } catch (Exception e) {
            throw buildException("No fue posible cambiar el estado de la referencia de evidencia.", e);
        }
    }

    private EntyEvirefmdreferenciaDto toDto(
            final EntyEvirefmdreferencia entity
    ) {
        EntyEvirefmdreferenciaDto dto = new EntyEvirefmdreferenciaDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private EntyEvirefmdreferencia toEntity(
            final EntyEvirefmdreferenciaDto dto
    ) {
        EntyEvirefmdreferencia entity = new EntyEvirefmdreferencia();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    private EntyEvirefmdreferenciaResponse buildResponse(
            final List<EntyEvirefmdreferenciaDto> data,
            final com.system.crosscutting.domain.model.PaginationResponse pagination
    ) {
        EntyEvirefmdreferenciaResponse response =
                new EntyEvirefmdreferenciaResponse();

        response.setRspMessage("Consulta ejecutada correctamente.");
        response.setRspValue("OK");
        response.setRspParentKey("");
        response.setRspAppKey("MSVC-EVIDENCIA");
        response.setRspPagination(pagination);
        response.setRspData(data);

        return response;
    }
}