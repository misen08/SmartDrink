package xyris.smartdrink.messages.consultar.bebida;

import java.util.List;

import xyris.smartdrink.entities.Bebida;
import xyris.smartdrink.messages.BaseMessageResponse;

public class ConsultaBebidaResponse extends BaseMessageResponse{
	private List<Bebida> bebidas;

	public List<Bebida> getBebidas() {
		return bebidas;
	}

	public void setBebidas(List<Bebida> bebidas) {
		this.bebidas = bebidas;
	}
}
