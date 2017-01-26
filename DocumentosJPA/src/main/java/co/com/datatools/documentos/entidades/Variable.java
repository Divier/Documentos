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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditJoinTable;

/**
 * The persistent class for the variable database table.
 * 
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Variable.findAll", query = "SELECT v FROM Variable v"),
        @NamedQuery(
                name = "Variable.variablesPorProceso",
                query = "SELECT v FROM Variable v WHERE v.proceso.idProceso = :idProceso ORDER BY v.nombreVariable"),
        @NamedQuery(
                name = "Variable.variablesPorPlantilla",
                query = "SELECT v FROM Variable v JOIN v.plantillas p WHERE p.idPlantilla = :idPlantilla ORDER BY v.nombreVariable"),
        @NamedQuery(
                name = "Variable.variablesPorOrganizacion",
                query = "SELECT v FROM Variable v WHERE v.contextoAplicacionVariable.idContextoAplicacion = :idContextoAplicacion ORDER BY v.nombreVariable") })
public class Variable implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String SQ_VARIABLES_POR_PROCESO = "Variable.variablesPorProceso";
    public static final String SQ_VARIABLES_POR_PLANTILLA = "Variable.variablesPorPlantilla";
    public static final String SQ_VARIABLES_POR_ORGANIZACION = "Variable.variablesPorOrganizacion";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_variable")
    private int idVariable;

    @Column(name = "descripcion_variable")
    private String descripcionVariable;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "formato_variable")
    private String formatoVariable;

    @Lob
    private byte[] imagen;

    @Column(name = "longitud_variable")
    private Integer longitudVariable;

    @Column(name = "nombre_variable")
    private String nombreVariable;

    @Lob
    @Column(name = "palabra_clave")
    private String palabraClave;

    @Column(name = "valor_defecto")
    private String valorDefecto;

    // bi-directional many-to-one association to FirmaPlantilla
    @OneToMany(mappedBy = "variable")
    private List<FirmaPlantilla> firmaPlantillas;

    // bi-directional many-to-one association to TipoVariable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_variable")
    private TipoVariable tipoVariable;

    // bi-directional many-to-one association to ContextoAplicacionVariable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contexto_aplicacion")
    private ContextoAplicacionVariable contextoAplicacionVariable;

    // bi-directional many-to-one association to Proceso
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proceso")
    private Proceso proceso;

    // bi-directional many-to-many association to Plantilla
    @AuditJoinTable(schema = "documentos_aud")
    @ManyToMany
    @JoinTable(
            name = "variable_plantilla",
            joinColumns = { @JoinColumn(name = "id_variable") },
            inverseJoinColumns = { @JoinColumn(name = "id_plantilla") })
    private List<Plantilla> plantillas;

    public Variable() {
    }

    public int getIdVariable() {
        return this.idVariable;
    }

    public void setIdVariable(int idVariable) {
        this.idVariable = idVariable;
    }

    public String getDescripcionVariable() {
        return this.descripcionVariable;
    }

    public void setDescripcionVariable(String descripcionVariable) {
        this.descripcionVariable = descripcionVariable;
    }

    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFormatoVariable() {
        return this.formatoVariable;
    }

    public void setFormatoVariable(String formatoVariable) {
        this.formatoVariable = formatoVariable;
    }

    public byte[] getImagen() {
        return this.imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Integer getLongitudVariable() {
        return this.longitudVariable;
    }

    public void setLongitudVariable(Integer longitudVariable) {
        this.longitudVariable = longitudVariable;
    }

    public String getNombreVariable() {
        return this.nombreVariable;
    }

    public void setNombreVariable(String nombreVariable) {
        this.nombreVariable = nombreVariable;
    }

    public String getPalabraClave() {
        return this.palabraClave;
    }

    public void setPalabraClave(String palabraClave) {
        this.palabraClave = palabraClave;
    }

    public String getValorDefecto() {
        return this.valorDefecto;
    }

    public void setValorDefecto(String valorDefecto) {
        this.valorDefecto = valorDefecto;
    }

    public List<FirmaPlantilla> getFirmaPlantillas() {
        return this.firmaPlantillas;
    }

    public void setFirmaPlantillas(List<FirmaPlantilla> firmaPlantillas) {
        this.firmaPlantillas = firmaPlantillas;
    }

    public FirmaPlantilla addFirmaPlantilla(FirmaPlantilla firmaPlantilla) {
        getFirmaPlantillas().add(firmaPlantilla);
        firmaPlantilla.setVariable(this);

        return firmaPlantilla;
    }

    public FirmaPlantilla removeFirmaPlantilla(FirmaPlantilla firmaPlantilla) {
        getFirmaPlantillas().remove(firmaPlantilla);
        firmaPlantilla.setVariable(null);

        return firmaPlantilla;
    }

    public TipoVariable getTipoVariable() {
        return this.tipoVariable;
    }

    public void setTipoVariable(TipoVariable tipoVariable) {
        this.tipoVariable = tipoVariable;
    }

    public ContextoAplicacionVariable getContextoAplicacionVariable() {
        return this.contextoAplicacionVariable;
    }

    public void setContextoAplicacionVariable(ContextoAplicacionVariable contextoAplicacionVariable) {
        this.contextoAplicacionVariable = contextoAplicacionVariable;
    }

    public Proceso getProceso() {
        return this.proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public List<Plantilla> getPlantillas() {
        return this.plantillas;
    }

    public void setPlantillas(List<Plantilla> plantillas) {
        this.plantillas = plantillas;
    } 
}