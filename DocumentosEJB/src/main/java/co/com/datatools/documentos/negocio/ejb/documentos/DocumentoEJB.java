package co.com.datatools.documentos.negocio.ejb.documentos;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.jboss.logging.Logger;

import com.lowagie.text.pdf.codec.Base64;

import co.com.datatools.documentos.administracion.ConsultaCargoFuncionarioDTO;
import co.com.datatools.documentos.administracion.FuncionarioCargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.cm.interfaces.IRGestorArchivos;
import co.com.datatools.documentos.constantes.ConstantesDocumentos;
import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.constantes.ConstantesPlantillas;
import co.com.datatools.documentos.documento.DocumentoUtil;
import co.com.datatools.documentos.documentos.DocumentoDTO;
import co.com.datatools.documentos.documentos.FirmaDigitalDocumentoDTO;
import co.com.datatools.documentos.documentos.GeneraDocumentoDTO;
import co.com.datatools.documentos.documentos.XmlDocumentoDTO;
import co.com.datatools.documentos.entidades.Documento;
import co.com.datatools.documentos.entidades.FirmaDigitalDocumento;
import co.com.datatools.documentos.entidades.Funcionario;
import co.com.datatools.documentos.entidades.Plantilla;
import co.com.datatools.documentos.entidades.UsuarioOrganizacion;
import co.com.datatools.documentos.entidades.XmlDocumento;
import co.com.datatools.documentos.enumeraciones.EnumFormatoDocumento;
import co.com.datatools.documentos.enumeraciones.EnumParametrosSistema;
import co.com.datatools.documentos.excepcion.DocumentosException;
import co.com.datatools.documentos.ftp.Ftp;
import co.com.datatools.documentos.jasper.JasperUtil;
import co.com.datatools.documentos.jasper.JasperUtilDTO;
import co.com.datatools.documentos.negocio.error.ErrorDocumentos;
import co.com.datatools.documentos.negocio.helper.DocumentoHelper;
import co.com.datatools.documentos.negocio.helper.FirmaDigitalDocumentoHelper;
import co.com.datatools.documentos.negocio.helper.FuncionarioHelper;
import co.com.datatools.documentos.negocio.helper.PlantillaHelper;
import co.com.datatools.documentos.negocio.helper.UsuarioOrganizacionHelper;
import co.com.datatools.documentos.negocio.helper.XmlDocumentoHelper;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILFuncionario;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILParametroSistema;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILProceso;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILUsuarioOrganizacion;
import co.com.datatools.documentos.negocio.interfaces.documentos.ILDocumento;
import co.com.datatools.documentos.negocio.interfaces.documentos.ILFirmaDigitalDocumento;
import co.com.datatools.documentos.negocio.interfaces.documentos.ILFirmaPlantilla;
import co.com.datatools.documentos.negocio.interfaces.documentos.ILJasperPlantilla;
import co.com.datatools.documentos.negocio.interfaces.documentos.ILPlantilla;
import co.com.datatools.documentos.negocio.interfaces.documentos.ILVariable;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRDocumento;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRFirmaDigitalDocumento;
import co.com.datatools.documentos.plantillas.FirmaPlantillaDTO;
import co.com.datatools.documentos.plantillas.JasperPlantillaDTO;
import co.com.datatools.documentos.plantillas.PlantillaDTO;
import co.com.datatools.documentos.plantillas.VariableDTO;
import net.sf.jasperreports.engine.JRException;

@Stateless(name = "DocumentoEJB")
@LocalBean
public class DocumentoEJB implements ILDocumento, IRDocumento, IRFirmaDigitalDocumento, ILFirmaDigitalDocumento {

    private static final Logger LOGGER = Logger.getLogger(DocumentoEJB.class.getName());

    @PersistenceContext(unitName = "DocumentosJPA")
    EntityManager em;

    private static final String NOMBRE_FUNCIONARIO = "NOMBRE_FUNCIONARIO_";
    private static final String CARGO_FUNCIONARIO = "CARGO_FUNCIONARIO_";
    private static final String FIRMA_FUNCIONARIO = "FIRMA_FUNCIONARIO_";

    // @EJB
    // private ILGestorArchivos ilGestorArchivos;

    @EJB
    private ILPlantilla ilPlantilla;

    @EJB
    private ILFirmaPlantilla ilFirmaPlantilla;

    @EJB
    private ILVariable ilVariable;

    @EJB
    private ILJasperPlantilla ilJasperPlantilla;

    @EJB
    private ILFuncionario ilFuncionario;

    @EJB
    private ILParametroSistema ilParametroSistema;

    @EJB
    private ILProceso ilProceso;

    @EJB
    private ILUsuarioOrganizacion ilUsuarioOrganizacion;

    private IRGestorArchivos iGestorArchivos;

    public DocumentoEJB() {
        try {
            final Hashtable<String, String> props = new Hashtable<String, String>();
            // setup the ejb: namespace URL factory
            props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            // create the InitialContext
            final Context context = new javax.naming.InitialContext(props);
            // lookup the bean

            iGestorArchivos = (IRGestorArchivos) context.lookup(
                    "ejb:GestorArchivosEAR-2.0.4/GestorArchivosSqlEJB-2.0.4/GestorArchivosSqlEJB!co.com.datatools.documentos.cm.interfaces.IRGestorArchivos");
        } catch (NamingException e) {
            LOGGER.error("Error localizando Jndi EJB", e);
        }

    }

    @Override
    public List<DocumentoDTO> consultarDocumento(DocumentoDTO documentoDTO) {
        LOGGER.debug("DocumentoEJB::consultarDocumento");
        List<DocumentoDTO> lDocumentoDTOs = new ArrayList<DocumentoDTO>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT d FROM Documento d ");
        sql.append("WHERE 1 = 1 ");
        if (documentoDTO.getCodigoDocumento() != null && !documentoDTO.getCodigoDocumento().trim().isEmpty()) {
            sql.append(" AND UPPER(TRIM(d.codigoDocumento)) = :codigo");
        }
        if (documentoDTO.getConsecutivoDocumento() != null) {
            sql.append(" AND d.consecutivoDocumento = :consecutivo");
        }
        if (documentoDTO.getVersionDocumento() > 0) {
            sql.append(" AND d.versionDocumento = :version");
        }
        if (documentoDTO.getDescripcionDocumento() != null
                && !documentoDTO.getDescripcionDocumento().trim().isEmpty()) {
            sql.append(" AND UPPER(TRIM(d.descripcionDocumento)) LIKE :descrpDocumento");
        }
        if (documentoDTO.getFechaCreacionDesde() != null) {
            sql.append(" AND d.fechaCreacion >= :fechaCreacionDesde");
        }
        if (documentoDTO.getFechaCreacionHasta() != null) {
            sql.append(" AND d.fechaCreacion <= :fechaCreacionHasta");
        }
        sql.append(" ORDER BY d.fechaGeneracion ASC");
        TypedQuery<Documento> consulta = em.createQuery(sql.toString(), Documento.class);
        if (documentoDTO.getCodigoDocumento() != null && !documentoDTO.getCodigoDocumento().isEmpty()) {
            consulta.setParameter("codigo", documentoDTO.getCodigoDocumento().toLowerCase().trim());
        }
        if (documentoDTO.getConsecutivoDocumento() != null) {
            consulta.setParameter("consecutivo", documentoDTO.getConsecutivoDocumento());
        }
        if (documentoDTO.getVersionDocumento() > 0) {
            consulta.setParameter("version", documentoDTO.getVersionDocumento());
        }
        if (documentoDTO.getDescripcionDocumento() != null
                && !documentoDTO.getDescripcionDocumento().trim().isEmpty()) {
            consulta.setParameter("descrpDocumento",
                    "%" + documentoDTO.getDescripcionDocumento().toUpperCase().trim() + "%");
        }

        if (documentoDTO.getFechaCreacionDesde() != null) {
            Calendar fechaFinal = Calendar.getInstance();
            fechaFinal.setTime(documentoDTO.getFechaCreacionDesde());
            fechaFinal.set(Calendar.HOUR, 0);
            fechaFinal.set(Calendar.MINUTE, 0);
            fechaFinal.set(Calendar.SECOND, 0);
            consulta.setParameter("fechaCreacionDesde", fechaFinal.getTime());
        }
        if (documentoDTO.getFechaCreacionHasta() != null) {
            Calendar fechaFinal = Calendar.getInstance();
            fechaFinal.setTime(documentoDTO.getFechaCreacionHasta());
            fechaFinal.set(Calendar.HOUR, 23);
            fechaFinal.set(Calendar.MINUTE, 59);
            fechaFinal.set(Calendar.SECOND, 59);
            consulta.setParameter("fechaCreacionHasta", fechaFinal.getTime());
        }

        List<Documento> lDocumentos = consulta.getResultList();
        for (Documento documento : lDocumentos) {
            DocumentoDTO doDto = DocumentoHelper.toDocumentoDTO(documento);
            if (documento.getDocumento() != null) {
                DocumentoDTO documentoOrigen = DocumentoHelper.toDocumentoDTO(documento.getDocumento());
                doDto.setDocumentoOrigenDTO(documentoOrigen);
            }
            if (documento.getPlantilla() != null) {
                PlantillaDTO plantillaDTO = PlantillaHelper.toPlantillaDTO(documento.getPlantilla());
                doDto.setPlantillaDTO(plantillaDTO);
            }
            if (documento.getXmlDocumento() != null) {
                XmlDocumentoDTO xmlDocumentoDTO = XmlDocumentoHelper.toXmlDocumentoDTO(documento.getXmlDocumento());
                doDto.setXmlDocumentoDTO(xmlDocumentoDTO);
            }
            if (documento.getUsuarioOrganizacion() != null) {
                UsuarioOrganizacionDTO usuarioOrganizacionDTO = UsuarioOrganizacionHelper
                        .toUsuarioOrganizacionDTO(documento.getUsuarioOrganizacion());
                documentoDTO.setUsuarioOrganizacionDTO(usuarioOrganizacionDTO);
            }
            lDocumentoDTOs.add(doDto);
        }

        return lDocumentoDTOs;
    }

