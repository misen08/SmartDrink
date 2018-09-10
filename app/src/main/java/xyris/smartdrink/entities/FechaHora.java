package xyris.smartdrink.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FechaHora {
    private String dia;
    private String mes;
    private String anio;
    private String hora;
    private String minutos;
    private SimpleDateFormat df;

    public void FechaHora(String dia, String mes, String anio, String hora, String minutos){
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.minutos = minutos;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMinutos() {
        return minutos;
    }

    public void setMinutos(String minutos) {
        this.minutos = minutos;
    }

    public String formatDate(Date date) {
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return df.format(date);
    }
}
