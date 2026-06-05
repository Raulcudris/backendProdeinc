package com.system.crosscutting.persistence.repository;

import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrspspmdplansemanal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrspspmdplansemanalRepository
        extends JpaRepository<EntyOrspspmdplansemanal, Integer> {

    Optional<EntyOrspspmdplansemanal> findByOrsIdentifkeyPspl(String orsIdentifkeyPspl);

    @Query("SELECT s FROM EntyOrspspmdplansemanal s WHERE s.orsPrimarykeyPspl = :id")
    Page<EntyOrspspmdplansemanal> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT s FROM EntyOrspspmdplansemanal s " +
            "WHERE LOWER(s.orsIdentifkeyPspl) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrspspmdplansemanal> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT s FROM EntyOrspspmdplansemanal s " +
            "WHERE LOWER(s.orsIdentifkeyPltr) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrspspmdplansemanal> searchByPlan(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT s FROM EntyOrspspmdplansemanal s " +
            "WHERE s.orsSemanaPspl = :semana")
    Page<EntyOrspspmdplansemanal> searchBySemana(
            @Param("semana") Integer semana,
            Pageable pageable
    );

    @Query("SELECT s FROM EntyOrspspmdplansemanal s WHERE s.orsEstadoregPspl = :status")
    Page<EntyOrspspmdplansemanal> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT s FROM EntyOrspspmdplansemanal s " +
            "WHERE LOWER(s.orsIdentifkeyPspl) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(s.orsIdentifkeyPltr) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(s.orsObservacionPspl) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrspspmdplansemanal> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}