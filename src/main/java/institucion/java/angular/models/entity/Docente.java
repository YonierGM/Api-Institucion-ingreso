package institucion.java.angular.models.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "docente")

@Data
public class Docente implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipodocumento")
	@JsonIgnoreProperties(value= {"handler","hibernateLazyInitializer","FieldHandler"})
	private TipoDocumento tipodocumento;

	private String numerodocumento;
	private String nombres;
	private String apellidos;
	private String fecha_nacimiento;
	private int asig_dictadas;
	private String grado_escolaridad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_gradoresponsable")
	@JsonIgnoreProperties(value= {"handler","hibernateLazyInitializer","FieldHandler"})
	private Grado grado;

	private String email;
	private String fijo;
	private String celular;

	@JsonIgnore
	@OneToMany(mappedBy = "docente")
	private List<Asignatura> asignatura;

}
