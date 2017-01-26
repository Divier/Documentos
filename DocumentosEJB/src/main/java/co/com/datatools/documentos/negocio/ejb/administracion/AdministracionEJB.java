package co.com.datatools.documentos.negocio.ejb.administracion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import co.com.datatools.documentos.administracion.CargoDTO;
import co.com.datatools.documentos.administracion.ConsultaCargoFuncionarioDTO;
import co.com.datatools.documentos.administracion.ConsultaFuncionarioDTO;
import co.com.datatools.documentos.administracion.FuncionarioCargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.ParametroSistemaDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.TipoDatoDTO;
import co.com.datatools.documentos.entidades.Cargo;
import co.com.datatools.documentos.entidades.Funcionario;
import co.com.datatools.documentos.entidades.FuncionarioCargo;
import co.com.datatools.documentos.entidades.ParametroSistema;
import co.com.datatools.documentos.entidades.Proceso;
import co.com.datatools.documentos.entidades.TipoDato;
import co.com.datatools.documentos.enumeraciones.EnumParametrosSistema;
import co.com.datatools.documentos.excepcion.DocumentosException;
import co.com.datatools.documentos.negocio.error.ErrorDocumentos;
import co.com.datatools.documentos.negocio.helper.CargoHelper;
import co.com.datatools.documentos.negocio.helper.FuncionarioCargoHelper;
import co.com.datatools.documentos.negocio.helper.FuncionarioHelper;
import co.com.datatools.documentos.negocio.helper.ParametrosSistemaHelper;
import co.com.datatools.documentos.negocio.helper.ProcesoHelper;
import co.com.datatools.documentos.negocio.helper.TipoDatoHelper;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILCargo;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILFuncionario;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILParametroSistema;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILProceso;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRCargo;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRFuncionario;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRParametroSistema;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRProceso;
import co.com.datatools.util.GenericDao;

