package co.com.datatools.documentos.negocio.interfaces.documentos;

import java.util.List;

import javax.ejb.Local;

import co.com.datatools.documentos.plantillas.JasperPlantillaDTO;

@Local
public interface ILJasperPlantilla {

    /**
     * @see IRJasperPlantilla#consultarJasperPlantilla(JasperPlantillaDTO)
     */
    public List<JasperPlantillaDTO> consultarJasperPlantilla(JasperPlantillaDTO jasperPlantillaDTO);

    /**
     * @see IRJasperPlantilla#registrarJasperPlantilla(JasperPlantillaDTO)
     */
    public JasperPlantillaDTO registrarJasperPlantilla(JasperPlantillaDTO jasperPlantillaDTO);
}
