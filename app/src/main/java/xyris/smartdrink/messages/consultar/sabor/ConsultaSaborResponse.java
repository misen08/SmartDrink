package xyris.smartdrink.messages.consultar.sabor;

import java.util.List;

import xyris.smartdrink.entities.SaborEnBotella;
import xyris.smartdrink.messages.BaseMessageResponse;

public class ConsultaSaborResponse extends BaseMessageResponse {
	private List<SaborEnBotella> sabores;

	public List<SaborEnBotella> getSabores() {
		return sabores;
	}

	public void setSabores(List<SaborEnBotella> sabores) {
		this.sabores = sabores;
	}
}
