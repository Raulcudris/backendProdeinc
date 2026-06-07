package com.system.crosscutting.persistence.repository;

import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyEvitipmatipoevidencia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyEvitipmatipoevidenciaRepository
        extends JpaRepository<EntyEvitipmatipoevidencia, Integer> {

    Optional<EntyEvitipmatipoevidencia> findByEviIdentifkeyTiev(String eviIdentifkeyTiev);

    @Query("SELECT t FROM EntyEvitipmatipoevidencia t WHERE t.eviPrimarykeyTiev = :id")
    Page<EntyEvitipmatipoevidencia> searchByPrimaryKey(@Param("id") Integer id, Pageable pageable);

    @Query("SELECT t FROM EntyEvitipmatipoevidencia t WHERE LOWER(t.eviIdentifkeyTiev) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvitipmatipoevidencia> searchByIdentifKey(@Param("filter") String filter, Pageable pageable);

    @Query("SELECT t FROM EntyEvitipmatipoevidencia t WHERE t.eviEstadoregTiev = :status")
    Page<EntyEvitipmatipoevidencia> searchByStatus(@Param("status") String status, Pageable pageable);

    @Query("SELECT t FROM EntyEvitipmatipoevidencia t " +
            "WHERE LOWER(t.eviIdentifkeyTiev) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(t.eviDescripcionTiev) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvitipmatipoevidencia> searchByText(@Param("filter") String filter, Pageable pageable);
}