package ud1.actividad1.excepciones;

public class NoEsDirectorioException extends Exception {
    public NoEsDirectorioException(String mensaje) {
        super(mensaje);
    }

    public NoEsDirectorioException(){
        this("La ruta proporcionada no corresponde a un directorio.");
    }
}
