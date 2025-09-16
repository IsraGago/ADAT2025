package ud1.actividad1.servicio;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import ud1.actividad1.excepciones.ArchivoNoExisteException;
import ud1.actividad1.excepciones.DirectorioNoExisteException;
import ud1.actividad1.excepciones.NoEsDirectorioException;

import static ud1.actividad1.servicio.Utilidades.mostrarInfo;

public class OperacionesIO {
    public static void visualizarContenido(String ruta) throws DirectorioNoExisteException, NoEsDirectorioException {
        File directorio = new File(ruta);
        Utilidades.validarDirectorio(directorio);

        File[] ficheros = directorio.listFiles();
        for (File file : ficheros) {
            mostrarInfo(file, "");
        }

        // for (String nombre : directorio.list()) {
        //     File elemento = new File(ruta + "\\" + nombre);

        //     if (elemento.isFile()) {
        //         String patronFecha = "dd/MM/yyyy HH:mm:ss";
        //         String fechaFormateada = Utilidades.formatearFecha(elemento.lastModified(),patronFecha);

        //         System.out.println("- | " + elemento.getName() + " " + "<FICHERO> " + elemento.length() / 1024.0 + " "
        //                 + fechaFormateada);
        //     } else {
        //         // ES DIRECTORIO
        //         System.out.println("- | " + elemento.getName() + " " + "<DIR>");
        //     }

        // }
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
        Utilidades.validarDirectorio(directorio);

        // CADA VEZ QUE SE LLAMA A SI MISMO VUELVE A VALIDAR; ES INEFICIENTE.
        // recorrer(directorio, "-|");

        String identacion = "-".repeat(nivelIdentacion);

        for (String nombre : directorio.list()) {
            File elemento = new File(ruta + "\\" + nombre);
            if (elemento.isFile()) {
                String patronFecha = "dd/MM/yyyy HH:mm:ss";
                String fechaFormateada = Utilidades.formatearFecha(elemento.lastModified(), patronFecha);

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

    private static void recorrer(File dir, String sangria) {
        File[] elementos = dir.listFiles();
        if (elementos == null) {
            return;
        }
        for (File f : elementos) {
            mostrarInfo(f, sangria);
            if (f.isDirectory()) {
                recorrer(f, sangria + "----");
            }
        }
    }

    public static void filtrarPorExtension(String ruta, String extension)
            throws DirectorioNoExisteException, NoEsDirectorioException {
        File directorio = new File(ruta);
        Utilidades.validarDirectorio(directorio);

        File[] listaArchivos = directorio.listFiles(new FiltroExtension(extension));

        if (listaArchivos != null) {
            for (File file : listaArchivos) {
                mostrarInfo(file, extension);
            }
        } else {
            System.out.println("No se ha encontrado ningún fichero con la extensión: " + extension);

        }
    }


    public static void filtrarPorExtensionYOrdenar(String ruta, String extension, boolean descendente)
            throws NoEsDirectorioException, DirectorioNoExisteException {
        ArrayList<File> lista = new ArrayList<File>();
        File directorio = new File(ruta);
        Utilidades.validarDirectorio(directorio);

        Comparator<File> comparador = Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER);

        Collections.addAll(lista, Objects.requireNonNull(directorio.listFiles(new FiltroExtension(extension))));

        if (descendente) {
            lista.sort(comparador.reversed());
        } else {
            lista.sort(comparador);
        }

        for (File f : lista) {
            mostrarInfo(f, extension);
        }
    }

    public static void filtrarPorSubcadena(String ruta, String subcadena) throws DirectorioNoExisteException, NoEsDirectorioException {
        File directorio = new File(ruta);
        Utilidades.validarDirectorio(directorio);
        File[] ficheros = directorio.listFiles(new FiltroSubstring("subadena"));
        if (ficheros != null) {
            for (File f : ficheros) {
                mostrarInfo(f, subcadena);
            }
        } else {
            System.out.println("No se ha encontrado ningún archivo o directorio con la subcadena: "+subcadena);
        }
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

    public static void borrar(String ruta) throws DirectorioNoExisteException, ArchivoNoExisteException {
        File directorio = new File(ruta);
        Utilidades.validarExistenciaArchivo(directorio);

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
