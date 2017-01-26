package co.com.datatools.documentos.negocio.interfaces.documentos;

import java.util.List;

import javax.ejb.Remote;

import co.com.datatools.documentos.plantillas.JasperPlantillaDTO;

@Remote
public interface IRJasperPlantilla {

    /**
     * Se encarga de consultar las jaspers asociadas a la plantilla que cumplen con los criterios recibidos
     * 
     * @param jasperPlantillaDTO
     *            Contiene los datos filtrar en la consulta
     * @return Lista de JasperPlantillaDTOs
     * @author julio.pinzon
     */
    public List<JasperPlantillaDTO> consultarJasperPlantilla(JasperPlantillaDTO jasperPlantillaDTO);

    /**
     * Se encarga de guardar un nuevo registro de jasper de plantilla
     * 
     * @param jasperPlantillaDTO
     *            Contiene los datos de la entidad a persistir
     * @return JasperPlantilla persistida
     * @author julio.pinzon
     */
    public JasperPlantillaDTO registrarJasperPlantilla(JasperPlantillaDTO jasperPlantillaDTO);
}
