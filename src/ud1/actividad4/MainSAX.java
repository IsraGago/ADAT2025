package ud1.actividad4;

import ud1.actividad4.clases.RutasFicheros;
import ud1.actividad4.logica.GestorEquipos;

import ud1.actividad4.logica.GestorCorredores;
import ud1.actividad4.persistencia.TipoValidacion;

public class MainSAX {
    static GestorCorredores gestorCorredores = new GestorCorredores();
    static GestorEquipos gestorEquipos = new GestorEquipos();
    public static void main(String[] args) {
        gestorCorredores.cargarDocumento(RutasFicheros.RUTA_XML_CORREDORES.getRuta(), TipoValidacion.DTD);

        mostrarTodosLosCorredores();

        mostrarCorredoresPorEquipo("Correcaminos");

    }

    private static void mostrarCorredoresPorEquipo(String nombreEquipo) {
        System.out.println("\nCORREDORES POR NOMBRE DE EQUIPO: "+nombreEquipo+"\n");
        gestorCorredores.mostrarCorredoresPorEquipoSAX(nombreEquipo, RutasFicheros.RUTA_XML_EQUIPOS.getRuta());
    }

    private static void mostrarTodosLosCorredores() {
        System.out.println("\nTODOS LOS CORREDORES\n");
        gestorCorredores.mostrarTodosLosCorredoresSAX();
    }


}
