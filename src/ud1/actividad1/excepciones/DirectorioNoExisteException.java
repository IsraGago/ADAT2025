package ud1.actividad1.excepciones;

public class DirectorioNoExisteException extends Exception{
    public DirectorioNoExisteException(String mensaje) {
        super(mensaje);
    }

    public DirectorioNoExisteException(){
        this("El directorio no existe.");
    }
}
