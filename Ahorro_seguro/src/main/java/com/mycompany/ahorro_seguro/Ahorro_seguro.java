package com.mycompany.ahorro_seguro;

import java.util.Date;
import javax.swing.JOptionPane;

public class Ahorro_seguro {

    public static Archivo a = new Archivo();
    private static List listaCreditos = a.LeerLista("ArchivoPlanoCreditos.txt");
    private static Stack pilaR = a.LeerPila("ArchivoRechazados.txt", listaCreditos);

    public static void main(String[] args) {

        List listRegistrados = a.LeerLista("ArchivoPlanoClientes.txt");
        Cola colaSolicitud = a.LeerCola("ArchivoSolicitudes.txt", listaCreditos);
        ArbolBinario Arbol = new ArbolBinario();

        int code = 1;

        String menu[] = {"Registrar Cliente", "Solicitud de credito", "Aprobar o negar una solicitud de crédito",
            "Pagar cuota", "Cancelar crédito", "Revaluar Solicitudes", "Guardar informacion", "Mostrar Créditos de una Persona", "Exit"};
        String option;
        do {
            option = (String) JOptionPane.showInputDialog(null, "Select", "Menu", 1, null, menu, menu[0]);
            switch (option) {
                case "Registrar Cliente":
                    String nombre = JOptionPane.showInputDialog("Nombre del cliente a registrar");
                    int clienteID = Integer.parseInt(JOptionPane.showInputDialog("Digite el ID del Cliente"));
                    String tipo = JOptionPane.showInputDialog("Persona Natural/Juridico");
                    String direccion = JOptionPane.showInputDialog("Direccion del cliente");
                    String telefono = JOptionPane.showInputDialog("Telefono");
                    String tContrato = JOptionPane.showInputDialog("Tipo de contrato");
                    double salarioM = Double.parseDouble(JOptionPane.showInputDialog("Salario mensual del cliente"));

                    Cliente nCliente = new Cliente(clienteID, tipo, nombre, direccion, telefono, tContrato, salarioM);
                    listRegistrados.AddFirst(nCliente);

                    JOptionPane.showMessageDialog(null, "Cliente Registrado");

                    break;

                case "Solicitud de credito":

                    String tipoT = JOptionPane.showInputDialog("1. tarjeta \n 2. libre inversión \n 3.nómina \n 4.hipotecario (Digite el numero del prestamo que solicite)");
                    int idTitular = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del cliente titular"));

                    Cliente titular = listRegistrados.buscarClientePorId(idTitular);

                    if (titular != null) {
                        double valor = Double.parseDouble(JOptionPane.showInputDialog("Digite el valor del prestamo"));
                        int NumeroCuota = Integer.parseInt(JOptionPane.showInputDialog("Digite el numero de cuotas"));

                        Credito nCredito = new Credito(code, tipoT, titular, new Date(), valor, NumeroCuota);
                        colaSolicitud.Enqueue(nCredito);

                        JOptionPane.showMessageDialog(null, "Credito añadido a cola");
                        code += 1;

                    } else {
                        JOptionPane.showMessageDialog(null, "Cliente no encontrado");
                    }
                    break;

                case "Aprobar o negar una solicitud de crédito":

                    Credito procesado = colaSolicitud.DequeueSolic();
                    if (procesado != null) {
                        if (aprobarSolicitud(procesado)) {
                            JOptionPane.showMessageDialog(null, "Solicitud aprobada, código: " + procesado.getCódigoCredito());
                        } else {
                            JOptionPane.showMessageDialog(null, "Solicitud rechazada, código: " + procesado.getCódigoCredito());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay solicitudes de crédito para procesar");
                    }

                    break;

                case "Pagar cuota":
                    int codigoP = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código del préstamo: "));
                    double montoPago = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto a pagar: "));
                    pagarCuota(codigoP, montoPago);

                    break;
                case "Cancelar crédito":
                    int codigoPrestamo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el código del préstamo a cancelar:"));
                    cancelarCredito(codigoPrestamo);
                    break;

                case "Revaluar Solicitudes":
                    while (!pilaR.isEmpty()) {
                        CreditoVigente creditoRechazado = (CreditoVigente) pilaR.Pop();
                        Arbol.Add(creditoRechazado);
                    }

                    Arbol.revaluarSolicitudes(listaCreditos);

                    JOptionPane.showMessageDialog(null, "Revaluación de solicitudes completada.");
                    break;
                case "Guardar informacion":
                    a.EscribirListaCliente(listRegistrados);
                    a.EscribirListaCredito(listaCreditos);
                    a.EscribirCola(colaSolicitud);
                    a.EscribirPila(pilaR);
                    JOptionPane.showMessageDialog(null, "Toda la informacion ha sido guardada");

                    break;

                case "Mostrar Créditos de una Persona":
                    int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la identificación del cliente"));

                    // Use listRegistrados to find the client, as it should contain Cliente objects
                    Cliente cliente = listRegistrados.buscarClientePorId(idCliente);

                    if (cliente != null) {
                        // Client found, now show their credits
                        String mensaje = "Créditos del cliente " + cliente.getNombre() + " (ID: " + cliente.getClienteID() + "):\n\n";

                        // Traverse the list of active credits (listaCreditos) to display this client's credits
                        Node creditoActual = listaCreditos.getFirst(); // Primer nodo de la lista de créditos
                        boolean tieneCreditos = false;

                        while (creditoActual != null) {
                            CreditoVigente credito = (CreditoVigente) creditoActual.getData();

                            // Check if the credit belongs to the client
                            if (credito.getSolicitud().getTitular().getClienteID() == cliente.getClienteID()) {
                                // Display credit information
                                mensaje += "Código de Crédito: " + credito.getSolicitud().getCódigoCredito() + "\n";
                                mensaje += "Cuotas Pendientes: " + contarPagosPendientes(credito) + "\n";
                                mensaje += "Monto Pendiente: $" + calcularTotalCredito(credito) + "\n";
                                mensaje += "------------------------------------------\n";

                                tieneCreditos = true;
                            }
                            creditoActual = creditoActual.getLink();
                        }

                        if (!tieneCreditos) {
                            mensaje = "El cliente no tiene créditos vigentes.";
                        }

                        JOptionPane.showMessageDialog(null, mensaje);
                    } else {
                        JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
                    }
                    break;
            }
            }
            while (!option.equals("Exit"));

        }

    public static double calcularTEM(String tipoCredito) {
        double tasaAnual;

        switch (tipoCredito.toLowerCase()) {
            case "1":
                tasaAnual = (28 + (Math.random() * (32 - 28)));
                break;
            case "2":
                tasaAnual = (20 + (Math.random() * (25 - 20)));
                break;
            case "3":
                tasaAnual = (15 + (Math.random() * (20 - 15)));
                break;
            case "4":
                tasaAnual = (10 + (Math.random() * (12 - 10)));
                break;
            default:
                return 0;
        }

        return (Math.pow((1 + tasaAnual / 100), (1.0 / 12)) - 1);
    }

    public static double calcularCuota(double monto, double tasa, int meses) {
        return (monto * (tasa * Math.pow((1 + tasa), meses)) / (Math.pow((1 + tasa), meses) - 1));
    }

    public static boolean aprobarSolicitud(Credito solicitud) {

        double salarioMensual = solicitud.getTitular().getSalarioM();
        double cuotaMensual = calcularCuota(solicitud.getValor(), calcularTEM(solicitud.getTipo()), solicitud.getnCuotas());
        double sumaCuotasActuales = listaCreditos.obtenerSumaCuotasMensuales(solicitud.getTitular());

        if ((sumaCuotasActuales + cuotaMensual) <= (salarioMensual * 0.5)) {

            CreditoVigente creditoAprobado = new CreditoVigente(true, solicitud);

            JOptionPane.showMessageDialog(null, "El valor de cada cuota mensual será de: $" + cuotaMensual);

            for (int i = 0; i < solicitud.getnCuotas(); i++) {
                Cuota cuota = new Cuota(cuotaMensual);
                creditoAprobado.agregarCuota(cuota);
            }

            listaCreditos.agregarCredito(creditoAprobado);
            return true;
        } else {

            CreditoVigente creditoRechazado = new CreditoVigente(false, solicitud);
            pilaR.Push(creditoRechazado);
            return false;
        }
    }

    public static void aplicarExcedente(CreditoVigente credito, double excedente) {
        Cuota cuotaPendiente = credito.obtenerSiguienteCuotaPendiente();

        while (cuotaPendiente != null && excedente > 0) {
            double valorCuota = cuotaPendiente.getValor();

            if (excedente >= valorCuota) {
                cuotaPendiente.setValor(0);
                cuotaPendiente.setEstado("pagada");
                excedente -= valorCuota;
            } else {
                cuotaPendiente.setValor(valorCuota - excedente);
                excedente = 0;
            }

            cuotaPendiente = credito.obtenerSiguienteCuotaPendiente();
        }
    }

    public static void pagarCuota(int codigoPrestamo, double monto) {
        CreditoVigente credito = listaCreditos.buscarCreditoPorCodigo(codigoPrestamo);

        if (credito != null) {
            Cuota cuotaPendiente = credito.obtenerSiguienteCuotaPendiente();

            if (cuotaPendiente != null) {
                double valorCuota = cuotaPendiente.getValor();
                JOptionPane.showMessageDialog(null, "El valor de la cuota pendiente es: $" + valorCuota);

                if (monto >= valorCuota) {

                    cuotaPendiente.setEstado("pagada");
                    double excedente = monto - valorCuota;

                    if (excedente > 0) {
                        aplicarExcedente(credito, excedente);
                    }
                    JOptionPane.showMessageDialog(null, "Pago realizado, cuota pagada.");
                } else {
                    cuotaPendiente.setValor(valorCuota - monto);
                    JOptionPane.showMessageDialog(null, "Monto insuficiente, la cuota sigue en estado pendiente.");
                }

                int cuotasPendientes = contarCuotasPendientes(credito);
                JOptionPane.showMessageDialog(null, "Quedan " + cuotasPendientes + " cuotas pendientes.");

            } else {
                JOptionPane.showMessageDialog(null, "No hay cuotas pendientes para este préstamo.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Préstamo no encontrado.");
        }
    }

    public static int contarCuotasPendientes(CreditoVigente credito) {
        int contador = 0;
        Node nodoCuota = credito.getCuotas().getFirst();

        while (nodoCuota != null) {
            Cuota cuota = (Cuota) nodoCuota.getData();
            if (cuota.getEstado().equals("pendiente")) {
                contador++;
            }
            nodoCuota = nodoCuota.getLink();
        }

        return contador;
    }

    public static void cancelarCredito(int codigoPrestamo) {

        CreditoVigente credito = listaCreditos.buscarCreditoPorCodigo(codigoPrestamo);

        if (credito != null) {
            double totalCapital = 0;
            Node nodoCuota = credito.getCuotas().getFirst();
            while (nodoCuota != null) {
                Cuota cuota = (Cuota) nodoCuota.getData();
                if (cuota.getEstado().equals("pendiente")) {
                    totalCapital += cuota.getValor();
                }
                nodoCuota = nodoCuota.getLink();
            }

            double totalConDescuento = totalCapital * 0.9;
            JOptionPane.showMessageDialog(null, "El total a cancelar con el descuento del 10% es: $" + totalConDescuento);

            nodoCuota = credito.getCuotas().getFirst();
            while (nodoCuota != null) {
                Cuota cuota = (Cuota) nodoCuota.getData();
                cuota.setEstado("pagada");
                cuota.setValor(0);
                nodoCuota = nodoCuota.getLink();
            }

            JOptionPane.showMessageDialog(null, "Crédito cancelado y todas las cuotas se han marcado como pagadas.");
        } else {
            JOptionPane.showMessageDialog(null, "Préstamo no encontrado.");
        }
    }

    public static double obtenerTotalCredito(Cliente cliente) {
        double total = 0.0;
        Node creditoActual = listaCreditos.getFirst();
        while (creditoActual != null) {
            CreditoVigente credito = (CreditoVigente) creditoActual.getData();
            if (credito.getSolicitud().getTitular().getClienteID() == cliente.getClienteID()) {
                total += calcularTotalCredito(credito);
            }
            creditoActual = creditoActual.getLink();
        }
        return total;
    }

    public static double calcularTotalCredito(CreditoVigente credito) {
        double total = 0;
        Node cuotaActual = credito.getCuotas().getFirst();
        while (cuotaActual != null) {
            Cuota cuota = (Cuota) cuotaActual.getData();
            if (cuota.getEstado().equals("pendiente")) {
                total += cuota.getValor();
            }
            cuotaActual = cuotaActual.getLink();
        }
        return total;
    }

    public static int contarPagosPendientes(CreditoVigente credito) {
        int pagosPendientes = 0;
        Node cuotaActual = credito.getCuotas().getFirst();
        while (cuotaActual != null) {
            Cuota cuota = (Cuota) cuotaActual.getData();
            if (cuota.getEstado().equals("pendiente")) {
                pagosPendientes++;
            }
            cuotaActual = cuotaActual.getLink();
        }
        return pagosPendientes;
    }

}

// math.random()*4+28

