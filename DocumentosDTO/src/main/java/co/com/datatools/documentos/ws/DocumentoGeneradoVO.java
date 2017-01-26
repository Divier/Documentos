package co.com.datatools.documentos.ws;

import java.io.Serializable;

/**
 * Objeto VO de respuesta de generacion de documento
 * 
 * @author julio.pinzon
 **/
public class DocumentoGeneradoVO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    
    /**
     * Codigo asignado al documento luego de la generacion
     */
    private long codigoDocumento;
    
    /**
     * Version del documento generada (es relevante en acciones de modificacion de documentos)
     */
    private int version;
    
    /**
     * Nombre del documento en el sistema
     */
    private String nombre;
    
    /**
     * Almacena el contenido del archivo
     */
    private byte[] contenido;

    // Constructors Declaration

    public DocumentoGeneradoVO() {

    }

    // Start Methods Declaration

    public long getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(long codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }
    

    // Finish the class
}