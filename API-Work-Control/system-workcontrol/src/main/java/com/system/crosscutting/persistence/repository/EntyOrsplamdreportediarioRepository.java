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

    Optional<EntyOrsplamdreportediario> findByOrsIdentifkeyPdia(
            String reporteKey
    );

    boolean existsByOrsIdentifkeyPdia(
            String reporteKey
    );

    List<EntyOrsplamdreportediario>
    findByOrsIdentifkeyOrdeOrderByOrsFechareportPdiaAsc(
            String ordenKey
    );

    List<EntyOrsplamdreportediario>
    findByOrsIdentifkeyPlseOrderByOrsFechareportPdiaAsc(
            String planSemanaKey
    );

    List<EntyOrsplamdreportediario>
    findByOrsIdentifkeyPsemOrderByOrsFechareportPdiaAsc(
            String semanaKey
    );

    @Query(
            "SELECT COALESCE(SUM(e.orsEjecutunidadPdia), 0) " +
                    "FROM EntyOrsplamdreportediario e " +
                    "WHERE e.orsIdentifkeyPlse = :planSemanaKey " +
                    "AND e.orsEstadoregPdia <> '3'"
    )

    default Integer sumEjecutadoByPlanSemana(
            final String planSemanaKey
    ) {
        Long total = sumEjecutadoValidoByPlanSemana(planSemanaKey);

        return total != null ? total.intValue() : 0;
    }

    @Query(
            "SELECT COALESCE(SUM(e.orsEjecutunidadPdia), 0) " +
                    "FROM EntyOrsplamdreportediario e " +
                    "WHERE e.orsIdentifkeyPlse = :planSemanaKey " +
                    "AND e.orsEstadoregPdia <> '3'"
    )
    Long sumEjecutadoValidoByPlanSemana(
            @Param("planSemanaKey") String planSemanaKey
    );

    @Query(
            "SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
                    "FROM EntyOrsplamdreportediario e " +
                    "WHERE e.orsIdentifkeyPlse = :planSemanaKey " +
                    "AND e.orsFechareportPdia = :fecha " +
                    "AND e.orsEstadoregPdia <> '3'"
    )
    boolean existsReporteValidoByPlanSemanaAndFecha(
            @Param("planSemanaKey") String planSemanaKey,
            @Param("fecha") LocalDate fecha
    );

    @Query(
            "SELECT e FROM EntyOrsplamdreportediario e " +
                    "WHERE (:id IS NULL OR e.orsPrimarykeyPdia = :id) " +
                    "ORDER BY e.orsFechareportPdia DESC"
    )
    Page<EntyOrsplamdreportediario> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamdreportediario e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyPdia, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsFechareportPdia DESC"
    )
    Page<EntyOrsplamdreportediario> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamdreportediario e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyOrde, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsFechareportPdia DESC"
    )
    Page<EntyOrsplamdreportediario> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamdreportediario e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyPlse, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsFechareportPdia DESC"
    )
    Page<EntyOrsplamdreportediario> searchByPlanSemana(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamdreportediario e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyPsem, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsFechareportPdia DESC"
    )
    Page<EntyOrsplamdreportediario> searchBySemana(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamdreportediario e " +
                    "WHERE LOWER(COALESCE(e.orsEstadoregPdia, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsFechareportPdia DESC"
    )
    Page<EntyOrsplamdreportediario> searchByStatus(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamdreportediario e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyPdia, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsIdentifkeyOrde, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsIdentifkeyPlse, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsIdentifkeyPsem, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsObservacionPdia, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsTiporegistPdia, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsEstadoregPdia, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsFechareportPdia DESC"
    )
    Page<EntyOrsplamdreportediario> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}