package xyris.smartdrink.entities;

public class PedidoBebida {
	private String idBebida;
	private String hielo;
	private String agitado;
	private String agendado;
	private String fechaHoraAgendado;

	public PedidoBebida(String idBebida, String hielo, String agitado, String agendado, String fechaHoraAgendado) {
		this.idBebida = idBebida;
		this.hielo = hielo;
		this.agitado = agitado;
		this.agendado = agendado;
		this.fechaHoraAgendado = fechaHoraAgendado;
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
	
}
