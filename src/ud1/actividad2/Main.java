package ud1.actividad2;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final String CARPETA = "./src/ud1/actividad2/ficheros/";
        final String FICHERO_SALIDA = CARPETA + "Salida.txt";
        args = new String[4];
        args[0] = CARPETA + "exercicio5-1";
        args[1] = CARPETA + "file1.txt";
        args[2] = CARPETA + "file3.txt"; // CURSOS
        args[3] = CARPETA + "file2.txt"; // PALABRAS REPETIDAS

        /* EJERCICIO 1: ESCRIBIR EL NÚMERO DE LINEAS DE LOS FICHEROS */
        ArrayList<String> lineas = new ArrayList<String>();
        for (String f : args) {
            int numLineas = Operaciones.contarLineas(f);
            String nombreFichero = f.substring(CARPETA.length());
            if (numLineas >= 0) {
                System.out.println(nombreFichero + ": " + numLineas + " líneas"); // SOLO PARA VER POR TERMINAL, NO HACE
                                                                                  // FALTA.
                lineas.add(nombreFichero + ": " + numLineas + " líneas");
            }
        }
        Operaciones.escribirLineas(lineas.toArray(new String[0]), FICHERO_SALIDA);

        /*
         * EJERCICIO 2: A partir de un fichero de texto con el formato
         * CURSO/NUMERO/ALUMNO crear un directorio por cada
         * curso y dentro de este un directorio por cada alumno perteneciente a ese
         * curso. En un fichero de texto
         * llamado ficherolog.txt se irá escribiendo el éxito o fracaso en la creación
         * de cada directorio de alumnos
         */

        

    }
}
