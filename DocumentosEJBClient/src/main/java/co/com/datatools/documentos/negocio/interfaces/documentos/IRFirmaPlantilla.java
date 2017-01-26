package co.com.datatools.documentos.negocio.interfaces.documentos;

import java.util.List;

import javax.ejb.Remote;

import co.com.datatools.documentos.plantillas.FirmaPlantillaDTO;

@Remote
public interface IRFirmaPlantilla {

    /**
     * Se encarga de consultar las firmas asociadas a la plantilla que cumplen con los criterios recibidos
     * 
     * @param firmaPlantillaDTO
     *            Contiene los datos filtrar en la consulta
     * @return Lista de FirmaPlantillaDTOs
     * @author dixon.alvarez<br>
     *         claudia.rodriguez(mod 2014-09-19)
     */
    public List<FirmaPlantillaDTO> consultarFirmaPlantilla(FirmaPlantillaDTO firmaPlantillaDTO);

    /**
     * Se encarga de guardar un nuevo registro de firma de plantilla
     * 
     * @param firmaPlantillaDTO
     *            Contiene los datos de la entidad a persistir
     * @return FirmaPlantilla persistida
     * @author dixon.alvarez<br>
     *         claudia.rodriguez(mod 2014-09-19)
     */
    public FirmaPlantillaDTO registrarFirmaPlantilla(FirmaPlantillaDTO firmaPlantillaDTO);
}
