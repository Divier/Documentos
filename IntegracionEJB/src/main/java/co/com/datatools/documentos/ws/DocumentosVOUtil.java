package co.com.datatools.documentos.ws;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import co.com.datatools.documentos.administracion.CargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioCargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.negocio.error.ErrorDocumentos;
import co.com.datatools.documentos.ws.exception.DocumentosWebException;

public class DocumentosVOUtil {
    public static CargoDTO toCargoDTO(CargoVO cargoVO) {
        CargoDTO cargoDTO = new CargoDTO();
        cargoDTO.setIdCargo(cargoVO.getId());
        cargoDTO.setNombreCargo(cargoVO.getNombre());
        if (cargoVO.getProcesos() != null) {
            List<ProcesoDTO> listProcesosDTO = new ArrayList<ProcesoDTO>(0);
            for (Long idProceso : cargoVO.getProcesos()) {
                ProcesoDTO procesoDTO = new ProcesoDTO();
                procesoDTO.setIdProceso(idProceso);
                listProcesosDTO.add(procesoDTO);
            }
            cargoDTO.setListProcesosDTO(listProcesosDTO);
        }
        return cargoDTO;
    }

    public static ProcesoDTO toProcesoDTO(ProcesoVO procesoVO) {
        ProcesoDTO procesoDTO = new ProcesoDTO();
        procesoDTO.setDescripcionProceso(procesoVO.getDescripcionProceso());
        procesoDTO.setIdProceso(procesoVO.getIdProceso());
        procesoDTO.setNombreProceso(procesoVO.getNombreProceso());
        if (procesoVO.getIdPadre() != null) {
            procesoDTO.setProcesoDTO(new ProcesoDTO());
            procesoDTO.getProcesoDTO().setIdProceso(procesoVO.getIdPadre());
        }
        return procesoDTO;
    }

    public static UsuarioOrganizacionDTO toUsuarioOrganizacionDTO(UsuarioOrganizacionVO usuarioOrganizacionVO) {
        UsuarioOrganizacionDTO usuarioDTO = new UsuarioOrganizacionDTO();
        usuarioDTO.setLoginUsuario(usuarioOrganizacionVO.getLogin());
        usuarioDTO.setContrasena(usuarioOrganizacionVO.getContrasena());
        if (StringUtils.isNotBlank(usuarioOrganizacionVO.getNombreTipoDocumentoFuncionario())
                || StringUtils.isNotBlank(usuarioOrganizacionVO.getNombreTipoDocumentoFuncionario())) {
            usuarioDTO.setFuncionarioDTO(new FuncionarioDTO());
            usuarioDTO.getFuncionarioDTO().setNumeroDocumIdent(usuarioOrganizacionVO.getNumeroDocumentoFuncionario());
            usuarioDTO.getFuncionarioDTO().setNombreTipoIdentificacion(
                    usuarioOrganizacionVO.getNombreTipoDocumentoFuncionario());
        }
        return usuarioDTO;
    }

    public static Date stringToDate(String fecha) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(ConstantesGeneral.DATE_FORMAT);
        Date fechaGeneracion = sdf.parse(fecha);
        return fechaGeneracion;
    }

    public static FuncionarioDTO toFuncionarioDTO(FuncionarioVO funcionarioVO) throws DocumentosWebException {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();

        funcionarioDTO.setNumeroDocumIdent(funcionarioVO.getNumeroDocumIdent());
        funcionarioDTO.setNombreTipoIdentificacion(funcionarioVO.getNombreTipoIdentificacion());
        funcionarioDTO.setSiglaTipoIdentificacion(funcionarioVO.getSiglaTipoIdentificacion());
        funcionarioDTO.setNombreFuncionario(funcionarioVO.getNombreFuncionario());
        try {
            funcionarioDTO.setFechaInicialFuncionario(stringToDate(funcionarioVO.getFechaInicialFuncionario()));
        } catch (ParseException e) {
            DocumentosWSEJB.logger.error("Error en formato de fecha inicial del funcionario", e);
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001061);
        }
        if (StringUtils.isNotBlank(funcionarioVO.getFechaFinalFuncionario())) {
            try {
                funcionarioDTO.setFechaFinalFuncionario(stringToDate(funcionarioVO.getFechaFinalFuncionario()));
            } catch (ParseException e) {
                DocumentosWSEJB.logger.error("Error en formato de fecha final del funcionario", e);
                throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001062);
            }
        }

        if (funcionarioVO.getIdCargo() != null) {
            CargoDTO cargoDTO = new CargoDTO();
            cargoDTO.setIdCargo(funcionarioVO.getIdCargo());
            FuncionarioCargoDTO funcionarioCargoDTO = new FuncionarioCargoDTO();
            try {
                funcionarioCargoDTO.setFechaInicio(stringToDate(funcionarioVO.getFechaInicialFuncionario()));
            } catch (ParseException e) {
                DocumentosWSEJB.logger.error("Error en formato de fecha inicial del cargo del funcionario", e);
                throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001063);
            }
            if (StringUtils.isNotBlank(funcionarioVO.getFechaFinCargo())) {
                try {
                    funcionarioCargoDTO.setFechaFin(stringToDate(funcionarioVO.getFechaFinCargo()));
                } catch (ParseException e) {
                    DocumentosWSEJB.logger.error("Error en formato de fecha fin del cargo del funcionario", e);
                    throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001064);
                }
            }
            funcionarioCargoDTO.setCargoDTO(cargoDTO);

            funcionarioDTO.setListFuncionarioCargosDTO(new ArrayList<FuncionarioCargoDTO>());
            funcionarioDTO.getListFuncionarioCargosDTO().add(funcionarioCargoDTO);
        }

        if (funcionarioVO.getFirma() != null) {
            funcionarioDTO.setFirma(funcionarioVO.getFirma());
        }
        return funcionarioDTO;
    }
}