    @Override
    public DocumentoDTO consultarDocumentoId(long idDocumento) {
        LOGGER.debug("DocumentoEJB::consultarDocumentoId");
        DocumentoDTO documentoDTO = new DocumentoDTO();
        Documento documento = em.find(Documento.class, idDocumento);
        documentoDTO = DocumentoHelper.toDocumentoDTO(documento);
        if (documento.getDocumento() != null) {
            DocumentoDTO documentoOrigen = DocumentoHelper.toDocumentoDTO(documento.getDocumento());
            documentoDTO.setDocumentoOrigenDTO(documentoOrigen);
        }
        if (documento.getPlantilla() != null) {
            PlantillaDTO plantillaDTO = PlantillaHelper.toPlantillaDTO(documento.getPlantilla());
            documentoDTO.setPlantillaDTO(plantillaDTO);
        }
        if (documento.getXmlDocumento() != null) {
            XmlDocumentoDTO xmlDocumentoDTO = XmlDocumentoHelper.toXmlDocumentoDTO(documento.getXmlDocumento());
            documentoDTO.setXmlDocumentoDTO(xmlDocumentoDTO);
        }
        if (documento.getUsuarioOrganizacion() != null) {
            UsuarioOrganizacionDTO usuarioOrganizacionDTO = UsuarioOrganizacionHelper
                    .toUsuarioOrganizacionDTO(documento.getUsuarioOrganizacion());
            documentoDTO.setUsuarioOrganizacionDTO(usuarioOrganizacionDTO);
        }

        return documentoDTO;
    }

    @Override
    public DocumentoDTO registrarDocumento(DocumentoDTO documentoDTO) {
        LOGGER.debug("DocumentoEJB::registrarDocumento");
        Documento documento = DocumentoHelper.toDocumento(documentoDTO, new Documento());

        if (documentoDTO.getPlantillaDTO() != null) {
            Plantilla plantilla = PlantillaHelper.toPlantilla(documentoDTO.getPlantillaDTO(), new Plantilla());
            documento.setPlantilla(plantilla);
        }
        if (documentoDTO.getUsuarioOrganizacionDTO() != null) {
            UsuarioOrganizacion usuarioOrganizacion = UsuarioOrganizacionHelper
                    .toUsuarioOrganizacion(documentoDTO.getUsuarioOrganizacionDTO(), new UsuarioOrganizacion());
            documento.setUsuarioOrganizacion(usuarioOrganizacion);
        }
        if (documentoDTO.getDocumentoOrigenDTO() != null) {
            Documento documentoPadre = DocumentoHelper.toDocumento((documentoDTO.getDocumentoOrigenDTO()),
                    new Documento());
            documento.setDocumento(documentoPadre);
        }

        em.persist(documento);
        em.flush();
        documentoDTO.setIdDocumento(documento.getIdDocumento());

        if (documentoDTO.getXmlDocumentoDTO() != null) {
            documentoDTO.getXmlDocumentoDTO().setIdXmlDocumento(documento.getIdDocumento());
            documentoDTO.getXmlDocumentoDTO().setDocumentoDTO(documentoDTO);
            documentoDTO.setXmlDocumentoDTO(registrarXmlDocumento(documentoDTO.getXmlDocumentoDTO()));
        }

        return documentoDTO;
    }

    @Override
    public DocumentoDTO consultarUltimaVersionDocumento(DocumentoDTO documentoDTO) {
        LOGGER.debug("DocumentoEJB::consultarUltimaVersionDocumento");
        DocumentoDTO ultimaVersionDocumento = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT d FROM Documento d");
        sql.append(" WHERE d.versionDocumento = ");
        sql.append("(SELECT MAX(doc.versionDocumento) FROM Documento doc");
        sql.append(" WHERE doc.consecutivoDocumento = :consecutivoDocumento)");
        sql.append(" AND d.consecutivoDocumento = :consecutivoDocumento");
        TypedQuery<Documento> consulta = em.createQuery(sql.toString(), Documento.class);
        consulta.setParameter("consecutivoDocumento", documentoDTO.getConsecutivoDocumento());
        try {
            Documento documento = consulta.getSingleResult();
            ultimaVersionDocumento = DocumentoHelper.toDocumentoDTO(documento);
            if (documento.getDocumento() != null) {
                ultimaVersionDocumento
                        .setDocumentoOrigenDTO(consultarDocumentoId(documento.getDocumento().getIdDocumento()));
            }
            if (documento.getPlantilla() != null) {
                ultimaVersionDocumento
                        .setPlantillaDTO(ilPlantilla.consultarPlantillaId(documento.getPlantilla().getIdPlantilla()));
            }
            if (documento.getXmlDocumento() != null) {
                XmlDocumentoDTO xmlDocumentoDTO = XmlDocumentoHelper.toXmlDocumentoDTO(documento.getXmlDocumento());
                ultimaVersionDocumento.setXmlDocumentoDTO(xmlDocumentoDTO);
            }
            if (documento.getUsuarioOrganizacion() != null) {
                UsuarioOrganizacionDTO usuarioOrganizacionDTO = UsuarioOrganizacionHelper
                        .toUsuarioOrganizacionDTO(documento.getUsuarioOrganizacion());
                ultimaVersionDocumento.setUsuarioOrganizacionDTO(usuarioOrganizacionDTO);
            }
        } catch (NoResultException e) {
            LOGGER.info("No encontro ultima version del documento");
        }
        return ultimaVersionDocumento;

    }

    // INICIO METODOS IFIRMADIGITALDOCUMENTO

