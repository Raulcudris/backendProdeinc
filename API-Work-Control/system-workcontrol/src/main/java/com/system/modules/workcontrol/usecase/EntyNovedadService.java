package com.system.modules.workcontrol.usecase;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriDto;
import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.dataproviders.IjpaNovedadHistoriDataProviders;

@Service
public class EntyNovedadService {

    @Autowired
    private IjpaNovedadHistoriDataProviders dataProvider;

    public EntyOrsconfnovedadhistoriResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsconfnovedadhistoriDto get(Integer id) throws EBusinessException {
        return dataProvider.get(id);
    }

    public EntyOrsconfnovedadhistoriDto saveBefore(
            EntyOrsconfnovedadhistoriDto dto
    ) throws EBusinessException {

        aplicarDefaults(dto);

        return dataProvider.save(dto);
    }

    public List<EntyOrsconfnovedadhistoriDto> saveBefore(
            List<EntyOrsconfnovedadhistoriDto> dtoList
    ) throws EBusinessException {

        if (dtoList == null || dtoList.isEmpty()) {
            return List.of();
        }

        for (EntyOrsconfnovedadhistoriDto dto : dtoList) {
            aplicarDefaults(dto);
        }

        return dataProvider.save(dtoList);
    }

    public EntyOrsconfnovedadhistoriDto updateBefore(
            Integer id,
            EntyOrsconfnovedadhistoriDto dto
    ) throws EBusinessException {

        aplicarDefaults(dto);

        return dataProvider.update(id, dto);
    }

    public Object findByOrden(String ordenKey) throws EBusinessException {
        return dataProvider.findByOrden(ordenKey);
    }

    public Object findByRegistroBase(String registroBase) throws EBusinessException {
        return dataProvider.findByRegistroBase(registroBase);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyOrsconfnovedadhistoriDto dto = dataProvider.get(id);

        if (dto == null || dto.getOrsPrimarykeyNove() == null) {
            return "Registro no encontrado";
        }

        dto.setOrsEstadoregNove(estado);
        dataProvider.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(Integer id) throws EBusinessException {
        dataProvider.delete(id);
        return "Registro eliminado correctamente";
    }

    private void aplicarDefaults(
            EntyOrsconfnovedadhistoriDto dto
    ) {
        if (dto == null) {
            return;
        }

        if (dto.getOrsEstadoregNove() == null || dto.getOrsEstadoregNove().isBlank()) {
            dto.setOrsEstadoregNove("1");
        }

        if (dto.getOrsFechreportNove() == null) {
            dto.setOrsFechreportNove(LocalDate.now());
        }
    }
}