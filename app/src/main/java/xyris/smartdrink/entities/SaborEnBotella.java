package xyris.smartdrink.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SaborEnBotella {
	private String idSabor;
	private String descripcion;

	public SaborEnBotella(String idSabor, String descripcion){
		this.idSabor = idSabor;
		this.descripcion = descripcion;
	}

	public SaborEnBotella() {

    }

	public String getIdSabor() {
		return idSabor;
	}
	public void setIdSabor(String idSabor) {
		this.idSabor = idSabor;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

    public ArrayList<SaborEnBotella> parsearSaborEnBotella (String response) {
	    JSONObject responseReader;
        ArrayList<SaborEnBotella> listSaboresEnBotella = new ArrayList<SaborEnBotella>();

        try {
            responseReader = new JSONObject(response);
            String codigoError = responseReader.getString("codigoError");

            if("0".equals(codigoError.toString())){
                // Se obtiene el nodo del array "sabores"
                JSONArray sabores = responseReader.getJSONArray("sabores");

                // Ciclando en todos los sabores
                for (int i = 0; i < sabores.length(); i++) {
                    JSONObject sabor = sabores.getJSONObject(i);
                    String idSabor = sabor.getString("idSabor");
                    String descripcion = sabor.getString("descripcion");

                    SaborEnBotella saborEnBotella = new SaborEnBotella(idSabor, descripcion);
                    listSaboresEnBotella.add(saborEnBotella);
                }
            } else {

            }

        } catch (JSONException e) { e.printStackTrace(); }

        return listSaboresEnBotella;
    }
}