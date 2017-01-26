package co.com.datatools.documentos.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the xml_plantilla database table.
 * 
 */
@Entity
@Table(name="xml_plantilla")
@NamedQuery(name="XmlPlantilla.findAll", query="SELECT x FROM XmlPlantilla x")
public class XmlPlantilla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_xml_plantilla")
	private int idXmlPlantilla;

	@Lob
    @Column(name="contenido_xml_plantilla")
    private byte[] contenidoXmlPlantilla;

	@Column(name="nombre_xml_plantilla")
	private String nombreXmlPlantilla;

    //bi-directional one-to-one association to Plantilla
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_xml_plantilla")
    private Plantilla plantilla;

	//bi-directional many-to-one association to XmlPlantilla
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_xml_plantilla_padre")
	private XmlPlantilla xmlPlantilla;

	//bi-directional many-to-one association to XmlPlantilla
	@OneToMany(mappedBy="xmlPlantilla")
	private List<XmlPlantilla> xmlPlantillas;

    @Column(name="codigo_documento_html")
    private String codigoDocumentoHtml;

	public XmlPlantilla() {
	}

	public int getIdXmlPlantilla() {
		return this.idXmlPlantilla;
	}

	public void setIdXmlPlantilla(int idXmlPlantilla) {
		this.idXmlPlantilla = idXmlPlantilla;
	}

	public byte[] getContenidoXmlPlantilla() {
		return this.contenidoXmlPlantilla;
	}

	public void setContenidoXmlPlantilla(byte[] contenidoXmlPlantilla) {
		this.contenidoXmlPlantilla = contenidoXmlPlantilla;
	}

	public String getNombreXmlPlantilla() {
		return this.nombreXmlPlantilla;
	}

	public void setNombreXmlPlantilla(String nombreXmlPlantilla) {
		this.nombreXmlPlantilla = nombreXmlPlantilla;
	}

	public XmlPlantilla getXmlPlantilla() {
		return this.xmlPlantilla;
	}

	public void setXmlPlantilla(XmlPlantilla xmlPlantilla) {
		this.xmlPlantilla = xmlPlantilla;
	}

	public List<XmlPlantilla> getXmlPlantillas() {
		return this.xmlPlantillas;
	}

	public void setXmlPlantillas(List<XmlPlantilla> xmlPlantillas) {
		this.xmlPlantillas = xmlPlantillas;
	}

	public XmlPlantilla addXmlPlantilla(XmlPlantilla xmlPlantilla) {
		getXmlPlantillas().add(xmlPlantilla);
		xmlPlantilla.setXmlPlantilla(this);

		return xmlPlantilla;
	}

	public XmlPlantilla removeXmlPlantilla(XmlPlantilla xmlPlantilla) {
		getXmlPlantillas().remove(xmlPlantilla);
		xmlPlantilla.setXmlPlantilla(null);

		return xmlPlantilla;
	}

    public Plantilla getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(Plantilla plantilla) {
        this.plantilla = plantilla;
    }

    public String getCodigoDocumentoHtml() {
        return codigoDocumentoHtml;
    }

    public void setCodigoDocumentoHtml(String codigoDocumentoHtml) {
        this.codigoDocumentoHtml = codigoDocumentoHtml;
    }

}