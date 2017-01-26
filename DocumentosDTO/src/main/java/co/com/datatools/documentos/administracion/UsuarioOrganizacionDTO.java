package co.com.datatools.documentos.administracion;

import java.io.Serializable;
import java.util.List;

import co.com.datatools.documentos.administracion.tmp.RolDTO;
import co.com.datatools.documentos.documentos.DocumentoDTO;
import co.com.datatools.documentos.plantillas.PlantillaDTO;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class UsuarioOrganizacionDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private long idUsuario;
    private String loginUsuario;
    private String contrasena;
    private List<DocumentoDTO> listDocumentosDTO;
    private List<PlantillaDTO> listPlantillasDTO;
    private FuncionarioDTO funcionarioDTO;
    private List<RolDTO> roles;

    // Constructors Declaration

    public UsuarioOrganizacionDTO() {

    }

    // Start Methods Declaration

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public List<DocumentoDTO> getListDocumentosDTO() {
        return listDocumentosDTO;
    }

    public void setListDocumentosDTO(List<DocumentoDTO> listDocumentosDTO) {
        this.listDocumentosDTO = listDocumentosDTO;
    }

    public List<PlantillaDTO> getListPlantillasDTO() {
        return listPlantillasDTO;
    }

    public void setListPlantillasDTO(List<PlantillaDTO> listPlantillasDTO) {
        this.listPlantillasDTO = listPlantillasDTO;
    }

    public FuncionarioDTO getFuncionarioDTO() {
        return funcionarioDTO;
    }

    public void setFuncionarioDTO(FuncionarioDTO funcionarioDTO) {
        this.funcionarioDTO = funcionarioDTO;
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

    public List<RolDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RolDTO> roles) {
        this.roles = roles;
    }

    // Finish the class
}