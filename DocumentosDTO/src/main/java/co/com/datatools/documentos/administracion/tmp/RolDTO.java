package co.com.datatools.documentos.administracion.tmp;

import java.io.Serializable;

/**
 * Clase que repesenta al Rol para la comunicación entre capas
 * 
 * @author sergio.torres
 * 
 */
public class RolDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int idRol;
    private String descripcion;
    private String nombre;

    public RolDTO() {

    }

    public RolDTO(int idRol, String nombre, String descripcion) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
