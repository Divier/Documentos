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
@Table(name="tipo_dato")
@NamedQuery(name="TipoDato.findAll", query="SELECT t FROM TipoDato t")
public class TipoDato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_dato")
	private int idTipoDato;

	@Column(name="nombre_tipo_dato")
	private String nombreTipoDato;

	//bi-directional many-to-one association to ParametroSistema
	@OneToMany(mappedBy="tipoDato")
	private List<ParametroSistema> parametrosSistema;

	public TipoDato() {
	}

	public int getIdTipoDato() {
        return idTipoDato;
    }

    public void setIdTipoDato(int idTipoDato) {
        this.idTipoDato = idTipoDato;
    }

    public String getNombreTipoDato() {
        return nombreTipoDato;
    }

    public void setNombreTipoDato(String nombreTipoDato) {
        this.nombreTipoDato = nombreTipoDato;
    }

    public List<ParametroSistema> getParametrosSistema() {
		return this.parametrosSistema;
	}

	public void setParametrosSistema(List<ParametroSistema> parametrosSistema) {
		this.parametrosSistema = parametrosSistema;
	}

	public ParametroSistema addParametroSistema(ParametroSistema parametroSistema) {
		getParametrosSistema().add(parametroSistema);
		parametroSistema.setTipoDato(this);

		return parametroSistema;
	}

	public ParametroSistema removeParametroSistema(ParametroSistema parametroSistema) {
		getParametrosSistema().remove(parametroSistema);
		parametroSistema.setTipoDato(null);

		return parametroSistema;
	}

}