package co.com.datatools.documentos.excepcion;

import javax.ejb.ApplicationException;

/**
 * Excepcion general de seguridad
 * 
 * @author Felipe Martinez
 */
@ApplicationException(rollback = true)
public class DocumentosRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor con mensaje adicional a la informacion de excepcion causante
     * 
     * @param message
     *            mensaje adicional
     * @param cause
     *            causa real de la excepcion
     */
    public DocumentosRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor de excepcion con mensaje simple
     * 
     * @param message
     *            mensaje basico de la excepcion
     */
    public DocumentosRuntimeException(String message) {
        super(message);

    }

    /**
     * Constructor de excepcion con la causa del error
     * 
     * @param cause
     *            causa de la excepcion
     */
    public DocumentosRuntimeException(Throwable cause) {
        super(cause);

    }
}
