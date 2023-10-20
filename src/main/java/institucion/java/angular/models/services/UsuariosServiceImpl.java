package institucion.java.angular.models.services;

import institucion.java.angular.models.dao.IusuariosDao;
import institucion.java.angular.models.entity.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuariosServiceImpl implements IusuariosService{

    @Autowired
    private IusuariosDao userDao;

    @Override
    @Transactional(readOnly = true)
    public Page<Usuarios> findAll(Pageable pageable) {
        return userDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuarios> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuarios findById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public Usuarios saveUsuarios(Usuarios usuarios) {
        return userDao.save(usuarios);
    }

    @Override
    @Transactional()
    public void delete(Long id) {
        userDao.deleteById(id);

    }
}
