package co.com.datatools.documentos.administracion;

import java.io.Serializable;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class TipoIdentificacionDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private int idTipoIdentificacion;
    private String nombreTipoIdentificacion;
    private String siglaTipoIdentificacion;
    private int codigoTipoIdentificacion;

    // Constructors Declaration

    public TipoIdentificacionDTO() {

    }

    // Start Methods Declaration

    public int getIdTipoIdentificacion() {
        return idTipoIdentificacion;
    }

    public void setIdTipoIdentificacion(int idTipoIdentificacion) {
        this.idTipoIdentificacion = idTipoIdentificacion;
    }

    public String getNombreTipoIdentificacion() {
        return nombreTipoIdentificacion;
    }

    public void setNombreTipoIdentificacion(String nombreTipoIdentificacion) {
        this.nombreTipoIdentificacion = nombreTipoIdentificacion;
    }

    public String getSiglaTipoIdentificacion() {
        return siglaTipoIdentificacion;
    }

    public void setSiglaTipoIdentificacion(String siglaTipoIdentificacion) {
        this.siglaTipoIdentificacion = siglaTipoIdentificacion;
    }

    public int getCodigoTipoIdentificacion() {
        return codigoTipoIdentificacion;
    }

    public void setCodigoTipoIdentificacion(int codigoTipoIdentificacion) {
        this.codigoTipoIdentificacion = codigoTipoIdentificacion;
    }

    // Finish the class
}