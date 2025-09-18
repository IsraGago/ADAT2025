package ud1.actividad1.servicio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
    }

    public static void recorrerRecursivo(String ruta) {
        File directorio = new File(ruta);

        try {
            Utilidades.validarDirectorio(directorio);
            recorrerRecursivo(directorio, "─ "); // para no obligar a poner un int en la llamada
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void recorrerRecursivo(File directorio, String sangria) {
        File[] archivos = directorio.listFiles();
        if (archivos != null) {
            for (File f : archivos) {
                mostrarInfo(f, sangria);
                if (f.isDirectory()) {
                    recorrerRecursivo(f, "───" + sangria);
                }
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
        File[] ficheros = directorio.listFiles(new FiltroSubstring(subcadena));
        if (ficheros != null) {
            for (File f : ficheros) {
                mostrarInfo(f, subcadena);
            }
        } else {
            System.out.println("No se ha encontrado ningún archivo o directorio con la subcadena: " + subcadena);
        }
    }

    public static void copiarArchivo(String origen, String destino) throws ArchivoNoExisteException, IOException {
        File archivoOrigen = new File(origen);
        File archivoDestino = new File(destino);

        Utilidades.validarExistenciaArchivo(archivoOrigen);

        if (archivoDestino.isDirectory()){
            throw new IOException("El destino no puede ser un directorio: " + destino);
        }

        try (FileInputStream fis = new FileInputStream(archivoOrigen);
             FileOutputStream fos = new FileOutputStream(archivoDestino)) {

            byte[] buffer = new byte[8192]; // Buffer de 1 KB
            int bytesLeidos;

            while ((bytesLeidos = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesLeidos);
            }

            System.out.println("Archivo copiado exitosamente de " + origen + " a " + destino);

        } catch (SecurityException e) {
            System.out.println("Acceso denegado: No tienes permisos para acceder al archivo o directorio.");
        } catch (IOException e) {
            System.out.println("Error de E/S al copiar el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    public static void moverArchivo(String origen, String destino) throws ArchivoNoExisteException, IOException, DirectorioNoExisteException {
        copiarArchivo(origen,destino);
        borrar(origen);
    }

    public static void copiarDirectorio(String origen, String destino) throws DirectorioNoExisteException, NoEsDirectorioException, ArchivoNoExisteException, IOException {
        File dirOrigen = new File(origen);
        File dirDestino = new File(destino);

        Utilidades.validarDirectorio(dirOrigen);

        if (!dirDestino.exists()) {
            if (!dirDestino.mkdirs()) {
                throw new IOException("No se pudo crear el directorio de destino: " + dirDestino.getAbsolutePath());
            }
        }

        File[] ficherosOrigen = dirOrigen.listFiles();
        if (ficherosOrigen != null) {
            for (File f : ficherosOrigen) {
                File destinoArchivo = new File(dirDestino, f.getName());
                if (f.isDirectory()) {
                    // Copiar subdirectorios recursivamente
                    copiarDirectorio(f.getAbsolutePath(), destinoArchivo.getAbsolutePath());
                } else {
                    // Copiar archivos
                    copiarArchivo(f.getAbsolutePath(), destinoArchivo.getAbsolutePath());
                }
            }
        }
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
