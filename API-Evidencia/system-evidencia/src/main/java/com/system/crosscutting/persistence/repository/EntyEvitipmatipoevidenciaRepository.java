package com.system.crosscutting.persistence.repository;

import java.util.Optional;

import com.system.crosscutting.persistence.entity.EntyEvitipmatipoevidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para consultar y administrar tipos de evidencia.
 */
@Repository
public interface EntyEvitipmatipoevidenciaRepository extends JpaRepository<EntyEvitipmatipoevidencia, Integer> {

    /**
     * Busca un tipo de evidencia por su identificador funcional.
     *
     * @param eviIdentifkeyTiev código único funcional del tipo de evidencia.
     * @return tipo de evidencia encontrado, si existe.
     */
    Optional<EntyEvitipmatipoevidencia> findByEviIdentifkeyTiev(String eviIdentifkeyTiev);
}