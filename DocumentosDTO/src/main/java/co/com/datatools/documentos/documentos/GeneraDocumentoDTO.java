package co.com.datatools.documentos.documentos;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import co.com.datatools.documentos.enumeraciones.EnumFormatoDocumento;

/**
 * DTO para generar documento
 * @author julio.pinzon
 **/
public class GeneraDocumentoDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private Long codigoDocumento;
    private String codigoPlantilla;
    private Date fechaGeneracion;
    private String nombreDocumento;
    private String descripcionDocumento;
    private EnumFormatoDocumento formato;
    private Map<String, Object> data;
    private String usuario;
    private byte[] contenido;
    private String xmlDocumento;
    private String carpeta;
    private boolean preliminar;
    private boolean actualizar;
    private String contextPath;

    // Constructors Declaration

    public GeneraDocumentoDTO() {

    }

    // Start Methods Declaration

    public String getDescripcionDocumento() {
        return descripcionDocumento;
    }

    public void setDescripcionDocumento(String descripcionDocumento) {
        this.descripcionDocumento = descripcionDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
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

    public String getXmlDocumento() {
        return xmlDocumento;
    }

    public void setXmlDocumento(String xmlDocumento) {
        this.xmlDocumento = xmlDocumento;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(String carpeta) {
        this.carpeta = carpeta;
    }

    public boolean isPreliminar() {
        return preliminar;
    }

    public void setPreliminar(boolean preliminar) {
        this.preliminar = preliminar;
    }

    public boolean isActualizar() {
        return actualizar;
    }

    public void setActualizar(boolean actualizar) {
        this.actualizar = actualizar;
    }

    public Long getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(Long codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }


    // Finish the class
}