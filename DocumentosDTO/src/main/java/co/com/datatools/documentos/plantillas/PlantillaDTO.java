package co.com.datatools.documentos.plantillas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.documentos.DocumentoDTO;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class PlantillaDTO implements Serializable, Cloneable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private int idPlantilla;
    private Date fechaCreacion;
    private Date fechaFin;
    private Date fechaInicio;
    private PlantillaDTO plantillaOrigenDTO;
    private String nombrePlantilla;
    private String codigoPlantilla;
    private boolean plantillaBloqueada;
    private int versionPlantilla;
    private List<DocumentoDTO> listDocumentosDTO;
    private List<FirmaPlantillaDTO> listFirmasPlantillaDTO;
    private ProcesoDTO procesoDTO;
    private UsuarioOrganizacionDTO usuarioOrganizacionDTO;
    private XmlPlantillaDTO xmlPlantillaDTO;
    private List<VariableDTO> listVariablesDTO;
    private List<JasperPlantillaDTO> listJasperPlantillaDTO;
    private boolean ultimaVersion;
    private boolean editar;

    // Constructors Declaration

    public PlantillaDTO() {

    }

    // Start Methods Declaration

    public int getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(int idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getNombrePlantilla() {
        return nombrePlantilla;
    }

    public void setNombrePlantilla(String nombrePlantilla) {
        this.nombrePlantilla = nombrePlantilla;
    }

    public boolean getPlantillaBloqueada() {
        return plantillaBloqueada;
    }

    public void setPlantillaBloqueada(boolean plantillaBloqueada) {
        this.plantillaBloqueada = plantillaBloqueada;
    }

    public int getVersionPlantilla() {
        return versionPlantilla;
    }

    public void setVersionPlantilla(int versionPlantilla) {
        this.versionPlantilla = versionPlantilla;
    }

    public List<DocumentoDTO> getListDocumentosDTO() {
        return listDocumentosDTO;
    }

    public void setListDocumentosDTO(List<DocumentoDTO> listDocumentosDTO) {
        this.listDocumentosDTO = listDocumentosDTO;
    }

    public List<FirmaPlantillaDTO> getListFirmasPlantillaDTO() {
        return listFirmasPlantillaDTO;
    }

    public void setListFirmasPlantillaDTO(List<FirmaPlantillaDTO> listFirmasPlantillaDTO) {
        this.listFirmasPlantillaDTO = listFirmasPlantillaDTO;
    }

    public ProcesoDTO getProcesoDTO() {
        return procesoDTO;
    }

    public void setProcesoDTO(ProcesoDTO procesoDTO) {
        this.procesoDTO = procesoDTO;
    }

    public XmlPlantillaDTO getXmlPlantillaDTO() {
        return xmlPlantillaDTO;
    }

    public void setXmlPlantillaDTO(XmlPlantillaDTO xmlPlantillaDTO) {
        this.xmlPlantillaDTO = xmlPlantillaDTO;
    }

    public List<VariableDTO> getListVariablesDTO() {
        return listVariablesDTO;
    }

    public void setListVariablesDTO(List<VariableDTO> listVariablesDTO) {
        this.listVariablesDTO = listVariablesDTO;
    }

    public PlantillaDTO getPlantillaOrigenDTO() {
        return plantillaOrigenDTO;
    }

    public void setPlantillaOrigenDTO(PlantillaDTO plantillaOrigenDTO) {
        this.plantillaOrigenDTO = plantillaOrigenDTO;
    }

    public UsuarioOrganizacionDTO getUsuarioOrganizacionDTO() {
        return usuarioOrganizacionDTO;
    }

    public void setUsuarioOrganizacionDTO(UsuarioOrganizacionDTO usuarioOrganizacionDTO) {
        this.usuarioOrganizacionDTO = usuarioOrganizacionDTO;
    }
    
   public String getCodigoPlantilla() {
        return codigoPlantilla;
    }

    public void setCodigoPlantilla(String codigoPlantilla) {
        this.codigoPlantilla = codigoPlantilla;
    }

    public List<JasperPlantillaDTO> getListJasperPlantillaDTO() {
        return listJasperPlantillaDTO;
    }

    public void setListJasperPlantillaDTO(List<JasperPlantillaDTO> listJasperPlantillaDTO) {
        this.listJasperPlantillaDTO = listJasperPlantillaDTO;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean isUltimaVersion() {
        return ultimaVersion;
    }

    public void setUltimaVersion(boolean ultimaVersion) {
        this.ultimaVersion = ultimaVersion;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    // Finish the class
}