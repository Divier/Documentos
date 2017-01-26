/**
 * 
 */
package co.com.datatools.documentos.managed_bean.comun;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jboss.logging.Logger;

import co.com.datatools.documentos.documento.UtilSeguridad;
import co.com.datatools.documentos.enumeraciones.EnumNavegacion;

/**
 * @author julio.pinzon
 * @author sergio.torres (17-feb-2016) cambio para especializar la clase.
 * 
 */
@ManagedBean
@SessionScoped
public class PrincipalMB extends AbstractDocumentosSuperMB {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final static Logger logger = Logger.getLogger(PrincipalMB.class.getName());

    private final String versionArtefacto = UtilSeguridad.cargarVersionArtefactoDoc();

    public PrincipalMB() {
    }

    /**
     * Se encarga de ir a la ruta de navegacion recibida
     * 
     * @param ruta
     * @return
     */
    public String navegar(String recurso) {
        logger.debug("PrincipalMB::navegar - " + recurso);
        limpiarSesion();
        return EnumNavegacion.buscarEnumNavegacionRecurso(recurso).getRuta();
    }

    public String cerrarSesion() {
        logger.debug("PrincipalMB::cerrarSesion");
        invalidateSession();
        return EnumNavegacion.salida.getRuta();
    }

    public String getVersionArtefacto() {
        return versionArtefacto;
    }

}
