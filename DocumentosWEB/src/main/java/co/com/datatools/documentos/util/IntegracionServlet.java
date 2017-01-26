//package co.com.datatools.documentos.util;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import co.com.datatools.documentos.administracion.CargoDTO;
//import co.com.datatools.documentos.administracion.ConsultaFuncionarioDTO;
//import co.com.datatools.documentos.administracion.FuncionarioDTO;
//import co.com.datatools.documentos.administracion.OrganizacionDTO;
//import co.com.datatools.documentos.administracion.ProcesoDTO;
//import co.com.datatools.documentos.negocio.interfaces.ILIntegracionDocumentos;
//import co.com.datatools.documentos.negocio.interfaces.administracion.ILCargo;
//import co.com.datatools.documentos.negocio.interfaces.administracion.ILFuncionario;
//import co.com.datatools.documentos.negocio.interfaces.administracion.ILOrganizacion;
//import co.com.datatools.documentos.negocio.interfaces.administracion.ILProceso;
//
///**
// * Servlet implementation class IntegracionServlet
// */
//public class IntegracionServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    private ILIntegracionDocumentos ilIntegracionDocumentos;
//    private ILOrganizacion ilOrganizacion;
//    private ILCargo ilCargo;
//    private ILFuncionario ilFuncionario;
//    private ILProceso ilProceso;
//
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public IntegracionServlet() {
//        super();
//    }
//
//    /**
//     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//     */
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        inicializarInterfaces();
//        importarOrganizacion();
//        importarCargo();
//        importarFuncionario();
//        importarProceso();
//        System.out
//                .println("-------------------------Proceso de sincronizacion: termino completo------------------------");
//    }
//
//    /**
//     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//     */
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
//            IOException {
//    }
//
//    public void inicializarInterfaces() {
//        try {
//            Context context = new InitialContext();
//            ilIntegracionDocumentos = (ILIntegracionDocumentos) context
//                    .lookup("ejb:DocumentosEAR/IntegracionEJB/IntegracionDocumentosEJB!co.com.datatools.documentos.negocio.interfaces.ILIntegracionDocumentos");
//            ilOrganizacion = (ILOrganizacion) context
//                    .lookup("ejb:DocumentosEAR/AdministracionEJB/AdministracionEJB!co.com.datatools.documentos.negocio.interfaces.ILOrganizacion");
//            ilCargo = (ILCargo) context
//                    .lookup("ejb:DocumentosEAR/AdministracionEJB/AdministracionEJB!co.com.datatools.documentos.negocio.interfaces.ILCargo");
//            ilFuncionario = (ILFuncionario) context
//                    .lookup("ejb:DocumentosEAR/AdministracionEJB/AdministracionEJB!co.com.datatools.documentos.negocio.interfaces.ILFuncionario");
//            ilProceso = (ILProceso) context
//                    .lookup("ejb:DocumentosEAR/AdministracionEJB/AdministracionEJB!co.com.datatools.documentos.negocio.interfaces.ILProceso");
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void importarOrganizacion() {
//        List<OrganizacionDTO> lOrganizacionDTOs = ilIntegracionDocumentos.importarOrganizacion(null);
//        for (OrganizacionDTO organizacionDTO : lOrganizacionDTOs) {
//            if (ilOrganizacion.consultarOrganizacionId(organizacionDTO.getIdOrganizacion()) == null) {
//                ilOrganizacion.registrarOrganizacion(organizacionDTO);
//            } else {
//                ilOrganizacion.actualizarOrganizacion(organizacionDTO);
//            }
//        }
//        System.out.println("---------------------importarOrganizacion: termino completo------------------------------");
//    }
//
//    public void importarCargo() {
//
//        List<CargoDTO> lCargoDTOs = ilIntegracionDocumentos.importarCargo(null, 0);
//        for (CargoDTO cargoDTO : lCargoDTOs) {
//            if (ilCargo.consultarCargoId(cargoDTO.getIdCargo()) == null) {
//                ilCargo.registrarCargo(cargoDTO);
//            } else {
//                ilCargo.actualizarCargo(cargoDTO);
//            }
//        }
//        System.out.println("------------------------------importarCargo: termino completo--------------------------");
//
//    }
//
//    public void importarFuncionario() {
//
//        List<FuncionarioDTO> lfuncionarioDTOs = ilIntegracionDocumentos.importarFuncionario(null, 0);
//        ConsultaFuncionarioDTO consultaFuncionarioDTO = new ConsultaFuncionarioDTO();
//        for (FuncionarioDTO funcionarioDTO : lfuncionarioDTOs) {
//            consultaFuncionarioDTO.setFuncionarioDTO(funcionarioDTO);
//            List<FuncionarioDTO> lFuncionarios = ilFuncionario.consultarFuncionario(consultaFuncionarioDTO);
//            if (lFuncionarios != null && !lFuncionarios.isEmpty()) {
//                funcionarioDTO.setIdFuncionario(lFuncionarios.get(0).getIdFuncionario());
//                ilFuncionario.actualizarFuncionario(funcionarioDTO);
//            } else {
//                ilFuncionario.registrarFuncionario(funcionarioDTO);
//            }
//        }
//        System.out
//                .println("--------------------------------importarFuncionario: termino completo------------------------------");
//
//    }
//
//    public void importarProceso() {
//
//        List<ProcesoDTO> lProcesoDTOs = ilIntegracionDocumentos.importarProceso(null, 0);
//        if (lProcesoDTOs != null && !lProcesoDTOs.isEmpty()) {
//            for (ProcesoDTO procesoDTO : lProcesoDTOs) {
//                if (ilIntegracionDocumentos.importarProcesoCargos(procesoDTO) != null
//                        && !ilIntegracionDocumentos.importarProcesoCargos(procesoDTO).isEmpty()) {
//                    procesoDTO.setListCargosDTO(ilIntegracionDocumentos.importarProcesoCargos(procesoDTO).get(0)
//                            .getListCargosDTO());
//                }
//                if (ilProceso.consultarProcesoId(procesoDTO.getIdProceso()) == null) {
//                    ilProceso.registrarProceso(procesoDTO);
//                } else {
//                    ilProceso.actualizarProceso(procesoDTO);
//                }
//            }
//        }
//        System.out.println("----------------------------------importarProceso: termino completo----------------------");
//
//    }
//
// }
