package com.mycompany.ahorro_seguro;

import javax.swing.JOptionPane;

public class Ahorro_seguro {

    public static void main(String[] args) {
        List list = new List();
        Cola colaCliente = new Cola();
        int code = 000001;

        String menu[] = {"Registrar Cliente", "Solicitud de credito", "Aprobar o negar una solicitud de crédito", "Add",
            "toString", "Last", "Size",
            "DeleteFirst", "DeleteLast", "DeletePos", "Exit"};
        String option;
        do {
            option = (String) JOptionPane.showInputDialog(null, "Select", "Menu", 1, null, menu, menu[0]);
            switch (option) {
                case "Registrar Cliente":
                    String nombre = JOptionPane.showInputDialog("Nombre del cliente a registrar");
                    String clienteID = JOptionPane.showInputDialog("Digite el ID del Cliente");
                    String tipo = JOptionPane.showInputDialog("Persona Natural/Juridico");
                    String direccion = JOptionPane.showInputDialog("Direccion del cliente");
                    String telefono = JOptionPane.showInputDialog("Telefono");
                    String tContrato = JOptionPane.showInputDialog("Tipo de contrato");
                    double salarioM = Double.parseDouble(JOptionPane.showInputDialog("Salario mensual del cliente"));

                    Cliente nCliente = new Cliente(clienteID, tipo, nombre, direccion, telefono, tContrato, salarioM);
                    list.AddFirst(nCliente);

                    JOptionPane.showMessageDialog(null, "Cliente Registrado");

                    break;

                case "Solicitud de credito":

                    String tipoT = JOptionPane.showInputDialog("tarjeta, libre inversión, nómina o/e hipotecario");
                    int idTitular = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del cliente titular"));
                    Cliente titular = List.buscarClientePorId(idTitular);

                    if (titular == null) {
                        JOptionPane.showMessageDialog(null, "Cliente no encontrado");
                        return null;
                    }
                    String fecha = JOptionPane.showInputDialog("Fecha en DD/MM/AAAA");
                    double valor = Double.parseDouble(JOptionPane.showInputDialog("Digite el valor del prestamo"));
                    int NumeroCuota = Integer.parseInt(JOptionPane.showInputDialog("Digite el numero de cuotas"));

                    Credito nCredito = new Credito(code, tipoT, titular, fecha, valor, NumeroCuota);
                    colaCliente.Enqueue(nCredito);

                    JOptionPane.showMessageDialog(null, "Credito añadido a cola");
                    code += 1;

                    break;

                case "Aprobar o negar una solicitud de crédito":
                    String titulaA = JOptionPane.showInputDialog("ID del titular");

                    CreditoFin(list, colaCliente, titulaA);

                    break;

                case "Size":

                    break;

                case "AddPos":

                    break;
                case "DeleteFirst":

                    break;
                case "DeleteLast":

                    break;

            }

        } while (!option.equals("Exit"));

    }

    public static Cliente buscarCliente(List clientes, String id) {
        if (!clientes.isEmpty()) {
            Node aux = clientes.Search(id);
            while (aux != null && !((Cliente) aux.getData()).getClienteID().equals(id)) {
                aux = aux.getLink();
            }
            if (aux != null) {
                return (Cliente) aux.getData();
            }
        }
        return null;
    }

    public static void CreditoFin(List l, Cola n) {
        Cliente z = n.Dequeue();
        List k = buscarCliente(l, );

    }

}

// math.random()*4+28

