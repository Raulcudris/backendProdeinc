package com.system.crosscutting.persistence.repository;
import com.system.crosscutting.persistence.entity.EntySispaisbestados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EntySispaisbestadosRepository extends JpaRepository<EntySispaisbestados,String>
{
    String FILTER_CODSTATE_QUERY = "SELECT c FROM EntySispaisbestados c WHERE c.sisIdedptSidp =?1";
    @Query(value = FILTER_CODSTATE_QUERY)
    EntySispaisbestados findByCodState(String filter);
}
