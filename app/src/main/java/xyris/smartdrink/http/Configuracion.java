package xyris.smartdrink.http;

import java.net.InetAddress;

public class Configuracion {

    private static Configuracion instancia = null;
    private String ip;

    private Configuracion() {

    }

    public static Configuracion getInstance() {
        if(instancia == null){
            instancia = new Configuracion();
        }
        return instancia;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }
}
