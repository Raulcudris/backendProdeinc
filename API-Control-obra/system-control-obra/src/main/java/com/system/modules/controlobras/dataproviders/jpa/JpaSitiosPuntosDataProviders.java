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

import com.system.crosscutting.domain.model.EntyOrsordmdsitiospuntosDto;
import com.system.crosscutting.domain.model.EntyOrsordmdsitiospuntosResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsordmdsitiospuntos;
import com.system.crosscutting.persistence.repository.EntyOrsordmdsitiospuntosRepository;
import com.system.modules.controlobras.dataproviders.IjpaSitiosPuntosDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaSitiosPuntosDataProviders extends JpaDataProviderSupport
        implements IjpaSitiosPuntosDataProviders {

    private final EntyOrsordmdsitiospuntosRepository repository;

    @Override
    public EntyOrsordmdsitiospuntosResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsordmdsitiospuntosResponse getAll(
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
            Page<EntyOrsordmdsitiospuntos> page;

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
                case "NOMBRE":
                    page = repository.searchByNombreSitio(search, pageable);
                    break;
                case "PROVINCIA":
                    page = repository.searchByProvincia(search, pageable);
                    break;
                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;
                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyOrsordmdsitiospuntosDto> data = page.getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsordmdsitiospuntosDto.class))
                    .collect(Collectors.toList());

            EntyOrsordmdsitiospuntosResponse response = new EntyOrsordmdsitiospuntosResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-control-obras");
            response.setRspData(data);
            response.setRspPagination(buildPagination(pageNumber + 1, size, page));

            return response;
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando sitios/puntos", e);
        }
    }

    @Override
    public EntyOrsordmdsitiospuntosDto get(Integer id) throws EBusinessException {
        try {
            EntyOrsordmdsitiospuntos entity = repository.findById(id).orElse(null);
            return entity == null
                    ? new EntyOrsordmdsitiospuntosDto()
                    : toDto(entity, EntyOrsordmdsitiospuntosDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando sitio/punto", e);
        }
    }

    @Override
    public EntyOrsordmdsitiospuntosDto save(
            EntyOrsordmdsitiospuntosDto dto
    ) throws EBusinessException {
        try {
            dto.setOrsPrimarykeyPunt(null);

            if (dto.getOrsTiporegistPunt() == null || dto.getOrsTiporegistPunt().isBlank()) {
                dto.setOrsTiporegistPunt("1");
            }

            if (dto.getOrsEstadoregPunt() == null || dto.getOrsEstadoregPunt().isBlank()) {
                dto.setOrsEstadoregPunt("1");
            }

            EntyOrsordmdsitiospuntos entity = toEntity(dto, EntyOrsordmdsitiospuntos.class);
            return toDto(repository.save(entity), EntyOrsordmdsitiospuntosDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando sitio/punto", e);
        }
    }

    @Override
    public List<EntyOrsordmdsitiospuntosDto> save(
            List<EntyOrsordmdsitiospuntosDto> dtos
    ) throws EBusinessException {
        List<EntyOrsordmdsitiospuntosDto> result = new ArrayList<>();
        for (EntyOrsordmdsitiospuntosDto dto : dtos) {
            result.add(save(dto));
        }
        return result;
    }

    @Override
    public EntyOrsordmdsitiospuntosDto update(
            Integer id,
            EntyOrsordmdsitiospuntosDto dto
    ) throws EBusinessException {
        try {
            EntyOrsordmdsitiospuntos old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyOrsordmdsitiospuntosDto();
            }

            dto.setOrsPrimarykeyPunt(id);
            BeanUtils.copyProperties(dto, old);

            return toDto(repository.save(old), EntyOrsordmdsitiospuntosDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando sitio/punto", e);
        }
    }

    @Override
    public void delete(Integer id) throws EBusinessException {
        try {
            repository.deleteById(id);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando sitio/punto", e);
        }
    }

    @Override
    public List<EntyOrsordmdsitiospuntosDto> findByOrden(
            String ordenKey
    ) throws EBusinessException {
        try {
            return repository.findByOrsIdentifkeyOrde(ordenKey)
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsordmdsitiospuntosDto.class))
                    .collect(Collectors.toList());
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando sitios/puntos por orden", e);
        }
    }
}