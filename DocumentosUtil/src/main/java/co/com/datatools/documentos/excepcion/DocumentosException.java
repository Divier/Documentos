package co.com.datatools.documentos.excepcion;

import javax.ejb.ApplicationException;

import co.com.datatools.documentos.error.ErrorInfo;

/**
 * Excepcion para manejar los errores controlados de negocio de Seguridad
 * 
 * @author felipe.martinez
 */
@ApplicationException(rollback = true)
public class DocumentosException extends Exception {


    protected static final String MSG_ERROR_REQUERIDO = "La excepcion debe contener el error catalogado";
    private static final long serialVersionUID = 1L;

    /**
     * Objeto con la informacion de error
     */
    ErrorInfo errorInfo;

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public DocumentosException(ErrorInfo errorInfo) {
        super(errorInfo != null ? (errorInfo.getCodigoError() + " - " + errorInfo.getDescError()) : "Unknow");
        this.errorInfo = errorInfo;
        if (this.errorInfo == null) {
            throw new DocumentosIllegalArgumentException(MSG_ERROR_REQUERIDO);
        }
    }

    public DocumentosException(ErrorInfo errorInfo, String contextualInfo) {
        super(errorInfo != null ? (errorInfo.getCodigoError() + " - " + errorInfo.getDescError()) : "Unknow" + "[ "
                + contextualInfo + " ]");
        this.errorInfo = errorInfo;
        if (this.errorInfo == null) {
            throw new DocumentosIllegalArgumentException(MSG_ERROR_REQUERIDO);
        }
    }

    @Deprecated
    public DocumentosException(String msg) {
        super(msg);
    }

    @Deprecated
    public DocumentosException(String message, Throwable cause) {
        super(message, cause);
    }

    protected void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    
}
