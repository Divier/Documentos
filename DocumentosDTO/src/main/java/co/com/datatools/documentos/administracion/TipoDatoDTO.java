package co.com.datatools.documentos.administracion;

import java.io.Serializable;
import java.util.List;


/** 
 *
 * @author Generated
 * @version 2.0
 **/ 
public class TipoDatoDTO implements Serializable{

	// Attributes Declaration 

	private static final long serialVersionUID=1L;
	private int idTipoDato;
	private String nombreTipoDato;
	private List<ParametroSistemaDTO> listDatosDTO;

	// Constructors Declaration 

	public TipoDatoDTO(){

	}


	// Start Methods Declaration 

	public int getIdTipoDato() {
		return idTipoDato;
	}

	 public void setIdTipoDato(int idTipoDato) {
		this.idTipoDato=idTipoDato;
	}

	public String getNombreTipoDato() {
		return nombreTipoDato;
	}

	 public void setNombreTipoDato(String nombreTipoDato) {
		this.nombreTipoDato=nombreTipoDato;
	}

	public List<ParametroSistemaDTO> getListDatosDTO() {
		return listDatosDTO;
	}

	 public void setListDatosDTO(List<ParametroSistemaDTO> listDatosDTO) {
		this.listDatosDTO=listDatosDTO;
	}



// Finish the class
 }