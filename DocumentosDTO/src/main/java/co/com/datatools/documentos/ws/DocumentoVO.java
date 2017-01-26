package co.com.datatools.documentos.ws;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import co.com.datatools.documentos.enumeraciones.EnumFormatoDocumento;

/**
 * Objeto utilizado para generar documento
 * 
 * @author julio.pinzon
 **/
public class DocumentoVO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;

    /**
     * Contiene el codigo del documento
     */
    private Long codigoDocumento;

    /**
     * Codigo unico de la plantilla
     */
    private String codigoPlantilla;

    /**
     * Fecha de generacion del documento
     */
    private Date fechaGeneracion;

    /**
     * Descripcion del documento a generar
     */
    private String descripcion;

    /**
     * Formatos permitidos de generacion del documento
     */
    private EnumFormatoDocumento formato;

    /**
     * Valores a reemplazar en la plantilla para generar el documento
     */
    private Map<String, Object> valoresPlantilla;

    /**
     * Usuario que genera el documento
     */
    private String usuario;

    /**
     * Ubicacion donde se va a guardar el documento
     */
    private String ubicacion;

    // Constructors Declaration

    public DocumentoVO() {

    }

    // Start Methods Declaration

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoPlantilla() {
        return codigoPlantilla;
    }

    public void setCodigoPlantilla(String codigoPlantilla) {
        this.codigoPlantilla = codigoPlantilla;
    }

    public EnumFormatoDocumento getFormato() {
        return formato;
    }

    public void setFormato(EnumFormatoDocumento formato) {
        this.formato = formato;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Map<String, Object> getValoresPlantilla() {
        return valoresPlantilla;
    }

    public void setValoresPlantilla(Map<String, Object> data) {
        this.valoresPlantilla = data;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Long getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(Long codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    // Finish the class
}