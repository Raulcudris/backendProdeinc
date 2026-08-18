package com.system.crosscutting.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsconfnovedadhistori;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsconfnovedadhistoriRepository
        extends JpaRepository<EntyOrsconfnovedadhistori, Integer> {

    Optional<EntyOrsconfnovedadhistori> findByOrsIdentifkeyNove(
            String novedadKey
    );

    boolean existsByOrsIdentifkeyNove(
            String novedadKey
    );

    List<EntyOrsconfnovedadhistori>
    findByOrsIdentifkeyOrdeOrderByOrsFechreportNoveDesc(
            String ordenKey
    );

    List<EntyOrsconfnovedadhistori>
    findByOrsTiponovedadNovtOrderByOrsFechreportNoveDesc(
            String tipoNovedad
    );

    List<EntyOrsconfnovedadhistori>
    findByOrsRegistrbaseNoveOrderByOrsFechreportNoveDesc(
            String registroBase
    );

    List<EntyOrsconfnovedadhistori>
    findByOrsRegistrnoveNoveOrderByOrsFechreportNoveDesc(
            String registroNovedad
    );

    List<EntyOrsconfnovedadhistori>
    findByOrsFechreportNoveOrderByOrsPrimarykeyNoveDesc(
            LocalDate fechaReporte
    );

    @Query(
            "SELECT e FROM EntyOrsconfnovedadhistori e " +
                    "WHERE (:id IS NULL OR e.orsPrimarykeyNove = :id) " +
                    "ORDER BY e.orsFechreportNove DESC"
    )
    Page<EntyOrsconfnovedadhistori> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsconfnovedadhistori e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyNove, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsFechreportNove DESC"
    )
    Page<EntyOrsconfnovedadhistori> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsconfnovedadhistori e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyOrde, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsFechreportNove DESC"
    )
    Page<EntyOrsconfnovedadhistori> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsconfnovedadhistori e " +
                    "WHERE LOWER(COALESCE(e.orsTiponovedadNovt, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsFechreportNove DESC"
    )
    Page<EntyOrsconfnovedadhistori> searchByTipo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsconfnovedadhistori e " +
                    "WHERE LOWER(COALESCE(e.orsEstadoregNove, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsFechreportNove DESC"
    )
    Page<EntyOrsconfnovedadhistori> searchByStatus(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsconfnovedadhistori e " +
                    "WHERE (" +
                    "LOWER(COALESCE(e.orsIdentifkeyNove, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsIdentifkeyOrde, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsTiponovedadNovt, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsRegistrbaseNove, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsRegistrnoveNove, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsEstadoregNove, '')) LIKE LOWER(CONCAT('%', :filter, '%'))" +
                    ") " +
                    "ORDER BY e.orsFechreportNove DESC"
    )
    Page<EntyOrsconfnovedadhistori> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}