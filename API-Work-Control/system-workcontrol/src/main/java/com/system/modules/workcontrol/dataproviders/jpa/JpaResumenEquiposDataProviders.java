package com.system.modules.workcontrol.dataproviders.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.system.crosscutting.domain.model.EntyOrsordmdresumenequiposDto;
import com.system.crosscutting.domain.model.EntyOrsordmdresumenequiposResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsordmdresumenequipos;
import com.system.crosscutting.persistence.repository.EntyOrsordmdresumenequiposRepository;
import com.system.modules.workcontrol.dataproviders.IjpaResumenEquiposDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaResumenEquiposDataProviders extends JpaDataProviderSupport
        implements IjpaResumenEquiposDataProviders {

    private final EntyOrsordmdresumenequiposRepository repository;

    @Override
    public EntyOrsordmdresumenequiposResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsordmdresumenequiposResponse getAll(
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
            Page<EntyOrsordmdresumenequipos> page;

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
                    page = repository.searchByTipoEquipo(search, pageable);
                    break;
                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;
                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyOrsordmdresumenequiposDto> data = page.getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsordmdresumenequiposDto.class))
                    .collect(Collectors.toList());

            EntyOrsordmdresumenequiposResponse response = new EntyOrsordmdresumenequiposResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-control-obras");
            response.setRspData(data);
            response.setRspPagination(buildPagination(pageNumber + 1, size, page));

            return response;
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando resumen de equipos", e);
        }
    }

    @Override
    public EntyOrsordmdresumenequiposDto get(Integer id) throws EBusinessException {
        try {
            EntyOrsordmdresumenequipos entity = repository.findById(id).orElse(null);
            return entity == null
                    ? new EntyOrsordmdresumenequiposDto()
                    : toDto(entity, EntyOrsordmdresumenequiposDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando resumen de equipo", e);
        }
    }

    @Override
    public EntyOrsordmdresumenequiposDto save(
            EntyOrsordmdresumenequiposDto dto
    ) throws EBusinessException {
        try {
            dto.setOrsPrimarykeyRseq(null);

            if (dto.getOrsValortotalRseq() == null
                    && dto.getOrsValorunidadRseq() != null
                    && dto.getOrsCantidunidadRseq() != null) {
                dto.setOrsValortotalRseq(
                        dto.getOrsValorunidadRseq()
                                .multiply(BigDecimal.valueOf(dto.getOrsCantidunidadRseq()))
                );
            }

            if (dto.getOrsTiporegistRseq() == null || dto.getOrsTiporegistRseq().isBlank()) {
                dto.setOrsTiporegistRseq("1");
            }

            if (dto.getOrsEstadoregRseq() == null || dto.getOrsEstadoregRseq().isBlank()) {
                dto.setOrsEstadoregRseq("1");
            }

            EntyOrsordmdresumenequipos entity = toEntity(dto, EntyOrsordmdresumenequipos.class);
            return toDto(repository.save(entity), EntyOrsordmdresumenequiposDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando resumen de equipo", e);
        }
    }

    @Override
    public List<EntyOrsordmdresumenequiposDto> save(
            List<EntyOrsordmdresumenequiposDto> dtos
    ) throws EBusinessException {
        List<EntyOrsordmdresumenequiposDto> result = new ArrayList<>();
        for (EntyOrsordmdresumenequiposDto dto : dtos) {
            result.add(save(dto));
        }
        return result;
    }

    @Override
    public EntyOrsordmdresumenequiposDto update(
            Integer id,
            EntyOrsordmdresumenequiposDto dto
    ) throws EBusinessException {
        try {
            EntyOrsordmdresumenequipos old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyOrsordmdresumenequiposDto();
            }

            dto.setOrsPrimarykeyRseq(id);
            BeanUtils.copyProperties(dto, old);

            return toDto(repository.save(old), EntyOrsordmdresumenequiposDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando resumen de equipo", e);
        }
    }

    @Override
    public void delete(Integer id) throws EBusinessException {
        try {
            repository.deleteById(id);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando resumen de equipo", e);
        }
    }

    @Override
    public List<EntyOrsordmdresumenequiposDto> findByOrden(
            String ordenKey
    ) throws EBusinessException {
        try {
            return repository.findByOrsIdentifkeyOrde(ordenKey)
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsordmdresumenequiposDto.class))
                    .collect(Collectors.toList());
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando resumen de equipos por orden", e);
        }
    }
}