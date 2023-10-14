package institucion.java.angular.models.services;

import institucion.java.angular.models.dao.IdocenteDao;
import institucion.java.angular.models.dao.IestudianteDao;
import institucion.java.angular.models.entity.Docente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IdocenteServiceImpl implements IdocenteService{

    @Autowired
    private IdocenteDao docenteDao;

    @Override
    @Transactional(readOnly = true)
    public Page<Docente> findAll(Pageable pageable) {
        return docenteDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Docente> findAll() {
        return (List<Docente>) docenteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Docente findById(Integer id) {
        return docenteDao.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public Docente saveDocente(Docente docente) {
        return docenteDao.save(docente);
    }

    @Override
    @Transactional()
    public void delete(Integer id) {
        docenteDao.deleteById(id);
    }
}
