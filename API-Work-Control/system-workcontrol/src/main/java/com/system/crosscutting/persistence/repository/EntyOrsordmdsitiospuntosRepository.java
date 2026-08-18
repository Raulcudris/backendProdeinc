package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsordmdsitiospuntos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsordmdsitiospuntosRepository
        extends JpaRepository<EntyOrsordmdsitiospuntos, Integer> {

    boolean existsByOrsIdentifkeyPunt(String puntoKey);

    boolean existsByOrsIdentifkeyOrdeAndOrsEstadoregPunt(
            String ordenKey,
            String estado
    );

    List<EntyOrsordmdsitiospuntos>
    findByOrsIdentifkeyOrdeOrderByOrsPrimarykeyPuntAsc(
            String ordenKey
    );

    @Query(
            "SELECT e FROM EntyOrsordmdsitiospuntos e " +
                    "WHERE (:id IS NULL OR e.orsPrimarykeyPunt = :id) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPunt ASC"
    )
    Page<EntyOrsordmdsitiospuntos> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsordmdsitiospuntos e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyPunt, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPunt ASC"
    )
    Page<EntyOrsordmdsitiospuntos> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsordmdsitiospuntos e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyOrde, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPunt ASC"
    )
    Page<EntyOrsordmdsitiospuntos> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsordmdsitiospuntos e " +
                    "WHERE LOWER(COALESCE(e.orsEstadoregPunt, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPunt ASC"
    )
    Page<EntyOrsordmdsitiospuntos> searchByStatus(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsordmdsitiospuntos e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyPunt, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsIdentifkeyOrde, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsNombresitioPunt, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.sisCodproSipr, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsPathimagenPunt, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsTiporegistPunt, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsEstadoregPunt, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsPrimarykeyPunt ASC"
    )
    Page<EntyOrsordmdsitiospuntos> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}