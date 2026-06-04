package com.system.crosscutting.persistence.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.system.crosscutting.persistence.entity.EntyEquasimdasignequipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio JPA para consultar y administrar asignaciones de equipos.
 */
public interface EntyEquasimdasignequipoRepository
        extends JpaRepository<EntyEquasimdasignequipo, Integer> {

    Optional<EntyEquasimdasignequipo> findByEquIdentifkeyAseq(String equIdentifkeyAseq);

    List<EntyEquasimdasignequipo> findByEquIdentifkeyEqui(String equIdentifkeyEqui);

    List<EntyEquasimdasignequipo> findByOrsIdentifkeyOrde(String orsIdentifkeyOrde);

    List<EntyEquasimdasignequipo> findByOrsIdentifkeyPltr(String orsIdentifkeyPltr);

    List<EntyEquasimdasignequipo> findByEquEstadoregAseq(String equEstadoregAseq);

    @Query("SELECT a FROM EntyEquasimdasignequipo a " +
            "WHERE a.equPrimarykeyAseq = :id")
    Page<EntyEquasimdasignequipo> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT a FROM EntyEquasimdasignequipo a " +
            "WHERE LOWER(a.equIdentifkeyAseq) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEquasimdasignequipo> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT a FROM EntyEquasimdasignequipo a " +
            "WHERE LOWER(a.equIdentifkeyEqui) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEquasimdasignequipo> searchByEquipo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT a FROM EntyEquasimdasignequipo a " +
            "WHERE LOWER(a.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEquasimdasignequipo> searchByOrdenServicio(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT a FROM EntyEquasimdasignequipo a " +
            "WHERE LOWER(a.orsIdentifkeyPltr) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEquasimdasignequipo> searchByPlanTrabajo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT a FROM EntyEquasimdasignequipo a " +
            "WHERE LOWER(a.equResponsableAseq) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEquasimdasignequipo> searchByResponsable(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT a FROM EntyEquasimdasignequipo a " +
            "WHERE a.equEstadoregAseq = :status")
    Page<EntyEquasimdasignequipo> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT a FROM EntyEquasimdasignequipo a " +
            "WHERE a.equFechaasigAseq BETWEEN :fechaInicio AND :fechaFin")
    Page<EntyEquasimdasignequipo> searchByFechaAsignacionBetween(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin,
            Pageable pageable
    );

    @Query("SELECT a FROM EntyEquasimdasignequipo a " +
            "WHERE LOWER(a.equIdentifkeyAseq) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(a.equIdentifkeyEqui) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(a.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(a.orsIdentifkeyPltr) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(a.equResponsableAseq) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(a.equObservacionAseq) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEquasimdasignequipo> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}