package co.com.datatools.documentos.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.AuditJoinTable;

import co.com.datatools.documentos.entidades.tmp.seguridad.Rol;

/**
 * The persistent class for the usuario_organizacion database table.
 * 
 */
@Entity
@Table(name = "usuario_organizacion")
@NamedQueries({
        @NamedQuery(name = "UsuarioOrganizacion.findAll", query = "SELECT u FROM UsuarioOrganizacion u"),
        @NamedQuery(
                name = "UsuarioOrganizacion.usuarioPorLogin",
                query = "SELECT u FROM UsuarioOrganizacion u WHERE u.loginUsuario = :loginUsuario") })
public class UsuarioOrganizacion implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String SQ_USUARIO_POR_LOGIN = "UsuarioOrganizacion.usuarioPorLogin";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private long idUsuario;

    @Column(name = "login_usuario")
    private String loginUsuario;

    @Column(name = "contrasena")
    private String contrasena;

    // bi-directional many-to-one association to Documento
    @OneToMany(mappedBy = "usuarioOrganizacion")
    private List<Documento> documentos;

    // bi-directional many-to-one association to Plantilla
    @OneToMany(mappedBy = "usuarioOrganizacion")
    private List<Plantilla> plantillas;

    // bi-directional many-to-one association to Organizacion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;

    // uni-directional many-to-many association to Rol
    @AuditJoinTable(schema = "documentos_aud")
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinTable(
            name = "usuario_rol",
            joinColumns = { @JoinColumn(name = "id_usuario") },
            inverseJoinColumns = { @JoinColumn(name = "id_rol") })
    private List<Rol> rols;

    public UsuarioOrganizacion() {
    }

    public long getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLoginUsuario() {
        return this.loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public List<Documento> getDocumentos() {
        return this.documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public Documento addDocumento(Documento documento) {
        getDocumentos().add(documento);
        documento.setUsuarioOrganizacion(this);

        return documento;
    }

    public Documento removeDocumento(Documento documento) {
        getDocumentos().remove(documento);
        documento.setUsuarioOrganizacion(null);

        return documento;
    }

    public List<Plantilla> getPlantillas() {
        return this.plantillas;
    }

    public void setPlantillas(List<Plantilla> plantillas) {
        this.plantillas = plantillas;
    }

    public Plantilla addPlantilla(Plantilla plantilla) {
        getPlantillas().add(plantilla);
        plantilla.setUsuarioOrganizacion(this);

        return plantilla;
    }

    public Plantilla removePlantilla(Plantilla plantilla) {
        getPlantillas().remove(plantilla);
        plantilla.setUsuarioOrganizacion(null);

        return plantilla;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena
     *            the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<Rol> getRols() {
        return this.rols;
    }

    public void setRols(List<Rol> rols) {
        this.rols = rols;
    }

}