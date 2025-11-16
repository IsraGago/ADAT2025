package ud1.actividad4;

import ud1.actividad4.clases.RutasFicheros;
import ud1.actividad4.logica.GestorCorredores;
import ud1.actividad4.logica.GestorEquipos;
import ud1.actividad4.persistencia.TipoValidacion;

public class MainStAXEventos {
    public static void main(String[] args) {
        GestorCorredores gestorCorredores = new GestorCorredores();
        GestorEquipos gestorEquipos = new GestorEquipos();
//        gestorCorredores.mostrarCorredoresStaxEventos(RutasFicheros.RUTA_XML_CORREDORES.getRuta(), TipoValidacion.NO_VALIDAR);
        //gestorCorredores.mostrarCorredoresPorEquipoEventos(RutasFicheros.RUTA_XML_CORREDORES.getRuta(), TipoValidacion.NO_VALIDAR, "E1");
        gestorEquipos.calcularDonacionTotalPorPatrocinadorEventos(RutasFicheros.RUTA_XML_EQUIPOS.getRuta(), RutasFicheros.RUTA_XML_SALIDA_DONACIONES.getRuta());
    }
}
