package co.com.datatools.documentos.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the plantilla_configuracion database table.
 * 
 */
@Entity
@Table(name = "plantilla_configuracion")
public class PlantillaConfiguracion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plantilla_config")
    private Long idPlantillaConfig;

    @Column(name = "consulta")
    private String consulta;

    @Column(name = "orden_variables")
    private String ordenVariables;

    @Column(name = "parametros")
    private String parametros;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "parametros_ubicacion")
    private String parametrosUbicacion;

    @Column(name = "nombre_grupo")
    private String nombreGrupo;

    // bi-directional many-to-one association to Documento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plantilla_config_padre")
    private PlantillaConfiguracion configuracionPadre;

    // bi-directional many-to-one association to Plantilla
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plantilla")
    private Plantilla plantilla;

    // bi-directional many-to-one association to XmlPlantilla
    @OneToMany(mappedBy = "configuracionPadre")
    private List<PlantillaConfiguracion> configuracionesPlantilla;

    public PlantillaConfiguracion() {
    }

    public Long getIdPlantillaConfig() {
        return idPlantillaConfig;
    }

    public void setIdPlantillaConfig(Long idPlantillaConfig) {
        this.idPlantillaConfig = idPlantillaConfig;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public String getOrdenVariables() {
        return ordenVariables;
    }

    public void setOrden_variables(String ordenVariables) {
        this.ordenVariables = ordenVariables;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getParametrosUbicacion() {
        return parametrosUbicacion;
    }

    public void setParametrosUbicacion(String parametrosUbicacion) {
        this.parametrosUbicacion = parametrosUbicacion;
    }

    public PlantillaConfiguracion getConfiguracionPadre() {
        return configuracionPadre;
    }

    public void setConfiguracionPadre(PlantillaConfiguracion configuracionPadre) {
        this.configuracionPadre = configuracionPadre;
    }

    public Plantilla getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(Plantilla plantilla) {
        this.plantilla = plantilla;
    }

    public List<PlantillaConfiguracion> getConfiguracionesPlantilla() {
        return configuracionesPlantilla;
    }

    public void setConfiguracionesPlantilla(List<PlantillaConfiguracion> configuracionesPlantilla) {
        this.configuracionesPlantilla = configuracionesPlantilla;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public void setOrdenVariables(String ordenVariables) {
        this.ordenVariables = ordenVariables;
    }

}