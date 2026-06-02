package com.system.crosscutting.persistence.repository;
import com.system.crosscutting.persistence.entity.EntyEquinvmaequipos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para consultar y administrar equipos, maquinaria,
 * vehículos y herramientas.
 */
@Repository
public interface EntyEquinvmaequiposRepository extends JpaRepository<EntyEquinvmaequipos, Integer> {

    /**
     * Busca un equipo por su identificador funcional.
     *
     * @param equIdentifkeyEqui código único funcional del equipo.
     * @return equipo encontrado, si existe.
     */
    Optional<EntyEquinvmaequipos> findByEquIdentifkeyEqui(String equIdentifkeyEqui);

    /**
     * Consulta equipos por tipo.
     *
     * @param equIdentifkeyTieq código único funcional del tipo de equipo.
     * @return lista de equipos asociados al tipo.
     */
    List<EntyEquinvmaequipos> findByEquIdentifkeyTieq(String equIdentifkeyTieq);

    /**
     * Consulta equipos por estado operativo.
     *
     * @param equEstadooperEqui estado operativo del equipo.
     * @return lista de equipos asociados al estado operativo.
     */
    List<EntyEquinvmaequipos> findByEquEstadooperEqui(String equEstadooperEqui);

    /**
     * Consulta equipos por proveedor.
     *
     * @param prvIdentifkeyMprv código único funcional del proveedor.
     * @return lista de equipos asociados al proveedor.
     */
    List<EntyEquinvmaequipos> findByPrvIdentifkeyMprv(String prvIdentifkeyMprv);
}
