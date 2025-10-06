package ud1.actividad3.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Puntuacion implements Serializable, Comparable<Puntuacion> {
    private static final long serialVersionUID = 4L;

    private int anio;
    private float puntos;

    public Puntuacion(int anio, float puntos) {
        if (anio < 1900 || anio > LocalDate.now().getYear()) {
            throw new IllegalArgumentException("Año no válido");
        }
        if (puntos < 0) {
            throw new IllegalArgumentException("Puntos no pueden ser negativos");
        }
        this.anio = anio;
        this.puntos = puntos;
    }

    public int getAnio() {
        return anio;
    }

    public float getPuntos() {
        return puntos;
    }

    @Override
    public int compareTo(Puntuacion o) {
        return Integer.compare(this.anio, o.anio);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Puntuacion) && this.anio == ((Puntuacion) obj).anio;
    }

    @Override
    public String toString() {
        return anio+" = "+puntos;
    }

}
