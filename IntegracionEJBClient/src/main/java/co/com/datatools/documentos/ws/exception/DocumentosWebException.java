package co.com.datatools.documentos.ws.exception;

import javax.ejb.ApplicationException;

import co.com.datatools.documentos.error.ErrorInfo;
import co.com.datatools.documentos.excepcion.DocumentosIllegalArgumentException;

/**
 * Excepcion para manejar los errores controlados del negocio de la interfaz de integracion WEB de Documentos
 * 
 * @author robert.bautista
 */
@ApplicationException(rollback = true)
public class DocumentosWebException extends Exception {

    private static final long serialVersionUID = 1L;
    protected static final String MSG_ERROR_REQUERIDO = "La excepcion debe contener el error catalogado";

    /**
     * Objeto con la informacion de error
     */
    // ErrorInfo errorInfo;
    //
    // public ErrorInfo getErrorInfo() {
    // return errorInfo;
    // }

    @SuppressWarnings("deprecation")
    public DocumentosWebException(ErrorInfo errorInfo) {
        super(errorInfo != null ? (errorInfo.getCodigoNumericoError().toString()) : "-1");
        // setErrorInfo(errorInfo);
        if (errorInfo == null) {
            throw new DocumentosIllegalArgumentException(MSG_ERROR_REQUERIDO);
        }
    }
    //
    // public DocumentosWebException(ErrorInfo errorInfo, String contextualInfo) {
    // super(errorInfo != null ? (errorInfo.getCodigoError() + " - " + errorInfo.getDescError()) : "Unknow" + "[ "
    // + contextualInfo + " ]");
    // this.errorInfo = errorInfo;
    // if (this.errorInfo == null) {
    // throw new DocumentosIllegalArgumentException(MSG_ERROR_REQUERIDO);
    // }
    // }
    //
    // protected void setErrorInfo(ErrorInfo errorInfo) {
    // this.errorInfo = errorInfo;
    // }

}
