package com.system.crosscutting.translate;

import org.springframework.stereotype.Component;

import com.system.crosscutting.domain.model.EntyRechomeestadistDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyRechomeestadist;
import com.system.crosscutting.utils.GsonUtil;

@Component
public class EntyRechomeestadistEntityToDtoTranslate implements Translator<EntyRechomeestadist, EntyRechomeestadistDto> {

    @Override
    public EntyRechomeestadistDto translate(EntyRechomeestadist input) throws EBusinessException {
      EntyRechomeestadistDto output  = GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(input), EntyRechomeestadistDto.class);
        return output;
    }
    
}
