package com.system.crosscutting.translate;

import org.springframework.stereotype.Component;

import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyPrvmaeproveedoresma;
import com.system.crosscutting.utils.GsonUtil;

@Component
public class EntyPrvmaeproveedoresmaDtoToEntityTranslate
        implements Translator<EntyPrvmaeproveedoresmaDto, EntyPrvmaeproveedoresma> {

    @Override
    public EntyPrvmaeproveedoresma translate(
            final EntyPrvmaeproveedoresmaDto input
    ) throws EBusinessException {
        return GsonUtil.getGson(false).fromJson(
                GsonUtil.getGson().toJson(input),
                EntyPrvmaeproveedoresma.class
        );
    }
}