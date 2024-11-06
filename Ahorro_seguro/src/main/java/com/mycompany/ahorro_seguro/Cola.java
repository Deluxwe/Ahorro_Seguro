package com.mycompany.ahorro_seguro;

public class Cola {

    private Node first;//donde sacamos
    private Node last; //donde metemos

    public Cola() {
    }

    public boolean isEmpty() {
        return first == null && last == null;
    }

    public void EnqueueSolic(Cola solicitud) {
        Node nuevoNodo = new Node(solicitud);
        if (first == null) {
            first = last = nuevoNodo;
        } else {
            last.setLink(nuevoNodo);
            last = nuevoNodo;
        }
    }

    public void Enqueue(Object data) {
        if (isEmpty()) {
            first = new Node(data);
            last = first;
        } else {
            Node n = new Node(data);
            last.setLink(n);
            last = n;
        }
    }

    public Object Dequeue() {
        Object data = null;
        if (!isEmpty()) {
            data = first.getData();
            first = first.getLink();
            if (first == null) {
                last = null;
            }
        }
        return data;
    }

    public Credito DequeueSolic() {
        if (first == null) {
            return null;
        }
        Credito solicitud = (Credito) first.getData();
        first = first.getLink();
        if (first == null) {
            last = null;
        }
        return solicitud;
    }

}
