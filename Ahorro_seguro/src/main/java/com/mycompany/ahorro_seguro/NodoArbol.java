
package com.mycompany.ahorro_seguro;


public class NodoArbol {

    public CreditoVigente credito;
    public NodoArbol izquierdo;
    public NodoArbol derecho;

    public NodoArbol(CreditoVigente credito) {
        this.credito = credito;
        this.izquierdo = null;
        this.derecho = null;
    }
}

