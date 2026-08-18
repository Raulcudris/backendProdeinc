package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyOrsordmaordenservicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsordmaordenservicioRepository
        extends JpaRepository<EntyOrsordmaordenservicio, Integer> {

    Optional<EntyOrsordmaordenservicio> findByOrsIdentifkeyOrde(
            String ordenKey
    );

    boolean existsByOrsIdentifkeyOrde(
            String ordenKey
    );

    List<EntyOrsordmaordenservicio> findByOrsEstadoregOrde(
            String estado
    );

    @Query(
            "SELECT e FROM EntyOrsordmaordenservicio e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyOrde, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsServiceventOrde, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsServiclugarOrde, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsServicobjetoOrde, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.prvIdentifkeyMprv, '')) LIKE LOWER(CONCAT('%', :filter, '%'))"
    )
    Page<EntyOrsordmaordenservicio> searchText(
            @Param("filter") String filter,
            Pageable pageable
    );
}