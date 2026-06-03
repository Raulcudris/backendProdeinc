package com.system.crosscutting.persistence.repository;
import java.util.Optional;
import com.system.crosscutting.persistence.entity.EntyDoccatmacategoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EntyDoccatmacategoriaRepository extends JpaRepository<EntyDoccatmacategoria, Integer> {

    Optional<EntyDoccatmacategoria> findByDocIdentifkeyCado(String docIdentifkeyCado);

    @Query("SELECT c FROM EntyDoccatmacategoria c " +
            "WHERE c.docPrimarykeyCado = :id")
    Page<EntyDoccatmacategoria> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT c FROM EntyDoccatmacategoria c " +
            "WHERE LOWER(c.docIdentifkeyCado) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyDoccatmacategoria> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT c FROM EntyDoccatmacategoria c " +
            "WHERE c.docEstadoregCado = :status")
    Page<EntyDoccatmacategoria> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT c FROM EntyDoccatmacategoria c " +
            "WHERE LOWER(c.docIdentifkeyCado) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(c.docDescripcionCado) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyDoccatmacategoria> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}