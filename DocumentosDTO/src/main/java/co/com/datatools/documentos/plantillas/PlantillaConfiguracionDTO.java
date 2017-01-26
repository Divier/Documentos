package co.com.datatools.documentos.plantillas;

import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the plantilla_configuracion database table.
 * 
 */
public class PlantillaConfiguracionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idPlantillaConfig;
    private String consulta;
    private String ordenVariables;
    private String parametros;
    private String ubicacion;
    private String parametrosUbicacion;
    private String nombreGrupo;
    private PlantillaConfiguracionDTO configuracionPadre;
    private PlantillaDTO plantilla;
    private List<PlantillaConfiguracionDTO> configuracionesPlantilla;

    public PlantillaConfiguracionDTO() {
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

    public void setOrdenVariables(String ordenVariables) {
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

    public PlantillaConfiguracionDTO getConfiguracionPadre() {
        return configuracionPadre;
    }

    public void setConfiguracionPadre(PlantillaConfiguracionDTO configuracionPadre) {
        this.configuracionPadre = configuracionPadre;
    }

    public PlantillaDTO getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(PlantillaDTO plantilla) {
        this.plantilla = plantilla;
    }

    public List<PlantillaConfiguracionDTO> getConfiguracionesPlantilla() {
        return configuracionesPlantilla;
    }

    public void setConfiguracionesPlantilla(List<PlantillaConfiguracionDTO> configuracionesPlantilla) {
        this.configuracionesPlantilla = configuracionesPlantilla;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

}