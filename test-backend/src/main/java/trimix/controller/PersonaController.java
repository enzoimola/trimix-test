package trimix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import trimix.dto.PersonaDto;
import trimix.dto.Response;
import trimix.exception.EntityNotFoundException;
import trimix.service.PersonaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaS;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody PersonaDto personaDTO) throws Exception {
        Response response = personaS.save(personaDTO);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") int id) throws EntityNotFoundException, Exception {
        Response response = personaS.get(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping()
    public ResponseEntity<?> list() throws Exception {
        Response response = personaS.list();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody PersonaDto personaDTO) throws Exception {
		Response response = personaS.update(personaDTO);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) throws Exception {
		Response response = personaS.delete(id);
		return ResponseEntity.ok().body(response);
    }

}
