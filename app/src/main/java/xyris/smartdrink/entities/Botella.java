package xyris.smartdrink.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Botella {
	private String idBotella;
	private String idSabor;
	private String disponible;

	public Botella(String idBotella, String idSabor, String disponible) {
		this.idBotella = idBotella;
		this.idSabor = idSabor;
		this.disponible = disponible;
	}

	public Botella() {

	}

    /**
	 * @return the idBotella
	 */
	public String getIdBotella() {
		return idBotella;
	}
	/**
	 * @param idBotella the idBotella to set
	 */
	public void setIdBotella(String idBotella) {
		this.idBotella = idBotella;
	}
	/**
	 * @return the idSabor
	 */
	public String getIdSabor() {
		return idSabor;
	}
	/**
	 * @param idSabor the idSabor to set
	 */
	public void setIdSabor(String idSabor) {
		this.idSabor = idSabor;
	}

	/**
	 * @return the disponible
	 */
	public String getDisponible() {
		return disponible;
	}
	/**
	 * @param disponible the disponible to set
	 */
	public void setDisponible(String disponible) {
		this.disponible = disponible;
	}

	public ArrayList<Botella> parsearSabor (String response) {
		JSONObject responseReader;
		ArrayList<Botella> listSaboresEnBotella = new ArrayList<Botella>();

		try {
			responseReader = new JSONObject(response);
			String codigoError = responseReader.getString("codigoError");

			if("0".equals(codigoError.toString())){
				// Se obtiene el nodo del array "sabores"
				JSONArray saboresEnBotella = responseReader.getJSONArray("botellas");

				// Ciclando en todos los sabores
				for (int i = 0; i < saboresEnBotella.length(); i++) {
					JSONObject sabor = saboresEnBotella.getJSONObject(i);
					String idBotella = sabor.getString("idBotella");
					String idSabor = sabor.getString("idSabor");
					String habilitado = sabor.getString("disponible");

					Botella botella = new Botella(idBotella, idSabor, habilitado);
					listSaboresEnBotella.add(botella);
				}
			} else {
				// TODO: manejar codigos de error de consultarSabores
			}

		} catch (JSONException e) { e.printStackTrace(); }

		return listSaboresEnBotella;
	}

}
