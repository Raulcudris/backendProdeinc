package com.system.modules.proveedores.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaDto;
import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.proveedores.contracts.IjpaDataProviders;

public interface IjpaProveedorDataProviders
        extends IjpaDataProviders<EntyPrvmaeproveedoresmaDto, EntyPrvmaeproveedoresmaResponse> {

    EntyPrvmaeproveedoresmaDto findByKey(
            String proveedorKey
    ) throws EBusinessException;

    List<EntyPrvmaeproveedoresmaDto> findByEstado(
            String estado
    ) throws EBusinessException;
}