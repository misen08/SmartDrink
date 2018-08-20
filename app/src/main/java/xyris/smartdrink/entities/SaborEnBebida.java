package xyris.smartdrink.entities;

public class SaborEnBebida {
	private String idSabor;
	private String descripcion;
	private String porcentaje;

	public SaborEnBebida(String idSabor, String descripcion, String porcentaje) {
		this.idSabor = idSabor;
		this.descripcion = descripcion;
		this.porcentaje = porcentaje;
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
	public String getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}
}
