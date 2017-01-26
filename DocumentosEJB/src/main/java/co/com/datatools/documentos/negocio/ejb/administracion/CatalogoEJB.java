package co.com.datatools.documentos.negocio.ejb.administracion;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import co.com.datatools.documentos.administracion.ContextoAplicacionVariableDTO;
import co.com.datatools.documentos.administracion.TipoDatoDTO;
import co.com.datatools.documentos.administracion.TipoFechaReferenciaDTO;
import co.com.datatools.documentos.administracion.TipoFirmaPlantillaDTO;
import co.com.datatools.documentos.administracion.TipoIdentificacionDTO;
import co.com.datatools.documentos.administracion.TipoVariableDTO;
import co.com.datatools.documentos.entidades.ContextoAplicacionVariable;
import co.com.datatools.documentos.entidades.TipoDato;
import co.com.datatools.documentos.entidades.TipoFechaReferencia;
import co.com.datatools.documentos.entidades.TipoFirmaPlantilla;
import co.com.datatools.documentos.entidades.TipoVariable;
import co.com.datatools.documentos.negocio.helper.ContextoAplicacionVariableHelper;
import co.com.datatools.documentos.negocio.helper.TipoDatoHelper;
import co.com.datatools.documentos.negocio.helper.TipoFechaReferenciaHelper;
import co.com.datatools.documentos.negocio.helper.TipoFirmaPlantillaHelper;
import co.com.datatools.documentos.negocio.helper.TipoVariableHelper;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILCatalogo;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRCatalogo;

@Stateless(name = "CatalogoEJB")
@LocalBean
public class CatalogoEJB implements ILCatalogo, IRCatalogo {

    private static final Logger logger = Logger.getLogger(CatalogoEJB.class.getName());

    @PersistenceContext(unitName = "DocumentosJPA")
    EntityManager em;

    public CatalogoEJB() {

    }

    @Override
    public List<ContextoAplicacionVariableDTO> consultarContextoAplicacionVariable() {
        logger.debug("CatalogoEJB::consultarContextoAplicacionVariable");
        List<ContextoAplicacionVariableDTO> lContextoAplicacionVariableDTOs = new ArrayList<ContextoAplicacionVariableDTO>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT cv FROM ContextoAplicacionVariable cv ");
        sql.append(" ORDER BY cv.nombreContextoAplicacion ASC");
        TypedQuery<ContextoAplicacionVariable> consulta = em.createQuery(sql.toString(),
                ContextoAplicacionVariable.class);
        List<ContextoAplicacionVariable> lContextoAplicacionVariables = consulta.getResultList();
        for (ContextoAplicacionVariable contextoAplicacionVariable : lContextoAplicacionVariables) {
            ContextoAplicacionVariableDTO contextoAplicacionVariableDTO = ContextoAplicacionVariableHelper
                    .toContextoAplicacionVariableDTO(contextoAplicacionVariable);
            lContextoAplicacionVariableDTOs.add(contextoAplicacionVariableDTO);
        }

        return lContextoAplicacionVariableDTOs;
    }

    @Override
    public ContextoAplicacionVariableDTO consultarContextAplicacionVariableId(int idContextoAplicacionVariable) {
        return null;
    }

    @Override
    public List<TipoVariableDTO> consultarTipoVariable() {
        logger.debug("CatalogoEJB::consultarTipoVariable");
        List<TipoVariableDTO> lTipoVariableDTOs = new ArrayList<TipoVariableDTO>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t FROM TipoVariable t ");
        sql.append(" ORDER BY t.nombreTipoVariable ASC");
        TypedQuery<TipoVariable> consulta = em.createQuery(sql.toString(), TipoVariable.class);
        List<TipoVariable> lTipoVariables = consulta.getResultList();
        for (TipoVariable tipoVariable : lTipoVariables) {
            TipoVariableDTO tipoVariableDTO = TipoVariableHelper.toTipoVariableDTO(tipoVariable);
            lTipoVariableDTOs.add(tipoVariableDTO);
        }

        return lTipoVariableDTOs;
    }

