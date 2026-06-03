package com.system.crosscutting.persistence.repository;

import com.system.crosscutting.persistence.entity.EntyEvirefmdreferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para consultar y administrar referencias de evidencias.
 */
@Repository
public interface EntyEvirefmdreferenciaRepository extends JpaRepository<EntyEvirefmdreferencia, Integer> {

    /**
     * Busca una referencia de evidencia por su identificador funcional.
     *
     * @param eviIdentifkeyEvre código único funcional de la referencia.
     * @return referencia encontrada, si existe.
     */
    Optional<EntyEvirefmdreferencia> findByEviIdentifkeyEvre(String eviIdentifkeyEvre);

    /**
     * Consulta referencias asociadas a una evidencia.
     *
     * @param eviIdentifkeyEvid código único funcional de la evidencia.
     * @return lista de referencias asociadas a la evidencia.
     */
    List<EntyEvirefmdreferencia> findByEviIdentifkeyEvid(String eviIdentifkeyEvid);

    /**
     * Consulta referencias por tipo de referencia.
     *
     * @param eviTiporeferenEvre tipo de referencia.
     * @return lista de referencias asociadas al tipo.
     */
    List<EntyEvirefmdreferencia> findByEviTiporeferenEvre(String eviTiporeferenEvre);

    /**
     * Consulta referencias por tipo y código del registro referenciado.
     *
     * @param eviTiporeferenEvre tipo de referencia.
     * @param eviReferenciaidEvre código del registro referenciado.
     * @return lista de referencias encontradas.
     */
    List<EntyEvirefmdreferencia> findByEviTiporeferenEvreAndEviReferenciaidEvre(String eviTiporeferenEvre, String eviReferenciaidEvre);

    /**
     * Consulta referencias por estado de registro.
     *
     * @param eviEstadoregEvre estado del registro.
     * @return lista de referencias asociadas al estado.
     */
    List<EntyEvirefmdreferencia> findByEviEstadoregEvre(String eviEstadoregEvre);
}
