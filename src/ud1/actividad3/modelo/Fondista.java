package ud1.actividad3.modelo;

import java.time.LocalDate;

public class Fondista extends Corredor {
    private static final long serialVersionUID = 2L;
    private float distanciaMax; // en KM
    
    public Fondista(String nombre,LocalDate fechanacimiento,int equipo,float distanciaMax) {
        super(nombre,fechanacimiento,equipo);
        if (distanciaMax <= 0) {
            throw new IllegalArgumentException("La distancia máxima debe ser positiva");
        }
        this.distanciaMax = distanciaMax;
    }
}
