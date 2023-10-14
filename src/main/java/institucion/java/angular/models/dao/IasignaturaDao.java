package institucion.java.angular.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import institucion.java.angular.models.entity.Asignatura;

public interface IasignaturaDao extends JpaRepository<Asignatura, Integer>{

}
