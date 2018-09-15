package xyris.smartdrink.entities;

public class Botella {
	private String idBotella;
	private String idSabor;

	public Botella(String idBotella, String idSabor) {
		this.idBotella = idBotella;
		this.idSabor = idSabor;
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
}
