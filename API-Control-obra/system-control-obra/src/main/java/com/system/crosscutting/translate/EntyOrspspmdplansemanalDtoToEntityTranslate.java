package com.system.crosscutting.translate;

import org.springframework.stereotype.Component;

import com.system.crosscutting.domain.model.EntyOrspspmdplansemanalDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrspspmdplansemanal;
import com.system.crosscutting.utils.GsonUtil;

@Component
public class EntyOrspspmdplansemanalDtoToEntityTranslate
        implements Translator<EntyOrspspmdplansemanalDto, EntyOrspspmdplansemanal> {

    @Override
    public EntyOrspspmdplansemanal translate(
            final EntyOrspspmdplansemanalDto input
    ) throws EBusinessException {
        return GsonUtil.getGson(false).fromJson(
                GsonUtil.getGson().toJson(input),
                EntyOrspspmdplansemanal.class
        );
    }
}