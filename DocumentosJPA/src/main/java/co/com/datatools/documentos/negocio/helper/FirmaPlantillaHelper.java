package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.entidades.FirmaPlantilla;
import co.com.datatools.documentos.plantillas.FirmaPlantillaDTO;

/**
 * Permite la trabsformaci�n entre entidad y DTo de FirmaPlantilla
 **/
public class FirmaPlantillaHelper {

    /**
     * Obtiene un DTo a partir de una entidad FirmaPlantilla
     * 
     * @param firmaPlantilla
     *            entidad a convertir en DTO
     * @return DTO con os datos basicos de la entidad
     */
    public static FirmaPlantillaDTO toFirmaPlantillaDTO(FirmaPlantilla firmaPlantilla) {
        FirmaPlantillaDTO firmaPlantillaDTO = new FirmaPlantillaDTO();
        firmaPlantillaDTO.setIdFirmaPlantilla(firmaPlantilla.getIdFirmaPlantilla());
        firmaPlantillaDTO.setMostrarNombreFuncionario(firmaPlantilla.getMostrarNombreFuncionario());
        firmaPlantillaDTO.setMostrarCargoFuncionario(firmaPlantilla.getMostrarCargoFuncionario());
        firmaPlantillaDTO.setCodigoFirmaPlantilla(firmaPlantilla.getCodigoFirmaPlantilla());
        return firmaPlantillaDTO;
    }

    /**
     * Obtiene una entidad FirmaPlantilla a partir de su DTO
     * 
     * @param firmaPlantillaDTO
     *            DTO a convertir en entidad
     * @param firmaPlantilla
     *            Entidad base sobre la cual se asignan los datos, si es nula se instancia
     * @return Entidad con los datos basicos enviados en el DTO
     */
    public static FirmaPlantilla toFirmaPlantilla(FirmaPlantillaDTO firmaPlantillaDTO, FirmaPlantilla firmaPlantilla) {
        if (null == firmaPlantilla) {
            firmaPlantilla = new FirmaPlantilla();
        }
        firmaPlantilla.setIdFirmaPlantilla(firmaPlantillaDTO.getIdFirmaPlantilla());
        firmaPlantilla.setMostrarNombreFuncionario(firmaPlantillaDTO.isMostrarNombreFuncionario());
        firmaPlantilla.setMostrarCargoFuncionario(firmaPlantillaDTO.isMostrarCargoFuncionario());
        firmaPlantilla.setCodigoFirmaPlantilla(firmaPlantillaDTO.getCodigoFirmaPlantilla());
        return firmaPlantilla;
    }

}