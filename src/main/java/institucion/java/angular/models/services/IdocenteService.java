package institucion.java.angular.models.services;

import institucion.java.angular.models.entity.Asignatura;
import institucion.java.angular.models.entity.Docente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IdocenteService {


    public Page<Docente> findAll(Pageable pageable);

    public List<Docente> findAll();

    public Docente findById(Integer id);

    public Docente saveDocente(Docente docente);

    public void delete(Integer id);

}
