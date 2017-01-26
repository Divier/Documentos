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
 * The persistent class for the parametro_organizacion database table.
 * 
 */
@Entity
@Table(name="parametro_sistema")
@NamedQuery(name="ParametroSistema.findAll", query="SELECT p FROM ParametroSistema p")
public class ParametroSistema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_parametro_sistema")
	private int idParametroSistema;

	@Column(name="nombre_parametro")
	private String nombreParametro;

	@Column(name="valor_parametro")
	private String valorParametro;

    @Column(name="editable")
    private boolean editable;

	//bi-directional many-to-one association to TipoDato
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tipo_dato")
	private TipoDato tipoDato;

	public ParametroSistema() {
	}

	public int getIdParametroSistema() {
		return this.idParametroSistema;
	}

	public void setIdParametroSistema(int idParametroSistema) {
		this.idParametroSistema = idParametroSistema;
	}

	public String getNombreParametro() {
		return this.nombreParametro;
	}

	public void setNombreParametro(String nombreParametro) {
		this.nombreParametro = nombreParametro;
	}

	public String getValorParametro() {
		return this.valorParametro;
	}

	public void setValorParametro(String valorParametro) {
		this.valorParametro = valorParametro;
	}

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public TipoDato getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(TipoDato tipoDato) {
        this.tipoDato = tipoDato;
    }

}