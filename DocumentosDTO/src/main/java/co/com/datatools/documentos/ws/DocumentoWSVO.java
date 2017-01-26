package co.com.datatools.documentos.ws;

import java.io.Serializable;

import co.com.datatools.documentos.enumeraciones.EnumFormatoDocumento;

/**
 * Objeto VO para generar documento
 * 
 * @author julio.pinzon
 **/
public class DocumentoWSVO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    /**
     * Codigo en el sistema de la plantilla, para generar el documento
     */
    private String codigoPlantilla;
    /**
     * Fecha de generacion del documento en formato yyyy-MM-dd HH:mm:ss<br>
     * Debe ser menor o igual a la fecha actual
     */
    private String fechaGeneracion;
    /**
     * Descripcion asociada al archivo generado<br>
     * Longitud maxima de 300 caracteres
     */
    private String descripcion;
    /**
     * Formato de documento a generar @see {@link EnumFormatoDocumento}
     */
    private String formato;
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
    /**
     * Ubicacion donde se va a poner el documento generado de manera que sea diciente en la logica de negocio<br/>
     * Se maneja de la misma manera que el sistema de archivos de Linux<br>
     * Longitud maxima de 255 caracteres
     */
    private String ubicacion;

    // Constructors Declaration

    public DocumentoWSVO() {

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

    public String getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(String fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getValoresPlantilla() {
        return valoresPlantilla;
    }

    public void setValoresPlantilla(String valoresPlantilla) {
        this.valoresPlantilla = valoresPlantilla;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    // Finish the class
}