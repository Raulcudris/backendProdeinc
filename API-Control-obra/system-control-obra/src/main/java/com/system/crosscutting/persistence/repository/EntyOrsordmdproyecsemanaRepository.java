package com.system.crosscutting.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsordmdproyecsemana;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsordmdproyecsemanaRepository
        extends JpaRepository<EntyOrsordmdproyecsemana, Integer> {

    Optional<EntyOrsordmdproyecsemana> findByOrsIdentifkeyPsem(String orsIdentifkeyPsem);

    List<EntyOrsordmdproyecsemana> findByOrsIdentifkeyOrde(String orsIdentifkeyOrde);

    @Query("SELECT p FROM EntyOrsordmdproyecsemana p " +
            "WHERE p.orsPrimarykeyPsem = :id")
    Page<EntyOrsordmdproyecsemana> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsordmdproyecsemana p " +
            "WHERE LOWER(p.orsIdentifkeyPsem) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdproyecsemana> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsordmdproyecsemana p " +
            "WHERE LOWER(p.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdproyecsemana> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsordmdproyecsemana p " +
            "WHERE p.orsNumerosemPsem = :numeroSemana")
    Page<EntyOrsordmdproyecsemana> searchByNumeroSemana(
            @Param("numeroSemana") Integer numeroSemana,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsordmdproyecsemana p " +
            "WHERE p.orsSemfechiniPsem >= :fechaInicio " +
            "AND p.orsSemfechfinPsem <= :fechaFin")
    Page<EntyOrsordmdproyecsemana> searchByRangoSemana(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsordmdproyecsemana p " +
            "WHERE p.orsEstadoregPsem = :status")
    Page<EntyOrsordmdproyecsemana> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsordmdproyecsemana p " +
            "WHERE LOWER(p.orsIdentifkeyPsem) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.orsTitulosemPsem) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.orsDiashabilesPsem) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.orsDiasnhabilesPsem) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdproyecsemana> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}