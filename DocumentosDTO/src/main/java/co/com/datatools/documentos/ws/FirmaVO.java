/**
 * 
 */
package co.com.datatools.documentos.ws;

import java.io.Serializable;

/**
 * Clase inmutable que contiene la firma de un funcionario.
 * 
 * @author robert.bautista
 * 
 */
public class FirmaVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Contiene la firma del funcionario.
     */
    private byte[] firma;

    /**
     * Contiene el numero de documento del funcionario al cual pertenece la firma.
     */
    private String numeroDocumento;

    /**
     * Contiene el tipo de identificacion del funcionario al cual pertenece la firma
     */
    private String tipoDocumento;
    
    public FirmaVO() {
        super();
    }

    /**
     * Constructor con todos los campos de la clase
     * 
     * @param firma
     *            firma del funcionario
     * @param numeroDocumento
     *            numero de documento del funcionario al cual pertenece la firma
     * @param tipoDocumento
     *            tipo de documento del funcionario al cual pertenece la firma
     */
    public FirmaVO(byte[] firma, String numeroDocumento, String tipoDocumento) {
        super();
        this.firma = firma;
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = tipoDocumento;
    }

    public byte[] getFirma() {
        return this.firma;
    }

    public String getNumeroDocumento() {
        return this.numeroDocumento;
    }

    public String getTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setFirma(byte[] firma) {
        this.firma = firma;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

}
