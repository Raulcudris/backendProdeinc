package com.system.crosscutting.persistence.repository;
import com.system.crosscutting.persistence.entity.EntyDocvenmdvencimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para consultar y administrar vencimientos documentales.
 */
@Repository
public interface EntyDocvenmdvencimientoRepository extends JpaRepository<EntyDocvenmdvencimiento, Integer> {

    /**
     * Busca un vencimiento documental por su identificador funcional.
     *
     * @param docIdentifkeyVedo código único funcional del vencimiento documental.
     * @return vencimiento documental encontrado, si existe.
     */
    Optional<EntyDocvenmdvencimiento> findByDocIdentifkeyVedo(String docIdentifkeyVedo);

    /**
     * Consulta vencimientos asociados a un documento.
     *
     * @param docIdentifkeyDocu código único funcional del documento.
     * @return lista de vencimientos asociados al documento.
     */
    List<EntyDocvenmdvencimiento> findByDocIdentifkeyDocu(String docIdentifkeyDocu);

    /**
     * Consulta vencimientos por estado.
     *
     * @param docEstadovencVedo estado del vencimiento.
     * @return lista de vencimientos asociados al estado.
     */
    List<EntyDocvenmdvencimiento> findByDocEstadovencVedo(String docEstadovencVedo);

    /**
     * Consulta vencimientos con fecha menor o igual a la fecha indicada.
     *
     * @param fecha fecha límite.
     * @return lista de vencimientos encontrados.
     */
    List<EntyDocvenmdvencimiento> findByDocFechavenceVedoLessThanEqual(LocalDate fecha);

    /**
     * Consulta vencimientos en un rango de fechas.
     *
     * @param fechaInicio fecha inicial.
     * @param fechaFin fecha final.
     * @return lista de vencimientos encontrados.
     */
    List<EntyDocvenmdvencimiento> findByDocFechavenceVedoBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
