package co.com.datatools.documentos.negocio.interfaces.aud;

import javax.ejb.Remote;

@Remote
public interface ILUsuarioSesion {

    /**
     * @see IRUsuarioSesion#getUsuario()
     */
    String getUsuario();

    /**
     * @see IRUsuarioSesion#getIP()
     */
    String getIP();

    /**
     * @see IRUsuarioSesion#almacenarUsuario(String, String)
     */
    public long almacenarUsuario(String loginUsuario, String ip);

    /**
     * @see IRUsuarioSesion#removerUsuario()
     */
    void removerUsuario();

}
