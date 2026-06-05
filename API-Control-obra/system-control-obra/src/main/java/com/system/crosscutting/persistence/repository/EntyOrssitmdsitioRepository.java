package com.system.crosscutting.persistence.repository;

import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrssitmdsitio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrssitmdsitioRepository
        extends JpaRepository<EntyOrssitmdsitio, Integer> {

    Optional<EntyOrssitmdsitio> findByOrsIdentifkeySitr(String orsIdentifkeySitr);

    @Query("SELECT s FROM EntyOrssitmdsitio s " +
            "WHERE s.orsPrimarykeySitr = :id")
    Page<EntyOrssitmdsitio> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT s FROM EntyOrssitmdsitio s " +
            "WHERE LOWER(s.orsIdentifkeySitr) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrssitmdsitio> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT s FROM EntyOrssitmdsitio s " +
            "WHERE LOWER(s.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrssitmdsitio> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT s FROM EntyOrssitmdsitio s " +
            "WHERE s.orsEstadoregSitr = :status")
    Page<EntyOrssitmdsitio> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT s FROM EntyOrssitmdsitio s " +
            "WHERE LOWER(s.orsIdentifkeySitr) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(s.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(s.orsNombreSitr) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(s.orsDescripcionSitr) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(s.orsUbicacionSitr) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrssitmdsitio> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}