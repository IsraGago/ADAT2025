package ud1.actividad4.clases;

import java.util.HashSet;
import java.util.Set;
import ud1.actividad4.clases.Patrocinador;

public class Equipo {
    private String idEquipo;
    private String nombre;
    private Set<Patrocinador> patrocinadores = new HashSet<>();
    private boolean estaBorrado = false;
    private int numPatrocinadores;

    // CONSTRUCTOR
    public Equipo(String id, String nombre) {
        this.idEquipo = id;
        this.nombre = nombre;
    }

    public Equipo(String nombre, Set<Patrocinador> patrocinadores) {
        this.nombre = nombre;
        this.patrocinadores = patrocinadores;
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
        return "ID: "+idEquipo+" | "+"Nombre: " + nombre + " | " + "Nº Patrocinadores: " + getNumPatrocinadores() +" | " + "Esta Borrado: " + estaBorrado;
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
    public String getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(String idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumPatrocinadores() {
        return patrocinadores.size();
    }

    public Set<Patrocinador> getPatrocinadores() {
        return patrocinadores;
    }

    public boolean addPatrocinador(Patrocinador p) {
        return patrocinadores.add(p);
    }

    public boolean quitarPatrocinador(Patrocinador p){
        return patrocinadores.remove(p);
    }

    public boolean editarPatrocinador(Patrocinador p){
        for (Patrocinador patrocinador : patrocinadores) {
            if (patrocinador.equals(p)) {
                patrocinador.setDonacion(p.getDonacion());
                patrocinador.setFechaInicio(p.getFechaInicio());
                return true;
            }
        }
        return false;
    }

    public void setPatrocinadores(Set<Patrocinador> patrocinadores) {
        this.patrocinadores = patrocinadores;
    }

    public boolean estaBorrado() {
        return estaBorrado;
    }

    public void setEstaBorrado(boolean estaBorrado) {
        this.estaBorrado = estaBorrado;
    }

}
