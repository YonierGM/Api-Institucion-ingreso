package institucion.java.angular.models.services;

import institucion.java.angular.models.entity.EstudianteAsignatura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IestudianteAsignaturaService {
    public Page<EstudianteAsignatura> findAll(Pageable pageable);

    public List<EstudianteAsignatura> findAll();

    public EstudianteAsignatura findById(Integer id);

    public EstudianteAsignatura saveEstudianteAsignatura(EstudianteAsignatura estudianteAsignatura);
}
