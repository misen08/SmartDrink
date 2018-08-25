package ar.edu.xyris.smartdrinks.messages.preparacion;

import ar.edu.xyris.smartdrinks.messages.BaseMessageRequest;

public class CancelaPedidoRequest extends BaseMessageRequest{
	private String idPedido;

	/**
	 * @return the idPedido
	 */
	public String getIdPedido() {
		return idPedido;
	}

	/**
	 * @param idPedido the idPedido to set
	 */
	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}
}
