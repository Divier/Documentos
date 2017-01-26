package co.com.datatools.documentos.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditJoinTable;


/**
 * The persistent class for the plantilla database table.
 * 
 */
@Entity
@NamedQuery(name="Plantilla.findAll", query="SELECT p FROM Plantilla p")
public class Plantilla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_plantilla")
	private int idPlantilla;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_fin")
	private Date fechaFin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_inicio")
	private Date fechaInicio;

	@Column(name="nombre_plantilla")
	private String nombrePlantilla;

    @Column(name="codigo_plantilla")
    private String codigoPlantilla;

	@Column(name="plantilla_bloqueada")
	private boolean plantillaBloqueada;

	@Column(name="version_plantilla")
	private int versionPlantilla;

	//bi-directional many-to-one association to Documento
	@OneToMany(mappedBy="plantilla")
	private List<Documento> documentos;

	//bi-directional many-to-one association to FirmaPlantilla
	@OneToMany(mappedBy="plantilla")
	private List<FirmaPlantilla> firmaPlantillas;

	//bi-directional many-to-one association to Plantilla
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_plantilla_origen")
	private Plantilla plantilla;

	//bi-directional many-to-one association to Plantilla
	@OneToMany(mappedBy="plantilla")
	private List<Plantilla> plantillas;

	//bi-directional many-to-one association to Proceso
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_proceso")
	private Proceso proceso;

	//bi-directional many-to-one association to UsuarioOrganizacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	private UsuarioOrganizacion usuarioOrganizacion;

    //bi-directional one-to-one association to XmlPlantilla
    @OneToOne(mappedBy="plantilla", fetch=FetchType.LAZY)
    private XmlPlantilla xmlPlantilla;

	//bi-directional many-to-many association to Variable
    @AuditJoinTable(schema = "documentos_aud")
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "variable_plantilla", joinColumns = { @JoinColumn(name = "id_plantilla") }, inverseJoinColumns = { @JoinColumn(name = "id_variable") })
	private List<Variable> variables;

    //bi-directional many-to-one association to JasperPlantilla
    @OneToMany(mappedBy="plantilla")
    private List<JasperPlantilla> jaspers;

	public Plantilla() {
	}

	public int getIdPlantilla() {
		return this.idPlantilla;
	}

	public void setIdPlantilla(int idPlantilla) {
		this.idPlantilla = idPlantilla;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getNombrePlantilla() {
		return this.nombrePlantilla;
	}

	public void setNombrePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}

	public boolean getPlantillaBloqueada() {
		return this.plantillaBloqueada;
	}

	public void setPlantillaBloqueada(boolean plantillaBloqueada) {
		this.plantillaBloqueada = plantillaBloqueada;
	}

	public int getVersionPlantilla() {
		return this.versionPlantilla;
	}

	public void setVersionPlantilla(int versionPlantilla) {
		this.versionPlantilla = versionPlantilla;
	}

	public List<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public Documento addDocumento(Documento documento) {
		getDocumentos().add(documento);
		documento.setPlantilla(this);

		return documento;
	}

	public Documento removeDocumento(Documento documento) {
		getDocumentos().remove(documento);
		documento.setPlantilla(null);

		return documento;
	}

	public List<FirmaPlantilla> getFirmaPlantillas() {
		return this.firmaPlantillas;
	}

	public void setFirmaPlantillas(List<FirmaPlantilla> firmaPlantillas) {
		this.firmaPlantillas = firmaPlantillas;
	}

	public FirmaPlantilla addFirmaPlantilla(FirmaPlantilla firmaPlantilla) {
		getFirmaPlantillas().add(firmaPlantilla);
		firmaPlantilla.setPlantilla(this);

		return firmaPlantilla;
	}

	public FirmaPlantilla removeFirmaPlantilla(FirmaPlantilla firmaPlantilla) {
		getFirmaPlantillas().remove(firmaPlantilla);
		firmaPlantilla.setPlantilla(null);

		return firmaPlantilla;
	}

	public Plantilla getPlantilla() {
		return this.plantilla;
	}

	public void setPlantilla(Plantilla plantilla) {
		this.plantilla = plantilla;
	}

	public List<Plantilla> getPlantillas() {
		return this.plantillas;
	}

	public void setPlantillas(List<Plantilla> plantillas) {
		this.plantillas = plantillas;
	}

	public Plantilla addPlantilla(Plantilla plantilla) {
		getPlantillas().add(plantilla);
		plantilla.setPlantilla(this);

		return plantilla;
	}

	public Plantilla removePlantilla(Plantilla plantilla) {
		getPlantillas().remove(plantilla);
		plantilla.setPlantilla(null);

		return plantilla;
	}

	public Proceso getProceso() {
		return this.proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public UsuarioOrganizacion getUsuarioOrganizacion() {
		return this.usuarioOrganizacion;
	}

	public void setUsuarioOrganizacion(UsuarioOrganizacion usuarioOrganizacion) {
		this.usuarioOrganizacion = usuarioOrganizacion;
	}

	public XmlPlantilla getXmlPlantilla() {
		return this.xmlPlantilla;
	}

	public void setXmlPlantilla(XmlPlantilla xmlPlantilla) {
		this.xmlPlantilla = xmlPlantilla;
	}

	public List<Variable> getVariables() {
		return this.variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

    public String getCodigoPlantilla() {
        return codigoPlantilla;
    }

    public void setCodigoPlantilla(String codigoPlantilla) {
        this.codigoPlantilla = codigoPlantilla;
    }

    public List<JasperPlantilla> getJaspers() {
        return jaspers;
    }

    public void setJaspers(List<JasperPlantilla> jaspers) {
        this.jaspers = jaspers;
    }

}