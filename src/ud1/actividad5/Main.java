package ud1.actividad5;

import ud1.actividad5.logica.GestorEquipos;
import ud1.actividad5.persistencia.RutasFicheros;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        GestorEquipos gestor = new GestorEquipos();
        gestor.mostrarTodosLosEquiposJAXB(RutasFicheros.RUTA_XML_EQUIPOS.getRuta());

    }
}
