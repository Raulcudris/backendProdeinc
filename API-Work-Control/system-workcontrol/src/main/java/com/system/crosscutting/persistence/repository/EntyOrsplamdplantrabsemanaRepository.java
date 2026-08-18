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

    Optional<EntyOrsplamdplantrabsemana> findByOrsIdentifkeyPlse(
            String planSemanaKey
    );

    boolean existsByOrsIdentifkeyPlse(
            String planSemanaKey
    );

    boolean existsByOrsIdentifkeyOrdeAndOrsEstadoregPlse(
            String ordenKey,
            String estado
    );

    List<EntyOrsplamdplantrabsemana> findByOrsIdentifkeyPltr(
            String planTrabajoKey
    );

    List<EntyOrsplamdplantrabsemana> findByOrsIdentifkeyPsem(
            String semanaKey
    );

    List<EntyOrsplamdplantrabsemana> findByOrsIdentifkeyOrde(
            String ordenKey
    );

    List<EntyOrsplamdplantrabsemana>
    findByOrsIdentifkeyOrdeOrderByOrsPrimarykeyPlseAsc(
            String ordenKey
    );

    List<EntyOrsplamdplantrabsemana>
    findByOrsIdentifkeyPltrOrderByOrsPrimarykeyPlseAsc(
            String planTrabajoKey
    );

    List<EntyOrsplamdplantrabsemana>
    findByOrsIdentifkeyPsemOrderByOrsPrimarykeyPlseAsc(
            String semanaKey
    );

    @Query(
            "SELECT e FROM EntyOrsplamdplantrabsemana e " +
                    "WHERE (:id IS NULL OR e.orsPrimarykeyPlse = :id) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPlse ASC"
    )
    Page<EntyOrsplamdplantrabsemana> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamdplantrabsemana e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyPlse, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPlse ASC"
    )
    Page<EntyOrsplamdplantrabsemana> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamdplantrabsemana e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyOrde, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPlse ASC"
    )
    Page<EntyOrsplamdplantrabsemana> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamdplantrabsemana e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyPltr, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPlse ASC"
    )
    Page<EntyOrsplamdplantrabsemana> searchByPlanTrabajo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamdplantrabsemana e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyPsem, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPlse ASC"
    )
    Page<EntyOrsplamdplantrabsemana> searchBySemana(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamdplantrabsemana e " +
                    "WHERE LOWER(COALESCE(e.orsEstadoregPlse, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPlse ASC"
    )
    Page<EntyOrsplamdplantrabsemana> searchByStatus(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamdplantrabsemana e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyPlse, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsIdentifkeyOrde, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsIdentifkeyPltr, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsIdentifkeyPsem, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsTiporegistPlse, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsEstadoregPlse, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPlse ASC"
    )
    Page<EntyOrsplamdplantrabsemana> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}