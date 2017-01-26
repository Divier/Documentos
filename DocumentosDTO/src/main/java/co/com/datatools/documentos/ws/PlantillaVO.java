package co.com.datatools.documentos.ws;

import java.io.Serializable;

/**
 * Plantilla enviada en servicio
 * 
 * @author julio.pinzon 2016-09-06
 *
 */
public class PlantillaVO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    /**
     * Nombre de la plantilla
     */
    private String nombrePlantilla;

    /**
     * Codigo de la plantilla
     */
    private String codigoPlantilla;

    // Constructors Declaration

    public PlantillaVO() {

    }

    // Start Methods Declaration

    public String getNombrePlantilla() {
        return nombrePlantilla;
    }

    public void setNombrePlantilla(String nombrePlantilla) {
        this.nombrePlantilla = nombrePlantilla;
    }

    public String getCodigoPlantilla() {
        return codigoPlantilla;
    }

    public void setCodigoPlantilla(String codigoPlantilla) {
        this.codigoPlantilla = codigoPlantilla;
    }

    // Finish the class
}