package co.com.datatools.documentos.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * The persistent class for the formato database table.
 * 
 */
@Entity
public class Formato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_formato")
	private int idFormato;

	private String formato;

	private String nombre;

	//bi-directional many-to-one association to TipoVariable
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tipo_variable")
	private TipoVariable tipoVariable;

	public Formato() {
	}

	public int getIdFormato() {
		return this.idFormato;
	}

	public void setIdFormato(int idFormato) {
		this.idFormato = idFormato;
	}

	public String getFormato() {
		return this.formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoVariable getTipoVariable() {
		return this.tipoVariable;
	}

	public void setTipoVariable(TipoVariable tipoVariable) {
		this.tipoVariable = tipoVariable;
	}

}