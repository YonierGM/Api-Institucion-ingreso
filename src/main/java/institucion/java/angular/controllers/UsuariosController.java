package institucion.java.angular.controllers;

import institucion.java.angular.models.dao.IusuariosDao;
import institucion.java.angular.models.entity.Usuarios;
import institucion.java.angular.models.services.IusuariosService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class UsuariosController {

    @Autowired
    private IusuariosService usuariosService;

    @Autowired
    private IusuariosDao iusuariosDao;

    @GetMapping("/usuarios")
    public List<Usuarios> index() {
        return usuariosService.findAll();
    }

    @GetMapping("/usuario/{email}/{password}")
    public List<Usuarios> index2(@PathVariable String email, @PathVariable String password) {
        return iusuariosDao.searcUser(email, password);
    }

    @GetMapping("/usuarios/page/{page}")
    public Page<Usuarios> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return usuariosService.findAll(pageable);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> showUsuarios(@PathVariable Long id) {

        Usuarios usuario = null;

        Map<String, Object> response = new HashMap<>();

        try {
            usuario = usuariosService.findById(id);

        } catch (DataAccessException e) {

            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (usuario == null) {

            response.put("mensaje",
                    "El usuario con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Usuarios>(usuario, HttpStatus.OK);
    }

    @PostMapping("/usuarios")
    // RequestBody ya que la respuesta es en formato json
    public ResponseEntity<?> createUsuarios(@Valid @RequestBody Usuarios usuario, BindingResult result) {

        Usuarios usuarioNew = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {

            usuarioNew = usuariosService.saveUsuarios(usuario);

        } catch (DataAccessException e) {

            response.put("mensaje", "Error al guardar los datos en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Usuario se ha creado con exito");
        response.put("usuario", usuarioNew);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> updateUsuario(@Valid @RequestBody Usuarios usuario, BindingResult result,
                                           @PathVariable Long id) {

        Usuarios usuarioActual = usuariosService.findById(id);
        Usuarios usuarioUpdate = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (usuarioActual == null) {

            response.put("mensaje", "No se pudo editar: el usuario con el ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            usuarioActual.setPassword(usuario.getPassword());
            usuarioActual.setEmail(usuario.getEmail());

            usuarioUpdate = usuariosService.saveUsuarios(usuarioActual);

        } catch (DataAccessException e) {

            response.put("mensaje", "Error al actualizar el usuario en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario se ha actualizado con exito");
        response.put("usuario", usuarioUpdate);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        Usuarios usuario = usuariosService.findById(id);

        if (usuario == null) {

            response.put("mensaje", "No se pudo eliminar: el usuario con el ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

        }else {
            try {

                usuariosService.delete(id);

            } catch (DataAccessException e) {

                response.put("mensaje", "Error al emiminar el usuario de la base de datos");
                response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        response.put("mensaje", "Registro eliminado con exito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
