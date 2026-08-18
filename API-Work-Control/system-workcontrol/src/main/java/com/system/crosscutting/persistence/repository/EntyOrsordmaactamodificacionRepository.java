package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsordmaactamodificacion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsordmaactamodificacionRepository
        extends JpaRepository<EntyOrsordmaactamodificacion, Integer> {

    Optional<EntyOrsordmaactamodificacion> findByOrsIdentifkeyAcmo(String orsIdentifkeyAcmo);

    List<EntyOrsordmaactamodificacion> findByOrsIdentifkeyOrde(String orsIdentifkeyOrde);

    List<EntyOrsordmaactamodificacion> findByOrsEstadoactaAcmo(String orsEstadoactaAcmo);

    List<EntyOrsordmaactamodificacion> findByOrsEstadoregAcmo(String orsEstadoregAcmo);

    @Query("SELECT a FROM EntyOrsordmaactamodificacion a " +
            "WHERE LOWER(a.orsIdentifkeyAcmo) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmaactamodificacion> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT a FROM EntyOrsordmaactamodificacion a " +
            "WHERE LOWER(a.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmaactamodificacion> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT a FROM EntyOrsordmaactamodificacion a " +
            "WHERE LOWER(a.orsNumeroactaAcmo) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmaactamodificacion> searchByNumeroActa(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT a FROM EntyOrsordmaactamodificacion a " +
            "WHERE a.orsEstadoactaAcmo = :estadoActa")
    Page<EntyOrsordmaactamodificacion> searchByEstadoActa(
            @Param("estadoActa") String estadoActa,
            Pageable pageable
    );

    @Query("SELECT a FROM EntyOrsordmaactamodificacion a " +
            "WHERE a.orsEstadoregAcmo = :status")
    Page<EntyOrsordmaactamodificacion> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT a FROM EntyOrsordmaactamodificacion a " +
            "WHERE LOWER(a.orsIdentifkeyAcmo) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(a.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(a.orsNumeroactaAcmo) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(a.orsTipomodificacionAcmo) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(a.orsEstadoactaAcmo) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(a.orsCausalAcmo) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmaactamodificacion> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}