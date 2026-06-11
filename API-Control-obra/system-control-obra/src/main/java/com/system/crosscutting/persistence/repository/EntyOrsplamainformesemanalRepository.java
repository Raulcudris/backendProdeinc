package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsplamainformesemanal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsplamainformesemanalRepository
        extends JpaRepository<EntyOrsplamainformesemanal, Integer> {

    Optional<EntyOrsplamainformesemanal> findByOrsIdentifkeyInse(String orsIdentifkeyInse);

    List<EntyOrsplamainformesemanal> findByOrsIdentifkeyOrde(String orsIdentifkeyOrde);

    List<EntyOrsplamainformesemanal> findByOrsIdentifkeyPsem(String orsIdentifkeyPsem);

    List<EntyOrsplamainformesemanal> findByOrsSemanaInse(Integer orsSemanaInse);

    List<EntyOrsplamainformesemanal> findByOrsEstadoregInse(String orsEstadoregInse);

    @Query("SELECT i FROM EntyOrsplamainformesemanal i " +
            "WHERE LOWER(i.orsIdentifkeyInse) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamainformesemanal> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT i FROM EntyOrsplamainformesemanal i " +
            "WHERE LOWER(i.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamainformesemanal> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT i FROM EntyOrsplamainformesemanal i " +
            "WHERE LOWER(i.orsIdentifkeyPsem) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamainformesemanal> searchByProyeccionSemana(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT i FROM EntyOrsplamainformesemanal i " +
            "WHERE i.orsSemanaInse = :semana")
    Page<EntyOrsplamainformesemanal> searchBySemana(
            @Param("semana") Integer semana,
            Pageable pageable
    );

    @Query("SELECT i FROM EntyOrsplamainformesemanal i " +
            "WHERE i.orsEstadoavanceInse = :estadoAvance")
    Page<EntyOrsplamainformesemanal> searchByEstadoAvance(
            @Param("estadoAvance") String estadoAvance,
            Pageable pageable
    );

    @Query("SELECT i FROM EntyOrsplamainformesemanal i " +
            "WHERE i.orsEstadoregInse = :status")
    Page<EntyOrsplamainformesemanal> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT i FROM EntyOrsplamainformesemanal i " +
            "WHERE LOWER(i.orsIdentifkeyInse) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(i.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(i.orsIdentifkeyPsem) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(i.orsEstadoavanceInse) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(i.orsObservacionInse) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamainformesemanal> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}