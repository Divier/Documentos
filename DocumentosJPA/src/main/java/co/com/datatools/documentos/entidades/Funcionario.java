package co.com.datatools.documentos.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the funcionario database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(
        name = "Funcionario.findByNumDocTipDoc",
        query = "SELECT f FROM Funcionario f WHERE f.numeroDocumIdent = :pNumDoc AND f.nombreTipoIdentificacion = :pNomTipIde"),
        @NamedQuery(
                name = "Funcionario.countByNumDocTipDoc",
                query = "SELECT COUNT(f) FROM Funcionario f WHERE f.numeroDocumIdent = :pNumDoc AND f.nombreTipoIdentificacion = :pNomTipIde")})
public class Funcionario implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Consulta funcionario por numero de documento de identificacion y nombre del tipo de identificacion
     * 
     * <p>
     * Parametros:
     * <li><b>pNumDoc</b> Numero de documento</li>
     * <li><b>pNomTipIde</b> Nombre del tipo de documento</li>
     * </p>
     * </br>
     * 
     * SELECT f FROM Funcionario f WHERE f.numeroDocumIdent = :pNumDoc AND f.nombreTipoIdentificacion = :pNomTipIde</br>
     * 
     * @author luis.forero(2015-07-30)
     */
    public static final String SQ_BY_NUM_DOC_TIP_DOC = "Funcionario.findByNumDocTipDoc";
    
    /**
     * Consulta cuantos funcionarios hay con el mismo numero de documento de identificacion y nombre del tipo de identificacion
     * 
     * <p>
     * Parametros:
     * <li><b>pNumDoc</b> Numero de documento</li>
     * <li><b>pNomTipIde</b> Nombre del tipo de documento</li>
     * </p>
     * </br>
     * 
     * SELECT COUNT(f) FROM Funcionario f WHERE f.numeroDocumIdent = :pNumDoc AND f.nombreTipoIdentificacion = :pNomTipIde</br>
     * 
     * @author luis.forero(2015-08-03)
     */
    public static final String SQ_COUNT_BY_NUM_DOC_TIP_DOC = "Funcionario.countByNumDocTipDoc";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario")
    private long idFuncionario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_final_funcionario")
    private Date fechaFinalFuncionario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicial_funcionario")
    private Date fechaInicialFuncionario;

    @Lob
    private byte[] firma;

    @Column(name = "nombre_funcionario")
    private String nombreFuncionario;

    @Column(name = "nombre_tipo_identificacion")
    private String nombreTipoIdentificacion;

    @Column(name = "numero_docum_ident")
    private String numeroDocumIdent;

    @Column(name = "sigla_tipo_identificacion")
    private String siglaTipoIdentificacion;

    // bi-directional many-to-one association to FirmaDigitalDocumento
    @OneToMany(mappedBy = "funcionario")
    private List<FirmaDigitalDocumento> firmaDigitalDocumentos;

    // bi-directional many-to-one association to FirmaPlantilla
    @OneToMany(mappedBy = "funcionario")
    private List<FirmaPlantilla> firmaPlantillas;

    // bi-directional many-to-one association to FuncionarioCargo
    @OneToMany(mappedBy = "funcionario")
    private List<FuncionarioCargo> funcionarioCargos;

    // bi-directional many-to-one association to UsuarioOrganizacion
    @OneToMany(mappedBy = "funcionario")
    private List<UsuarioOrganizacion> usuarioOrganizacions;

    public Funcionario() {
    }

    public long getIdFuncionario() {
        return this.idFuncionario;
    }

    public void setIdFuncionario(long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Date getFechaFinalFuncionario() {
        return this.fechaFinalFuncionario;
    }

    public void setFechaFinalFuncionario(Date fechaFinalFuncionario) {
        this.fechaFinalFuncionario = fechaFinalFuncionario;
    }

    public Date getFechaInicialFuncionario() {
        return this.fechaInicialFuncionario;
    }

    public void setFechaInicialFuncionario(Date fechaInicialFuncionario) {
        this.fechaInicialFuncionario = fechaInicialFuncionario;
    }

    public byte[] getFirma() {
        return this.firma;
    }

    public void setFirma(byte[] firma) {
        this.firma = firma;
    }

    public String getNombreFuncionario() {
        return this.nombreFuncionario;
    }

    public void setNombreFuncionario(String nombreFuncionario) {
        this.nombreFuncionario = nombreFuncionario;
    }

    public String getNombreTipoIdentificacion() {
        return this.nombreTipoIdentificacion;
    }

    public void setNombreTipoIdentificacion(String nombreTipoIdentificacion) {
        this.nombreTipoIdentificacion = nombreTipoIdentificacion;
    }

    public String getNumeroDocumIdent() {
        return this.numeroDocumIdent;
    }

    public void setNumeroDocumIdent(String numeroDocumIdent) {
        this.numeroDocumIdent = numeroDocumIdent;
    }

    public String getSiglaTipoIdentificacion() {
        return this.siglaTipoIdentificacion;
    }

    public void setSiglaTipoIdentificacion(String siglaTipoIdentificacion) {
        this.siglaTipoIdentificacion = siglaTipoIdentificacion;
    }

    public List<FirmaDigitalDocumento> getFirmaDigitalDocumentos() {
        return this.firmaDigitalDocumentos;
    }

    public void setFirmaDigitalDocumentos(List<FirmaDigitalDocumento> firmaDigitalDocumentos) {
        this.firmaDigitalDocumentos = firmaDigitalDocumentos;
    }

    public FirmaDigitalDocumento addFirmaDigitalDocumento(FirmaDigitalDocumento firmaDigitalDocumento) {
        getFirmaDigitalDocumentos().add(firmaDigitalDocumento);
        firmaDigitalDocumento.setFuncionario(this);

        return firmaDigitalDocumento;
    }

    public FirmaDigitalDocumento removeFirmaDigitalDocumento(FirmaDigitalDocumento firmaDigitalDocumento) {
        getFirmaDigitalDocumentos().remove(firmaDigitalDocumento);
        firmaDigitalDocumento.setFuncionario(null);

        return firmaDigitalDocumento;
    }

    public List<FirmaPlantilla> getFirmaPlantillas() {
        return this.firmaPlantillas;
    }

    public void setFirmaPlantillas(List<FirmaPlantilla> firmaPlantillas) {
        this.firmaPlantillas = firmaPlantillas;
    }

    public FirmaPlantilla addFirmaPlantilla(FirmaPlantilla firmaPlantilla) {
        getFirmaPlantillas().add(firmaPlantilla);
        firmaPlantilla.setFuncionario(this);

        return firmaPlantilla;
    }

    public FirmaPlantilla removeFirmaPlantilla(FirmaPlantilla firmaPlantilla) {
        getFirmaPlantillas().remove(firmaPlantilla);
        firmaPlantilla.setFuncionario(null);

        return firmaPlantilla;
    }

    public List<FuncionarioCargo> getFuncionarioCargos() {
        return this.funcionarioCargos;
    }

    public void setFuncionarioCargos(List<FuncionarioCargo> funcionarioCargos) {
        this.funcionarioCargos = funcionarioCargos;
    }

    public FuncionarioCargo addFuncionarioCargo(FuncionarioCargo funcionarioCargo) {
        getFuncionarioCargos().add(funcionarioCargo);
        funcionarioCargo.setFuncionario(this);

        return funcionarioCargo;
    }

    public FuncionarioCargo removeFuncionarioCargo(FuncionarioCargo funcionarioCargo) {
        getFuncionarioCargos().remove(funcionarioCargo);
        funcionarioCargo.setFuncionario(null);

        return funcionarioCargo;
    }

    public List<UsuarioOrganizacion> getUsuarioOrganizacions() {
        return this.usuarioOrganizacions;
    }

    public void setUsuarioOrganizacions(List<UsuarioOrganizacion> usuarioOrganizacions) {
        this.usuarioOrganizacions = usuarioOrganizacions;
    }

    public UsuarioOrganizacion addUsuarioOrganizacion(UsuarioOrganizacion usuarioOrganizacion) {
        getUsuarioOrganizacions().add(usuarioOrganizacion);
        usuarioOrganizacion.setFuncionario(this);

        return usuarioOrganizacion;
    }

    public UsuarioOrganizacion removeUsuarioOrganizacion(UsuarioOrganizacion usuarioOrganizacion) {
        getUsuarioOrganizacions().remove(usuarioOrganizacion);
        usuarioOrganizacion.setFuncionario(null);

        return usuarioOrganizacion;
    }

}