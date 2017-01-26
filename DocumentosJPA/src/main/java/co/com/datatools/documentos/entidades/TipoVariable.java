package co.com.datatools.documentos.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the tipo_variable database table.
 * 
 */
@Entity
@Table(name="tipo_variable")
@NamedQuery(name="TipoVariable.findAll", query="SELECT t FROM TipoVariable t")
public class TipoVariable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_variable")
	private int idTipoVariable;

	@Column(name="nombre_tipo_variable")
	private String nombreTipoVariable;

	//bi-directional many-to-one association to Variable
	@OneToMany(mappedBy="tipoVariable")
	private List<Variable> variables;

	public TipoVariable() {
	}

	public int getIdTipoVariable() {
		return this.idTipoVariable;
	}

	public void setIdTipoVariable(int idTipoVariable) {
		this.idTipoVariable = idTipoVariable;
	}

	public String getNombreTipoVariable() {
		return this.nombreTipoVariable;
	}

	public void setNombreTipoVariable(String nombreTipoVariable) {
		this.nombreTipoVariable = nombreTipoVariable;
	}

	public List<Variable> getVariables() {
		return this.variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

	public Variable addVariable(Variable variable) {
		getVariables().add(variable);
		variable.setTipoVariable(this);

		return variable;
	}

	public Variable removeVariable(Variable variable) {
		getVariables().remove(variable);
		variable.setTipoVariable(null);

		return variable;
	}

}