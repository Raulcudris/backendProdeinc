package com.system.crosscutting.persistence.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.system.crosscutting.persistence.entity.EntyDocdocmadocumento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyDocdocmadocumentoRepository extends JpaRepository<EntyDocdocmadocumento, Integer> {

    Optional<EntyDocdocmadocumento> findByDocIdentifkeyDocu(String docIdentifkeyDocu);

    List<EntyDocdocmadocumento> findByDocIdentifkeyTido(String docIdentifkeyTido);

    List<EntyDocdocmadocumento> findByDocTiporeferenDocuAndDocReferenciaidDocu(
            String docTiporeferenDocu,
            String docReferenciaidDocu
    );

    List<EntyDocdocmadocumento> findByDocFechavenceDocuLessThanEqual(LocalDate fecha);

    @Query("SELECT d FROM EntyDocdocmadocumento d " +
            "WHERE d.docPrimarykeyDocu = :id")
    Page<EntyDocdocmadocumento> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyDocdocmadocumento d " +
            "WHERE LOWER(d.docIdentifkeyDocu) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyDocdocmadocumento> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyDocdocmadocumento d " +
            "WHERE LOWER(d.docIdentifkeyTido) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyDocdocmadocumento> searchByTipoDocumento(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyDocdocmadocumento d " +
            "WHERE LOWER(d.docTiporeferenDocu) LIKE LOWER(CONCAT('%', :tipoReferencia, '%')) " +
            "AND LOWER(d.docReferenciaidDocu) LIKE LOWER(CONCAT('%', :referenciaId, '%'))")
    Page<EntyDocdocmadocumento> searchByReferencia(
            @Param("tipoReferencia") String tipoReferencia,
            @Param("referenciaId") String referenciaId,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyDocdocmadocumento d " +
            "WHERE d.docFechavenceDocu <= :fecha")
    Page<EntyDocdocmadocumento> searchByVencidos(
            @Param("fecha") LocalDate fecha,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyDocdocmadocumento d " +
            "WHERE d.docEstadoregDocu = :status")
    Page<EntyDocdocmadocumento> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT d FROM EntyDocdocmadocumento d " +
            "WHERE LOWER(d.docIdentifkeyDocu) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.docIdentifkeyTido) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.docNombreDocu) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.docDescripcionDocu) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.docEntidadDocu) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.docTiporeferenDocu) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(d.docReferenciaidDocu) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyDocdocmadocumento> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}