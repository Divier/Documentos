package co.com.datatools.documentos.negocio.interfaces.documentos;

import javax.ejb.Remote;

import co.com.datatools.documentos.plantillas.PlantillaConfiguracionDTO;

@Remote
public interface IRPlantillaConfiguracion {

    /**
     * Se encarga de consultar la configuracion de una plantilla
     * 
     * @param plantillaConfiguracionDTO
     *            Contiene los datos filtrar en la consulta
     * @param proceso
     *            Indica si se debe buscar por proceso de la plantilla
     * @return Configuracion si la encuentra, de lo contrario null
     * @author julio.pinzon 2016-09-08
     */
    public PlantillaConfiguracionDTO consultarConfiguracionPlantilla(
            PlantillaConfiguracionDTO plantillaConfiguracionDTO, boolean proceso);
}
