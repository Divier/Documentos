package co.com.datatools.documentos.managed_bean.funcionarios;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;

import co.com.datatools.documentos.administracion.ConsultaFuncionarioDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRFuncionario;
import co.com.datatools.util.web.mb.AbstractSwfManagedBean;

/**
 * Managed Bean para el manejo de funcionarios
 * 
 * @author sergio.torres (04/05/2015)
 * 
 */
@ManagedBean
@SessionScoped
public class FuncionarioMB extends AbstractSwfManagedBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ConsultaFuncionarioDTO funcionarioConsulta;
    private List<FuncionarioDTO> funcionariosResultado;
    private FuncionarioDTO funcionarioSeleccionado;
    private boolean consultaRealizada;
    
    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(FuncionarioMB.class.getName());

    @EJB
    private IRFuncionario irFuncionario;

    /**
     * Constructor que inicializa el objeto que almacena los filtros de busqueda
     * 
     */
    public FuncionarioMB() {
        funcionarioConsulta = new ConsultaFuncionarioDTO();
        funcionarioConsulta.setFuncionarioDTO(new FuncionarioDTO());
    }

    /**
     * Se encarga de consultar los funcionarios de acuerdo a los filtros diligenciados en el formulario
     * 
     * @return null para la ejecución del ajax de primefaces
     * @author sergio.torres (04/05/2015)
     */
    public String consultarFuncionarios() {
        funcionariosResultado = irFuncionario.consultarFuncionario(funcionarioConsulta);
        return null;
    }

    /**
     * Se encarga de cerra el popup generando una respuesta para que pueda ser manejada por el <strong>caller<strong>
     * 
     * 
     * @author sergio.torres (04/05/2015)
     */
    public void seleccionarFuncionarioPopup() {
    	logger.debug("FuncionarioMB::seleccionarFuncionarioPopup");
        RequestContext.getCurrentInstance().closeDialog(funcionarioSeleccionado);
    }

    public List<FuncionarioDTO> getFuncionariosResultado() {
        return funcionariosResultado;
    }

    public void setFuncionariosResultado(List<FuncionarioDTO> funcionariosResultado) {
        this.funcionariosResultado = funcionariosResultado;
    }

    public boolean isConsultaRealizada() {
        return consultaRealizada;
    }

    public void setConsultaRealizada(boolean consultaRealizada) {
        this.consultaRealizada = consultaRealizada;
    }

    public FuncionarioDTO getFuncionarioSeleccionado() {
        return funcionarioSeleccionado;
    }

    public void setFuncionarioSeleccionado(FuncionarioDTO funcionarioSeleccionado) {
        this.funcionarioSeleccionado = funcionarioSeleccionado;
    }

    public ConsultaFuncionarioDTO getFuncionarioConsulta() {
        return funcionarioConsulta;
    }

    public void setFuncionarioConsulta(ConsultaFuncionarioDTO funcionarioConsulta) {
        this.funcionarioConsulta = funcionarioConsulta;
    }

}
