package ud1.refuerzo.mareas.excepciones;

public class ExcepcionXML extends RuntimeException {
    public ExcepcionXML(String mensaje){
        super(mensaje);
    }
    public ExcepcionXML(String mensaje, Throwable causa){
        super(mensaje,causa);
    }
}