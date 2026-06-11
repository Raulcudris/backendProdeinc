package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyPrvinvmdequipmaquinaria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyPrvinvmdequipmaquinariaRepository
        extends JpaRepository<EntyPrvinvmdequipmaquinaria, Integer> {

    Optional<EntyPrvinvmdequipmaquinaria> findByPrvTipoequipoTieq(
            String prvTipoequipoTieq
    );

    List<EntyPrvinvmdequipmaquinaria> findByPrvIdentifkeyUnme(
            String prvIdentifkeyUnme
    );

    List<EntyPrvinvmdequipmaquinaria> findByPrvEstadoregTieq(
            String prvEstadoregTieq
    );

    @Query("SELECT t FROM EntyPrvinvmdequipmaquinaria t " +
            "WHERE t.prvPrimarykeyTieq = :id")
    Page<EntyPrvinvmdequipmaquinaria> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT t FROM EntyPrvinvmdequipmaquinaria t " +
            "WHERE LOWER(t.prvTipoequipoTieq) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvinvmdequipmaquinaria> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT t FROM EntyPrvinvmdequipmaquinaria t " +
            "WHERE LOWER(t.prvIdentifkeyUnme) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvinvmdequipmaquinaria> searchByUnidad(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT t FROM EntyPrvinvmdequipmaquinaria t " +
            "WHERE t.prvEstadoregTieq = :status")
    Page<EntyPrvinvmdequipmaquinaria> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT t FROM EntyPrvinvmdequipmaquinaria t " +
            "WHERE LOWER(t.prvTipoequipoTieq) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(t.prvDescripcionTieq) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(t.prvIdentifkeyUnme) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(t.prvTiporegistTieq) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvinvmdequipmaquinaria> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}