    @Override
    public List<TipoFirmaPlantillaDTO> consultarTipoFirmaPlantilla() {
        logger.debug("CatalogoEJB::consultarTipoFirmaPlantilla");
        List<TipoFirmaPlantillaDTO> lTipoFirmaPlantillaDTOs = new ArrayList<TipoFirmaPlantillaDTO>();

        TypedQuery<TipoFirmaPlantilla> consulta = em.createNamedQuery(TipoFirmaPlantilla.SQ_TIPOFIRMA_ALL,
                TipoFirmaPlantilla.class);
        List<TipoFirmaPlantilla> lTipoFirmaPlantillas = consulta.getResultList();
        for (TipoFirmaPlantilla tipoFirmaPlantilla : lTipoFirmaPlantillas) {
            TipoFirmaPlantillaDTO tipoFirmaPlantillaDTO = TipoFirmaPlantillaHelper
                    .toTipoFirmaPlantillaDTO(tipoFirmaPlantilla);
            lTipoFirmaPlantillaDTOs.add(tipoFirmaPlantillaDTO);
        }
        return lTipoFirmaPlantillaDTOs;
    }

    @Override
    public List<TipoFechaReferenciaDTO> consultarTipoFechaReferencia() {
        logger.debug("CatalogoEJB::consultarTipoFechaReferencia");
        List<TipoFechaReferenciaDTO> lTipoFechaReferenciaDTOs = new ArrayList<TipoFechaReferenciaDTO>();
        TypedQuery<TipoFechaReferencia> consulta = em.createNamedQuery(TipoFechaReferencia.SQ_TIPOFECHAREF_ALL,
                TipoFechaReferencia.class);
        List<TipoFechaReferencia> lTipoFechaReferencias = consulta.getResultList();
        for (TipoFechaReferencia tipoFechaReferencia : lTipoFechaReferencias) {
            TipoFechaReferenciaDTO tipoFechaReferenciaDTO = TipoFechaReferenciaHelper
                    .toTipoFechaReferenciaDTO(tipoFechaReferencia);
            lTipoFechaReferenciaDTOs.add(tipoFechaReferenciaDTO);
        }
        return lTipoFechaReferenciaDTOs;
    }

    @Override
    public List<TipoDatoDTO> consultarTipoDato() {
        logger.debug("CatalogoEJB::consultarTipoDato");
        List<TipoDatoDTO> lTipoDatoDTOs = new ArrayList<TipoDatoDTO>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t FROM TipoDato t ");
        sql.append(" ORDER BY t.nombreTipoDato ASC");
        TypedQuery<TipoDato> consulta = em.createQuery(sql.toString(), TipoDato.class);
        List<TipoDato> lTipoDatos = consulta.getResultList();
        for (TipoDato tipoVariable : lTipoDatos) {
            TipoDatoDTO tipoVariableDTO = TipoDatoHelper.toTipoDatoDTO(tipoVariable);
            lTipoDatoDTOs.add(tipoVariableDTO);
        }

        return lTipoDatoDTOs;
    }

    @Override
    public List<TipoIdentificacionDTO> consultarTipoIdentificacion() {
        logger.debug("CatalogoEJB::consultarTipoIdentificacion");
        List<TipoIdentificacionDTO> lTipoIdentificacionDTOs = new ArrayList<TipoIdentificacionDTO>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT f.siglaTipoIdentificacion, f.nombreTipoIdentificacion FROM Funcionario f ");
        sql.append(" WHERE 1 = 1 ");
        sql.append(" AND f.siglaTipoIdentificacion <> ''");
        sql.append(" AND f.siglaTipoIdentificacion IS NOT NULL");
        sql.append(" ORDER BY f.siglaTipoIdentificacion ASC");
        TypedQuery<Object[]> consulta = em.createQuery(sql.toString(), Object[].class);
        List<Object[]> resultList = consulta.getResultList();
        for (Object[] result : resultList) {     
            String siglaTipo = (String)result[0];  
            String nombreTipo = (String)result[1];
            if(StringUtils.isNotBlank(siglaTipo) && StringUtils.isNotBlank(nombreTipo)) {
                TipoIdentificacionDTO tipo = new TipoIdentificacionDTO();
                tipo.setSiglaTipoIdentificacion(siglaTipo);
                tipo.setNombreTipoIdentificacion(nombreTipo);
                lTipoIdentificacionDTOs.add(tipo);                
            }
        } 

        return lTipoIdentificacionDTOs;
    }

}
