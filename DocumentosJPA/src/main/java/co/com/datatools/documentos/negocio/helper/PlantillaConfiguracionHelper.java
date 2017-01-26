package co.com.datatools.documentos.negocio.helper;

import java.util.ArrayList;
import java.util.List;

import co.com.datatools.documentos.entidades.PlantillaConfiguracion;
import co.com.datatools.documentos.plantillas.PlantillaConfiguracionDTO;
import co.com.datatools.documentos.plantillas.PlantillaDTO;
import co.com.datatools.documentos.ws.PlantillaConfiguracionVO;

/**
 *
 * @author Pedro.Moncada
 * @version 2.0
 **/
public class PlantillaConfiguracionHelper {

    public static PlantillaConfiguracionDTO toPlantillaConfiguracionDTO(PlantillaConfiguracion entidad) {
        PlantillaConfiguracionDTO dto = new PlantillaConfiguracionDTO();
        dto.setIdPlantillaConfig(entidad.getIdPlantillaConfig());
        dto.setConsulta(entidad.getConsulta());
        dto.setOrdenVariables(entidad.getOrdenVariables());
        dto.setParametros(entidad.getParametros());
        dto.setParametrosUbicacion(entidad.getParametrosUbicacion());
        dto.setUbicacion(entidad.getUbicacion());
        dto.setNombreGrupo(entidad.getNombreGrupo());
        return dto;
    }

    public static PlantillaConfiguracion toPlantillaConfiguracion(PlantillaConfiguracionDTO dto,
            PlantillaConfiguracion entidad) {
        if (null == entidad) {
            entidad = new PlantillaConfiguracion();
        }
        dto.setIdPlantillaConfig(entidad.getIdPlantillaConfig());
        dto.setConsulta(entidad.getConsulta());
        dto.setOrdenVariables(entidad.getOrdenVariables());
        dto.setParametros(entidad.getParametros());
        dto.setParametrosUbicacion(entidad.getParametrosUbicacion());
        dto.setUbicacion(entidad.getUbicacion());
        dto.setNombreGrupo(entidad.getNombreGrupo());
        return entidad;
    }

    public static List<PlantillaConfiguracionDTO> toPlantillaConfiguracionDTOList(
            List<PlantillaConfiguracion> listEntidad) {

        List<PlantillaConfiguracionDTO> listDto = new ArrayList<PlantillaConfiguracionDTO>();
        for (PlantillaConfiguracion entidad : listEntidad) {
            listDto.add(toPlantillaConfiguracionDTO(entidad));
        }
        return listDto;
    }

    /**
     * Completa el objeto configuracion con los dto relacionados
     * 
     * @param entidad
     * @return Objeto configuracion completo
     * @author julio.pinzon 2016-09-08
     */
    public static PlantillaConfiguracionDTO completarPlantillaConfiguracion(PlantillaConfiguracion entidad) {

        PlantillaConfiguracionDTO dto = toPlantillaConfiguracionDTO(entidad);
        if (entidad.getPlantilla() != null) {
            PlantillaDTO plantilla = PlantillaHelper.toPlantillaDTO(entidad.getPlantilla());
            dto.setPlantilla(plantilla);
        }
        if (entidad.getConfiguracionPadre() != null) {
            PlantillaConfiguracionDTO configuracionPadre = PlantillaConfiguracionHelper
                    .toPlantillaConfiguracionDTO(entidad.getConfiguracionPadre());
            dto.setConfiguracionPadre(configuracionPadre);
        }
        return dto;
    }

    /**
     * Completa el objeto configuracion con los dto relacionados
     * 
     * @param entidad
     * @return Objeto configuracion completo
     * @author julio.pinzon 2016-09-08
     */
    public static PlantillaConfiguracionVO toPlantillaConfiguracionVO(PlantillaConfiguracionDTO dto) {

        PlantillaConfiguracionVO vo = new PlantillaConfiguracionVO();
        vo.setConsulta(dto.getConsulta());
        vo.setOrdenVariables(dto.getOrdenVariables());
        vo.setParametros(dto.getParametros());
        vo.setParametrosUbicacion(dto.getParametrosUbicacion());
        vo.setUbicacion(dto.getUbicacion());
        vo.setNombreGrupo(dto.getNombreGrupo());
        if (dto.getConfiguracionesPlantilla() != null) {
            List<PlantillaConfiguracionVO> configuracionesPlantilla = new ArrayList<>();
            for (PlantillaConfiguracionDTO config : dto.getConfiguracionesPlantilla()) {
                configuracionesPlantilla.add(toPlantillaConfiguracionVO(config));
            }
            vo.setConfiguracionesPlantilla(configuracionesPlantilla);
        }
        return vo;
    }

}