package co.com.datatools.documentos.plantillas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import co.com.datatools.documentos.administracion.ContextoAplicacionVariableDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.TipoVariableDTO;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class VariableDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private int idVariable;
    private String descripcionVariable;
    private String formatoVariable;
    private Integer longitudVariable;
    private String nombreVariable;
    private String valorDefecto;
    private TipoVariableDTO tipoVariableDTO;
    private ContextoAplicacionVariableDTO contextoAplicacionVariableDTO;
    private ProcesoDTO procesoDTO;
    private List<PlantillaDTO> plantillas;
    private byte[] imagen;
    private String palabraClave;
    private Date fechaCreacion;
    private String encodedImagen;

    // Constructors Declaration

    public VariableDTO() {

    }

    // Start Methods Declaration

    public int getIdVariable() {
        return idVariable;
    }

    public void setIdVariable(int idVariable) {
        this.idVariable = idVariable;
    }

    public String getDescripcionVariable() {
        return descripcionVariable;
    }

    public void setDescripcionVariable(String descripcionVariable) {
        this.descripcionVariable = descripcionVariable;
    }

    public String getFormatoVariable() {
        return formatoVariable;
    }

    public void setFormatoVariable(String formatoVariable) {
        this.formatoVariable = formatoVariable;
    }

    public Integer getLongitudVariable() {
        return longitudVariable;
    }

    public void setLongitudVariable(Integer longitudVariable) {
        this.longitudVariable = longitudVariable;
    }

    public String getNombreVariable() {
        return nombreVariable;
    }

    public void setNombreVariable(String nombreVariable) {
        this.nombreVariable = nombreVariable;
    }

    public String getValorDefecto() {
        return valorDefecto;
    }

    public void setValorDefecto(String valorDefecto) {
        this.valorDefecto = valorDefecto;
    }

    public TipoVariableDTO getTipoVariableDTO() {
        return tipoVariableDTO;
    }

    public void setTipoVariableDTO(TipoVariableDTO tipoVariableDTO) {
        this.tipoVariableDTO = tipoVariableDTO;
    }

    public ContextoAplicacionVariableDTO getContextoAplicacionVariableDTO() {
        return contextoAplicacionVariableDTO;
    }

    public void setContextoAplicacionVariableDTO(ContextoAplicacionVariableDTO contextoAplicacionVariableDTO) {
        this.contextoAplicacionVariableDTO = contextoAplicacionVariableDTO;
    }

    public ProcesoDTO getProcesoDTO() {
        return procesoDTO;
    }

    public void setProcesoDTO(ProcesoDTO procesoDTO) {
        this.procesoDTO = procesoDTO;
    }

    public List<PlantillaDTO> getPlantillas() {
        return plantillas;
    }

    public void setPlantillas(List<PlantillaDTO> plantillas) {
        this.plantillas = plantillas;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getPalabraClave() {
        return palabraClave;
    }

    public void setPalabraClave(String palabraClave) {
        this.palabraClave = palabraClave;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEncodedImagen() {
        return encodedImagen;
    }

    public void setEncodedImagen(String encodedImagen) {
        this.encodedImagen = encodedImagen;
    }

    @Override
    public int hashCode() {
        int result = 31 + idVariable;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;            
        } else if (obj == null || (getClass() != obj.getClass())) {
            return false;            
        } else {
            VariableDTO other = (VariableDTO) obj;
            if (idVariable != other.idVariable) {
                return false;                
            }
        }
        return true;
    } 
    
    
    // Finish the class
}