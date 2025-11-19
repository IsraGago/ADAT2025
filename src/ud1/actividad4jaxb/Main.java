package ud1.actividad4jaxb;

import ud1.actividad4jaxb.logica.GestorCorredores;
import ud1.actividad4jaxb.logica.GestorEquipos;
import ud1.actividad4jaxb.persistencia.RutasFicheros;

public class Main {
    public static void main(String[] args) {
        GestorEquipos gestorEquipos = new GestorEquipos();
        GestorCorredores gestorCorredores = new GestorCorredores();
//        gestorEquipos.mostrarTodosLosEquiposJAXB(RutasFicheros.RUTA_XML_EQUIPOS.getRuta());
       gestorCorredores.mostrarTodosLosCorredoresJAXB(RutasFicheros.RUTA_XML_CORREDORES.getRuta());
    }
}
