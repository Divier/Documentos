package co.com.datatools.documentos.administracion;

import java.io.Serializable;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class TipoFirmaPlantillaDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private int idTipoFirmaPlantilla;
    private String nombre;

    // Constructors Declaration

    public TipoFirmaPlantillaDTO() {

    }

    // Start Methods Declaration

    public int getIdTipoFirmaPlantilla() {
        return idTipoFirmaPlantilla;
    }

    public void setIdTipoFirmaPlantilla(int idTipoFirmaPlantilla) {
        this.idTipoFirmaPlantilla = idTipoFirmaPlantilla;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Finish the class
}