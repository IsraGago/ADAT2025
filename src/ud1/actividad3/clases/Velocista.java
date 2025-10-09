package ud1.actividad3.clases;

public class Velocista extends Corredor{
    private static final long serialVersionUID = 3L;
    private float velocidadMedia; // EN KM/H
    public Velocista(String nombre,java.time.LocalDate fechanacimiento,int equipo,float velocidadMedia) {
        super(nombre,fechanacimiento,equipo);
        if (velocidadMedia <= 0) {
            throw new IllegalArgumentException("La velocidad media debe ser positiva");
        }
        this.velocidadMedia = velocidadMedia;
    }
}
