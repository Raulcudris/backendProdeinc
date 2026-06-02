package com.system.crosscutting.persistence.repository;
import com.system.crosscutting.persistence.entity.EntyDoctipmatipodocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para consultar y administrar tipos de documentos.
 */
@Repository
public interface EntyDoctipmatipodocumentoRepository extends JpaRepository<EntyDoctipmatipodocumento, Integer> {

    /**
     * Busca un tipo de documento por su identificador funcional.
     *
     * @param docIdentifkeyTido código único funcional del tipo de documento.
     * @return tipo de documento encontrado, si existe.
     */
    Optional<EntyDoctipmatipodocumento> findByDocIdentifkeyTido(String docIdentifkeyTido);

    /**
     * Consulta tipos de documento por categoría documental.
     *
     * @param docIdentifkeyCado código único funcional de categoría documental.
     * @return lista de tipos de documento asociados a la categoría.
     */
    List<EntyDoctipmatipodocumento> findByDocIdentifkeyCado(String docIdentifkeyCado);

    /**
     * Consulta tipos de documento según si requieren vencimiento.
     *
     * @param docRequievenceTido indicador de vencimiento: 1=Sí, 2=No.
     * @return lista de tipos de documento.
     */
    List<EntyDoctipmatipodocumento> findByDocRequievenceTido(String docRequievenceTido);
}
