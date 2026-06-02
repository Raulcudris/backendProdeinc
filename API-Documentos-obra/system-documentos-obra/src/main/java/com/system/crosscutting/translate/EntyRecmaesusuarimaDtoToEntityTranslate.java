package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyRecmaesusuarimaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyRecmaesusuarima;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

@Component
public class EntyRecmaesusuarimaDtoToEntityTranslate implements Translator<EntyRecmaesusuarimaDto, EntyRecmaesusuarima>{
    @Override
    public EntyRecmaesusuarima translate(EntyRecmaesusuarimaDto input) throws EBusinessException {
        return GsonUtil.getGson(false)
                .fromJson(GsonUtil.getGson().toJson(input), EntyRecmaesusuarima.class);
    }

}
