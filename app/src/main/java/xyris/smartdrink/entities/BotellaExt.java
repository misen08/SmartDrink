package xyris.smartdrink.entities;

public class BotellaExt extends Botella {
	private String distancia;

	public BotellaExt(String idBotella, String idSabor, String distancia, String disponible) {
		super(idBotella, idSabor, disponible);
		this.distancia = distancia;
	}

	/**
	 * @return the distancia
	 */
	public String getDistancia() {
		return distancia;
	}
	/**
	 * @param distancia the distancia to set
	 */
	public void setDistancia(String distancia) {
		this.distancia = distancia;
	}

}
