package com.system.crosscutting.persistence.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.system.crosscutting.persistence.entity.EntySispaisciudadma;

public interface EntySispaisciudadmaRepository extends JpaRepository<EntySispaisciudadma,String>
{
    String FILTER_CODCITY_QUERY = "SELECT c FROM EntySispaisciudadma c WHERE c.sisCodmunSimu =?1";
    @Query(value = FILTER_CODCITY_QUERY)
    EntySispaisciudadma findByKeyCity(String filter);
}
