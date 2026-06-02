package com.system.modules.documentosobra.api;
import com.system.crosscutting.domain.model.EntyDoccatmacategoriaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.documentosobra.usecase.EntyCategoriaDocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Controlador REST para consultar categorías documentales.
 */
@RestController
@RequiredArgsConstructor
public class EntyCategoriaDocumentoWebApi {

    private final EntyCategoriaDocumentoService service;

    /**
     * Consulta todas las categorías documentales.
     *
     * @return listado de categorías documentales.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/documentos-obra/categorias/pages")
    public ResponseEntity<List<EntyDoccatmacategoriaDto>> findAll() throws EBusinessException {
        return ResponseEntity.ok(service.findAll());
    }
}
