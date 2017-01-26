package co.com.datatools.documentos.administracion;

import java.io.Serializable;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class EntidadDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private int idEntidad;
    private String nombre;

    // Constructors Declaration

    public EntidadDTO() {

    }

    // Start Methods Declaration

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(int idEntidad) {
        this.idEntidad = idEntidad;
    }

    // Finish the class
}