    @Override
    public List<FirmaDigitalDocumentoDTO> consultarFirmaDigitalDocumento(DocumentoDTO documentoDTO) {
        return null;
    }

    @Override
    public FirmaDigitalDocumentoDTO registrarFirmaDigitalDocumento(FirmaDigitalDocumentoDTO firmaDigitalDocumentoDTO) {
        LOGGER.debug("DocumentoEJB::registrarFirmaDigitalDocumento");

        FirmaDigitalDocumento firmaDigitalDocumento = FirmaDigitalDocumentoHelper
                .toFirmaDigitalDocumento(firmaDigitalDocumentoDTO, new FirmaDigitalDocumento());
        if (firmaDigitalDocumentoDTO.getFuncionarioDTO() != null
                && firmaDigitalDocumentoDTO.getFuncionarioDTO().getIdFuncionario() != 0) {
            Funcionario funcionario = FuncionarioHelper.toFuncionario(firmaDigitalDocumentoDTO.getFuncionarioDTO(),
                    null);
            firmaDigitalDocumento.setFuncionario(funcionario);
        }
        if (firmaDigitalDocumentoDTO.getDocumentoDTO() != null) {
            Documento documento = DocumentoHelper.toDocumento(firmaDigitalDocumentoDTO.getDocumentoDTO(), null);
            firmaDigitalDocumento.setDocumento(documento);
        }
        em.persist(firmaDigitalDocumento);
        em.flush();
        firmaDigitalDocumentoDTO.setIdFirmaDigitalDocumento(firmaDigitalDocumento.getIdFirmaDigitalDocumento());

        return firmaDigitalDocumentoDTO;
    }

    // FIN METODOS IFIRMADIGITALDOCUMENTO

