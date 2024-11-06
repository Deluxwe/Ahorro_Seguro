
package com.mycompany.ahorro_seguro;


public class NodoArbol {
    private CreditoVigente credito;
    private NodoArbol izquierdo;
    private NodoArbol derecho;

    public NodoArbol(CreditoVigente credito) {
        this.credito = credito;
        this.izquierdo = null;
        this.derecho = null;
    }

    public CreditoVigente getCredito() {
        return credito;
    }

    public void setCredito(CreditoVigente credito) {
        this.credito = credito;
    }

    public NodoArbol getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoArbol izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoArbol getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoArbol derecho) {
        this.derecho = derecho;
    }
    
}

