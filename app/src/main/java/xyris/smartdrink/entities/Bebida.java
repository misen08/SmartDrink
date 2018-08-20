package xyris.smartdrink.entities;

import java.util.ArrayList;

public class Bebida {
	private String idBebida;
	private String descripcion;
	private String disponible;
	private ArrayList<SaborEnBebida> sabores;

	public Bebida(String idBebida, String descripcion, String disponible, ArrayList<SaborEnBebida> sabores) {
		this.idBebida = idBebida;
		this.descripcion = descripcion;
		this.disponible = disponible;
		this.sabores = sabores;
	}

//
//	public Bebida(String idBebida, String descripcion, String disponible) {
//		this.idBebida = idBebida;
//		this.descripcion = descripcion;
//		this.disponible = disponible;
//	}


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
	public ArrayList<SaborEnBebida> getSabores() {
		return sabores;
	}
	public void setSabores(ArrayList<SaborEnBebida> sabores) {
		this.sabores = sabores;
	}
}
