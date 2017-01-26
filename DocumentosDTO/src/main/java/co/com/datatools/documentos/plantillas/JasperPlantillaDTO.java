package co.com.datatools.documentos.plantillas;


import java.io.Serializable;


/** 
 *
 * @author Generated
 * @version 2.0
 **/ 
public class JasperPlantillaDTO implements Serializable{

	// Attributes Declaration 

	private static final long serialVersionUID=1L;
	private String idJasperPlantilla;
	private String codigoDocumento;
	private PlantillaDTO plantillaDTO;
	//No corresponde a un campo en la bd
    private String contenidoJasper;
    private String nombreJasper;
    private boolean grupo;

	// Constructors Declaration 

	public JasperPlantillaDTO(){

	}


	// Start Methods Declaration 

	public String getIdJasperPlantilla() {
		return idJasperPlantilla;
	}

	 public void setIdJasperPlantilla(String idJasperPlantilla) {
		this.idJasperPlantilla=idJasperPlantilla;
	}

	public String getCodigoDocumento() {
		return codigoDocumento;
	}

	 public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento=codigoDocumento;
	}

	public PlantillaDTO getPlantillaDTO() {
		return plantillaDTO;
	}

	 public void setPlantillaDTO(PlantillaDTO plantillaDTO) {
		this.plantillaDTO=plantillaDTO;
	}

    public String getContenidoJasper() {
        return contenidoJasper;
    }

    public void setContenidoJasper(String contenidoJasper) {
        this.contenidoJasper = contenidoJasper;
    }

    public String getNombreJasper() {
        return nombreJasper;
    }

    public void setNombreJasper(String nombreJasper) {
        this.nombreJasper = nombreJasper;
    }

    public boolean isGrupo() {
        return grupo;
    }

    public void setGrupo(boolean grupo) {
        this.grupo = grupo;
    }



// Finish the class
 }