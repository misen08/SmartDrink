package ar.edu.xyris.smartdrinks.messages.asignacion;

import java.util.List;

import ar.edu.xyris.smartdrinks.messages.BaseMessageRequest;
import xyris.smartdrink.entities.Botella;

public class AsignaBotellaRequest extends BaseMessageRequest {
	List<Botella> botellas;

	/**
	 * @return the botellas
	 */
	public List<Botella> getBotellas() {
		return botellas;
	}

	/**
	 * @param botellas the botellas to set
	 */
	public void setBotellas(List<Botella> botellas) {
		this.botellas = botellas;
	}
}
