package com.system.crosscutting.persistence.repository;
import java.util.List;
import com.system.crosscutting.persistence.entity.EntyOrsordmdproyecsemana;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyOrsordmdproyecsemanaRepository
        extends JpaRepository<EntyOrsordmdproyecsemana, Integer> {

    boolean existsByOrsIdentifkeyOrdeAndOrsEstadoregPsem(
            String ordenKey,
            String estado
    );

    List<EntyOrsordmdproyecsemana> findByOrsIdentifkeyOrde(
            String ordenKey
    );

    List<EntyOrsordmdproyecsemana>
    findByOrsIdentifkeyOrdeOrderByOrsNumerosemPsemAsc(
            String ordenKey
    );

    Page<EntyOrsordmdproyecsemana>
    findByOrsIdentifkeyOrdeOrderByOrsNumerosemPsemAsc(
            String ordenKey,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsordmdproyecsemana e " +
                    "WHERE (:id IS NULL OR e.orsPrimarykeyPsem = :id) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsNumerosemPsem ASC"
    )
    Page<EntyOrsordmdproyecsemana> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsordmdproyecsemana e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyPsem, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsNumerosemPsem ASC"
    )
    Page<EntyOrsordmdproyecsemana> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsordmdproyecsemana e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyOrde, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsNumerosemPsem ASC"
    )
    Page<EntyOrsordmdproyecsemana> searchByOrden(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsordmdproyecsemana e " +
                    "WHERE LOWER(COALESCE(e.orsEstadoregPsem, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsNumerosemPsem ASC"
    )
    Page<EntyOrsordmdproyecsemana> searchByStatus(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(
            "SELECT e FROM EntyOrsordmdproyecsemana e " +
                    "WHERE LOWER(COALESCE(e.orsIdentifkeyPsem, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsIdentifkeyOrde, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsTitulosemPsem, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsDiashabilesPsem, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsDiasnhabilesPsem, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsTiporegistPsem, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(e.orsEstadoregPsem, '')) " +
                    "LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "ORDER BY e.orsIdentifkeyOrde ASC, e.orsNumerosemPsem ASC"
    )
    Page<EntyOrsordmdproyecsemana> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}