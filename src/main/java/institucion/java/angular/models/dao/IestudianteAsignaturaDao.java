package institucion.java.angular.models.dao;

import institucion.java.angular.models.entity.EstudianteAsignatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface IestudianteAsignaturaDao extends JpaRepository<EstudianteAsignatura, Serializable> {
}
