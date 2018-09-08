package xyris.smartdrink.entities;

public class PedidoAgendado extends PedidoBebida {
    private String idPedido;
    private String descripcion;

    public PedidoAgendado(String idBebida, String descripcionBebida, String hielo, String agitado,
                          String agendado, String fechaHoraAgendado, String idPedido, String descripcion) {
        super(idBebida, hielo, agitado, agendado, fechaHoraAgendado);
        this.idPedido = idPedido;
        this.descripcion = descripcion;
    }

    public PedidoAgendado(String idPedido, String idBebida, String hielo, String agitado,
                          String agendado, String fechaHoraAgendado) {
        super(idBebida, hielo, agitado, agendado, fechaHoraAgendado);
        this.idPedido = idPedido;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the idPedido
     */
    public String getIdPedido() {
        return idPedido;
    }

    /**
     * @param idPedido the idPedido to set
     */
    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }
}