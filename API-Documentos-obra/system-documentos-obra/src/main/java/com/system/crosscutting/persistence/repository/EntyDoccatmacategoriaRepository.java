package com.system.crosscutting.persistence.repository;
import com.system.crosscutting.persistence.entity.EntyDoccatmacategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio JPA para consultar y administrar categorías documentales.
 */
@Repository
public interface EntyDoccatmacategoriaRepository extends JpaRepository<EntyDoccatmacategoria, Integer> {

    /**
     * Busca una categoría documental por su identificador funcional.
     *
     * @param docIdentifkeyCado código único funcional de categoría documental.
     * @return categoría encontrada, si existe.
     */
    Optional<EntyDoccatmacategoria> findByDocIdentifkeyCado(String docIdentifkeyCado);
}
