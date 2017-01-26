/**
 * 
 */
package co.com.datatools.documentos.ws;

import java.io.Serializable;

/**
 * Clase generada para representar un documento a editar.
 * 
 * @author robert.bautista
 * 
 */
public class DocumentoEditadoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Contiene el codigo del documento
     */
    private Long codigoDocumento;

    /**
     * <p>
     * Valores de la plantilla en formato JSON de un objeto de tipo map<String, Object>
     * </p>
     * <p>
     * Los key de cada valor del mapa corresponde al valor de la variable en la plantilla
     * </p>
     */
    private String valoresPlantilla;
    
    /**
     * Usuario registrado del sistema que es el encargado de generar el documento
     * 
     * sergio.torres (13-May-2015) - Cambio en el manejo del usuario que genera el documento, se maneja un usuario de autenticación y otro diferente
     * de generacion de documento
     * 
     */
    private String usuario;   

    public DocumentoEditadoVO() {
    }

    public DocumentoEditadoVO(long codigoDocumento, String valoresPlantilla, String usuario) {
        this.codigoDocumento = codigoDocumento;
        this.valoresPlantilla = valoresPlantilla;
        this.usuario = usuario;
    }

    public Long getCodigoDocumento() {
        return codigoDocumento;
    }

    public String getValoresPlantilla() {
        return valoresPlantilla;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setCodigoDocumento(Long codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public void setValoresPlantilla(String valoresPlantilla) {
        this.valoresPlantilla = valoresPlantilla;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    

}
