package ud1.actividad3.logica;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ud1.actividad3.clases.Corredor;
import ud1.actividad3.clases.Equipo;
import ud1.actividad3.clases.Puntuacion;
import ud1.actividad3.persistencia.CorredoresRead;
import ud1.actividad3.persistencia.CorredoresWrite;
import ud1.actividad3.persistencia.EquiposRandom;
import ud1.actividad3.persistencia.Fichero;

public class GestorCorredores {
    public static final String RUTA = "./src/ud1/actividad3/saves/corredores.dat";

    public void guardarCorredor(Corredor corredor) {
        if (corredor == null) {
            throw new IllegalArgumentException("El corredor no puede ser nulo.");
        }
        if (!validarEquipo(corredor.getEquipo())) {
            throw new IllegalArgumentException("El equipo con el código " + corredor.getEquipo() + " no existe.");
        }
        CorredoresRead read = new CorredoresRead(RUTA);
        CorredoresWrite write = new CorredoresWrite(RUTA);
        try {

            corredor.setDorsal(read.getUltimoDorsal() + 1);

            write.abrir();
            write.escribir(corredor);

        } catch (RuntimeException e) {
            System.out.println("Error inesperado al guardar el corredor" + e.getMessage());
        } finally {
            write.cerrar();
        }

    }

