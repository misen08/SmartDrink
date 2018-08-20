package xyris.smartdrink.entities;

import java.util.List;

public class Bebida {
	private String idBebida;
	private String descripcion;
	private String disponible;
	private List<SaborEnBebida> sabores;

	public Bebida(String idBebida, String descripcion, String disponible, List<SaborEnBebida> sabores) {
		this.idBebida = idBebida;
		this.descripcion = descripcion;
		this.disponible = disponible;
		this.sabores = sabores;
	}

	public String getIdBebida() {
		return idBebida;
	}
	public void setIdBebida(String idBebida) {
		this.idBebida = idBebida;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDisponible() {
		return disponible;
	}
	public void setDisponible(String disponible) {
		this.disponible = disponible;
	}
	public List<SaborEnBebida> getSabores() {
		return sabores;
	}
	public void setSabores(List<SaborEnBebida> sabores) {
		this.sabores = sabores;
	}
}