@Stateless(name = "AdministracionEJB")
@LocalBean
public class AdministracionEJB implements ILProceso, IRProceso, ILFuncionario, IRFuncionario, ILCargo, IRCargo,
        IRParametroSistema, ILParametroSistema {

    private static final Logger logger = Logger.getLogger(AdministracionEJB.class.getName());

    @PersistenceContext(unitName = "DocumentosJPA")
    EntityManager em;

    public AdministracionEJB() {

    }

    // INICIO METODOS CARGO

    @Override
    public CargoDTO registrarCargo(CargoDTO cargoDTO) throws DocumentosException {
        logger.debug("AdministracionEJB::registrarCargo");

        TypedQuery<Long> query = null;
        query = em.createNamedQuery(Cargo.SQ_COUNT_BY_NOMBRE, Long.class);

        query.setParameter("pNomCar", cargoDTO.getNombreCargo().toUpperCase());
        Long countRegistros = query.getSingleResult();
        if (countRegistros > 0) {
            throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001050);
        }
        query = em.createNamedQuery(Cargo.SQ_COUNT_BY_ID, Long.class);
        query.setParameter("pIdCar", cargoDTO.getIdCargo());
        countRegistros = query.getSingleResult();
        if (countRegistros > 0) {
            throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001051);
        }

        Cargo cargo = CargoHelper.toCargo(cargoDTO, new Cargo());

        if (cargoDTO.getListProcesosDTO() != null) {
            cargo.setProcesos(new ArrayList<Proceso>());
            for (ProcesoDTO procesoDTO : cargoDTO.getListProcesosDTO()) {
                Proceso proceso = em.find(Proceso.class, procesoDTO.getIdProceso());
                if (proceso == null) {
                    throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001054);
                }
                cargo.getProcesos().add(proceso);
            }
        }

        em.persist(cargo);
        em.flush();
        cargoDTO.setIdCargo(cargo.getIdCargo());

        return cargoDTO;
    }

    @Override
    public CargoDTO consultarCargoId(int idCargo) {
        logger.debug("AdministracionEJB::consultarCargoId");
        CargoDTO cargoDTO = null;

        Cargo cargo = em.find(Cargo.class, idCargo);
        if (cargo != null) {
            cargoDTO = CargoHelper.toCargoDTO(cargo);

            if (cargo.getProcesos() != null && !cargo.getProcesos().isEmpty()) {
                List<ProcesoDTO> lProcesoDTOs = new ArrayList<ProcesoDTO>();
                for (Proceso proceso : cargo.getProcesos()) {
                    ProcesoDTO procesoDTO = ProcesoHelper.toProcesoDTO(proceso);
                    lProcesoDTOs.add(procesoDTO);
                }
                cargoDTO.setListProcesosDTO(lProcesoDTOs);
            }
        }
        return cargoDTO;
    }

    @Override
    public void actualizarCargo(CargoDTO cargoDTO) throws DocumentosException {
        logger.debug("AdministracionEJB::actualizarCargo");

        Cargo cargo = em.find(Cargo.class, cargoDTO.getIdCargo());
        if (cargo == null) {
            throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001071);
        }
        if (cargoDTO.getListProcesosDTO() != null) {
            List<Proceso> procesos = cargo.getProcesos();
            for (Iterator<Proceso> iterator = procesos.iterator(); iterator.hasNext();) {
                boolean remover = true;
                Proceso proceso = iterator.next();
                for (ProcesoDTO procesoDTO : cargoDTO.getListProcesosDTO()) {
                    if (procesoDTO.getIdProceso() == proceso.getIdProceso()) {
                        remover = false;
                        cargoDTO.getListProcesosDTO().remove(procesoDTO);
                        break;
                    }
                }
                if (remover) {
                    iterator.remove();
                }
            }

            for (ProcesoDTO procesoDTO : cargoDTO.getListProcesosDTO()) {
                Proceso proceso = em.find(Proceso.class, procesoDTO.getIdProceso());
                if (proceso == null) {
                    throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001054);
                }
                procesos.add(proceso);
            }
        }

        cargo = CargoHelper.toCargo(cargoDTO, cargo);

        em.merge(cargo);
        em.flush();
    }

    @Override
    public List<CargoDTO> consultarCargo(CargoDTO cargoDTO) {
        // TODO Agregar Implementacion
        return null;
    }

    // FIN METODOS CARGO

    // INICIO METODOS FUNCIONARIO

    @Override
    public FuncionarioDTO registrarFuncionario(FuncionarioDTO funcionarioDTO) throws DocumentosException {
        logger.debug("AdministracionEJB::registrarFuncionario");

        TypedQuery<Long> query = em.createNamedQuery(Funcionario.SQ_COUNT_BY_NUM_DOC_TIP_DOC, Long.class);
        query.setParameter("pNumDoc", funcionarioDTO.getNumeroDocumIdent());
        query.setParameter("pNomTipIde", funcionarioDTO.getNombreTipoIdentificacion());
        long count = query.getSingleResult();
        if (count > 0) {
            throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001065);
        }

        Funcionario funcionario = FuncionarioHelper.toFuncionario(funcionarioDTO, new Funcionario());

        em.persist(funcionario);
        funcionarioDTO.setIdFuncionario(funcionario.getIdFuncionario());

        if (funcionarioDTO.getListFuncionarioCargosDTO() != null
                && !funcionarioDTO.getListFuncionarioCargosDTO().isEmpty()) {

            for (int i = 0; i < funcionarioDTO.getListFuncionarioCargosDTO().size(); i++) {
                FuncionarioCargoDTO funcionarioCargoDTO = funcionarioDTO.getListFuncionarioCargosDTO().get(i);
                if ((i + 1) < funcionarioDTO.getListFuncionarioCargosDTO().size()
                        && funcionarioCargoDTO.getFechaFin() != null && funcionarioCargoDTO.getFechaFin().compareTo(
                                funcionarioDTO.getListFuncionarioCargosDTO().get(i + 1).getFechaInicio()) > 0) {
                    throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001067);
                } else if (i == 0 && funcionarioCargoDTO.getFechaFin() != null
                        && funcionarioDTO.getListFuncionarioCargosDTO().size() > 1) {
                    throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001067);
                }
                funcionarioCargoDTO.setFuncionarioDTO(funcionarioDTO);
                registrarFuncionarioCargo(funcionarioCargoDTO);
            }
        }

        return funcionarioDTO;
    }

    @Override
    public List<FuncionarioDTO> consultarFuncionario(ConsultaFuncionarioDTO consultaFuncionarioDTO) {
        logger.debug("AdministracionEJB::consultarFuncionario");
        List<FuncionarioDTO> lFuncionarioDTOs = new ArrayList<FuncionarioDTO>();
        FuncionarioDTO funcionarioDTO = consultaFuncionarioDTO.getFuncionarioDTO();

        StringBuilder sql = new StringBuilder();
        GenericDao<Funcionario> funcionarioDao = new GenericDao<>(Funcionario.class, em);
        Map<String, Object> params = new HashMap<>(5);

        sql.append("SELECT f FROM Funcionario f ");

        if (funcionarioDTO != null) {
            if (funcionarioDTO.getListFuncionarioCargosDTO() != null
                    && !funcionarioDTO.getListFuncionarioCargosDTO().isEmpty()) {
                sql.append(" JOIN f.funcionarioCargos fc");
            }
            sql.append(" WHERE 1=1");

            if (funcionarioDTO.getIdFuncionario() != 0) {
                sql.append(" AND f.idFuncionario= :idFuncionario");
                params.put("idFuncionario", funcionarioDTO.getIdFuncionario());
            }
            if (StringUtils.isNotBlank(funcionarioDTO.getNumeroDocumIdent())) {
                sql.append(" AND UPPER(TRIM(f.numeroDocumIdent)) LIKE :numeroIdentificacion");
                params.put("numeroIdentificacion",
                        "%" + funcionarioDTO.getNumeroDocumIdent().toUpperCase().trim() + "%");
            }
            if (StringUtils.isNotBlank(funcionarioDTO.getNombreFuncionario())) {
                sql.append(" AND UPPER(TRIM(f.nombreFuncionario)) LIKE :nombreFuncionario");
                params.put("nombreFuncionario", "%" + funcionarioDTO.getNombreFuncionario().toUpperCase().trim() + "%");
            }
            if (StringUtils.isNotBlank(funcionarioDTO.getSiglaTipoIdentificacion())) {
                sql.append(" AND UPPER(TRIM(f.siglaTipoIdentificacion)) = :siglaTipoIdentificacion");
                params.put("siglaTipoIdentificacion", funcionarioDTO.getSiglaTipoIdentificacion().toUpperCase());
            }
            // Filtro para consultar funcionarios por cargo
            List<FuncionarioCargoDTO> listaCargos = funcionarioDTO.getListFuncionarioCargosDTO();
            if (listaCargos != null && !listaCargos.isEmpty()) {
                if (listaCargos.get(0) != null && listaCargos.get(0).getCargoDTO() != null) {
                    sql.append(" AND fc.cargo.idCargo = :idCargo");
                    params.put("idCargo", listaCargos.get(0).getCargoDTO().getIdCargo());
                    if (consultaFuncionarioDTO.isCargoVigente())
                        sql.append(" AND fc.fechaFin IS NULL");
                }
            }
            // Filtro para consultar los que tengan firma
            if (consultaFuncionarioDTO.isTieneFirma()) {
                sql.append(" AND f.firma IS NOT NULL");
            }
        }

        List<Funcionario> lFuncionarios = funcionarioDao.buildAndExecuteQuery(sql.toString(), params);
        for (Funcionario funcionario : lFuncionarios) {
            FuncionarioDTO funcDTO = FuncionarioHelper.toFuncionarioDTO(funcionario);
            lFuncionarioDTOs.add(funcDTO);
        }
        return lFuncionarioDTOs;
    }

    @Override
    public void actualizarFuncionario(FuncionarioDTO funcionarioDTO) throws DocumentosException {
        logger.debug("AdministracionEJB::actualizarFuncionario");

        Funcionario funcionario = em.find(Funcionario.class, funcionarioDTO.getIdFuncionario());
        Funcionario funcionarioMod = FuncionarioHelper.toFuncionario(funcionarioDTO, funcionario);
        em.merge(funcionarioMod);
        em.flush();
        if (funcionarioDTO.getListFuncionarioCargosDTO() != null
                && !funcionarioDTO.getListFuncionarioCargosDTO().isEmpty()) {
            for (int i = 0; i < funcionarioDTO.getListFuncionarioCargosDTO().size(); i++) {
                FuncionarioCargoDTO funcionarioCargoDTO = funcionarioDTO.getListFuncionarioCargosDTO().get(i);
                if ((i + 1) < funcionarioDTO.getListFuncionarioCargosDTO().size()
                        && funcionarioCargoDTO.getFechaFin() != null && funcionarioCargoDTO.getFechaFin().compareTo(
                                funcionarioDTO.getListFuncionarioCargosDTO().get(i + 1).getFechaInicio()) > 0) {
                    throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001067);
                } else if (i == 0 && funcionarioCargoDTO.getFechaFin() != null
                        && funcionarioDTO.getListFuncionarioCargosDTO().size() > 1) {
                    throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001067);
                }

                FuncionarioCargo funcionarioCargo = em.find(FuncionarioCargo.class,
                        funcionarioCargoDTO.getIdFuncionarioCargo());
                if (funcionarioCargo != null) {
                    funcionarioCargoDTO.setIdFuncionarioCargo(funcionarioCargo.getIdFuncionarioCargo());
                    actualizarFuncionarioCargo(funcionarioCargoDTO);
                } else {
                    registrarFuncionarioCargo(funcionarioCargoDTO);
                }
            }
        }

    }

    @Override
    public FuncionarioDTO consultarFuncionarioId(long idFuncionario) {
        logger.debug("AdministracionEJB::consultarFuncionarioId");
        FuncionarioDTO funcionarioDTO = null;

        Funcionario funcionario = em.find(Funcionario.class, idFuncionario);
        if (funcionario != null) {
            funcionarioDTO = FuncionarioHelper.toFuncionarioDTO(funcionario);
        }
        return funcionarioDTO;
    }

    @Override
    public FuncionarioCargoDTO consultarFirmaFuncionarioCargo(ConsultaCargoFuncionarioDTO consultaCargoFuncionarioDTO) {
        logger.debug("AdministracionEJB::consultarFuncionarioCargo");
        FuncionarioCargoDTO funcionarioCargoDTO = null;
        Map<String, Object> parametros = new HashMap<>(5);
        GenericDao<FuncionarioCargo> funcionarioCargoDao = new GenericDao<>(FuncionarioCargo.class, em);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT fc FROM FuncionarioCargo fc");
        if ((consultaCargoFuncionarioDTO.getProcesoDTO() != null
                && consultaCargoFuncionarioDTO.getProcesoDTO().getIdProceso() != 0)
                || (consultaCargoFuncionarioDTO.getProcesos() != null
                        && !consultaCargoFuncionarioDTO.getProcesos().isEmpty())) {
            sql.append(" JOIN fc.cargo.procesos p  ");
        }
        sql.append(" WHERE 1=1");
        if (consultaCargoFuncionarioDTO.getCargoDTO() != null
                && consultaCargoFuncionarioDTO.getCargoDTO().getIdCargo() != 0) {
            sql.append(" AND fc.cargo.idCargo = :idCargo");
            parametros.put("idCargo", consultaCargoFuncionarioDTO.getCargoDTO().getIdCargo());
        }
        if (consultaCargoFuncionarioDTO.getFuncionarioDTO() != null
                && consultaCargoFuncionarioDTO.getFuncionarioDTO().getIdFuncionario() != 0) {
            sql.append(" AND fc.funcionario.idFuncionario = :idFuncionario");
            parametros.put("idFuncionario", consultaCargoFuncionarioDTO.getFuncionarioDTO().getIdFuncionario());
        }
        if (consultaCargoFuncionarioDTO.getProcesoDTO() != null
                && consultaCargoFuncionarioDTO.getProcesoDTO().getIdProceso() != 0) {
            sql.append(" AND p.idProceso = :idProceso");
            parametros.put("idProceso", consultaCargoFuncionarioDTO.getProcesoDTO().getIdProceso());
        } else if (consultaCargoFuncionarioDTO.getProcesos() != null
                && !consultaCargoFuncionarioDTO.getProcesos().isEmpty()) {
            sql.append(" AND p.idProceso IN :idProcesos");
            List<Long> idsProceso = new ArrayList<Long>();
            for (ProcesoDTO proceso : consultaCargoFuncionarioDTO.getProcesos()) {
                idsProceso.add(proceso.getIdProceso());
            }
            parametros.put("idProcesos", idsProceso);
        }

        // Fecha de referencia
        if (consultaCargoFuncionarioDTO.getFechaReferencia() != null) {
            sql.append(" AND ((fc.fechaInicio < :fechaReferencia");
            sql.append(" AND fc.fechaFin >= :fechaReferencia)");
            sql.append(" OR (fc.fechaInicio < :fechaReferencia");
            sql.append(" AND fc.fechaFin IS NULL))");
            parametros.put("fechaReferencia", consultaCargoFuncionarioDTO.getFechaReferencia());
        }
        sql.append(" ORDER BY fc.fechaInicio ASC");

        List<FuncionarioCargo> lFuncionarioCargos = funcionarioCargoDao.buildAndExecuteQuery(sql.toString(),
                parametros);

        for (FuncionarioCargo funcionarioCargo : lFuncionarioCargos) {
            FuncionarioCargoDTO funCargoDTO = FuncionarioCargoHelper.toFuncionarioCargoDTO(funcionarioCargo);
            if (funcionarioCargo.getCargo() != null && funcionarioCargo.getCargo().getIdCargo() != 0) {
                // CargoDTO cargoDTO = CargoHelper.toCargoDTO(funcionarioCargo.getCargo());
                CargoDTO cargoDTO = consultarCargoId(funcionarioCargo.getCargo().getIdCargo());
                funCargoDTO.setCargoDTO(cargoDTO);
            }
            if (funcionarioCargo.getFuncionario() != null
                    && funcionarioCargo.getFuncionario().getIdFuncionario() != 0) {
                // FuncionarioDTO funcionarioDTO = FuncionarioHelper.toFuncionarioDTO(funcionarioCargo.getFuncionario());
                FuncionarioDTO funcionarioDTO = consultarFuncionarioId(
                        funcionarioCargo.getFuncionario().getIdFuncionario());
                funCargoDTO.setFuncionarioDTO(funcionarioDTO);
            }
            funcionarioCargoDTO = funCargoDTO;
            if (funCargoDTO.getFuncionarioDTO().getFirma() != null) {
                break;
            }
        }

        return funcionarioCargoDTO;
    }

    // FIN METODOS FUNCIONARIO

    // INICIO METODOS PROCESO

    @Override
    public List<ProcesoDTO> consultarProceso(ProcesoDTO procesoDTO) {
        logger.debug("AdministracionEJB::consultarProceso");
        List<ProcesoDTO> lProcesoDTOs = new ArrayList<ProcesoDTO>();
        GenericDao<Proceso> procesoDao = new GenericDao<>(Proceso.class, em);
        Map<String, Object> params = new HashMap<>(5);
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT p FROM Proceso p ");
        sql.append("WHERE 1=1");

        sql.append(" ORDER BY p.nombreProceso ASC");

        List<Proceso> lProcesos = procesoDao.buildAndExecuteQuery(sql.toString(), params);
        for (Proceso proceso : lProcesos) {
            ProcesoDTO doDto = ProcesoHelper.toProcesoDTO(proceso);

            if (proceso.getProceso() != null) {
                ProcesoDTO procesoPadre = ProcesoHelper.toProcesoDTO(proceso.getProceso());
                doDto.setProcesoDTO(procesoPadre);
            }
            lProcesoDTOs.add(doDto);
        }

        return lProcesoDTOs;
    }

    @Override
    public ProcesoDTO registrarProceso(ProcesoDTO procesoDTO) throws DocumentosException {
        logger.debug("AdministracionEJB::registrarProceso");

        TypedQuery<Long> query = em.createNamedQuery(Proceso.SQ_COUNT_BY_ID, Long.class);
        query.setParameter("pIdPro", procesoDTO.getIdProceso());
        Long count = query.getSingleResult();
        if (count > 0) {
            throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001052);
        }
        query = em.createNamedQuery(Proceso.SQ_COUNT_BY_NOMBRE, Long.class);
        query.setParameter("pNomPro", procesoDTO.getNombreProceso().toUpperCase());
        count = query.getSingleResult();
        if (count > 0) {
            throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001053);
        }

        Proceso proceso = ProcesoHelper.toProceso(procesoDTO, new Proceso());

        if (procesoDTO.getProcesoDTO() != null) {
            Proceso procesoPadre = em.find(Proceso.class, procesoDTO.getProcesoDTO().getIdProceso());
            if (procesoPadre != null) {
                proceso.setProceso(procesoPadre);
            } else {
                throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001055);
            }
        }
        em.persist(proceso);
        em.flush();

        return procesoDTO;
    }

    @Override
    public void actualizarProceso(ProcesoDTO procesoDTO) {
        logger.debug("AdministracionEJB::actualizarProceso");

        Proceso proceso = em.find(Proceso.class, procesoDTO.getIdProceso());
        Proceso procesoMod = ProcesoHelper.toProceso(procesoDTO, proceso);
        if (procesoDTO.getProcesoDTO() != null) {
            Proceso procesoPadre = ProcesoHelper.toProceso(procesoDTO.getProcesoDTO(), null);
            procesoMod.setProceso(procesoPadre);
        }
        if (procesoDTO.getListCargosDTO() != null && !procesoDTO.getListCargosDTO().isEmpty()) {
            List<Cargo> lCargos = new ArrayList<Cargo>();
            for (CargoDTO cargoDTO : procesoDTO.getListCargosDTO()) {
                // cargoDTO = consultarCargoId(cargoDTO.getIdCargo());
                Cargo cargo = CargoHelper.toCargo(cargoDTO, null);
                // if (cargoDTO.getOrganizacionDTO() != null){
                // Organizacion organizacion = OrganizacionHelper
                // .toOrganizacion(cargoDTO.getOrganizacionDTO(), null);
                // cargo.setOrganizacion(organizacion);
                // }
                lCargos.add(cargo);
            }
            procesoMod.setCargos(lCargos);
        }
        em.merge(procesoMod);
        em.flush();

    }

    @Override
    public ProcesoDTO consultarProcesoId(long idProceso) {
        logger.debug("AdministracionEJB::consultarProcesoId");
        ProcesoDTO procesoDTO = null;

        Proceso proceso = em.find(Proceso.class, idProceso);
        if (proceso != null) {
            procesoDTO = ProcesoHelper.toProcesoDTO(proceso);
            if (proceso.getCargos() != null && !proceso.getCargos().isEmpty()) {
                List<CargoDTO> lCargoDTOs = new ArrayList<CargoDTO>();
                for (Cargo cargo : proceso.getCargos()) {
                    CargoDTO cargoDTO = CargoHelper.toCargoDTO(cargo);
                    lCargoDTOs.add(cargoDTO);
                }
                procesoDTO.setListCargosDTO(lCargoDTOs);
            }
            if (proceso.getProceso() != null) {
                ProcesoDTO procesoPadre = ProcesoHelper.toProcesoDTO(proceso.getProceso());
                procesoDTO.setProcesoDTO(procesoPadre);
            }
        }
        return procesoDTO;
    }

    @Override
    public ProcesoDTO consultarProceso(String codigoProceso) {
        logger.debug("AdministracionEJB::consultarProceso(String)");
        ProcesoDTO procesoDTO = null;

        TypedQuery<Proceso> query = em.createNamedQuery(Proceso.SQ_FIND_BY_CODIGO, Proceso.class);
        query.setParameter("codigoProceso", codigoProceso);
        List<Proceso> procesos = query.getResultList();
        if (procesos != null && !procesos.isEmpty()) {
            Proceso proceso = procesos.get(0);
            procesoDTO = ProcesoHelper.toProcesoDTO(proceso);
            if (proceso.getProceso() != null) {
                ProcesoDTO procesoPadre = ProcesoHelper.toProcesoDTO(proceso.getProceso());
                procesoDTO.setProcesoDTO(procesoPadre);
            }
        }
        return procesoDTO;
    }

    // FIN METODOS PROCESO

    // INICIO METODOS FUNCIONARIO-CARGO

    @Override
    public FuncionarioCargoDTO registrarFuncionarioCargo(FuncionarioCargoDTO funcionarioCargoDTO)
            throws DocumentosException {
        logger.debug("AdministracionEJB::registrarFuncionarioCargo");

        FuncionarioCargo funcionarioCargo = FuncionarioCargoHelper.toFuncionarioCargo(funcionarioCargoDTO,
                new FuncionarioCargo());
        Cargo cargo = null;
        if (funcionarioCargoDTO.getCargoDTO() != null) {
            if (funcionarioCargoDTO.getCargoDTO().getIdCargo() != 0) {
                cargo = em.find(Cargo.class, funcionarioCargoDTO.getCargoDTO().getIdCargo());
                funcionarioCargo.setCargo(cargo);
            }
        }
        if (cargo == null) {
            throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001066);
        }

        if (funcionarioCargoDTO.getFuncionarioDTO() != null) {
            if (funcionarioCargoDTO.getFuncionarioDTO().getIdFuncionario() != 0) {
                Funcionario funcionario = FuncionarioHelper.toFuncionario(funcionarioCargoDTO.getFuncionarioDTO(),
                        new Funcionario());
                funcionarioCargo.setFuncionario(funcionario);
            }
        }

        em.persist(funcionarioCargo);
        funcionarioCargoDTO.setIdFuncionarioCargo(funcionarioCargo.getIdFuncionarioCargo());

        return funcionarioCargoDTO;
    }

    @Override
    public List<FuncionarioCargoDTO> consultarFuncionarioCargo(FuncionarioCargoDTO funcionarioCargoDTO) {
        logger.debug("AdministracionEJB::consultarFuncionarioCargo");
        List<FuncionarioCargoDTO> lFuncionarioCargoDTOs = new ArrayList<FuncionarioCargoDTO>();
        Map<String, Object> parametros = new HashMap<>(5);
        GenericDao<FuncionarioCargo> funcionarioCargoDao = new GenericDao<>(FuncionarioCargo.class, em);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT fc FROM FuncionarioCargo fc");
        sql.append(" WHERE 1=1");
        if (funcionarioCargoDTO.getCargoDTO() != null && funcionarioCargoDTO.getCargoDTO().getIdCargo() != 0) {
            sql.append(" AND fc.cargo.idCargo = :idCargo");
            parametros.put("idCargo", funcionarioCargoDTO.getCargoDTO().getIdCargo());
        }
        if (funcionarioCargoDTO.getFuncionarioDTO() != null
                && funcionarioCargoDTO.getFuncionarioDTO().getIdFuncionario() != 0) {
            sql.append(" AND fc.funcionario.idFuncionario = :idFuncionario");
            parametros.put("idFuncionario", funcionarioCargoDTO.getFuncionarioDTO().getIdFuncionario());
        }

        sql.append(" ORDER BY fc.idFuncionarioCargo");

        List<FuncionarioCargo> lFuncionarioCargos = funcionarioCargoDao.buildAndExecuteQuery(sql.toString(),
                parametros);

        for (FuncionarioCargo funcionarioCargo : lFuncionarioCargos) {
            FuncionarioCargoDTO funCargoDTO = FuncionarioCargoHelper.toFuncionarioCargoDTO(funcionarioCargo);
            if (funcionarioCargo.getCargo() != null && funcionarioCargo.getCargo().getIdCargo() != 0) {
                CargoDTO cargoDTO = CargoHelper.toCargoDTO(funcionarioCargo.getCargo());
                // CargoDTO cargoDTO = consultarCargoId(
                // funcionarioCargo.getCargo().getIdCargo());
                funCargoDTO.setCargoDTO(cargoDTO);
            }
            // if (funcionarioCargo.getFuncionario() != null &&
            // funcionarioCargo.getFuncionario().getIdFuncionario() != 0){
            // FuncionarioDTO funcionarioDTO = consultarFuncionarioId(
            // funcionarioCargo.getFuncionario().getIdFuncionario());
            // funCargoDTO.setFuncionarioDTO(funcionarioDTO);
            // }
            lFuncionarioCargoDTOs.add(funCargoDTO);
        }

        return lFuncionarioCargoDTOs;
    }

    /**
     * Actualiza un funcionarioCargo enviado como parametro
     * 
     * @param funcionarioCargoDTO
     *            Contiene los datos a actualizar
     * @author dixon.alvarez
     */
    private void actualizarFuncionarioCargo(FuncionarioCargoDTO funcionarioCargoDTO) {
        logger.debug("AdministracionEJB::actualizarFuncionarioCargo");

        FuncionarioCargo funcionarioCargo = em.find(FuncionarioCargo.class,
                funcionarioCargoDTO.getIdFuncionarioCargo());
        FuncionarioCargo funcionarioCargoMod = FuncionarioCargoHelper.toFuncionarioCargo(funcionarioCargoDTO,
                funcionarioCargo);

        if (funcionarioCargoDTO.getCargoDTO() != null) {
            Cargo cargo = CargoHelper.toCargo(funcionarioCargoDTO.getCargoDTO(), null);
            funcionarioCargoMod.setCargo(cargo);
        }
        if (funcionarioCargoDTO.getFuncionarioDTO() != null) {
            Funcionario funcionario = FuncionarioHelper.toFuncionario(funcionarioCargoDTO.getFuncionarioDTO(), null);
            funcionarioCargoMod.setFuncionario(funcionario);
        }
        em.merge(funcionarioCargoMod);

    }

    // FIN METODOS FUNCIONARIO-CARGO

    // INICIO METODOS IPARAMETROORGANIZACION

    @Override
    public void actualizarParametroSistema(ParametroSistemaDTO parametroSistemaDTO) {
        logger.debug("AdministracionEJB::actualizarParametroSistema");
        ParametroSistema parametroSistema = em.find(ParametroSistema.class,
                parametroSistemaDTO.getIdParametroSistema());
        ParametroSistema parametroOrganizacionMod = ParametrosSistemaHelper.toParametroSistema(parametroSistemaDTO,
                parametroSistema);

        if (parametroSistemaDTO.getTipoDatoDTO() != null) {
            TipoDato tipoDato = TipoDatoHelper.toTipoDato(parametroSistemaDTO.getTipoDatoDTO(), null);
            parametroOrganizacionMod.setTipoDato(tipoDato);
        }
        em.merge(parametroOrganizacionMod);
        em.flush();
    }

    @Override
    public List<ParametroSistemaDTO> consultarParametroSistema(ParametroSistemaDTO parametroSistemaDTO) {
        logger.debug("AdministracionEJB::consultarParametroSistema");
        List<ParametroSistemaDTO> lParametroSistemaDTOs = new ArrayList<ParametroSistemaDTO>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p FROM ParametroSistema p");
        sql.append(" WHERE 1 = 1");
        if (parametroSistemaDTO != null) {
            if (parametroSistemaDTO.getIdParametroSistema() != 0) {
                sql.append(" AND p.idParametroSistema = :idParametro");
            }
            if (parametroSistemaDTO.getNombreParametro() != null
                    && !parametroSistemaDTO.getNombreParametro().isEmpty()) {
                sql.append(" AND TRIM(UPPER(p.nombreParametro)) LIKE :nombre");
            }
        }
        sql.append(" ORDER BY p.nombreParametro ASC");
        TypedQuery<ParametroSistema> consulta = em.createQuery(sql.toString(), ParametroSistema.class);
        if (parametroSistemaDTO != null) {
            if (parametroSistemaDTO.getIdParametroSistema() != 0) {
                consulta.setParameter("idParametro", parametroSistemaDTO.getIdParametroSistema());
            }
            if (parametroSistemaDTO.getNombreParametro() != null
                    && !parametroSistemaDTO.getNombreParametro().isEmpty()) {
                consulta.setParameter("nombre",
                        "%" + parametroSistemaDTO.getNombreParametro().toUpperCase().trim() + "%");
            }
        }
        List<ParametroSistema> lParametroSistemas = consulta.getResultList();
        for (ParametroSistema parametroSistema : lParametroSistemas) {
            ParametroSistemaDTO paramOrganizacionDTO = ParametrosSistemaHelper.toParametroSistemaDTO(parametroSistema);

            if (parametroSistema.getTipoDato() != null) {
                TipoDatoDTO tipoDatoDTO = TipoDatoHelper.toTipoDatoDTO(parametroSistema.getTipoDato());
                paramOrganizacionDTO.setTipoDatoDTO(tipoDatoDTO);
            }
            lParametroSistemaDTOs.add(paramOrganizacionDTO);
        }
        return lParametroSistemaDTOs;
    }

    @Override
    public String consultarValorParametroSistema(EnumParametrosSistema parametro) {
        logger.debug("AdministracionEJB::consultarValorParametroSistema(EnumParametrosSistema)");
        GenericDao<ParametroSistema> paramDao = new GenericDao<>(ParametroSistema.class, em);
        ParametroSistema result = paramDao.findUniqueByAttribute("nombreParametro", parametro.toString());
        if (result != null) {
            logger.trace("Se encuentra el parametro de nombre: " + parametro + " con el valor= "
                    + result.getValorParametro());
            return result.getValorParametro();
        } else {
            logger.info("No se encontró el parametro: " + parametro);
            return "";
        }
    }

    // FIN METODOS IPARAMETROORGANIZACION
}
