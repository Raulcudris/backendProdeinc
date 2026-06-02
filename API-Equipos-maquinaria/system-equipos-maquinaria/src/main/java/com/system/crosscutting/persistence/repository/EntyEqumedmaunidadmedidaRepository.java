package com.system.crosscutting.persistence.repository;
import com.system.crosscutting.persistence.entity.EntyEqumedmaunidadmedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


/**
 * Repositorio JPA para consultar y administrar unidades de medida.
 */
@Repository
public interface EntyEqumedmaunidadmedidaRepository extends JpaRepository<EntyEqumedmaunidadmedida, Integer> {

    /**
     * Busca una unidad de medida por su identificador funcional.
     *
     * @param equIdentifkeyUnme código único funcional de unidad de medida.
     * @return unidad de medida encontrada, si existe.
     */
    Optional<EntyEqumedmaunidadmedida> findByEquIdentifkeyUnme(String equIdentifkeyUnme);

    /**
     * Busca una unidad de medida por su código corto.
     *
     * @param equCodigoUnme código corto de unidad de medida.
     * @return unidad de medida encontrada, si existe.
     */
    Optional<EntyEqumedmaunidadmedida> findByEquCodigoUnme(String equCodigoUnme);
}
