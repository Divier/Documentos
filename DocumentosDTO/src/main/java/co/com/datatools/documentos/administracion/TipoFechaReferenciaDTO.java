package co.com.datatools.documentos.administracion;

import java.io.Serializable;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class TipoFechaReferenciaDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private int idTipoFecha;
    private String nombreTipoFecha;

    // Constructors Declaration

    public TipoFechaReferenciaDTO() {

    }

    // Start Methods Declaration

    public int getIdTipoFecha() {
        return idTipoFecha;
    }

    public void setIdTipoFecha(int idTipoFecha) {
        this.idTipoFecha = idTipoFecha;
    }

    public String getNombreTipoFecha() {
        return nombreTipoFecha;
    }

    public void setNombreTipoFecha(String nombreTipoFecha) {
        this.nombreTipoFecha = nombreTipoFecha;
    }

    // Finish the class
}