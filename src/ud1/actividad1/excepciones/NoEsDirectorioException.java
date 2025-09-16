package ud1.actividad1.excepciones;

public class NoEsDirectorioException extends Exception {
    public NoEsDirectorioException() {
        super("La ruta proporcionada no corresponde a un directorio.");
    }
}
