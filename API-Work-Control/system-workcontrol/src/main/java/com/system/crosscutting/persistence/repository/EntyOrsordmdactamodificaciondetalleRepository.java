package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsordmdactamodificaciondetalle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsordmdactamodificaciondetalleRepository
        extends JpaRepository<EntyOrsordmdactamodificaciondetalle, Integer> {

    Optional<EntyOrsordmdactamodificaciondetalle> findByOrsIdentifkeyAcmd(String orsIdentifkeyAcmd);

    List<EntyOrsordmdactamodificaciondetalle> findByOrsIdentifkeyAcmo(String orsIdentifkeyAcmo);

    List<EntyOrsordmdactamodificaciondetalle> findByOrsIdentifkeyOrde(String orsIdentifkeyOrde);

    List<EntyOrsordmdactamodificaciondetalle> findByOrsIdentifkeyRseq(String orsIdentifkeyRseq);

    List<EntyOrsordmdactamodificaciondetalle> findByPrvTipoequipoTieq(String prvTipoequipoTieq);

    List<EntyOrsordmdactamodificaciondetalle> findByOrsEstadoregAcmd(String orsEstadoregAcmd);

    @Query("SELECT d FROM EntyOrsordmdactamodificaciondetalle d " +
            "WHERE LOWER(d.orsIdentifkeyAcmd) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdactamodificaciondetalle> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyOrsordmdactamodificaciondetalle d " +
            "WHERE LOWER(d.orsIdentifkeyAcmo) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdactamodificaciondetalle> searchByActa(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyOrsordmdactamodificaciondetalle d " +
            "WHERE LOWER(d.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdactamodificaciondetalle> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyOrsordmdactamodificaciondetalle d " +
            "WHERE LOWER(d.orsIdentifkeyRseq) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdactamodificaciondetalle> searchByResumenEquipo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyOrsordmdactamodificaciondetalle d " +
            "WHERE LOWER(d.prvTipoequipoTieq) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdactamodificaciondetalle> searchByTipoEquipo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyOrsordmdactamodificaciondetalle d " +
            "WHERE d.orsEstadoregAcmd = :status")
    Page<EntyOrsordmdactamodificaciondetalle> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyOrsordmdactamodificaciondetalle d " +
            "WHERE LOWER(d.orsIdentifkeyAcmd) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.orsIdentifkeyAcmo) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.orsIdentifkeyRseq) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.prvTipoequipoTieq) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.orsDescripcionEquipoAcmd) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.orsUnidadAcmd) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.orsObservacionAcmd) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdactamodificaciondetalle> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}