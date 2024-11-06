package com.mycompany.ahorro_seguro;

public class List {

    private Node first;

    public List() {
    }

    public Node getFirst() {
        return first;
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

    public void agregarCredito(CreditoVigente credito) {
        Node nuevoNodo = new Node(credito);
        if (first == null) {
            first = nuevoNodo;
        } else {
            Node actual = first;
            while (actual.getLink() != null) {
                actual = actual.getLink();
            }
            actual.setLink(nuevoNodo);
        }
    }

    public void agregarCuota(Cuota cuota) {
        Node nuevoNodo = new Node(cuota);
        if (first == null) {
            first = nuevoNodo;
        } else {
            Node actual = first;
            while (actual.getLink() != null) {
                actual = actual.getLink();
            }
            actual.setLink(nuevoNodo);
        }
    }

    public Cuota obtenerPrimeraCuota() {
        if (first != null) {
            return (Cuota) first.getData();
        }
        return null;
    }

    public Cliente buscarClientePorId(int id) {
        Node aux = first;
        while (aux != null) {
            Cliente cliente = (Cliente) aux.getData();
            if (cliente.getClienteID() == id) {
                return cliente;
            }
            aux = aux.getLink();
        }
        return null;
    }

    public double obtenerSumaCuotasMensuales(Cliente c) {
        double sumaCuotas = 0;
        Node actual = first;
        while (actual != null) {
            CreditoVigente credito = (CreditoVigente) actual.getData();
            if (credito.isAprobado() && credito.getSolicitud().getTitular().equals(c)) {
                Cuota primeraCuota = credito.getCuotas().obtenerPrimeraCuota();
                if (primeraCuota != null) {
                    sumaCuotas += primeraCuota.getValor();
                }
            }
            actual = actual.getLink();
        }
        return sumaCuotas;
    }

    public CreditoVigente buscarCreditoPorCodigo(int codigo) {
        Node actual = first;
        while (actual != null) {
            CreditoVigente credito = (CreditoVigente) actual.getData();
            if (credito.getSolicitud().getCódigoCredito() == codigo) {
                return credito;
            }
            actual = actual.getLink();
        }
        return null; // Si no se encuentra el crédito, retorna null
    }

}
