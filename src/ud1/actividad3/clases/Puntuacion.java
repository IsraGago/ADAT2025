package ud1.actividad3.clases;

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
    public String toString() {
        return anio+" = "+puntos;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + anio;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Puntuacion other = (Puntuacion) obj;
        if (anio != other.anio)
            return false;
        return true;
    }

}
