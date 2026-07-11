package com.system.crosscutting.translate;

import org.springframework.stereotype.Component;

import com.system.crosscutting.domain.model.EntyDiaProyeccionSemanaDto;
import com.system.crosscutting.persistence.entity.EntityDiaProyeccionSemana;

@Component
public class EntyDiaProyeccionSemanaDtoToEntityTranslate {

    public EntityDiaProyeccionSemana translate(
            final EntyDiaProyeccionSemanaDto dto
    ) {
        if (dto == null) {
            return null;
        }

        EntityDiaProyeccionSemana entity = new EntityDiaProyeccionSemana();

        entity.setOrsPrimarykeyDpse(dto.getOrsPrimarykeyDpse());
        entity.setOrsIdentifkeyDpse(dto.getOrsIdentifkeyDpse());
        entity.setOrsIdentifkeyPsem(dto.getOrsIdentifkeyPsem());
        entity.setOrsIdentifkeyOrde(dto.getOrsIdentifkeyOrde());
        entity.setOrsFechaDpse(dto.getOrsFechaDpse());
        entity.setOrsNombrediaDpse(dto.getOrsNombrediaDpse());
        entity.setOrsEshabilDpse(dto.getOrsEshabilDpse());
        entity.setOrsEstrabajadoDpse(dto.getOrsEstrabajadoDpse());
        entity.setOrsObservacionDpse(dto.getOrsObservacionDpse());
        entity.setOrsTiporegistDpse(dto.getOrsTiporegistDpse());
        entity.setOrsEstadoregDpse(dto.getOrsEstadoregDpse());

        return entity;
    }
}