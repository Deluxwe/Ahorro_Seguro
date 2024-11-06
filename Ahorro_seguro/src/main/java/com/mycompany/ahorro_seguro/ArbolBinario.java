
package com.mycompany.ahorro_seguro;


public class ArbolBinario {
     private NodoArbol raiz;

    public ArbolBinario() {
        this.raiz = null;
    }


    public void insertar(CreditoVigente credito) {
        NodoArbol nuevoNodo = new NodoArbol(credito);
        if (raiz == null) {
            raiz = nuevoNodo;
        } else {
            insertar(raiz, nuevoNodo);
        }
    }

    private void insertar(NodoArbol actual, NodoArbol nuevoNodo) {
        if (nuevoNodo.getCredito().getSolicitud().getValor() > actual.getCredito().getSolicitud().getValor()) {

            if (actual.getIzquierdo() == null) {
                actual.izquierdo = nuevoNodo;
            } else {
                insertar(actual.izquierdo, nuevoNodo);
            }
        } else {

            if (actual.getDerecho() == null) {
                actual.derecho = nuevoNodo;
            } else {
                insertar(actual.derecho, nuevoNodo);
            }
        }
    }


    public void revaluarSolicitudes(List listaCreditosVigentes) {
        revaluarSolicitudes(raiz, listaCreditosVigentes);
    }

    private void revaluarSolicitudes(NodoArbol actual, List listaCreditosVigentes) {
        if (actual != null) {
            // Revalúa las hojas izquierda y derecha primero (recorrido postorden)
            revaluarSolicitudes(actual.getIzquierdo(), listaCreditosVigentes);
            revaluarSolicitudes(actual.getDerecho(), listaCreditosVigentes);

            // Verificar si el nodo tiene un único hijo
            if ((actual.getIzquierdo() == null && actual.getDerecho() != null) || (actual.getIzquierdo() != null && actual.getDerecho() == null)) {
                // Aprobar el crédito y moverlo a la lista de créditos vigentes
                listaCreditosVigentes.agregarCredito(actual.credito);

                // Eliminar el nodo del árbol (reemplazarlo por su hijo)
                if (actual.getIzquierdo() != null) {
                    reemplazarNodo(actual, actual.getIzquierdo());
                } else if (actual.getDerecho() != null) {
                    reemplazarNodo(actual, actual.getDerecho());
                }
            }
        }
    }

    private void reemplazarNodo(NodoArbol nodoEliminar, NodoArbol nodoReemplazo) {
        nodoEliminar.getCredito() = nodoReemplazo.credito;
        nodoEliminar.getIzquierdo()  = nodoReemplazo.izquierdo;
        nodoEliminar.getDerecho() = nodoReemplazo.derecho;
    }
    
    
}
