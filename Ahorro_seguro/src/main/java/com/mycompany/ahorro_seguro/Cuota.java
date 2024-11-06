package com.mycompany.ahorro_seguro;

public class Cuota {

    private double valor;
    private String estado;

    public Cuota(double valor) {
        this.valor = valor;
        this.estado = "pendiente";
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Cuota{valor=" + valor + ", estado=" + estado + "}";
    }
}
