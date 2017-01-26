package co.com.datatools.documentos.plantillas;

import java.io.Serializable;

import co.com.datatools.documentos.administracion.CargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.TipoFechaReferenciaDTO;
import co.com.datatools.documentos.administracion.TipoFirmaPlantillaDTO;


/** 
 *
 * @author Generated
 * @version 2.0
 **/ 
public class FirmaPlantillaDTO implements Serializable{

	// Attributes Declaration 

	private static final long serialVersionUID=1L;
	private int idFirmaPlantilla;
	private CargoDTO cargoDTO;
	private FuncionarioDTO funcionarioDTO;
	private PlantillaDTO plantillaDTO;
	private TipoFechaReferenciaDTO tipoFechaReferenciaDTO;
	private TipoFirmaPlantillaDTO tipoFirmaPlantillaDTO;
	private VariableDTO variableDTO;
	private boolean mostrarNombreFuncionario;
    private boolean mostrarCargoFuncionario;
    private int codigoFirmaPlantilla;

	// Constructors Declaration 

	public FirmaPlantillaDTO(){

	}

	// Start Methods Declaration 

	public int getIdFirmaPlantilla() {
		return idFirmaPlantilla;
	}

	 public void setIdFirmaPlantilla(int idFirmaPlantilla) {
		this.idFirmaPlantilla=idFirmaPlantilla;
	}

	public CargoDTO getCargoDTO() {
		return cargoDTO;
	}

	 public void setCargoDTO(CargoDTO cargoDTO) {
		this.cargoDTO=cargoDTO;
	}

	public FuncionarioDTO getFuncionarioDTO() {
		return funcionarioDTO;
	}

	 public void setFuncionarioDTO(FuncionarioDTO funcionarioDTO) {
		this.funcionarioDTO=funcionarioDTO;
	}

	public PlantillaDTO getPlantillaDTO() {
		return plantillaDTO;
	}

	 public void setPlantillaDTO(PlantillaDTO plantillaDTO) {
		this.plantillaDTO=plantillaDTO;
	}

	public TipoFechaReferenciaDTO getTipoFechaReferenciaDTO() {
		return tipoFechaReferenciaDTO;
	}

	 public void setTipoFechaReferenciaDTO(TipoFechaReferenciaDTO tipoFechaReferenciaDTO) {
		this.tipoFechaReferenciaDTO=tipoFechaReferenciaDTO;
	}

	public TipoFirmaPlantillaDTO getTipoFirmaPlantillaDTO() {
		return tipoFirmaPlantillaDTO;
	}

	 public void setTipoFirmaPlantillaDTO(TipoFirmaPlantillaDTO tipoFirmaPlantillaDTO) {
		this.tipoFirmaPlantillaDTO=tipoFirmaPlantillaDTO;
	}

	public VariableDTO getVariableDTO() {
		return variableDTO;
	}

	 public void setVariableDTO(VariableDTO variableDTO) {
		this.variableDTO=variableDTO;
	}

	public boolean isMostrarNombreFuncionario() {
		return mostrarNombreFuncionario;
	}

	 public void setMostrarNombreFuncionario(boolean mostrarNombreFuncionario) {
		this.mostrarNombreFuncionario=mostrarNombreFuncionario;
	}

    public int getCodigoFirmaPlantilla() {
        return codigoFirmaPlantilla;
    }

    public void setCodigoFirmaPlantilla(int codigoFirmaPlantilla) {
        this.codigoFirmaPlantilla = codigoFirmaPlantilla;
    }

    public boolean isMostrarCargoFuncionario() {
        return mostrarCargoFuncionario;
    }

    public void setMostrarCargoFuncionario(boolean mostrarCargoFuncionario) {
        this.mostrarCargoFuncionario = mostrarCargoFuncionario;
    }



// Finish the class
 }