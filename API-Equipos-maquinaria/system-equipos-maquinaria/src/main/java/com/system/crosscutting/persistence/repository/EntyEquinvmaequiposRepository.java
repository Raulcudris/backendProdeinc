package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyEquinvmaequipos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio JPA para consultar y administrar equipos, maquinaria,
 * vehículos y herramientas.
 */
public interface EntyEquinvmaequiposRepository
        extends JpaRepository<EntyEquinvmaequipos, Integer> {

    Optional<EntyEquinvmaequipos> findByEquIdentifkeyEqui(String equIdentifkeyEqui);

    List<EntyEquinvmaequipos> findByEquIdentifkeyTieq(String equIdentifkeyTieq);

    List<EntyEquinvmaequipos> findByEquEstadooperEqui(String equEstadooperEqui);

    List<EntyEquinvmaequipos> findByPrvIdentifkeyMprv(String prvIdentifkeyMprv);

    @Query("SELECT e FROM EntyEquinvmaequipos e " +
            "WHERE e.equPrimarykeyEqui = :id")
    Page<EntyEquinvmaequipos> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyEquinvmaequipos e " +
            "WHERE LOWER(e.equIdentifkeyEqui) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEquinvmaequipos> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyEquinvmaequipos e " +
            "WHERE LOWER(e.equIdentifkeyTieq) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEquinvmaequipos> searchByTipoEquipo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyEquinvmaequipos e " +
            "WHERE LOWER(e.prvIdentifkeyMprv) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEquinvmaequipos> searchByProveedor(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyEquinvmaequipos e " +
            "WHERE LOWER(e.equPlacaEqui) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEquinvmaequipos> searchByPlaca(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyEquinvmaequipos e " +
            "WHERE LOWER(e.equSerialEqui) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEquinvmaequipos> searchBySerial(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyEquinvmaequipos e " +
            "WHERE e.equEstadooperEqui = :estadoOperativo")
    Page<EntyEquinvmaequipos> searchByEstadoOperativo(
            @Param("estadoOperativo") String estadoOperativo,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyEquinvmaequipos e " +
            "WHERE e.equEstadoregEqui = :status")
    Page<EntyEquinvmaequipos> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyEquinvmaequipos e " +
            "WHERE LOWER(e.equIdentifkeyEqui) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.equIdentifkeyTieq) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.prvIdentifkeyMprv) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.equCodinternoEqui) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.equNombreEqui) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.equMarcaEqui) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.equModeloEqui) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.equPlacaEqui) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.equSerialEqui) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyEquinvmaequipos> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}