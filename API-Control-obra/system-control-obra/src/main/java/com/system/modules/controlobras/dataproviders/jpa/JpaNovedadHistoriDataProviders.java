package com.system.modules.controlobras.dataproviders.jpa;

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

import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriDto;
import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsconfnovedadhistori;
import com.system.crosscutting.persistence.repository.EntyOrsconfnovedadhistoriRepository;
import com.system.modules.controlobras.dataproviders.IjpaNovedadHistoriDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaNovedadHistoriDataProviders extends JpaDataProviderSupport
        implements IjpaNovedadHistoriDataProviders {

    private final EntyOrsconfnovedadhistoriRepository repository;

    @Override
    public EntyOrsconfnovedadhistoriResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsconfnovedadhistoriResponse getAll(
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
            Page<EntyOrsconfnovedadhistori> page;

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
                case "TIPO":
                    page = repository.searchByTipoNovedad(search, pageable);
                    break;
                case "REGISTRO_BASE":
                    page = repository.searchByRegistroBase(search, pageable);
                    break;
                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;
                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyOrsconfnovedadhistoriDto> data = page.getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsconfnovedadhistoriDto.class))
                    .collect(Collectors.toList());

            EntyOrsconfnovedadhistoriResponse response = new EntyOrsconfnovedadhistoriResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-control-obras");
            response.setRspData(data);
            response.setRspPagination(buildPagination(pageNumber + 1, size, page));

            return response;
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando novedades", e);
        }
    }

    @Override
    public EntyOrsconfnovedadhistoriDto get(Integer id) throws EBusinessException {
        try {
            EntyOrsconfnovedadhistori entity = repository.findById(id).orElse(null);
            return entity == null
                    ? new EntyOrsconfnovedadhistoriDto()
                    : toDto(entity, EntyOrsconfnovedadhistoriDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando novedad", e);
        }
    }

    @Override
    public EntyOrsconfnovedadhistoriDto save(
            EntyOrsconfnovedadhistoriDto dto
    ) throws EBusinessException {
        try {
            dto.setOrsPrimarykeyNove(null);

            if (dto.getOrsFechreportNove() == null) {
                dto.setOrsFechreportNove(LocalDate.now());
            }

            if (dto.getOrsEstadoregNove() == null || dto.getOrsEstadoregNove().isBlank()) {
                dto.setOrsEstadoregNove("1");
            }

            EntyOrsconfnovedadhistori entity = toEntity(dto, EntyOrsconfnovedadhistori.class);
            return toDto(repository.save(entity), EntyOrsconfnovedadhistoriDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando novedad", e);
        }
    }

    @Override
    public List<EntyOrsconfnovedadhistoriDto> save(
            List<EntyOrsconfnovedadhistoriDto> dtos
    ) throws EBusinessException {
        List<EntyOrsconfnovedadhistoriDto> result = new ArrayList<>();
        for (EntyOrsconfnovedadhistoriDto dto : dtos) {
            result.add(save(dto));
        }
        return result;
    }

    @Override
    public EntyOrsconfnovedadhistoriDto update(
            Integer id,
            EntyOrsconfnovedadhistoriDto dto
    ) throws EBusinessException {
        try {
            EntyOrsconfnovedadhistori old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyOrsconfnovedadhistoriDto();
            }

            dto.setOrsPrimarykeyNove(id);
            BeanUtils.copyProperties(dto, old);

            return toDto(repository.save(old), EntyOrsconfnovedadhistoriDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando novedad", e);
        }
    }

    @Override
    public void delete(Integer id) throws EBusinessException {
        try {
            repository.deleteById(id);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando novedad", e);
        }
    }

    @Override
    public List<EntyOrsconfnovedadhistoriDto> findByOrden(
            String ordenKey
    ) throws EBusinessException {
        try {
            return repository.findByOrsIdentifkeyOrde(ordenKey)
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsconfnovedadhistoriDto.class))
                    .collect(Collectors.toList());
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando novedades por orden", e);
        }
    }

    @Override
    public List<EntyOrsconfnovedadhistoriDto> findByRegistroBase(
            String registroBase
    ) throws EBusinessException {
        try {
            return repository.findByOrsRegistrbaseNove(registroBase)
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsconfnovedadhistoriDto.class))
                    .collect(Collectors.toList());
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando novedades por registro base", e);
        }
    }
}