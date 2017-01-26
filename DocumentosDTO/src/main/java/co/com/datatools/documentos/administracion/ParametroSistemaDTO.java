package co.com.datatools.documentos.administracion;

import java.io.Serializable;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class ParametroSistemaDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private int idParametroSistema;
    private String nombreParametro;
    private TipoDatoDTO tipoDatoDTO;
    private String valorParametro;
    private String descripcionParametro;
    private boolean editable;

    // Constructors Declaration

    public ParametroSistemaDTO() {

    }

    // Start Methods Declaration

    public int getIdParametroSistema() {
        return idParametroSistema;
    }

    public void setIdParametroSistema(int idParametroSistema) {
        this.idParametroSistema = idParametroSistema;
    }

    public String getNombreParametro() {
        return nombreParametro;
    }

    public void setNombreParametro(String nombreParametro) {
        this.nombreParametro = nombreParametro;
    }

    public TipoDatoDTO getTipoDatoDTO() {
        return tipoDatoDTO;
    }

    public void setTipoDatoDTO(TipoDatoDTO tipoDatoDTO) {
        this.tipoDatoDTO = tipoDatoDTO;
    }

    public String getValorParametro() {
        return valorParametro;
    }

    public void setValorParametro(String valorParametro) {
        this.valorParametro = valorParametro;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getDescripcionParametro() {
        return descripcionParametro;
    }

    public void setDescripcionParametro(String descripcionParametro) {
        this.descripcionParametro = descripcionParametro;
    }

    // Finish the class
}