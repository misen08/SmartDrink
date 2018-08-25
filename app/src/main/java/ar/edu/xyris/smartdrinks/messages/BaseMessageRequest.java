package ar.edu.xyris.smartdrinks.messages;


/**
 * Clase Base de Requests 
 */
public class BaseMessageRequest {
	private String idDispositivo;
	private String fechaHoraPeticion;
	
	public String getIdDispositivo() {
		return idDispositivo;
	}
	public void setIdDispositivo(String idDispositivo) {
		this.idDispositivo = idDispositivo;
	}
	public String getFechaHoraPeticion() {
		return fechaHoraPeticion;
	}
	public void setFechaHoraPeticion(String fechaHoraPeticion) {
		this.fechaHoraPeticion = fechaHoraPeticion;
	}
}
