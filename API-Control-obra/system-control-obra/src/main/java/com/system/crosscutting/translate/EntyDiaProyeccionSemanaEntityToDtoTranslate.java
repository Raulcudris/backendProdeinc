package com.system.crosscutting.translate;

import org.springframework.stereotype.Component;

import com.system.crosscutting.domain.model.EntyDiaProyeccionSemanaDto;
import com.system.crosscutting.persistence.entity.EntityDiaProyeccionSemana;

@Component
public class EntyDiaProyeccionSemanaEntityToDtoTranslate {

    public EntyDiaProyeccionSemanaDto translate(
            final EntityDiaProyeccionSemana entity
    ) {
        if (entity == null) {
            return null;
        }

        EntyDiaProyeccionSemanaDto dto = new EntyDiaProyeccionSemanaDto();

        dto.setOrsPrimarykeyDpse(entity.getOrsPrimarykeyDpse());
        dto.setOrsIdentifkeyDpse(entity.getOrsIdentifkeyDpse());
        dto.setOrsIdentifkeyPsem(entity.getOrsIdentifkeyPsem());
        dto.setOrsIdentifkeyOrde(entity.getOrsIdentifkeyOrde());
        dto.setOrsFechaDpse(entity.getOrsFechaDpse());
        dto.setOrsNombrediaDpse(entity.getOrsNombrediaDpse());
        dto.setOrsEshabilDpse(entity.getOrsEshabilDpse());
        dto.setOrsEstrabajadoDpse(entity.getOrsEstrabajadoDpse());
        dto.setOrsObservacionDpse(entity.getOrsObservacionDpse());
        dto.setOrsTiporegistDpse(entity.getOrsTiporegistDpse());
        dto.setOrsEstadoregDpse(entity.getOrsEstadoregDpse());

        return dto;
    }
}