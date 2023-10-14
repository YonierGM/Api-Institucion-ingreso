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
@Table(name = "tipo_documento")

@Data
public class TipoDocumento implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String descripcion;

	@JsonIgnore
	@OneToMany(mappedBy = "tipodocumento")
	private List<Estudiante> estudiante;

	@JsonIgnore
	@OneToMany(mappedBy = "tipodocumento")
	private List<Docente> docente;
}
