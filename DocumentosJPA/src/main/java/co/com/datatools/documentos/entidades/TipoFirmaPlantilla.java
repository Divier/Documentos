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
 * The persistent class for the tipo_firma_plantilla database table.
 * 
 */
@Entity
@Table(name = "tipo_firma_plantilla")
@NamedQuery(name = "TipoFirmaPlantilla.findAll", query = "SELECT t FROM TipoFirmaPlantilla t ORDER BY t.nombre ASC")
public class TipoFirmaPlantilla implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String SQ_TIPOFIRMA_ALL = "TipoFirmaPlantilla.findAll";

    @Id
    @Column(name = "id_tipo_firma_plantilla")
    private int idTipoFirmaPlantilla;

    private String nombre;

    // bi-directional many-to-one association to FirmaPlantilla
    @OneToMany(mappedBy = "tipoFirmaPlantilla")
    private List<FirmaPlantilla> firmaPlantillas;

    public TipoFirmaPlantilla() {
    }

    public int getIdTipoFirmaPlantilla() {
        return this.idTipoFirmaPlantilla;
    }

    public void setIdTipoFirmaPlantilla(int idTipoFirmaPlantilla) {
        this.idTipoFirmaPlantilla = idTipoFirmaPlantilla;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<FirmaPlantilla> getFirmaPlantillas() {
        return this.firmaPlantillas;
    }

    public void setFirmaPlantillas(List<FirmaPlantilla> firmaPlantillas) {
        this.firmaPlantillas = firmaPlantillas;
    }

    public FirmaPlantilla addFirmaPlantilla(FirmaPlantilla firmaPlantilla) {
        getFirmaPlantillas().add(firmaPlantilla);
        firmaPlantilla.setTipoFirmaPlantilla(this);

        return firmaPlantilla;
    }

    public FirmaPlantilla removeFirmaPlantilla(FirmaPlantilla firmaPlantilla) {
        getFirmaPlantillas().remove(firmaPlantilla);
        firmaPlantilla.setTipoFirmaPlantilla(null);

        return firmaPlantilla;
    }

}