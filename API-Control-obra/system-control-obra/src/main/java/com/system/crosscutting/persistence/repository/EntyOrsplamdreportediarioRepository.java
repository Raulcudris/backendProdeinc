package com.system.crosscutting.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsplamdreportediario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsplamdreportediarioRepository
        extends JpaRepository<EntyOrsplamdreportediario, Integer> {

    Optional<EntyOrsplamdreportediario> findByOrsIdentifkeyPdia(String orsIdentifkeyPdia);

    List<EntyOrsplamdreportediario> findByOrsIdentifkeyOrde(String orsIdentifkeyOrde);

    List<EntyOrsplamdreportediario> findByOrsIdentifkeyPlse(String orsIdentifkeyPlse);

    List<EntyOrsplamdreportediario> findByOrsIdentifkeyPsem(String orsIdentifkeyPsem);

    @Query("SELECT r FROM EntyOrsplamdreportediario r " +
            "WHERE r.orsPrimarykeyPdia = :id")
    Page<EntyOrsplamdreportediario> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsplamdreportediario r " +
            "WHERE LOWER(r.orsIdentifkeyPdia) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamdreportediario> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsplamdreportediario r " +
            "WHERE LOWER(r.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamdreportediario> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsplamdreportediario r " +
            "WHERE LOWER(r.orsIdentifkeyPlse) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamdreportediario> searchByPlanSemana(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsplamdreportediario r " +
            "WHERE LOWER(r.orsIdentifkeyPsem) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamdreportediario> searchByProyeccionSemana(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsplamdreportediario r " +
            "WHERE r.orsFechareportPdia BETWEEN :fechaInicio AND :fechaFin")
    Page<EntyOrsplamdreportediario> searchByFechaReporteBetween(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsplamdreportediario r " +
            "WHERE r.orsEstadoregPdia = :status")
    Page<EntyOrsplamdreportediario> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsplamdreportediario r " +
            "WHERE LOWER(r.orsIdentifkeyPdia) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsIdentifkeyPlse) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsIdentifkeyPsem) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsObservacionPdia) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamdreportediario> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT COALESCE(SUM(r.orsEjecutunidadPdia), 0) " +
            "FROM EntyOrsplamdreportediario r " +
            "WHERE r.orsIdentifkeyPlse = :plseKey " +
            "AND r.orsEstadoregPdia = '1'")
    Integer sumEjecutadoByPlanSemana(
            @Param("plseKey") String plseKey
    );
}