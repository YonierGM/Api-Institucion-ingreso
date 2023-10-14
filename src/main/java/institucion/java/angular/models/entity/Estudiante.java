package institucion.java.angular.models.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estudiante")

@Data
public class Estudiante implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "FieldHandler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipodocumento")
	private TipoDocumento tipodocumento;

	private String numerodocumento;
	private String nombre;
	private String apellidos;
	private String fecha_nacimiento;

	@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "FieldHandler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_grado")
	private Grado grado;

	@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "FieldHandler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dane")
	private Dane dane;

	private String direccion;
	private String email;
	private String fijo;
	private String celular;

	@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "FieldHandler" })
	@OneToMany(mappedBy = "estudiante", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<EstudianteAsignatura> estudianteAsignaturas;

}
