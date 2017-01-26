package co.com.datatools.documentos.negocio.ejb.documentos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import co.com.datatools.documentos.administracion.ContextoAplicacionVariableDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.TipoVariableDTO;
import co.com.datatools.documentos.constantes.ConstantesPlantillas;
import co.com.datatools.documentos.entidades.ContextoAplicacionVariable;
import co.com.datatools.documentos.entidades.Plantilla;
import co.com.datatools.documentos.entidades.Proceso;
import co.com.datatools.documentos.entidades.TipoVariable;
import co.com.datatools.documentos.entidades.Variable;
import co.com.datatools.documentos.negocio.helper.ContextoAplicacionVariableHelper;
import co.com.datatools.documentos.negocio.helper.ProcesoHelper;
import co.com.datatools.documentos.negocio.helper.TipoVariableHelper;
import co.com.datatools.documentos.negocio.helper.VariableHelper;
import co.com.datatools.documentos.negocio.interfaces.documentos.ILVariable;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRVariable;
import co.com.datatools.documentos.plantillas.PlantillaDTO;
import co.com.datatools.documentos.plantillas.VariableDTO;

/**
 * Session Bean implementation class VariableEJB
 */
@Stateless(name = "VariableEJB")
@LocalBean
public class VariableEJB implements ILVariable, IRVariable {

    private static final Logger logger = Logger.getLogger(VariableEJB.class.getName());

    @PersistenceContext(unitName = "DocumentosJPA")
    EntityManager em;

    @EJB
    private ILVariable ilVarible;

    // INICIO METODOS VARIABLE

    @Override
    public List<VariableDTO> consultarVariableOrganizacion() {
        logger.debug("PlantillaEJB::consultarVariableOrganizacion");
        List<VariableDTO> lVariableDTOs = new ArrayList<VariableDTO>();
        TypedQuery<Variable> consulta = em.createNamedQuery(Variable.SQ_VARIABLES_POR_ORGANIZACION, Variable.class);
        consulta.setParameter("idContextoAplicacion", ConstantesPlantillas.ID_CONTEXTO_APLICACION_VARIABLE_ORGANIZACION);
        List<Variable> lVariables = consulta.getResultList();
        for (Variable variable : lVariables) {
            VariableDTO variableDTO = VariableHelper.toVariableDTO(variable);
            if (variable.getTipoVariable() != null) {
                TipoVariableDTO tipoVariableDTO = TipoVariableHelper.toTipoVariableDTO(variable.getTipoVariable());
                variableDTO.setTipoVariableDTO(tipoVariableDTO);
            }
            if (variable.getProceso() != null) {
                ProcesoDTO procDto = ProcesoHelper.toProcesoDTO(variable.getProceso());
                variableDTO.setProcesoDTO(procDto);
            }
            if (variable.getContextoAplicacionVariable() != null) {
                ContextoAplicacionVariableDTO contextoAplicacionVariableDTO = ContextoAplicacionVariableHelper
                        .toContextoAplicacionVariableDTO(variable.getContextoAplicacionVariable());
                variableDTO.setContextoAplicacionVariableDTO(contextoAplicacionVariableDTO);
            }
            lVariableDTOs.add(variableDTO);
        }

        return lVariableDTOs;
    }

    @Override
    public List<VariableDTO> consultarVariablePlantilla(PlantillaDTO plantillaDTO) {
        logger.debug("PlantillaEJB::consultarVariablePlantilla");
        List<VariableDTO> lVariableDTOs = new ArrayList<VariableDTO>();
        TypedQuery<Variable> consulta = em.createNamedQuery(Variable.SQ_VARIABLES_POR_PLANTILLA, Variable.class);
        consulta.setParameter("idPlantilla", plantillaDTO.getIdPlantilla());
        List<Variable> lVariables = consulta.getResultList();
        for (Variable variable : lVariables) {
            VariableDTO variableDTO = VariableHelper.toVariableDTO(variable);
            if (variable.getTipoVariable() != null) {
                TipoVariableDTO tipoVariableDTO = TipoVariableHelper.toTipoVariableDTO(variable.getTipoVariable());
                variableDTO.setTipoVariableDTO(tipoVariableDTO);
            }
            if (variable.getProceso() != null) {
                ProcesoDTO procDto = ProcesoHelper.toProcesoDTO(variable.getProceso());
                variableDTO.setProcesoDTO(procDto);
            }
            if (variable.getContextoAplicacionVariable() != null) {
                ContextoAplicacionVariableDTO contextoAplicacionVariableDTO = ContextoAplicacionVariableHelper
                        .toContextoAplicacionVariableDTO(variable.getContextoAplicacionVariable());
                variableDTO.setContextoAplicacionVariableDTO(contextoAplicacionVariableDTO);
            }
            lVariableDTOs.add(variableDTO);
        }
        return lVariableDTOs;
    }

