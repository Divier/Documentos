package co.com.datatools.documentos.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the xml_documento database table.
 * 
 */
@Entity
@Table(name="xml_documento")
@NamedQuery(name="XmlDocumento.findAll", query="SELECT x FROM XmlDocumento x")
public class XmlDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_xml_documento")
	private long idXmlDocumento;

	@Lob
	@Column(name="contenido_xml")
	private byte[] contenidoXml;

	@Column(name="nombre_xml_doc")
	private String nombreXmlDoc;

	@Lob
	@Column(name="parametros_documento")
	private String parametrosDocumento;

    //bi-directional one-to-one association to Documento
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_xml_documento")
    private Documento documento;

    @Column(name="codigo_documento_html")
    private String codigoDocumentoHtml;

	public XmlDocumento() {
	}

	public long getIdXmlDocumento() {
		return this.idXmlDocumento;
	}

	public void setIdXmlDocumento(long idXmlDocumento) {
		this.idXmlDocumento = idXmlDocumento;
	}

	public byte[] getContenidoXml() {
		return this.contenidoXml;
	}

	public void setContenidoXml(byte[] contenidoXml) {
		this.contenidoXml = contenidoXml;
	}

	public String getNombreXmlDoc() {
		return this.nombreXmlDoc;
	}

	public void setNombreXmlDoc(String nombreXmlDoc) {
		this.nombreXmlDoc = nombreXmlDoc;
	}

	public String getParametrosDocumento() {
		return this.parametrosDocumento;
	}

	public void setParametrosDocumento(String parametrosDocumento) {
		this.parametrosDocumento = parametrosDocumento;
	}

    public String getCodigoDocumentoHtml() {
        return codigoDocumentoHtml;
    }

    public void setCodigoDocumentoHtml(String codigoDocumentoHtml) {
        this.codigoDocumentoHtml = codigoDocumentoHtml;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

}