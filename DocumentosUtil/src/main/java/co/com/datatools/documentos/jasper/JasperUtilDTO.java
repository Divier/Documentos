package co.com.datatools.documentos.jasper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import co.com.datatools.documentos.enumeraciones.EnumFormatoDocumento;

/**
 * DTO para generar instancia de jasper util pasando los parametros necesarios
 * @author julio.pinzon
 **/
public class JasperUtilDTO implements Serializable  {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Indica si es subreporte o reporte principal
     */
    private boolean isSubreporte;
    
    /**
     * Mapa de parametros enviados para la generacion del documento
     */
    private List<Map<String, Object>> jasperDatasourceList;
    
    /**
     * Utilizado para generar la vista preliminar con los subreportes y valores por defecto
     * Nombres de los grupos generados
     */
    private Map<String, Object> groupNames;
    /**
     * Indica si es preliminar o es la generacion del jasper principal
     */
    private boolean isPreliminar;

    private String preliminarImage;
    private String signImage;
    private String xmlDocumento;
    private String jasperPath;
    private String nombreDocumento;
    private EnumFormatoDocumento formato;

    /**
     * Path de reportes
     */
    private String pathReportes;

    /**
     * Path de documentos
     */
    private String pathDocumentos;

    /**
     * Path de imagenes
     */
    private String pathImagenes;

    /**
     * Path temporal de reportes
     */
    private String pathTemporalGuardaReporte;
    
    /**
     * Path de contexto
     */
    private String contextPath;
    
    
    public boolean isSubreporte() {
        return isSubreporte;
    }
    public void setSubreporte(boolean isSubreporte) {
        this.isSubreporte = isSubreporte;
    }
    public List<Map<String, Object>> getJasperDatasourceList() {
        return jasperDatasourceList;
    }
    public void setJasperDatasourceList(List<Map<String, Object>> jasperDatasourceList) {
        this.jasperDatasourceList = jasperDatasourceList;
    }
    public Map<String, Object> getGroupNames() {
        return groupNames;
    }
    public void setGroupNames(Map<String, Object> groupNames) {
        this.groupNames = groupNames;
    }
    public boolean isPreliminar() {
        return isPreliminar;
    }
    public void setPreliminar(boolean isPreliminar) {
        this.isPreliminar = isPreliminar;
    }
    public String getPreliminarImage() {
        return preliminarImage;
    }
    public void setPreliminarImage(String preliminarImage) {
        this.preliminarImage = preliminarImage;
    }
    public String getSignImage() {
        return signImage;
    }
    public void setSignImage(String signImage) {
        this.signImage = signImage;
    }
    public String getXmlDocumento() {
        return xmlDocumento;
    }
    public void setXmlDocumento(String xmlDocumento) {
        this.xmlDocumento = xmlDocumento;
    }
    public String getJasperPath() {
        return jasperPath;
    }
    public void setJasperPath(String jasperPath) {
        this.jasperPath = jasperPath;
    }
    public String getNombreDocumento() {
        return nombreDocumento;
    }
    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }
    public EnumFormatoDocumento getFormato() {
        return formato;
    }
    public void setFormato(EnumFormatoDocumento formato) {
        this.formato = formato;
    }
    public String getPathReportes() {
        return pathReportes;
    }
    public void setPathReportes(String pathReportes) {
        this.pathReportes = pathReportes;
    }
    public String getPathDocumentos() {
        return pathDocumentos;
    }
    public void setPathDocumentos(String pathDocumentos) {
        this.pathDocumentos = pathDocumentos;
    }
    public String getPathImagenes() {
        return pathImagenes;
    }
    public void setPathImagenes(String pathImagenes) {
        this.pathImagenes = pathImagenes;
    }
    public String getPathTemporalGuardaReporte() {
        return pathTemporalGuardaReporte;
    }
    public void setPathTemporalGuardaReporte(String pathTemporalGuardaReporte) {
        this.pathTemporalGuardaReporte = pathTemporalGuardaReporte;
    }
    public String getContextPath() {
        return contextPath;
    }
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
    
    
}