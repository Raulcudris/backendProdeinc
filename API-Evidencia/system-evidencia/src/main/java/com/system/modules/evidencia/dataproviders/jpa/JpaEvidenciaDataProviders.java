package com.system.modules.evidencia.dataproviders.jpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyEvievimaevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvievimaevidenciaResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyEvievimaevidencia;
import com.system.crosscutting.persistence.repository.EntyEvievimaevidenciaRepository;
import com.system.modules.evidencia.dataproviders.IjpaEvidenciaDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaEvidenciaDataProviders extends JpaDataProviderSupport
        implements IjpaEvidenciaDataProviders {

    private final EntyEvievimaevidenciaRepository repository;

    @Override
    public EntyEvievimaevidenciaResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyEvievimaevidenciaResponse getAll(
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
            Page<EntyEvievimaevidencia> page;

            switch (safeParameter(parameter)) {
                case "ID":
                    page = repository.searchByPrimaryKey(parseInteger(search), pageable);
                    break;
                case "KEY":
                    page = repository.searchByIdentifKey(search, pageable);
                    break;
                case "TIPO":
                    page = repository.searchByTipo(search, pageable);
                    break;
                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;
                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyEvievimaevidenciaDto> data = page.getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyEvievimaevidenciaDto.class))
                    .collect(Collectors.toList());

            EntyEvievimaevidenciaResponse response = new EntyEvievimaevidenciaResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-evidencias");
            response.setRspData(data);
            response.setRspPagination(buildPagination(pageNumber + 1, size, page));

            return response;

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando evidencias", e);
        }
    }

    @Override
    public EntyEvievimaevidenciaDto get(Integer id) throws EBusinessException {
        try {
            EntyEvievimaevidencia entity = repository.findById(id).orElse(null);

            return entity == null
                    ? new EntyEvievimaevidenciaDto()
                    : toDto(entity, EntyEvievimaevidenciaDto.class);

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando evidencia", e);
        }
    }

    @Override
    public EntyEvievimaevidenciaDto save(
            EntyEvievimaevidenciaDto dto
    ) throws EBusinessException {
        try {
            dto.setEviPrimarykeyEvid(null);

            if (dto.getEviFechacapturaEvid() == null) {
                dto.setEviFechacapturaEvid(LocalDate.now());
            }

            if (dto.getEviTiporegistEvid() == null || dto.getEviTiporegistEvid().isBlank()) {
                dto.setEviTiporegistEvid("1");
            }

            if (dto.getEviEstadoregEvid() == null || dto.getEviEstadoregEvid().isBlank()) {
                dto.setEviEstadoregEvid("1");
            }

            EntyEvievimaevidencia entity = toEntity(
                    dto,
                    EntyEvievimaevidencia.class
            );

            return toDto(
                    repository.save(entity),
                    EntyEvievimaevidenciaDto.class
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando evidencia", e);
        }
    }

    @Override
    public List<EntyEvievimaevidenciaDto> save(
            List<EntyEvievimaevidenciaDto> dtos
    ) throws EBusinessException {
        List<EntyEvievimaevidenciaDto> result = new ArrayList<>();

        for (EntyEvievimaevidenciaDto dto : dtos) {
            result.add(save(dto));
        }

        return result;
    }

    @Override
    public EntyEvievimaevidenciaDto update(
            Integer id,
            EntyEvievimaevidenciaDto dto
    ) throws EBusinessException {
        try {
            EntyEvievimaevidencia old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyEvievimaevidenciaDto();
            }

            dto.setEviPrimarykeyEvid(id);
            BeanUtils.copyProperties(dto, old);

            return toDto(
                    repository.save(old),
                    EntyEvievimaevidenciaDto.class
            );

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando evidencia", e);
        }
    }

    @Override
    public void delete(Integer id) throws EBusinessException {
        try {
            repository.deleteById(id);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando evidencia", e);
        }
    }

    @Override
    public List<EntyEvievimaevidenciaDto> findByTipo(
            String tipoKey
    ) throws EBusinessException {
        try {
            return repository.findByEviIdentifkeyTiev(tipoKey)
                    .stream()
                    .map(entity -> toDto(entity, EntyEvievimaevidenciaDto.class))
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando evidencias por tipo", e);
        }
    }

    @Override
    public List<EntyEvievimaevidenciaDto> findByEstado(
            String estado
    ) throws EBusinessException {
        try {
            return repository.searchByStatus(estado, Pageable.unpaged())
                    .getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyEvievimaevidenciaDto.class))
                    .collect(Collectors.toList());

        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando evidencias por estado", e);
        }
    }
}