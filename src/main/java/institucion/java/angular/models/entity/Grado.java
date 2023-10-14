package institucion.java.angular.models.entity;


import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "grado")

@Getter
@Setter
public class Grado implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String descripcion;

	@JsonIgnore
	@OneToMany(mappedBy = "grado")
	private List<Estudiante> estudiante;

	@JsonIgnore
	@OneToMany(mappedBy = "grado")
	private List<Docente> docente;
}
