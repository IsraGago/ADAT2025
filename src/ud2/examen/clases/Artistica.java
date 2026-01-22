package ud2.examen.clases;
/**
 * @author ISRAEL BEJAMÍN GAGO ACUÑA 54321342b
 */
import java.time.LocalDate;

public class Artistica extends Fotografia{
    private String encuadre;
    private String composicion;

    public Artistica(String nombre, String medidas, LocalDate fecha, char color,String encuadre, String composicion) {
        super(nombre, medidas, fecha, color);
        this.encuadre = encuadre;
        this.composicion = composicion;
    }

    public String getEncuadre() {
        return encuadre;
    }


    public String getComposicion() {
        return composicion;
    }

}
