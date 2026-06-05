package com.system.crosscutting.persistence.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsrdomdreporteDiario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsrdomdreporteDiarioRepository
        extends JpaRepository<EntyOrsrdomdreporteDiario, Integer> {

    Optional<EntyOrsrdomdreporteDiario> findByOrsIdentifkeyRedi(String orsIdentifkeyRedi);

    @Query("SELECT r FROM EntyOrsrdomdreporteDiario r WHERE r.orsPrimarykeyRedi = :id")
    Page<EntyOrsrdomdreporteDiario> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsrdomdreporteDiario r " +
            "WHERE LOWER(r.orsIdentifkeyRedi) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsrdomdreporteDiario> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsrdomdreporteDiario r " +
            "WHERE LOWER(r.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsrdomdreporteDiario> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsrdomdreporteDiario r " +
            "WHERE LOWER(r.orsIdentifkeyPltr) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsrdomdreporteDiario> searchByPlan(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsrdomdreporteDiario r " +
            "WHERE LOWER(r.orsIdentifkeyPspl) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsrdomdreporteDiario> searchByPlanSemanal(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsrdomdreporteDiario r " +
            "WHERE r.orsFechareporteRedi = :fecha")
    Page<EntyOrsrdomdreporteDiario> searchByFecha(
            @Param("fecha") LocalDate fecha,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsrdomdreporteDiario r " +
            "WHERE r.orsFechareporteRedi BETWEEN :fechaInicio AND :fechaFin")
    Page<EntyOrsrdomdreporteDiario> searchByFechaBetween(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsrdomdreporteDiario r WHERE r.orsEstadoregRedi = :status")
    Page<EntyOrsrdomdreporteDiario> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsrdomdreporteDiario r " +
            "WHERE LOWER(r.orsIdentifkeyRedi) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsIdentifkeyPltr) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsIdentifkeyPspl) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsActividadRedi) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsResponsableRedi) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsObservacionRedi) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsrdomdreporteDiario> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT COALESCE(SUM(r.orsCantidadejecRedi), 0) " +
            "FROM EntyOrsrdomdreporteDiario r " +
            "WHERE r.orsIdentifkeyPltr = :planKey " +
            "AND r.orsEstadoregRedi = '1'")
    BigDecimal sumEjecutadoByPlan(
            @Param("planKey") String planKey
    );

    @Query("SELECT COALESCE(SUM(r.orsCantidadejecRedi), 0) " +
            "FROM EntyOrsrdomdreporteDiario r " +
            "WHERE r.orsIdentifkeyPspl = :planSemanalKey " +
            "AND r.orsEstadoregRedi = '1'")
    BigDecimal sumEjecutadoByPlanSemanal(
            @Param("planSemanalKey") String planSemanalKey
    );

    @Query("SELECT COALESCE(SUM(r.orsCantidadejecRedi), 0) " +
            "FROM EntyOrsrdomdreporteDiario r " +
            "WHERE r.orsIdentifkeyOrde = :ordenKey " +
            "AND r.orsEstadoregRedi = '1'")
    BigDecimal sumEjecutadoByOrden(
            @Param("ordenKey") String ordenKey
    );
}