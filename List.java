package com.mycompany.ahorro_seguro;

public class List {

    private Node first;

    public List() {
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void AddFirst(Object data) {
        if (isEmpty()) {
            first = new Node(data);
        } else {
            Node n = new Node(data);
            n.setLink(first);
            first = n;
        }
    }

    public String toString() {
        String text = "";
        Node aux = first;
        while (aux != null) {
            text = text + aux.getData() + "\n";
            aux = aux.getLink();
        }
        return text;
    }

    public Node Last() {
        Node aux = first;
        while (aux != null && aux.getLink() != null) {
            aux = aux.getLink();
        }
        return aux;
    }

    public void AddLast(Object data) {
        if (isEmpty()) {
            AddFirst(data);
        } else {
            Last().setLink(new Node(data));
        }
    }

    public int Size() {
        int count = 0;
        Node aux = first;
        while (aux != null) {
            count++;
            aux = aux.getLink();
        }
        return count;
    }

    public void AddPos(Object data, int pos) {
        if (pos == 1) {
            AddFirst(data);
        } else {
            if (pos == Size() + 1) {
                AddLast(data);
            } else {
                int count = 1;
                Node aux = first;
                Node pre = null;
                while (aux != null && count != pos) {
                    pre = aux;
                    aux = aux.getLink();
                    count++;
                }
                Node n = new Node(data);
                n.setLink(aux);
                pre.setLink(n);
            }
        }

    }

    public boolean DeleteFirst() {
        if (!isEmpty()) {
            first = first.getLink();
            return true;
        }
        return false;
    }

    public Node Search(String clienteID) {

        Node aux = first; // Comienza desde el primer nodo

        while (aux != null) {

            // Verifica si el dato del nodo actual es una instancia de Cliente
            if (aux.getData() instanceof Cliente) {

                Cliente cliente = (Cliente) aux.getData(); // Realiza el casting a Cliente

                if (cliente.getClienteID() == clienteID) {

                    return aux; // Devuelve el nodo si se encuentra

                }

            }

            aux = aux.getLink(); // Avanza al siguiente nodo

        }

        return null; // Devuelve null si no se encuentra el cliente
    }

    }
