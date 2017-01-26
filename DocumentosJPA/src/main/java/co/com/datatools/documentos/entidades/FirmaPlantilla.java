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
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the firma_plantilla database table.
 * 
 */
@Entity
@Table(name="firma_plantilla")
@NamedQuery(name="FirmaPlantilla.findAll", query="SELECT f FROM FirmaPlantilla f")
public class FirmaPlantilla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_firma_plantilla")
	private int idFirmaPlantilla;

	@Column(name="mostrar_nombre_funcionario")
    private boolean mostrarNombreFuncionario;

    @Column(name="mostrar_cargo_funcionario")
    private boolean mostrarCargoFuncionario;

	//bi-directional many-to-one association to Cargo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_cargo")
	private Cargo cargo;

	//bi-directional many-to-one association to Funcionario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_funcionario")
	private Funcionario funcionario;

	//bi-directional many-to-one association to Plantilla
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_plantilla")
	private Plantilla plantilla;

	//bi-directional many-to-one association to TipoFechaReferencia
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tipo_fecha")
	private TipoFechaReferencia tipoFechaReferencia;

	//bi-directional many-to-one association to TipoFirmaPlantilla
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tipo_firma_plantilla")
	private TipoFirmaPlantilla tipoFirmaPlantilla;

	//bi-directional many-to-one association to Variable
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_fecha_referencia")
	private Variable variable;
	
    @Column(name="codigo_firma_plantilla")
    private int codigoFirmaPlantilla;

	public FirmaPlantilla() {
	}

	public int getIdFirmaPlantilla() {
		return this.idFirmaPlantilla;
	}

	public void setIdFirmaPlantilla(int idFirmaPlantilla) {
		this.idFirmaPlantilla = idFirmaPlantilla;
	}

	public boolean getMostrarNombreFuncionario() {
		return this.mostrarNombreFuncionario;
	}

	public void setMostrarNombreFuncionario(boolean mostrarNombreFuncionario) {
		this.mostrarNombreFuncionario = mostrarNombreFuncionario;
	}

	public Cargo getCargo() {
		return this.cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Funcionario getFuncionario() {
		return this.funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Plantilla getPlantilla() {
		return this.plantilla;
	}

	public void setPlantilla(Plantilla plantilla) {
		this.plantilla = plantilla;
	}

	public TipoFechaReferencia getTipoFechaReferencia() {
		return this.tipoFechaReferencia;
	}

	public void setTipoFechaReferencia(TipoFechaReferencia tipoFechaReferencia) {
		this.tipoFechaReferencia = tipoFechaReferencia;
	}

	public TipoFirmaPlantilla getTipoFirmaPlantilla() {
		return this.tipoFirmaPlantilla;
	}

	public void setTipoFirmaPlantilla(TipoFirmaPlantilla tipoFirmaPlantilla) {
		this.tipoFirmaPlantilla = tipoFirmaPlantilla;
	}

	public Variable getVariable() {
		return this.variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

    public int getCodigoFirmaPlantilla() {
        return codigoFirmaPlantilla;
    }

    public void setCodigoFirmaPlantilla(int codigoFirmaPlantilla) {
        this.codigoFirmaPlantilla = codigoFirmaPlantilla;
    }

    public boolean getMostrarCargoFuncionario() {
        return mostrarCargoFuncionario;
    }

    public void setMostrarCargoFuncionario(boolean mostrarCargoFuncionario) {
        this.mostrarCargoFuncionario = mostrarCargoFuncionario;
    }

}