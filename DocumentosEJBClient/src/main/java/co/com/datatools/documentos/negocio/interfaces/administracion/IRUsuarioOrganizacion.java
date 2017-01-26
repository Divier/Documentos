package co.com.datatools.documentos.negocio.interfaces.administracion;

import java.util.List;

import javax.ejb.Remote;

import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.administracion.tmp.RolDTO;
import co.com.datatools.documentos.excepcion.DocumentosException;

/**
 * @author dixon.alvarez
 * 
 */

@Remote
public interface IRUsuarioOrganizacion {

    /**
     * Se encarga de consultar un usuario por su id exacto en el sistema
     * 
     * @param idUsuario
     *            Id del usuario a buscar
     * @return UsuarioDTO
     * @author dixon.alvarez
     */
    public UsuarioOrganizacionDTO consultarUsuarioOrganizacion(long idUsuario);

    /**
     * Se encarga de consultar un usuario por su login exacto en el sistema.<br/>
     * El <strong>modoSeguro</strong> indica que si no existe el usuario en el sistema, crea un usuario de no acceso al sistema para no detener la
     * creacion de documentos
     * 
     * @param loginUsuario
     * @param idOrganizacion
     * @return
     * @author<p> sergio.torres 13-may-2015<br/>
     *            Cambio para agregar el modo seguro al metodo </p>
     */
    public UsuarioOrganizacionDTO consultarUsuarioOrganizacion(String loginUsuario, boolean modoSeguro)
            throws DocumentosException;

    /**
     * Permite consultar un conjunto de usuarios que cumplen con los filtros
     * 
     * @param usuarioOrganizacionDTO
     *            -
     *            <p>
     *            <strong>loginUsuario <strong>Permite consultar por todo o parte del login del usuario sin tener en cuenta mayusculas o minusculas
     *            </p>
     * 
     * @return List
     *         <p>
     *         Con los objetos <strong>UsuarioOrganizacionDTO</strong> que cumplen con los criterios recibidos en <strong>param</strong>
     *         </p>
     * 
     * @author sergio.torres(30/04/2015)
     */
    public List<UsuarioOrganizacionDTO> consultarUsuarioOrganizacion(UsuarioOrganizacionDTO usuarioOrganizacionDTO);

    /**
     * Permite consultar los Roles definidos para el sistema de documentos
     * 
     * @return List
     *         <p>
     *         Con los objetos <strong>RolDTO</strong> que se encuentran en el sistema
     *         </p>
     * @author sergio.torres (04/05/2015)
     */
    public List<RolDTO> consultarRol();

    /**
     * Se encarga de almacenar un nuevo usuario en el sistema
     * 
     * 
     * @param usuarioOrganizacionDTO
     *            - datos del usuario
     * @param rolesUsuario
     *            - Lista de roles que se van a asignar al usuario
     * @throws DocumentosException
     *             <p>
     *             Excepcion si el usuario ya existe en el sistema
     *             </p>
     * 
     * @author sergio.torres (04/05/2015)
     */
    public UsuarioOrganizacionDTO registrarUsuarioOrganizacion(UsuarioOrganizacionDTO usuarioOrganizacionDTO,
            List<RolDTO> rolesUsuario) throws DocumentosException;

    /**
     * Se encarga de almacenar un nuevo usuario en el sistema. Su diferencia se ve en que NO cifra el password, pues ya viene cifrado.
     * 
     * @param usuarioOrganizacionDTO
     *            - datos del usuario
     * @param rolesUsuario
     *            - Lista de roles que se van a asignar al usuario
     * @throws DocumentosException
     *             <p>
     *             Excepcion si el usuario ya existe en el sistema
     *             </p>
     * 
     * @author sergio.torres (04/05/2015)
     */
    public UsuarioOrganizacionDTO registrarUsuarioOrganizacionCifrado(UsuarioOrganizacionDTO usuarioOrganizacionDTO,
            List<RolDTO> rolesUsuario) throws DocumentosException;

    /**
     * Actualiza el usuario indicado, su clave ya viene cifrada.
     * 
     * @param usuarioOrganizacionDTO
     *            - datos del usuario
     * @param rolesUsuario
     *            - Lista de roles que se van a asignar al usuario
     * @return Retorna la información del usuario actualizado
     * @throws DocumentosException
     * @author luis.forero(2015-07-30)
     */
    public UsuarioOrganizacionDTO actualizarUsuarioOrganizacionCifrado(UsuarioOrganizacionDTO usuarioOrganizacionDTO,
            List<RolDTO> rolesUsuario) throws DocumentosException;

    /**
     * Se encarga de validar las credenciales de acceso del usuario recibidas como parámetro
     * 
     * @param usuario
     *            <p>
     *            <strong>Obligatorio</strong> Nombre del usuario de acuerdo a la fuente de datos, es sensible a mayusculas y minusculas
     *            </p>
     * @param contrasena
     *            <p>
     *            <strong>Obligatorio</strong> Contrasena del usuario codificada
     *            </p>
     * @return UsuarioOrganizacionDTO
     *         <p>
     *         Con los datos del usuario que cumple con la validación
     *         </p>
     * @throws DocumentosAutenticacionExcepcion
     *             <p>
     *             Excepcion si las credenciales de acceso no coinciden
     *             </p>
     */
    public UsuarioOrganizacionDTO autenticarUsuarioOrganizacionDTO(String usuario, String contrasena)
            throws DocumentosException;

}