    @Override
    public List<VariableDTO> consultarVariableProceso(ProcesoDTO procesoDTO) {
        logger.debug("PlantillaEJB::consultarVariableProceso");
        List<VariableDTO> lVariableDTOs = new ArrayList<VariableDTO>();
        TypedQuery<Variable> consulta = em.createNamedQuery(Variable.SQ_VARIABLES_POR_PROCESO, Variable.class);
        consulta.setParameter("idProceso", procesoDTO.getIdProceso());
        List<Variable> lVariables = consulta.getResultList();
        for (Variable variable : lVariables) {
            VariableDTO variableDTO = VariableHelper.toVariableDTO(variable);
            if (variable.getTipoVariable() != null) {
                TipoVariableDTO tipoVariableDTO = TipoVariableHelper.toTipoVariableDTO(variable.getTipoVariable());
                variableDTO.setTipoVariableDTO(tipoVariableDTO);
            }
            if (variable.getProceso() != null) {
                ProcesoDTO procDto = ProcesoHelper.toProcesoDTO(variable.getProceso());
                variableDTO.setProcesoDTO(procDto);
            }
            if (variable.getContextoAplicacionVariable() != null) {
                ContextoAplicacionVariableDTO contextoAplicacionVariableDTO = ContextoAplicacionVariableHelper
                        .toContextoAplicacionVariableDTO(variable.getContextoAplicacionVariable());
                variableDTO.setContextoAplicacionVariableDTO(contextoAplicacionVariableDTO);
            }
            lVariableDTOs.add(variableDTO);
        }

        return lVariableDTOs;
    }

    @Override
    public VariableDTO registrarVariable(VariableDTO variableDTO) {
        logger.debug("PlantillaEJB::registrarVariable");
        variableDTO.setFechaCreacion(new Date());
        Variable variable = VariableHelper.toVariable(variableDTO, new Variable());
        if (variableDTO.getProcesoDTO() != null) {
            Proceso proceso = ProcesoHelper.toProceso(variableDTO.getProcesoDTO(), null);
            variable.setProceso(proceso);
        }
        if (variableDTO.getContextoAplicacionVariableDTO() != null) {
            ContextoAplicacionVariable contextoAplicacionVariable = ContextoAplicacionVariableHelper
                    .toContextoAplicacionVariable(variableDTO.getContextoAplicacionVariableDTO(), null);
            variable.setContextoAplicacionVariable(contextoAplicacionVariable);
        }
        if (variableDTO.getTipoVariableDTO() != null) {
            TipoVariable tipoVariable = TipoVariableHelper.toTipoVariable(variableDTO.getTipoVariableDTO(), null);
            variable.setTipoVariable(tipoVariable);
        }
        em.persist(variable);
        em.flush();
        variableDTO.setIdVariable(variable.getIdVariable());

        return variableDTO;
    }

    @Override
    public boolean validarExistenciaVariable(VariableDTO variableDTO) {
        logger.debug("PlantillaEJB::validarExistenciaVariable");

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT v FROM Variable v");
        sql.append(" LEFT JOIN v.plantillas p");
        sql.append(" WHERE 1=1");
        if (StringUtils.isNotBlank(variableDTO.getNombreVariable())) {
            sql.append(" AND TRIM(UPPER(v.nombreVariable)) = :nombreVariable");
        }
        TypedQuery<Variable> consulta = em.createQuery(sql.toString(), Variable.class);

        if (StringUtils.isNotBlank(variableDTO.getNombreVariable())) {
            consulta.setParameter("nombreVariable", variableDTO.getNombreVariable().toUpperCase().trim());
        }
        List<Variable> resultado = consulta.getResultList();
        for (Variable variable : resultado) {
            // Si es de visibilidad de la organizacion devuelve verdadero
            // Si es de visibilidad del proceso se valida el proceso asociado
            // Si es de visibilidad de la plantilla se valida la plantilla asociada
            if (variable.getContextoAplicacionVariable() != null
                    && variable.getContextoAplicacionVariable().getIdContextoAplicacion() == ConstantesPlantillas.ID_CONTEXTO_APLICACION_VARIABLE_ORGANIZACION) {
                return true;
            } else if (variable.getContextoAplicacionVariable() != null
                    && variable.getContextoAplicacionVariable().getIdContextoAplicacion() == ConstantesPlantillas.ID_CONTEXTO_APLICACION_VARIABLE_PROCESO) {
                if (variableDTO.getProcesoDTO() != null && variableDTO.getProcesoDTO().getIdProceso() != 0
                        && variable.getProceso() != null
                        && variableDTO.getProcesoDTO().getIdProceso() == variable.getProceso().getIdProceso()) {
                    return true;
                }
            } else if (variable.getContextoAplicacionVariable() != null
                    && variable.getContextoAplicacionVariable().getIdContextoAplicacion() == ConstantesPlantillas.ID_CONTEXTO_APLICACION_VARIABLE_PLANTILLA) {
                if (variableDTO.getPlantillas() != null && !variableDTO.getPlantillas().isEmpty()
                        && variable.getPlantillas() != null && !variable.getPlantillas().isEmpty()) {
                    for (Plantilla plantilla : variable.getPlantillas()) {
                        if (variableDTO.getPlantillas().get(0).getIdPlantilla() != 0
                                && variableDTO.getPlantillas().get(0).getIdPlantilla() == plantilla.getIdPlantilla()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void actualizarVariable(VariableDTO variableDTO) {
        // TODO Agregar implementacion del metodo

    }

    @Override
    public VariableDTO consultarVariableId(int idVariable) {
        VariableDTO variableDTO = null;

        Variable variable = em.find(Variable.class, idVariable);
        if (variable != null) {
            variableDTO = VariableHelper.toVariableDTO(variable);
        }
        return variableDTO;
    }

    // FIN METODOS VARIABLE

}
