package com.system.crosscutting.persistence.repository;
import java.util.List;
import java.util.Optional;
import com.system.crosscutting.persistence.entity.EntyDoctipmatipodocumento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyDoctipmatipodocumentoRepository
        extends JpaRepository<EntyDoctipmatipodocumento, Integer> {

    Optional<EntyDoctipmatipodocumento> findByDocIdentifkeyTido(String docIdentifkeyTido);

    List<EntyDoctipmatipodocumento> findByDocIdentifkeyCado(String docIdentifkeyCado);

    List<EntyDoctipmatipodocumento> findByDocRequievenceTido(String docRequievenceTido);

    @Query("SELECT t FROM EntyDoctipmatipodocumento t " +
            "WHERE t.docPrimarykeyTido = :id")
    Page<EntyDoctipmatipodocumento> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT t FROM EntyDoctipmatipodocumento t " +
            "WHERE LOWER(t.docIdentifkeyTido) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyDoctipmatipodocumento> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT t FROM EntyDoctipmatipodocumento t " +
            "WHERE LOWER(t.docIdentifkeyCado) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyDoctipmatipodocumento> searchByCategoria(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT t FROM EntyDoctipmatipodocumento t " +
            "WHERE t.docRequievenceTido = :requiere")
    Page<EntyDoctipmatipodocumento> searchByRequiereVencimiento(
            @Param("requiere") String requiere,
            Pageable pageable
    );

    @Query("SELECT t FROM EntyDoctipmatipodocumento t " +
            "WHERE t.docEstadoregTido = :status")
    Page<EntyDoctipmatipodocumento> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT t FROM EntyDoctipmatipodocumento t " +
            "WHERE LOWER(t.docIdentifkeyTido) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(t.docIdentifkeyCado) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(t.docDescripcionTido) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyDoctipmatipodocumento> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}