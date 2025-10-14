package ud1.actividad3new.logica;

import ud1.actividad3new.persistencia.EquiposRandom;
import ud1.actividad3new.clases.Equipo;

public class GestorEquipos {
    EquiposRandom fichero;
    public static final String RUTA = "./src/ud1/actividad3new/saves/equipos.dat";

    public GestorEquipos() {
        fichero = new EquiposRandom(RUTA);
        fichero.crearPadreSiNoExiste();
    }

    public Equipo buscarEquipoPorNombre(String nombre) {
        try {
            if (nombre == null || nombre.isBlank()) {
                throw new IllegalArgumentException("El nombre del equipo no puede ser nulo o vacío.");
            }
            int id = 1;
            Equipo equipo;
            while ((equipo = fichero.leerEquipo(id)) != null) {
                if (equipo.getNombre().equalsIgnoreCase(nombre)) {
                    return equipo;
                }
                id++;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean guardarEquipo(Equipo equipo) {
        if (equipo == null) {
            throw new IllegalArgumentException("El equipo no puede ser nulo.");
        }
        try {
            if (buscarEquipoPorNombre(equipo.getNombre()) != null) {
                System.out.printf("El equipo %s ya existe.%n", equipo.getNombre());
                return false;
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

    public void eliminarEquipoPorID(int idEquipo) {
        if (idEquipo <= 0) {
            System.out.println("El ID del equipo debe ser un número positivo.");
            return;
        }

    }

    public void listarEquipos(){
        int id = 1;
        Equipo equipo;
        try {
            while ((equipo = fichero.leerEquipo(id)) != null) {
                if (!equipo.estaBorrado()) {
                    System.out.println(equipo);
                }
                id++;
            }
        } catch (Exception e) {
            System.out.println("Error al listar los equipos: " + e.getMessage());
        }
    }
}
