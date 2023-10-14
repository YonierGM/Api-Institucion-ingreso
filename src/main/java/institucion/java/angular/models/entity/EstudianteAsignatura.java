package institucion.java.angular.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
@Entity
@Table(name = "estudiante_asignatura")

@Data
public class EstudianteAsignatura implements Serializable {

    @EmbeddedId
    private EstudianteAsignaturaPK id;

    private Double nota;
    private int periodo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    @MapsId("estudiante")
    private Estudiante estudiante;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_asignatura")
    @MapsId("asignatura")
    private Asignatura asignatura;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

}
