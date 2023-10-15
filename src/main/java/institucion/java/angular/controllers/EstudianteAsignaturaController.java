package institucion.java.angular.controllers;
import institucion.java.angular.models.entity.EstudianteAsignatura;
import institucion.java.angular.models.services.IestudianteAsignaturaService;
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
public class EstudianteAsignaturaController {
    @Autowired
    private IestudianteAsignaturaService estudianteAsignaturaService;

    @GetMapping("/estudianteAsignatura")
    public List<EstudianteAsignatura> index() {return estudianteAsignaturaService.findAll();}

    @GetMapping("/estudianteAsignatura/page/{page}")
    public Page<EstudianteAsignatura> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return estudianteAsignaturaService.findAll(pageable);
    }

    @PostMapping("/estudianteAsignatura")
    // RequestBody ya que la respuesta es en formato json
    public ResponseEntity<?> createEstudianteAsignatura(@Valid @RequestBody EstudianteAsignatura estudiantea, BindingResult result) {

        EstudianteAsignatura estudianteANew = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {

            estudianteANew = estudianteAsignaturaService.saveEstudianteAsignatura(estudiantea);

        } catch (DataAccessException e) {

            response.put("mensaje", "Error al guardar los datos en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Se ha vinculado con exito");
        response.put("estudianteAsignatura", estudianteANew);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
