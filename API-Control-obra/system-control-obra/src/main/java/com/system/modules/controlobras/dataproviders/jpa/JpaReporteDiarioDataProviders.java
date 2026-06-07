package com.system.modules.controlobras.dataproviders.jpa;

import java.math.BigDecimal;
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

import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioDto;
import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioResponse;
import com.system.crosscutting.exceptions.DataProvider;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsplamdplantrabsemana;
import com.system.crosscutting.persistence.entity.EntyOrsplamdreportediario;
import com.system.crosscutting.persistence.repository.EntyOrsplamdplantrabsemanaRepository;
import com.system.crosscutting.persistence.repository.EntyOrsplamdreportediarioRepository;
import com.system.modules.controlobras.dataproviders.IjpaReporteDiarioDataProviders;

import lombok.RequiredArgsConstructor;

@DataProvider
@RequiredArgsConstructor
public class JpaReporteDiarioDataProviders extends JpaDataProviderSupport
        implements IjpaReporteDiarioDataProviders {

    private final EntyOrsplamdreportediarioRepository repository;
    private final EntyOrsplamdplantrabsemanaRepository planSemanaRepository;

    @Override
    public EntyOrsplamdreportediarioResponse getAll() throws EBusinessException {
        return getAll(1, 10, "TEXT", "");
    }

    @Override
    public EntyOrsplamdreportediarioResponse getAll(
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
            Page<EntyOrsplamdreportediario> page;

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
                case "PLAN_SEMANA":
                    page = repository.searchByPlanSemana(search, pageable);
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

            List<EntyOrsplamdreportediarioDto> data = page.getContent()
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsplamdreportediarioDto.class))
                    .collect(Collectors.toList());

            EntyOrsplamdreportediarioResponse response = new EntyOrsplamdreportediarioResponse();
            response.setRspMessage("OK");
            response.setRspValue("OK");
            response.setRspParentKey("NA");
            response.setRspAppKey("msvc-control-obras");
            response.setRspData(data);
            response.setRspPagination(buildPagination(pageNumber + 1, size, page));

            return response;
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando reportes diarios", e);
        }
    }

    @Override
    public EntyOrsplamdreportediarioDto get(Integer id) throws EBusinessException {
        try {
            EntyOrsplamdreportediario entity = repository.findById(id).orElse(null);
            return entity == null
                    ? new EntyOrsplamdreportediarioDto()
                    : toDto(entity, EntyOrsplamdreportediarioDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando reporte diario", e);
        }
    }

    @Override
    public EntyOrsplamdreportediarioDto save(
            EntyOrsplamdreportediarioDto dto
    ) throws EBusinessException {
        try {
            dto.setOrsPrimarykeyPdia(null);

            if (dto.getOrsFechasistemaPdia() == null) {
                dto.setOrsFechasistemaPdia(LocalDate.now());
            }

            if (dto.getOrsTiporegistPdia() == null || dto.getOrsTiporegistPdia().isBlank()) {
                dto.setOrsTiporegistPdia("1");
            }

            if (dto.getOrsEstadoregPdia() == null || dto.getOrsEstadoregPdia().isBlank()) {
                dto.setOrsEstadoregPdia("1");
            }

            EntyOrsplamdreportediario entity = toEntity(dto, EntyOrsplamdreportediario.class);
            EntyOrsplamdreportediario saved = repository.save(entity);

            recalcularEjecutadoSemana(dto.getOrsIdentifkeyPlse());

            return toDto(saved, EntyOrsplamdreportediarioDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error creando reporte diario", e);
        }
    }

    @Override
    public List<EntyOrsplamdreportediarioDto> save(
            List<EntyOrsplamdreportediarioDto> dtos
    ) throws EBusinessException {
        List<EntyOrsplamdreportediarioDto> result = new ArrayList<>();
        for (EntyOrsplamdreportediarioDto dto : dtos) {
            result.add(save(dto));
        }
        return result;
    }

    @Override
    public EntyOrsplamdreportediarioDto update(
            Integer id,
            EntyOrsplamdreportediarioDto dto
    ) throws EBusinessException {
        try {
            EntyOrsplamdreportediario old = repository.findById(id).orElse(null);

            if (old == null) {
                return new EntyOrsplamdreportediarioDto();
            }

            String oldPlanSemanaKey = old.getOrsIdentifkeyPlse();

            dto.setOrsPrimarykeyPdia(id);
            BeanUtils.copyProperties(dto, old);

            EntyOrsplamdreportediario saved = repository.save(old);

            recalcularEjecutadoSemana(oldPlanSemanaKey);
            recalcularEjecutadoSemana(dto.getOrsIdentifkeyPlse());

            return toDto(saved, EntyOrsplamdreportediarioDto.class);
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error actualizando reporte diario", e);
        }
    }

    @Override
    public void delete(Integer id) throws EBusinessException {
        try {
            EntyOrsplamdreportediario old = repository.findById(id).orElse(null);

            if (old != null) {
                String planSemanaKey = old.getOrsIdentifkeyPlse();
                repository.delete(old);
                recalcularEjecutadoSemana(planSemanaKey);
            }
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error eliminando reporte diario", e);
        }
    }

    @Override
    public List<EntyOrsplamdreportediarioDto> findByOrden(
            String ordenKey
    ) throws EBusinessException {
        try {
            return repository.findByOrsIdentifkeyOrde(ordenKey)
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsplamdreportediarioDto.class))
                    .collect(Collectors.toList());
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando reportes diarios por orden", e);
        }
    }

    @Override
    public List<EntyOrsplamdreportediarioDto> findByPlanSemana(
            String planSemanaKey
    ) throws EBusinessException {
        try {
            return repository.findByOrsIdentifkeyPlse(planSemanaKey)
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsplamdreportediarioDto.class))
                    .collect(Collectors.toList());
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando reportes diarios por plan semanal", e);
        }
    }

    @Override
    public List<EntyOrsplamdreportediarioDto> findByProyeccionSemana(
            String proyeccionKey
    ) throws EBusinessException {
        try {
            return repository.findByOrsIdentifkeyPsem(proyeccionKey)
                    .stream()
                    .map(entity -> toDto(entity, EntyOrsplamdreportediarioDto.class))
                    .collect(Collectors.toList());
        } catch (PersistenceException | DataAccessException e) {
            throw buildException("Error consultando reportes diarios por proyección", e);
        }
    }

    private void recalcularEjecutadoSemana(String planSemanaKey) {
        if (planSemanaKey == null || planSemanaKey.isBlank()) {
            return;
        }

        EntyOrsplamdplantrabsemana planSemana = planSemanaRepository
                .findByOrsIdentifkeyPlse(planSemanaKey)
                .orElse(null);

        if (planSemana == null) {
            return;
        }

        Integer ejecutado = repository.sumEjecutadoByPlanSemana(planSemanaKey);

        if (ejecutado == null) {
            ejecutado = 0;
        }

        planSemana.setOrsEjecutunidadPlse(ejecutado);

        if (planSemana.getOrsValorunidadPlse() != null) {
            planSemana.setOrsValorejecutPlse(
                    planSemana.getOrsValorunidadPlse()
                            .multiply(BigDecimal.valueOf(ejecutado))
            );
        }

        planSemanaRepository.save(planSemana);
    }
}