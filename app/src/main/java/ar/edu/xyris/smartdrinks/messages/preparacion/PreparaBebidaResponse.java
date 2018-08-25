package ar.edu.xyris.smartdrinks.messages.preparacion;

import ar.edu.xyris.smartdrinks.messages.BaseMessageResponse;

public class PreparaBebidaResponse extends BaseMessageResponse{
	private String idPedido;

	/**
	 * @return the idBebida
	 */
	public String getIdPedido() {
		return idPedido;
	}

	/**
	 * @param idPedido the idBebida to set
	 */
	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}
}
