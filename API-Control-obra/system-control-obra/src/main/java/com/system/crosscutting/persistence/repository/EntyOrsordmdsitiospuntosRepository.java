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

    Optional<EntyOrsordmdsitiospuntos> findByOrsIdentifkeyPunt(String orsIdentifkeyPunt);

    List<EntyOrsordmdsitiospuntos> findByOrsIdentifkeyOrde(String orsIdentifkeyOrde);

    @Query("SELECT s FROM EntyOrsordmdsitiospuntos s " +
            "WHERE s.orsPrimarykeyPunt = :id")
    Page<EntyOrsordmdsitiospuntos> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT s FROM EntyOrsordmdsitiospuntos s " +
            "WHERE LOWER(s.orsIdentifkeyPunt) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdsitiospuntos> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT s FROM EntyOrsordmdsitiospuntos s " +
            "WHERE LOWER(s.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdsitiospuntos> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT s FROM EntyOrsordmdsitiospuntos s " +
            "WHERE LOWER(s.orsNombresitioPunt) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdsitiospuntos> searchByNombreSitio(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT s FROM EntyOrsordmdsitiospuntos s " +
            "WHERE LOWER(s.sisCodproSipr) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdsitiospuntos> searchByProvincia(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT s FROM EntyOrsordmdsitiospuntos s " +
            "WHERE s.orsEstadoregPunt = :status")
    Page<EntyOrsordmdsitiospuntos> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT s FROM EntyOrsordmdsitiospuntos s " +
            "WHERE LOWER(s.orsIdentifkeyPunt) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(s.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(s.orsNombresitioPunt) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(s.sisCodproSipr) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmdsitiospuntos> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}