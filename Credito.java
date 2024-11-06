
package com.mycompany.ahorro_seguro;



public class Credito {
    private int códigoCredito;
    private String tipo;
    private int titular;
    private String Fecha;
    private double valor;
    private int nCuotas;


    public Credito(int códigoCredito, String tipo, int titular, String Fecha, double valor, int nCuotas) {
        this.códigoCredito = códigoCredito;
        this.tipo = tipo;
        this.titular = titular;
        this.Fecha = Fecha;
        this.valor = valor;
        this.nCuotas = nCuotas;
    }


    public int getCódigoCredito() {
        return códigoCredito;
    }

    public void setCódigoCredito(int códigoCredito) {
        this.códigoCredito = códigoCredito;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTitular() {
        return titular;
    }

    public void setTitular(int titular) {
        this.titular = titular;
    }



    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getnCuotas() {
        return nCuotas;
    }

    public void setnCuotas(int nCuotas) {
        this.nCuotas = nCuotas;
    }

    @Override
    public String toString() {
        return "Credito{" + "c\u00f3digoCredito=" + códigoCredito + ", tipo=" + tipo + ", titular=" + titular + ", Fecha=" + Fecha + ", valor=" + valor + ", nCuotas=" + nCuotas + '}';
    }


    
}
