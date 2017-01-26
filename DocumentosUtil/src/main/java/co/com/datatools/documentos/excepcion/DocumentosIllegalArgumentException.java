package co.com.datatools.documentos.excepcion;

import javax.ejb.ApplicationException;

/**
 * Excepcion para manejar errores de parametros ingresados a un metodo de negocio
 * 
 * @author felipe.martinez
 * 
 */
@ApplicationException(rollback = true)
public class DocumentosIllegalArgumentException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    public DocumentosIllegalArgumentException(String message) {
        super(message);
    }
}
