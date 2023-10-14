package institucion.java.angular.models.services;

import institucion.java.angular.models.entity.Asignatura;
import institucion.java.angular.models.entity.Estudiante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IestudianteService {


    public Page<Estudiante> findAll(Pageable pageable);

        public List<Estudiante> findAll();

    public Estudiante findById(Integer id);

    public Estudiante saveEstudiante(Estudiante estudiante);

    public void delete(Integer id);
}
