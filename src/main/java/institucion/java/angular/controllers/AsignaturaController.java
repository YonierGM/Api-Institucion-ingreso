package institucion.java.angular.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import institucion.java.angular.models.entity.Asignatura;
import institucion.java.angular.models.services.IasignaturaService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class AsignaturaController {

	@Autowired
	private IasignaturaService asignaturaService;

	@GetMapping("/asignaturas")
	public List<Asignatura> index() {
		return asignaturaService.findAll();
	}

	@GetMapping("/asignaturas/page/{page}")
	public Page<Asignatura> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 5);
		return asignaturaService.findAll(pageable);
	}

	@GetMapping("/asignaturas/{id}")
	public ResponseEntity<?> showAsignatura(@PathVariable Integer id) {

		Asignatura asignatura = null;

		Map<String, Object> response = new HashMap<>();

		try {
			asignatura = asignaturaService.findById(id);

		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (asignatura == null) {

			response.put("mensaje",
					"La asignatura con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Asignatura>(asignatura, HttpStatus.OK);
	}

	@PostMapping("/asignaturas")
	// RequestBody ya que la respuesta es en formato json
	public ResponseEntity<?> createAsignatura(@Valid @RequestBody Asignatura asignatura, BindingResult result) {

		Asignatura asignaturaNew = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {

			asignaturaNew = asignaturaService.saveAsignatura(asignatura);

		} catch (DataAccessException e) {

			response.put("mensaje", "Error al guardar los datos en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente se ha creado con exito");
		response.put("cliente", asignaturaNew);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/asignaturas/{id}")
	public ResponseEntity<?> updateCliente(@Valid @RequestBody Asignatura asignatura, BindingResult result,
			@PathVariable Integer id) {

		Asignatura asignaturaActual = asignaturaService.findById(id);
		Asignatura asignaturaUpdate = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (asignaturaActual == null) {

			response.put("mensaje", "No se pudo editar: La asignatura con el ID: "
					.concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			asignaturaActual.setDescripcion(asignatura.getDescripcion());
			asignaturaActual.setId(asignatura.getId());

			asignaturaUpdate = asignaturaService.saveAsignatura(asignaturaActual);

		} catch (DataAccessException e) {

			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La asignatura se ha actualizado con exito");
		response.put("cliente", asignaturaUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/asignaturas/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		 
		Map<String, Object> response = new HashMap<>();
		Asignatura asignatura = asignaturaService.findById(id);
		
		if (asignatura == null) {
			
			response.put("mensaje", "No se pudo eliminar: la asignatura con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			
		}else {
			try {
				
				asignaturaService.delete(id);
				
			} catch (DataAccessException e) {
				
				response.put("mensaje", "Error al emiminar la asignatura de la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		response.put("mensaje", "Registro eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
