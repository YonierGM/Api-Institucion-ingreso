package institucion.java.angular.models.dao;

import institucion.java.angular.models.entity.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdocenteDao extends JpaRepository<Docente, Integer> {
}
