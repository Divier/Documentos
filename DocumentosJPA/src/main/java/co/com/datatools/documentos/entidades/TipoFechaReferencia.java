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
 * The persistent class for the tipo_fecha_referencia database table.
 * 
 */
@Entity
@Table(name = "tipo_fecha_referencia")
@NamedQuery(
        name = "TipoFechaReferencia.findAll",
        query = "SELECT t FROM TipoFechaReferencia t ORDER BY t.nombreTipoFecha ASC")
public class TipoFechaReferencia implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String SQ_TIPOFECHAREF_ALL = "TipoFechaReferencia.findAll";

    @Id
    @Column(name = "id_tipo_fecha")
    private int idTipoFecha;

    @Column(name = "nombre_tipo_fecha")
    private String nombreTipoFecha;

    // bi-directional many-to-one association to FirmaPlantilla
    @OneToMany(mappedBy = "tipoFechaReferencia")
    private List<FirmaPlantilla> firmaPlantillas;

    public TipoFechaReferencia() {
    }

    public int getIdTipoFecha() {
        return this.idTipoFecha;
    }

    public void setIdTipoFecha(int idTipoFecha) {
        this.idTipoFecha = idTipoFecha;
    }

    public String getNombreTipoFecha() {
        return this.nombreTipoFecha;
    }

    public void setNombreTipoFecha(String nombreTipoFecha) {
        this.nombreTipoFecha = nombreTipoFecha;
    }

    public List<FirmaPlantilla> getFirmaPlantillas() {
        return this.firmaPlantillas;
    }

    public void setFirmaPlantillas(List<FirmaPlantilla> firmaPlantillas) {
        this.firmaPlantillas = firmaPlantillas;
    }

    public FirmaPlantilla addFirmaPlantilla(FirmaPlantilla firmaPlantilla) {
        getFirmaPlantillas().add(firmaPlantilla);
        firmaPlantilla.setTipoFechaReferencia(this);

        return firmaPlantilla;
    }

    public FirmaPlantilla removeFirmaPlantilla(FirmaPlantilla firmaPlantilla) {
        getFirmaPlantillas().remove(firmaPlantilla);
        firmaPlantilla.setTipoFechaReferencia(null);

        return firmaPlantilla;
    }

}