    @Override
    public DocumentoDTO generarDocumento(GeneraDocumentoDTO generaDocumentoDTO) throws DocumentosException {
        LOGGER.debug("DocumentoEJB::generarDocumento");
        DocumentoDTO documentoDTO = null;

        try {
            if (!generaDocumentoDTO.isActualizar()) {
                // Consulta la plantilla utilizando el codigo
                PlantillaDTO plantillaDTOTemp = new PlantillaDTO();
                plantillaDTOTemp.setCodigoPlantilla(generaDocumentoDTO.getCodigoPlantilla());

                List<PlantillaDTO> plantillas = ilPlantilla.consultarPlantilla(plantillaDTOTemp,
                        generaDocumentoDTO.getFechaGeneracion());
                // Verifica que existan plantillas que coincidan
                if (!plantillas.isEmpty()) {
                    if (generaDocumentoDTO.getData() == null) {
                        generaDocumentoDTO.setData(new HashMap<String, Object>());
                    }
                    // Verifica el usuario que esta generando el documento
                    UsuarioOrganizacionDTO usuario = ilUsuarioOrganizacion
                            .consultarUsuarioOrganizacion(generaDocumentoDTO.getUsuario(), true);
                    if (usuario == null) {
                        LOGGER.info("No se encontro el usuario : " + generaDocumentoDTO.getUsuario());
                        throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001002);
                    }

                    for (PlantillaDTO plantillaDTO : plantillas) {

                        LOGGER.info("DocumentoEJB::generarDocumento" + usuario);
                        // Genera objeto jasper para la generacion
                        JasperUtilDTO jasperUtilDTO = generarJasperUtilDTO(generaDocumentoDTO, plantillaDTO, usuario);

                        XmlDocumentoDTO xmlDocumentoDTO = new XmlDocumentoDTO();
                        xmlDocumentoDTO.setParametrosDocumento(generaDocumentoDTO.getData().toString());

                        // Obtiene el consecutivo de documento
                        // En caso de concurrencia
                        long consecutivoDocumento = DocumentoUtil.obtenerConsecutivoDocumento(cargarConsecutivo());
                        jasperUtilDTO.setNombreDocumento(consecutivoDocumento + "_v1");

                        // Crea la instancia del utilitario de jasper
                        JasperUtil jasperUtil = new JasperUtil(jasperUtilDTO);

                        // Genera documento en formato deseado utilizando jasperreports
                        jasperUtil.exportDocument();

                        // Generado exitosamente
                        generaDocumentoDTO.setContenido(Files.readAllBytes(Paths.get(jasperUtil.getPathArchivo())));
                        generaDocumentoDTO.setNombreDocumento(consecutivoDocumento + "");
                        // Se guarda en el repositorio
                        documentoDTO = crearDocumento(generaDocumentoDTO);
                        if (documentoDTO != null) {
                            // Asigna datos para guardar el documento en la BD
                            documentoDTO.setPathDocumento(jasperUtil.getPathArchivo());
                            documentoDTO.setDescripcionDocumento(generaDocumentoDTO.getDescripcionDocumento());
                            documentoDTO.setFechaGeneracion(generaDocumentoDTO.getFechaGeneracion());
                            documentoDTO.setFechaCreacion(new Date());
                            documentoDTO.setPlantillaDTO(plantillaDTO);
                            documentoDTO.setUsuarioOrganizacionDTO(usuario);
                            documentoDTO.setConsecutivoDocumento(consecutivoDocumento);
                            documentoDTO.setVersionDocumento(1);
                            documentoDTO.setNombreDocumento(jasperUtilDTO.getNombreDocumento()
                                    + generaDocumentoDTO.getFormato().getExtension());

                            // TODO: Genera html que posteriormente servirá para la edicion del documento
                            // String htmlDocumento = HtmlUtil.generaHtmlDocumento(new String(plantillaDTO
                            // .getXmlPlantillaDTO().getContenidoXmlPlantilla(), StandardCharsets.UTF_8),
                            // jasperUtilDTO.getJasperDatasourceList().get(0), jasperUtilDTO.getPathImagenes());
                            //
                            // // Para guardar en BD
                            // xmlDocumentoDTO.setCodigoDocumentoHtml("-");
                            // xmlDocumentoDTO.setContenidoXml(htmlDocumento.getBytes());

                            xmlDocumentoDTO.setContenidoXml("".getBytes());
                            xmlDocumentoDTO.setNombreXmlDoc(jasperUtilDTO.getNombreDocumento());
                            documentoDTO.setXmlDocumentoDTO(xmlDocumentoDTO);
                            documentoDTO = registrarDocumento(documentoDTO);
                            documentoDTO.setXmlDocumentoDTO(null);
                        } else {
                            LOGGER.info("DocumentoEJB::generarDocumento No se pudo guardar en el repositorio");
                            throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001003);
                        }

                        // Solo toma la primera plantilla encontrada
                        break;
                    }
                } else {
                    LOGGER.info("No se encontro la plantilla asociada al codigo: "
                            + generaDocumentoDTO.getCodigoPlantilla());
                    throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001005);
                }
            } else {
                // Verifica el usuario que esta generando el documento
                UsuarioOrganizacionDTO usuario = ilUsuarioOrganizacion
                        .consultarUsuarioOrganizacion(generaDocumentoDTO.getUsuario(), false);
                if (usuario == null) {
                    LOGGER.info("No se encontro el usuario : " + generaDocumentoDTO.getUsuario());
                    throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001002);
                }

                // Obtiene la ultima version del documento
                DocumentoDTO documentoDTOTemp = new DocumentoDTO();
                documentoDTOTemp.setConsecutivoDocumento(generaDocumentoDTO.getCodigoDocumento());
                DocumentoDTO ultimaVersionDocumentoDTO = consultarUltimaVersionDocumento(documentoDTOTemp);
                if (ultimaVersionDocumentoDTO == null) {
                    LOGGER.info("No se encontro el documento asociado al codigo : "
                            + generaDocumentoDTO.getCodigoDocumento());
                    throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001018);
                }

                // Validamos si el documento ya se encuentra firmado digitalmente
                if (ultimaVersionDocumentoDTO.isFirmado()) {
                    // Validamos el parametro que permite editar documentos firmados
                    String permiteEditarFirmado = ilParametroSistema.consultarValorParametroSistema(
                            EnumParametrosSistema.PERMITIR_EDITAR_DOCUMENTOS_FIRMADOS_DIGITALMENTE);
                    if (permiteEditarFirmado.equalsIgnoreCase(ConstantesGeneral.VALOR_NO)) {
                        LOGGER.info("El documento está firmado digitalmente y NO es posible editarlo id: "
                                + ultimaVersionDocumentoDTO.getIdDocumento());
                        throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001019);
                    }
                }
                if (generaDocumentoDTO.getData() == null) {
                    generaDocumentoDTO.setData(new HashMap<String, Object>());
                }
                generaDocumentoDTO.setFormato(EnumFormatoDocumento.valueOf(
                        FilenameUtils.getExtension(ultimaVersionDocumentoDTO.getNombreDocumento().toUpperCase())));
                generaDocumentoDTO.setFechaGeneracion(ultimaVersionDocumentoDTO.getFechaGeneracion());

                // Se halla el usuario que generó el documento inicial
                UsuarioOrganizacionDTO usuarioGeneroDocumento = ultimaVersionDocumentoDTO.getUsuarioOrganizacionDTO();
                if (ultimaVersionDocumentoDTO.getDocumentoOrigenDTO() != null) {
                    usuarioGeneroDocumento = ultimaVersionDocumentoDTO.getDocumentoOrigenDTO()
                            .getUsuarioOrganizacionDTO();
                }
                if (usuarioGeneroDocumento.getFuncionarioDTO() == null) {
                    usuarioGeneroDocumento = ilUsuarioOrganizacion
                            .consultarUsuarioOrganizacion(usuarioGeneroDocumento.getIdUsuario());
                }

                // Genera objeto jasper para la generacion
                JasperUtilDTO jasperUtilDTO = generarJasperUtilDTO(generaDocumentoDTO,
                        ultimaVersionDocumentoDTO.getPlantillaDTO(), usuarioGeneroDocumento);

                XmlDocumentoDTO xmlDocumentoDTO = new XmlDocumentoDTO();
                xmlDocumentoDTO.setParametrosDocumento(generaDocumentoDTO.getData().toString());

                // Obtiene el consecutivo de documento
                // En caso de concurrencia
                long consecutivoDocumento = ultimaVersionDocumentoDTO.getConsecutivoDocumento();
                jasperUtilDTO.setNombreDocumento(
                        consecutivoDocumento + "_v" + (ultimaVersionDocumentoDTO.getVersionDocumento() + 1));

                // Crea la instancia del utilitario de jasper
                JasperUtil jasperUtil = new JasperUtil(jasperUtilDTO);

                // Genera documento en formato deseado utilizando jasperreports
                jasperUtil.exportDocument();

                // Generado exitosamente
                // Se obtiene el documento del repositorio
                co.com.datatools.documentos.cm.dto.Documento documentCM = null;
                if (ultimaVersionDocumentoDTO.getVersionDocumentoCm() > 0) {
                    // Consulta la version especifica del documento
                    documentCM = iGestorArchivos.obtenerDocumento(ultimaVersionDocumentoDTO.getCodigoDocumento(),
                            ultimaVersionDocumentoDTO.getVersionDocumentoCm() + "");
                } else {
                    // Consulta la ultima version del documento
                    documentCM = iGestorArchivos.obtenerDocumento(ultimaVersionDocumentoDTO.getCodigoDocumento(), null);
                }
                documentCM.setContenido(Files.readAllBytes(Paths.get(jasperUtil.getPathArchivo())));
                documentCM.setNombre(consecutivoDocumento + "");

                // Actualiza el documento en el repositorio
                co.com.datatools.documentos.cm.dto.Documento documentCMRespuesta = iGestorArchivos
                        .actualizarDocumento(documentCM);

                if (documentCMRespuesta != null) {
                    // La actualizacion fue exitosa
                    documentoDTO = new DocumentoDTO();
                    documentoDTO.setCodigoDocumento(documentCMRespuesta.getIdDocumento());
                    documentoDTO.setVersionDocumentoCm(Integer.parseInt(documentCMRespuesta.getVersion()));
                    documentoDTO.setContenido(documentCMRespuesta.getContenido());

                    // Asigna datos para guardar el documento en la BD
                    documentoDTO.setPathDocumento(jasperUtil.getPathArchivo());
                    documentoDTO.setDescripcionDocumento(ultimaVersionDocumentoDTO.getDescripcionDocumento());
                    documentoDTO.setFechaGeneracion(ultimaVersionDocumentoDTO.getFechaGeneracion());
                    documentoDTO.setFechaCreacion(new Date());
                    documentoDTO.setPlantillaDTO(ultimaVersionDocumentoDTO.getPlantillaDTO());
                    documentoDTO.setUsuarioOrganizacionDTO(usuario);
                    documentoDTO.setConsecutivoDocumento(consecutivoDocumento);
                    documentoDTO.setVersionDocumento(ultimaVersionDocumentoDTO.getVersionDocumento() + 1);
                    documentoDTO.setNombreDocumento(jasperUtilDTO.getNombreDocumento() + documentCMRespuesta.getTipo());

                    xmlDocumentoDTO.setNombreXmlDoc(jasperUtilDTO.getNombreDocumento());
                    xmlDocumentoDTO.setContenidoXml("".getBytes());
                    documentoDTO.setXmlDocumentoDTO(xmlDocumentoDTO);
                    documentoDTO = registrarDocumento(documentoDTO);
                    documentoDTO.setXmlDocumentoDTO(null);
                } else {
                    LOGGER.info("DocumentoEJB::generarDocumento No se pudo guardar en el repositorio");
                    throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001003);
                }
            }
        } catch (IOException e) {
            LOGGER.error("DocumentoEJB::generarDocumento Error al guardar archivos", e);
            throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001004);
        } catch (JRException e) {
            LOGGER.error("DocumentoEJB::generarDocumento Error de jasper", e);
            throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001004);
        } catch (DocumentosException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("DocumentoEJB::generarDocumento Error inesperado", e);
            throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001004);
        }

        return documentoDTO;
    }

    @Override
    public DocumentoDTO generarDocumentoSinGuardar(GeneraDocumentoDTO generaDocumentoDTO) throws DocumentosException {
        LOGGER.debug("DocumentoEJB::generarDocumentoSinGuardar");
        DocumentoDTO documentoDTO = null;

        // Consulta la plantilla utilizando el codigo
        PlantillaDTO plantillaDTOTemp = new PlantillaDTO();
        plantillaDTOTemp.setCodigoPlantilla(generaDocumentoDTO.getCodigoPlantilla());

        List<PlantillaDTO> plantillas = null;
        if (!generaDocumentoDTO.isPreliminar()) {
            plantillas = ilPlantilla.consultarPlantilla(plantillaDTOTemp, generaDocumentoDTO.getFechaGeneracion());
        } else {
            plantillas = new ArrayList<PlantillaDTO>();
            plantillas.add(plantillaDTOTemp);
        }
        // Verifica que existan plantillas que coincidan
        if (!plantillas.isEmpty()) {
            // Verifica el usuario que esta generando el documento
            UsuarioOrganizacionDTO usuario = ilUsuarioOrganizacion
                    .consultarUsuarioOrganizacion(generaDocumentoDTO.getUsuario(), true);
            if (usuario == null) {
                LOGGER.info("No se encontro el usuario : " + generaDocumentoDTO.getUsuario());
                throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001002);
            }
            if (generaDocumentoDTO.getData() == null) {
                generaDocumentoDTO.setData(new HashMap<String, Object>());
            }
            try {
                for (PlantillaDTO plantillaDTO : plantillas) {
                    // En caso de concurrencia
                    synchronized (this) {
                        // Genera objeto jasper para la generacion
                        JasperUtilDTO jasperUtilDTO = generarJasperUtilDTO(generaDocumentoDTO, plantillaDTO, usuario);

                        // Crea la instancia del utilitario de jasper
                        JasperUtil jasperUtil = new JasperUtil(jasperUtilDTO);

                        if (!generaDocumentoDTO.isPreliminar()) {
                            // Exporta el documento en formato deseado utilizando jasperreports
                            jasperUtil.exportDocument();
                        } else {
                            // Genera documento en formato deseado utilizando jasperreports
                            jasperUtil.generateDocument();
                        }
                        // Generado exitosamente
                        generaDocumentoDTO.setContenido(Files.readAllBytes(Paths.get(jasperUtil.getPathArchivo())));
                        generaDocumentoDTO.setNombreDocumento(jasperUtilDTO.getNombreDocumento());
                        // Asigna datos al dto de respuesta
                        documentoDTO = new DocumentoDTO();
                        documentoDTO.setPathDocumento(jasperUtil.getPathArchivo());
                        documentoDTO.setDescripcionDocumento(generaDocumentoDTO.getDescripcionDocumento());
                        documentoDTO.setVersionDocumento(1);
                        documentoDTO.setConsecutivoDocumento(-1l);
                        documentoDTO.setNombreDocumento(
                                jasperUtilDTO.getNombreDocumento() + jasperUtilDTO.getFormato().getExtension());
                        documentoDTO.setContenido(generaDocumentoDTO.getContenido());

                        // Solo toma la primera plantilla encontrada
                        break;
                    }
                }
            } catch (IOException e) {
                LOGGER.error("DocumentoEJB::generarDocumento Error al guardar archivos ", e);
                throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001004);
            } catch (JRException e) {
                LOGGER.error("DocumentoEJB::generarDocumentoSinGuardar Error de jasper ", e);
                throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001004);
            } catch (Exception e) {
                LOGGER.error("DocumentoEJB::generarDocumentoSinGuardar Excepcion en jasperUtil ", e);
                throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001004);
            }

        } else {
            LOGGER.info("No se encontro la plantilla asociada al código: " + generaDocumentoDTO.getCodigoPlantilla());
            throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001005);
        }

        return documentoDTO;
    }

    /**
     * Genera Objeto DTO para incluirlo en el llamado al utilitario de Jasper
     * 
     * @param generaDocumentoDTO
     *            Objeto de generacion
     * @param plantillaDTO
     *            PLantilla seleccionada
     * @param usuario
     *            Usuario que genera el documento
     * @return Objeto DTO para incluirlo en el llamado al utilitario de Jasper
     * @throws IOException
     *             Excepcion al generar archivos
     */
    private JasperUtilDTO generarJasperUtilDTO(GeneraDocumentoDTO generaDocumentoDTO, PlantillaDTO plantillaDTO,
            UsuarioOrganizacionDTO usuario) throws IOException {
        // Obtiene parametros del sistema con las rutas donde se guardan los reportes temporalmente
        String pathImagenes = ConstantesGeneral.PATH_DOCUMENTOS_FILE_SYSTEM
                + ilParametroSistema.consultarValorParametroSistema(EnumParametrosSistema.PATH_IMAGENES);
        String pathReportes = ConstantesGeneral.PATH_DOCUMENTOS_FILE_SYSTEM
                + ilParametroSistema.consultarValorParametroSistema(EnumParametrosSistema.PATH_REPORTES);
        String pathTemporalGuardaReporte = ConstantesGeneral.PATH_DOCUMENTOS_FILE_SYSTEM
                + ilParametroSistema.consultarValorParametroSistema(EnumParametrosSistema.PATH_TEMPORAL_GUARDA_REPORTE);
        String imagenPreliminar = ilParametroSistema
                .consultarValorParametroSistema(EnumParametrosSistema.IMAGEN_PRELIMINAR);
        String simboloMoneda = ilParametroSistema.consultarValorParametroSistema(EnumParametrosSistema.SIMBOLO_MONEDA);

        // Genera lista para pasarlo al metodo que exporta el documento al formato requerido
        List<Map<String, Object>> jasperDatasourceList = new ArrayList<Map<String, Object>>();
        jasperDatasourceList.add(generaDocumentoDTO.getData());

        // Genera DTO a utilizar para generar documento
        JasperUtilDTO jasperUtilDTO = new JasperUtilDTO();
        jasperUtilDTO.setPreliminar(generaDocumentoDTO.isPreliminar());
        jasperUtilDTO.setFormato(generaDocumentoDTO.getFormato());
        jasperUtilDTO.setPathDocumentos(ConstantesGeneral.PATH_DOCUMENTOS_FILE_SYSTEM);
        jasperUtilDTO.setPathImagenes(pathImagenes);
        jasperUtilDTO.setPathReportes(pathReportes);
        jasperUtilDTO.setPathTemporalGuardaReporte(pathTemporalGuardaReporte);
        jasperUtilDTO.setPreliminarImage(imagenPreliminar);
        jasperUtilDTO.setContextPath(generaDocumentoDTO.getContextPath());

        if (!generaDocumentoDTO.isPreliminar()) {
            // Verifica que las rutas existan, si no existen, las crea
            JasperUtil.verifyFolders(ConstantesGeneral.PATH_DOCUMENTOS_FILE_SYSTEM, pathReportes,
                    pathTemporalGuardaReporte, pathImagenes);

            // Verificar parametros de firmas
            completarFirmasDocumento(generaDocumentoDTO, plantillaDTO, usuario, pathImagenes);

            // Guardar imagenes temporales para la plantilla
            List<VariableDTO> variables = ilVariable.consultarVariablePlantilla(plantillaDTO);
            for (VariableDTO variableDTO : variables) {
                if (variableDTO.getTipoVariableDTO()
                        .getIdTipoVariable() == ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_LOCAL) {
                    JasperUtil.saveTempFile(variableDTO.getImagen(),
                            pathImagenes + variableDTO.getIdVariable() + "_" + variableDTO.getValorDefecto());
                } else if (variableDTO.getTipoVariableDTO()
                        .getIdTipoVariable() == ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_VARIABLE) {
                    // Recorre los valores enviados para encontrar si viene una variable de este tipo y si debe obtenerla del gestor de archivos
                    cargarVariableImagenVariableGestor(jasperDatasourceList, pathImagenes,
                            variableDTO.getNombreVariable());
                }
            }

            // Obtiene los archivos guardados de jasper
            int index = 0;
            JasperPlantillaDTO jasperPlantillaDTOTemp = new JasperPlantillaDTO();
            jasperPlantillaDTOTemp.setPlantillaDTO(plantillaDTO);
            List<JasperPlantillaDTO> jasperPlantillaList = ilJasperPlantilla
                    .consultarJasperPlantilla(jasperPlantillaDTOTemp);
            for (JasperPlantillaDTO jasperPlantillaDTO : jasperPlantillaList) {
                // Obtiene el archivo jasper para la generacion del documento
                co.com.datatools.documentos.cm.dto.Documento documentCM = iGestorArchivos
                        .obtenerDocumento(jasperPlantillaDTO.getCodigoDocumento(), null);
                // Guarda el temporal del jasper
                JasperUtil.saveTempFile(documentCM.getContenido(), pathReportes + documentCM.getNombre());
                // El primer jasper es el principal, a partir de este se genera el documento
                if (index == 0) {
                    jasperUtilDTO.setJasperPath(pathReportes + documentCM.getNombre());
                }
                index++;
            }

            // Completar mapa de parametros
            jasperDatasourceList = JasperUtil.completeData(
                    new String(plantillaDTO.getXmlPlantillaDTO().getContenidoXmlPlantilla(), StandardCharsets.UTF_8),
                    jasperDatasourceList, pathImagenes, simboloMoneda);

            jasperUtilDTO.setJasperDatasourceList(jasperDatasourceList);
            jasperUtilDTO.setNombreDocumento(plantillaDTO.getCodigoPlantilla());
        } else {
            // Completar mapa de parametros
            jasperDatasourceList = JasperUtil.completeData(generaDocumentoDTO.getXmlDocumento(), jasperDatasourceList,
                    pathImagenes, simboloMoneda);

            String imagenFirma = ilParametroSistema.consultarValorParametroSistema(EnumParametrosSistema.IMAGEN_FIRMA);

            jasperUtilDTO.setSignImage(imagenFirma);
            jasperUtilDTO.setNombreDocumento(generaDocumentoDTO.getNombreDocumento());
            jasperUtilDTO.setXmlDocumento(generaDocumentoDTO.getXmlDocumento());
            jasperUtilDTO.setJasperDatasourceList(jasperDatasourceList);
        }

        // Imagen de background cuando es una vista preliminar
        if (generaDocumentoDTO.getFormato().equals(EnumFormatoDocumento.PDF_PREVIEW)) {
            if (!Files.exists(Paths.get(pathImagenes + ConstantesPlantillas.PREVIEW_IMAGE))) {
                byte[] imagenTemp = Base64.decode(imagenPreliminar);
                JasperUtil.saveTempFile(imagenTemp, pathImagenes + ConstantesPlantillas.PREVIEW_IMAGE);
            }

            jasperUtilDTO.setPreliminar(true);
            jasperUtilDTO.setFormato(EnumFormatoDocumento.PDF);
        }
        return jasperUtilDTO;
    }

    @Override
    public DocumentoDTO crearDocumento(GeneraDocumentoDTO generaDocumentoDTO) {
        LOGGER.debug("DocumentoEJB::crearDocumento");
        co.com.datatools.documentos.cm.dto.Documento documentCMRespuesta = null;
        co.com.datatools.documentos.cm.dto.Documento documentCM = new co.com.datatools.documentos.cm.dto.Documento();
        documentCM.setContenido(generaDocumentoDTO.getContenido());
        documentCM.setFecha(generaDocumentoDTO.getFechaGeneracion());
        documentCM.setNombre(generaDocumentoDTO.getNombreDocumento());
        documentCM.setNombreReal(generaDocumentoDTO.getNombreDocumento());
        documentCM.setDescripcion(generaDocumentoDTO.getDescripcionDocumento());
        documentCM.setCarpeta(generaDocumentoDTO.getCarpeta());
        documentCM.setTipo(generaDocumentoDTO.getFormato().getExtension());
        if (!generaDocumentoDTO.isActualizar()) {
            documentCMRespuesta = iGestorArchivos.guardarDocumento(documentCM);
        } else {
            // Actualizar documento
            documentCMRespuesta = iGestorArchivos.actualizarDocumento(documentCM);
        }

        DocumentoDTO documentoDTO = null;
        if (documentCMRespuesta != null) {
            documentoDTO = new DocumentoDTO();
            documentoDTO.setCodigoDocumento(documentCMRespuesta.getIdDocumento());
            documentoDTO.setVersionDocumentoCm(Integer.parseInt(documentCMRespuesta.getVersion()));
            documentoDTO.setContenido(documentCMRespuesta.getContenido());
        }
        return documentoDTO;
    }

    /**
     * Método que persiste la entidad XmlDocumento
     * 
     * @param xmlDocumentoDTO
     *            Contiene los datos de la entidad a persistir
     * @return DTO del XmlDocumento ingresado
     * @author julio.pinzon
     */
    private XmlDocumentoDTO registrarXmlDocumento(XmlDocumentoDTO xmlDocumentoDTO) {
        LOGGER.debug("DocumentoEJB::registrarXmlDocumento");

        XmlDocumento xmlDocumento = XmlDocumentoHelper.toXmlDocumento(xmlDocumentoDTO, new XmlDocumento());
        Documento documento = DocumentoHelper.toDocumento((xmlDocumentoDTO.getDocumentoDTO()), new Documento());
        xmlDocumento.setDocumento(documento);
        em.persist(xmlDocumento);
        em.flush();
        xmlDocumentoDTO.setIdXmlDocumento(xmlDocumento.getIdXmlDocumento());

        return xmlDocumentoDTO;
    }

    /**
     * Completa los datos de la firma
     * 
     * @param generaDocumentoDTO
     * @param plantillaDTO
     * @param usuario
     * @author julio.pinzon
     * @param pathImagenes
     */
    private void completarFirmasDocumento(GeneraDocumentoDTO generaDocumentoDTO, PlantillaDTO plantillaDTO,
            UsuarioOrganizacionDTO usuario, String pathImagenes) {
        // Extension de la firma
        String extension = ilParametroSistema
                .consultarValorParametroSistema(EnumParametrosSistema.EXTENSION_IMAGEN_FIRMA);
        // Verificar parametros de firmas
        FirmaPlantillaDTO firmaPlantillaDTOTemp = new FirmaPlantillaDTO();
        firmaPlantillaDTOTemp.setPlantillaDTO(plantillaDTO);
        List<FirmaPlantillaDTO> firmas = ilFirmaPlantilla.consultarFirmaPlantilla(firmaPlantillaDTOTemp);
        for (FirmaPlantillaDTO firmaPlantillaDTO : firmas) {

            // Variables para firmas
            String nombreFuncionario = "";
            String cargoFuncionario = "";
            String firma = "";
            String codigo = (firmaPlantillaDTO.getCodigoFirmaPlantilla() > 0)
                    ? firmaPlantillaDTO.getCodigoFirmaPlantilla() + "" : "";
            // Agregar parametros correctos para las firmas
            if (firmaPlantillaDTO.getTipoFirmaPlantillaDTO()
                    .getIdTipoFirmaPlantilla() == ConstantesPlantillas.ID_TIPO_FIRMA_PLANTILLA_FUNCIONARIO) {
                FuncionarioDTO funcionarioDTO = ilFuncionario
                        .consultarFuncionarioId(firmaPlantillaDTO.getFuncionarioDTO().getIdFuncionario());
                nombreFuncionario = funcionarioDTO.getNombreFuncionario();
                if (funcionarioDTO.getFirma() != null) {
                    firma = JasperUtil.saveTempFile(funcionarioDTO.getFirma(),
                            pathImagenes + FIRMA_FUNCIONARIO + funcionarioDTO.getIdFuncionario() + extension);
                }
                firmaPlantillaDTO.setFuncionarioDTO(funcionarioDTO);
            } else if (firmaPlantillaDTO.getTipoFirmaPlantillaDTO()
                    .getIdTipoFirmaPlantilla() == ConstantesPlantillas.ID_TIPO_FIRMA_PLANTILLA_FUNCIONARIO_GENERA_DOCUMENTO
                    && usuario.getFuncionarioDTO() != null) {
                LOGGER.info(usuario);
                FuncionarioDTO funcionarioDTO = ilFuncionario
                        .consultarFuncionarioId(usuario.getFuncionarioDTO().getIdFuncionario());
                nombreFuncionario = funcionarioDTO.getNombreFuncionario();
                if (funcionarioDTO.getFirma() != null) {
                    firma = JasperUtil.saveTempFile(funcionarioDTO.getFirma(),
                            pathImagenes + FIRMA_FUNCIONARIO + funcionarioDTO.getIdFuncionario() + extension);
                }
                firmaPlantillaDTO.setFuncionarioDTO(funcionarioDTO);
            }

            if (firmaPlantillaDTO.getTipoFirmaPlantillaDTO()
                    .getIdTipoFirmaPlantilla() == ConstantesPlantillas.ID_TIPO_FIRMA_PLANTILLA_CARGO
                    || firmaPlantillaDTO.isMostrarCargoFuncionario()) {
                // Consulta el cargo para el funcionario
                ConsultaCargoFuncionarioDTO consultaCargoFuncionarioDTO = new ConsultaCargoFuncionarioDTO();
                consultaCargoFuncionarioDTO.setFuncionarioDTO(firmaPlantillaDTO.getFuncionarioDTO());
                if (firmaPlantillaDTO.getCargoDTO() != null) {
                    consultaCargoFuncionarioDTO.setCargoDTO(firmaPlantillaDTO.getCargoDTO());
                } else {
                    List<ProcesoDTO> procesos = new ArrayList<ProcesoDTO>();
                    // Agregamos los procesos padres para la consulta de cargos
                    ProcesoDTO procesoTemp = plantillaDTO.getProcesoDTO().getProcesoDTO();
                    while (procesoTemp != null) {
                        procesos.add(procesoTemp);
                        procesoTemp = procesoTemp.getProcesoDTO();
                    }
                    consultaCargoFuncionarioDTO.setProcesos(procesos);
                    consultaCargoFuncionarioDTO.setProcesoDTO(plantillaDTO.getProcesoDTO());
                }
                // Verifica el tipo de fecha de referencia
                if (firmaPlantillaDTO.getTipoFechaReferenciaDTO() != null) {
                    if (firmaPlantillaDTO.getTipoFechaReferenciaDTO()
                            .getIdTipoFecha() == ConstantesPlantillas.ID_TIPO_FECHA_REFERENCIA_FECHA_GENERACION) {
                        // Verificar fecha generacion
                        consultaCargoFuncionarioDTO.setFechaReferencia(generaDocumentoDTO.getFechaGeneracion());
                    } else if (firmaPlantillaDTO.getTipoFechaReferenciaDTO()
                            .getIdTipoFecha() == ConstantesPlantillas.ID_TIPO_FECHA_REFERENCIA_FECHA_VARIABLE_PLANTILLA) {
                        SimpleDateFormat sdf = new SimpleDateFormat(
                                firmaPlantillaDTO.getVariableDTO().getFormatoVariable());
                        if (generaDocumentoDTO.getData()
                                .containsKey(firmaPlantillaDTO.getVariableDTO().getNombreVariable())) {
                            if (generaDocumentoDTO.getData()
                                    .get(firmaPlantillaDTO.getVariableDTO().getNombreVariable()) instanceof Date) {
                                consultaCargoFuncionarioDTO.setFechaReferencia((Date) generaDocumentoDTO.getData()
                                        .get(firmaPlantillaDTO.getVariableDTO().getNombreVariable()));
                            } else {
                                consultaCargoFuncionarioDTO.setFechaReferencia(new Date());
                            }
                        } else {
                            try {
                                consultaCargoFuncionarioDTO.setFechaReferencia(
                                        sdf.parse(firmaPlantillaDTO.getVariableDTO().getValorDefecto()));
                            } catch (ParseException e) {
                                LOGGER.error("Error al obtener valor de variable por defecto", e);
                                consultaCargoFuncionarioDTO.setFechaReferencia(new Date());
                            }
                        }
                    } else if (firmaPlantillaDTO.getTipoFechaReferenciaDTO()
                            .getIdTipoFecha() == ConstantesPlantillas.ID_TIPO_FECHA_REFERENCIA_FECHA_ACTUAL) {
                        // Verificar fecha actual
                        consultaCargoFuncionarioDTO.setFechaReferencia(new Date());
                    }
                } else {
                    consultaCargoFuncionarioDTO.setFechaReferencia(generaDocumentoDTO.getFechaGeneracion());
                }
                // Consulta del funcionario y cargo para la firma
                FuncionarioCargoDTO funcionarioCargo = ilFuncionario
                        .consultarFirmaFuncionarioCargo(consultaCargoFuncionarioDTO);
                if (funcionarioCargo == null && !consultaCargoFuncionarioDTO.getProcesos().isEmpty()) {
                    consultaCargoFuncionarioDTO.setProcesoDTO(null);
                    // Consulta del funcionario y cargo para la firma
                    funcionarioCargo = ilFuncionario.consultarFirmaFuncionarioCargo(consultaCargoFuncionarioDTO);
                }
                if (funcionarioCargo != null) {
                    cargoFuncionario = funcionarioCargo.getCargoDTO().getNombreCargo();
                    nombreFuncionario = funcionarioCargo.getFuncionarioDTO().getNombreFuncionario();
                    if (firma.isEmpty() && funcionarioCargo.getFuncionarioDTO().getFirma() != null) {
                        firma = JasperUtil.saveTempFile(funcionarioCargo.getFuncionarioDTO().getFirma(), pathImagenes
                                + FIRMA_FUNCIONARIO + funcionarioCargo.getIdFuncionarioCargo() + extension);
                    }
                }
            }

            // Agrega los valores para las firmas
            if (firmaPlantillaDTO.isMostrarNombreFuncionario()
                    && !generaDocumentoDTO.getData().containsKey(NOMBRE_FUNCIONARIO + codigo)) {
                generaDocumentoDTO.getData().put(NOMBRE_FUNCIONARIO + codigo, nombreFuncionario);
            }
            if (firmaPlantillaDTO.isMostrarCargoFuncionario()
                    && !generaDocumentoDTO.getData().containsKey(CARGO_FUNCIONARIO + codigo)) {
                generaDocumentoDTO.getData().put(CARGO_FUNCIONARIO + codigo, cargoFuncionario);
            }
            if (!generaDocumentoDTO.getData().containsKey(FIRMA_FUNCIONARIO + codigo)) {
                generaDocumentoDTO.getData().put(FIRMA_FUNCIONARIO + codigo, firma);
            }
        }
    }

    @Override
    public DocumentoDTO obtenerDocumento(long consecutivoDocumento) {
        LOGGER.debug("DocumentoEJB::obtenerDocumento");
        return obtenerDocumentoVersion(consecutivoDocumento, 0);
    }

    @Override
    public DocumentoDTO obtenerDocumento(long consecutivoDocumento, int version) {
        LOGGER.debug("DocumentoEJB::obtenerDocumento");
        return obtenerDocumentoVersion(consecutivoDocumento, version);
    }

    /**
     * Obtiene el documento del repositorio
     * 
     * @param consecutivoDocumento
     * @param version
     * @return Documento encontrado
     */
    private DocumentoDTO obtenerDocumentoVersion(long consecutivoDocumento, int version) {
        LOGGER.debug("DocumentoEJB::obtenerDocumentoVersion");
        // Obtiene parametros del sistema con las rutas donde se guardan los reportes temporalmente
        // Consulta el parametro de la ruta temporal del documento
        String pathTemporal = ConstantesGeneral.PATH_DOCUMENTOS_FILE_SYSTEM
                + ilParametroSistema.consultarValorParametroSistema(EnumParametrosSistema.PATH_TEMPORAL_GUARDA_REPORTE);
        co.com.datatools.documentos.cm.dto.Documento documentCM = null;
        DocumentoDTO documentoDTOTemp = new DocumentoDTO();
        documentoDTOTemp.setConsecutivoDocumento(consecutivoDocumento);
        DocumentoDTO documentoDTO = null;
        if (version > 0) {
            documentoDTOTemp.setVersionDocumento(version);
            List<DocumentoDTO> documentosDTO = consultarDocumento(documentoDTOTemp);
            if (!documentosDTO.isEmpty()) {
                documentoDTO = documentosDTO.get(0);
            }
        } else {
            documentoDTO = consultarUltimaVersionDocumento(documentoDTOTemp);
        }
        if (documentoDTO != null) {
            if (documentoDTO.getVersionDocumentoCm() > 0) {
                // Consulta la version especifica del documento
                documentCM = iGestorArchivos.obtenerDocumento(documentoDTO.getCodigoDocumento(),
                        documentoDTO.getVersionDocumentoCm() + "");
            } else {
                // Consulta la ultima version del documento
                documentCM = iGestorArchivos.obtenerDocumento(documentoDTO.getCodigoDocumento(), null);
            }
        }
        // Si encuentra el documento en el repositorio
        if (documentCM != null) {
            String pathDocumento = JasperUtil.saveTempFile(documentCM.getContenido(),
                    pathTemporal + documentoDTO.getNombreDocumento());
            documentoDTO.setPathDocumento(pathDocumento);
            documentoDTO.setContenido(documentCM.getContenido());
        }
        return documentoDTO;
    }

    /**
     * Genera el consecutivo para guardar el documento
     * 
     * @return Retorna el siguiente consecutivo disponible para almacenar un documento
     */
    private long cargarConsecutivo() {
        LOGGER.debug("DocumentoEJB::consultarUltimaVersionDocumento");
        long consecutivoDocumento = 0;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT MAX(doc.consecutivoDocumento) FROM Documento doc");
        TypedQuery<Long> consulta = em.createQuery(sql.toString(), Long.class);
        Long maxConsecutivoDocumento = consulta.getSingleResult();
        if (maxConsecutivoDocumento != null) {
            consecutivoDocumento = maxConsecutivoDocumento.longValue();
        }
        return consecutivoDocumento;
    }

    @Override
    public DocumentoDTO consultarDocumentosPDF(List<Long> codigosDocumentos) throws DocumentosException {
        LOGGER.debug("DocumentoEJB::consultarDocumentosPDF");

        // Obtiene parametros del sistema con las rutas donde se guardan los reportes temporalmente
        // Consulta el parametro de la ruta temporal del documento
        String pathTemporal = ConstantesGeneral.PATH_DOCUMENTOS_FILE_SYSTEM
                + ilParametroSistema.consultarValorParametroSistema(EnumParametrosSistema.PATH_TEMPORAL_GUARDA_REPORTE);

        DocumentoDTO documentoDTO = null;
        PDFMergerUtility ut = new PDFMergerUtility();
        boolean tienePDF = false;
        for (Long codigoDocumento : codigosDocumentos) {
            DocumentoDTO documentoDTOTemp = obtenerDocumento(codigoDocumento);
            // Si encuentra el documento en el repositorio
            if (documentoDTOTemp != null && documentoDTOTemp.getNombreDocumento().toLowerCase().trim()
                    .endsWith(EnumFormatoDocumento.PDF.getExtension())) {
                String pathDocumento = JasperUtil.saveTempFile(documentoDTOTemp.getContenido(),
                        pathTemporal + documentoDTOTemp.getNombreDocumento());
                ut.addSource(pathDocumento);
                tienePDF = true;
            }
        }
        // Si hay documentos en la lista de pdf
        if (tienePDF) {
            long fechaActual = new Date().getTime();
            String nombreArchivoCombinado = ConstantesDocumentos.NOMBRE_ARCHIVO_PDF_COMBINADO + fechaActual
                    + EnumFormatoDocumento.PDF.getExtension();
            ut.setDestinationFileName(pathTemporal + nombreArchivoCombinado);
            try {
                ut.mergeDocuments();
                documentoDTO = new DocumentoDTO();
                documentoDTO.setNombreDocumento(nombreArchivoCombinado);
                documentoDTO.setContenido(Files.readAllBytes(Paths.get(pathTemporal + nombreArchivoCombinado)));
            } catch (COSVisitorException | IOException e) {
                LOGGER.error("Error al combinar archivos", e);
                throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001006);
            }
        } else {
            LOGGER.info("La lista de codigos enviados no contiene documentos en formato PDF");
            throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001007);
        }
        return documentoDTO;
    }

    @Override
    public String consultarURLDocumentos(List<Long> ids) throws DocumentosException {
        String rutaArchivoLocal, servidor, usuario, clave, rutaDestino, urlResultado;
        File archivoLocal;

        if (ids == null) {
            LOGGER.info("DocumentoEJB::consultarURLDocumentos Lista de ids es nula");
            throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        if (ids.isEmpty()) {
            LOGGER.info("DocumentoEJB::consultarURLDocumentos Lista de ids es vacia");
            throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        String pathDocumentos = this.cargarParametroString(EnumParametrosSistema.PATH_DESCARGA_ARCHIVOS);
        DocumentoDTO documentoDTO = this.consultarDocumentosPDF(ids);
        File arch = new File(pathDocumentos, documentoDTO.getNombreDocumento());
        rutaArchivoLocal = JasperUtil.saveTempFile(documentoDTO.getContenido(), arch.toPath().toString());
        if ((rutaArchivoLocal == null) || (rutaArchivoLocal.isEmpty())) {
            LOGGER.info("DocumentoEJB::consultarURLDocumentos Problemas guardando el archivo en la ruta indicada.");
            throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001004);
        }

        archivoLocal = new File(rutaArchivoLocal);
        servidor = this.cargarParametroString(EnumParametrosSistema.SERVIDOR_FTP);
        usuario = this.cargarParametroString(EnumParametrosSistema.USUARIO_FTP);
        clave = this.cargarParametroString(EnumParametrosSistema.CLAVE_FTP);
        rutaDestino = this.cargarParametroString(EnumParametrosSistema.RUTA_DESTINO_FTP);

        try {
            urlResultado = Ftp.cargarArchivoFTP(servidor, usuario, clave, archivoLocal, rutaDestino,
                    archivoLocal.getName());
        } catch (Exception e) {
            LOGGER.error("DocumentoEJB::consultarURLDocumentos Problemas usando Ftp.cargarArchivoFTP.", e);
            throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001017);
        }

        return urlResultado;

    }

    /**
     * Retorna el valor del parametro indicado como un String
     * 
     * @param param
     *            el parametro a cargar
     * @throws DocumentosException
     *             si el parametro enum o su valor es nulo
     */
    private String cargarParametroString(EnumParametrosSistema param) throws DocumentosException {
        String valor;

        if (param == null) {
            LOGGER.error("DocumentoEJB::cargarParametroString Se intenta cargar un parametro NULO.");
            throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001017);
        }

        valor = this.ilParametroSistema.consultarValorParametroSistema(param);
        if (valor == null) {
            LOGGER.error("DocumentoEJB::cargarParametroString El parametro de configuracion " + param + " es nulo.");
            throw new DocumentosException(ErrorDocumentos.GenerarDocumento.DOC_001012);
        }

        return valor;
    }

    /**
     * Carga imagenes variables desde el gestor de archivos
     * 
     * @param jasperDatasourceList
     * @param pathImagenes
     * @param nombreVariable
     * @author julio.pinzon 2016-08-18
     */
    private void cargarVariableImagenVariableGestor(List<Map<String, Object>> jasperDatasourceList, String pathImagenes,
            String nombreVariable) {

        // Recorre los valores enviados para encontrar si viene una variable de este tipo y si debe obtenerla del gestor de archivos
        for (Map<String, Object> mapGroup : jasperDatasourceList) {
            for (Entry<String, Object> data : mapGroup.entrySet()) {

                // Si no tiene parametros envia los que son por defecto
                if (data.getValue() instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> grupo = (ArrayList<Map<String, Object>>) data.getValue();
                    cargarVariableImagenVariableGestor(grupo, pathImagenes, nombreVariable);
                } else if (data.getKey().equals(nombreVariable) && StringUtils.isNumeric(data.getValue().toString())) {
                    // Obtiene el archivo jasper para la generacion del documento
                    co.com.datatools.documentos.cm.dto.Documento documentCM = iGestorArchivos
                            .obtenerDocumento(data.getValue().toString(), null);
                    if (documentCM != null) {
                        // Guarda el temporal de la imagen
                        JasperUtil.saveTempFile(documentCM.getContenido(), pathImagenes + documentCM.getNombre());
                        data.setValue(pathImagenes + documentCM.getNombre());
                    }
                }
            }
        }
    }

}
