package co.com.datatools.documentos.plantillas;

import java.io.Serializable;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class GrupoPlantillaDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private int idTab;
    private String nombreTab;
    private String contenidoXmlPlantilla;
    private boolean disabled;

    // Constructors Declaration

    public GrupoPlantillaDTO() {

    }

    // Start Methods Declaration


    public int getIdTab() {
        return idTab;
    }

    public void setIdTab(int idTab) {
        this.idTab = idTab;
    }

    public String getNombreTab() {
        return nombreTab;
    }

    public void setNombreTab(String nombreTab) {
        this.nombreTab = nombreTab;
    }

    public String getContenidoXmlPlantilla() {
        return contenidoXmlPlantilla;
    }

    public void setContenidoXmlPlantilla(String contenidoXmlPlantilla) {
        this.contenidoXmlPlantilla = contenidoXmlPlantilla;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public int hashCode() {
        int result = 1;
        if(nombreTab == null) {
            result = 31;            
        } else {
            result = 31 + nombreTab.hashCode();            
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;            
        } else if (obj == null || (getClass() != obj.getClass())) {
            return false;            
        } else {
            GrupoPlantillaDTO other = (GrupoPlantillaDTO) obj;
            if ((nombreTab == null && other.nombreTab != null) || 
                    (nombreTab != null && !nombreTab.equals(other.nombreTab))) {
                return false;                     
            }       
        }
        return true;
    }

    // Finish the class
    
}