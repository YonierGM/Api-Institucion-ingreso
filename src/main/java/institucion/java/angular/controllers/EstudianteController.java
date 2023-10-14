package institucion.java.angular.controllers;

import institucion.java.angular.models.entity.Estudiante;
import institucion.java.angular.models.services.IestudianteService;
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
public class EstudianteController {
    @Autowired
    private IestudianteService estudianteService;

    @GetMapping("/estudiantes")
    public List<Estudiante> index() {
        return estudianteService.findAll();
    }

    @GetMapping("/estudiantes/page/{page}")
    public Page<Estudiante> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return estudianteService.findAll(pageable);
    }

    @GetMapping("/estudiantes/{id}")
    public ResponseEntity<?> showEstudiantes(@PathVariable Integer id) {

        Estudiante estudiante = null;

        Map<String, Object> response = new HashMap<>();

        try {
            estudiante = estudianteService.findById(id);

        } catch (DataAccessException e) {

            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (estudiante == null) {

            response.put("mensaje",
                    "El estudiante con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Estudiante>(estudiante, HttpStatus.OK);
    }

    @PostMapping("/estudiantes")
    // RequestBody ya que la respuesta es en formato json
    public ResponseEntity<?> createEstudiante(@Valid @RequestBody Estudiante estudiante, BindingResult result) {

        Estudiante estudianteNew = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {

            estudianteNew = estudianteService.saveEstudiante(estudiante);

        } catch (DataAccessException e) {

            response.put("mensaje", "Error al guardar los datos en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El estudiante se ha creado con exito");
        response.put("estudiante", estudianteNew);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/estudiantes/{id}")
    public ResponseEntity<?> updateEstudiante(@Valid @RequestBody Estudiante estudiante, BindingResult result,
                                           @PathVariable Integer id) {

        Estudiante estudianteActual = estudianteService.findById(id);
        Estudiante estudianteUpdate = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (estudianteActual == null) {

            response.put("mensaje", "No se pudo editar: El estudiante con el ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            estudianteActual.setTipodocumento(estudiante.getTipodocumento());
            estudianteActual.setNumerodocumento(estudiante.getNumerodocumento());
            estudianteActual.setNombre(estudiante.getNombre());
            estudianteActual.setApellidos(estudiante.getApellidos());

            estudianteActual.setFecha_nacimiento(estudiante.getFecha_nacimiento());
            estudianteActual.setGrado(estudiante.getGrado());
            estudianteActual.setDane(estudiante.getDane());

            estudianteActual.setDireccion(estudiante.getDireccion());
            estudianteActual.setEmail(estudiante.getEmail());
            estudianteActual.setFijo(estudiante.getFijo());
            estudianteActual.setCelular(estudiante.getCelular());

            estudianteUpdate = estudianteService.saveEstudiante(estudianteActual);

        } catch (DataAccessException e) {

            response.put("mensaje", "Error al actualizar el estudiante en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El estudiante se ha actualizado con exito");
        response.put("estudiante", estudianteUpdate);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/estudiantes/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        Map<String, Object> response = new HashMap<>();
        Estudiante estudiante = estudianteService.findById(id);

        if (estudiante == null) {

            response.put("mensaje", "No se pudo eliminar: El estudiante con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

        }else {
            try {

                estudianteService.delete(id);

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
