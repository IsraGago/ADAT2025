package ud1.actividad4.clases;

import java.time.LocalDate;

public class Fondista extends Corredor {
    private float distanciaMax; // en KM

    public Fondista(String codigo, int dorsal, String nombre, LocalDate fecha, String equipo, float distanciaMax) {
        super(codigo,dorsal,nombre,fecha,equipo);
        if (distanciaMax <= 0) {
            throw new IllegalArgumentException("La distancia máxima debe ser positiva");
        }
        this.distanciaMax = distanciaMax;
    }

    public float getDistanciaMax() {
        return distanciaMax;
    }

    public void setDistanciaMax(float distanciaMax) {
        this.distanciaMax = distanciaMax;
    }
}
