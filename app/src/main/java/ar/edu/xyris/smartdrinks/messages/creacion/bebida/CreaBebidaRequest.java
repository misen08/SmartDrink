package ar.edu.xyris.smartdrinks.messages.creacion.bebida;

import ar.edu.xyris.smartdrinks.messages.BaseMessageRequest;
import xyris.smartdrink.entities.Bebida;

public class CreaBebidaRequest extends BaseMessageRequest{
	private Bebida bebida;

	public Bebida getBebida() {
		return bebida;
	}

	public void setBebida(Bebida bebida) {
		this.bebida = bebida;
	}
}
