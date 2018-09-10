package xyris.smartdrink.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SaborEnBotella {
	private String idSabor;
	private String descripcion;
	private String habilitado;

	public SaborEnBotella(String idSabor, String descripcion, String habilitado){
		this.idSabor = idSabor;
		this.descripcion = descripcion;
		this.habilitado = habilitado;
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
	public String getHabilitado() {
		return habilitado;
	}
	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
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
                    String habilitado = sabor.getString("habilitado");

                    SaborEnBotella saborEnBotella = new SaborEnBotella(idSabor, descripcion, habilitado);
                    listSaboresEnBotella.add(saborEnBotella);
                }
            } else {
                // TODO: manejar codigos de error de consultarSabores
            }

        } catch (JSONException e) { e.printStackTrace(); }

        return listSaboresEnBotella;
    }
}