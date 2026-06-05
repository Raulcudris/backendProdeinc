package com.system.crosscutting.persistence.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.system.crosscutting.persistence.entity.EntyDocvenmdvencimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyDocvenmdvencimientoRepository extends JpaRepository<EntyDocvenmdvencimiento, Integer> {

    Optional<EntyDocvenmdvencimiento> findByDocIdentifkeyVedo(String docIdentifkeyVedo);

    List<EntyDocvenmdvencimiento> findByDocIdentifkeyDocu(String docIdentifkeyDocu);

    List<EntyDocvenmdvencimiento> findByDocFechavenceVedoLessThanEqual(LocalDate fecha);

    List<EntyDocvenmdvencimiento> findByDocFechavenceVedoBetween(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT v FROM EntyDocvenmdvencimiento v " +
            "WHERE v.docPrimarykeyVedo = :id")
    Page<EntyDocvenmdvencimiento> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT v FROM EntyDocvenmdvencimiento v " +
            "WHERE LOWER(v.docIdentifkeyVedo) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyDocvenmdvencimiento> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT v FROM EntyDocvenmdvencimiento v " +
            "WHERE LOWER(v.docIdentifkeyDocu) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyDocvenmdvencimiento> searchByDocumento(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT v FROM EntyDocvenmdvencimiento v " +
            "WHERE v.docFechavenceVedo <= :fecha")
    Page<EntyDocvenmdvencimiento> searchByVencidos(
            @Param("fecha") LocalDate fecha,
            Pageable pageable
    );

    @Query("SELECT v FROM EntyDocvenmdvencimiento v " +
            "WHERE v.docFechavenceVedo BETWEEN :fechaInicio AND :fechaFin")
    Page<EntyDocvenmdvencimiento> searchByProximos(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin,
            Pageable pageable
    );

    @Query("SELECT v FROM EntyDocvenmdvencimiento v " +
            "WHERE v.docEstadovencVedo = :estado")
    Page<EntyDocvenmdvencimiento> searchByEstadoVencimiento(
            @Param("estado") String estado,
            Pageable pageable
    );

    @Query("SELECT v FROM EntyDocvenmdvencimiento v " +
            "WHERE v.docEstadoregVedo = :status")
    Page<EntyDocvenmdvencimiento> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT v FROM EntyDocvenmdvencimiento v " +
            "WHERE LOWER(v.docIdentifkeyVedo) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(v.docIdentifkeyDocu) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(v.docObservacionVedo) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyDocvenmdvencimiento> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}