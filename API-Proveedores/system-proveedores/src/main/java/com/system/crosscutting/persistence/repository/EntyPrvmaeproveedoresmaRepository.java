package com.system.crosscutting.persistence.repository;
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

    @Query("SELECT p FROM EntyPrvmaeproveedoresma p WHERE p.prvPrimarykeyMprv = :id")
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
            "WHERE LOWER(p.prvNitMprv) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvmaeproveedoresma> searchByNit(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyPrvmaeproveedoresma p " +
            "WHERE LOWER(p.prvTipoproveedorMprv) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvmaeproveedoresma> searchByTipoProveedor(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyPrvmaeproveedoresma p WHERE p.prvEstadoregMprv = :status")
    Page<EntyPrvmaeproveedoresma> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query("SELECT p FROM EntyPrvmaeproveedoresma p " +
            "WHERE LOWER(p.prvIdentifkeyMprv) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.prvNitMprv) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.prvRazonsocialMprv) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.prvNombrecomercialMprv) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.prvTipoproveedorMprv) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.prvContactoMprv) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(p.prvCiudadMprv) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<EntyPrvmaeproveedoresma> searchByText(
            @Param("filter") String filter,
            Pageable pageable
    );
}