/**
 * 
 */
package co.com.datatools.documentos.ws;

import java.io.Serializable;
import java.util.List;

/**
 * Representa un cargo y su listado de procesos asociados
 * 
 * @author robert.bautista
 * 
 */
public class CargoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Contiene el id del cargo
     */
    private int id;
    /**
     * Contiene el nombre del cargo
     */
    private String nombre;
    /**
     * Ids de los procesos a los cuales está relacionado el cargo
     */
    private List<Long> procesos;

    public CargoVO() {
    }

    public CargoVO(int id, String nombre, List<Long> procesos) {
        this.id = id;
        this.nombre = nombre;
        this.procesos = procesos;
    }

    public int getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public List<Long> getProcesos() {
        return this.procesos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProcesos(List<Long> procesos) {
        this.procesos = procesos;
    }

}
