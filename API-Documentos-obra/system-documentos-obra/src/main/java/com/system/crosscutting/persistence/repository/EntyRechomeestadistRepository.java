package com.system.crosscutting.persistence.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.system.crosscutting.persistence.entity.EntyRechomeestadist;

public interface EntyRechomeestadistRepository  extends JpaRepository<EntyRechomeestadist,Integer> {

    EntyRechomeestadist findByRecIdentifkeyRhes(int id);

    /*
    String FILTER_ESTADIST_RECUNIKEYREUS_QUERY = "select c from EntyRechomeestadist c  where c.recIdenumkeyRhes = :id and c.recStatusregiRhes=1";
    @Query(value = FILTER_ESTADIST_RECUNIKEYREUS_QUERY)
    List<EntyRechomeestadist> findByRecIdenumkeyRhes(@Param("id") String id);
    */

    String FILTER_ESTADIST_APJIDEUNIKEYAPHP_QUERY = "SELECT c FROM EntyRechomeestadist c "+
                                                    "WHERE c.recIdenumkeyRhes =:idGestion";
    @Query(value = FILTER_ESTADIST_APJIDEUNIKEYAPHP_QUERY)
    List<EntyRechomeestadist> findByRecIdenumkeyRhes(@Param("idGestion") String idGestion);

}
