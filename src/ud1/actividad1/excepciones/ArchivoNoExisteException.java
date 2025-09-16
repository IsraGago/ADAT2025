package ud1.actividad1.excepciones;

public class ArchivoNoExisteException extends Exception {
    public ArchivoNoExisteException() {
        super("El archivo no existe.");
    }
}
