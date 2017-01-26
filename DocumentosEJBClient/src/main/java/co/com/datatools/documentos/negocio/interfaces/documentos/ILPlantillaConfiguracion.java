package co.com.datatools.documentos.negocio.interfaces.documentos;

import javax.ejb.Local;

import co.com.datatools.documentos.plantillas.PlantillaConfiguracionDTO;

@Local
public interface ILPlantillaConfiguracion {

    /**
     * @see IRPlantillaConfiguracion#consultarConfiguracionPlantilla(PlantillaConfiguracionDTO, boolean)
     */
    public PlantillaConfiguracionDTO consultarConfiguracionPlantilla(PlantillaConfiguracionDTO plantillaDTO,
            boolean proceso);
}
