package com.system.crosscutting.persistence.repository;
import java.util.Optional;
import com.system.crosscutting.persistence.entity.EntyEqutipmatipoequipos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio JPA para consultar y administrar tipos de equipo.
 */
public interface EntyEqutipmatipoequipoRepository
        extends JpaRepository<EntyEqutipmatipoequipos, Integer> {

    Optional<EntyEqutipmatipoequipos> findByEquIdentifkeyTieq(String equIdentifkeyTieq);

    @Query("SELECT t FROM EntyEqutipmatipoequipos t " +
            "WHERE t.equPrimarykeyTieq = :id")
    Page<EntyEqutipmatipoequipos> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT t FROM EntyEqutipmatipoequipos t " +
            "WHERE LOWER(t.equIdentifkeyTieq) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEqutipmatipoequipos> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT t FROM EntyEqutipmatipoequipos t " +
            "WHERE t.equEstadoregTieq = :status")
    Page<EntyEqutipmatipoequipos> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT t FROM EntyEqutipmatipoequipos t " +
            "WHERE LOWER(t.equIdentifkeyTieq) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(t.equDescripcionTieq) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEqutipmatipoequipos> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}