package com.system.modules.evidencia.dataproviders.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyEvirefmdreferencia;
import com.system.crosscutting.persistence.repository.EntyEvirefmdreferenciaRepository;
import com.system.modules.evidencia.dataproviders.IjpaReferenciaEvidenciaDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaReferenciaEvidenciaDataProviders extends JpaDataProviderSupport
        implements IjpaReferenciaEvidenciaDataProviders {

    private final EntyEvirefmdreferenciaRepository repository;

    @Override
    public EntyEvirefmdreferenciaResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyEvirefmdreferenciaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        try {
            int pageNumber = safeCurrentPage(currentPage);
            int size = safePageSize(pageSize);
            String search = safeFilter(filter);

            Pageable pageable = PageRequest.of(pageNumber, size);
            Page<EntyEvirefmdreferencia> page;

            switch (safeParameter(parameter)) {
                case "ID":
                    page = repository.searchByPrimaryKey(parseInteger(search), pageable);
                    break;
                case "KEY":
                    page = repository.searchByIdentifKey(search, pageable);
                    break;
                case "EVIDENCIA":
                    page = repository.searchByEvidencia(search, pageable);
                    break;
                case "REGISTRO":
                    page = repository.searchByRegistro(search, pageable);
                    break;
                case "TIPO_REGISTRO":
                    page = repository.searchByTipoRegistro(search, pageable);
                    break;
                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;
                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyEvirefmdreferenciaDto> data = page.getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyEvirefmdreferenciaDto.class))
                    .collect(Collectors.toList());

            EntyEvirefmdreferenciaResponse response = new EntyEvirefmdreferenciaResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-evidencias");
            response.setRspData(data);
            response.setRspPagination(buildPagination(pageNumber + 1, size, page));

            return response;

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando referencias de evidencia", e);
        }
    }

    @Override
    public EntyEvirefmdreferenciaDto get(Integer id) throws EBusinessException {
        try {
            EntyEvirefmdreferencia entity = repository.findById(id).orElse(null);

            return entity == null
                    ? new EntyEvirefmdreferenciaDto()
                    : toDto(entity, EntyEvirefmdreferenciaDto.class);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando referencia de evidencia", e);
        }
    }

    @Override
    public EntyEvirefmdreferenciaDto save(
            EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException {
        try {
            dto.setEviPrimarykeyRefe(null);

            if (dto.getEviTiporegistRefe() == null || dto.getEviTiporegistRefe().isBlank()) {
                dto.setEviTiporegistRefe("1");
            }

            if (dto.getEviEstadoregRefe() == null || dto.getEviEstadoregRefe().isBlank()) {
                dto.setEviEstadoregRefe("1");
            }

            EntyEvirefmdreferencia entity = toEntity(
                    dto,
                    EntyEvirefmdreferencia.class
            );

            return toDto(
                    repository.save(entity),
                    EntyEvirefmdreferenciaDto.class
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando referencia de evidencia", e);
        }
    }

    @Override
    public List<EntyEvirefmdreferenciaDto> save(
            List<EntyEvirefmdreferenciaDto> dtos
    ) throws EBusinessException {
        List<EntyEvirefmdreferenciaDto> result = new ArrayList<>();

        for (EntyEvirefmdreferenciaDto dto : dtos) {
            result.add(save(dto));
        }

        return result;
    }

    @Override
    public EntyEvirefmdreferenciaDto update(
            Integer id,
            EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException {
        try {
            EntyEvirefmdreferencia old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyEvirefmdreferenciaDto();
            }

            dto.setEviPrimarykeyRefe(id);
            BeanUtils.copyProperties(dto, old);

            return toDto(
                    repository.save(old),
                    EntyEvirefmdreferenciaDto.class
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando referencia de evidencia", e);
        }
    }

    @Override
    public void delete(Integer id) throws EBusinessException {
        try {
            repository.deleteById(id);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando referencia de evidencia", e);
        }
    }

    @Override
    public List<EntyEvirefmdreferenciaDto> findByEvidencia(
            String evidenciaKey
    ) throws EBusinessException {
        try {
            return repository.findByEviIdentifkeyEvid(evidenciaKey)
                    .stream()
                    .map(entity -> toDto(entity, EntyEvirefmdreferenciaDto.class))
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando referencias por evidencia", e);
        }
    }

    @Override
    public List<EntyEvirefmdreferenciaDto> findByRegistro(
            String registroKey
    ) throws EBusinessException {
        try {
            return repository.findByEviIdentifregistroRefe(registroKey)
                    .stream()
                    .map(entity -> toDto(entity, EntyEvirefmdreferenciaDto.class))
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando referencias por registro", e);
        }
    }

    @Override
    public List<EntyEvirefmdreferenciaDto> findByTipoRegistro(
            String tipoRegistro
    ) throws EBusinessException {
        try {
            return repository.findByEviTiporegistroRefe(tipoRegistro)
                    .stream()
                    .map(entity -> toDto(entity, EntyEvirefmdreferenciaDto.class))
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando referencias por tipo de registro", e);
        }
    }

    @Override
    public List<EntyEvirefmdreferenciaDto> findByTipoRegistroAndRegistro(
            String tipoRegistro,
            String registroKey
    ) throws EBusinessException {
        try {
            return repository.findByEviTiporegistroRefeAndEviIdentifregistroRefe(
                            tipoRegistro,
                            registroKey
                    )
                    .stream()
                    .map(entity -> toDto(entity, EntyEvirefmdreferenciaDto.class))
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando referencias por tipo y registro", e);
        }
    }

    @Override
    public List<EntyEvirefmdreferenciaDto> findByEstado(
            String estado
    ) throws EBusinessException {
        try {
            return repository.searchByStatus(estado, Pageable.unpaged())
                    .getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyEvirefmdreferenciaDto.class))
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando referencias por estado", e);
        }
    }
}