/**
 * 
 */
package co.com.datatools.documentos.ws;

import java.io.Serializable;

/**
 * @author robert.bautista
 * 
 */
public class UsuarioOrganizacionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Campo obligatorio que contiene el login del usuario
     */
    private String login;

    /**
     * Campo obligatorio que contiene la clave usuario cifrada con el metodo SHA 512 base 64
     */
    private String contrasena;

    /**
     * Contiene el nombre del tipo de documento del funcionario asociado al usuario.
     */
    private String nombreTipoDocumentoFuncionario;

    /**
     * Contiene el número de documento del funcionario al cual está asociado el usuario.
     */
    private String numeroDocumentoFuncionario;

    public UsuarioOrganizacionVO() {
        super();
    }

    public UsuarioOrganizacionVO(String login, String contrasena, String nombreTipoDocumentoFuncionario,
            String numeroDocumentoFuncionario) {
        this.login = login;
        this.contrasena = contrasena;
        this.nombreTipoDocumentoFuncionario = nombreTipoDocumentoFuncionario;
        this.numeroDocumentoFuncionario = numeroDocumentoFuncionario;
    }

    public String getLogin() {
        return this.login;
    }

    public String getContrasena() {
        return this.contrasena;
    }

    public String getNombreTipoDocumentoFuncionario() {
        return this.nombreTipoDocumentoFuncionario;
    }

    public String getNumeroDocumentoFuncionario() {
        return this.numeroDocumentoFuncionario;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setNombreTipoDocumentoFuncionario(String nombreTipoDocumentoFuncionario) {
        this.nombreTipoDocumentoFuncionario = nombreTipoDocumentoFuncionario;
    }

    public void setNumeroDocumentoFuncionario(String numeroDocumentoFuncionario) {
        this.numeroDocumentoFuncionario = numeroDocumentoFuncionario;
    }

}
