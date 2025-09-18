package ud1.actividad1.servicio;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

import ud1.actividad1.excepciones.DirectorioNoExisteException;
import ud1.actividad1.excepciones.NoEsDirectorioException;

public class OperacionesNIO {
    public static void visualizarContenido(String ruta) throws DirectorioNoExisteException, NoEsDirectorioException {
        Path path = Paths.get(ruta);
        Utilidades.validarDirectorio(path);

        // PROGRAMACIÓN FUNCIONAL
        try (Stream<Path> s = Files.list(path)) {
            s.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // FORMA CLÁSICA
        // try (Stream<Path> stream = Files.list(path)) {
        // Iterator<Path> it = stream.iterator();
        // while (it.hasNext()) {
        // Path p = it.next();
        // System.out.println(p);
        // }
        // } catch (IOException e) {
        // System.out.println(e.getMessage());
        // }
    }

    public static void recorrerRecursivo(String ruta) throws DirectorioNoExisteException, NoEsDirectorioException {
        Path path = Paths.get(ruta);

        Utilidades.validarDirectorio(path);
        String sangria = "";

        try (Stream<Path> stream = Files.walk(path)) {
            Iterator<Path> it = stream.iterator();
            while (it.hasNext()) {
                Path p = it.next();
                System.out.println(p);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void filtrarPorExtension(String ruta, String extension) {
        Path path = Paths.get(ruta);
        if (extension.charAt(0) == '.') {
            extension = extension.substring(1);
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*."+extension);) {
            for (Path p : stream) {
                System.out.println(p);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void filtrarPorExtensionYOrdenar(String ruta, String extension, boolean descendente) {
    }

    public static void filtrarPorSubcadena(String ruta, String subcadena) {
    }

    public static void copiarArchivo(String origen, String destino) {
    }

    public static void moverArchivo(String origen, String destino) {
    }

    public static void copiarDirectorio(String origen, String destino) {
    }

    public static void borrar(String ruta) {
    }

    public static void main(String[] args) {
        String ruta = "D:/igagoacun/prueba";
        try {
            //visualizarContenido(ruta);
            //recorrerRecursivo(ruta);
            filtrarPorExtension(ruta, ".txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
