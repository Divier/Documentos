package co.com.datatools.documentos.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.envers.AuditJoinTable;

/**
 * The persistent class for the cargo database table.
 * 
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Cargo.findAll", query = "SELECT c FROM Cargo c"),
        @NamedQuery(
                name = "Cargo.countByNombre",
                query = "SELECT COUNT(c) FROM Cargo c WHERE UPPER(c.nombreCargo) LIKE :pNomCar"),
        @NamedQuery(name = "Cargo.countById", query = "SELECT COUNT(c) FROM Cargo c WHERE c.idCargo = :pIdCar") })
public class Cargo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Cuenta cuantos cargos existen con el mismo nombre ignorando minusculas y mayusculas
     * <p>
     * Parametros:
     * <li><b>pNomCar</b> nombre cargo</li>
     * </p>
     * </br>
     * 
     * SELECT COUNT(c) FROM Cargo c WHERE UPPER(c.nombreCargo) LIKE :pNomCar</br>
     * 
     * @author luis.forero (2015-07-29)
     */
    public static final String SQ_COUNT_BY_NOMBRE = "Cargo.countByNombre";

    /**
     * Cuenta cuantos cargos existen con el mismo Identificador (ID)
     * <p>
     * Parametros:
     * <li><b>pIdCar</b> identificador 'Id' del cargo cargo</li>
     * </p>
     * </br>
     * 
     * SELECT COUNT(c) FROM Cargo c WHERE c.idCargo = :pIdCar</br>
     * 
     * @author luis.forero (2015-07-29)
     */
    public static final String SQ_COUNT_BY_ID = "Cargo.countById";

    @Id
    @Column(name = "id_cargo")
    private int idCargo;

    // bi-directional many-to-many association to Proceso
    @AuditJoinTable(schema = "documentos_aud")
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "cargo_proceso",
            joinColumns = { @JoinColumn(name = "id_cargo") },
            inverseJoinColumns = { @JoinColumn(name = "id_proceso") })
    private List<Proceso> procesos;

    @Column(name = "nombre_cargo")
    private String nombreCargo;

    // bi-directional many-to-one association to FirmaPlantilla
    @OneToMany(mappedBy = "cargo")
    private List<FirmaPlantilla> firmaPlantillas;

    // bi-directional many-to-one association to FuncionarioCargo
    @OneToMany(mappedBy = "cargo")
    private List<FuncionarioCargo> funcionarioCargos;

    public Cargo() {
    }

    public int getIdCargo() {
        return this.idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    public String getNombreCargo() {
        return this.nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public List<Proceso> getProcesos() {
        return this.procesos;
    }

    public void setProcesos(List<Proceso> procesos) {
        this.procesos = procesos;
    }

    public List<FirmaPlantilla> getFirmaPlantillas() {
        return this.firmaPlantillas;
    }

    public void setFirmaPlantillas(List<FirmaPlantilla> firmaPlantillas) {
        this.firmaPlantillas = firmaPlantillas;
    }

    public FirmaPlantilla addFirmaPlantilla(FirmaPlantilla firmaPlantilla) {
        getFirmaPlantillas().add(firmaPlantilla);
        firmaPlantilla.setCargo(this);

        return firmaPlantilla;
    }

    public FirmaPlantilla removeFirmaPlantilla(FirmaPlantilla firmaPlantilla) {
        getFirmaPlantillas().remove(firmaPlantilla);
        firmaPlantilla.setCargo(null);

        return firmaPlantilla;
    }

    public List<FuncionarioCargo> getFuncionarioCargos() {
        return this.funcionarioCargos;
    }

    public void setFuncionarioCargos(List<FuncionarioCargo> funcionarioCargos) {
        this.funcionarioCargos = funcionarioCargos;
    }

    public FuncionarioCargo addFuncionarioCargo(FuncionarioCargo funcionarioCargo) {
        getFuncionarioCargos().add(funcionarioCargo);
        funcionarioCargo.setCargo(this);

        return funcionarioCargo;
    }

    public FuncionarioCargo removeFuncionarioCargo(FuncionarioCargo funcionarioCargo) {
        getFuncionarioCargos().remove(funcionarioCargo);
        funcionarioCargo.setCargo(null);

        return funcionarioCargo;
    }

}