/**
 * 
 */
package co.com.datatools.documentos.administracion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author julio.pinzon
 *
 */
public class ConsultaCargoFuncionarioDTO implements Serializable {

        // Attributes Declaration

        private static final long serialVersionUID = 1L;
        private Date fechaReferencia;
        private CargoDTO cargoDTO;
        private FuncionarioDTO funcionarioDTO;
        private ProcesoDTO procesoDTO;
        private List<ProcesoDTO> procesos;
        
        public Date getFechaReferencia() {
            return fechaReferencia;
        }
        public void setFechaReferencia(Date fechaReferencia) {
            this.fechaReferencia = fechaReferencia;
        }
        public CargoDTO getCargoDTO() {
            return cargoDTO;
        }
        public void setCargoDTO(CargoDTO cargoDTO) {
            this.cargoDTO = cargoDTO;
        }
        public FuncionarioDTO getFuncionarioDTO() {
            return funcionarioDTO;
        }
        public void setFuncionarioDTO(FuncionarioDTO funcionarioDTO) {
            this.funcionarioDTO = funcionarioDTO;
        }
        public ProcesoDTO getProcesoDTO() {
            return procesoDTO;
        }
        public void setProcesoDTO(ProcesoDTO procesoDTO) {
            this.procesoDTO = procesoDTO;
        }
        public List<ProcesoDTO> getProcesos() {
            return procesos;
        }
        public void setProcesos(List<ProcesoDTO> procesos) {
            this.procesos = procesos;
        }
        
        
}
