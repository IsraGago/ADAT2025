package ud1.actividad1.excepciones;

public class DirectorioNoExisteException extends Exception{
    public DirectorioNoExisteException() {
        super("El directorio no existe.");
    }
}
