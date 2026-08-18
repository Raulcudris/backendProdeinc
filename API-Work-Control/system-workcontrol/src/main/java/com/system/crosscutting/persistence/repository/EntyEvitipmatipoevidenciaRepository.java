package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyEvitipmatipoevidencia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyEvitipmatipoevidenciaRepository
        extends JpaRepository<EntyEvitipmatipoevidencia, Integer> {

    Optional<EntyEvitipmatipoevidencia> findByEviIdentifkeyTiev(
            String tipoEvidenciaKey
    );

    boolean existsByEviIdentifkeyTiev(
            String tipoEvidenciaKey
    );

    List<EntyEvitipmatipoevidencia> findByEviEstadoregTievOrderByEviDescripcionTievAsc(
            String estado
    );

    @Query(
            "SELECT e FROM EntyEvitipmatipoevidencia e " +
                    "WHERE :id IS NULL OR e.eviPrimarykeyTiev = :id " +
                    "ORDER BY e.eviPrimarykeyTiev DESC"
    )
    Page<EntyEvitipmatipoevidencia> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyEvitipmatipoevidencia e " +
                    "WHERE LOWER(e.eviIdentifkeyTiev) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.eviPrimarykeyTiev DESC"
    )
    Page<EntyEvitipmatipoevidencia> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyEvitipmatipoevidencia e " +
                    "WHERE LOWER(e.eviEstadoregTiev) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.eviPrimarykeyTiev DESC"
    )
    Page<EntyEvitipmatipoevidencia> searchByStatus(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyEvitipmatipoevidencia e " +
                    "WHERE LOWER(e.eviIdentifkeyTiev) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(e.eviDescripcionTiev) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(e.eviEstadoregTiev) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.eviPrimarykeyTiev DESC"
    )
    Page<EntyEvitipmatipoevidencia> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}