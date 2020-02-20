package com.example.parsearjson;

public class Datos {
    String portada , number, volume;

    public Datos(String portada, String number, String volume) {
        this.portada = portada;
        this.number = number;
        this.volume = volume;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
