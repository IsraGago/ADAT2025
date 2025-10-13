package ud1.actividad3new.logica;

import ud1.actividad3.persistencia.EquiposRandom;

public class GestorEquipos {
    EquiposRandom fichero;

    public GestorEquipos(String nombreFichero) {
        fichero = new EquiposRandom(nombreFichero);
        try {
            fichero.abrir();
        } catch (Exception e) {
            System.out.println("Error al abrir el fichero: " + e.getMessage());
        }
    }
}
