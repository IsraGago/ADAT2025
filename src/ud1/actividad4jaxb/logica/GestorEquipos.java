package ud1.actividad4jaxb.logica;

import ud1.actividad4jaxb.clases.clasesEquipo.Equipos;
import ud1.actividad4jaxb.persistencia.EquiposJAXB;

public class GestorEquipos {
    public void mostrarTodosLosEquiposJAXB(String ruta) {
        Equipos equipos = EquiposJAXB.leerEquipos(ruta);
        if (equipos == null || equipos.getEquipo() == null) {
            System.out.println("no hay equipos para mostrar");
            return;
        }
        System.out.println(equipos);
    }
}
