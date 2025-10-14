package ud1.actividad3new.clases;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import ud1.actividad3.servicio.Utilidades;

public class Corredor implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int dorsal;
    protected String nombre;
    protected LocalDate fechanacimiento;
    protected int equipo;
    protected ArrayList<Puntuacion> historial;

    public Corredor(String nombre, LocalDate fechanacimiento, int equipo) {
        if (!Utilidades.esNombreValido(nombre)) {
            throw new IllegalArgumentException("Nombre no válido");
        }
        if (fechanacimiento == null || fechanacimiento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Fecha de nacimiento no válida");
        }
        if (equipo < 0) {
            throw new IllegalArgumentException("El número de equipo no puede ser negativo");
        }
        this.nombre = nombre;
        this.fechanacimiento = fechanacimiento;
        this.equipo = equipo;
        historial = new ArrayList<>();
    }

    @Override
    public String toString() {
        return nombre;
    }

    public int getDorsal() {
        return dorsal;
    }

    public int getEquipo() {
        return equipo;
    }

    public LocalDate getFechanacimiento() {
        return fechanacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Puntuacion> getHistorial() {
        return historial;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public void addPuntuacion(Puntuacion p) {
        if (p == null || historial.contains(p)) {
            throw new IllegalArgumentException("La puntuación no puede ser nula ni repetida.");
        }
        historial.add(p);
        historial.sort((p1,p2) -> Integer.compare(p1.getAnio(),p2.getAnio())); // Ordena las puntuaciones por año
    }

    public boolean quitarPuntuacion(Puntuacion p){
        return historial.remove(p);
    }

    public void mostrarInformacion() {
        System.out.println("CORREDOR " + this.getClass().getSimpleName().toUpperCase() + ": " + this);
        System.out.println("NOMBRE: " + this.getNombre() + " - FECHA NACIMIENTO: " + this.getFechanacimiento()
                + " - EQUIPO: " + this.getEquipo() + " - DORSAL: " + this.getDorsal()+ " - PUNTUACIONES: "+this.historial);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((fechanacimiento == null) ? 0 : fechanacimiento.hashCode());
        result = prime * result + equipo;
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
        Corredor other = (Corredor) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (fechanacimiento == null) {
            if (other.fechanacimiento != null)
                return false;
        } else if (!fechanacimiento.equals(other.fechanacimiento))
            return false;
        if (equipo != other.equipo)
            return false;
        return true;
    }

    
}
