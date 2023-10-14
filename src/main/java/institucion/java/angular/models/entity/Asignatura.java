package institucion.java.angular.models.entity;


import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="asignatura")

@Data
public class Asignatura implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String descripcion;

	@JsonIgnoreProperties(value= {"handler","hibernateLazyInitializer","FieldHandler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_docente")
	private Docente docente;

	@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "FieldHandler" })
	@OneToMany(mappedBy = "asignatura", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<EstudianteAsignatura> estudianteAsignaturas;
}
