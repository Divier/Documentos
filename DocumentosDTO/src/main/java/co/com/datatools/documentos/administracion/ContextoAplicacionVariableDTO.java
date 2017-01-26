package co.com.datatools.documentos.administracion;

import java.io.Serializable;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class ContextoAplicacionVariableDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private int idContextoAplicacion;
    private String nombreContextoAplicacion;

    // Constructors Declaration

    public ContextoAplicacionVariableDTO() {

    }

    // Start Methods Declaration

    public int getIdContextoAplicacion() {
        return idContextoAplicacion;
    }

    public void setIdContextoAplicacion(int idContextoAplicacion) {
        this.idContextoAplicacion = idContextoAplicacion;
    }

    public String getNombreContextoAplicacion() {
        return nombreContextoAplicacion;
    }

    public void setNombreContextoAplicacion(String nombreContextoAplicacion) {
        this.nombreContextoAplicacion = nombreContextoAplicacion;
    }

    // Finish the class
}