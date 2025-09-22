package ud1.actividad1.servicio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import ud1.actividad1.excepciones.ArchivoNoExisteException;
import ud1.actividad1.excepciones.DirectorioNoExisteException;
import ud1.actividad1.excepciones.NoEsDirectorioException;

public class Utilidades {
    public static Scanner sc = new Scanner(System.in);

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

    public static void validarDirectorio(Path path) throws DirectorioNoExisteException, NoEsDirectorioException {
        if (!Files.exists(path)) {
            throw new DirectorioNoExisteException("La ruta no existe: " + path);
        } else if (Files.isRegularFile(path)) {
            throw new NoEsDirectorioException("La ruta no corresponde a un directorio: " + path);
        }
    }

    public static void validarExistenciaArchivo(File arch) throws ArchivoNoExisteException {
        if (!arch.exists()) {
            throw new ArchivoNoExisteException();
        }
    }

    public static void mostrarInfo(File f){
        mostrarInfo(f,"");
    }

    public static void mostrarInfo(Path p){
        mostrarInfo(p.toFile(),"");
    }

    public static void mostrarInfo(File f, String sangria) {
        if (f == null) {
            throw new IllegalArgumentException("El archivo proporcionado es nulo.");
        }

        final double KB = 1024.0;
        String patronFecha = "dd/MM/yyyy HH:mm:ss";

        if (f.isFile()) {
            String fechaFormateada = Utilidades.formatearFecha(f.lastModified(), patronFecha);
            System.out.printf("├%s%s <FICHERO> %.2f KB %s%n",
                    sangria, f.getName(), f.length() / KB, fechaFormateada);
        } else if (f.isDirectory()) {
            System.out.printf("├%s%s <DIR>%n", sangria, f.getName());
        } else {
            System.out.printf("├%s%s <DESCONOCIDO>%n", sangria, f.getName());
        }
    }

    public static int leerInt(String mensaje) {
        int num;
        while (true) {
            System.out.print(mensaje);
            if (sc.hasNextInt()) {
                num = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                System.out.println("Valor no válido. Intenta de nuevo.");
                sc.next(); // Limpiar el valor incorrecto
            }
        }
        return num;
    }

    public static char leerChar(String mensaje, char[] caracteresValidos) {
        boolean charValido = false;
        char caracter = ' ';
        while (!charValido) {
            System.out.println(mensaje);
            try {
                caracter = sc.nextLine().toLowerCase().charAt(0);
                for (char c : caracteresValidos) {
                    if (c == caracter) {
                        charValido = true;
                    }
                }
                if (!charValido) {
                    System.out.print("Los caracteres permitidos son:");
                    for (char c : caracteresValidos) {
                        System.out.print(" " + c);
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR: El valor introducido debe ser un caracter.");
            }
        }
        return caracter;
    }

    public static void crearDirectorio(File dir) throws IOException {
        if (!dir.mkdirs()) {
            throw new IOException("No se pudo crear el directorio de destino: " + dir.getAbsolutePath());
        }
    }
}
