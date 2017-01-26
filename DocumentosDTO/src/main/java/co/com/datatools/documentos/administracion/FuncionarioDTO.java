package co.com.datatools.documentos.administracion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class FuncionarioDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private long idFuncionario;
    private String nombreFuncionario;
    private String numeroDocumIdent;
    private byte[] firma;
    private Date fechaFinalFuncionario;
    private Date fechaInicialFuncionario;
    private List<FuncionarioCargoDTO> listFuncionarioCargosDTO;
    private List<UsuarioOrganizacionDTO> listUsuarioOrganizacionsDTO;
    private String siglaTipoIdentificacion;
    private String nombreTipoIdentificacion;

    // Constructors Declaration

    public FuncionarioDTO() {

    }

    // Start Methods Declaration

    public long getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNombreFuncionario() {
        return nombreFuncionario;
    }

    public void setNombreFuncionario(String nombreFuncionario) {
        this.nombreFuncionario = nombreFuncionario;
    }

    public String getNumeroDocumIdent() {
        return numeroDocumIdent;
    }

    public void setNumeroDocumIdent(String numeroDocumIdent) {
        this.numeroDocumIdent = numeroDocumIdent;
    }

    public Date getFechaFinalFuncionario() {
        return fechaFinalFuncionario;
    }

    public void setFechaFinalFuncionario(Date fechaFinalFuncionario) {
        this.fechaFinalFuncionario = fechaFinalFuncionario;
    }

    public Date getFechaInicialFuncionario() {
        return fechaInicialFuncionario;
    }

    public void setFechaInicialFuncionario(Date fechaInicialFuncionario) {
        this.fechaInicialFuncionario = fechaInicialFuncionario;
    }

    public byte[] getFirma() {
        return firma;
    }

    public void setFirma(byte[] firma) {
        this.firma = firma;
    }

    public List<FuncionarioCargoDTO> getListFuncionarioCargosDTO() {
        return listFuncionarioCargosDTO;
    }

    public void setListFuncionarioCargosDTO(List<FuncionarioCargoDTO> listFuncionarioCargosDTO) {
        this.listFuncionarioCargosDTO = listFuncionarioCargosDTO;
    }

    public List<UsuarioOrganizacionDTO> getListUsuarioOrganizacionsDTO() {
        return listUsuarioOrganizacionsDTO;
    }

    public void setListUsuarioOrganizacionsDTO(List<UsuarioOrganizacionDTO> listUsuarioOrganizacionsDTO) {
        this.listUsuarioOrganizacionsDTO = listUsuarioOrganizacionsDTO;
    }

    public String getSiglaTipoIdentificacion() {
        return siglaTipoIdentificacion;
    }

    public void setSiglaTipoIdentificacion(String siglaTipoIdentificacion) {
        this.siglaTipoIdentificacion = siglaTipoIdentificacion;
    }

    public String getNombreTipoIdentificacion() {
        return nombreTipoIdentificacion;
    }

    public void setNombreTipoIdentificacion(String nombreTipoIdentificacion) {
        this.nombreTipoIdentificacion = nombreTipoIdentificacion;
    }

    // Finish the class
}