package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsplamdplantrabsemana;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsplamdplantrabsemanaRepository
        extends JpaRepository<EntyOrsplamdplantrabsemana, Integer> {

    Optional<EntyOrsplamdplantrabsemana> findByOrsIdentifkeyPlse(String orsIdentifkeyPlse);

    List<EntyOrsplamdplantrabsemana> findByOrsIdentifkeyOrde(String orsIdentifkeyOrde);

    List<EntyOrsplamdplantrabsemana> findByOrsIdentifkeyPltr(String orsIdentifkeyPltr);

    List<EntyOrsplamdplantrabsemana> findByOrsIdentifkeyPsem(String orsIdentifkeyPsem);

    @Query("SELECT p FROM EntyOrsplamdplantrabsemana p " +
            "WHERE p.orsPrimarykeyPlse = :id")
    Page<EntyOrsplamdplantrabsemana> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplamdplantrabsemana p " +
            "WHERE LOWER(p.orsIdentifkeyPlse) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamdplantrabsemana> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplamdplantrabsemana p " +
            "WHERE LOWER(p.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamdplantrabsemana> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplamdplantrabsemana p " +
            "WHERE LOWER(p.orsIdentifkeyPltr) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamdplantrabsemana> searchByPlanTrabajo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplamdplantrabsemana p " +
            "WHERE LOWER(p.orsIdentifkeyPsem) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamdplantrabsemana> searchByProyeccionSemana(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplamdplantrabsemana p " +
            "WHERE p.orsEstadoregPlse = :status")
    Page<EntyOrsplamdplantrabsemana> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplamdplantrabsemana p " +
            "WHERE LOWER(p.orsIdentifkeyPlse) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.orsIdentifkeyPltr) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.orsIdentifkeyPsem) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamdplantrabsemana> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}