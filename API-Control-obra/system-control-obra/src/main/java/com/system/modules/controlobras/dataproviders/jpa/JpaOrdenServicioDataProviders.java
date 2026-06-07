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

import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioDto;
import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsordmaordenservicio;
import com.system.crosscutting.persistence.repository.EntyOrsordmaordenservicioRepository;
import com.system.modules.controlobras.dataproviders.IjpaOrdenServicioDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaOrdenServicioDataProviders extends JpaDataProviderSupport
        implements IjpaOrdenServicioDataProviders {

    private final EntyOrsordmaordenservicioRepository repository;

    @Override
    public EntyOrsordmaordenservicioResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsordmaordenservicioResponse getAll(
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
            Page<EntyOrsordmaordenservicio> page;

            switch (safeParameter(parameter)) {
                case "ID":
                    page = repository.searchByPrimaryKey(parseInteger(search), pageable);
                    break;
                case "KEY":
                    page = repository.searchByIdentifKey(search, pageable);
                    break;
                case "PROVEEDOR":
                    page = repository.searchByProveedor(search, pageable);
                    break;
                case "CODIGO":
                    page = repository.searchByCodigoServicio(search, pageable);
                    break;
                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;
                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyOrsordmaordenservicioDto> data = page.getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsordmaordenservicioDto.class))
                    .collect(Collectors.toList());

            EntyOrsordmaordenservicioResponse response = new EntyOrsordmaordenservicioResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-control-obras");
            response.setRspData(data);
            response.setRspPagination(buildPagination(pageNumber + 1, size, page));

            return response;
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando órdenes de servicio", e);
        }
    }

    @Override
    public EntyOrsordmaordenservicioDto get(Integer id) throws EBusinessException {
        try {
            EntyOrsordmaordenservicio entity = repository.findById(id).orElse(null);
            return entity == null
                    ? new EntyOrsordmaordenservicioDto()
                    : toDto(entity, EntyOrsordmaordenservicioDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando orden de servicio", e);
        }
    }

    @Override
    public EntyOrsordmaordenservicioDto save(
            EntyOrsordmaordenservicioDto dto
    ) throws EBusinessException {
        try {
            dto.setOrsPrimarykeyOrde(null);

            if (dto.getOrsTiporegistOrde() == null || dto.getOrsTiporegistOrde().isBlank()) {
                dto.setOrsTiporegistOrde("1");
            }

            if (dto.getOrsEstadoregOrde() == null || dto.getOrsEstadoregOrde().isBlank()) {
                dto.setOrsEstadoregOrde("1");
            }

            EntyOrsordmaordenservicio entity = toEntity(dto, EntyOrsordmaordenservicio.class);
            return toDto(repository.save(entity), EntyOrsordmaordenservicioDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando orden de servicio", e);
        }
    }

    @Override
    public List<EntyOrsordmaordenservicioDto> save(
            List<EntyOrsordmaordenservicioDto> dtos
    ) throws EBusinessException {
        List<EntyOrsordmaordenservicioDto> result = new ArrayList<>();
        for (EntyOrsordmaordenservicioDto dto : dtos) {
            result.add(save(dto));
        }
        return result;
    }

    @Override
    public EntyOrsordmaordenservicioDto update(
            Integer id,
            EntyOrsordmaordenservicioDto dto
    ) throws EBusinessException {
        try {
            EntyOrsordmaordenservicio old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyOrsordmaordenservicioDto();
            }

            dto.setOrsPrimarykeyOrde(id);
            BeanUtils.copyProperties(dto, old);

            return toDto(repository.save(old), EntyOrsordmaordenservicioDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando orden de servicio", e);
        }
    }

    @Override
    public void delete(Integer id) throws EBusinessException {
        try {
            repository.deleteById(id);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando orden de servicio", e);
        }
    }
}