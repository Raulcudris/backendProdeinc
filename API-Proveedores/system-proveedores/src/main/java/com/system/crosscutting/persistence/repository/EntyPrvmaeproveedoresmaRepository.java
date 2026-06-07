package com.system.crosscutting.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyPrvmaeproveedoresma;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntyPrvmaeproveedoresmaRepository
        extends JpaRepository<EntyPrvmaeproveedoresma, Integer> {

    Optional<EntyPrvmaeproveedoresma> findByPrvIdentifkeyMprv(String prvIdentifkeyMprv);

    List<EntyPrvmaeproveedoresma> findByPrvEstadoregMprv(String prvEstadoregMprv);

    @Query("SELECT p FROM EntyPrvmaeproveedoresma p " +
            "WHERE p.prvPrimarykeyMprv = :id")
    Page<EntyPrvmaeproveedoresma> searchByPrimaryKey(
            @Param("id") Integer id,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyPrvmaeproveedoresma p " +
            "WHERE LOWER(p.prvIdentifkeyMprv) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvmaeproveedoresma> searchByIdentifKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyPrvmaeproveedoresma p " +
            "WHERE LOWER(p.prvNumeronitMprv) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvmaeproveedoresma> searchByNit(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyPrvmaeproveedoresma p " +
            "WHERE LOWER(p.prvRazonsocialMprv) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvmaeproveedoresma> searchByRazonSocial(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyPrvmaeproveedoresma p " +
            "WHERE LOWER(p.prvCorreoMprv) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvmaeproveedoresma> searchByCorreo(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyPrvmaeproveedoresma p " +
            "WHERE p.prvEstadoregMprv = :status")
    Page<EntyPrvmaeproveedoresma> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyPrvmaeproveedoresma p " +
            "WHERE LOWER(p.prvIdentifkeyMprv) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.prvNumeronitMprv) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.prvRazonsocialMprv) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.prvCorreoMprv) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.prvTelefonoMprv) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvmaeproveedoresma> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}