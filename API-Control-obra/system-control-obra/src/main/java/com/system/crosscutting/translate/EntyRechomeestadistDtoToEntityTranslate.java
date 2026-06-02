package com.system.crosscutting.translate;
import org.springframework.stereotype.Component;
import com.system.crosscutting.domain.model.EntyRechomeestadistDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyRechomeestadist;
import com.system.crosscutting.utils.GsonUtil;

@Component
public class EntyRechomeestadistDtoToEntityTranslate implements Translator<EntyRechomeestadistDto, EntyRechomeestadist>{

    @Override
    public EntyRechomeestadist translate(EntyRechomeestadistDto input) throws EBusinessException {
        return  GsonUtil.getGson(false)
                .fromJson(GsonUtil.getGson().toJson(input), EntyRechomeestadist.class);
    }
    
}
