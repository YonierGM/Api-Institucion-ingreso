package institucion.java.angular.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import institucion.java.angular.models.dao.IasignaturaDao;
import institucion.java.angular.models.entity.Asignatura;


@Service
public class AsignaturaServiceImpl implements IasignaturaService{
	
	@Autowired
	private IasignaturaDao asignaturaDao;

	@Override
	@Transactional(readOnly = true)
	public Page<Asignatura> findAll(Pageable pageable) {
		return asignaturaDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Asignatura> findAll() {
		return (List<Asignatura>) asignaturaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Asignatura findById(Integer id) {
		return asignaturaDao.findById(id).orElse(null); //Si no lo encuentra retorna null
	}

	@Override
	@Transactional()
	public Asignatura saveAsignatura(Asignatura asignatura) {
		return asignaturaDao.save(asignatura);
	}

	@Override
	@Transactional()
	public void delete(Integer id) {
		asignaturaDao.deleteById(id);
	}
}
