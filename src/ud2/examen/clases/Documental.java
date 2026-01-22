package ud2.examen.clases;
/**
 * @author ISRAEL BEJAMÍN GAGO ACUÑA 54321342b
 */
import java.time.LocalDate;

public class Documental extends Fotografia{
    private String tipo;

    public Documental(String nombre, String medidas, LocalDate fecha, char color, String tipo) {
        super(nombre, medidas, fecha, color);
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
