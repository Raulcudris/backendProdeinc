package com.system.crosscutting.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.system.crosscutting.persistence.entity.EntyRecmaesusuarima;

public interface EntyRecmaesusuarimaRepository extends JpaRepository<EntyRecmaesusuarima, Integer> {

    String SEARCH_BY_TEXT_QUERY =
            "SELECT c " +
                    "FROM EntyRecmaesusuarima c " +
                    "WHERE " +
                    "   LOWER(COALESCE(c.recNroregReus, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(c.recNiknamReus, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(c.recNombreReus, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(c.recApelidReus, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(c.recNomusuReus, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(c.apjCorreoApgm, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(c.recNroideReus, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(c.recTelefoReus, '')) LIKE LOWER(CONCAT('%', :filter, '%'))";
    @Query(value = SEARCH_BY_TEXT_QUERY)
    Page<EntyRecmaesusuarima> searchByText( @Param("filter") String filter, Pageable pageable);


    String SEARCH_BY_TEXT_AND_STATUS_QUERY =
            "SELECT c " +
                    "FROM EntyRecmaesusuarima c " +
                    "WHERE c.recEstregReus = :status " +
                    "AND ( " +
                    "   LOWER(COALESCE(c.recNroregReus, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(c.recNiknamReus, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(c.recNombreReus, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(c.recApelidReus, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(c.recNomusuReus, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(c.apjCorreoApgm, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(c.recNroideReus, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(COALESCE(c.recTelefoReus, '')) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    ")";
    @Query(value = SEARCH_BY_TEXT_AND_STATUS_QUERY)
    Page<EntyRecmaesusuarima> searchByTextAndStatus(@Param("filter") String filter, @Param("status") String status,Pageable pageable);

    String SEARCH_BY_PRIMARY_KEY_QUERY = "SELECT c FROM EntyRecmaesusuarima c WHERE c.recIdeunikeyReus = :id";
    @Query(value = SEARCH_BY_PRIMARY_KEY_QUERY)
    Page<EntyRecmaesusuarima> searchByPrimaryKey(@Param("id") Integer id,Pageable pageable);

    String SEARCH_BY_REGISTER_KEY_QUERY = "SELECT c " +"FROM EntyRecmaesusuarima c "+"WHERE LOWER(COALESCE(c.recNroregReus, '')) LIKE LOWER(CONCAT('%', :filter, '%'))";

    String SEARCH_BY_STATUS_QUERY ="SELECT c FROM EntyRecmaesusuarima c WHERE c.recEstregReus = :status";

    String SEARCH_BY_EMAIL_QUERY =
            "SELECT c " +
                    "FROM EntyRecmaesusuarima c " +
                    "WHERE LOWER(COALESCE(c.apjCorreoApgm, '')) LIKE LOWER(CONCAT('%', :filter, '%'))";

    String SEARCH_BY_DOCUMENT_QUERY =
            "SELECT c " +
                    "FROM EntyRecmaesusuarima c " +
                    "WHERE LOWER(COALESCE(c.recNroideReus, '')) LIKE LOWER(CONCAT('%', :filter, '%'))";

    String SEARCH_BY_PHONE_QUERY =
            "SELECT c " +
                    "FROM EntyRecmaesusuarima c " +
                    "WHERE LOWER(COALESCE(c.recTelefoReus, '')) LIKE LOWER(CONCAT('%', :filter, '%'))";






    @Query(value = SEARCH_BY_REGISTER_KEY_QUERY)
    Page<EntyRecmaesusuarima> searchByRegisterKey(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(value = SEARCH_BY_STATUS_QUERY)
    Page<EntyRecmaesusuarima> searchByStatus(
            @Param("status") String status,
            Pageable pageable
    );

    @Query(value = SEARCH_BY_EMAIL_QUERY)
    Page<EntyRecmaesusuarima> searchByEmail(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(value = SEARCH_BY_DOCUMENT_QUERY)
    Page<EntyRecmaesusuarima> searchByDocument(
            @Param("filter") String filter,
            Pageable pageable
    );

    @Query(value = SEARCH_BY_PHONE_QUERY)
    Page<EntyRecmaesusuarima> searchByPhone(
            @Param("filter") String filter,
            Pageable pageable
    );
}