package com.system.modules.controlobras.dataproviders.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyOrsordmdproyecsemanaDto;
import com.system.crosscutting.domain.model.EntyOrsordmdproyecsemanaResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsordmdproyecsemana;
import com.system.crosscutting.persistence.repository.EntyOrsordmdproyecsemanaRepository;
import com.system.modules.controlobras.dataproviders.IjpaProyeccionSemanaDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaProyeccionSemanaDataProviders extends JpaDataProviderSupport
        implements IjpaProyeccionSemanaDataProviders {

    private final EntyOrsordmdproyecsemanaRepository repository;

    @Override
    public EntyOrsordmdproyecsemanaResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsordmdproyecsemanaResponse getAll(
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
            Page<EntyOrsordmdproyecsemana> page;

            switch (safeParameter(parameter)) {
                case "ID":
                    page = repository.searchByPrimaryKey(parseInteger(search), pageable);
                    break;
                case "KEY":
                    page = repository.searchByIdentifKey(search, pageable);
                    break;
                case "ORDEN":
                    page = repository.searchByOrden(search, pageable);
                    break;
                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;
                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyOrsordmdproyecsemanaDto> data = page.getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsordmdproyecsemanaDto.class))
                    .collect(Collectors.toList());

            EntyOrsordmdproyecsemanaResponse response = new EntyOrsordmdproyecsemanaResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-control-obras");
            response.setRspData(data);
            response.setRspPagination(buildPagination(pageNumber + 1, size, page));

            return response;
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando proyección semanal", e);
        }
    }

    @Override
    public EntyOrsordmdproyecsemanaDto get(Integer id) throws EBusinessException {
        try {
            EntyOrsordmdproyecsemana entity = repository.findById(id).orElse(null);
            return entity == null
                    ? new EntyOrsordmdproyecsemanaDto()
                    : toDto(entity, EntyOrsordmdproyecsemanaDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando proyección semanal", e);
        }
    }

    @Override
    public EntyOrsordmdproyecsemanaDto save(
            EntyOrsordmdproyecsemanaDto dto
    ) throws EBusinessException {
        try {
            dto.setOrsPrimarykeyPsem(null);

            if (dto.getOrsTiporegistPsem() == null || dto.getOrsTiporegistPsem().isBlank()) {
                dto.setOrsTiporegistPsem("1");
            }

            if (dto.getOrsEstadoregPsem() == null || dto.getOrsEstadoregPsem().isBlank()) {
                dto.setOrsEstadoregPsem("1");
            }

            EntyOrsordmdproyecsemana entity = toEntity(dto, EntyOrsordmdproyecsemana.class);
            return toDto(repository.save(entity), EntyOrsordmdproyecsemanaDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando proyección semanal", e);
        }
    }

    @Override
    public List<EntyOrsordmdproyecsemanaDto> save(
            List<EntyOrsordmdproyecsemanaDto> dtos
    ) throws EBusinessException {
        List<EntyOrsordmdproyecsemanaDto> result = new ArrayList<>();
        for (EntyOrsordmdproyecsemanaDto dto : dtos) {
            result.add(save(dto));
        }
        return result;
    }

    @Override
    public EntyOrsordmdproyecsemanaDto update(
            Integer id,
            EntyOrsordmdproyecsemanaDto dto
    ) throws EBusinessException {
        try {
            EntyOrsordmdproyecsemana old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyOrsordmdproyecsemanaDto();
            }

            dto.setOrsPrimarykeyPsem(id);
            BeanUtils.copyProperties(dto, old);

            return toDto(repository.save(old), EntyOrsordmdproyecsemanaDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando proyección semanal", e);
        }
    }

    @Override
    public void delete(Integer id) throws EBusinessException {
        try {
            repository.deleteById(id);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando proyección semanal", e);
        }
    }

    @Override
    public List<EntyOrsordmdproyecsemanaDto> findByOrden(
            String ordenKey
    ) throws EBusinessException {
        try {
            return repository.findByOrsIdentifkeyOrde(ordenKey)
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsordmdproyecsemanaDto.class))
                    .collect(Collectors.toList());
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando proyección semanal por orden", e);
        }
    }
}