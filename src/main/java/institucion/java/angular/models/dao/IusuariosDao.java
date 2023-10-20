package institucion.java.angular.models.dao;

import institucion.java.angular.models.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IusuariosDao extends JpaRepository<Usuarios, Long> {

    @Query(
            value = "Select * from usuarios Where usuarios.email LIKE %:email% AND usuarios.password LIKE %:password%",
            nativeQuery = true
    )
    List<Usuarios> searcUser(@Param("email") String email, @Param("password") String password);
}
