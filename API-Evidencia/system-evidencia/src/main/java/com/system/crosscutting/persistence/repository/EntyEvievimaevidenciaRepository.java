package com.system.crosscutting.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyEvievimaevidencia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio JPA para consultar y administrar evidencias.
 */
public interface EntyEvievimaevidenciaRepository
        extends JpaRepository<EntyEvievimaevidencia, Integer> {

    Optional<EntyEvievimaevidencia> findByEviIdentifkeyEvid(String eviIdentifkeyEvid);

    List<EntyEvievimaevidencia> findByEviIdentifkeyTiev(String eviIdentifkeyTiev);

    List<EntyEvievimaevidencia> findByEviUsuariocreaEvid(String eviUsuariocreaEvid);

    List<EntyEvievimaevidencia> findByEviEstadoregEvid(String eviEstadoregEvid);

    @Query("SELECT e FROM EntyEvievimaevidencia e " +
            "WHERE e.eviPrimarykeyEvid = :id")
    Page<EntyEvievimaevidencia> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyEvievimaevidencia e " +
            "WHERE LOWER(e.eviIdentifkeyEvid) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvievimaevidencia> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyEvievimaevidencia e " +
            "WHERE LOWER(e.eviIdentifkeyTiev) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvievimaevidencia> searchByTipoEvidencia(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyEvievimaevidencia e " +
            "WHERE LOWER(e.eviUsuariocreaEvid) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvievimaevidencia> searchByUsuario(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyEvievimaevidencia e " +
            "WHERE e.eviEstadoregEvid = :status")
    Page<EntyEvievimaevidencia> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyEvievimaevidencia e " +
            "WHERE e.eviFechacapturaEvid BETWEEN :fechaInicio AND :fechaFin")
    Page<EntyEvievimaevidencia> searchByFechaCapturaBetween(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyEvievimaevidencia e " +
            "WHERE LOWER(e.eviIdentifkeyEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.eviIdentifkeyTiev) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.eviNombreEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.eviDescripcionEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.eviUrlarchivoEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.eviUsuariocreaEvid) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvievimaevidencia> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}