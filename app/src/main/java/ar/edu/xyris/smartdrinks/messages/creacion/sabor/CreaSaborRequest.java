package ar.edu.xyris.smartdrinks.messages.creacion.sabor;

import ar.edu.xyris.smartdrinks.messages.BaseMessageRequest;

public class CreaSaborRequest extends BaseMessageRequest {
	private String descripcion;

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
