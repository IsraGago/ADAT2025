package ud1.actividad1.servicio;

import static ud1.actividad1.servicio.Utilidades.mostrarInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;

import ud1.actividad1.excepciones.DirectorioNoExisteException;
import ud1.actividad1.excepciones.NoEsDirectorioException;

public class OperacionesNIO {
    public static void visualizarContenido(String ruta) throws DirectorioNoExisteException, NoEsDirectorioException {
        Path path = Paths.get(ruta);
        Utilidades.validarDirectorio(path);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            // for (Path p : stream) {
            // Utilidades.mostrarInfo(p.toFile());
            // }
            stream.forEach(p -> Utilidades.mostrarInfo(p.toFile()));
            // stream.forEach(Utilidades::mostrarInfo); // SOBRECARGANDO MOSTRAR INFO PARA
            // QUE ADMITA PATH
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // PROGRAMACIÓN FUNCIONAL
        // try (Stream<Path> s = Files.list(path)) {
        // s.forEach(System.out::println);
        // } catch (Exception e) {
        // System.out.println(e.getMessage());
        // }

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

        try (Stream<Path> stream = Files.walk(path)) {
            // stream.forEach(p -> { // EXPRESIÓN LAMBDA
            // String sangria = Utilidades.calcularSangria(p,path);
            // try {
            // Utilidades.mostrarInfo(p.toFile(), sangria); // PUEDE DAR EXCEPCIONES POR
            // PERMISOS
            // } catch (Exception e) {
            // System.out.println(e.getMessage());
            // }
            // });

            stream.filter(p -> Files.isReadable(p)).forEach(p -> {
                String sangria = Utilidades.calcularSangria(p, path);
                Utilidades.mostrarInfo(p.toFile(), sangria);
            });

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // public static void recorrerRecursivo2(String ruta){
    // Path dir = Paths.get(ruta);
    // Files.walkFileTree(dir, new SimpleFileVisitor<Path>()); //TODO EL CONSTRUCTOR
    // NO ES VISIBLE (?)
    // }

    public static void filtrarPorExtension(String ruta, String extension) {
        Path path = Paths.get(ruta);
        extension = Utilidades.limpiarPuntoExtension(extension);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*." + extension);) {
            boolean encontrado = false;
            for (Path p : stream) {
                Utilidades.mostrarInfo(p);
                encontrado = true;
            }
            if (!encontrado) {
                System.out.println("No se ha encontrado ningún archivo con la extensión: ." + extension);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void filtrarPorExtensionRecursiva(String ruta, String extension) {
        Path path = Paths.get(ruta);
        final String EXTENSION = Utilidades.limpiarPuntoExtension(extension);
        try (Stream<Path> s = Files.walk(path)) {
            Stream<Path> filtrados = s.filter(p -> {
                /*
                 * 1. Archivo regular, no directorio
                 * 2. que tenga permisos de lectura
                 * 3. que tenga la extensión adecuada
                 */
                return Files.isRegularFile(p) && Files.isReadable(path) && p.toString().endsWith(EXTENSION);
            });
            filtrados.forEach(p -> {
                String santria = Utilidades.calcularSangria(p, path);
                Utilidades.mostrarInfo(p.toFile(), santria);
            });
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void filtrarPorExtensionYOrdenar(String ruta, String extension, boolean descendente) {
        Path path = Paths.get(ruta);
        extension = Utilidades.limpiarPuntoExtension(extension);
        try {
            Utilidades.validarDirectorio(path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*." + extension);) {
            ArrayList<Path> lista = new ArrayList<Path>();
            for (Path p : stream) {
                lista.add(p);
            }
            if (!lista.isEmpty()) {
                Comparator<Path> comp = Comparator.comparing(p -> p.getFileName().toString().toLowerCase());
                // Comparator<Path> comp2 = (p1, p2) ->
                // p1.getFileName().toString().toLowerCase()
                // .compareTo(p2.getFileName().toString().toLowerCase());
                if (descendente) {
                    lista.sort(comp.reversed());
                } else {
                    lista.sort(comp);
                }
                lista.forEach(Utilidades::mostrarInfo);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void filtrarPorExtensionOrdenarRecursiva(String ruta, String extension, boolean descendente) {
        Path path = Paths.get(ruta);
        final String EXTENSION = Utilidades.limpiarPuntoExtension(extension);
        ArrayList<Path> listaPaths = new ArrayList<Path>();
        try (Stream<Path> s = Files.walk(path)) {
            Stream<Path> filtrados = s.filter(p -> {
                return Files.isRegularFile(p) && Files.isReadable(path) && p.toString().endsWith(EXTENSION);
            });
            filtrados.forEach(p -> {
                listaPaths.add(p);
            });

            Comparator<Path> comp = (p1, p2) -> p1.getFileName().toString().toLowerCase()
                    .compareTo(p2.getFileName().toString().toLowerCase());

            if (descendente) {
                listaPaths.sort(comp.reversed());
            } else {
                listaPaths.sort(comp);
            }

            for (Path p : listaPaths) {
                String santria = Utilidades.calcularSangria(p, path);
                Utilidades.mostrarInfo(p.toFile(), santria);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void filtrarPorSubcadena(String ruta, String subcadena) {
        Path path = Paths.get(ruta);
        
    }

    public static void copiarArchivo(String origen, String destino) {
    }

    public static void moverArchivo(String origen, String destino) {
        Path pOrigen = Paths.get(origen);
        Path pDestino = Paths.get(destino);
        try {
            boolean mover = false;
            char[] caracteresValidos = { 's', 'n' };
            if (Files.exists(pDestino)) {
                mover = Utilidades.leerChar("Sobreescribir " + pDestino + " (s/n): ", caracteresValidos) == 's';
                if (mover) {
                    Files.move(pOrigen, pDestino, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    System.out.println("Operación cancelada");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void copiarDirectorio(String origen, String destino) {
    }

    public static void borrar(String ruta) {
    }

    public static void main(String[] args) {
        String ruta = "D:/igagoacun/prueba";
        String extension = ".txt";
        try {
            // visualizarContenido(ruta);
            // recorrerRecursivo(ruta);
            // OperacionesIO.recorrerRecursivo(ruta);
            // filtrarPorExtension(ruta, extension);
            // filtrarPorExtensionRecursiva(ruta, extension);
            // filtrarPorExtensionYOrdenar(ruta, extension, false);
            filtrarPorExtensionOrdenarRecursiva(ruta, extension, false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
