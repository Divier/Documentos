package co.com.datatools.documentos.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;

/**
 * The persistent class for the contexto_aplicacion_variable database table.
 * 
 */
@Entity
@Table(name = "contexto_aplicacion_variable")
@AuditTable(value = "contexto_aplicacion_varia_aud")
@NamedQuery(name = "ContextoAplicacionVariable.findAll", query = "SELECT c FROM ContextoAplicacionVariable c")
public class ContextoAplicacionVariable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_contexto_aplicacion")
	private int idContextoAplicacion;

	@Column(name = "nombre_contexto_aplicacion")
	private String nombreContextoAplicacion;

	// bi-directional many-to-one association to Variable
	@OneToMany(mappedBy = "contextoAplicacionVariable")
	private List<Variable> variables;

	public ContextoAplicacionVariable() {
	}

	public int getIdContextoAplicacion() {
		return this.idContextoAplicacion;
	}

	public void setIdContextoAplicacion(int idContextoAplicacion) {
		this.idContextoAplicacion = idContextoAplicacion;
	}

	public String getNombreContextoAplicacion() {
		return this.nombreContextoAplicacion;
	}

	public void setNombreContextoAplicacion(String nombreContextoAplicacion) {
		this.nombreContextoAplicacion = nombreContextoAplicacion;
	}

	public List<Variable> getVariables() {
		return this.variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

	public Variable addVariable(Variable variable) {
		getVariables().add(variable);
		variable.setContextoAplicacionVariable(this);

		return variable;
	}

	public Variable removeVariable(Variable variable) {
		getVariables().remove(variable);
		variable.setContextoAplicacionVariable(null);

		return variable;
	}

}