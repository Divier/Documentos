package co.com.datatools.documentos.managed_bean.comun;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import co.com.datatools.documentos.enumeraciones.EnumParametrosSistema;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILParametroSistema;

/**
 * MB a utilizar para implementar el cambio dinamico de los formatos de fecha y moneda en el aplicativo
 * 
 * @author Jeison.Rodriguez
 *
 */
@ManagedBean
@SessionScoped
public class UtilidadMB extends AbstractDocumentosSuperMB {
    private static final long serialVersionUID = 1L;
    private String formatoFecha;
    private String formatoFechaCompleta;

    @EJB
    private ILParametroSistema ilParametroSistema;

    /**
     * retorna el formato de fecha corta
     * 
     * @return
     */
    public String modificarFormatoFechaCorta() {
        String[] valorParametroDTO = ilParametroSistema
                .consultarValorParametroSistema(EnumParametrosSistema.FORMATO_FECHA).split(" ");
        return valorParametroDTO[0];
    }

    /**
     * retorna el formato de fecha larga
     * 
     * @return
     */
    public String modificarFormatoFechaCompleta() {
        String valorParametroDTO = ilParametroSistema
                .consultarValorParametroSistema(EnumParametrosSistema.FORMATO_FECHA);
        return valorParametroDTO;
    }

    public String getFormatoFecha() {

        if (formatoFecha == null) {
            formatoFecha = modificarFormatoFechaCorta();
        }
        return formatoFecha;
    }

    public String getFormatoFechaCompleta() {
        if (formatoFechaCompleta == null) {
            formatoFechaCompleta = modificarFormatoFechaCompleta();
        }
        return formatoFechaCompleta;
    }

}
