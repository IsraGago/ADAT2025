package ud1.actividad4;

import ud1.actividad4.logica.GestorEquipos;

import ud1.actividad4.logica.GestorCorredores;
import ud1.actividad4.persistencia.TipoValidacion;

public class MainSAX {
    static final String RUTA_XML_CORREDORES = "./src/ud1/actividad4/archivos/corredores.xml";
    static final String RUTA_XML_EQUIPOS = "./src/ud1/actividad4/archivos/equipos.xml";
    static GestorCorredores gestorCorredores = new GestorCorredores();
    static GestorEquipos gestorEquipos = new GestorEquipos();
    public static void main(String[] args) {
        gestorCorredores.cargarDocumento(RUTA_XML_CORREDORES, TipoValidacion.DTD);

        mostrarTodosLosCorredores();

        mostrarCorredoresPorEquipo("Correcaminos");

    }

    private static void mostrarCorredoresPorEquipo(String nombreEquipo) {
        System.out.println("\nCORREDORES POR NOMBRE DE EQUIPO: "+nombreEquipo+"\n");
        gestorCorredores.mostrarCorredoresPorEquipoSAX(nombreEquipo, RUTA_XML_EQUIPOS);
    }

    private static void mostrarTodosLosCorredores() {
        System.out.println("\nTODOS LOS CORREDORES\n");
        gestorCorredores.mostrarTodosLosCorredoresSAX();
    }


}
