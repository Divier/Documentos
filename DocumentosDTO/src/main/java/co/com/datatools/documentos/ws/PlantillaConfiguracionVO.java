package co.com.datatools.documentos.ws;

import java.io.Serializable;
import java.util.List;

/**
 * Configuracion de plantilla que obtiene para generar un documento
 * 
 * @author julio.pinzon 2016-09-08
 * 
 */
public class PlantillaConfiguracionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Query que se eejecutará para obtener
     */
    private String consulta;

    /**
     * Orden en que se muestran las columnas en la consulta
     */
    private String ordenVariables;

    /**
     * nombres de los parametros de la consulta
     */
    private String parametros;

    /**
     * Ubicacion del archivo generado con la plantilla
     */
    private String ubicacion;

    /**
     * Numeros que indican el orden de los parametros que cambian en la ubicacion
     */
    private String parametrosUbicacion;

    /**
     * Nombre en caso que sea un grupo
     */
    private String nombreGrupo;

    /**
     * Configuracion de grupos
     */
    private List<PlantillaConfiguracionVO> configuracionesPlantilla;

    public PlantillaConfiguracionVO() {
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

    public List<PlantillaConfiguracionVO> getConfiguracionesPlantilla() {
        return configuracionesPlantilla;
    }

    public void setConfiguracionesPlantilla(List<PlantillaConfiguracionVO> configuracionesPlantilla) {
        this.configuracionesPlantilla = configuracionesPlantilla;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

}