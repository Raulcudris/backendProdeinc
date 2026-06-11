package com.system.crosscutting.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsplamdreporteoperacion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsplamdreporteoperacionRepository
        extends JpaRepository<EntyOrsplamdreporteoperacion, Integer> {

    Optional<EntyOrsplamdreporteoperacion> findByOrsIdentifkeyRope(String orsIdentifkeyRope);

    List<EntyOrsplamdreporteoperacion> findByOrsIdentifkeyOrde(String orsIdentifkeyOrde);

    List<EntyOrsplamdreporteoperacion> findByOrsIdentifkeyPsem(String orsIdentifkeyPsem);

    List<EntyOrsplamdreporteoperacion> findByOrsIdentifkeyPlse(String orsIdentifkeyPlse);

    List<EntyOrsplamdreporteoperacion> findByOrsIdentifkeyPunt(String orsIdentifkeyPunt);

    List<EntyOrsplamdreporteoperacion> findByPrvIdentifkeyMprv(String prvIdentifkeyMprv);

    List<EntyOrsplamdreporteoperacion> findByOrsEstadoregRope(String orsEstadoregRope);

    List<EntyOrsplamdreporteoperacion> findByOrsFechareportRope(LocalDate orsFechareportRope);

    @Query("SELECT r FROM EntyOrsplamdreporteoperacion r " +
            "WHERE LOWER(r.orsIdentifkeyRope) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamdreporteoperacion> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsplamdreporteoperacion r " +
            "WHERE LOWER(r.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamdreporteoperacion> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsplamdreporteoperacion r " +
            "WHERE LOWER(r.orsIdentifkeyPsem) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamdreporteoperacion> searchByProyeccionSemana(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsplamdreporteoperacion r " +
            "WHERE LOWER(r.orsIdentifkeyPlse) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamdreporteoperacion> searchByPlanSemanal(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsplamdreporteoperacion r " +
            "WHERE LOWER(r.orsIdentifkeyPunt) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamdreporteoperacion> searchByPunto(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsplamdreporteoperacion r " +
            "WHERE r.orsFechareportRope BETWEEN :fechaInicio AND :fechaFin")
    Page<EntyOrsplamdreporteoperacion> searchByFechaReporteBetween(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsplamdreporteoperacion r " +
            "WHERE r.orsEstadoregRope = :status")
    Page<EntyOrsplamdreporteoperacion> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsplamdreporteoperacion r " +
            "WHERE LOWER(r.orsIdentifkeyRope) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsIdentifkeyPsem) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsIdentifkeyPlse) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsIdentifkeyPunt) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsMunicipioRope) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsSitioRope) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsDescsuministroRope) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsObservacionRope) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamdreporteoperacion> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}