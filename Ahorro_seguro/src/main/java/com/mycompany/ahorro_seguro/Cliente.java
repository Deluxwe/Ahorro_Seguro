
package com.mycompany.ahorro_seguro;

public class Cliente {
    private int clienteID;
    private String tipo;
    private String nombre;
    private String direccion;
    private String telefono;
    private String  tContrato;
    private double salarioM;

    public Cliente(int clienteID, String tipo, String nombre, String direccion, String telefono, String tContrato, double salarioM) {
        this.clienteID = clienteID;
        this.tipo = tipo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.tContrato = tContrato;
        this.salarioM = salarioM;
    }

    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }



    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String gettContrato() {
        return tContrato;
    }

    public void settContrato(String tContrato) {
        this.tContrato = tContrato;
    }

    public double getSalarioM() {
        return salarioM;
    }

    public void setSalarioM(double salarioM) {
        this.salarioM = salarioM;
    }

    @Override
    public String toString() {
        return "Cliente{" + "clienteID=" + clienteID + ", tipo=" + tipo + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono + ", tContrato=" + tContrato + ", salarioM=" + salarioM + '}';
    }



    
    
    
}
