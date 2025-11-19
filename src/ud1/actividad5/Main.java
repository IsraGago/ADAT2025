package ud1.actividad5;

import ud1.actividad5.logica.GestorCorredores;
import ud1.actividad5.logica.GestorEquipos;
import ud1.actividad5.persistencia.RutasFicheros;

public class Main {
    public static void main(String[] args) {
        GestorEquipos gestorEquipos = new GestorEquipos();
        GestorCorredores gestorCorredores = new GestorCorredores();
//        gestorEquipos.mostrarTodosLosEquiposJAXB(RutasFicheros.RUTA_XML_EQUIPOS.getRuta());
       gestorCorredores.mostrarTodosLosCorredoresJAXB(RutasFicheros.RUTA_XML_CORREDORES.getRuta());
    }
}
