/**
 * 
 */
package co.com.datatools.documentos.ws;

import java.io.Serializable;

/**
 * @author robert.bautista
 * 
 */
public class FuncionarioVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombreFuncionario;
    private String numeroDocumIdent;
    private byte[] firma;
    private String fechaInicialFuncionario;
    private String fechaFinalFuncionario;
    private String siglaTipoIdentificacion;
    private String nombreTipoIdentificacion;
    private Integer idCargo;
    private String fechaInicioCargo;
    private String fechaFinCargo;

    public FuncionarioVO() {
    }

    public FuncionarioVO(String nombreFuncionario, String numeroDocumIdent, byte[] firma, String fechaFinalFuncionario,
            String fechaInicialFuncionario, String siglaTipoIdentificacion, String nombreTipoIdentificacion,
            Integer idCargo, String fechaInicioCargo, String fechaFinCargo) {
        super();
        this.nombreFuncionario = nombreFuncionario;
        this.numeroDocumIdent = numeroDocumIdent;
        this.firma = firma;
        this.fechaFinalFuncionario = fechaFinalFuncionario;
        this.fechaInicialFuncionario = fechaInicialFuncionario;
        this.siglaTipoIdentificacion = siglaTipoIdentificacion;
        this.nombreTipoIdentificacion = nombreTipoIdentificacion;
        this.idCargo = idCargo;
        this.fechaInicioCargo = fechaInicioCargo;
        this.fechaFinCargo = fechaFinCargo;
    }

    public String getNombreFuncionario() {
        return this.nombreFuncionario;
    }

    public String getNumeroDocumIdent() {
        return this.numeroDocumIdent;
    }

    public byte[] getFirma() {
        return this.firma;
    }

    public String getFechaFinalFuncionario() {
        return this.fechaFinalFuncionario;
    }

    public String getFechaInicialFuncionario() {
        return this.fechaInicialFuncionario;
    }

    public String getSiglaTipoIdentificacion() {
        return this.siglaTipoIdentificacion;
    }

    public String getNombreTipoIdentificacion() {
        return this.nombreTipoIdentificacion;
    }

    public Integer getIdCargo() {
        return this.idCargo;
    }

    public String getFechaInicioCargo() {
        return this.fechaInicioCargo;
    }

    public String getFechaFinCargo() {
        return this.fechaFinCargo;
    }

    public void setNombreFuncionario(String nombreFuncionario) {
        this.nombreFuncionario = nombreFuncionario;
    }

    public void setNumeroDocumIdent(String numeroDocumIdent) {
        this.numeroDocumIdent = numeroDocumIdent;
    }

    public void setFirma(byte[] firma) {
        this.firma = firma;
    }

    public void setFechaInicialFuncionario(String fechaInicialFuncionario) {
        this.fechaInicialFuncionario = fechaInicialFuncionario;
    }

    public void setFechaFinalFuncionario(String fechaFinalFuncionario) {
        this.fechaFinalFuncionario = fechaFinalFuncionario;
    }

    public void setSiglaTipoIdentificacion(String siglaTipoIdentificacion) {
        this.siglaTipoIdentificacion = siglaTipoIdentificacion;
    }

    public void setNombreTipoIdentificacion(String nombreTipoIdentificacion) {
        this.nombreTipoIdentificacion = nombreTipoIdentificacion;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public void setFechaInicioCargo(String fechaInicioCargo) {
        this.fechaInicioCargo = fechaInicioCargo;
    }

    public void setFechaFinCargo(String fechaFinCargo) {
        this.fechaFinCargo = fechaFinCargo;
    }

}
