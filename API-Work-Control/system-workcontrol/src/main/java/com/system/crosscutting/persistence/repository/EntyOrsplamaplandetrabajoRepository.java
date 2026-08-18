package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsplamaplandetrabajo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsplamaplandetrabajoRepository
        extends JpaRepository<EntyOrsplamaplandetrabajo, Integer> {

    Optional<EntyOrsplamaplandetrabajo> findByOrsIdentifkeyPltr(
            String planKey
    );

    boolean existsByOrsIdentifkeyPltr(
            String planKey
    );

    boolean existsByOrsIdentifkeyOrdeAndOrsEstadoregPltr(
            String ordenKey,
            String estado
    );

    List<EntyOrsplamaplandetrabajo>
    findByOrsIdentifkeyOrdeOrderByOrsPrimarykeyPltrAsc(
            String ordenKey
    );

    List<EntyOrsplamaplandetrabajo>
    findByOrsIdentifkeyPuntOrderByOrsPrimarykeyPltrAsc(
            String puntoKey
    );

    @Query(
            "SELECT e FROM EntyOrsplamaplandetrabajo e " +
                    "WHERE (:id IS NULL OR e.orsPrimarykeyPltr = :id) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPltr ASC"
    )
    Page<EntyOrsplamaplandetrabajo> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamaplandetrabajo e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyPltr, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPltr ASC"
    )
    Page<EntyOrsplamaplandetrabajo> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamaplandetrabajo e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyOrde, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPltr ASC"
    )
    Page<EntyOrsplamaplandetrabajo> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamaplandetrabajo e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyPunt, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPltr ASC"
    )
    Page<EntyOrsplamaplandetrabajo> searchByPunto(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamaplandetrabajo e " +
                    "WHERE LOWER(COALESCE(e.orsEstadoregPltr, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPltr ASC"
    )
    Page<EntyOrsplamaplandetrabajo> searchByStatus(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsplamaplandetrabajo e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyPltr, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsIdentifkeyOrde, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsIdentifkeyPunt, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsDesactividadPltr, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsIdentifkeyRseq, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.prvIdentifkeyInve, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsTiporegistPltr, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsEstadoregPltr, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPltr ASC"
    )
    Page<EntyOrsplamaplandetrabajo> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}