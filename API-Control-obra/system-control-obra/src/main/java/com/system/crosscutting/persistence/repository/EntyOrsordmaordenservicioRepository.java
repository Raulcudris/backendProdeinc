package com.system.crosscutting.persistence.repository;

import java.util.Optional;
import com.system.crosscutting.persistence.entity.EntyOrsordmaordenservicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para consultar y administrar órdenes de servicio.
 */
@Repository
public interface EntyOrsordmaordenservicioRepository extends JpaRepository<EntyOrsordmaordenservicio, Integer> {

    /**
     * Busca una orden de servicio por su identificador funcional.
     *
     * @param orsIdentifkeyOrde código único funcional de la orden.
     * @return orden encontrada, si existe.
     */
    Optional<EntyOrsordmaordenservicio> findByOrsIdentifkeyOrde(String orsIdentifkeyOrde);
}