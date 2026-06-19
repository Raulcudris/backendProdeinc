package com.system.modules.controlobras.usecase;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoDto;
import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.dataproviders.IjpaPlanTrabajoDataProviders;

@Service
public class EntyPlanTrabajoService {

    @Autowired
    private IjpaPlanTrabajoDataProviders dataProvider;

    public EntyOrsplamaplandetrabajoResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsplamaplandetrabajoDto get(Integer id) throws EBusinessException {
        return dataProvider.get(id);
    }

    public EntyOrsplamaplandetrabajoDto saveBefore(
            EntyOrsplamaplandetrabajoDto dto
    ) throws EBusinessException {

        aplicarDefaults(dto);

        return dataProvider.save(dto);
    }

    public List<EntyOrsplamaplandetrabajoDto> saveBefore(
            final List<EntyOrsplamaplandetrabajoDto> dtoList
    ) throws EBusinessException {

        if (dtoList == null || dtoList.isEmpty()) {
            return List.of();
        }

        for (EntyOrsplamaplandetrabajoDto dto : dtoList) {
            aplicarDefaults(dto);
        }

        return dataProvider.save(dtoList);
    }

    public EntyOrsplamaplandetrabajoDto updateBefore(
            Integer id,
            EntyOrsplamaplandetrabajoDto dto
    ) throws EBusinessException {

        aplicarDefaults(dto);

        return dataProvider.update(id, dto);
    }

    public Object findByOrden(String ordenKey) throws EBusinessException {
        return dataProvider.findByOrden(ordenKey);
    }

    public Object findByPunto(String puntoKey) throws EBusinessException {
        return dataProvider.findByPunto(puntoKey);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyOrsplamaplandetrabajoDto dto = dataProvider.get(id);

        if (dto == null || dto.getOrsPrimarykeyPltr() == null) {
            return "Registro no encontrado";
        }

        dto.setOrsEstadoregPltr(estado);
        dataProvider.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(Integer id) throws EBusinessException {
        dataProvider.delete(id);
        return "Registro eliminado correctamente";
    }

    private void aplicarDefaults(
            EntyOrsplamaplandetrabajoDto dto
    ) {
        if (dto == null) {
            return;
        }

        if (dto.getOrsEstadoregPltr() == null || dto.getOrsEstadoregPltr().isBlank()) {
            dto.setOrsEstadoregPltr("1");
        }

        if (dto.getOrsTiporegistPltr() == null || dto.getOrsTiporegistPltr().isBlank()) {
            dto.setOrsTiporegistPltr("1");
        }

        if (dto.getOrsCantidunidadRseq() != null &&
                dto.getOrsValorunidadRseq() != null &&
                dto.getOrsValortotalRseq() == null) {

            dto.setOrsValortotalRseq(
                    dto.getOrsValorunidadRseq()
                            .multiply(BigDecimal.valueOf(dto.getOrsCantidunidadRseq()))
            );
        }
    }
}