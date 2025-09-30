package ud1.actividad3.servicio;

import java.time.LocalDate;

public class Fondista extends Corredor {
    private float distanciaMax; // en KM
    
    public Fondista(String nombre,LocalDate fechanacimiento,int equipo,float distanciaMax) {
        super(nombre,fechanacimiento,equipo);
        if (distanciaMax <= 0) {
            throw new IllegalArgumentException("La distancia máxima debe ser positiva");
        }
        this.distanciaMax = distanciaMax;
    }
}
