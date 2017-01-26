package co.com.datatools.documentos.negocio.interfaces.documentos;

import java.util.List;

import javax.ejb.Local;

import co.com.datatools.documentos.plantillas.FirmaPlantillaDTO;

@Local
public interface ILFirmaPlantilla {

    /**
     * @see IRFirmaPlantilla#consultarFirmaPlantilla(FirmaPlantillaDTO)
     */
    public List<FirmaPlantillaDTO> consultarFirmaPlantilla(FirmaPlantillaDTO firmaPlantillaDTO);

    /**
     * @see IRFirmaPlantilla#registrarFirmaPlantilla(FirmaPlantillaDTO)
     */
    public FirmaPlantillaDTO registrarFirmaPlantilla(FirmaPlantillaDTO firmaPlantillaDTO);
}
