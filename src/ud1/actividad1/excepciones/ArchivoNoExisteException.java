package ud1.actividad1.excepciones;

public class ArchivoNoExisteException extends Exception {
    public ArchivoNoExisteException(String mensaje) {
        super(mensaje);
    }

    public ArchivoNoExisteException(){
        this("El archivo no existe.");
    }
}
