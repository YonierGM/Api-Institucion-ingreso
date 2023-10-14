package institucion.java.angular.models.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dane")

@Getter
@Setter
public class Dane implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String descripcion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipodane")
	@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "FieldHandler" })
	private TipoDane tipodane;

	@JsonIgnore
	@OneToMany(mappedBy = "dane")
	private List<Estudiante> estudiante;
}
