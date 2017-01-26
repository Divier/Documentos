package co.com.datatools.documentos.managed_bean.comun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;

import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.constantes.ConstantesDocumentos;
import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.enumeraciones.EnumNavegacion;
import co.com.datatools.documentos.enumeraciones.EnumOperacion;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRUsuarioOrganizacion;
import co.com.datatools.documentos.util.ConstantesManagedBean;
import co.com.datatools.seguridad.dto.autenticacion.ResultadoAutenticacionDto;
import co.com.datatools.seguridad.dto.autenticacion.ResultadoAutenticacionDto.EstadoAutenticacion;
import co.com.datatools.seguridad.dto.autorizacion.MenuDto;
import co.com.datatools.seguridad.dto.autorizacion.OperacionDto;
import co.com.datatools.seguridad.interfaces.IRAutorizacion;
import co.com.datatools.seguridad.utilidades.ConstantesSeguridad;
import co.com.datatools.seguridad.web.mb.AutenticacionBean;

/**
 * MB a utilizar para implementar la generacion dinamica del menu de C2
 * 
 * @author felipe.martinez giovanni.velandia(mod 02-02-2016)
 * @author sergio.torres (17-feb-2016) Modificación de especialización de clase para ajuste a SSO
 */
@ManagedBean
@SessionScoped
public class MenuMB extends AbstractDocumentosSuperMB {

    private final static Logger LOGGER = Logger.getLogger(MenuMB.class.getName());

    private static final long serialVersionUID = 1L;

    @EJB
    private IRAutorizacion autorizacionEjb;

    @EJB
    private IRUsuarioOrganizacion irUsuarioOrganizacion;

    private List<MenuDto> menuDtos;
    private List<MenuDto> menuTempDtos;
    private UsuarioOrganizacionDTO usuarioOrganizacion;
    private String nombreAplicacion;

    private Map<String, List<OperacionDto>> operacionesRecurso;

    @ManagedProperty(value = "#{adminCuentaUsuarioMB}")
    private AdminCuentaUsuarioMB adminCuentaUsuarioMB;

    /**
     * DATOS DE VALIDACION DE PASSWORD Y CARGA DE MENU
     */
    private static final String URL_CAMBIAR_PASS = "/protected/usuarios/actualizarPw.xhtml";
    private AutenticacionBean autenticador;

    public MenuMB() {
        super();
        autenticador = new AutenticacionBean();
    }

    @PostConstruct
    public void init() {
        HttpServletRequest httpRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
                .getContext();
        nombreAplicacion = servletContext.getInitParameter(ConstantesDocumentos.ID_APLICACION);
        operacionesRecurso = new HashMap<String, List<OperacionDto>>();
        addSessionObject(ConstantesSeguridad.NombresManagedBeans.AUTENTICACION_BEAN, autenticador);

        // validar el estado del password que ingreso al sistema
        if (!validarEstadoPassword()) {
            try {
                getFacesContext().getExternalContext().redirect(httpRequest.getContextPath() + URL_CAMBIAR_PASS);
            } catch (IOException e) {
                // No debe ocurrir
                e.printStackTrace();
            }
        }

        // Subir usuario aplicacion a session
        try {
            usuarioOrganizacion = irUsuarioOrganizacion.consultarUsuarioOrganizacion(getUsuarioSesion(), false);
            addSessionObject(ConstantesManagedBean.NOMBRE_OBJ_USUARIO_ORGANIZACION, usuarioOrganizacion);
        } catch (Exception e) {
            addErrorMessage(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL, ConstantesGeneral.LLAVE_MENSAJE_ERROR_SISTEMA);
            return;
        }

    }

    /**
     * Se en carga de pintar el menu con sus recursos desde seguridad
     * 
     * @author giovanni.velandia
     * @param nombresRoles
     */
    public void actualizarMenuSesion(List<String> nombresRoles) {
        LOGGER.debug(MenuMB.class.getName().concat(":actualizarMenuSesion(List<String>)"));
        AutenticacionBean autenticacionBean = findSessionObject(AutenticacionBean.class,
                ConstantesSeguridad.NombresManagedBeans.AUTENTICACION_BEAN);

        menuTempDtos = new ArrayList<MenuDto>();

        if (autenticacionBean != null && autenticacionBean.getUsuario() != null) {
            menuTempDtos = autorizacionEjb.consultarRecursosMenu(nombreAplicacion);
        }

        menuDtos = new ArrayList<MenuDto>();
        if (menuTempDtos != null && !menuTempDtos.isEmpty()) {
            // Agregar opciones al menubar de acuerdo a los permisos
            boolean recursoPermitido = false;

            for (MenuDto menuDto : menuTempDtos) {
                recursoPermitido = false;

                // Si tiene permiso de ingresar al recurso, agregarla al menu
                recursoPermitido = autorizacionEjb.esRecursoPermitidoRoles(nombreAplicacion, nombresRoles,
                        menuDto.getRecurso().getNombreRecurso());

                if (recursoPermitido) {
                    menuDtos.add(menuDto);
                }
            }
        }
        consultarOperacionesRecursos();
    }

    /**
     * Se encarga de consultar todas las operaciones de los recursos cargados en el menu
     * 
     * @author giovanni.velandia
     */
    private void consultarOperacionesRecursos() {
        for (MenuDto menuDto : menuTempDtos) {
            for (MenuDto subMenuDto : menuDto.getSubmenu()) {

                List<OperacionDto> operacionDtos = new ArrayList<OperacionDto>();
                Collection<OperacionDto> operacionTempDtos = autorizacionEjb.consultarOperacionesPermitidasUsuario(
                        nombreAplicacion, getLogin(), subMenuDto.getRecurso().getNombreRecurso());

                for (OperacionDto operacionDto : operacionTempDtos) {
                    operacionDtos.add(operacionDto);
                }

                operacionesRecurso.put(subMenuDto.getRecurso().getNombreRecurso(), operacionDtos);
            }
        }
    }

