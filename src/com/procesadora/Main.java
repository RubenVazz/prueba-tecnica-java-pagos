

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("*** INICIANDO SISTEMA DE PAGOS ***");
        
        ProcesadorPagos servicio = new ProcesadorPagos();

        List<Operacion> pendientes = servicio.obtenerPendientes();
        System.out.println("Se encontraron " + pendientes.size() + " operaciones pendientes.");

        for (Operacion op : pendientes) {
            servicio.procesarOperacion(op);
        }

        System.out.println("*** FIN DEL PROCESO ***");
    }
}