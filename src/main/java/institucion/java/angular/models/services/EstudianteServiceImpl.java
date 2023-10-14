package institucion.java.angular.models.services;

import institucion.java.angular.models.dao.IasignaturaDao;
import institucion.java.angular.models.dao.IestudianteDao;
import institucion.java.angular.models.entity.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstudianteServiceImpl implements IestudianteService{

    @Autowired
    private IestudianteDao estudianteDao;

    @Override
    @Transactional(readOnly = true)
    public Page<Estudiante> findAll(Pageable pageable) {
        return estudianteDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> findAll() {
        return (List<Estudiante>) estudianteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Estudiante findById(Integer id) {
        return estudianteDao.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public Estudiante saveEstudiante(Estudiante estudiante) {
        return estudianteDao.save(estudiante);
    }

    @Override
    @Transactional()
    public void delete(Integer id) {
        estudianteDao.deleteById(id);

    }
}
