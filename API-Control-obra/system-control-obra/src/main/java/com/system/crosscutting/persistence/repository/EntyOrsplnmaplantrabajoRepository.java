package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsplnmaplantrabajo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsplnmaplantrabajoRepository
        extends JpaRepository<EntyOrsplnmaplantrabajo, Integer> {

    Optional<EntyOrsplnmaplantrabajo> findByOrsIdentifkeyPltr(String orsIdentifkeyPltr);

    @Query("SELECT p FROM EntyOrsplnmaplantrabajo p WHERE p.orsPrimarykeyPltr = :id")
    Page<EntyOrsplnmaplantrabajo> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplnmaplantrabajo p " +
            "WHERE LOWER(p.orsIdentifkeyPltr) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplnmaplantrabajo> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplnmaplantrabajo p " +
            "WHERE LOWER(p.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplnmaplantrabajo> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplnmaplantrabajo p " +
            "WHERE LOWER(p.orsIdentifkeySitr) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplnmaplantrabajo> searchBySitio(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplnmaplantrabajo p WHERE p.orsEstadoregPltr = :status")
    Page<EntyOrsplnmaplantrabajo> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplnmaplantrabajo p " +
            "WHERE LOWER(p.orsIdentifkeyPltr) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.orsIdentifkeySitr) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.orsActividadPltr) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.orsDescripcionPltr) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.orsUnidadmedidaPltr) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplnmaplantrabajo> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplnmaplantrabajo p " +
            "WHERE p.orsIdentifkeyOrde = :ordenKey " +
            "AND p.orsEstadoregPltr = '1'")
    List<EntyOrsplnmaplantrabajo> findActiveByOrden(
            @Param("ordenKey") String ordenKey
    );
}