package co.com.datatools.documentos.plantillas;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author julio.pinzon
 **/
public class PlantillaConsultaDTO implements Serializable {

	// Attributes Declaration

	private static final long serialVersionUID = 1L;
	private Date fechaFinDesde;
	private Date fechaFinHasta;
	private Date fechaInicioDesde;
	private Date fechaInicioHasta;
	private PlantillaDTO plantillaDTO;
	private boolean validaOrigen;
	private boolean ultimaVersion;

	// Constructors Declaration

	public PlantillaConsultaDTO() {

	}

	// Start Methods Declaration


	public Date getFechaFinDesde() {
		return fechaFinDesde;
	}

	public void setFechaFinDesde(Date fechaFinDesde) {
		this.fechaFinDesde = fechaFinDesde;
	}

	public Date getFechaFinHasta() {
		return fechaFinHasta;
	}

	public void setFechaFinHasta(Date fechaFinHasta) {
		this.fechaFinHasta = fechaFinHasta;
	}

	public Date getFechaInicioDesde() {
		return fechaInicioDesde;
	}

	public void setFechaInicioDesde(Date fechaInicioDesde) {
		this.fechaInicioDesde = fechaInicioDesde;
	}

	public Date getFechaInicioHasta() {
		return fechaInicioHasta;
	}

	public void setFechaInicioHasta(Date fechaInicioHasta) {
		this.fechaInicioHasta = fechaInicioHasta;
	}

	public PlantillaDTO getPlantillaDTO() {
		return plantillaDTO;
	}

	public void setPlantillaDTO(PlantillaDTO plantillaDTO) {
		this.plantillaDTO = plantillaDTO;
	}

    public boolean isValidaOrigen() {
        return validaOrigen;
    }

    public void setValidaOrigen(boolean validaOrigen) {
        this.validaOrigen = validaOrigen;
    }

    public boolean isUltimaVersion() {
        return ultimaVersion;
    }

    public void setUltimaVersion(boolean ultimaVersion) {
        this.ultimaVersion = ultimaVersion;
    }
	
	

	// Finish the class
}