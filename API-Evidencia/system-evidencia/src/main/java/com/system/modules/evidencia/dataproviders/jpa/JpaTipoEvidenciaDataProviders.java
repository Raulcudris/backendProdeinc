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

import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyEvitipmatipoevidencia;
import com.system.crosscutting.persistence.repository.EntyEvitipmatipoevidenciaRepository;
import com.system.modules.evidencia.dataproviders.IjpaTipoEvidenciaDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaTipoEvidenciaDataProviders extends JpaDataProviderSupport
        implements IjpaTipoEvidenciaDataProviders {

    private final EntyEvitipmatipoevidenciaRepository repository;

    @Override
    public EntyEvitipmatipoevidenciaResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyEvitipmatipoevidenciaResponse getAll(
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
            Page<EntyEvitipmatipoevidencia> page;

            switch (safeParameter(parameter)) {
                case "ID":
                    page = repository.searchByPrimaryKey(parseInteger(search), pageable);
                    break;
                case "KEY":
                    page = repository.searchByIdentifKey(search, pageable);
                    break;
                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;
                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyEvitipmatipoevidenciaDto> data = page.getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyEvitipmatipoevidenciaDto.class))
                    .collect(Collectors.toList());

            EntyEvitipmatipoevidenciaResponse response = new EntyEvitipmatipoevidenciaResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-evidencias");
            response.setRspData(data);
            response.setRspPagination(buildPagination(pageNumber + 1, size, page));

            return response;

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando tipos de evidencia", e);
        }
    }

    @Override
    public EntyEvitipmatipoevidenciaDto get(Integer id) throws EBusinessException {
        try {
            EntyEvitipmatipoevidencia entity = repository.findById(id).orElse(null);

            return entity == null
                    ? new EntyEvitipmatipoevidenciaDto()
                    : toDto(entity, EntyEvitipmatipoevidenciaDto.class);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando tipo de evidencia", e);
        }
    }

    @Override
    public EntyEvitipmatipoevidenciaDto save(
            EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException {
        try {
            dto.setEviPrimarykeyTiev(null);

            if (dto.getEviTiporegistTiev() == null || dto.getEviTiporegistTiev().isBlank()) {
                dto.setEviTiporegistTiev("1");
            }

            if (dto.getEviEstadoregTiev() == null || dto.getEviEstadoregTiev().isBlank()) {
                dto.setEviEstadoregTiev("1");
            }

            EntyEvitipmatipoevidencia entity = toEntity(
                    dto,
                    EntyEvitipmatipoevidencia.class
            );

            return toDto(
                    repository.save(entity),
                    EntyEvitipmatipoevidenciaDto.class
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando tipo de evidencia", e);
        }
    }

    @Override
    public List<EntyEvitipmatipoevidenciaDto> save(
            List<EntyEvitipmatipoevidenciaDto> dtos
    ) throws EBusinessException {
        List<EntyEvitipmatipoevidenciaDto> result = new ArrayList<>();

        for (EntyEvitipmatipoevidenciaDto dto : dtos) {
            result.add(save(dto));
        }

        return result;
    }

    @Override
    public EntyEvitipmatipoevidenciaDto update(
            Integer id,
            EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException {
        try {
            EntyEvitipmatipoevidencia old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyEvitipmatipoevidenciaDto();
            }

            dto.setEviPrimarykeyTiev(id);
            BeanUtils.copyProperties(dto, old);

            return toDto(
                    repository.save(old),
                    EntyEvitipmatipoevidenciaDto.class
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando tipo de evidencia", e);
        }
    }

    @Override
    public void delete(Integer id) throws EBusinessException {
        try {
            repository.deleteById(id);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando tipo de evidencia", e);
        }
    }

    @Override
    public List<EntyEvitipmatipoevidenciaDto> findByEstado(
            String estado
    ) throws EBusinessException {
        try {
            return repository.searchByStatus(estado, Pageable.unpaged())
                    .getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyEvitipmatipoevidenciaDto.class))
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando tipos de evidencia por estado", e);
        }
    }
}