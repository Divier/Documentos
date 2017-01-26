package co.com.datatools.documentos.negocio.interfaces.administracion;

import java.util.List;

import javax.ejb.Local;

import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.administracion.tmp.RolDTO;
import co.com.datatools.documentos.excepcion.DocumentosException;

/**
 * @author dixon.alvarez
 * 
 */

@Local
public interface ILUsuarioOrganizacion {

    /**
     * @see IRUsuarioOrganizacion#consultarUsuarioOrganizacion(long)
     */
    public UsuarioOrganizacionDTO consultarUsuarioOrganizacion(long idUsuario);

    /**
     * @see IRUsuarioOrganizacion#consultarUsuarioOrganizacion(String,boolean)
     */
    public UsuarioOrganizacionDTO consultarUsuarioOrganizacion(String loginUsuario, boolean modoSeguro)
            throws DocumentosException;

    /**
     * @see IRUsuarioOrganizacion#consultarUsuarioOrganizacion(UsuarioOrganizacionDTO)
     */
    public List<UsuarioOrganizacionDTO> consultarUsuarioOrganizacion(UsuarioOrganizacionDTO usuarioOrganizacionDTO);

    /**
     * @see IRUsuarioOrganizacion#consultarRol()
     */
    public List<RolDTO> consultarRol();

    /**
     * @see IRUsuarioOrganizacion#registrarUsuarioOrganizacion(UsuarioOrganizacionDTO, List)
     */
    public UsuarioOrganizacionDTO registrarUsuarioOrganizacion(UsuarioOrganizacionDTO usuarioOrganizacionDTO,
            List<RolDTO> rolesUsuario) throws DocumentosException;
    
    /**
     * @see IRUsuarioOrganizacion#registrarUsuarioOrganizacionCifrado(UsuarioOrganizacionDTO, List)
     */
    public UsuarioOrganizacionDTO registrarUsuarioOrganizacionCifrado(UsuarioOrganizacionDTO usuarioOrganizacionDTO,
            List<RolDTO> rolesUsuario) throws DocumentosException;
    
    /**
     * @see IRUsuarioOrganizacion#actualizarUsuarioOrganizacionCifrado(UsuarioOrganizacionDTO, List)
     */
    public UsuarioOrganizacionDTO actualizarUsuarioOrganizacionCifrado(UsuarioOrganizacionDTO usuarioOrganizacionDTO,
            List<RolDTO> rolesUsuario) throws DocumentosException;

    /**
     * @see IRUsuarioOrganizacion#autenticarusuarioOrganizacionDTO(String, String)
     */
    public UsuarioOrganizacionDTO autenticarUsuarioOrganizacionDTO(String usuario, String contrasena)
            throws DocumentosException;

}
