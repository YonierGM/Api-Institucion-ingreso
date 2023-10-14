package institucion.java.angular.models.services;

import institucion.java.angular.models.dao.IestudianteAsignaturaDao;
import institucion.java.angular.models.entity.EstudianteAsignatura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstudianteAsignaturaServiceImpl implements IestudianteAsignaturaService{

    @Autowired
    private IestudianteAsignaturaDao estudianteA;

    @Override
    @Transactional(readOnly = true)
    public Page<EstudianteAsignatura> findAll(Pageable pageable) {
        return estudianteA.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteAsignatura> findAll() {
        return (List<EstudianteAsignatura>) estudianteA.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public EstudianteAsignatura findById(Integer id) {
        return estudianteA.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public EstudianteAsignatura saveEstudianteAsignatura(EstudianteAsignatura estudianteAsignatura) {
        return estudianteA.save(estudianteAsignatura);
    }
}
