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
 * The persistent class for the jasper_plantilla database table.
 * 
 */
@Entity
@Table(name="jasper_plantilla")
@NamedQuery(name="JasperPlantilla.findAll", query="SELECT x FROM JasperPlantilla x")
public class JasperPlantilla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_jasper_plantilla")
	private String idJasperPlantilla;

    @Column(name="codigo_documento")
    private String codigoDocumento;

    //bi-directional many-to-one association to Plantilla
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_plantilla")
    private Plantilla plantilla;

	public JasperPlantilla() {
	}

	public String getIdJasperPlantilla() {
		return this.idJasperPlantilla;
	}

	public void setIdJasperPlantilla(String idJasperPlantilla) {
		this.idJasperPlantilla = idJasperPlantilla;
	}

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public Plantilla getPlantilla() {
        return this.plantilla;
    }

    public void setPlantilla(Plantilla plantilla) {
        this.plantilla = plantilla;
    }

}