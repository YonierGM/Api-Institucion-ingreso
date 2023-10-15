package institucion.java.angular.models.dao;

import institucion.java.angular.models.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IusuariosDao extends JpaRepository<Usuarios, Long> {
}
