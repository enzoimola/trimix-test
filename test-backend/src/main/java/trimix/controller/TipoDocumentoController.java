package trimix.controller;

import trimix.dto.Response;
import trimix.exception.EntityNotFoundException;
import trimix.model.TipoDocumento;
import trimix.service.TipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/documentos")
public class TipoDocumentoController {

    @Autowired
    private TipoDocumentoService tipoDocumentoS;

    @GetMapping("/{id}")
    public ResponseEntity<TipoDocumento> get(@PathVariable("id") int id) throws EntityNotFoundException, Exception {
        TipoDocumento tipoDocumento = tipoDocumentoS.get(id);
        return ResponseEntity.ok().body(tipoDocumento);
    }

    @GetMapping()
    public ResponseEntity<?> list() throws Exception {
        Response response = tipoDocumentoS.list();
        return ResponseEntity.ok().body(response);
    }

}
