package com.mycompany.ahorro_seguro;

public class CreditoVigente {

    private boolean aprobado;
    private Credito solicitud;
    private List cuotas;

    public CreditoVigente(boolean aprobado, Credito solicitud) {
        this.aprobado = aprobado;
        this.solicitud = solicitud;
        this.cuotas = new List();
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public Credito getSolicitud() {
        return solicitud;
    }

    public List getCuotas() {
        return cuotas;
    }

    public void agregarCuota(Cuota cuota) {
        cuotas.agregarCuota(cuota); 
    }
public Cuota obtenerSiguienteCuotaPendiente() {
    Node actual = cuotas.getFirst();
    while (actual != null) {
        if (actual.getData() instanceof Cuota) { 
            Cuota cuota = (Cuota) actual.getData();
            if (cuota.getEstado().equals("pendiente")) {
                return cuota;
            }
        }
        actual = actual.getLink();
    }
    return null; 

}

}
