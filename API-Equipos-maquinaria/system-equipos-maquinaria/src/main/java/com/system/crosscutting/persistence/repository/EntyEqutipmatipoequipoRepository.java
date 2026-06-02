package com.system.crosscutting.persistence.repository;
import com.system.crosscutting.persistence.entity.EntyEqutipmatipoequipos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para consultar y administrar tipos de equipo.
 */
@Repository
public interface EntyEqutipmatipoequipoRepository extends JpaRepository<EntyEqutipmatipoequipos, Integer> {

    /**
     * Busca un tipo de equipo por su identificador funcional.
     *
     * @param equIdentifkeyTieq código único funcional del tipo de equipo.
     * @return tipo de equipo encontrado, si existe.
     */
    Optional<EntyEqutipmatipoequipos> findByEquIdentifkeyTieq(String equIdentifkeyTieq);
}
