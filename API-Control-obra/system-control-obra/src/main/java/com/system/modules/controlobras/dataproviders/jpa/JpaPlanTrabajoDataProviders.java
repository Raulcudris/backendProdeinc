package com.system.modules.controlobras.dataproviders.jpa;

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

import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoDto;
import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsplamaplandetrabajo;
import com.system.crosscutting.persistence.repository.EntyOrsplamaplandetrabajoRepository;
import com.system.modules.controlobras.dataproviders.IjpaPlanTrabajoDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaPlanTrabajoDataProviders extends JpaDataProviderSupport
        implements IjpaPlanTrabajoDataProviders {

    private final EntyOrsplamaplandetrabajoRepository repository;

    @Override
    public EntyOrsplamaplandetrabajoResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsplamaplandetrabajoResponse getAll(
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
            Page<EntyOrsplamaplandetrabajo> page;

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
                case "PUNTO":
                    page = repository.searchByPunto(search, pageable);
                    break;
                case "EQUIPO":
                    page = repository.searchByEquipoInventario(search, pageable);
                    break;
                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;
                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyOrsplamaplandetrabajoDto> data = page.getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsplamaplandetrabajoDto.class))
                    .collect(Collectors.toList());

            EntyOrsplamaplandetrabajoResponse response = new EntyOrsplamaplandetrabajoResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-control-obras");
            response.setRspData(data);
            response.setRspPagination(buildPagination(pageNumber + 1, size, page));

            return response;
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando planes de trabajo", e);
        }
    }

    @Override
    public EntyOrsplamaplandetrabajoDto get(Integer id) throws EBusinessException {
        try {
            EntyOrsplamaplandetrabajo entity = repository.findById(id).orElse(null);
            return entity == null
                    ? new EntyOrsplamaplandetrabajoDto()
                    : toDto(entity, EntyOrsplamaplandetrabajoDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando plan de trabajo", e);
        }
    }

    @Override
    public EntyOrsplamaplandetrabajoDto save(
            EntyOrsplamaplandetrabajoDto dto
    ) throws EBusinessException {
        try {
            dto.setOrsPrimarykeyPltr(null);

            if (dto.getOrsValortotalRseq() == null
                    && dto.getOrsValorunidadRseq() != null
                    && dto.getOrsCantidunidadRseq() != null) {
                dto.setOrsValortotalRseq(
                        dto.getOrsValorunidadRseq()
                                .multiply(BigDecimal.valueOf(dto.getOrsCantidunidadRseq()))
                );
            }

            if (dto.getOrsTiporegistPltr() == null || dto.getOrsTiporegistPltr().isBlank()) {
                dto.setOrsTiporegistPltr("1");
            }

            if (dto.getOrsEstadoregPltr() == null || dto.getOrsEstadoregPltr().isBlank()) {
                dto.setOrsEstadoregPltr("1");
            }

            EntyOrsplamaplandetrabajo entity = toEntity(dto, EntyOrsplamaplandetrabajo.class);
            return toDto(repository.save(entity), EntyOrsplamaplandetrabajoDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando plan de trabajo", e);
        }
    }

    @Override
    public List<EntyOrsplamaplandetrabajoDto> save(
            List<EntyOrsplamaplandetrabajoDto> dtos
    ) throws EBusinessException {
        List<EntyOrsplamaplandetrabajoDto> result = new ArrayList<>();
        for (EntyOrsplamaplandetrabajoDto dto : dtos) {
            result.add(save(dto));
        }
        return result;
    }

    @Override
    public EntyOrsplamaplandetrabajoDto update(
            Integer id,
            EntyOrsplamaplandetrabajoDto dto
    ) throws EBusinessException {
        try {
            EntyOrsplamaplandetrabajo old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyOrsplamaplandetrabajoDto();
            }

            dto.setOrsPrimarykeyPltr(id);
            BeanUtils.copyProperties(dto, old);

            return toDto(repository.save(old), EntyOrsplamaplandetrabajoDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando plan de trabajo", e);
        }
    }

    @Override
    public void delete(Integer id) throws EBusinessException {
        try {
            repository.deleteById(id);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando plan de trabajo", e);
        }
    }

    @Override
    public List<EntyOrsplamaplandetrabajoDto> findByOrden(
            String ordenKey
    ) throws EBusinessException {
        try {
            return repository.findByOrsIdentifkeyOrde(ordenKey)
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsplamaplandetrabajoDto.class))
                    .collect(Collectors.toList());
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando planes por orden", e);
        }
    }

    @Override
    public List<EntyOrsplamaplandetrabajoDto> findByPunto(
            String puntoKey
    ) throws EBusinessException {
        try {
            return repository.findByOrsIdentifkeyPunt(puntoKey)
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsplamaplandetrabajoDto.class))
                    .collect(Collectors.toList());
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando planes por punto", e);
        }
    }
}