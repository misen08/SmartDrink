package xyris.smartdrink.entities;

public class SaborEnBotella {
	private String idSabor;
	private String descripcion;
	private String habilitado;


	public SaborEnBotella(String idSabor, String descripcion, String habilitado){
		this.idSabor = idSabor;
		this.descripcion = descripcion;
		this.habilitado = habilitado;
	}

	
	public String getIdSabor() {
		return idSabor;
	}
	public void setIdSabor(String idSabor) {
		this.idSabor = idSabor;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getHabilitado() {
		return habilitado;
	}
	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
	}
}
