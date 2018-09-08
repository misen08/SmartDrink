package ar.edu.xyris.smartdrinks.messages.preparacion;

public class ModificaPedidoRequest extends PreparaBebidaRequest {
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
