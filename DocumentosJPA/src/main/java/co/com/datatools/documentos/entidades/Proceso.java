package co.com.datatools.documentos.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.envers.AuditJoinTable;

/**
 * The persistent class for the proceso database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Proceso.findAll", query = "SELECT p FROM Proceso p"),
        @NamedQuery(
                name = "Proceso.countByNombre",
                query = "SELECT COUNT(p) FROM Proceso p WHERE UPPER(p.nombreProceso) LIKE :pNomPro"),
        @NamedQuery(name = "Proceso.countByID", query = "SELECT COUNT(p) FROM Proceso p WHERE p.idProceso = :pIdPro"),
        @NamedQuery(
                name = "Proceso.findByCodigo",
                query = "SELECT p FROM Proceso p WHERE p.codigoProceso = :codigoProceso") })

public class Proceso implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Cuenta el numero de procesos con el mismo nombre
     * <p>
     * Parametros:
     * <li><b>pNomPro</b> nombre Proceso</li>
     * </p>
     * </br>
     * 
     * SELECT p FROM Proceso p WHERE UPPER(p.nombreProceso) LIKE :pNomPro<br>
     * 
     * @author luis.forero (2015-07-29)
     */
    public static final String SQ_COUNT_BY_NOMBRE = "Proceso.countByNombre";

    /**
     * Cuenta el numero de procesos con el mismo ID
     * <p>
     * Parametros:
     * <li><b>pIdPro</b> ID Proceso</li>
     * </p>
     * </br>
     * 
     * SELECT p FROM Proceso p WHERE p.idProceso = :pIdPro<br>
     * 
     * @author luis.forero (2015-07-29)
     */
    public static final String SQ_COUNT_BY_ID = "Proceso.countByID";

    /**
     * Consulta los proceso por codigo
     * <p>
     * Parametros:
     * <li><b>codigoProceso</b> Codigo Proceso</li>
     * </p>
     * </br>
     * 
     * SELECT p FROM Proceso p WHERE p.codigoProceso = :codigoProceso<br>
     * 
     * @author julio.pinzon (2016-09-06)
     */
    public static final String SQ_FIND_BY_CODIGO = "Proceso.findByCodigo";

    @Id
    @Column(name = "id_proceso")
    private long idProceso;

    @Column(name = "descripcion_proceso")
    private String descripcionProceso;

    // bi-directional many-to-many association to Cargo
    @AuditJoinTable(schema = "documentos_aud")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cargo_proceso",
            joinColumns = { @JoinColumn(name = "id_proceso") },
            inverseJoinColumns = { @JoinColumn(name = "id_cargo") })
    private List<Cargo> cargos;

    @Column(name = "nombre_proceso")
    private String nombreProceso;

    // bi-directional many-to-one association to Plantilla
    @OneToMany(mappedBy = "proceso")
    private List<Plantilla> plantillas;

    // bi-directional many-to-one association to Proceso
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proceso_padre")
    private Proceso proceso;

    // bi-directional many-to-one association to Variable
    @OneToMany(mappedBy = "proceso")
    private List<Variable> variables;

    @Column(name = "codigo_proceso")
    private String codigoProceso;

    public Proceso() {
    }

    public long getIdProceso() {
        return this.idProceso;
    }

    public void setIdProceso(long idProceso) {
        this.idProceso = idProceso;
    }

    public String getDescripcionProceso() {
        return this.descripcionProceso;
    }

    public void setDescripcionProceso(String descripcionProceso) {
        this.descripcionProceso = descripcionProceso;
    }

    public String getNombreProceso() {
        return this.nombreProceso;
    }

    public void setNombreProceso(String nombreProceso) {
        this.nombreProceso = nombreProceso;
    }

    public List<Cargo> getCargos() {
        return this.cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public List<Plantilla> getPlantillas() {
        return this.plantillas;
    }

    public void setPlantillas(List<Plantilla> plantillas) {
        this.plantillas = plantillas;
    }

    public Plantilla addPlantilla(Plantilla plantilla) {
        getPlantillas().add(plantilla);
        plantilla.setProceso(this);

        return plantilla;
    }

    public Plantilla removePlantilla(Plantilla plantilla) {
        getPlantillas().remove(plantilla);
        plantilla.setProceso(null);

        return plantilla;
    }

    public Proceso getProceso() {
        return this.proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public List<Variable> getVariables() {
        return this.variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public Variable addVariable(Variable variable) {
        getVariables().add(variable);
        variable.setProceso(this);

        return variable;
    }

    public Variable removeVariable(Variable variable) {
        getVariables().remove(variable);
        variable.setProceso(null);

        return variable;
    }

    public String getCodigoProceso() {
        return codigoProceso;
    }

    public void setCodigoProceso(String codigoProceso) {
        this.codigoProceso = codigoProceso;
    }

}