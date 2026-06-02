package com.system.crosscutting.translate;

import org.springframework.stereotype.Component;

import com.system.crosscutting.domain.model.EntyRecmaesusuarimaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyRecmaesusuarima;
import com.system.crosscutting.utils.GsonUtil;

@Component
public class EntyRecmaesusuarimaEntityToDtoTranslate implements Translator<EntyRecmaesusuarima, EntyRecmaesusuarimaDto>{

    @Override
    public EntyRecmaesusuarimaDto translate(EntyRecmaesusuarima input) throws EBusinessException {

        EntyRecmaesusuarimaDto output  = GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(input), EntyRecmaesusuarimaDto.class);
        return output;
    }
}
