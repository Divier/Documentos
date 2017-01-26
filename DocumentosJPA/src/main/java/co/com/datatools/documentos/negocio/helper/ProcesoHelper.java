package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.entidades.Proceso;

/**
 *
 * @author Pedro.Moncada
 * @version 2.0
 **/
public class ProcesoHelper {

    public static ProcesoDTO toProcesoDTO(Proceso proceso) {
        ProcesoDTO procesoDTO = new ProcesoDTO();
        procesoDTO.setIdProceso(proceso.getIdProceso());
        procesoDTO.setDescripcionProceso(proceso.getDescripcionProceso());
        procesoDTO.setNombreProceso(proceso.getNombreProceso());
        procesoDTO.setCodigoProceso(proceso.getCodigoProceso());
        return procesoDTO;
    }

    public static Proceso toProceso(ProcesoDTO procesoDTO, Proceso proceso) {
        if (null == proceso) {
            proceso = new Proceso();
        }
        proceso.setIdProceso(procesoDTO.getIdProceso());
        proceso.setDescripcionProceso(procesoDTO.getDescripcionProceso());
        proceso.setNombreProceso(procesoDTO.getNombreProceso());
        proceso.setCodigoProceso(procesoDTO.getCodigoProceso());
        return proceso;
    }

}