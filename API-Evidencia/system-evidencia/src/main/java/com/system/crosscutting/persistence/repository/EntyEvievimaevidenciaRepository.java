package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyEvievimaevidencia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyEvievimaevidenciaRepository
        extends JpaRepository<EntyEvievimaevidencia, Integer> {

    Optional<EntyEvievimaevidencia> findByEviIdentifkeyEvid(
            String eviIdentifkeyEvid
    );

    List<EntyEvievimaevidencia> findByEviIdentifkeyTiev(
            String eviIdentifkeyTiev
    );

    List<EntyEvievimaevidencia> findByEviEstadoregEvid(
            String eviEstadoregEvid
    );

    @Query("SELECT e FROM EntyEvievimaevidencia e WHERE e.eviPrimarykeyEvid = :id")
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
    Page<EntyEvievimaevidencia> searchByTipo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyEvievimaevidencia e " +
            "WHERE LOWER(e.eviNombrearchivoEvid) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvievimaevidencia> searchByNombreArchivo(
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
            "WHERE LOWER(e.eviIdentifkeyEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.eviIdentifkeyTiev) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.eviNombrearchivoEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.eviDescripcionEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.eviUrlarchivoEvid) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvievimaevidencia> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}