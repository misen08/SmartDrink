package xyris.smartdrink.messages.preparar;

import xyris.smartdrink.entities.PedidoBebida;
import xyris.smartdrink.messages.BaseMessageRequest;

/**
 * Clase para request de preparacion de bebida 
 */
public class PreparaBebidaRequest extends BaseMessageRequest {
	private PedidoBebida peidoBebida;
	
	public PedidoBebida getPeidoBebida() {
		return peidoBebida;
	}
	public void setPeidoBebida(PedidoBebida peidoBebida) {
		this.peidoBebida = peidoBebida;
	}
}
