package co.com.datatools.documentos.administracion;

import java.io.Serializable;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class TipoVariableDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private int idTipoVariable;
    private String nombreTipoVariable;

    // Constructors Declaration

    public TipoVariableDTO() {

    }

    // Start Methods Declaration

    public int getIdTipoVariable() {
        return idTipoVariable;
    }

    public void setIdTipoVariable(int idTipoVariable) {
        this.idTipoVariable = idTipoVariable;
    }

    public String getNombreTipoVariable() {
        return nombreTipoVariable;
    }

    public void setNombreTipoVariable(String nombreTipoVariable) {
        this.nombreTipoVariable = nombreTipoVariable;
    }

    // Finish the class
}