package com.system.crosscutting.persistence.repository;
import java.util.Optional;
import com.system.crosscutting.persistence.entity.EntyOrsordmaordenservicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EntyOrsordmaordenservicioRepository
        extends JpaRepository<EntyOrsordmaordenservicio, Integer> {

    Optional<EntyOrsordmaordenservicio> findByOrsIdentifkeyOrde(String orsIdentifkeyOrde);

    @Query("SELECT o FROM EntyOrsordmaordenservicio o " +
            "WHERE o.orsPrimarykeyOrde = :id")
    Page<EntyOrsordmaordenservicio> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT o FROM EntyOrsordmaordenservicio o " +
            "WHERE LOWER(o.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmaordenservicio> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT o FROM EntyOrsordmaordenservicio o " +
            "WHERE o.orsEstadoregOrde = :status")
    Page<EntyOrsordmaordenservicio> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT o FROM EntyOrsordmaordenservicio o " +
            "WHERE LOWER(o.orsIdentifkeyOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(o.orsCodservicioSebs) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(o.orsServiceventOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(o.orsServiclugarOrde) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(o.orsServicobjetoOrde) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyOrsordmaordenservicio> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}