package ar.edu.xyris.smartdrinks.messages.creacion.bebida;

import java.util.List;

import ar.edu.xyris.smartdrinks.messages.BaseMessageResponse;
import xyris.smartdrink.entities.Bebida;

public class CreaBebidaResponse extends BaseMessageResponse {
	private List<Bebida> bebidas;

	public List<Bebida> getBebidas() {
		return bebidas;
	}

	public void setBebidas(List<Bebida> bebidas) {
		this.bebidas = bebidas;
	}
}
