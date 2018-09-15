package xyris.smartdrink.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PedidoBebida {
	private String idBebida;
	private String hielo;
	private String agitado;
	private String agendado;
	private String fechaHoraAgendado;

	public PedidoBebida(String idBebida,
						String hielo, String agitado,
						String agendado, String fechaHoraAgendado) {
		this.idBebida = idBebida;
		this.hielo = hielo;
		this.agitado = agitado;
		this.agendado = agendado;
		this.fechaHoraAgendado = fechaHoraAgendado;
	}

	public PedidoBebida() {

	}

	public String getIdBebida() {
		return idBebida;
	}
	public void setIdBebida(String idBebida) {
		this.idBebida = idBebida;
	}

	public String getHielo() {
		return hielo;
	}
	public void setHielo(String hielo) {
		this.hielo = hielo;
	}
	public String getAgitado() {
		return agitado;
	}
	public void setAgitado(String agitado) {
		this.agitado = agitado;
	}
	public String getAgendado() {
		return agendado;
	}
	public void setAgendado(String agendado) {
		this.agendado = agendado;
	}
	public String getFechaHoraAgendado() {
		return fechaHoraAgendado;
	}
	public void setFechaHoraAgendado(String fechaHoraAgendado) {
		this.fechaHoraAgendado = fechaHoraAgendado;
	}


	public ArrayList<PedidoAgendado> parsearBebidasAgendadas (String response) {

		JSONObject responseReader;

		ArrayList<PedidoAgendado> listBebidasAgendadas = new ArrayList<PedidoAgendado>();

		try {
			responseReader = new JSONObject(response);
			String codigoError = responseReader.getString("codigoError");

			if("0".equals(codigoError.toString())){
				// Se obtiene el nodo del array "pedidoBebida"
				JSONArray pedidoAgendado = responseReader.getJSONArray("pedidos");

				// Ciclando en todos los pedidos de bebida agendados
				for (int i = 0; i < pedidoAgendado.length(); i++) {
					String hielo="";
					String agitado="";
					JSONObject bebidaAgendada = pedidoAgendado.getJSONObject(i);
					String idPedido = bebidaAgendada.getString("idPedido");
					String idBebida = bebidaAgendada.getString("idBebida");
					String descripcionBebida = bebidaAgendada.getString("descripcion");
					if("true".equals(bebidaAgendada.getString("hielo").toString())){
						hielo = "Con hielo";
					} else {
						hielo = "Sin hielo";
					}
					if("true".equals(bebidaAgendada.getString("agitado").toString())){
						agitado = "Agitado";
					} else {
						agitado = "Sin agitar";
					}
					String agendado = bebidaAgendada.getString("agendado");
					String fechaHoraAgendado = bebidaAgendada.getString("fechaHoraAgendado");

					PedidoAgendado bebida = new PedidoAgendado(idBebida, descripcionBebida, hielo, agitado, agendado, fechaHoraAgendado, idPedido, descripcionBebida);

					listBebidasAgendadas.add(bebida);
				}
			} else {
				// TODO: manejar codigos de error de consultarPedidos
			}

		} catch (JSONException e) { e.printStackTrace(); }

		return listBebidasAgendadas;
	}
}
