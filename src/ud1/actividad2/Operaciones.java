package ud1.actividad2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Operaciones {
    public static int contarLineas(String ruta) {
        int contador = -1;
        try {
            LecturaTexto lectura = new LecturaTexto(ruta);
            lectura.abrirArchivo();
            String linea = "";
            contador = 0;
            while (linea != null) {
                linea = lectura.leerLinea();
                if (linea != null) {
                    contador++;
                }
            }
            return contador;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return contador;
    }

    public static void contarLineas(String[] archivos, String ficheroSalida) {
        ArrayList<String> lineas = new ArrayList<String>();
        for (String a : archivos) {
            if (esArchivoTexto(a)) {
                int numLineas = contarLineas(a);
                if (numLineas >= 0) {
                    lineas.add(a + ": " + numLineas + " líneas");
                }
            } else {
                lineas.add(a + " - No existe o no es un archivo de texto");
            }
        }
        escribirLineas(lineas.toArray(new String[0]), ficheroSalida);
    }

    private static boolean esArchivoTexto(String ruta) {
        File archivo = new File(ruta);
        return archivo.exists() && archivo.isFile() && archivo.getName().endsWith(".txt");
    }

    public static void escribirLineas(String[] lineas, String ruta) {
        EscribirTexto escritura = new EscribirTexto(ruta);
        escritura.abrirArchivo();
        for (String linea : lineas) {
            escritura.escribirLinea(linea);
        }
        escritura.cerrarArchivo();
    }

    public static void crearDirectoriosAlumnos(String ficheroAlumnos, String carpetaSalida) {
        ArrayList<String> alumnos = new ArrayList<String>();
        try {
            LecturaTexto leer = new LecturaTexto(ficheroAlumnos);
            String linea = "";
            leer.abrirArchivo();
            while (linea != null) {
                linea = leer.leerLinea();
                if (linea.matches("DAM|ASIR[1,2]/\\d+/[A-Za-zñ]+")) {
                    String[] partes = linea.split("/");
                    String curso = partes[0];
                    String numero = partes[1];
                    String nombre = partes[2];

                } else {
                    alumnos.add(
                            linea + "----> No tiene el formato CURSO/NUMERO/ALUMNO. NO SE PUEDE CREAR EL DIRECTORIO");
                }
            }
            leer.cerrarArchivo();
        } catch (Exception e) {

        }
    }

    public static void contarPalabraEspecificaFichero(String ficheroEntrada, String ficheroSalida,
            String palabraBuscar) {
        LecturaTexto leer = new LecturaTexto(ficheroEntrada);

        ArrayList<String> lineasSalida = new ArrayList<String>();
        lineasSalida.add("La palabra \"" + palabraBuscar + "\" en el fichero " + ficheroEntrada + ":");
        lineasSalida.add("-----------------------------------");
        int contadorLineas = 0;
        int contadorTotalRepeticiones = 0;

        try {
            leer.abrirArchivo();
            String linea = "";
            while (linea != null) {
                linea = leer.leerLinea();

                if (linea != null) {
                    int repeticionesPalabra = 0;
                    contadorLineas++;
                    for (String palabra : linea.split(" ")) {
                        if (palabra.matches(palabraBuscar + "[.,¿?¡!:;]?")) {
                            repeticionesPalabra++;
                        }
                    }
                    contadorTotalRepeticiones += repeticionesPalabra;

                    lineasSalida.add("línea " + contadorLineas + ": aparece " + repeticionesPalabra + " veces.");
                }
            }
            lineasSalida.add("-----------------------------------");
            lineasSalida.add("Aparecen un total de " + contadorTotalRepeticiones + " veces.");
            leer.cerrarArchivo();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        escribirLineas(lineasSalida.toArray(new String[0]), ficheroSalida);
    }

    public static void contarPalabraFichero(String ficheroEntrada, String ficheroSalida) {
        LecturaTexto leer = new LecturaTexto(ficheroEntrada);
        String caracteresEspeciales = ".,¿?¡!;:%$&\"\'()<>{}";
        Map<String, Integer> mapa = new HashMap<>();
        ArrayList<String> lineasSalida = new ArrayList<>();
        lineasSalida.add("Palabras y sus repeticiones en el archivo: " + ficheroEntrada + "");
        lineasSalida.add("-------------------------");
        try {
            leer.abrirArchivo();
            String linea = "";
            while (linea != null) {
                linea = leer.leerLinea();
                if (linea != null) {
                    linea = linea.toLowerCase();
                    for (String palabra : linea.split("[ " + caracteresEspeciales + "]")) {
                        if (!palabra.isEmpty()) {
                            if (mapa.containsKey(palabra)) {
                                mapa.put(palabra, mapa.get(palabra) + 1);
                            } else {
                                mapa.put(palabra, 1);
                            }
                        }
                    }
                }
            }
            leer.cerrarArchivo();

            for (Map.Entry<String, Integer> entrada : mapa.entrySet()) {
                lineasSalida.add(entrada.getKey() + ": " + entrada.getValue() + " veces.");
            }

            escribirLineas(lineasSalida.toArray(new String[0]), ficheroSalida);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
