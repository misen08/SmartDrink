package ar.edu.xyris.smartdrinks.messages.contador;

import ar.edu.xyris.smartdrinks.messages.BaseMessageResponse;

public class ConsultaContadorResponse extends BaseMessageResponse {
	private String cantidadBebidasPreparadas;

	/**
	 * @return the cantidadBebidasPreparadas
	 */
	public String getCantidadBebidasPreparadas() {
		return cantidadBebidasPreparadas;
	}

	/**
	 * @param cantidadBebidasPreparadas the cantidadBebidasPreparadas to set
	 */
	public void setCantidadBebidasPreparadas(String cantidadBebidasPreparadas) {
		this.cantidadBebidasPreparadas = cantidadBebidasPreparadas;
	}
	
	
}
