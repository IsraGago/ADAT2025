package ud1.actividad1.servicio;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

import ud1.actividad1.excepciones.ArchivoNoExisteException;
import ud1.actividad1.excepciones.DirectorioNoExisteException;
import ud1.actividad1.excepciones.NoEsDirectorioException;

public class OperacionesIO {
    public static void visualizarContenido(String ruta) throws DirectorioNoExisteException, NoEsDirectorioException {
        final long KB = (long) 1024.0;
        File directorio = new File(ruta);
        if (!directorio.exists()) {
            throw new DirectorioNoExisteException();
        }
        if (directorio.isFile()) {
            throw new NoEsDirectorioException();
        }

        for (String nombre : directorio.list()) {
            File elemento = new File(ruta + "\\" + nombre);
            if (elemento.isFile()) {
                LocalDateTime fecha = LocalDateTime.ofInstant(Instant.ofEpochMilli(elemento.lastModified()),
                        ZoneId.systemDefault());
                DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String fechaFormateada = formateador.format(fecha);

                System.out.println("- | " + elemento.getName() + " " + "<FICHERO> " + elemento.length() / KB + " "
                        + fechaFormateada);
            } else {
                // ES DIRECTORIO
                System.out.println("- | " + elemento.getName() + " " + "<DIR>");
            }

        }
    }

    public static void recorrerRecursivo(String ruta) {
        try {
            recorrerRecursivo(ruta, 1); // para no obligar a poner un int en la llamada
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void recorrerRecursivo(String ruta, int nivelIdentacion)
            throws DirectorioNoExisteException, NoEsDirectorioException {
        final long KB = (long) 1024.0;
        File directorio = new File(ruta);
        if (!directorio.exists()) {
            throw new DirectorioNoExisteException();
        }
        if (directorio.isFile()) {
            throw new NoEsDirectorioException();
        }

        String identacion = "-".repeat(nivelIdentacion);

        for (String nombre : directorio.list()) {
            File elemento = new File(ruta + "\\" + nombre);
            if (elemento.isFile()) {
                LocalDateTime fecha = LocalDateTime.ofInstant(Instant.ofEpochMilli(elemento.lastModified()),
                        ZoneId.systemDefault());
                DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String fechaFormateada = formateador.format(fecha);

                System.out.println(identacion + "| " + elemento.getName()
                        + " <FICHERO> " + (elemento.length() / KB) + " KB "
                        + fechaFormateada);
            } else {
                // ES DIRECTORIO
                System.out.println(identacion + "| " + elemento.getName() + " <DIR>");
                recorrerRecursivo(elemento.getAbsolutePath(), nivelIdentacion + 5);

            }

        }
    }

    public static void filtrarPorExtension(String ruta, String extension)
            throws DirectorioNoExisteException, NoEsDirectorioException {
        File directorio = new File(ruta);
        if (!directorio.exists()) {
            throw new DirectorioNoExisteException();
        }
        if (directorio.isFile()) {
            throw new NoEsDirectorioException();
        }

        boolean hayFichero = false;

        for (String nombre : directorio.list()) {
            File elemento = new File(ruta + "\\" + nombre);
            if (elemento.isFile() && getExtension(elemento).equals(extension)) {
                System.out.println("-| " + elemento.getName());
                hayFichero = true;
            }
        }

        if (!hayFichero) {
            System.out.println("No se ha encontrado ningún fichero con la extensión ." + extension);
        }
    }

    private static String getExtension(File f) {
        String nombre = f.getName();
        int posicionPunto = nombre.lastIndexOf('.');
        if (posicionPunto > 0 && posicionPunto < nombre.length() - 1) {
            return nombre.substring(posicionPunto + 1);
        } else {
            throw new IllegalArgumentException("ERROR: El archivo no tiene extensión");
        }
    }

    public static void filtrarPorExtensionYOrdenar(String ruta, String extension, boolean descendente)
            throws NoEsDirectorioException, DirectorioNoExisteException {
        ArrayList<File> lista = new ArrayList<File>();
        File directorio = new File(ruta);
        if (!directorio.exists()) {
            throw new DirectorioNoExisteException();
        } else if (!directorio.isDirectory()) {
            throw new NoEsDirectorioException();
        }

        Comparator<File> comparador = Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER);

        for (String nombre : directorio.list()) {
            File elemento = new File(ruta + "\\" + nombre);
            if (elemento.isFile() && getExtension(elemento).equalsIgnoreCase(extension)) {
                lista.add(elemento);
            }
        }

        if (descendente) {
            lista.sort(comparador.reversed());
        } else {
            lista.sort(comparador);
        }

        for (File f : lista) {
            System.out.println(f.getName());
        }
    }

    public static void filtrarPorSubcadena(String ruta, String subcadena) {

    }

    public static void copiarArchivo(String origen, String destino) throws ArchivoNoExisteException {
        File rutaOrigen = new File(origen);
        File rutaDestino = new File(destino);

        if (!rutaOrigen.exists()) {
            throw new ArchivoNoExisteException();
        } else if (rutaOrigen.isDirectory()) {
            System.out.println("ERROR: La ruta de origen debe corresponder a un archivo, no a un directorio.");
        }

        // TODO LEER EL FICHERO Y ESCRIBIRLO EN LA NUEVA RUTA
    }

    public static void moverArchivo(String origen, String destino) {

        // TODO LEER EL FICHERO, ESCRIBIRLO EN LA NUEVA RUTA Y BORRAR EL VIEJO
    }

    public static void copiarDirectorio(String origen, String destino) {
    }

    public static void borrar(String ruta) throws DirectorioNoExisteException {
        File directorio = new File(ruta);
        if (!directorio.exists()) {
            throw new DirectorioNoExisteException();
        }

        if (directorio.isFile() && directorio.delete()) {
            System.out.println("Archivo " + directorio.getAbsolutePath() + " borrado correctamente.");
        } else if (directorio.isFile()) {
            System.out.println("No se ha podido eliminar el archivo " + directorio.getAbsolutePath() + "");
        } else {
            for (String nombre : directorio.list()) {
                File elemento = new File(ruta + "\\" + nombre);
                if (elemento.isFile()) {
                    System.out.println("Borrando: " + elemento.getName());
                    if (!elemento.delete()) {
                        System.out.println("Se ha producido un error y el archivo no se ha podido borrar.");
                    }
                } else { // SI ES UNA CARPETA
                    System.out.println("Entrando en la carpeta: " + elemento.getName());
                    borrar(elemento.getAbsolutePath());
                    // Intentar borrar la carpeta después de vaciarla
                    System.out.println("Borrando la carpeta: " + elemento.getName());
                    if (!elemento.delete()) {
                        System.out.println("Se ha producido un error y la carpeta no se ha podido borrar.");
                    }

                }
            }
            if (!directorio.delete()) {
                System.out.println("No se pudo borrar la carpeta raíz: " + directorio.getName());
            } else {
                System.out.println("Borrando la carpeta " + ruta);
            }
        }

    }

}
