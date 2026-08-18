package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsordmdresumenequipos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsordmdresumenequiposRepository
        extends JpaRepository<EntyOrsordmdresumenequipos, Integer> {

    Optional<EntyOrsordmdresumenequipos> findByOrsIdentifkeyRseq(String orsIdentifkeyRseq);

    List<EntyOrsordmdresumenequipos> findByOrsIdentifkeyOrde(String orsIdentifkeyOrde);

    List<EntyOrsordmdresumenequipos> findByPrvTipoequipoTieq(String prvTipoequipoTieq);

    @Query("SELECT r FROM EntyOrsordmdresumenequipos r " +
            "WHERE r.orsPrimarykeyRseq = :id")
    Page<EntyOrsordmdresumenequipos> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsordmdresumenequipos r " +
            "WHERE LOWER(r.orsIdentifkeyRseq) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdresumenequipos> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsordmdresumenequipos r " +
            "WHERE LOWER(r.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdresumenequipos> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsordmdresumenequipos r " +
            "WHERE LOWER(r.prvTipoequipoTieq) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdresumenequipos> searchByTipoEquipo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsordmdresumenequipos r " +
            "WHERE r.orsEstadoregRseq = :status")
    Page<EntyOrsordmdresumenequipos> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT r FROM EntyOrsordmdresumenequipos r " +
            "WHERE LOWER(r.orsIdentifkeyRseq) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(r.prvTipoequipoTieq) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdresumenequipos> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}