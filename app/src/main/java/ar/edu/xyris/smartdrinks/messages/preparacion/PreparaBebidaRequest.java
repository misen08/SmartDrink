package ar.edu.xyris.smartdrinks.messages.preparacion;

import ar.edu.xyris.smartdrinks.messages.BaseMessageRequest;
import xyris.smartdrink.entities.PedidoBebida;

/**
 * Clase para request de preparacion de bebida 
 */
public class PreparaBebidaRequest extends BaseMessageRequest {
	private PedidoBebida pedidoBebida;
	
	public PedidoBebida getPedidoBebida() {
		return pedidoBebida;
	}
	public void setPedidoBebida(PedidoBebida pedidoBebida) {
		this.pedidoBebida = pedidoBebida;
	}
}
