package ud1.actividad3new.logica;

import ud1.actividad3new.persistencia.EquiposRandom;
import ud1.actividad3new.clases.Equipo;

public class GestorEquipos {
    EquiposRandom fichero;
    public static final String RUTA = "./src/actividad3new/saves/equipos.dat";

    public GestorEquipos(String nombreFichero) {
        fichero = new EquiposRandom(nombreFichero);
        try {
            fichero.abrir();
        } catch (Exception e) {
            System.out.println("Error al abrir el fichero: " + e.getMessage());
        }
    }

    public Equipo buscarEquipoPorNombre(String nombre) {
        int id = 1;
        Equipo equipo;
        while ((equipo = fichero.leerEquipo(id)) != null) {
            if (equipo.getNombre().equalsIgnoreCase(nombre)) {
                return equipo;
            }
            id++;
        }
        return null;
    }

    public boolean guardarEquipo(Equipo equipo) {
        try {
            if (buscarEquipoPorNombre(equipo.getNombre()) != null) {
                System.out.printf("El equipo %s ya existe.%n", equipo.getNombre());
                return false; // El equipo ya existe
            }
            int nuevoId = fichero.calcularNuevoID();
            equipo.setIdEquipo(nuevoId);
            fichero.guardarEquipo(equipo);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Datos inválidos: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error al guardar el equipo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
        return false;

    }

    public Equipo buscarEquipoPorID(int id) {
        if (id <= 0) {
            System.out.println("El ID del equipo debe ser un número positivo.");
            return null;
        }
        try {
            return fichero.leerEquipo(id);
        } catch (RuntimeException e) {
            System.out.println("Error al buscar el equipo: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
        return null;
    }

    public void eliminarEquipoPorID(int idEquipo){
        if (idEquipo <= 0) {
            System.out.println("El ID del equipo debe ser un número positivo.");
            return;
        }
        
    }
}
