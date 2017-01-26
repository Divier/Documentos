package co.com.datatools.documentos.negocio.ejb.documentos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import co.com.datatools.documentos.administracion.CargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.TipoFechaReferenciaDTO;
import co.com.datatools.documentos.administracion.TipoFirmaPlantillaDTO;
import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.constantes.ConstantesPlantillas;
import co.com.datatools.documentos.documentos.DocumentoDTO;
import co.com.datatools.documentos.documentos.GeneraDocumentoDTO;
import co.com.datatools.documentos.entidades.FirmaPlantilla;
import co.com.datatools.documentos.entidades.JasperPlantilla;
import co.com.datatools.documentos.entidades.Plantilla;
import co.com.datatools.documentos.entidades.PlantillaConfiguracion;
import co.com.datatools.documentos.entidades.Variable;
import co.com.datatools.documentos.entidades.XmlPlantilla;
import co.com.datatools.documentos.enumeraciones.EnumFormatoDocumento;
import co.com.datatools.documentos.enumeraciones.EnumParametrosSistema;
import co.com.datatools.documentos.excepcion.DocumentosRuntimeException;
import co.com.datatools.documentos.jasper.JasperUtil;
import co.com.datatools.documentos.jasper.JasperUtilDTO;
import co.com.datatools.documentos.negocio.helper.CargoHelper;
import co.com.datatools.documentos.negocio.helper.FirmaPlantillaHelper;
import co.com.datatools.documentos.negocio.helper.FuncionarioHelper;
import co.com.datatools.documentos.negocio.helper.JasperPlantillaHelper;
import co.com.datatools.documentos.negocio.helper.PlantillaConfiguracionHelper;
import co.com.datatools.documentos.negocio.helper.PlantillaHelper;
import co.com.datatools.documentos.negocio.helper.TipoFechaReferenciaHelper;
import co.com.datatools.documentos.negocio.helper.TipoFirmaPlantillaHelper;
import co.com.datatools.documentos.negocio.helper.UsuarioOrganizacionHelper;
import co.com.datatools.documentos.negocio.helper.VariableHelper;
import co.com.datatools.documentos.negocio.helper.XmlPlantillaHelper;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILParametroSistema;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILProceso;
import co.com.datatools.documentos.negocio.interfaces.documentos.ILDocumento;
import co.com.datatools.documentos.negocio.interfaces.documentos.ILFirmaPlantilla;
import co.com.datatools.documentos.negocio.interfaces.documentos.ILJasperPlantilla;
import co.com.datatools.documentos.negocio.interfaces.documentos.ILPlantilla;
import co.com.datatools.documentos.negocio.interfaces.documentos.ILPlantillaConfiguracion;
import co.com.datatools.documentos.negocio.interfaces.documentos.ILVariable;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRFirmaPlantilla;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRJasperPlantilla;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRPlantilla;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRPlantillaConfiguracion;
import co.com.datatools.documentos.plantillas.FirmaPlantillaDTO;
import co.com.datatools.documentos.plantillas.JasperPlantillaDTO;
import co.com.datatools.documentos.plantillas.PlantillaConfiguracionDTO;
import co.com.datatools.documentos.plantillas.PlantillaConsultaDTO;
import co.com.datatools.documentos.plantillas.PlantillaDTO;
import co.com.datatools.documentos.plantillas.VariableDTO;
import co.com.datatools.documentos.plantillas.XmlPlantillaDTO;
import co.com.datatools.util.GenericDao;
import co.com.datatools.util.date.UtilFecha;

/**
 * Session Bean implementation class PlantillaEJB: EJB para el CRUD de tablas de administraci�n
 * 
 * @author julio.pinzon
 * 
 */
