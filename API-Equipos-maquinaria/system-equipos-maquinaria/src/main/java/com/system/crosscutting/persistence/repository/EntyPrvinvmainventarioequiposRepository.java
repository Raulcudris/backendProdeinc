package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyPrvinvmainventarioequipos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyPrvinvmainventarioequiposRepository
        extends JpaRepository<EntyPrvinvmainventarioequipos, Integer> {

    Optional<EntyPrvinvmainventarioequipos> findByPrvIdentifkeyInve(
            String prvIdentifkeyInve
    );

    List<EntyPrvinvmainventarioequipos> findByPrvIdentifkeyMprv(
            String prvIdentifkeyMprv
    );

    List<EntyPrvinvmainventarioequipos> findByPrvTipoequipoTieq(
            String prvTipoequipoTieq
    );

    List<EntyPrvinvmainventarioequipos> findByPrvEquipoactivoInve(
            String prvEquipoactivoInve
    );

    List<EntyPrvinvmainventarioequipos> findByPrvEstadoregInve(
            String prvEstadoregInve
    );

    @Query("SELECT e FROM EntyPrvinvmainventarioequipos e " +
            "WHERE e.prvPrimarykeyInve = :id")
    Page<EntyPrvinvmainventarioequipos> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyPrvinvmainventarioequipos e " +
            "WHERE LOWER(e.prvIdentifkeyInve) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvinvmainventarioequipos> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyPrvinvmainventarioequipos e " +
            "WHERE LOWER(e.prvIdentifkeyMprv) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvinvmainventarioequipos> searchByProveedor(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyPrvinvmainventarioequipos e " +
            "WHERE LOWER(e.prvTipoequipoTieq) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvinvmainventarioequipos> searchByTipoEquipo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyPrvinvmainventarioequipos e " +
            "WHERE e.prvEquipoactivoInve = :disponible")
    Page<EntyPrvinvmainventarioequipos> searchByDisponible(
            @Param("disponible") String disponible,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyPrvinvmainventarioequipos e " +
            "WHERE e.prvEstadoregInve = :status")
    Page<EntyPrvinvmainventarioequipos> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT e FROM EntyPrvinvmainventarioequipos e " +
            "WHERE LOWER(e.prvIdentifkeyInve) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.prvIdentifkeyMprv) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.prvTipoequipoTieq) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.prvNombrequipoInve) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.prvRefermodeloInve) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.prvEquipoestadoInve) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(e.prvDescripcionInve) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvinvmainventarioequipos> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}