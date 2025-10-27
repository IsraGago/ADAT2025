package ud1.actividad4.clases;

import java.time.LocalDate;

public class Velocista extends Corredor {
    private float velocidadMedia; // EN KM/H

    public Velocista(String nombre, LocalDate fecha, String equipo, float velocidadMedia) {
        super(nombre,fecha,equipo);
        if (velocidadMedia <= 0) {
            throw new IllegalArgumentException("La velocidad media debe ser positiva");
        }
        this.velocidadMedia = velocidadMedia;
    }

    public float getVelocidadMedia() {
        return velocidadMedia;
    }

    public void setVelocidadMedia(float velocidadMedia) {
        this.velocidadMedia = velocidadMedia;
    }
}
