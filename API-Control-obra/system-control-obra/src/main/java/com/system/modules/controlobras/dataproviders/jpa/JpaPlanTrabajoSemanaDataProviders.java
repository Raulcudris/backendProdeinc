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

import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaDto;
import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsplamdplantrabsemana;
import com.system.crosscutting.persistence.repository.EntyOrsplamdplantrabsemanaRepository;
import com.system.modules.controlobras.dataproviders.IjpaPlanTrabajoSemanaDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaPlanTrabajoSemanaDataProviders extends JpaDataProviderSupport
        implements IjpaPlanTrabajoSemanaDataProviders {

    private final EntyOrsplamdplantrabsemanaRepository repository;

    @Override
    public EntyOrsplamdplantrabsemanaResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsplamdplantrabsemanaResponse getAll(
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
            Page<EntyOrsplamdplantrabsemana> page;

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
                case "PLAN":
                    page = repository.searchByPlanTrabajo(search, pageable);
                    break;
                case "PROYECCION":
                    page = repository.searchByProyeccionSemana(search, pageable);
                    break;
                case "STATUS":
                    page = repository.searchByStatus(search, pageable);
                    break;
                default:
                    page = repository.searchByText(search, pageable);
                    break;
            }

            List<EntyOrsplamdplantrabsemanaDto> data = page.getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsplamdplantrabsemanaDto.class))
                    .collect(Collectors.toList());

            EntyOrsplamdplantrabsemanaResponse response = new EntyOrsplamdplantrabsemanaResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-control-obras");
            response.setRspData(data);
            response.setRspPagination(buildPagination(pageNumber + 1, size, page));

            return response;
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando plan de trabajo semanal", e);
        }
    }

    @Override
    public EntyOrsplamdplantrabsemanaDto get(Integer id) throws EBusinessException {
        try {
            EntyOrsplamdplantrabsemana entity = repository.findById(id).orElse(null);
            return entity == null
                    ? new EntyOrsplamdplantrabsemanaDto()
                    : toDto(entity, EntyOrsplamdplantrabsemanaDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando plan de trabajo semanal", e);
        }
    }

    @Override
    public EntyOrsplamdplantrabsemanaDto save(
            EntyOrsplamdplantrabsemanaDto dto
    ) throws EBusinessException {
        try {
            dto.setOrsPrimarykeyPlse(null);

            if (dto.getOrsEjecutunidadPlse() == null) {
                dto.setOrsEjecutunidadPlse(0);
            }

            if (dto.getOrsValortotalPlse() == null
                    && dto.getOrsValorunidadPlse() != null
                    && dto.getOrsCantidunidadPlse() != null) {
                dto.setOrsValortotalPlse(
                        dto.getOrsValorunidadPlse()
                                .multiply(BigDecimal.valueOf(dto.getOrsCantidunidadPlse()))
                );
            }

            if (dto.getOrsValorejecutPlse() == null) {
                dto.setOrsValorejecutPlse(BigDecimal.ZERO);
            }

            if (dto.getOrsTiporegistPlse() == null || dto.getOrsTiporegistPlse().isBlank()) {
                dto.setOrsTiporegistPlse("1");
            }

            if (dto.getOrsEstadoregPlse() == null || dto.getOrsEstadoregPlse().isBlank()) {
                dto.setOrsEstadoregPlse("1");
            }

            EntyOrsplamdplantrabsemana entity = toEntity(dto, EntyOrsplamdplantrabsemana.class);
            return toDto(repository.save(entity), EntyOrsplamdplantrabsemanaDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando plan de trabajo semanal", e);
        }
    }

    @Override
    public List<EntyOrsplamdplantrabsemanaDto> save(
            List<EntyOrsplamdplantrabsemanaDto> dtos
    ) throws EBusinessException {
        List<EntyOrsplamdplantrabsemanaDto> result = new ArrayList<>();
        for (EntyOrsplamdplantrabsemanaDto dto : dtos) {
            result.add(save(dto));
        }
        return result;
    }

    @Override
    public EntyOrsplamdplantrabsemanaDto update(
            Integer id,
            EntyOrsplamdplantrabsemanaDto dto
    ) throws EBusinessException {
        try {
            EntyOrsplamdplantrabsemana old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyOrsplamdplantrabsemanaDto();
            }

            dto.setOrsPrimarykeyPlse(id);
            BeanUtils.copyProperties(dto, old);

            return toDto(repository.save(old), EntyOrsplamdplantrabsemanaDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando plan de trabajo semanal", e);
        }
    }

    @Override
    public void delete(Integer id) throws EBusinessException {
        try {
            repository.deleteById(id);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando plan de trabajo semanal", e);
        }
    }

    @Override
    public List<EntyOrsplamdplantrabsemanaDto> findByOrden(
            String ordenKey
    ) throws EBusinessException {
        try {
            return repository.findByOrsIdentifkeyOrde(ordenKey)
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsplamdplantrabsemanaDto.class))
                    .collect(Collectors.toList());
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando planes semanales por orden", e);
        }
    }

    @Override
    public List<EntyOrsplamdplantrabsemanaDto> findByPlan(
            String planKey
    ) throws EBusinessException {
        try {
            return repository.findByOrsIdentifkeyPltr(planKey)
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsplamdplantrabsemanaDto.class))
                    .collect(Collectors.toList());
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando planes semanales por plan", e);
        }
    }

    @Override
    public List<EntyOrsplamdplantrabsemanaDto> findByProyeccionSemana(
            String proyeccionKey
    ) throws EBusinessException {
        try {
            return repository.findByOrsIdentifkeyPsem(proyeccionKey)
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsplamdplantrabsemanaDto.class))
                    .collect(Collectors.toList());
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando planes semanales por proyección", e);
        }
    }
}