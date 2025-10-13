package ud1.actividad3new.clases;

import java.util.HashSet;
import java.util.Set;

public class Equipo {
    private int idEquipo;
    private String nombre;
    private int numPatrocinadores;
    private Set<Patrocinador> patrocinadores = new HashSet<>();
    private boolean estaBorrado = false;

    // CONSTRUCTOR
    public Equipo(String nombre, Set<Patrocinador> patrocinadores) {
        this.nombre = nombre;
        this.patrocinadores = patrocinadores;
        numPatrocinadores = patrocinadores.size();
    }

    public int getBytesAEscribir() {
        int bytesAEscribir = -1;
        try {
            bytesAEscribir = 4 + nombre.getBytes("UTF-8").length + 2 + 4 + 1 + getBytesAEscribirPatrocinadores();
            // bytesAEscribir = Integer.BYTES + nombre.getBytes("UTF-8").length + 2 + Integer.BYTES + 1 + getBytesAEscribirPatrocinadores();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytesAEscribir;

    }

    @Override
    public String toString() {
        return "ID: "+idEquipo+" | "+"Nombre: " + nombre + " | " + "Nº Patrocinadores: " + numPatrocinadores;
    }

    public int getBytesAEscribirPatrocinadores() {
        int bytesAEscribir = 0;
        for (Patrocinador patrocinador : patrocinadores) {
            bytesAEscribir += patrocinador.getBytesAEscribir();
        }
        return bytesAEscribir;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
        Equipo other = (Equipo) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        return true;
    }

    // GETTERS Y SETTERS
    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumPatrocinadores() {
        return numPatrocinadores;
    }

    public Set<Patrocinador> getPatrocinadores() {
        return patrocinadores;
    }

    public boolean addPatrocinador(Patrocinador p) {
        if (patrocinadores.add(p)) {
            numPatrocinadores = patrocinadores.size();
            return true;
        }
        return false;
    }

    public void setPatrocinadores(Set<Patrocinador> patrocinadores) {
        this.patrocinadores = patrocinadores;
    }

    public boolean isEstaBorrado() {
        return estaBorrado;
    }

    public void setEstaBorrado(boolean estaBorrado) {
        this.estaBorrado = estaBorrado;
    }

}
