package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyEvievimaevidencia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyEvievimaevidenciaRepository extends JpaRepository<EntyEvievimaevidencia, Integer> {


    Optional<EntyEvievimaevidencia> findByEviIdentifkeyEvid(String eviIdentifkeyEvid);

    boolean existsByEviIdentifkeyEvid(
            String evidenciaKey
    );

    List<EntyEvievimaevidencia> findByEviIdentifkeyTievOrderByEviFechacapturaEvidDesc(
            String tipoEvidenciaKey
    );

    List<EntyEvievimaevidencia> findByEviEstadoregEvidOrderByEviPrimarykeyEvidDesc(
            String estado
    );

    @Query(
            "SELECT e FROM EntyEvievimaevidencia e " +
                    "WHERE :id IS NULL OR e.eviPrimarykeyEvid = :id " +
                    "ORDER BY e.eviPrimarykeyEvid DESC"
    )
    Page<EntyEvievimaevidencia> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyEvievimaevidencia e " +
                    "WHERE LOWER(e.eviIdentifkeyEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.eviPrimarykeyEvid DESC"
    )
    Page<EntyEvievimaevidencia> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyEvievimaevidencia e " +
                    "WHERE LOWER(e.eviIdentifkeyTiev) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.eviPrimarykeyEvid DESC"
    )
    Page<EntyEvievimaevidencia> searchByTipo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyEvievimaevidencia e " +
                    "WHERE LOWER(e.eviEstadoregEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.eviPrimarykeyEvid DESC"
    )
    Page<EntyEvievimaevidencia> searchByStatus(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyEvievimaevidencia e " +
                    "WHERE LOWER(e.eviIdentifkeyEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(e.eviIdentifkeyTiev) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(e.eviNombrearchivoEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(e.eviDescripcionEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(e.eviUrlarchivoEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(e.eviEstadoregEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.eviPrimarykeyEvid DESC"
    )
    Page<EntyEvievimaevidencia> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );

}