package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyEvirefmdreferencia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyEvirefmdreferenciaRepository
        extends JpaRepository<EntyEvirefmdreferencia, Integer> {

    Optional<EntyEvirefmdreferencia> findByEviIdentifkeyRefe(
            String referenciaKey
    );

    boolean existsByEviIdentifkeyRefe(
            String referenciaKey
    );

    List<EntyEvirefmdreferencia> findByEviIdentifkeyEvidOrderByEviPrimarykeyRefeAsc(
            String evidenciaKey
    );

    List<EntyEvirefmdreferencia> findByEviTiporegistroRefeOrderByEviPrimarykeyRefeDesc(
            String tipoRegistro
    );

    List<EntyEvirefmdreferencia>
    findByEviTiporegistroRefeAndEviIdentifregistroRefeOrderByEviPrimarykeyRefeDesc(
            String tipoRegistro,
            String identificadorRegistro
    );

    @Query(
            "SELECT e FROM EntyEvirefmdreferencia e " +
                    "WHERE :id IS NULL OR e.eviPrimarykeyRefe = :id " +
                    "ORDER BY e.eviPrimarykeyRefe DESC"
    )
    Page<EntyEvirefmdreferencia> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyEvirefmdreferencia e " +
                    "WHERE LOWER(e.eviIdentifkeyEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.eviPrimarykeyRefe DESC"
    )
    Page<EntyEvirefmdreferencia> searchByEvidencia(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyEvirefmdreferencia e " +
                    "WHERE LOWER(e.eviTiporegistroRefe) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(e.eviIdentifregistroRefe) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.eviPrimarykeyRefe DESC"
    )
    Page<EntyEvirefmdreferencia> searchByRegistro(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyEvirefmdreferencia e " +
                    "WHERE LOWER(e.eviEstadoregRefe) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.eviPrimarykeyRefe DESC"
    )
    Page<EntyEvirefmdreferencia> searchByStatus(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyEvirefmdreferencia e " +
                    "WHERE LOWER(e.eviIdentifkeyRefe) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(e.eviIdentifkeyEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(e.eviTiporegistroRefe) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(e.eviIdentifregistroRefe) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(e.eviObservacionRefe) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(e.eviEstadoregRefe) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.eviPrimarykeyRefe DESC"
    )
    Page<EntyEvirefmdreferencia> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}