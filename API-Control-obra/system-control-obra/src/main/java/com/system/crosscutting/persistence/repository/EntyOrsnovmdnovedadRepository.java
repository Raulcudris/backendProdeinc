package com.system.crosscutting.persistence.repository;

import java.time.LocalDate;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsnovmdnovedad;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsnovmdnovedadRepository
        extends JpaRepository<EntyOrsnovmdnovedad, Integer> {

    Optional<EntyOrsnovmdnovedad> findByOrsIdentifkeyNove(String orsIdentifkeyNove);

    @Query("SELECT n FROM EntyOrsnovmdnovedad n WHERE n.orsPrimarykeyNove = :id")
    Page<EntyOrsnovmdnovedad> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT n FROM EntyOrsnovmdnovedad n " +
            "WHERE LOWER(n.orsIdentifkeyNove) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsnovmdnovedad> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT n FROM EntyOrsnovmdnovedad n " +
            "WHERE LOWER(n.orsIdentifkeyRedi) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsnovmdnovedad> searchByReporte(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT n FROM EntyOrsnovmdnovedad n " +
            "WHERE LOWER(n.orsTiponovedadNove) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsnovmdnovedad> searchByTipoNovedad(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT n FROM EntyOrsnovmdnovedad n " +
            "WHERE LOWER(n.orsCriticidadNove) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsnovmdnovedad> searchByCriticidad(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT n FROM EntyOrsnovmdnovedad n " +
            "WHERE n.orsFechanovedadNove = :fecha")
    Page<EntyOrsnovmdnovedad> searchByFecha(
            @Param("fecha") LocalDate fecha,
            Pageable pageable
    );

    @Query("SELECT n FROM EntyOrsnovmdnovedad n " +
            "WHERE n.orsEstadoregNove = :status")
    Page<EntyOrsnovmdnovedad> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT n FROM EntyOrsnovmdnovedad n " +
            "WHERE LOWER(n.orsIdentifkeyNove) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(n.orsIdentifkeyRedi) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(n.orsTiponovedadNove) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(n.orsDescripcionNove) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(n.orsCriticidadNove) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(n.orsResponsableNove) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(n.orsAcciontomadaNove) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsnovmdnovedad> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}