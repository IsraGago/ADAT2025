package ud1.actividad1;

import java.util.Scanner;

import ud1.actividad1.excepciones.DirectorioNoExisteException;
import ud1.actividad1.excepciones.NoEsDirectorioException;
import ud1.actividad1.servicio.OperacionesIO;

public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws DirectorioNoExisteException, NoEsDirectorioException {

        String ruta = "";
        String extension = "";
        String rutaDestino = "";

        int respuesta;

        do {
            mostrarMenu();
            respuesta = leerInt();
            switch (respuesta) {
                case 1: // isualizar contenido de un directorio
                    ruta = preguntarRuta("Introduce la ruta del directorio: ");
                    try {
                        OperacionesIO.visualizarContenido(ruta);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2: // Recorrido recursivo de un directorio
                    ruta = preguntarRuta("Introduce al ruta del directorio: ");
                    OperacionesIO.recorrerRecursivo(ruta);
                    break;
                case 3: // Filtrar por extensión
                    ruta = preguntarRuta("Introduce al ruta del directorio: ");
                    System.out.println("Introduce la extensión (sin el .): ");
                    extension = sc.nextLine();

                    try {
                        OperacionesIO.filtrarPorExtension(ruta, extension);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4: // Filtrar por extensión y ordenar
                    ruta = preguntarRuta("Introduce al ruta del directorio: ");
                    System.out.println("Introduce la extensión: ");
                    System.out.println("Ordenar ascendentemente (a) o descendentemente (d): ");
                    boolean descendentemente = sc.nextLine().charAt(0) == 'd';
                    extension = sc.nextLine();
                    
                    try {
                        OperacionesIO.filtrarPorExtensionYOrdenar(ruta, extension, descendentemente);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5: // Filtrar por subcadena
                    ruta = preguntarRuta("Introduce al ruta del directorio: ");
                    System.out.println("Introduce la subdacena a buscar: ");
                    String subcadena = sc.nextLine();
                    OperacionesIO.filtrarPorSubcadena(ruta, subcadena);
                    break;
                case 6: // Copiar archivo
                    ruta = preguntarRuta("Introduce la ruta del archivo de origen: ");
                    rutaDestino = preguntarRuta("Introduce la ruta de destino:");
                    try {
                        OperacionesIO.copiarArchivo(ruta, rutaDestino);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7: // Mover archivo
                    ruta = preguntarRuta("Introduce la ruta del archivo de origen: ");
                    rutaDestino = preguntarRuta("Introduce la ruta de destino:");
                    try {
                        OperacionesIO.moverArchivo(extension, rutaDestino);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 8: // Copiar directorio
                    ruta = preguntarRuta("Introduce la ruta del directorio origen: ");
                    rutaDestino = preguntarRuta("Introduce la ruta de destino:");
                    try {
                        OperacionesIO.copiarDirectorio(extension, rutaDestino);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 9: // Borrar archivo o directorio
                    ruta = preguntarRuta("Introduce la ruta del archivo o directorio a borrar: ");
                    try {
                        OperacionesIO.borrar(ruta);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 0: // SALIR
                    break;

                default:
                    break;
            }
        } while (respuesta != 0);

    }

    private static String preguntarRuta(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }

    private static int leerInt() {
        int num;
        while (true) {
            System.out.print("Introduce un número entero: ");
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

    private static void mostrarMenu() {
        System.out.println("\n--------------");
        System.out.println("1. Visualizar contenido de un directorio");
        System.out.println("2. Recorrido recursivo de un directorio");
        System.out.println("3. Filtrar por extensión");
        System.out.println("4. Filtrar por extensión y ordenar");
        System.out.println("5. Filtrar por subcadena");
        System.out.println("6. Copiar archivo");
        System.out.println("7. Mover archivo");
        System.out.println("8. Copiar directorio");
        System.out.println("9. Borrar archivo o directorio");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }
}
