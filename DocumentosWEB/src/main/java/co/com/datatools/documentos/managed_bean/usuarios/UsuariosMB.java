package co.com.datatools.documentos.managed_bean.usuarios;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jboss.logging.Logger;

import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.managed_bean.comun.AbstractDocumentosSuperMB;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRUsuarioOrganizacion;

/**
 * Managed Bean que se encarga de controlar la interfaz de administración de usuarios
 * 
 * @author sergio.torres (30/04/2015)
 * 
 */
@ManagedBean
@SessionScoped
public class UsuariosMB extends AbstractDocumentosSuperMB {

    Logger logger = Logger.getLogger(UsuariosMB.class);

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * Atributos de consulta
     */
    private UsuarioOrganizacionDTO usuarioDTO;
    private List<UsuarioOrganizacionDTO> usuarios;
    private boolean consultaRealizada;

    /**
     * Atributos generales
     */
    @EJB
    private IRUsuarioOrganizacion irUsuarioOrganizacion;

    /**
     * Constructor de la clase por defecto
     * 
     * @author sergio.torres (30/04/2015)
     */
    public UsuariosMB() {
        usuarioDTO = new UsuarioOrganizacionDTO();
        consultaRealizada = false;
    }

    /**
     * Consulta usuarios obteniendo los filtros diligenciados en la interfaz
     * 
     * @return null para permtir la ejecución de ajax
     * 
     * @author sergio.torres (30/04/2015)
     */
    public String consultarUsuarios() {
        consultaRealizada = true;
        usuarios = irUsuarioOrganizacion.consultarUsuarioOrganizacion(usuarioDTO);
        return null;
    }

    /**
     * Permite preparar los datos para mostrar la pantalla de registro de usuarios
     * 
     * @return navegacion a la pagina solicitada
     * 
     * @author sergio.torres (06/05/2015)
     */
    public String irRegistrarUsuarios() {
        removeSessionObject("usuariosRegistroMB");
        return "/protected/usuarios/registrar-usuario";
    }

    /**
     * @return the usuarioDTO
     */
    public UsuarioOrganizacionDTO getUsuarioDTO() {
        return usuarioDTO;
    }

    /**
     * @param usuarioDTO
     *            the usuarioDTO to set
     */
    public void setUsuarioDTO(UsuarioOrganizacionDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }

    /**
     * @return the usuarios
     */
    public List<UsuarioOrganizacionDTO> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios
     *            the usuarios to set
     */
    public void setUsuarios(List<UsuarioOrganizacionDTO> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the consultaRealizada
     */
    public boolean isConsultaRealizada() {
        return consultaRealizada;
    }

    /**
     * @param consultaRealizada
     *            the consultaRealizada to set
     */
    public void setConsultaRealizada(boolean consultaRealizada) {
        this.consultaRealizada = consultaRealizada;
    }

}
