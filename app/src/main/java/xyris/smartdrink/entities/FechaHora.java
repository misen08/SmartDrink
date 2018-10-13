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

    public void FechaHora(String dia, String mes, String anio, String hora, String minutos) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.minutos = minutos;
    }

    public void FechaHora() {

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

    public String fechaHoraFormateada(String fechaIngresada, String horaIngresada){

        String[] f = fechaIngresada.split("/");
        String dia = f[0]; // 17
        String mes = f[1]; // 08
        String anio = f[2]; // 2018

        String[] h = horaIngresada.split(":");
        String hh = h[0]; // 14
        String mm = h[1]; // 53
        String fechaHora = dia + "-" + mes + "-" + anio + "T" +  hh + ":" + mm + ":00";

        return fechaHora;
    }

    public String formatDate(Date date) {
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return df.format(date);
    }

    public String formatDateMantenimiento(Date date) {
        df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }

}
