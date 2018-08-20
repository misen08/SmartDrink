package xyris.smartdrink.messages.crear;

import xyris.smartdrink.entities.Bebida;
import xyris.smartdrink.messages.BaseMessageRequest;

public class CreaBebidaRequest extends BaseMessageRequest{
	private Bebida bebida;

	public Bebida getBebida() {
		return bebida;
	}

	public void setBebida(Bebida bebida) {
		this.bebida = bebida;
	}
}