    /**
     * Se encarga de buscar en el Enum el recurso por medio de la url para poder dar o negar el permiso en la aplicacion
     * 
     * @return
     */
    public boolean validarNavegacion(String url, String login) {
        LOGGER.debug(MenuMB.class.getName().concat(":validarNavegacion(String)"));
        boolean esPermitido = false;
        EnumNavegacion enumNavegacion = EnumNavegacion.buscarEnumNavegacion(url);
        if (enumNavegacion != null && !enumNavegacion.equals(EnumNavegacion.inicio)
                && !enumNavegacion.equals(EnumNavegacion.salida)) {
            esPermitido = autorizacionEjb.esRecursoPermitidoUsuario(nombreAplicacion, login,
                    enumNavegacion.getRecurso());
        } else {
            return true;
        }
        return esPermitido;
    }

    /**
     * Metodo que se encarga de verificar si la operacion solicitada esta con permiso en seguridad
     * 
     * @author giovanni.velandia
     * @param recurso
     * @param enumOperacion
     * @return
     */
    public boolean validarOperacion(String recurso, String operacion) {

        EnumNavegacion enumNavegacion = EnumNavegacion.valueOf(recurso);

        List<OperacionDto> operacionDtos = operacionesRecurso.get(enumNavegacion.getRecurso());

        EnumOperacion enumOperacion = EnumOperacion.valueOf(operacion);

        for (OperacionDto operacionDto : operacionDtos) {
            if (operacionDto.getNombreOperacion().equals(enumOperacion.toString())) {
                return true;
            }

        }
        return false;
    }

    /**
     * Se encarga de registrar el ingreso exitoso del usuario y a la vez evaluar: si el usuario esta bloqueado pero debe levantarse el bloqueo y si el
     * usuario tiene password temporal para que solicite cambio, tambien cierra otra sesion que puede encontrarse abierta
     */
    public boolean validarEstadoPassword() {
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String nombreAplicacion = context.getInitParameter(ConstantesDocumentos.ID_APLICACION);

        ResultadoAutenticacionDto resultadoAutenticacionDto = autenticador.validarEstadoPassword(nombreAplicacion,
                getUsuarioSesion(), getIp());
        // usuarioDto = resultadoAutenticacionDto.getUsuario();

        actualizarMenuSesion(resultadoAutenticacionDto.getRoles());
        adminCuentaUsuarioMB.setLogin(resultadoAutenticacionDto.getUsuario().getLogin());

        if (!resultadoAutenticacionDto.getEstadoAutenticacion().equals(EstadoAutenticacion.OK)) {

            EstadoAutenticacion estadoAutenticacion = resultadoAutenticacionDto.getEstadoAutenticacion();
            if (estadoAutenticacion.equals(EstadoAutenticacion.PASS_TEMPORAL)
                    || estadoAutenticacion.equals(EstadoAutenticacion.PASS_VENCIDO)) {
                adminCuentaUsuarioMB.setMostrarFormulario(true);
                adminCuentaUsuarioMB.setUsuarioDto(resultadoAutenticacionDto.getUsuario());
                if (estadoAutenticacion.equals(EstadoAutenticacion.PASS_VENCIDO)) {
                    adminCuentaUsuarioMB.setPwVencido(true);
                }
                return false;
            }
        } else {
            /*
             * // Agregar el usuario_persona a la sesion addSessionObject(ConstantesManagedBean.OBJ_USUARIO_PERSONA_AUTENTICA,
             * usuarioPersonaAutenticado.get(0)); // Redireccionar en caso de que haya una url de inicio a la cual ir de un acceso sin sesion //
             * valida realizado con anterioridad final String urlInicial = findSessionObject(String.class, ConstantesManagedBean.URL_INICIAL); if
             * (!StringUtils.isBlank(urlInicial) && !urlInicial.contains(ConstantesManagedBean.PAGINA_MAIN)) { HttpServletResponse httpRs =
             * (HttpServletResponse) FacesContext.getCurrentInstance() .getExternalContext().getResponse(); try { httpRs.sendRedirect(urlInicial); }
             * catch (IOException e) { logger.error("Error realizando el redirect a la url inicial: " + urlInicial + ". ", e); } }
             * 
             * inicializarOrganismo(getHttpRequest().getSession()); addSessionObject(ConstantesManagedBean.NOMBRE_OBJ_LOGIN,
             * autenticador.getUsuario().getLogin());
             */
        }
        return true;
    }

    public List<MenuDto> getMenuDtos() {
        return menuDtos;
    }

    public void setMenuDtos(List<MenuDto> menuDtos) {
        this.menuDtos = menuDtos;
    }

    private String getIp() {
        return getHttpRequest().getRemoteAddr();
    }

    public AdminCuentaUsuarioMB getAdminCuentaUsuarioMB() {
        return adminCuentaUsuarioMB;
    }

    public void setAdminCuentaUsuarioMB(AdminCuentaUsuarioMB adminCuentaUsuarioMB) {
        this.adminCuentaUsuarioMB = adminCuentaUsuarioMB;
    }

    public UsuarioOrganizacionDTO getUsuarioOrganizacion() {
        return usuarioOrganizacion;
    }

    public void setUsuarioOrganizacion(UsuarioOrganizacionDTO usuarioOrganizacion) {
        this.usuarioOrganizacion = usuarioOrganizacion;
    }

}
