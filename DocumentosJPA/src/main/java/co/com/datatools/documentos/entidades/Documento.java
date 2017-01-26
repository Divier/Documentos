package co.com.datatools.documentos.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the documento database table.
 * 
 */
@Entity
@NamedQuery(name="Documento.findAll", query="SELECT d FROM Documento d")
public class Documento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_documento")
	private long idDocumento;

	@Column(name="version_documento_cm")
	private int versionDocumentoCm;
	
    @Column(name="consecutivo_documento")
    private long consecutivoDocumento;

	@Column(name="descripcion_documento")
	private String descripcionDocumento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_generacion")
	private Date fechaGeneracion;

	private boolean firmado;

	@Column(name="nombre_documento")
	private String nombreDocumento;

	@Column(name="path_documento")
	private String pathDocumento;

	@Column(name="version_documento")
	private int versionDocumento;

    @Column(name="codigo_documento")
    private String codigoDocumento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_creacion")
    private Date fechaCreacion;

	//bi-directional many-to-one association to Documento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_documento_origen")
	private Documento documento;

	//bi-directional many-to-one association to Documento
	@OneToMany(mappedBy="documento")
	private List<Documento> documentos;

	//bi-directional many-to-one association to Plantilla
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_plantilla")
	private Plantilla plantilla;

	//bi-directional many-to-one association to UsuarioOrganizacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	private UsuarioOrganizacion usuarioOrganizacion;

	//bi-directional many-to-one association to XmlDocumento
    @OneToOne(mappedBy="documento", fetch=FetchType.LAZY)
	private XmlDocumento xmlDocumento;

	//bi-directional many-to-one association to FirmaDigitalDocumento
	@OneToMany(mappedBy="documento")
	private List<FirmaDigitalDocumento> firmaDigitalDocumentos;

	public Documento() {
	}

	public long getIdDocumento() {
		return this.idDocumento;
	}

	public void setIdDocumento(long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getDescripcionDocumento() {
		return this.descripcionDocumento;
	}

	public void setDescripcionDocumento(String descripcionDocumento) {
		this.descripcionDocumento = descripcionDocumento;
	}

	public Date getFechaGeneracion() {
		return this.fechaGeneracion;
	}

	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

	public boolean getFirmado() {
		return this.firmado;
	}

	public void setFirmado(boolean firmado) {
		this.firmado = firmado;
	}

	public String getNombreDocumento() {
		return this.nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public String getPathDocumento() {
		return this.pathDocumento;
	}

	public void setPathDocumento(String pathDocumento) {
		this.pathDocumento = pathDocumento;
	}

	public int getVersionDocumento() {
		return this.versionDocumento;
	}

	public void setVersionDocumento(int versionDocumento) {
		this.versionDocumento = versionDocumento;
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public List<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public Documento addDocumento(Documento documento) {
		getDocumentos().add(documento);
		documento.setDocumento(this);

		return documento;
	}

	public Documento removeDocumento(Documento documento) {
		getDocumentos().remove(documento);
		documento.setDocumento(null);

		return documento;
	}

	public Plantilla getPlantilla() {
		return this.plantilla;
	}

	public void setPlantilla(Plantilla plantilla) {
		this.plantilla = plantilla;
	}

	public UsuarioOrganizacion getUsuarioOrganizacion() {
		return this.usuarioOrganizacion;
	}

	public void setUsuarioOrganizacion(UsuarioOrganizacion usuarioOrganizacion) {
		this.usuarioOrganizacion = usuarioOrganizacion;
	}

	public XmlDocumento getXmlDocumento() {
		return this.xmlDocumento;
	}

	public void setXmlDocumento(XmlDocumento xmlDocumento) {
		this.xmlDocumento = xmlDocumento;
	}

	public List<FirmaDigitalDocumento> getFirmaDigitalDocumentos() {
		return this.firmaDigitalDocumentos;
	}

	public void setFirmaDigitalDocumentos(List<FirmaDigitalDocumento> firmaDigitalDocumentos) {
		this.firmaDigitalDocumentos = firmaDigitalDocumentos;
	}

	public FirmaDigitalDocumento addFirmaDigitalDocumento(FirmaDigitalDocumento firmaDigitalDocumento) {
		getFirmaDigitalDocumentos().add(firmaDigitalDocumento);
		firmaDigitalDocumento.setDocumento(this);

		return firmaDigitalDocumento;
	}

	public FirmaDigitalDocumento removeFirmaDigitalDocumento(FirmaDigitalDocumento firmaDigitalDocumento) {
		getFirmaDigitalDocumentos().remove(firmaDigitalDocumento);
		firmaDigitalDocumento.setDocumento(null);

		return firmaDigitalDocumento;
	}

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public int getVersionDocumentoCm() {
        return versionDocumentoCm;
    }

    public void setVersionDocumentoCm(int versionDocumentoCm) {
        this.versionDocumentoCm = versionDocumentoCm;
    }

    public long getConsecutivoDocumento() {
        return consecutivoDocumento;
    }

    public void setConsecutivoDocumento(long consecutivoDocumento) {
        this.consecutivoDocumento = consecutivoDocumento;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}