@Stateless(name = "PlantillaEJB")
@LocalBean
public class PlantillaEJB implements IRPlantilla, ILPlantilla, ILFirmaPlantilla, IRFirmaPlantilla, ILJasperPlantilla,
        IRJasperPlantilla, ILPlantillaConfiguracion, IRPlantillaConfiguracion {

    private static final Logger logger = Logger.getLogger(PlantillaEJB.class.getName());

    @PersistenceContext(unitName = "DocumentosJPA")
    EntityManager em;

    @EJB
    private ILVariable ilVariable;

    @EJB
    private ILDocumento ilDocumento;

    @EJB
    private ILParametroSistema ilParametroSistema;

    @EJB
    private ILProceso ilProceso;

    /**
     * Default constructor.
     */
    public PlantillaEJB() {

    }

    // CRUD Tabla de Plantillas

    @Override
    public List<PlantillaDTO> consultarPlantilla(PlantillaDTO plantillaDTO, Date fechaCorte) {
        logger.debug("PlantillaEJB::consultarPlantilla");
        List<PlantillaDTO> lPlantillasDTOs = new ArrayList<PlantillaDTO>();

        // Definici�n del string que alimenta la construcci�n del query de
        // acuerdo a los par�metros enviados
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p FROM Plantilla p ");
        sql.append("LEFT JOIN p.plantilla ");
        sql.append("WHERE 1 = 1 ");

        if (plantillaDTO.getCodigoPlantilla() != null && !plantillaDTO.getCodigoPlantilla().isEmpty()) {
            sql.append(" AND TRIM(UPPER(p.codigoPlantilla)) = :codigoPlantilla");
        }

        if (plantillaDTO.getProcesoDTO() != null && plantillaDTO.getProcesoDTO().getIdProceso() != 0) {
            sql.append(" AND p.proceso.idProceso = :idProceso");
        }

        // Fecha de corte
        if (fechaCorte != null) {
            sql.append(" AND ((p.fechaInicio <= :fechaCorte");
            sql.append(" AND p.fechaFin >= :fechaCorte)");
            sql.append(" OR (p.fechaInicio <= :fechaCorte");
            sql.append(" AND p.fechaFin IS NULL))");
        }
        sql.append(" ORDER BY p.codigoPlantilla, p.versionPlantilla DESC");
        TypedQuery<Plantilla> consulta = em.createQuery(sql.toString(), Plantilla.class);

        if (plantillaDTO.getCodigoPlantilla() != null && !plantillaDTO.getCodigoPlantilla().isEmpty()) {
            consulta.setParameter("codigoPlantilla", plantillaDTO.getCodigoPlantilla().trim().toUpperCase());
        }
        if (fechaCorte != null) {
            Calendar fecha = UtilFecha.resetTime(fechaCorte);
            consulta.setParameter("fechaCorte", fecha.getTime());
        }
        if (plantillaDTO.getProcesoDTO() != null && plantillaDTO.getProcesoDTO().getIdProceso() != 0) {
            consulta.setParameter("idProceso", plantillaDTO.getProcesoDTO().getIdProceso());
        }

        List<Plantilla> lPlantillas = consulta.getResultList();
        for (Plantilla plantilla : lPlantillas) {
            PlantillaDTO plaDto = PlantillaHelper.toPlantillaDTO(plantilla);
            if (plantilla.getUsuarioOrganizacion() != null) {
                UsuarioOrganizacionDTO usuarioOrganizacionDTO = UsuarioOrganizacionHelper
                        .toUsuarioOrganizacionDTO(plantilla.getUsuarioOrganizacion());
                plaDto.setUsuarioOrganizacionDTO(usuarioOrganizacionDTO);
            }
            if (plantilla.getProceso() != null) {
                ProcesoDTO procesoDTO = ilProceso.consultarProcesoId(plantilla.getProceso().getIdProceso());
                plaDto.setProcesoDTO(procesoDTO);
            }
            if (plantilla.getPlantilla() != null) {
                PlantillaDTO plantillaOrigenDTO = PlantillaHelper.toPlantillaDTO(plantilla.getPlantilla());
                plaDto.setPlantillaOrigenDTO(plantillaOrigenDTO);
            }
            if (plantilla.getXmlPlantilla() != null) {
                XmlPlantillaDTO xmlPlantillaDTO = XmlPlantillaHelper.toXmlPlantillaDTO(plantilla.getXmlPlantilla());
                plaDto.setXmlPlantillaDTO(xmlPlantillaDTO);
            }

            if (plantilla.getVariables() != null) {
                List<VariableDTO> variablesDTO = new ArrayList<VariableDTO>();
                for (Variable variable : plantilla.getVariables()) {
                    variablesDTO.add(VariableHelper.toVariableDTO(variable));
                }
                plaDto.setListVariablesDTO(variablesDTO);
            }

            lPlantillasDTOs.add(plaDto);
        }

        return lPlantillasDTOs;
    }

    @Override
    public boolean validarPlantilla(PlantillaDTO plantillaDTO) {
        logger.debug("PlantillaEJB::consultarPlantilla");

        // Definición del string que alimenta la construcci�n del query de
        // acuerdo a los par�metros enviados
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p FROM Plantilla p ");
        sql.append("WHERE p.versionPlantilla = :version ");

        if (plantillaDTO.getIdPlantilla() != 0) {
            sql.append(" AND p.idPlantilla <> :idPlantilla");
        }

        if (plantillaDTO.getCodigoPlantilla() != null && !plantillaDTO.getCodigoPlantilla().isEmpty()) {
            sql.append(" AND TRIM(UPPER(p.codigoPlantilla)) = :codigoPlantilla");
        }

        if (plantillaDTO.getNombrePlantilla() != null && !plantillaDTO.getNombrePlantilla().isEmpty()) {
            sql.append(" AND TRIM(UPPER(p.nombrePlantilla)) = :nombrePlantilla");
        }

        TypedQuery<Plantilla> consulta = em.createQuery(sql.toString(), Plantilla.class);

        consulta.setParameter("version", plantillaDTO.getVersionPlantilla());
        if (plantillaDTO.getIdPlantilla() != 0) {
            consulta.setParameter("idPlantilla", plantillaDTO.getIdPlantilla());
        }

        if (plantillaDTO.getCodigoPlantilla() != null && !plantillaDTO.getCodigoPlantilla().isEmpty()) {
            consulta.setParameter("codigoPlantilla", plantillaDTO.getCodigoPlantilla().trim().toUpperCase());
        }
        if (plantillaDTO.getNombrePlantilla() != null && !plantillaDTO.getNombrePlantilla().isEmpty()) {
            consulta.setParameter("nombrePlantilla", plantillaDTO.getNombrePlantilla().toUpperCase().trim());
        }

        List<Plantilla> lPlantillas = consulta.getResultList();

        return !lPlantillas.isEmpty();
    }

    @Override
    public List<PlantillaDTO> consultarPlantilla(PlantillaConsultaDTO plantillaConsultaDTO) {
        logger.debug("PlantillaEJB::consultarPlantilla");
        List<PlantillaDTO> lPlantillasDTOs = new ArrayList<PlantillaDTO>();

        // Definicion del string que alimenta la construccion del query de
        // acuerdo a los parametros enviados
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p FROM Plantilla p ");
        sql.append("WHERE 1=1 ");

        PlantillaDTO plantillaDTO = plantillaConsultaDTO.getPlantillaDTO();

        if (plantillaConsultaDTO.isValidaOrigen()) {
            if (plantillaDTO.getPlantillaOrigenDTO() != null) {
                sql.append(" AND p.plantilla.idPlantilla = :idPlantilla");
            } else {
                sql.append(" AND p.plantilla IS NULL");
            }
        }
        if (plantillaDTO.getNombrePlantilla() != null && !plantillaDTO.getNombrePlantilla().isEmpty()) {
            sql.append(" AND TRIM(UPPER(p.nombrePlantilla)) LIKE :nombre");
        }
        if (plantillaDTO.getProcesoDTO() != null && plantillaDTO.getProcesoDTO().getIdProceso() != 0) {
            sql.append(" AND p.proceso.idProceso = :idProceso");
        }
        if (plantillaDTO.getCodigoPlantilla() != null && !plantillaDTO.getCodigoPlantilla().isEmpty()) {
            sql.append(" AND TRIM(UPPER(p.codigoPlantilla)) LIKE :codigoPlantilla");
        }
        if (plantillaDTO.getVersionPlantilla() != 0) {
            sql.append(" AND p.versionPlantilla = :version");
        }
        if (plantillaConsultaDTO.isUltimaVersion()) {
            sql.append(" AND (p.fechaFin IS NULL OR p.versionPlantilla =");
            sql.append(
                    " (SELECT MAX(pl.versionPlantilla) FROM Plantilla pl WHERE pl.plantilla.idPlantilla = p.plantilla.idPlantilla )) ");
        }

        // Fechas
        if (plantillaConsultaDTO.getFechaInicioDesde() != null) {
            sql.append(" AND p.fechaInicio >= :fechaInicioInicio");
            sql.append(" AND p.fechaInicio <= :fechaInicioFinal");
        }
        if (plantillaConsultaDTO.getFechaFinDesde() != null) {
            sql.append(" AND p.fechaFin >= :fechaFinInicio");
            sql.append(" AND p.fechaFin <= :fechaFinFinal");
        }

        sql.append(" ORDER BY p.idPlantilla, p.versionPlantilla");
        TypedQuery<Plantilla> consulta = em.createQuery(sql.toString(), Plantilla.class);
        if (plantillaConsultaDTO.isValidaOrigen()) {
            if (plantillaDTO.getPlantillaOrigenDTO() != null) {
                consulta.setParameter("idPlantilla", plantillaDTO.getPlantillaOrigenDTO().getIdPlantilla());
            }
        }
        if (plantillaDTO.getNombrePlantilla() != null && !plantillaDTO.getNombrePlantilla().isEmpty()) {
            consulta.setParameter("nombre", "%" + plantillaDTO.getNombrePlantilla().toUpperCase().trim() + "%");
        }
        if (plantillaDTO.getProcesoDTO() != null && plantillaDTO.getProcesoDTO().getIdProceso() != 0) {
            consulta.setParameter("idProceso", plantillaDTO.getProcesoDTO().getIdProceso());
        }
        if (plantillaDTO.getCodigoPlantilla() != null && !plantillaDTO.getCodigoPlantilla().isEmpty()) {
            consulta.setParameter("codigoPlantilla",
                    "%" + plantillaDTO.getCodigoPlantilla().trim().toUpperCase() + "%");
        }
        if (plantillaDTO.getVersionPlantilla() != 0) {
            consulta.setParameter("version", plantillaDTO.getVersionPlantilla());
        }

        // Fechas
        if (plantillaConsultaDTO.getFechaInicioDesde() != null) {
            Calendar fechaFinal = Calendar.getInstance();
            fechaFinal.setTime(plantillaConsultaDTO.getFechaInicioHasta());
            fechaFinal.set(Calendar.HOUR, 23);
            fechaFinal.set(Calendar.MINUTE, 59);
            fechaFinal.set(Calendar.SECOND, 59);
            consulta.setParameter("fechaInicioInicio", plantillaConsultaDTO.getFechaInicioDesde());
            consulta.setParameter("fechaInicioFinal", fechaFinal.getTime());
        }
        if (plantillaConsultaDTO.getFechaFinDesde() != null) {
            Calendar fechaFinal = Calendar.getInstance();
            fechaFinal.setTime(plantillaConsultaDTO.getFechaFinHasta());
            fechaFinal.set(Calendar.HOUR, 23);
            fechaFinal.set(Calendar.MINUTE, 59);
            fechaFinal.set(Calendar.SECOND, 59);
            consulta.setParameter("fechaFinInicio", plantillaConsultaDTO.getFechaFinDesde());
            consulta.setParameter("fechaFinFinal", fechaFinal.getTime());
        }
        List<Plantilla> lPlantillas = consulta.getResultList();
        for (Plantilla plantilla : lPlantillas) {
            PlantillaDTO plaDto = PlantillaHelper.toPlantillaDTO(plantilla);
            if (plantilla.getUsuarioOrganizacion() != null) {
                UsuarioOrganizacionDTO usuarioOrganizacionDTO = UsuarioOrganizacionHelper
                        .toUsuarioOrganizacionDTO(plantilla.getUsuarioOrganizacion());
                plaDto.setUsuarioOrganizacionDTO(usuarioOrganizacionDTO);
            }
            if (plantilla.getProceso() != null) {
                ProcesoDTO procesoDTO = ilProceso.consultarProcesoId(plantilla.getProceso().getIdProceso());
                plaDto.setProcesoDTO(procesoDTO);
            }
            if (plantilla.getPlantilla() != null) {
                PlantillaDTO plantillaOrigenDTO = PlantillaHelper.toPlantillaDTO(plantilla.getPlantilla());
                plaDto.setPlantillaOrigenDTO(plantillaOrigenDTO);
            }
            if (plantilla.getXmlPlantilla() != null) {
                XmlPlantillaDTO xmlPlantillaDTO = XmlPlantillaHelper.toXmlPlantillaDTO(plantilla.getXmlPlantilla());
                plaDto.setXmlPlantillaDTO(xmlPlantillaDTO);
            }

            if (plantilla.getVariables() != null) {
                List<VariableDTO> variablesDTO = new ArrayList<VariableDTO>();
                for (Variable variable : plantilla.getVariables()) {
                    variablesDTO.add(VariableHelper.toVariableDTO(variable));
                }
                plaDto.setListVariablesDTO(variablesDTO);
            }
            plaDto.setUltimaVersion(plantillaConsultaDTO.isUltimaVersion());

            lPlantillasDTOs.add(plaDto);
        }

        return lPlantillasDTOs;
    }

    @Override
    public PlantillaDTO registrarPlantilla(PlantillaDTO plantillaDTO) {
        logger.debug("PlantillaEJB::registrarPlantilla");
        Plantilla plantilla = PlantillaHelper.toPlantilla(plantillaDTO, new Plantilla());

        // Completa las relaciones de la plantilla
        plantilla = PlantillaHelper.completarPlantilla(plantillaDTO, plantilla);

        // Completa las variables
        if (plantillaDTO.getListVariablesDTO() != null) {
            plantilla.setVariables(new ArrayList<Variable>());
            for (VariableDTO variableDTO : plantillaDTO.getListVariablesDTO()) {
                plantilla.getVariables().add(em.find(Variable.class, variableDTO.getIdVariable()));
            }
        }

        em.persist(plantilla);
        em.flush();

        plantillaDTO.setIdPlantilla(plantilla.getIdPlantilla());

        // Registra los objetos relacionados con la plantilla
        registrarRelacionesPlantilla(plantillaDTO);

        return plantillaDTO;

    }

    @Override
    public void actualizarPlantilla(PlantillaDTO plantillaDTO) {
        logger.debug("PlantillaEJB::modificarPlantilla");

        Plantilla plantillaGuardada = em.find(Plantilla.class, plantillaDTO.getIdPlantilla());
        Plantilla plantilla = PlantillaHelper.toPlantilla(plantillaDTO, plantillaGuardada);

        if (plantillaDTO.isEditar()) {
            // Eliminar los datos relacionados con la plantilla
            eliminarRelacionesPlantilla(plantilla);
        }

        // Completa las relaciones de la plantilla
        plantilla = PlantillaHelper.completarPlantilla(plantillaDTO, plantilla);

        // Completa las variables
        if (plantillaDTO.getListVariablesDTO() != null) {
            plantilla.setVariables(new ArrayList<Variable>());
            for (VariableDTO variableDTO : plantillaDTO.getListVariablesDTO()) {
                plantilla.getVariables().add(em.find(Variable.class, variableDTO.getIdVariable()));
            }
        }

        if (plantillaDTO.isEditar()) {
            registrarRelacionesPlantilla(plantillaDTO);
        }

        em.merge(plantilla);
        em.flush();
    }

    @Override
    public void eliminarPlantilla(final Integer idPlantilla) {
        Plantilla entidadPlantilla = em.find(Plantilla.class, idPlantilla);

        // Eliminar los datos relacionados con la plantilla
        eliminarRelacionesPlantilla(entidadPlantilla);

        // Contempla el caso en que sea la plantilla padre, es decir la inicial
        if (entidadPlantilla.getPlantilla() != null) {
            PlantillaConsultaDTO plantillaConsulta = new PlantillaConsultaDTO();
            PlantillaDTO plantillaTemp = new PlantillaDTO();
            plantillaTemp.setPlantillaOrigenDTO(PlantillaHelper.toPlantillaDTO(entidadPlantilla.getPlantilla()));
            plantillaConsulta.setPlantillaDTO(plantillaTemp);
            plantillaConsulta.setValidaOrigen(true);
            plantillaConsulta.setUltimaVersion(false);
            List<PlantillaDTO> plantillasHistorico = consultarPlantilla(plantillaConsulta);
            // Si es la primera version primero vuelve nulo el origen
            if (plantillasHistorico != null && !plantillasHistorico.isEmpty()) {
                // Si tiene varios hijos
                if (plantillasHistorico.size() > 1) {
                    // Busca la siguiente version
                    PlantillaDTO plantillaPadre = null;
                    int versionMenor = plantillasHistorico.get(plantillasHistorico.size() - 1).getVersionPlantilla();
                    // Recorre en sentido inverso para hallar la plantilla que pasara a ser la plantilla inicial
                    for (int i = plantillasHistorico.size() - 1; i >= 0; i--) {
                        PlantillaDTO plantillaHija = plantillasHistorico.get(i);
                        if (plantillaHija.getIdPlantilla() != entidadPlantilla.getIdPlantilla()
                                && plantillaHija.getVersionPlantilla() < versionMenor) {
                            plantillaPadre = plantillaHija;
                            versionMenor = plantillaHija.getVersionPlantilla();
                        }
                    }
                    // Actualiza al nuevo padre a todas las versiones de la plantilla
                    if (plantillaPadre != null) {
                        for (PlantillaDTO plantillaHija : plantillasHistorico) {
                            if (plantillaHija.getIdPlantilla() != entidadPlantilla.getIdPlantilla()) {
                                plantillaHija.setPlantillaOrigenDTO(plantillaPadre);
                                actualizarPlantilla(plantillaHija);
                            }
                        }
                    }
                }
                // Quita las relaciones con la plantilla
                entidadPlantilla.setPlantilla(null);
                entidadPlantilla.setPlantillas(new ArrayList<Plantilla>());
                em.merge(entidadPlantilla);
                em.flush();
            }
        }

        // Eliminar la firma
        em.remove(entidadPlantilla);
        em.flush();
        logger.debug("Se eliminó la plantilla con Id= " + idPlantilla);
    }

    /**
     * Elimina los datos relacionados con la plantilla
     * 
     * @param entidadPlantilla
     */
    private void eliminarRelacionesPlantilla(Plantilla entidadPlantilla) {
        // Eliminar las firmas
        GenericDao<FirmaPlantilla> firmaPlantDao = new GenericDao<>(FirmaPlantilla.class, em);
        // Se consulta cada entidad de historico y se borra explicitamente
        // para poder borrar en cascada la relacion en historico_rol_usuario
        final List<FirmaPlantilla> firmasPlantilla = firmaPlantDao.findByAttribute("plantilla.idPlantilla",
                entidadPlantilla.getIdPlantilla());
        for (FirmaPlantilla firmaPlantilla : firmasPlantilla) {
            em.remove(firmaPlantilla);
            logger.debug("Se eliminó la firma con Id= " + firmaPlantilla.getIdFirmaPlantilla() + " para la plantilla = "
                    + entidadPlantilla.getIdPlantilla());
        }
        // Eliminar el xml
        GenericDao<XmlPlantilla> xmlPlantDao = new GenericDao<>(XmlPlantilla.class, em);
        // Se consulta cada entidad de historico y se borra explicitamente
        // para poder borrar en cascada la relacion en historico_rol_usuario
        final List<XmlPlantilla> xmlsPlantDao = xmlPlantDao.findByAttribute("plantilla.idPlantilla",
                entidadPlantilla.getIdPlantilla());
        for (XmlPlantilla xmlPlantilla : xmlsPlantDao) {
            em.remove(xmlPlantilla);
            logger.debug("Se eliminó la XmlPlantilla con Id= " + xmlPlantilla.getIdXmlPlantilla()
                    + " para la plantilla = " + entidadPlantilla.getIdPlantilla());
        }

        // Eliminar las jaspers
        GenericDao<JasperPlantilla> jasperPlantDao = new GenericDao<>(JasperPlantilla.class, em);
        // Se consulta cada entidad de historico y se borra explicitamente
        // para poder borrar en cascada la relacion en historico_rol_usuario
        final List<JasperPlantilla> jaspersPlantilla = jasperPlantDao.findByAttribute("plantilla.idPlantilla",
                entidadPlantilla.getIdPlantilla());
        for (JasperPlantilla jasperPlantilla : jaspersPlantilla) {
            em.remove(jasperPlantilla);
            logger.debug("Se eliminó la jasper con Id= " + jasperPlantilla.getIdJasperPlantilla()
                    + " para la plantilla = " + entidadPlantilla.getIdPlantilla());
        }

        entidadPlantilla.setXmlPlantilla(null);
        entidadPlantilla.setJaspers(new ArrayList<JasperPlantilla>());
        entidadPlantilla.setFirmaPlantillas(new ArrayList<FirmaPlantilla>());
        entidadPlantilla.setVariables(new ArrayList<Variable>());
        entidadPlantilla = em.merge(entidadPlantilla);

        logger.debug("Se eliminó la plantilla con Id= " + entidadPlantilla.getIdPlantilla());
    }

    /**
     * Registra lobjetos relacionados con la plantilla
     * 
     * @param plantillaDTO
     */
    private void registrarRelacionesPlantilla(PlantillaDTO plantillaDTO) {

        if (plantillaDTO.getXmlPlantillaDTO() != null) {
            plantillaDTO.getXmlPlantillaDTO().setIdXmlPlantilla(plantillaDTO.getIdPlantilla());
            plantillaDTO.getXmlPlantillaDTO().setPlantillaDTO(plantillaDTO);
            plantillaDTO.setXmlPlantillaDTO(registrarXmlPlantilla(plantillaDTO.getXmlPlantillaDTO()));
        }

        if (plantillaDTO.getListFirmasPlantillaDTO() != null) {
            for (FirmaPlantillaDTO firmaPlantillaDTO : plantillaDTO.getListFirmasPlantillaDTO()) {
                firmaPlantillaDTO.setPlantillaDTO(plantillaDTO);
                registrarFirmaPlantilla(firmaPlantillaDTO);
            }
        }

        if (plantillaDTO.getListJasperPlantillaDTO() != null) {
            for (JasperPlantillaDTO jasperPlantillaDTO : plantillaDTO.getListJasperPlantillaDTO()) {
                jasperPlantillaDTO.setPlantillaDTO(plantillaDTO);
                registrarJasperPlantilla(jasperPlantillaDTO);
            }
        }

    }

    /**
     * Método que persiste la entidad XmlPlantilla
     * 
     * @param xmlPlantillaDTO
     *            Contiene los datos de la entidad a persistir
     * @return DTO del XmlPlantilla ingresado
     * @author dixon.alvarez
     */
    private XmlPlantillaDTO registrarXmlPlantilla(XmlPlantillaDTO xmlPlantillaDTO) {
        logger.debug("PlantillaEJB::registrarXmlPlantilla");

        XmlPlantilla xmlPlantilla = XmlPlantillaHelper.toXmlPlantilla(xmlPlantillaDTO, new XmlPlantilla());
        Plantilla plantilla = PlantillaHelper.toPlantilla((xmlPlantillaDTO.getPlantillaDTO()), new Plantilla());
        xmlPlantilla.setPlantilla(plantilla);
        em.persist(xmlPlantilla);
        em.flush();
        xmlPlantillaDTO.setIdXmlPlantilla(xmlPlantilla.getIdXmlPlantilla());

        return xmlPlantillaDTO;
    }

    @Override
    public PlantillaDTO consultarPlantillaId(int idPlantilla) {
        logger.debug("PlantillaEJB::consultarPlantillaId");
        PlantillaDTO plantillaDTO = new PlantillaDTO();
        Plantilla plantilla = em.find(Plantilla.class, idPlantilla);
        plantillaDTO = PlantillaHelper.toPlantillaDTO(plantilla);
        if (plantilla.getUsuarioOrganizacion() != null) {
            UsuarioOrganizacionDTO usuarioOrganizacionDTO = UsuarioOrganizacionHelper
                    .toUsuarioOrganizacionDTO(plantilla.getUsuarioOrganizacion());
            plantillaDTO.setUsuarioOrganizacionDTO(usuarioOrganizacionDTO);
        }
        if (plantilla.getProceso() != null) {
            ProcesoDTO procesoDTO = ilProceso.consultarProcesoId(plantilla.getProceso().getIdProceso());
            plantillaDTO.setProcesoDTO(procesoDTO);
        }
        if (plantilla.getPlantilla() != null) {
            PlantillaDTO plantillaOrigenDTO = PlantillaHelper.toPlantillaDTO(plantilla.getPlantilla());
            plantillaDTO.setPlantillaOrigenDTO(plantillaOrigenDTO);
        }
        if (plantilla.getXmlPlantilla() != null) {
            XmlPlantillaDTO xmlPlantillaDTO = XmlPlantillaHelper.toXmlPlantillaDTO(plantilla.getXmlPlantilla());
            plantillaDTO.setXmlPlantillaDTO(xmlPlantillaDTO);
        }

        if (plantilla.getVariables() != null) {
            List<VariableDTO> variablesDTO = new ArrayList<VariableDTO>();
            for (Variable variable : plantilla.getVariables()) {
                variablesDTO.add(VariableHelper.toVariableDTO(variable));
            }
            plantillaDTO.setListVariablesDTO(variablesDTO);
        }

        return plantillaDTO;
    }

    // INICIO METODOS FIRMA PLANTILLA

    @Override
    public List<FirmaPlantillaDTO> consultarFirmaPlantilla(FirmaPlantillaDTO firmaPlantillaDTO) {
        logger.debug("PlantillaEJB::consultarFirmaPlantilla");
        List<FirmaPlantillaDTO> lFirmaPlantillaDTOs = new ArrayList<FirmaPlantillaDTO>();
        Map<String, Object> parametros = new HashMap<>(5);
        GenericDao<FirmaPlantilla> firmaPlantillaDao = new GenericDao<>(FirmaPlantilla.class, em);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT fp FROM FirmaPlantilla fp");
        sql.append(" WHERE 1=1");
        if (firmaPlantillaDTO != null) {
            if (firmaPlantillaDTO.getIdFirmaPlantilla() != 0) {
                sql.append(" AND fp.idFirmaPlantilla = :idFirmaPlantilla");
                parametros.put("idFirmaPlantilla", firmaPlantillaDTO.getIdFirmaPlantilla());
            }
            if (firmaPlantillaDTO.getPlantillaDTO() != null) {
                sql.append(" AND fp.plantilla.idPlantilla = :idPlantilla");
                parametros.put("idPlantilla", firmaPlantillaDTO.getPlantillaDTO().getIdPlantilla());
            }
            if (firmaPlantillaDTO.getTipoFirmaPlantillaDTO() != null) {
                sql.append(" AND fp.tipoFirmaPlantilla.idTipoFirmaPlantilla = :idTipoFirma");
                parametros.put("idTipoFirma", firmaPlantillaDTO.getTipoFirmaPlantillaDTO().getIdTipoFirmaPlantilla());
            }
        }

        List<FirmaPlantilla> lFirmaPlantillas = firmaPlantillaDao.buildAndExecuteQuery(sql.toString(), parametros);
        for (FirmaPlantilla firmaPlantilla : lFirmaPlantillas) {
            FirmaPlantillaDTO firmaDTO = FirmaPlantillaHelper.toFirmaPlantillaDTO(firmaPlantilla);

            if (firmaPlantilla.getCargo() != null) {
                CargoDTO cargoDTO = CargoHelper.toCargoDTO(firmaPlantilla.getCargo());
                firmaDTO.setCargoDTO(cargoDTO);
            }

            if (firmaPlantilla.getFuncionario() != null) {
                FuncionarioDTO funcionarioDTO = FuncionarioHelper.toFuncionarioDTO(firmaPlantilla.getFuncionario());
                firmaDTO.setFuncionarioDTO(funcionarioDTO);
            }

            if (firmaPlantilla.getPlantilla() != null) {
                PlantillaDTO plantillaDTO = PlantillaHelper.toPlantillaDTO(firmaPlantilla.getPlantilla());
                firmaDTO.setPlantillaDTO(plantillaDTO);
            }

            if (firmaPlantilla.getTipoFechaReferencia() != null) {
                TipoFechaReferenciaDTO tipoFechaReferenciaDTO = TipoFechaReferenciaHelper
                        .toTipoFechaReferenciaDTO(firmaPlantilla.getTipoFechaReferencia());
                firmaDTO.setTipoFechaReferenciaDTO(tipoFechaReferenciaDTO);
            }

            if (firmaPlantilla.getTipoFirmaPlantilla() != null) {
                TipoFirmaPlantillaDTO tipoFirmaPlantillaDTO = TipoFirmaPlantillaHelper
                        .toTipoFirmaPlantillaDTO(firmaPlantilla.getTipoFirmaPlantilla());
                firmaDTO.setTipoFirmaPlantillaDTO(tipoFirmaPlantillaDTO);
            }

            if (firmaPlantilla.getVariable() != null) {
                VariableDTO variableDTO = VariableHelper.toVariableDTO(firmaPlantilla.getVariable());
                firmaDTO.setVariableDTO(variableDTO);
            }

            lFirmaPlantillaDTOs.add(firmaDTO);
        }

        return lFirmaPlantillaDTOs;
    }

    @Override
    public FirmaPlantillaDTO registrarFirmaPlantilla(FirmaPlantillaDTO firmaPlantillaDTO) {
        logger.debug("PlantillaEJB::registrarFirmaPlantilla");

        FirmaPlantilla firmaPlantilla = FirmaPlantillaHelper.toFirmaPlantilla(firmaPlantillaDTO, null);
        if (firmaPlantillaDTO.getFuncionarioDTO() != null) {
            firmaPlantilla.setFuncionario(FuncionarioHelper.toFuncionario(firmaPlantillaDTO.getFuncionarioDTO(), null));
        }
        if (firmaPlantillaDTO.getCargoDTO() != null) {
            firmaPlantilla.setCargo(CargoHelper.toCargo(firmaPlantillaDTO.getCargoDTO(), null));
        }
        if (firmaPlantillaDTO.getTipoFechaReferenciaDTO() != null) {
            firmaPlantilla.setTipoFechaReferencia(TipoFechaReferenciaHelper
                    .toTipoFechaReferencia(firmaPlantillaDTO.getTipoFechaReferenciaDTO(), null));
        }
        if (firmaPlantillaDTO.getTipoFirmaPlantillaDTO() != null) {
            firmaPlantilla.setTipoFirmaPlantilla(
                    TipoFirmaPlantillaHelper.toTipoFirmaPlantilla(firmaPlantillaDTO.getTipoFirmaPlantillaDTO(), null));
        }
        if (firmaPlantillaDTO.getPlantillaDTO() != null) {
            firmaPlantilla.setPlantilla(PlantillaHelper.toPlantilla(firmaPlantillaDTO.getPlantillaDTO(), null));
        }
        if (firmaPlantillaDTO.getVariableDTO() != null) {
            firmaPlantilla.setVariable(VariableHelper.toVariable(firmaPlantillaDTO.getVariableDTO(), null));
        }
        em.persist(firmaPlantilla);
        em.flush();
        firmaPlantillaDTO.setIdFirmaPlantilla(firmaPlantilla.getIdFirmaPlantilla());

        return firmaPlantillaDTO;
    }

    // FIN METODOS FIRMA PLANTILLA

    // INICIO METODOS JASPER PLANTILLA

    @Override
    public List<JasperPlantillaDTO> consultarJasperPlantilla(JasperPlantillaDTO jasperPlantillaDTO) {
        logger.debug("PlantillaEJB::consultarJasperPlantilla");
        List<JasperPlantillaDTO> lJasperPlantillaDTOs = new ArrayList<JasperPlantillaDTO>();
        Map<String, Object> parametros = new HashMap<>(5);
        GenericDao<JasperPlantilla> jasperPlantillaDao = new GenericDao<>(JasperPlantilla.class, em);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT fp FROM JasperPlantilla fp");
        sql.append(" WHERE 1=1");
        if (jasperPlantillaDTO != null) {
            if (jasperPlantillaDTO.getPlantillaDTO() != null) {
                sql.append(" AND fp.plantilla.idPlantilla = :idPlantilla");
                parametros.put("idPlantilla", jasperPlantillaDTO.getPlantillaDTO().getIdPlantilla());
            }
        }

        List<JasperPlantilla> lJasperPlantillas = jasperPlantillaDao.buildAndExecuteQuery(sql.toString(), parametros);
        for (JasperPlantilla jasperPlantilla : lJasperPlantillas) {
            JasperPlantillaDTO jasperDTO = JasperPlantillaHelper.toJasperPlantillaDTO(jasperPlantilla);

            if (jasperPlantilla.getPlantilla() != null) {
                PlantillaDTO plantillaDTO = PlantillaHelper.toPlantillaDTO(jasperPlantilla.getPlantilla());
                jasperDTO.setPlantillaDTO(plantillaDTO);
            }

            lJasperPlantillaDTOs.add(jasperDTO);
        }

        return lJasperPlantillaDTOs;
    }

    @Override
    public JasperPlantillaDTO registrarJasperPlantilla(JasperPlantillaDTO jasperPlantillaDTO) {
        logger.debug("PlantillaEJB::registrarJasperPlantilla");

        // Guardar .jasper en bd

        // Obtiene parametros del sistema con las rutas donde se guardan los reportes temporalmente
        String pathImagenes = ConstantesGeneral.PATH_DOCUMENTOS_FILE_SYSTEM
                + ilParametroSistema.consultarValorParametroSistema(EnumParametrosSistema.PATH_IMAGENES);
        String pathReportes = ConstantesGeneral.PATH_DOCUMENTOS_FILE_SYSTEM
                + ilParametroSistema.consultarValorParametroSistema(EnumParametrosSistema.PATH_REPORTES);
        String pathTemporalGuardaReporte = ConstantesGeneral.PATH_DOCUMENTOS_FILE_SYSTEM
                + ilParametroSistema.consultarValorParametroSistema(EnumParametrosSistema.PATH_TEMPORAL_GUARDA_REPORTE);
        // Crea la instancia del utilitario
        // Si se quiere generar solo el archivo jasper se genera sin una vista preliminar
        JasperUtilDTO jasperUtilDTO = new JasperUtilDTO();
        jasperUtilDTO.setSubreporte(jasperPlantillaDTO.isGrupo());
        jasperUtilDTO.setXmlDocumento(jasperPlantillaDTO.getContenidoJasper());
        jasperUtilDTO.setFormato(EnumFormatoDocumento.JASPER);
        jasperUtilDTO.setNombreDocumento(jasperPlantillaDTO.getNombreJasper());
        jasperUtilDTO.setPathDocumentos(ConstantesGeneral.PATH_DOCUMENTOS_FILE_SYSTEM);
        jasperUtilDTO.setPathImagenes(pathImagenes);
        jasperUtilDTO.setPathReportes(pathReportes);
        jasperUtilDTO.setPathTemporalGuardaReporte(pathTemporalGuardaReporte);
        JasperUtil jasperUtil = new JasperUtil(jasperUtilDTO);

        try {
            jasperUtil.generateDocument();
        } catch (Exception e1) {
            logger.error("PlantillaEJB::registrarJasperPlantilla problemas generando el documento. ", e1);
            throw new DocumentosRuntimeException("Error al guardar archivo jasper de la plantilla");
        }

        try {
            GeneraDocumentoDTO generaDocumentoDTO = new GeneraDocumentoDTO();
            generaDocumentoDTO.setFormato(EnumFormatoDocumento.JASPER);
            generaDocumentoDTO.setNombreDocumento(jasperPlantillaDTO.getNombreJasper());
            generaDocumentoDTO.setFechaGeneracion(new Date());
            generaDocumentoDTO.setContenido(Files.readAllBytes(Paths.get(jasperUtil.getPathArchivo())));
            generaDocumentoDTO.setCarpeta(ConstantesPlantillas.CARPETA_JASPER_PLANTILLAS
                    + jasperPlantillaDTO.getPlantillaDTO().getCodigoPlantilla() + "_"
                    + jasperPlantillaDTO.getPlantillaDTO().getVersionPlantilla());
            DocumentoDTO documento = ilDocumento.crearDocumento(generaDocumentoDTO);
            // Si viene nulo es porque ya existe el documento
            if (documento == null) {
                generaDocumentoDTO.setActualizar(true);
                // Actualizar documento
                documento = ilDocumento.crearDocumento(generaDocumentoDTO);
            }
            jasperPlantillaDTO.setCodigoDocumento(documento.getCodigoDocumento());
        } catch (IOException e) {
            throw new DocumentosRuntimeException("Error al guardar jasper");
        }

        JasperPlantilla jasperPlantilla = JasperPlantillaHelper.toJasperPlantilla(jasperPlantillaDTO, null);
        if (jasperPlantillaDTO.getPlantillaDTO() != null) {
            jasperPlantilla.setPlantilla(PlantillaHelper.toPlantilla(jasperPlantillaDTO.getPlantillaDTO(), null));
        }
        em.persist(jasperPlantilla);
        em.flush();
        jasperPlantillaDTO.setIdJasperPlantilla(jasperPlantilla.getIdJasperPlantilla());

        return jasperPlantillaDTO;
    }

    // FIN METODOS JASPER PLANTILLA

    @Override
    public PlantillaConfiguracionDTO consultarConfiguracionPlantilla(
            PlantillaConfiguracionDTO plantillaConfiguracionDTO, boolean proceso) {
        logger.debug("PlantillaEJB::consultarConfiguracionPlantilla(PlantillaDTO)");

        PlantillaConfiguracionDTO configuracion = null;
        Map<String, Object> parametros = new HashMap<>(5);
        GenericDao<PlantillaConfiguracion> dao = new GenericDao<>(PlantillaConfiguracion.class, em);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT pc FROM PlantillaConfiguracion pc ");
        sql.append("LEFT JOIN pc.configuracionesPlantilla ");
        sql.append("WHERE 1=1 ");
        if (plantillaConfiguracionDTO != null) {
            if (plantillaConfiguracionDTO.getPlantilla() != null) {
                if (!proceso) {
                    if (plantillaConfiguracionDTO.getPlantilla().getPlantillaOrigenDTO() != null) {
                        sql.append(
                                "AND (pc.plantilla.idPlantilla = :idPlantilla OR pc.plantilla.idPlantilla = :idPlantillaOrigen) ");
                        sql.append("AND pc.configuracionPadre IS NULL ");
                        parametros.put("idPlantillaOrigen",
                                plantillaConfiguracionDTO.getPlantilla().getPlantillaOrigenDTO().getIdPlantilla());
                    } else {
                        sql.append("AND pc.plantilla.idPlantilla = :idPlantilla AND pc.configuracionPadre IS NULL ");
                    }
                    parametros.put("idPlantilla", plantillaConfiguracionDTO.getPlantilla().getIdPlantilla());
                } else if (plantillaConfiguracionDTO.getPlantilla().getProcesoDTO() != null) {
                    sql.append("AND pc.plantilla.proceso.idProceso = :idProceso AND pc.configuracionPadre IS NULL ");
                    parametros.put("idProceso",
                            plantillaConfiguracionDTO.getPlantilla().getProcesoDTO().getIdProceso());

                }
            }
            if (plantillaConfiguracionDTO.getIdPlantillaConfig() != null) {
                sql.append("AND pc.idPlantillaConfig = :idPlantillaConfig ");
                parametros.put("idPlantillaConfig", plantillaConfiguracionDTO.getIdPlantillaConfig());
            }
            if (!proceso) {
                sql.append("ORDER BY pc.idPlantillaConfig DESC ");
            }
        }

        List<PlantillaConfiguracion> configuraciones = dao.buildAndExecuteQuery(sql.toString(), parametros);
        if (!configuraciones.isEmpty()) {
            PlantillaConfiguracion plantillaConfiguracion = configuraciones.get(0);
            configuracion = PlantillaConfiguracionHelper.completarPlantillaConfiguracion(plantillaConfiguracion);
            if (plantillaConfiguracion.getConfiguracionesPlantilla() != null) {
                for (PlantillaConfiguracion config : plantillaConfiguracion.getConfiguracionesPlantilla()) {
                    PlantillaConfiguracionDTO grupo = new PlantillaConfiguracionDTO();
                    grupo.setIdPlantillaConfig(config.getIdPlantillaConfig());
                    if (configuracion.getConfiguracionesPlantilla() == null) {
                        configuracion.setConfiguracionesPlantilla(new ArrayList<PlantillaConfiguracionDTO>());
                    }
                    configuracion.getConfiguracionesPlantilla().add(consultarConfiguracionPlantilla(grupo, false));
                }
            }
        }
        return configuracion;
    }
}
