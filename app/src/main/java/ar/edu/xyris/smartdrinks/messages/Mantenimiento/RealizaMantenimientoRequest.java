package ar.edu.xyris.smartdrinks.messages.Mantenimiento;

import ar.edu.xyris.smartdrinks.messages.BaseMessageRequest;
import xyris.smartdrink.entities.Bebida;

public class RealizaMantenimientoRequest extends BaseMessageRequest {

    private Boolean mantenimientoRealizado;

    public Boolean getMantenimientoRealizado() {
        return mantenimientoRealizado;
    }

    public void setMantenimientoRealizado(Boolean mantenimientoRealizado) {
        this.mantenimientoRealizado = mantenimientoRealizado;
    }
}
