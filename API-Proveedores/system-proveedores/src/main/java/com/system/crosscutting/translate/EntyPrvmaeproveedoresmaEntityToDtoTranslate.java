package com.system.crosscutting.translate;

import org.springframework.stereotype.Component;

import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyPrvmaeproveedoresma;
import com.system.crosscutting.utils.GsonUtil;

@Component
public class EntyPrvmaeproveedoresmaEntityToDtoTranslate
        implements Translator<EntyPrvmaeproveedoresma, EntyPrvmaeproveedoresmaDto> {

    @Override
    public EntyPrvmaeproveedoresmaDto translate(
            final EntyPrvmaeproveedoresma input
    ) throws EBusinessException {
        return GsonUtil.getGson().fromJson(
                GsonUtil.getGson().toJson(input),
                EntyPrvmaeproveedoresmaDto.class
        );
    }
}