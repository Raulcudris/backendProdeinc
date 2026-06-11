package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyPrvinvmdunidamedequipo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyPrvinvmdunidamedequipoRepository
        extends JpaRepository<EntyPrvinvmdunidamedequipo, String> {

    Optional<EntyPrvinvmdunidamedequipo> findByPrvTipunidamedUnme(
            String prvTipunidamedUnme
    );

    List<EntyPrvinvmdunidamedequipo> findByPrvEstadoregUnme(
            String prvEstadoregUnme
    );

    @Query("SELECT u FROM EntyPrvinvmdunidamedequipo u " +
            "WHERE LOWER(u.prvTipunidamedUnme) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvinvmdunidamedequipo> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT u FROM EntyPrvinvmdunidamedequipo u " +
            "WHERE u.prvEstadoregUnme = :status")
    Page<EntyPrvinvmdunidamedequipo> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT u FROM EntyPrvinvmdunidamedequipo u " +
            "WHERE LOWER(u.prvTipunidamedUnme) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(u.prvDescmedidaUnme) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvinvmdunidamedequipo> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}