package institucion.java.angular.controllers;

import institucion.java.angular.models.entity.Docente;
import institucion.java.angular.models.entity.Estudiante;
import institucion.java.angular.models.services.IdocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class DocenteController {

    @Autowired
    private IdocenteService docenteService;

    @GetMapping("/docentes")
    public List<Docente> index() {
        return docenteService.findAll();
    }

    @GetMapping("/docentes/page/{page}")
    public Page<Docente> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return docenteService.findAll(pageable);
    }

    @GetMapping("/docentes/{id}")
    public ResponseEntity<?> showDocentes(@PathVariable Integer id) {

        Docente docente = null;

        Map<String, Object> response = new HashMap<>();

        try {
            docente = docenteService.findById(id);

        } catch (DataAccessException e) {

            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (docente == null) {

            response.put("mensaje",
                    "El docente con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Docente>(docente, HttpStatus.OK);
    }

    @PostMapping("/docentes")
    // RequestBody ya que la respuesta es en formato json
    public ResponseEntity<?> createDocente(@Valid @RequestBody Docente docente, BindingResult result) {

        Docente docenteNew = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {

            docenteNew = docenteService.saveDocente(docente);

        } catch (DataAccessException e) {

            response.put("mensaje", "Error al guardar los datos en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El docente se ha creado con exito");
        response.put("docente", docenteNew);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/docentes/{id}")
    public ResponseEntity<?> updateDocente(@Valid @RequestBody Docente docente, BindingResult result,
                                              @PathVariable Integer id) {

        Docente docenteActual = docenteService.findById(id);
        Docente docenteUpdate = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (docenteActual == null) {

            response.put("mensaje", "No se pudo editar: El docente con el ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            docenteActual.setTipodocumento(docente.getTipodocumento());
            docenteActual.setNumerodocumento(docente.getNumerodocumento());
            docenteActual.setNombres(docente.getNombres());
            docenteActual.setApellidos(docente.getApellidos());

            docenteActual.setFecha_nacimiento(docente.getFecha_nacimiento());
            docenteActual.setAsig_dictadas(docente.getAsig_dictadas());
            docenteActual.setGrado_escolaridad(docente.getGrado_escolaridad());

            docenteActual.setGrado(docente.getGrado());
            docenteActual.setEmail(docente.getEmail());
            docenteActual.setFijo(docente.getFijo());
            docenteActual.setCelular(docente.getCelular());

            docenteUpdate = docenteService.saveDocente(docenteActual);

        } catch (DataAccessException e) {

            response.put("mensaje", "Error al actualizar el docente en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El docente se ha actualizado con exito");
        response.put("docente", docenteUpdate);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/docentes/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        Map<String, Object> response = new HashMap<>();
        Docente docente = docenteService.findById(id);

        if (docente == null) {

            response.put("mensaje", "No se pudo eliminar: El docente con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

        }else {
            try {

                docenteService.delete(id);

            } catch (DataAccessException e) {

                response.put("mensaje", "Error al emiminar el estudiante de la base de datos");
                response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        response.put("mensaje", "Registro eliminado con exito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
