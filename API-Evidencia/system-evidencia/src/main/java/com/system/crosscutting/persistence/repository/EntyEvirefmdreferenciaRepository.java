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

    Optional<EntyEvirefmdreferencia> findByEviIdentifkeyRefe(String eviIdentifkeyRefe);

    List<EntyEvirefmdreferencia> findByEviIdentifkeyEvid(String eviIdentifkeyEvid);

    List<EntyEvirefmdreferencia> findByEviIdentifregistroRefe(String eviIdentifregistroRefe);

    List<EntyEvirefmdreferencia> findByEviTiporegistroRefe(String eviTiporegistroRefe);

    List<EntyEvirefmdreferencia> findByEviTiporegistroRefeAndEviIdentifregistroRefe(
            String eviTiporegistroRefe,
            String eviIdentifregistroRefe
    );

    @Query("SELECT r FROM EntyEvirefmdreferencia r WHERE r.eviPrimarykeyRefe = :id")
    Page<EntyEvirefmdreferencia> searchByPrimaryKey(@Param("id") Integer id, Pageable pageable);

    @Query("SELECT r FROM EntyEvirefmdreferencia r WHERE LOWER(r.eviIdentifkeyRefe) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvirefmdreferencia> searchByIdentifKey(@Param("filter") String filter, Pageable pageable);

    @Query("SELECT r FROM EntyEvirefmdreferencia r WHERE LOWER(r.eviIdentifkeyEvid) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvirefmdreferencia> searchByEvidencia(@Param("filter") String filter, Pageable pageable);

    @Query("SELECT r FROM EntyEvirefmdreferencia r WHERE LOWER(r.eviIdentifregistroRefe) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvirefmdreferencia> searchByRegistro(@Param("filter") String filter, Pageable pageable);

    @Query("SELECT r FROM EntyEvirefmdreferencia r WHERE LOWER(r.eviTiporegistroRefe) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvirefmdreferencia> searchByTipoRegistro(@Param("filter") String filter, Pageable pageable);

    @Query("SELECT r FROM EntyEvirefmdreferencia r WHERE r.eviEstadoregRefe = :status")
    Page<EntyEvirefmdreferencia> searchByStatus(@Param("status") String status, Pageable pageable);

    @Query("SELECT r FROM EntyEvirefmdreferencia r " +
            "WHERE LOWER(r.eviIdentifkeyRefe) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.eviIdentifkeyEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.eviTiporegistroRefe) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.eviIdentifregistroRefe) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvirefmdreferencia> searchByText(@Param("filter") String filter, Pageable pageable);
}