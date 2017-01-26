package co.com.datatools.documentos.error;

/**
 * Interfaces a implementar por las enumeraciones que manejan errores
 * 
 * @author julio.pinzon
 * 
 */
public interface ErrorInfo {
    public String getCodigoError();

    public String getDescError();
    
    public Integer getCodigoNumericoError();
}
