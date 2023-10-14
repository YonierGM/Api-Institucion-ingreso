package institucion.java.angular.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import institucion.java.angular.models.entity.Asignatura;


public interface IasignaturaService {
	
	public Page<Asignatura> findAll(Pageable pageable);
	
	public List<Asignatura> findAll();
	
	public Asignatura findById(Integer id);
	
	public Asignatura saveAsignatura(Asignatura asignatura);
	
	public void delete(Integer id); 

}
