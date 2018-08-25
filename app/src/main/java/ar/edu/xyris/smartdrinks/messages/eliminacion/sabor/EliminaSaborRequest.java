package ar.edu.xyris.smartdrinks.messages.eliminacion.sabor;

import ar.edu.xyris.smartdrinks.messages.BaseMessageRequest;

public class EliminaSaborRequest extends BaseMessageRequest {
	private String idSabor;

	/**
	 * @return the idSabor
	 */
	public String getIdSabor() {
		return idSabor;
	}

	/**
	 * @param idSabor the idSabor to set
	 */
	public void setIdSabor(String idSabor) {
		this.idSabor = idSabor;
	}
}
