package ar.edu.xyris.smartdrinks.messages.consulta.botella;

import java.util.List;

import ar.edu.xyris.smartdrinks.messages.BaseMessageResponse;
import xyris.smartdrink.entities.BotellaExt;

public class ConsultaBotellasResponse extends BaseMessageResponse {
	private List<BotellaExt> botellas;

	/**
	 * @return the botellas
	 */
	public List<BotellaExt> getBotellas() {
		return botellas;
	}

	/**
	 * @param botellas the botellas to set
	 */
	public void setBotellas(List<BotellaExt> botellas) {
		this.botellas = botellas;
	}
	
	
	
}
