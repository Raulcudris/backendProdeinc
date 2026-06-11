package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.system.crosscutting.persistence.entity.EntyEvirefmdreferencia;

public interface EntyEvirefmdreferenciaRepository
        extends JpaRepository<EntyEvirefmdreferencia, Integer> {

    Optional<EntyEvirefmdreferencia> findByEviIdentifkeyRefe(
            String eviIdentifkeyRefe
    );

    List<EntyEvirefmdreferencia> findByEviIdentifkeyEvid(
            String eviIdentifkeyEvid
    );

    List<EntyEvirefmdreferencia> findByEviIdentifregistroRefe(
            String eviIdentifregistroRefe
    );

    List<EntyEvirefmdreferencia> findByEviTiporegistroRefe(
            String eviTiporegistroRefe
    );

    List<EntyEvirefmdreferencia> findByEviTiporegistroRefeAndEviIdentifregistroRefe(
            String eviTiporegistroRefe,
            String eviIdentifregistroRefe
    );

    List<EntyEvirefmdreferencia> findByEviEstadoregRefe(
            String eviEstadoregRefe
    );

    Page<EntyEvirefmdreferencia> findByEviIdentifkeyEvidContainingIgnoreCase(
            String eviIdentifkeyEvid,
            Pageable pageable
    );

    Page<EntyEvirefmdreferencia> findByEviIdentifregistroRefeContainingIgnoreCase(
            String eviIdentifregistroRefe,
            Pageable pageable
    );

    Page<EntyEvirefmdreferencia> findByEviTiporegistroRefeContainingIgnoreCase(
            String eviTiporegistroRefe,
            Pageable pageable
    );

    Page<EntyEvirefmdreferencia> findByEviEstadoregRefeContainingIgnoreCase(
            String eviEstadoregRefe,
            Pageable pageable
    );

    Page<EntyEvirefmdreferencia> findByEviIdentifkeyRefeContainingIgnoreCaseOrEviIdentifkeyEvidContainingIgnoreCaseOrEviIdentifregistroRefeContainingIgnoreCaseOrEviObservacionRefeContainingIgnoreCase(
            String referenciaKey,
            String evidenciaKey,
            String registroKey,
            String observacion,
            Pageable pageable
    );
}