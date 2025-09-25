package ud1.actividad2;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        final String CARPETA = "./src/ud1/actividad2/ficheros/";
        final String FICHERO_SALIDA = CARPETA + "Salida.txt";
        args = new String[4];
        args[0] = CARPETA + "exercicio5-1";
        args[1] = CARPETA + "file1.txt";
        args[2] = CARPETA + "file3.txt";
        args[3] = CARPETA + "file2.txt";

        // EJERCICIO 1
        ArrayList<String> lineas = new ArrayList<String>();
        for (String f : args) {
            int numLineas = Operaciones.contarLineas(f);
            String nombreFichero = f.substring(CARPETA.length());
            if (numLineas >= 0) {
                System.out.println(nombreFichero + ": " + numLineas + " líneas"); // SOLO PARA VER POR TERMINAL, NO HACE FALTA.
                lineas.add(nombreFichero + ": " + numLineas + " líneas");
                // TODO GUARDAR LA SALIDA EN EL FICHERO
                // Operaciones.escribirLineas();
            }
        }

    }
}
