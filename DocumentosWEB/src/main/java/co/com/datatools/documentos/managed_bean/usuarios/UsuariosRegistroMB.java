package co.com.datatools.documentos.managed_bean.usuarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.administracion.tmp.RolDTO;
import co.com.datatools.documentos.excepcion.DocumentosException;
import co.com.datatools.documentos.managed_bean.comun.AbstractDocumentosSuperMB;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRUsuarioOrganizacion;
/**
 * Se encarga de controlar el registro de un usuario
 * 
 * @author sergio.torres
 * 
 */
@ManagedBean
@SessionScoped
public class UsuariosRegistroMB extends AbstractDocumentosSuperMB {

    private static final Logger LOGGER = Logger.getLogger(UsuariosRegistroMB.class);
    private static final String NOMBRE_BUNDLE_PARAMETROS = "labelAdmin";

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * Atributos de regsitro
     */
    private UsuarioOrganizacionDTO usuarioRegistroDTO;
    private List<RolDTO> rolesSistema;
    private List<RolDTO> rolesUsuario;

    @EJB
    private IRUsuarioOrganizacion irUsuarioOrganizacion;

    public UsuariosRegistroMB() {
        usuarioRegistroDTO = new UsuarioOrganizacionDTO();
        usuarioRegistroDTO.setFuncionarioDTO(new FuncionarioDTO());
    }

    @PostConstruct
    public void inicializar() {
        rolesUsuario = new ArrayList<>();
        rolesSistema = irUsuarioOrganizacion.consultarRol();
    }

    /**
     * Se encarga de crear un popup con el contenido de la consulta de funcionarios
     * 
     * @author sergio.torres (04/05/2015)
     */
    public void verPopupFuncionarios() {
        LOGGER.info("UsuariosRegistroMB::verPopupFuncionarios");
        removeSessionObject("funcionarioMB");
        // Mostrar popup
        Map<String, Object> params = new HashMap<>();
        params.put("modal", true);
        params.put("resizable", false);
        params.put("contentHeight", 600);
        RequestContext.getCurrentInstance().openDialog("/protected/funcionarios/consulta-funcionarios", params, null);
    }

    /**
     * Se encarga de capturar el evento del cierre del popup para obtener el funcionario seleccionado
     * 
     * @param event
     * @author sergio.torres (04/05/2015)
     */
    public void onFuncionarioSeleccionado(SelectEvent event) {
        LOGGER.info("UsuariosRegistroMB::onFuncionarioSeleccionado");
        FuncionarioDTO funcionarioSeleccionado = (FuncionarioDTO) event.getObject();
        if (funcionarioSeleccionado != null) {
            usuarioRegistroDTO.setFuncionarioDTO(funcionarioSeleccionado);
        }
    }

    /**
     * Realiza las validaciones correspondientes y registra el usuario en el sistema
     * 
     * @return ruta a la consulta de usuarios
     */
    public String registrarUsuario() {
        LOGGER.info("UsuariosRegistroMB::registrarUsuario");
        // Validación de roles
        if (rolesUsuario.isEmpty()) {
            addErrorMessage(NOMBRE_BUNDLE_PARAMETROS, "msg_roles_usuario_requerido");
            return null;
        }
        if (usuarioRegistroDTO.getFuncionarioDTO().getNombreFuncionario() == null) {
            addErrorMessage(NOMBRE_BUNDLE_PARAMETROS, "msg_funcionario_requerido");
            return null;
        }
        try {
            irUsuarioOrganizacion.registrarUsuarioOrganizacion(usuarioRegistroDTO, rolesUsuario);
            addInfoMessage(NOMBRE_BUNDLE_PARAMETROS, "msg_usuario_guardado");
            getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
        } catch (DocumentosException e) {
            addErrorMessage(NOMBRE_BUNDLE_PARAMETROS, "msg_usuario_ya_existe");
            return null;
        }
        return "/protected/usuarios/consultar-usuarios";
    }

    public UsuarioOrganizacionDTO getUsuarioRegistroDTO() {
        return usuarioRegistroDTO;
    }

    public void setUsuarioRegistroDTO(UsuarioOrganizacionDTO usuarioRegistroDTO) {
        this.usuarioRegistroDTO = usuarioRegistroDTO;
    }

    public List<RolDTO> getRolesUsuario() {
        return rolesUsuario;
    }

    public void setRolesUsuario(List<RolDTO> rolesUsuario) {
        this.rolesUsuario = rolesUsuario;
    }

    public List<RolDTO> getRolesSistema() {
        return rolesSistema;
    }

    public void setRolesSistema(List<RolDTO> rolesSistema) {
        this.rolesSistema = rolesSistema;
    }

}
