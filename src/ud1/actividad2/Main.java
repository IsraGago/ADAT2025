package ud1.actividad2;

public class Main {
    public static void main(String[] args) {
        final String CARPETA = "./src/ud1/actividad2/ficheros/"; // TODO CAMBIAR PARA ENTREGAR
        final String FICHERO_SALIDA_NUMLINEAS = CARPETA + "Salida.txt";
        final String FICHERO_SALIDA_REPETICIONES = CARPETA + "RepeticionesPalabraEspecifica.txt";
        final String FICHERO_SALIDA_REPETICIONES_AMPLIACION = CARPETA + "Repeticiones.txt";
        final String CARPETA_ALUMNOS = "./src/ud1/actividad2/cursos/";
        final String PALABRA_BUSCAR = "escoba";

        args = new String[4];
        args[0] = CARPETA + "exercicio5-1";
        args[1] = CARPETA + "file1.txt";
        args[2] = CARPETA + "file3.txt"; // CURSOS
        args[3] = CARPETA + "file2.txt"; // PALABRAS REPETIDAS

        /*
         * EJERCICIO 1: Escribe un método que cuente el número de líneas de cada fichero
         * que se especifique en la línea de
         * comandos (Nota: pueden especificarse varios archivos, como por ejemplo:
         * "exercicio5-1 file1.txt file3.txt
         * file2.txt"). Los archivos deben ser archivos de texto con la extensión txt.
         * Escribe en un fichero de texto llamado Salida.txt: el nombre de cada fichero,
         * junto con el número de líneas
         * del fichero. Si ocurre un error al intentar leer uno de los ficheros, en el
         * fichero salida.txt se graba un mensaje
         * de error para el archivo, y se deben procesar todos los ficheros restantes.
         */
        Operaciones.contarLineas(args, FICHERO_SALIDA_NUMLINEAS);

        /*
         * EJERCICIO 2: A partir de un fichero de texto con el formato
         * CURSO/NUMERO/ALUMNO crear un directorio por cada
         * curso y dentro de este un directorio por cada alumno perteneciente a ese
         * curso. En un fichero de texto
         * llamado ficherolog.txt se irá escribiendo el éxito o fracaso en la creación
         * de cada directorio de alumnos
         */
        Operaciones.crearDirectoriosAlumnos(args[2], CARPETA_ALUMNOS);

        /*
         * EJERCICIO 3:Escribe un método en Java que, dado un fichero de texto y una
         * palabra, cuente cuántas veces aparece esa
         * palabra en cada línea del fichero y también el número total de apariciones en
         * todo el documento. La salida se
         * grabará en un fichero de texto.
         */
        Operaciones.contarPalabraEspecificaFichero(args[3], FICHERO_SALIDA_REPETICIONES, PALABRA_BUSCAR);

        /*
         * EJERCICIO 4: Ampliar el ejercicio anterior haciendo un método que dado un
         * texto, cuente las veces que se repite cada palabra. La salida se grabará en
         * un fichero de texto
         */

         Operaciones.contarPalabraFichero(args[3],FICHERO_SALIDA_REPETICIONES_AMPLIACION);
    }
}
