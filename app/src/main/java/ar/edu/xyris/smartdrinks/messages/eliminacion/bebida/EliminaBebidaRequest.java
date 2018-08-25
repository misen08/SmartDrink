package ar.edu.xyris.smartdrinks.messages.eliminacion.bebida;

import ar.edu.xyris.smartdrinks.messages.BaseMessageRequest;

public class EliminaBebidaRequest extends BaseMessageRequest {
	private String idBebida;

	/**
	 * @return the idBebida
	 */
	public String getIdBebida() {
		return idBebida;
	}

	/**
	 * @param idBebida the idBebida to set
	 */
	public void setIdBebida(String idBebida) {
		this.idBebida = idBebida;
	}
}
