package com.system.crosscutting.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntityDiaProyeccionSemana;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntyDiaProyeccionSemanaRepository
        extends JpaRepository<EntityDiaProyeccionSemana, Integer> {

    List<EntityDiaProyeccionSemana> findByOrsIdentifkeyPsemOrderByOrsFechaDpseAsc(
            String proyeccionKey
    );

    List<EntityDiaProyeccionSemana> findByOrsIdentifkeyOrdeOrderByOrsFechaDpseAsc(
            String ordenKey
    );

    Optional<EntityDiaProyeccionSemana> findByOrsIdentifkeyDpse(
            String diaKey
    );

    Optional<EntityDiaProyeccionSemana> findByOrsIdentifkeyPsemAndOrsFechaDpse(
            String proyeccionKey,
            LocalDate fecha
    );

    boolean existsByOrsIdentifkeyPsemAndOrsFechaDpse(
            String proyeccionKey,
            LocalDate fecha
    );
}