package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyEvirefmdreferencia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio JPA para consultar y administrar referencias de evidencias.
 */
public interface EntyEvirefmdreferenciaRepository
        extends JpaRepository<EntyEvirefmdreferencia, Integer> {

    Optional<EntyEvirefmdreferencia> findByEviIdentifkeyEvre(String eviIdentifkeyEvre);

    List<EntyEvirefmdreferencia> findByEviIdentifkeyEvid(String eviIdentifkeyEvid);

    List<EntyEvirefmdreferencia> findByEviTiporeferenEvre(String eviTiporeferenEvre);

    List<EntyEvirefmdreferencia> findByEviTiporeferenEvreAndEviReferenciaidEvre(
            String eviTiporeferenEvre,
            String eviReferenciaidEvre
    );

    List<EntyEvirefmdreferencia> findByEviEstadoregEvre(String eviEstadoregEvre);

    @Query("SELECT r FROM EntyEvirefmdreferencia r " +
            "WHERE r.eviPrimarykeyEvre = :id")
    Page<EntyEvirefmdreferencia> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyEvirefmdreferencia r " +
            "WHERE LOWER(r.eviIdentifkeyEvre) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvirefmdreferencia> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyEvirefmdreferencia r " +
            "WHERE LOWER(r.eviIdentifkeyEvid) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvirefmdreferencia> searchByEvidencia(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyEvirefmdreferencia r " +
            "WHERE LOWER(r.eviTiporeferenEvre) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvirefmdreferencia> searchByTipoReferencia(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyEvirefmdreferencia r " +
            "WHERE LOWER(r.eviReferenciaidEvre) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvirefmdreferencia> searchByReferenciaId(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyEvirefmdreferencia r " +
            "WHERE LOWER(r.eviTiporeferenEvre) LIKE LOWER(CONCAT('%', :tipoReferencia, '%')) " +
            "AND LOWER(r.eviReferenciaidEvre) LIKE LOWER(CONCAT('%', :referenciaId, '%'))")
    Page<EntyEvirefmdreferencia> searchByReferencia(
            @Param("tipoReferencia") String tipoReferencia,
            @Param("referenciaId") String referenciaId,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyEvirefmdreferencia r " +
            "WHERE r.eviEstadoregEvre = :status")
    Page<EntyEvirefmdreferencia> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyEvirefmdreferencia r " +
            "WHERE LOWER(r.eviIdentifkeyEvre) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.eviIdentifkeyEvid) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.eviTiporeferenEvre) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.eviReferenciaidEvre) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.eviObservacionEvre) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEvirefmdreferencia> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}