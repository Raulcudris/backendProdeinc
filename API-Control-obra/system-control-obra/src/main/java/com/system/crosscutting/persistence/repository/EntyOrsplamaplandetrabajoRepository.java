package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsplamaplandetrabajo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsplamaplandetrabajoRepository
        extends JpaRepository<EntyOrsplamaplandetrabajo, Integer> {

    Optional<EntyOrsplamaplandetrabajo> findByOrsIdentifkeyPltr(String orsIdentifkeyPltr);

    List<EntyOrsplamaplandetrabajo> findByOrsIdentifkeyOrde(String orsIdentifkeyOrde);

    List<EntyOrsplamaplandetrabajo> findByOrsIdentifkeyPunt(String orsIdentifkeyPunt);

    List<EntyOrsplamaplandetrabajo> findByOrsIdentifkeyRseq(String orsIdentifkeyRseq);

    List<EntyOrsplamaplandetrabajo> findByPrvIdentifkeyInve(String prvIdentifkeyInve);

    @Query("SELECT p FROM EntyOrsplamaplandetrabajo p " +
            "WHERE p.orsPrimarykeyPltr = :id")
    Page<EntyOrsplamaplandetrabajo> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplamaplandetrabajo p " +
            "WHERE LOWER(p.orsIdentifkeyPltr) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamaplandetrabajo> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplamaplandetrabajo p " +
            "WHERE LOWER(p.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamaplandetrabajo> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplamaplandetrabajo p " +
            "WHERE LOWER(p.orsIdentifkeyPunt) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamaplandetrabajo> searchByPunto(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplamaplandetrabajo p " +
            "WHERE LOWER(p.orsIdentifkeyRseq) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamaplandetrabajo> searchByResumenEquipo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplamaplandetrabajo p " +
            "WHERE LOWER(p.prvIdentifkeyInve) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamaplandetrabajo> searchByEquipoInventario(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplamaplandetrabajo p " +
            "WHERE p.orsEstadoregPltr = :status")
    Page<EntyOrsplamaplandetrabajo> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyOrsplamaplandetrabajo p " +
            "WHERE LOWER(p.orsIdentifkeyPltr) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.orsIdentifkeyPunt) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.orsIdentifkeyRseq) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.prvIdentifkeyInve) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.orsDesactividadPltr) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsplamaplandetrabajo> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}