package co.com.datatools.documentos.managed_bean.comun;

import java.util.Enumeration;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import co.com.datatools.util.web.mb.AbstractSwfManagedBean;

public abstract class AbstractDocumentosSuperMB extends AbstractSwfManagedBean {

    private static final Logger LOGGER = Logger.getLogger(AbstractDocumentosSuperMB.class);
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Permite obtener el usuario que ha ingresado en el sistema
     * 
     * @return Usuario que corresponde al login utilizado en el login de la aplicacion
     */
    public String getUsuarioSesion() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        String usuario = null;
        if (context != null && context.getUserPrincipal() != null) {
            usuario = context.getUserPrincipal().getName();
            usuario = usuario.split("#")[1];
        }
        return usuario;
    }

    /**
     * Permite limpiar los elementos transientes de la sesion web
     * 
     */
    public void limpiarSesion() {
        HttpSession sesion = getHttpRequest().getSession();
        Enumeration<String> objetosSesion = sesion.getAttributeNames();
        while (objetosSesion.hasMoreElements()) {
            String llave = objetosSesion.nextElement();
            boolean remover = true;
            // Objeto sesion MenuMB
            if (llave.equals("menuMB")) {
                remover = false;
            }
            // Objeto sesion PrincipalMB
            if (llave.equals("principalMB")) {
                remover = false;

            }
            // Objeto sesion AutenticacionDocumentosMB
            if (llave.equals("autenticacionDocumentosMB")) {
                remover = false;

            }
            // Solo saca de sesion los ManagedBean
            if (!llave.endsWith("MB")) {
                remover = false;
            }
            // Remover objeto sesion
            if (remover) {
                removeSessionObject(llave);
            }
        }
    }

}
