package com.system.crosscutting.persistence.repository;
import com.system.crosscutting.persistence.entity.EntySispaisamaestro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EntySispaisamaestroRepository  extends JpaRepository<EntySispaisamaestro,String>
{
    String FILTER_CODCOUNTRY_QUERY = "SELECT c FROM EntySispaisamaestro c WHERE c.sisCodpaiSipa =?1";
    @Query(value = FILTER_CODCOUNTRY_QUERY)
    EntySispaisamaestro findByCodCountry(String filter);
}