    public void nuevaPuntuacion(int dorsal, Puntuacion puntuacion) {
        if (puntuacion == null || dorsal <= 0) {
            throw new IllegalArgumentException("Dorsal o puntuación inválidos");
        }

        Fichero auxiliar = new Fichero(RUTA + ".tmp");
        auxiliar.crearPadreSiNoExiste();

        CorredoresRead read = new CorredoresRead(RUTA);
        CorredoresWrite write = new CorredoresWrite(RUTA + ".tmp");
        boolean encontrado = false;

        try {
            write.abrir();
            read.abrir();
            while (true) {
                Corredor corredor = read.leer();
                if (corredor == null)
                    break;

                if (corredor.getDorsal() == dorsal) {
                    if (corredor.getHistorial().contains(puntuacion)) {
                        corredor.quitarPuntuacion(puntuacion);
                    }
                    encontrado = true;
                    corredor.addPuntuacion(puntuacion);
                }
                write.escribir(corredor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            read.cerrar();
            write.cerrar();
        }

        if (encontrado) {
            Fichero original = new Fichero(RUTA);
            if (!original.delete()) {
                System.out.println("No se pudo borrar el archivo original.");
                return;
            }
            if (auxiliar.renombrar(RUTA)) {
                System.out.println("Corredor con dorsal " + dorsal + " editado.");
            } else {
                System.out.println("No se pudo renombrar el archivo auxiliar.");
            }
        } else {
            auxiliar.delete();
            System.out.println("No se encontró ningún corredor con el dorsal: " + dorsal);
        }
    }

    public void guardarListaCorredores(List<Corredor> lista) {
        if (lista == null) {
            throw new IllegalArgumentException("La lista no puede ser nula.");
        }
        CorredoresRead read = new CorredoresRead(RUTA);
        CorredoresWrite write = new CorredoresWrite(RUTA);

        int dorsal = read.getUltimoDorsal();
        try {
            write.abrir();
            for (Corredor corredor : lista) {
                dorsal++;
                corredor.setDorsal(dorsal);
                write.escribir(corredor);
                System.out.println("Corredor guardado: " + corredor);
            }
        } catch (RuntimeException e) {
            System.out.println("Error inesperado al guardar la lista de corredores: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al guardar la lista de corredores: " + e.getMessage());
        } finally {
            write.cerrar();
        }

    }

    public Corredor buscarCorredorPorDorsal(int dorsal) {
        return new CorredoresRead(RUTA).buscarPorDorsal(dorsal);
    }

    private boolean validarEquipo(int idEquipo) {

        try {
            EquiposRandom fichero = new EquiposRandom(GestorEquipos.RUTA);
            return fichero.leerEquipo(idEquipo) != null;
        } catch (Exception e) {

        }
        return false;
    }

    public void mostrarCorredorPorDorsal(int dorsal) {
        Corredor corredor = buscarCorredorPorDorsal(dorsal);
        if (corredor != null) {
            corredor.mostrarInformacion();
        } else {
            System.out.println("No se encontró ningún corredor con el dorsal: " + dorsal);
        }
    }

    public void mostrarCorredor(Corredor corredor) {
        if (corredor == null) {
            System.out.println("El corredor es nulo");
            return;
        }
        corredor.mostrarInformacion();
    }

    public void listarTodosLosCorredores() {
        CorredoresRead read = new CorredoresRead(RUTA);
        int contador = 0;
        try {
            read.abrir();
            Corredor corredor = null;
            while ((corredor = read.leer()) != null) {
                contador++;
                corredor.mostrarInformacion();
            }
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        } finally {
            read.cerrar();
        }
        if (contador == 0) {
            System.out.println("El fichero está vacío");
        } else {
            System.out.println("FIN DEL ARCHIVO. TOTAL: " + contador + " CORREDORES");
        }
    }

    public void borrarCorredor(int dorsal) {
        Fichero auxiliar = new Fichero(RUTA + ".tmp");
        auxiliar.crearPadreSiNoExiste();

        CorredoresRead read = new CorredoresRead(RUTA);
        CorredoresWrite write = new CorredoresWrite(RUTA + ".tmp");

        boolean encontrado = false;
        Corredor temporal = null;
        try {
            write.abrir();
            while ((temporal = read.leer()) != null) {
                if (temporal.getDorsal() == dorsal) {
                    encontrado = true;
                } else {
                    write.escribir(temporal);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            write.cerrar();
        }

        if (encontrado) {
            Fichero original = new Fichero(RUTA);
            if (!original.delete()) {
                System.out.println("No se pudo borrar el archivo original.");
                return;
            }
            if (auxiliar.renombrar(RUTA)) {
                System.out.println("Corredor con dorsal " + dorsal + " borrado.");
            } else {
                System.out.println("No se pudo renombrar el archivo auxiliar.");
            }
        } else {
            auxiliar.delete();
            System.out.println("No se encontró ningún corredor con el dorsal: " + dorsal);
        }
    }

    public Map<String, List<String>> mostrarCorredoresPorEquipo() {
        EquiposRandom archivoEquipos = new EquiposRandom(GestorEquipos.RUTA);
        CorredoresRead archivoCorredores = new CorredoresRead(RUTA);
        Map<String, List<String>> mapa = new LinkedHashMap<>();

        try {
            archivoCorredores.abrir();
            Corredor c;
            while ((c = archivoCorredores.leer()) != null) {
                Equipo equipo = archivoEquipos.leerEquipo(c.getEquipo());
                if (equipo != null && !equipo.estaBorrado()) {
                    mapa.computeIfAbsent(equipo.getNombre(), k -> new ArrayList<>())
                            .add(c.getNombre() + " (" + c.getClass().getSimpleName() + ")");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo de corredores: " + e.getMessage());
        } finally {
            archivoCorredores.cerrar();
        }

        for (Entry<String, List<String>> entrada : mapa.entrySet()) {
            System.out.println(entrada.getKey());
            for (String corredor : entrada.getValue()) {
                System.out.println("    - " + corredor);
            }
        }

        return mapa;
    }

    public void numCorredoresPorEquipo() {
        CorredoresRead archivoCorredores = new CorredoresRead(RUTA);
        Map<Equipo, Integer> mapa = new LinkedHashMap<>();
        int totalCorredores = 0;
        try {
            archivoCorredores.abrir();
            Corredor c;
            while ((c = archivoCorredores.leer()) != null) {
                Equipo equipo = new GestorEquipos().buscarEquipoPorID(c.getEquipo());
                if (equipo != null && !equipo.estaBorrado()) {
                    totalCorredores++;
                    if (mapa.get(equipo) != null) {
                        mapa.put(equipo, mapa.get(equipo) + 1);
                    } else {
                        mapa.put(equipo, 1);
                    }
                }
            }

            for (Entry<Equipo, Integer> entrada : mapa.entrySet()) {
                System.out.print(entrada.getKey().getNombre() + " -> " + entrada.getValue() + " corredor");
                if (entrada.getValue() > 1) {
                    System.out.println("es");
                } else {
                    System.out.println();
                }
            }
            System.out.println("Total de corredores: " + totalCorredores);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            archivoCorredores.cerrar();
        }
    }
}
