package ud1.actividad5.clases.clasesCorredor;

import ud1.actividad4.clases.Corredor;

import java.time.LocalDate;

public class Fondista extends Corredor {
    private float distanciaMax; // en KM

    public Fondista(){
        
    }

    public Fondista(String nombre, LocalDate fecha, String equipo, float distanciaMax) {
        super(nombre,fecha,equipo);
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
