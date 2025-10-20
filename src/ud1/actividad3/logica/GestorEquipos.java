package ud1.actividad3.logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ud1.actividad3.clases.Corredor;
import ud1.actividad3.clases.Equipo;
import ud1.actividad3.clases.Patrocinador;
import ud1.actividad3.clases.Puntuacion;
import ud1.actividad3.clases.Velocista;
import ud1.actividad3.persistencia.EquiposRandom;

public class GestorEquipos {
    EquiposRandom fichero;
    public static final String RUTA = "./src/ud1/actividad3/saves/equipos.dat";

    public GestorEquipos() {
        fichero = new EquiposRandom(RUTA);
        fichero.crearPadreSiNoExiste();
    }

    public Equipo buscarEquipoPorNombre(String nombre) {
        try {
            if (!fichero.existe()) {
                return null;
            }
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

    public void listarEquipos() {
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

    public void corredoresPatrocinadosPor(Patrocinador patrocinador) {
        ArrayList<Equipo> equipos = new ArrayList<>();
        ArrayList<Corredor> corredores = null;
        GestorCorredores gestorCorredores = new GestorCorredores();
        int id = 1;
        Equipo e;
        try {
            // equipos que son patrocinados por ese patrocinador
            while ((e = fichero.leerEquipo(id)) != null) {
                for (Patrocinador p : e.getPatrocinadores()) {
                    if (p.equals(patrocinador)) {
                        equipos.add(e);
                        break;
                    }
                }
                id++;
            }
            corredores = gestorCorredores.obtenerTodosLosCorredores();
            System.out.println("Patrocinador: " + patrocinador.getNombre() + "\n Corredores patrocinados:");
            for (Corredor corredor : corredores) {
                Equipo equipoCorredor = buscarEquipoPorID(corredor.getEquipo());
                if (equipos.contains(equipoCorredor)) {
                    System.out.println(
                            "    -" + corredor + (corredor instanceof Velocista ? "(Velocista)" : "(Fondista)"));
                }
            }

        } catch (Exception ex) {
            System.out.println("Se ha producido un error: " + ex.getMessage());
        }
    }

    public Map<Equipo, Double> mediaPuntuacionesPorEquipo() {
        GestorCorredores gestorCorredores = new GestorCorredores();
        Map<Equipo, Double> mapaMedia = new HashMap<>();
        try {
            ArrayList<Equipo> equipos = obtenerTodosLosEquipos();
            ArrayList<Corredor> corredores = gestorCorredores.obtenerTodosLosCorredores();

            // Verificar si la lista de corredores está vacía o nula
            if (corredores == null || corredores.isEmpty()) {
                System.out.println("No hay corredores disponibles.");
                return mapaMedia; // Retornar mapa vacío
            }

            // Agrupar corredores por equipo para optimizar las búsquedas
            Map<Integer, List<Corredor>> corredoresPorEquipo = new HashMap<>();
            for (Corredor corredor : corredores) {
                corredoresPorEquipo
                        .computeIfAbsent(corredor.getEquipo(), k -> new ArrayList<>())
                        .add(corredor);
            }

            // Calcular la media de puntuaciones por equipo
            for (Equipo equipo : equipos) {
                List<Corredor> corredoresEquipo = corredoresPorEquipo.get(equipo.getIdEquipo());
                if (corredoresEquipo == null || corredoresEquipo.isEmpty()) {
                    mapaMedia.put(equipo, 0.0); // Si no hay corredores, la media es 0
                    continue;
                }

                double sumaPuntuaciones = 0;
                int contadorPuntuaciones = 0;

                for (Corredor corredor : corredoresEquipo) {
                    if (corredor.getHistorial() != null) {
                        for (Puntuacion puntuacion : corredor.getHistorial()) {
                            sumaPuntuaciones += puntuacion.getPuntos();
                            contadorPuntuaciones++;
                        }
                    }
                }

                double media = contadorPuntuaciones > 0 ? sumaPuntuaciones / contadorPuntuaciones : 0;
                mapaMedia.put(equipo, media);
            }

            // Imprimir los resultados una vez calculados
            int contador = 1;
            for (Map.Entry<Equipo, Double> entry : mapaMedia.entrySet()) {
                System.out.printf("%d. %s -> %.2f puntos%n", contador++, entry.getKey().getNombre(), entry.getValue());
            }
        } catch (Exception e) {
            System.out.println("Error al calcular la media de puntuaciones: " + e.getMessage());
        }
        return mapaMedia;
    }

    private ArrayList<Equipo> obtenerTodosLosEquipos() {
        ArrayList<Equipo> equipos = new ArrayList<>();
        int id = 1;
        Equipo e;
        try {
            while ((e = fichero.leerEquipo(id)) != null) {
                equipos.add(e);
                id++;
            }
        } catch (Exception ex) {
            System.out.println("Error al obtener los equipos: " + ex.getMessage());
        }
        return equipos;
    }

    public void mostrarDonacionesYCorredoresPorEquipo() {
        try {
            ArrayList<Equipo> equipos = obtenerTodosLosEquipos();
            GestorCorredores gestorCorredores = new GestorCorredores();
            ArrayList<Corredor> corredores = gestorCorredores.obtenerTodosLosCorredores();

            if (equipos.isEmpty()) {
                System.out.println("No hay equipos disponibles.");
                return;
            }

            for (Equipo equipo : equipos) {
                double sumaDonaciones = 0;
                for (Patrocinador patrocinador : equipo.getPatrocinadores()) {
                    sumaDonaciones += patrocinador.getDonacion();
                }

                System.out.printf("Equipo: %s\n", equipo.getNombre());
                System.out.printf("  Suma total de donaciones: %.2f\n", sumaDonaciones);

                System.out.println("  Corredores:");
                for (Corredor corredor : corredores) {
                    if (corredor.getEquipo() == equipo.getIdEquipo()) {
                        System.out.printf("     - %s\n", corredor.getNombre());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar las donaciones y corredores por equipo: " + e.getMessage());
        }
    }
}
