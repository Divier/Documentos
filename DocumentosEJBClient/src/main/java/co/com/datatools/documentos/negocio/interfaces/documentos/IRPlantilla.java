package co.com.datatools.documentos.negocio.interfaces.documentos;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import co.com.datatools.documentos.excepcion.DocumentosException;
import co.com.datatools.documentos.plantillas.PlantillaConsultaDTO;
import co.com.datatools.documentos.plantillas.PlantillaDTO;

@Remote
public interface IRPlantilla {

	// CRUD DE TABLA PLANTILLAS
    
    /**
     * Método que se encarga de consultar una plantilla de acuerdo a los 
     * par�metros recibidos en la versi�n que aplica a una fecha específica, si no se
     * envia ningun filtro consulta todos los registros, si no encuentra
     * resultados retorna la lista vacía
     * 
     * @param plantillaDTO
     *            Objeto que contiene los valores de filtros que se aplicarán a
     *            la comsulta
     * @param fechaCorte
     *            Fecha de corte para obtener plantilla adecuada para generar documento        
     * @return Lista de los plantillas consultados de acuerdo a los
     *         parametros enviados
     * @author dixon.alvarez
     */
    public List<PlantillaDTO> consultarPlantilla(PlantillaDTO plantillaDTO, Date fechaCorte);
	
	/**
	 * Método que se encarga de consultar una plantilla de acuerdo a los 
	 * par�metros recibidos en la versi�n que aplica a una fecha específica, si no se
	 * envia ningun filtro consulta todos los registros, si no encuentra
	 * resultados retorna la lista vacía
	 * 
	 * @param plantillaConsultaDTO
	 *            Objeto que contiene los valores de filtros que se aplicarán a
	 *            la comsulta     
	 * @return Lista de los plantillas consultados de acuerdo a los
	 *         parametros enviados
	 * @author dixon.alvarez
	 */
	public List<PlantillaDTO> consultarPlantilla(
	        PlantillaConsultaDTO plantillaConsultaDTO);

	/**
	 * Método que persiste la entidad Plantilla
	 * 
	 * @param plantillaDTO
	 *            Contiene los datos de la entidad a persistir
	 * @return DTO del Plantilla ingresado
	 * @author dixon.alvarez
	 * @throws DocumentosException 
	 * @throws SQLIntegrityConstraintViolationException 
	 */
	public PlantillaDTO registrarPlantilla(
			PlantillaDTO plantillaDTO);

	/**
	 * Metodo para modificar entidad Plantilla
	 * 
	 * @param plantillaDTO
	 *            Contiene los datos de la entidad a modificar
	 * @author dixon.alvarez
	 */
	public void actualizarPlantilla(PlantillaDTO plantillaDTO);
	
	/**
     * Método que se encarga de validar una plantilla de acuerdo a los 
     * parámetros recibidos de codigo o nombre
     * 
     * @param plantillaDTO
     *            Objeto que contiene los valores de filtros que se aplicarán a
     *            la comsulta    
     * @return True si existe al menos una plantilla que coincida con los filtros
     * @author julio.pinzon
     */
    public boolean validarPlantilla(PlantillaDTO plantillaDTO);  

    /**
     * Método que se encarga de validar una plantilla de acuerdo a los 
     * parámetros recibidos de codigo o nombre, si no se
     * envia ningun filtro consulta todos los registros, si no encuentra
     * resultados retorna la lista vacía
     * 
     * @param idPlantilla
     *            Id de objeto que debe ser borrado    
     * @author julio.pinzon
     */
    public void eliminarPlantilla(Integer idPlantilla);
    
    /**
     * Método que se encarga de consultar una plantilla por su id
     * 
     * @param idPlantilla
     *            Id de la plantilla
     * @author julio.pinzon
     */
    public PlantillaDTO consultarPlantillaId(int idPlantilla);

}
