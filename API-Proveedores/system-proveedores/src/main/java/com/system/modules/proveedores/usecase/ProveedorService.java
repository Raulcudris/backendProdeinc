package com.system.modules.proveedores.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaDto;
import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.proveedores.dataproviders.IjpaProveedorDataProviders;

@Service
public class ProveedorService {

    @Autowired
    private IjpaProveedorDataProviders dataProviders;

    public EntyPrvmaeproveedoresmaResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyPrvmaeproveedoresmaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyPrvmaeproveedoresmaDto get(
            Integer id
    ) throws EBusinessException {
        return dataProviders.get(id);
    }

    public EntyPrvmaeproveedoresmaDto saveBefore(
            EntyPrvmaeproveedoresmaDto dto
    ) throws EBusinessException {

        if (dto.getPrvEstadoregMprv() == null || dto.getPrvEstadoregMprv().isBlank()) {
            dto.setPrvEstadoregMprv("1");
        }

        return dataProviders.save(dto);
    }

    public List<EntyPrvmaeproveedoresmaDto> saveBefore(
            List<EntyPrvmaeproveedoresmaDto> dtos
    ) throws EBusinessException {
        return dataProviders.save(dtos);
    }

    public EntyPrvmaeproveedoresmaDto updateBefore(
            Integer id,
            EntyPrvmaeproveedoresmaDto dto
    ) throws EBusinessException {
        return dataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyPrvmaeproveedoresmaDto dto = dataProviders.get(id);

        if (dto == null || dto.getPrvPrimarykeyMprv() == null) {
            return "No existe el proveedor con id: " + id;
        }

        dto.setPrvEstadoregMprv(estado);

        dataProviders.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(
            Integer id
    ) throws EBusinessException {
        dataProviders.delete(id);
        return "Registro eliminado correctamente";
    }

    public EntyPrvmaeproveedoresmaDto findByKey(
            String proveedorKey
    ) throws EBusinessException {
        return dataProviders.findByKey(proveedorKey);
    }

    public List<EntyPrvmaeproveedoresmaDto> findByEstado(
            String estado
    ) throws EBusinessException {
        return dataProviders.findByEstado(estado);
    }
}