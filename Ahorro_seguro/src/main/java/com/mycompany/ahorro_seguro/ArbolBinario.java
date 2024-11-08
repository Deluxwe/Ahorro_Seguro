package com.mycompany.ahorro_seguro;

public class ArbolBinario {

    private BinaryNode root;

    public ArbolBinario() {
        this.root = null;
    }

    public BinaryNode getRoot() {
        return root;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void Add(CreditoVigente data) {
        root = AddArb(data, root);
    }

    private BinaryNode AddArb(CreditoVigente data, BinaryNode aux) {
        if (aux == null) {
            return new BinaryNode(data);
        }

        if (data.getSolicitud().getValor() > aux.getCredito().getSolicitud().getValor()) {
            aux.setIzquierdo(AddArb(data, aux.getIzquierdo()));
        } else {
            aux.setDerecho(AddArb(data, aux.getDerecho()));
        }
        return aux;
    }

    public BinaryNode SearchN(CreditoVigente value) {
        return SearchN(value, root);
    }

    private BinaryNode SearchN(CreditoVigente value, BinaryNode aux) {
        if (aux == null) {
            return null;
        }

        if (aux.getCredito().equals(value)) {
            return aux;
        }

        BinaryNode result = SearchN(value, aux.getIzquierdo());
        if (result == null) {
            result = SearchN(value, aux.getDerecho());
        }
        return result;
    }

    public boolean Delete(CreditoVigente value) {
        if (isEmpty()) {
            return false;
        }
        root = Delete(value, root);
        return true;
    }

    private BinaryNode Delete(CreditoVigente value, BinaryNode aux) {
        if (aux == null) {
            return null;
        }

        if (value.equals(aux.getCredito())) {
            // Caso 1: Nodo sin hijos
            if (aux.getIzquierdo() == null && aux.getDerecho() == null) {
                return null;
            }
            // Caso 2: Nodo con un hijo
            if (aux.getIzquierdo() == null) {
                return aux.getDerecho();
            }
            if (aux.getDerecho() == null) {
                return aux.getIzquierdo();
            }
            // Caso 3: Nodo con dos hijos
            CreditoVigente successor = MostLeft(aux.getDerecho()).getCredito();
            aux.setCredito(successor);
            aux.setDerecho(Delete(successor, aux.getDerecho()));
        } else if (value.getSolicitud().getValor() < aux.getCredito().getSolicitud().getValor()) {
            aux.setIzquierdo(Delete(value, aux.getIzquierdo()));
        } else {
            aux.setDerecho(Delete(value, aux.getDerecho()));
        }
        return aux;
    }

    private BinaryNode MostLeft(BinaryNode aux) {
        if (aux.getIzquierdo() != null) {
            return MostLeft(aux.getIzquierdo());
        }
        return aux;
    }

    public void revaluarSolicitudes(List listaCreditosVigentes) {
        revaluarRecursivo(root, listaCreditosVigentes);
    }

    private void revaluarRecursivo(BinaryNode actual, List listaCreditosVigentes) {
        if (actual != null) {
            // Revaluar las hojas izquierda y derecha primero (recorrido postorden)
            revaluarRecursivo(actual.getIzquierdo(), listaCreditosVigentes);
            revaluarRecursivo(actual.getDerecho(), listaCreditosVigentes);

            // Verificar si el nodo tiene un único hijo
            if ((actual.getIzquierdo() == null && actual.getDerecho() != null) || 
                (actual.getIzquierdo() != null && actual.getDerecho() == null)) {
                
                // Aprobar el crédito y moverlo a la lista de créditos vigentes
                listaCreditosVigentes.agregarCredito(actual.getCredito());

                // Eliminar el nodo del árbol (reemplazarlo por su hijo)
                if (actual.getIzquierdo() != null) {
                    reemplazarNodo(actual, actual.getIzquierdo());
                } else {
                    reemplazarNodo(actual, actual.getDerecho());
                }
            }
        }
    }

    private void reemplazarNodo(BinaryNode nodoEliminar, BinaryNode nodoReemplazo) {
        nodoEliminar.setCredito(nodoReemplazo.getCredito());
        nodoEliminar.setIzquierdo(nodoReemplazo.getIzquierdo());
        nodoEliminar.setDerecho(nodoReemplazo.getDerecho());
    }
}

    /**
     * private NodoArbol raiz;
     *
     * public ArbolBinario() { this.raiz = null; }
     *
     *
     * public void insertar(CreditoVigente credito) { NodoArbol nuevoNodo = new
     * NodoArbol(credito); if (raiz == null) { raiz = nuevoNodo; } else {
     * insertar(raiz, nuevoNodo); } }
     *
     * private void insertar(NodoArbol actual, NodoArbol nuevoNodo) { if
     * (nuevoNodo.getCredito().getSolicitud().getValor() >
     * actual.getCredito().getSolicitud().getValor()) {
     *
     * if (actual.getIzquierdo() == null) { actual.getIzquierdo() = nuevoNodo; }
     * else { insertarRecursivo(actual.getIzquierdo(), nuevoNodo); } } else { //
     * Insertar a la derecha if (actual.getDerecho()== null) {
     * actual.getDerecho() = nuevoNodo; } else { insertar(actual.getDerecho(),
     * nuevoNodo); } } }
     *
     * // Método para revaluar las solicitudes en el árbol y aprobar las que
     * tengan un único hijo public void revaluarSolicitudes(List
     * listaCreditosVigentes) { revaluarRecursivo(raiz, listaCreditosVigentes);
     * }
     *
     * private void revaluarRecursivo(NodoArbol actual, List
     * listaCreditosVigentes) { if (actual != null) { // Revalúa las hojas
     * izquierda y derecha primero (recorrido postorden)
     * revaluarRecursivo(actual.izquierdo, listaCreditosVigentes);
     * revaluarRecursivo(actual.derecho, listaCreditosVigentes);
     *
     * // Verificar si el nodo tiene un único hijo if ((actual.izquierdo ==
     * null && actual.derecho != null) || (actual.izquierdo != null &&
     * actual.derecho == null)) { // Aprobar el crédito y moverlo a la lista de
     * créditos vigentes listaCreditosVigentes.agregarCredito(actual.credito);
     *
     * // Eliminar el nodo del árbol (reemplazarlo por su hijo) if
     * (actual.izquierdo != null) { reemplazarNodo(actual, actual.izquierdo); }
     * else if (actual.derecho != null) { reemplazarNodo(actual,
     * actual.derecho); } } } }
     *
     * private void reemplazarNodo(NodoArbol nodoEliminar, NodoArbol
     * nodoReemplazo) { nodoEliminar.credito = nodoReemplazo.credito;
     * nodoEliminar.izquierdo = nodoReemplazo.izquierdo; nodoEliminar.derecho =
     * nodoReemplazo.derecho; }
     */

