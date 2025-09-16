package ud1.actividad1.servicio;

import java.io.File;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import ud1.actividad1.excepciones.ArchivoNoExisteException;
import ud1.actividad1.excepciones.DirectorioNoExisteException;
import ud1.actividad1.excepciones.NoEsDirectorioException;

public class Utilidades {
    public static String getExtension(File f) {
        String nombre = f.getName();
        int posicionPunto = nombre.lastIndexOf('.');
        if (posicionPunto > 0 && posicionPunto < nombre.length() - 1) {
            return nombre.substring(posicionPunto + 1);
        } else {
            throw new IllegalArgumentException("ERROR: El archivo no tiene extensión");
        }
    }

    public static String formatearFecha(long milis, String patronFecha) {
        LocalDateTime fecha = LocalDateTime.ofInstant(Instant.ofEpochMilli(milis),
                ZoneId.systemDefault());
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return formateador.format(fecha);
    }

    public static void validarDirectorio(File dir) throws DirectorioNoExisteException, NoEsDirectorioException {
        if (!dir.exists()) {
            throw new DirectorioNoExisteException("La ruta no existe: " + dir.getAbsolutePath());
        } else if (!dir.isDirectory()) {
            throw new NoEsDirectorioException("La ruta no corresponde a un directorio: " + dir.getAbsolutePath());
        }
    }

    public static void validarExistenciaArchivo(File arch) throws ArchivoNoExisteException {
        if (!arch.exists()) {
            throw new ArchivoNoExisteException();
        }
    }

    public static void mostrarInfo(File f, String sangria) {
        final long KB = (long) 1024.0;
        String patronFecha = "dd/MM/yyyy HH:mm:ss";

        if (f.isFile()) {
            String fechaFormateada = Utilidades.formatearFecha(f.lastModified(), patronFecha);

            System.out.println(sangria + f.getName() + " " + "<FICHERO> " + f.length() / KB + " KB "
                    + fechaFormateada);
        } else {
            // ES DIRECTORIO
            System.out.println(sangria + f.getName() + " " + "<DIR>");
        }
    }
}
