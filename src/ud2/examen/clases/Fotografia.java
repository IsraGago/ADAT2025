package ud2.examen.clases;

import java.time.LocalDate;
/**
 * @author ISRAEL BEJAMÍN GAGO ACUÑA 54321342b
 */
public class Fotografia {
    protected int codigo;
    protected String nombre;
    protected String medidas;
    protected LocalDate fecha;
    protected int codFotografo;
    protected int codExposicion;
    protected char color;

    public Fotografia(String nombre, String medidas, LocalDate fecha, char color) {
        this.nombre = nombre;
        this.medidas = medidas;
        this.fecha = fecha;
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMedidas() {
        return medidas;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public char getColor() {
        return color;
    }

}
