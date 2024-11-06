package com.mycompany.ahorro_seguro;

public class Cuota {

    private double valor;
    private String estado; // "pendiente" o "pagada"

    public Cuota(double valor) {
        this.valor = valor;
        this.estado = "pendiente"; // Todas las cuotas inician como pendientes
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

