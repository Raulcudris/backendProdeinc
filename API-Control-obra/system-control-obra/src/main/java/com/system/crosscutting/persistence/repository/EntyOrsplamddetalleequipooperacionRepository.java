package com.system.crosscutting.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsplamddetalleequipooperacion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsplamddetalleequipooperacionRepository
        extends JpaRepository<EntyOrsplamddetalleequipooperacion, Integer> {

    Optional<EntyOrsplamddetalleequipooperacion> findByOrsIdentifkeyDeop(String orsIdentifkeyDeop);

    List<EntyOrsplamddetalleequipooperacion> findByOrsIdentifkeyRope(String orsIdentifkeyRope);

    List<EntyOrsplamddetalleequipooperacion> findByOrsIdentifkeyOrde(String orsIdentifkeyOrde);

    List<EntyOrsplamddetalleequipooperacion> findByOrsIdentifkeyPsem(String orsIdentifkeyPsem);

    List<EntyOrsplamddetalleequipooperacion> findByOrsIdentifkeyPlse(String orsIdentifkeyPlse);

    List<EntyOrsplamddetalleequipooperacion> findByOrsIdentifkeyPunt(String orsIdentifkeyPunt);

    List<EntyOrsplamddetalleequipooperacion> findByPrvIdentifkeyInve(String prvIdentifkeyInve);

    List<EntyOrsplamddetalleequipooperacion> findByPrvTipoequipoTieq(String prvTipoequipoTieq);

    List<EntyOrsplamddetalleequipooperacion> findByOrsFechatrabajoDeop(LocalDate orsFechatrabajoDeop);

    List<EntyOrsplamddetalleequipooperacion> findByOrsEstadoregDeop(String orsEstadoregDeop);

    @Query("SELECT d FROM EntyOrsplamddetalleequipooperacion d " +
            "WHERE LOWER(d.orsIdentifkeyDeop) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamddetalleequipooperacion> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyOrsplamddetalleequipooperacion d " +
            "WHERE LOWER(d.orsIdentifkeyRope) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamddetalleequipooperacion> searchByReporteOperacion(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyOrsplamddetalleequipooperacion d " +
            "WHERE LOWER(d.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamddetalleequipooperacion> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyOrsplamddetalleequipooperacion d " +
            "WHERE LOWER(d.orsIdentifkeyPlse) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamddetalleequipooperacion> searchByPlanSemanal(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyOrsplamddetalleequipooperacion d " +
            "WHERE LOWER(d.prvIdentifkeyInve) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamddetalleequipooperacion> searchByEquipo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyOrsplamddetalleequipooperacion d " +
            "WHERE LOWER(d.prvTipoequipoTieq) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamddetalleequipooperacion> searchByTipoEquipo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyOrsplamddetalleequipooperacion d " +
            "WHERE d.orsFechatrabajoDeop BETWEEN :fechaInicio AND :fechaFin")
    Page<EntyOrsplamddetalleequipooperacion> searchByFechaTrabajoBetween(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyOrsplamddetalleequipooperacion d " +
            "WHERE d.orsEstadoregDeop = :status")
    Page<EntyOrsplamddetalleequipooperacion> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT COALESCE(SUM(d.orsHorastrabajadasDeop), 0) " +
            "FROM EntyOrsplamddetalleequipooperacion d " +
            "WHERE d.orsIdentifkeyPlse = :planSemanalKey " +
            "AND d.orsEstadoregDeop = '1'")
    java.math.BigDecimal sumHorasByPlanSemanal(
            @Param("planSemanalKey") String planSemanalKey
    );

    @Query("SELECT COALESCE(SUM(d.orsValorejecutadoDeop), 0) " +
            "FROM EntyOrsplamddetalleequipooperacion d " +
            "WHERE d.orsIdentifkeyPlse = :planSemanalKey " +
            "AND d.orsEstadoregDeop = '1'")
    java.math.BigDecimal sumValorEjecutadoByPlanSemanal(
            @Param("planSemanalKey") String planSemanalKey
    );

    @Query("SELECT COALESCE(SUM(d.orsHorastrabajadasDeop), 0) " +
            "FROM EntyOrsplamddetalleequipooperacion d " +
            "WHERE d.orsIdentifkeyOrde = :ordenKey " +
            "AND d.orsEstadoregDeop = '1'")
    java.math.BigDecimal sumHorasByOrden(
            @Param("ordenKey") String ordenKey
    );

    @Query("SELECT COALESCE(SUM(d.orsValorejecutadoDeop), 0) " +
            "FROM EntyOrsplamddetalleequipooperacion d " +
            "WHERE d.orsIdentifkeyOrde = :ordenKey " +
            "AND d.orsEstadoregDeop = '1'")
    java.math.BigDecimal sumValorEjecutadoByOrden(
            @Param("ordenKey") String ordenKey
    );

    @Query("SELECT d FROM EntyOrsplamddetalleequipooperacion d " +
            "WHERE LOWER(d.orsIdentifkeyDeop) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.orsIdentifkeyRope) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.orsIdentifkeyPlse) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.prvIdentifkeyInve) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.prvTipoequipoTieq) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.orsNombrequipoDeop) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.orsRefermodeloDeop) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.orsNroregistroDeop) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.orsObservacionDeop) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamddetalleequipooperacion> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}