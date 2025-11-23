package ud1.refuerzo.biblioteca.excepciones;

public class ExcepcionXML extends RuntimeException {
    public ExcepcionXML(String mensaje){
        super(mensaje);
    }
    public ExcepcionXML(String mensaje, Throwable causa){
        super(mensaje,causa);
    }
}