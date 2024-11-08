package com.mycompany.ahorro_seguro;

import java.util.Date;
import javax.swing.JOptionPane;

public class Ahorro_seguro {

    private static List listaCreditos = new List();
    private static Stack pilaR = new Stack();

    public static void main(String[] args) {
        List listRegistrados = new List();
        Cola colaSolicitud = new Cola();
        ArbolBinario Arbol = new ArbolBinario();

        int code = 1;

        String menu[] = {"Registrar Cliente", "Solicitud de credito", "Aprobar o negar una solicitud de crédito",
            "Pagar cuota", "Cancelar crédito", "Revaluar Solicitudes", "Exit"};
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
                        String fecha = JOptionPane.showInputDialog("Fecha en DD/MM/AAAA");
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
                    // Revaluar las solicitudes que están en la pila
                    while (!pilaR.isEmpty()) {
                        CreditoVigente creditoRechazado = (CreditoVigente) pilaR.Pop();
                        Arbol.Add(creditoRechazado); // Agregar a árbol para revaluar
                    }

                    // Ahora revaluar las solicitudes en el árbol
                    Arbol.revaluarSolicitudes(listaCreditos);

                    JOptionPane.showMessageDialog(null, "Revaluación de solicitudes completada.");
                    break;
            }

        } while (!option.equals("Exit"));

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
    
}
    


// math.random()*4+28

