
package com.mycompany.ahorro_seguro;


public class ArbolBinario {
     private NodoArbol raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

    // Método para insertar una solicitud en el árbol binario
    public void insertar(CreditoVigente credito) {
        NodoArbol nuevoNodo = new NodoArbol(credito);
        if (raiz == null) {
            raiz = nuevoNodo;
        } else {
            insertarRecursivo(raiz, nuevoNodo);
        }
    }

    private void insertarRecursivo(NodoArbol actual, NodoArbol nuevoNodo) {
        if (nuevoNodo.credito.getSolicitud().getValor() > actual.credito.getSolicitud().getValor()) {
            // Insertar a la izquierda
            if (actual.izquierdo == null) {
                actual.izquierdo = nuevoNodo;
            } else {
                insertarRecursivo(actual.izquierdo, nuevoNodo);
            }
        } else {
            // Insertar a la derecha
            if (actual.derecho == null) {
                actual.derecho = nuevoNodo;
            } else {
                insertarRecursivo(actual.derecho, nuevoNodo);
            }
        }
    }

    // Método para revaluar las solicitudes en el árbol y aprobar las que tengan un único hijo
    public void revaluarSolicitudes(List listaCreditosVigentes) {
        revaluarRecursivo(raiz, listaCreditosVigentes);
    }

    private void revaluarRecursivo(NodoArbol actual, List listaCreditosVigentes) {
        if (actual != null) {
            // Revalúa las hojas izquierda y derecha primero (recorrido postorden)
            revaluarRecursivo(actual.izquierdo, listaCreditosVigentes);
            revaluarRecursivo(actual.derecho, listaCreditosVigentes);

            // Verificar si el nodo tiene un único hijo
            if ((actual.izquierdo == null && actual.derecho != null) || (actual.izquierdo != null && actual.derecho == null)) {
                // Aprobar el crédito y moverlo a la lista de créditos vigentes
                listaCreditosVigentes.agregarCredito(actual.credito);

                // Eliminar el nodo del árbol (reemplazarlo por su hijo)
                if (actual.izquierdo != null) {
                    reemplazarNodo(actual, actual.izquierdo);
                } else if (actual.derecho != null) {
                    reemplazarNodo(actual, actual.derecho);
                }
            }
        }
    }

    private void reemplazarNodo(NodoArbol nodoEliminar, NodoArbol nodoReemplazo) {
        nodoEliminar.credito = nodoReemplazo.credito;
        nodoEliminar.izquierdo = nodoReemplazo.izquierdo;
        nodoEliminar.derecho = nodoReemplazo.derecho;
    }
    
    
}
