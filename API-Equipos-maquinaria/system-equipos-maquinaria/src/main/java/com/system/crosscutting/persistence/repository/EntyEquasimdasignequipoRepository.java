package com.system.crosscutting.persistence.repository;
import com.system.crosscutting.persistence.entity.EntyEquasimdasignequipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para consultar y administrar asignaciones de equipos.
 */
@Repository
public interface EntyEquasimdasignequipoRepository extends JpaRepository<EntyEquasimdasignequipo, Integer> {

    /**
     * Busca una asignación de equipo por su identificador funcional.
     *
     * @param equIdentifkeyAseq código único funcional de la asignación.
     * @return asignación encontrada, si existe.
     */
    Optional<EntyEquasimdasignequipo> findByEquIdentifkeyAseq(String equIdentifkeyAseq);

    /**
     * Consulta asignaciones por equipo.
     *
     * @param equIdentifkeyEqui código único funcional del equipo.
     * @return lista de asignaciones del equipo.
     */
    List<EntyEquasimdasignequipo> findByEquIdentifkeyEqui(String equIdentifkeyEqui);

    /**
     * Consulta asignaciones por orden de servicio.
     *
     * @param orsIdentifkeyOrde código único funcional de la orden.
     * @return lista de asignaciones asociadas a la orden.
     */
    List<EntyEquasimdasignequipo> findByOrsIdentifkeyOrde(String orsIdentifkeyOrde);

    /**
     * Consulta asignaciones por plan de trabajo.
     *
     * @param orsIdentifkeyPltr código único funcional del plan.
     * @return lista de asignaciones asociadas al plan.
     */
    List<EntyEquasimdasignequipo> findByOrsIdentifkeyPltr(String orsIdentifkeyPltr);

    /**
     * Consulta asignaciones por estado de registro.
     *
     * @param equEstadoregAseq estado del registro.
     * @return lista de asignaciones asociadas al estado.
     */
    List<EntyEquasimdasignequipo> findByEquEstadoregAseq(String equEstadoregAseq);
}