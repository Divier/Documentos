package co.com.datatools.documentos.negocio.interfaces.documentos;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.datatools.documentos.plantillas.PlantillaConsultaDTO;
import co.com.datatools.documentos.plantillas.PlantillaDTO;

@Local
public interface ILPlantilla {

    // CRUD DE TABLA PLANTILLAS

    /**
     * @see IRPlantilla#consultarPlantilla(PlantillaConsultaDTO)
     */
    public List<PlantillaDTO> consultarPlantilla(PlantillaConsultaDTO plantillaConsultaDTO);

    /**
     * @see IRPlantilla#registrarPlantilla(PlantillaDTO)
     */
    public PlantillaDTO registrarPlantilla(PlantillaDTO plantillaDTO);

    /**
     * @see IRPlantilla#actualizarPlantilla(PlantillaDTO)
     */
    public void actualizarPlantilla(PlantillaDTO plantillaDTO);

    /**
     * @see IRPlantilla#consultarPlantilla(PlantillaDTO, Date)
     */
    public List<PlantillaDTO> consultarPlantilla(PlantillaDTO plantillaDTO, Date fechaCorte);

    /**
     * @see IRPlantilla#validarPlantilla(PlantillaDTO)
     */
    public boolean validarPlantilla(PlantillaDTO plantillaDTO);

    /**
     * @see IRPlantilla#eliminarPlantilla(Integer)
     */
    public void eliminarPlantilla(Integer idPlantilla);

    /**
     * @see IRPlantilla#consultarPlantillaId(int)
     */
    public PlantillaDTO consultarPlantillaId(int idPlantilla);

    // FIN - CRUD DE TABLA PLANTILLAS

}
