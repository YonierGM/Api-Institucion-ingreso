package institucion.java.angular.models.dao;

import institucion.java.angular.models.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IestudianteDao extends JpaRepository<Estudiante, Integer> {

}
