package ar.edu.xyris.smartdrinks.messages.consulta.sabor;

import java.util.List;

import ar.edu.xyris.smartdrinks.messages.BaseMessageResponse;
import xyris.smartdrink.entities.SaborEnBotella;

public class ConsultaSaborResponse extends BaseMessageResponse {
	private List<SaborEnBotella> sabores;

	public List<SaborEnBotella> getSabores() {
		return sabores;
	}

	public void setSabores(List<SaborEnBotella> sabores) {
		this.sabores = sabores;
	}
}
