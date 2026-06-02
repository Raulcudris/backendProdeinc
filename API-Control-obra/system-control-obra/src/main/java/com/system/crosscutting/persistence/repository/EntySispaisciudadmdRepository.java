package com.system.crosscutting.persistence.repository;

import com.system.crosscutting.persistence.entity.EntySispaisciudadmd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EntySispaisciudadmdRepository extends JpaRepository<EntySispaisciudadmd,String>
{
    String FILTER_CODCITY_QUERY = "SELECT c FROM EntySispaisciudadmd c WHERE c.sisCodproSipr =?1";
    @Query(value = FILTER_CODCITY_QUERY)
    EntySispaisciudadmd findByCodCity(String filter);
}
