package com.system.crosscutting.persistence.repository;
import java.util.Optional;
import com.system.crosscutting.persistence.entity.EntyEqumedmaunidadmedida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio JPA para consultar y administrar unidades de medida.
 */
public interface EntyEqumedmaunidadmedidaRepository
        extends JpaRepository<EntyEqumedmaunidadmedida, Integer> {

    Optional<EntyEqumedmaunidadmedida> findByEquIdentifkeyUnme(String equIdentifkeyUnme);

    Optional<EntyEqumedmaunidadmedida> findByEquCodigoUnme(String equCodigoUnme);

    @Query("SELECT u FROM EntyEqumedmaunidadmedida u " +
            "WHERE u.equPrimarykeyUnme = :id")
    Page<EntyEqumedmaunidadmedida> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT u FROM EntyEqumedmaunidadmedida u " +
            "WHERE LOWER(u.equIdentifkeyUnme) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEqumedmaunidadmedida> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT u FROM EntyEqumedmaunidadmedida u " +
            "WHERE LOWER(u.equCodigoUnme) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEqumedmaunidadmedida> searchByCodigo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT u FROM EntyEqumedmaunidadmedida u " +
            "WHERE u.equEstadoregUnme = :status")
    Page<EntyEqumedmaunidadmedida> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT u FROM EntyEqumedmaunidadmedida u " +
            "WHERE LOWER(u.equIdentifkeyUnme) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(u.equCodigoUnme) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(u.equDescripcionUnme) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEqumedmaunidadmedida> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}