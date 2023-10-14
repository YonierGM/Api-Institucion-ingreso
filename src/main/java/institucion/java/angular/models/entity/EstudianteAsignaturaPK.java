package institucion.java.angular.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data //Lombok
public class EstudianteAsignaturaPK implements Serializable {

    @Column(name = "id_estudiante")
    private Integer estudiante;

    @Column(name = "id_asignatura")
    private Integer asignatura;

}
