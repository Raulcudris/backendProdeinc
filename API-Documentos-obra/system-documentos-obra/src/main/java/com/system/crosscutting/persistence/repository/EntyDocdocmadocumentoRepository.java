package com.system.crosscutting.persistence.repository;
import com.system.crosscutting.persistence.entity.EntyDocdocmadocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para consultar y administrar documentos de obra.
 */
@Repository
public interface EntyDocdocmadocumentoRepository extends JpaRepository<EntyDocdocmadocumento, Integer> {

    /**
     * Busca un documento por su identificador funcional.
     *
     * @param docIdentifkeyDocu código único funcional del documento.
     * @return documento encontrado, si existe.
     */
    Optional<EntyDocdocmadocumento> findByDocIdentifkeyDocu(String docIdentifkeyDocu);

    /**
     * Consulta documentos por tipo documental.
     *
     * @param docIdentifkeyTido código único funcional del tipo documental.
     * @return lista de documentos asociados al tipo.
     */
    List<EntyDocdocmadocumento> findByDocIdentifkeyTido(String docIdentifkeyTido);

    /**
     * Consulta documentos por tipo de referencia.
     *
     * @param docTiporeferenDocu tipo de referencia.
     * @return lista de documentos asociados al tipo de referencia.
     */
    List<EntyDocdocmadocumento> findByDocTiporeferenDocu(String docTiporeferenDocu);

    /**
     * Consulta documentos por tipo de referencia e identificador de referencia.
     *
     * @param docTiporeferenDocu tipo de referencia.
     * @param docReferenciaidDocu identificador del registro referenciado.
     * @return lista de documentos asociados a la referencia.
     */
    List<EntyDocdocmadocumento> findByDocTiporeferenDocuAndDocReferenciaidDocu(String docTiporeferenDocu, String docReferenciaidDocu);

    /**
     * Consulta documentos con fecha de vencimiento menor o igual a la fecha indicada.
     *
     * @param fecha fecha límite de vencimiento.
     * @return lista de documentos encontrados.
     */
    List<EntyDocdocmadocumento> findByDocFechavenceDocuLessThanEqual(LocalDate fecha);
}