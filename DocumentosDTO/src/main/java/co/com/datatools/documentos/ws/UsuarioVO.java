/**
 * 
 */
package co.com.datatools.documentos.ws;

import java.io.Serializable;

/**
 * Contiene el usuario y clave del usuario que utiliza los servicios de la interfaz de integracion
 * 
 * @author robert.bautista
 * 
 */
public class UsuarioVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Atributo obligatorio que contiene el usuario que realiza el uso del servicio.<br>
     * Este campo esta encriptado con cifrado SHA-512 en base 64
     */
    private String usuario;

    /**
     * Atributo obligatorio que contiene la clave asociada al usuario.<br>
     * Este campo esta encriptado con el metodo de cifrado SHA-512 en base 64
     */
    private String clave;

    public UsuarioVO() {
        super();
    }

    public UsuarioVO(String usuario, String clave) {
        super();
        this.usuario = usuario;
        this.clave = clave;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

}
