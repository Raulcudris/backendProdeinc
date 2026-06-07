package com.system.crosscutting.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsconfnovedadhistori;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsconfnovedadhistoriRepository
        extends JpaRepository<EntyOrsconfnovedadhistori, Integer> {

    Optional<EntyOrsconfnovedadhistori> findByOrsIdentifkeyNove(String orsIdentifkeyNove);

    List<EntyOrsconfnovedadhistori> findByOrsIdentifkeyOrde(String orsIdentifkeyOrde);

    List<EntyOrsconfnovedadhistori> findByOrsRegistrbaseNove(String orsRegistrbaseNove);

    @Query("SELECT n FROM EntyOrsconfnovedadhistori n " +
            "WHERE n.orsPrimarykeyNove = :id")
    Page<EntyOrsconfnovedadhistori> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT n FROM EntyOrsconfnovedadhistori n " +
            "WHERE LOWER(n.orsIdentifkeyNove) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsconfnovedadhistori> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT n FROM EntyOrsconfnovedadhistori n " +
            "WHERE LOWER(n.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsconfnovedadhistori> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT n FROM EntyOrsconfnovedadhistori n " +
            "WHERE LOWER(n.orsTiponovedadNovt) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsconfnovedadhistori> searchByTipoNovedad(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT n FROM EntyOrsconfnovedadhistori n " +
            "WHERE LOWER(n.orsRegistrbaseNove) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsconfnovedadhistori> searchByRegistroBase(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT n FROM EntyOrsconfnovedadhistori n " +
            "WHERE n.orsFechreportNove BETWEEN :fechaInicio AND :fechaFin")
    Page<EntyOrsconfnovedadhistori> searchByFechaReporteBetween(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin,
            Pageable pageable
    );

    @Query("SELECT n FROM EntyOrsconfnovedadhistori n " +
            "WHERE n.orsEstadoregNove = :status")
    Page<EntyOrsconfnovedadhistori> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT n FROM EntyOrsconfnovedadhistori n " +
            "WHERE LOWER(n.orsIdentifkeyNove) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(n.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(n.orsTiponovedadNovt) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(n.orsRegistrbaseNove) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(n.orsRegistrnoveNove) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsconfnovedadhistori> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}