/**
 * 
 */
package co.com.datatools.documentos.ws;

import java.io.Serializable;

/**
 * @author robert.bautista
 * 
 */
public class ProcesoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Campo obligatorio que contiene el id del proceso
     */
    private long idProceso;

    /**
     * Campo obligatorio que contiene el nombre del proceso
     */
    private String nombreProceso;

    /**
     * Campo obligatorio que contiene la descripcion del proceso.
     */
    private String descripcionProceso;

    /**
     * Contiene el id del proceso padre.
     */
    private Long idPadre;

    public ProcesoVO() {
        super();
    }

    public ProcesoVO(long idProceso, String nombreProceso, String descripcionProceso, Long idPadre) {
        this.idProceso = idProceso;
        this.nombreProceso = nombreProceso;
        this.descripcionProceso = descripcionProceso;
        this.idPadre = idPadre;
    }

    public long getIdProceso() {
        return this.idProceso;
    }

    public String getNombreProceso() {
        return this.nombreProceso;
    }

    public String getDescripcionProceso() {
        return this.descripcionProceso;
    }

    public Long getIdPadre() {
        return this.idPadre;
    }

    public void setIdProceso(long idProceso) {
        this.idProceso = idProceso;
    }

    public void setNombreProceso(String nombreProceso) {
        this.nombreProceso = nombreProceso;
    }

    public void setDescripcionProceso(String descripcionProceso) {
        this.descripcionProceso = descripcionProceso;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

}
