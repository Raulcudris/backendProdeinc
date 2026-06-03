package com.system.crosscutting.persistence.repository;
import com.system.crosscutting.persistence.entity.EntyEvievimaevidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para consultar y administrar evidencias.
 */
@Repository
public interface EntyEvievimaevidenciaRepository extends JpaRepository<EntyEvievimaevidencia, Integer> {

    /**
     * Busca una evidencia por su identificador funcional.
     *
     * @param eviIdentifkeyEvid código único funcional de la evidencia.
     * @return evidencia encontrada, si existe.
     */
    Optional<EntyEvievimaevidencia> findByEviIdentifkeyEvid(String eviIdentifkeyEvid);

    /**
     * Consulta evidencias por tipo de evidencia.
     *
     * @param eviIdentifkeyTiev código único funcional del tipo de evidencia.
     * @return lista de evidencias asociadas al tipo.
     */
    List<EntyEvievimaevidencia> findByEviIdentifkeyTiev(String eviIdentifkeyTiev);

    /**
     * Consulta evidencias por usuario creador.
     *
     * @param eviUsuariocreaEvid usuario que registró la evidencia.
     * @return lista de evidencias asociadas al usuario.
     */
    List<EntyEvievimaevidencia> findByEviUsuariocreaEvid(String eviUsuariocreaEvid);

    /**
     * Consulta evidencias por estado de registro.
     *
     * @param eviEstadoregEvid estado del registro.
     * @return lista de evidencias asociadas al estado.
     */
    List<EntyEvievimaevidencia> findByEviEstadoregEvid(String eviEstadoregEvid);
}
