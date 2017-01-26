package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.entidades.Plantilla;
import co.com.datatools.documentos.entidades.Proceso;
import co.com.datatools.documentos.entidades.UsuarioOrganizacion;
import co.com.datatools.documentos.plantillas.PlantillaDTO;



/** 
 *
 * @author Pedro.Moncada
 * @version 2.0
 **/ 
public class PlantillaHelper {

	public static PlantillaDTO toPlantillaDTO(Plantilla plantilla){
		PlantillaDTO plantillaDTO = new PlantillaDTO();
		plantillaDTO.setIdPlantilla(plantilla.getIdPlantilla());
		plantillaDTO.setFechaCreacion(plantilla.getFechaCreacion());
		plantillaDTO.setFechaFin(plantilla.getFechaFin());
		plantillaDTO.setFechaInicio(plantilla.getFechaInicio());
		plantillaDTO.setNombrePlantilla(plantilla.getNombrePlantilla());
        plantillaDTO.setCodigoPlantilla(plantilla.getCodigoPlantilla());
		plantillaDTO.setPlantillaBloqueada(plantilla.getPlantillaBloqueada());
		plantillaDTO.setVersionPlantilla(plantilla.getVersionPlantilla());
		return plantillaDTO;
	}

	public static Plantilla toPlantilla(PlantillaDTO plantillaDTO, Plantilla plantilla){
		if(null==plantilla){
			plantilla = new Plantilla();
		}
		plantilla.setIdPlantilla(plantillaDTO.getIdPlantilla());
		plantilla.setFechaCreacion(plantillaDTO.getFechaCreacion());
		plantilla.setFechaFin(plantillaDTO.getFechaFin());
		plantilla.setFechaInicio(plantillaDTO.getFechaInicio());
		plantilla.setNombrePlantilla(plantillaDTO.getNombrePlantilla());
		plantilla.setCodigoPlantilla(plantillaDTO.getCodigoPlantilla());
		plantilla.setPlantillaBloqueada(plantillaDTO.getPlantillaBloqueada());
		plantilla.setVersionPlantilla(plantillaDTO.getVersionPlantilla());
		return plantilla;
	}

	/**
	 * Completa el objeto plantilla con los dto relacionados
	 * @param plantillaDTO
	 * @param plantilla
	 * @return Objeto plantilla completo
	 */
    public static Plantilla completarPlantilla(PlantillaDTO plantillaDTO, Plantilla plantilla){
        if (plantillaDTO.getUsuarioOrganizacionDTO() != null) {
            UsuarioOrganizacion usuarioOrganizacion = UsuarioOrganizacionHelper.toUsuarioOrganizacion(
                    (plantillaDTO.getUsuarioOrganizacionDTO()), new UsuarioOrganizacion());
            plantilla.setUsuarioOrganizacion(usuarioOrganizacion);
        }
        if (plantillaDTO.getProcesoDTO() != null) {
            Proceso proceso = ProcesoHelper.toProceso((plantillaDTO.getProcesoDTO()), new Proceso());
            plantilla.setProceso(proceso);
        }
        if (plantillaDTO.getPlantillaOrigenDTO() != null) {
            Plantilla plantillaOrigen = PlantillaHelper.toPlantilla((plantillaDTO.getPlantillaOrigenDTO()),
                    new Plantilla());
            plantilla.setPlantilla(plantillaOrigen);
        }
        return plantilla;
    }

}