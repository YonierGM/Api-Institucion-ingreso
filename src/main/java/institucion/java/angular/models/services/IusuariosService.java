package institucion.java.angular.models.services;

import institucion.java.angular.models.entity.Estudiante;
import institucion.java.angular.models.entity.Usuarios;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IusuariosService {

    public Page<Usuarios> findAll(Pageable pageable);

    public List<Usuarios> findAll();

    public Usuarios findById(Long id);

    public Usuarios saveUsuarios(Usuarios usuarios);

    public void delete(Long id);
}
