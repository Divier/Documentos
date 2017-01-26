package co.com.datatools.documentos.test.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.com.datatools.documentos.administracion.CargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioCargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.negocio.error.ErrorDocumentos;
import co.com.datatools.documentos.negocio.interfaces.IRIntegracionDocumentosADM;
import co.com.datatools.documentos.test.BaseDocumentosTest;
import co.com.datatools.documentos.ws.exception.DocumentosWebException;
import co.com.datatools.util.date.UtilFecha;

/**
 * Pruebas unitarias del modulo de integracion de documentos para tablas administradas por cliente
 * 
 * @author luis.forero(2015-08-05)
 * 
 */
@RunWith(Arquillian.class)
public class IntegracionDoucumentosAdminEJBTest extends BaseDocumentosTest {

    @EJB
    private IRIntegracionDocumentosADM integracionDocumentosAdminEJB;

    /**
     * RegistrarProcesoTestSC1
     */
    @Test
    public void test01() {
        ProcesoDTO procesoDTO = new ProcesoDTO();
        procesoDTO.setIdProceso(-1);
        procesoDTO.setNombreProceso("Proceso UNIT TEST 1");
        procesoDTO.setDescripcionProceso("Descripcion de Proceso UNIT TEST 1");
        try {
            integracionDocumentosAdminEJB.registrarProceso(procesoDTO);
        } catch (DocumentosWebException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * RegistrarCargoTestSC1
     */
    @Test
    public void test02() {
        CargoDTO cargoDTO = new CargoDTO();
        cargoDTO.setIdCargo(-1);
        cargoDTO.setNombreCargo("Cargo Prueba Unitaria 1");

        try {
            integracionDocumentosAdminEJB.registrarCargo(cargoDTO);
        } catch (DocumentosWebException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * RegistrarCargoTestSC2
     */
    @Test
    public void test03() {
        CargoDTO cargoDTO = new CargoDTO();
        cargoDTO.setIdCargo(-2);
        cargoDTO.setNombreCargo("Cargo Prueba Unitaria 2");

        List<ProcesoDTO> procesos = new ArrayList<>();
        ProcesoDTO procesoDTO = new ProcesoDTO();
        procesoDTO.setIdProceso(-1);
        procesos.add(procesoDTO);

        cargoDTO.setListProcesosDTO(procesos);
        try {
            integracionDocumentosAdminEJB.registrarCargo(cargoDTO);
        } catch (DocumentosWebException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * RegistrarFuncionarioSC1
     */
    @Test
    public void test04() {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setNombreFuncionario("Funcionario PRUEBA 1");
        funcionarioDTO.setNombreTipoIdentificacion("Cédula");
        funcionarioDTO.setSiglaTipoIdentificacion("CC");
        funcionarioDTO.setNumeroDocumIdent("123456789");
        funcionarioDTO.setFechaInicialFuncionario(new Date());

        List<FuncionarioCargoDTO> listFuncionarioCargosDTO = new ArrayList<>();
        FuncionarioCargoDTO funcionarioCargoDTO = new FuncionarioCargoDTO();
        funcionarioCargoDTO.setCargoDTO(new CargoDTO());
        funcionarioCargoDTO.getCargoDTO().setIdCargo(-1);
        funcionarioCargoDTO.setFechaInicio(funcionarioDTO.getFechaInicialFuncionario());

        listFuncionarioCargosDTO.add(funcionarioCargoDTO);

        funcionarioDTO.setListFuncionarioCargosDTO(listFuncionarioCargosDTO);

        try {
            integracionDocumentosAdminEJB.registrarFuncionario(funcionarioDTO);
        } catch (DocumentosWebException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * RegistrarFuncionarioSC2
     */
    @Test
    public void test05() {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setNombreFuncionario("Funcionario PRUEBA 2");
        funcionarioDTO.setNombreTipoIdentificacion("Cédula");
        funcionarioDTO.setSiglaTipoIdentificacion("CC");
        funcionarioDTO.setNumeroDocumIdent("123456789");
        funcionarioDTO.setFechaInicialFuncionario(UtilFecha.stringToDate("dd-MM-yyyy", "18-06-2015"));

        List<FuncionarioCargoDTO> listFuncionarioCargosDTO = new ArrayList<>();
        FuncionarioCargoDTO funcionarioCargoDTO = new FuncionarioCargoDTO();
        funcionarioCargoDTO.setCargoDTO(new CargoDTO());
        funcionarioCargoDTO.getCargoDTO().setIdCargo(-1);
        funcionarioCargoDTO.setFechaInicio(funcionarioDTO.getFechaInicialFuncionario());

        listFuncionarioCargosDTO.add(funcionarioCargoDTO);

        funcionarioDTO.setListFuncionarioCargosDTO(listFuncionarioCargosDTO);

        try {
            integracionDocumentosAdminEJB.registrarFuncionario(funcionarioDTO);
            Assert.fail();
        } catch (DocumentosWebException e) {
            Assert.assertTrue(e.getErrorInfo().getCodigoError()
                    .equals(ErrorDocumentos.Administracion.DOC_001065.getCodigoError()));
        }
    }

    /**
     * RegistrarUsuario
     */
    @Test
    public void test06() {
        UsuarioOrganizacionDTO usuarioOrganizacionDTO = new UsuarioOrganizacionDTO();
        usuarioOrganizacionDTO.setLoginUsuario("prueba");
        usuarioOrganizacionDTO
                .setContrasena("c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec");

        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setNumeroDocumIdent("123456789");
        funcionarioDTO.setNombreTipoIdentificacion("Cédula");

        usuarioOrganizacionDTO.setFuncionarioDTO(funcionarioDTO);

        try {
            integracionDocumentosAdminEJB.registrarUsuario(usuarioOrganizacionDTO);
        } catch (DocumentosWebException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * ActualizarFirma
     */
    @Test
    public void test07() {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setFirma(new byte[] { 0, 1 });
        funcionarioDTO.setNumeroDocumIdent("123456789");
        funcionarioDTO.setNombreTipoIdentificacion("Cédula");

        try {
            integracionDocumentosAdminEJB.actualizarFirma(funcionarioDTO);
        } catch (DocumentosWebException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * ActualizarUsuario
     */
    @Test
    public void test08() {
        UsuarioOrganizacionDTO usuarioOrganizacionDTO = new UsuarioOrganizacionDTO();
        usuarioOrganizacionDTO.setLoginUsuario("prueba");
        usuarioOrganizacionDTO.setContrasena("AbCdFgHi123J1K2L3mN4oPQR12StUvX98Yz67");
        try {
            integracionDocumentosAdminEJB.actualizarUsuario(usuarioOrganizacionDTO);
        } catch (DocumentosWebException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * ActualizarFuncionarioSC1
     */
    @Test
    public void test09() {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setNumeroDocumIdent("123456789");
        funcionarioDTO.setNombreTipoIdentificacion("Cédula");
        funcionarioDTO.setNombreFuncionario("PRUEBA ACTUALIZACION FUNCIONARIO");
        funcionarioDTO.setFechaInicialFuncionario(UtilFecha.stringToDate("dd-MM-yyyy", "18-06-2015"));
        funcionarioDTO.setSiglaTipoIdentificacion("CC");
        funcionarioDTO.setNumeroDocumIdent("123456789");

        List<FuncionarioCargoDTO> listFuncionarioCargosDTO = new ArrayList<>();
        FuncionarioCargoDTO funcionarioCargoDTO = new FuncionarioCargoDTO();
        funcionarioCargoDTO.setCargoDTO(new CargoDTO());
        funcionarioCargoDTO.getCargoDTO().setIdCargo(-1);
        funcionarioCargoDTO.setFechaInicio(funcionarioDTO.getFechaInicialFuncionario());
        funcionarioCargoDTO.setFechaFin(UtilFecha.stringToDate("dd-MM-yyyy", "30-06-2015"));

        funcionarioDTO.setListFuncionarioCargosDTO(listFuncionarioCargosDTO);

        try {
            integracionDocumentosAdminEJB.actualizarFuncionario(funcionarioDTO);
        } catch (DocumentosWebException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * ActualizarFuncionarioSC1
     */
    @Test
    public void test91() {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setNumeroDocumIdent("123456789");
        funcionarioDTO.setNombreTipoIdentificacion("Cédula");
        funcionarioDTO.setNombreFuncionario("PRUEBA ACTUALIZACION FUNCIONARIO");
        funcionarioDTO.setSiglaTipoIdentificacion("CC");
        funcionarioDTO.setNumeroDocumIdent("123456789");

        List<FuncionarioCargoDTO> listFuncionarioCargosDTO = new ArrayList<>();
        FuncionarioCargoDTO funcionarioCargoDTO = new FuncionarioCargoDTO();
        funcionarioCargoDTO.setCargoDTO(new CargoDTO());
        funcionarioCargoDTO.getCargoDTO().setIdCargo(-1);
        funcionarioCargoDTO.setFechaInicio(UtilFecha.stringToDate("dd-MM-yyyy", "01-07-2015"));

        funcionarioDTO.setListFuncionarioCargosDTO(listFuncionarioCargosDTO);

        try {
            integracionDocumentosAdminEJB.actualizarFuncionario(funcionarioDTO);
        } catch (DocumentosWebException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * AdicionarProcesosCargo
     */
    @Test
    public void test92() {
        int idCargo = -1;
        List<Long> procesos = new ArrayList<>();
        procesos.add(-1l);
        try {
            integracionDocumentosAdminEJB.adicionarProcesosCargo(idCargo, procesos);
        } catch (DocumentosWebException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * RemoverProcesosCargo
     */
    @Test
    public void test93() {
        int idCargo = -1;
        List<Long> procesos = new ArrayList<>();
        procesos.add(-1l);
        try {
            integracionDocumentosAdminEJB.removerProcesosCargo(idCargo, procesos);
        } catch (DocumentosWebException e) {
            Assert.fail(e.getMessage());
        }
    }

}
