package co.com.datatools.documentos.managed_bean.comun;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.jboss.security.SecurityContextAssociation;

import co.com.datatools.documentos.constantes.ConstantesDocumentos;

/**
 * Clase que permite realizar la autenticación contra el módulo de ingreso para estár atado a seguridad general de las aplicaciones
 * 
 * @author sergio.torres (25-Ene-2016)
 * 
 */
@ManagedBean
@SessionScoped
public class AutenticacionDocumentosMB extends AbstractDocumentosSuperMB {

    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = Logger.getLogger(AutenticacionDocumentosMB.class.getName());

    private static final String SECURITY_JBOSS_EXCEPTION = "org.jboss.security.exception";
    private static final String URL_PRINCIPAL = "/protected/index.xhtml?faces-redirect=true";
    private static final String URL_RECUPERAR_PASS = "/public/enviarVinculoPw.xhtml";

    private String ip;
    private String login;
    private String password;

    public AutenticacionDocumentosMB() {
        super();
    }

    @PostConstruct
    public void init() {
        LOGGER.debug("AutenticacionDocumentosMB::init()");

    }

    /**
     * Se encarga de autenticar contra el módulo JAAS asignado
     * 
     * @return String - faces response
     */
    public String login() {
        HttpServletRequest request = getHttpRequest();
        ip = request.getRemoteAddr();

        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        String nombreApp = ctx.getInitParameter(ConstantesDocumentos.ID_APLICACION);
        // boolean principal = true;
        try {
            request.login(nombreApp + "@" + ip + "#" + login, password);
            // principal = validarEstadoPassword();
            // if (principal) {
            //
            // } else {
            // return URL_CAMBIAR_PASS;
            // }
            return URL_PRINCIPAL;
        } catch (ServletException e) {
            validarLoginException();
            return null;
        }
    }

    /**
     * Permite extraer la excepcion lanzada desde el módulo de ingreso para mostrar el mensaje que contienen la excepción
     * 
     */
    private void validarLoginException() {
        Exception ex = (Exception) SecurityContextAssociation.getContextInfo(SECURITY_JBOSS_EXCEPTION);
        if (ex != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ex.fillInStackTrace().getMessage()));
        }
    }

    /**
     * Se encarga de redirigir al flujo para solicitar una contraseña
     */
    public String recuperarContraseña() {
        return URL_RECUPERAR_PASS;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
