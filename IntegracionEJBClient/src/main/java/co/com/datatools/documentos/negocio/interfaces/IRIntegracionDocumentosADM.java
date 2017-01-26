package co.com.datatools.documentos.negocio.interfaces;

import java.util.List;

import javax.ejb.Remote;

import co.com.datatools.documentos.administracion.CargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.ws.exception.DocumentosWebException;

@Remote
public interface IRIntegracionDocumentosADM {

    /**
     * Registra el cargo indicado en el sistema de documentos. Valida que no exista otro cargo con el mismo nombre, sin tener en cuenta mayusculas o
     * minusculas
     * 
     * @param cargoDTO
     *            el cargo a registrar
     * @throws DocumentosWebException
     * <br>
     *             DOC_001050:"Existe un cargo con el mismo nombre"(Cod:1050)<br>
     *             DOC_001051:"Existe un cargo con el mismo identificador 'id' "(Cod:1051)<br>
     *             DOC_001054:"No existe el proceso asociado al cargo", (cod:1054)<br>
     */
    public void registrarCargo(CargoDTO cargoDTO) throws DocumentosWebException;

    /**
     * Registra el proceso indicado en el sistema. Valida que no exista otro proceso con el mismo nombre, sin tener en cuenta mayusculas o minusculas.
     * Valida que el proceso padre exista en caso de traerlo.
     * 
     * @param procesoDTO
     *            el proceso a registrar
     * @throws DocumentosWebException
     * <br>
     *             DOC_001052:"Existe un proceso con el mismo identificador 'id' ", (Codigo:1052)</br>
     *             DOC_001053:"Existe un proceso con el mismo nombre", (Codigo:1053)</br>
     *             DOC_001055:"No existe el proceso padre asociado con el ingresado", (Codigo:1055)</br>
     */
    public void registrarProceso(ProcesoDTO procesoDTO) throws DocumentosWebException;

    /**
     * Registra el usuario indicado. Verifica que no exista otro usuario con el mismo login sin tener en cuenta mayusculas o minusculas. En caso de
     * ser necesario verifica que el funcionario exista.
     * 
     * @param usuarioOrganizacionDTO
     *            El usuario a registrar
     * @throws DocumentosWebException
     */
    public void registrarUsuario(UsuarioOrganizacionDTO usuarioOrganizacionDTO) throws DocumentosWebException;

    /**
     * Registra el funcionario indicado. Verifica que no exista otro funcionario con el tipo y número de documento de identidad. Verifica que el cargo
     * del funcionario exista y que las fechas de labor del funcionario sean ordenadas cronologicamente. Además verifica que las fechas del cargo se
     * encuentren dentro del rango de las fechas de labor del funcionario.
     * 
     * @param funcionarioDTO
     *            el funcionario a registrar
     * @throws DocumentosWebException
     */
    public void registrarFuncionario(FuncionarioDTO funcionarioDTO) throws DocumentosWebException;

    /**
     * Actualiza la firma indicada al funcionario relacionado a esta. Verifica que el funcionario exista por su tipo y numero de documento.
     * 
     * @param funcionarioDTO
     *            contiene la firma a actualizar junto con el numero y el tipo de documento
     * @throws DocumentosWebException
     */
    public void actualizarFirma(FuncionarioDTO funcionarioDTO) throws DocumentosWebException;

    /**
     * Actualiza la clave del usuario indicado. Solo se actualiza el funcionario si y solo si el usuario no tenía funcionario asociado.
     * 
     * @param usuarioOrganizacionDTO
     *            el usuario a actualizar
     * @throws DocumentosWebException
     */
    public void actualizarUsuario(UsuarioOrganizacionDTO usuarioOrganizacionDTO) throws DocumentosWebException;

    /**
     * Actualiza el funcionario indicado. Verifica que el cargo del funcionario exista y que las fechas de labor del funcionario sean ordenadas
     * cronológicamente. Ademas verifica que las fechas del cargo se encuentren dentro del rango de las fechas de labor del funcionario.
     * 
     * @param funcionarioDTO
     *            el funcionario a actualizar
     * @throws DocumentosWebException
     */
    public void actualizarFuncionario(FuncionarioDTO funcionarioDTO) throws DocumentosWebException;

    /**
     * Adiciona el listado de procesos identificados indicados al cargo. Verifica que el cargo exista, que los procesos existan y no esten asociados
     * al cargo. Si no cumple, no realiza ninguna adicion.
     * 
     * @param idCargo
     *            id que identifica el cargo a ser adicionados los procesos
     * @param procesos
     *            listado de ids de los procesos a asociar al cargo
     * @throws DocumentosWebException
     * @author luis.forero (2015-08-04)
     */
    public void adicionarProcesosCargo(int idCargo, List<Long> procesos) throws DocumentosWebException;

    /**
     * Remueve los procesos indicados del cargo con el id indicado. Verifica que el cargo exista, que los procesos existan y esten asociados al cargo.
     * Si no cumple, no realiza ningún cambio.
     * 
     * @param idCargo
     *            id que identifica el cargo al que se le removeran los procesos
     * @param procesos
     *            listado de ids de los procesos a ser removidos del cargo
     * @throws DocumentosWebException
     * @author luis.forero (2015-08-04)
     */
    public void removerProcesosCargo(int idCargo, List<Long> procesos) throws DocumentosWebException;
}
