package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsconfnovedadtipos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsconfnovedadtiposRepository
        extends JpaRepository<EntyOrsconfnovedadtipos, Integer> {

    Optional<EntyOrsconfnovedadtipos> findByOrsTiponovedadNovt(
            String tipoNovedad
    );

    boolean existsByOrsTiponovedadNovt(
            String tipoNovedad
    );

    List<EntyOrsconfnovedadtipos> findByOrsEstadoregNovt(
            String estado
    );

    @Query(
            "SELECT e FROM EntyOrsconfnovedadtipos e " +
                    "WHERE (:id IS NULL OR e.orsPrimarykeyNovt = :id) " +
                    "ORDER BY e.orsPrimarykeyNovt DESC"
    )
    Page<EntyOrsconfnovedadtipos> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsconfnovedadtipos e " +
                    "WHERE LOWER(COALESCE(e.orsTiponovedadNovt, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsPrimarykeyNovt DESC"
    )
    Page<EntyOrsconfnovedadtipos> searchByTipo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsconfnovedadtipos e " +
                    "WHERE LOWER(COALESCE(e.orsEstadoregNovt, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsPrimarykeyNovt DESC"
    )
    Page<EntyOrsconfnovedadtipos> searchByStatus(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsconfnovedadtipos e " +
                    "WHERE (" +
                    "LOWER(COALESCE(e.orsTiponovedadNovt, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsDescnovedadNovt, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsEstadoregNovt, '')) LIKE LOWER(CONCAT('%', :filter, '%'))" +
                    ") " +
                    "ORDER BY e.orsPrimarykeyNovt DESC"
    )
    Page<EntyOrsconfnovedadtipos> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}