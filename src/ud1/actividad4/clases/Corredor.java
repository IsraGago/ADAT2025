package ud1.actividad4.clases;

import java.time.LocalDate;
import java.util.ArrayList;

import ud1.actividad4.servicio.Utilidades;

public class Corredor {
    protected int dorsal;
    protected String codigo;
    protected String nombre;
    protected LocalDate fechanacimiento;
    protected int equipo;
    protected ArrayList<Puntuacion> historial;

    // TODO HACER QUE EQUIPO SEA STRING

    // public Corredor(String nombre, LocalDate fechanacimiento, int equipo) {
    //     if (!Utilidades.esNombreValido(nombre)) {
    //         throw new IllegalArgumentException("Nombre no válido");
    //     }
    //     if (fechanacimiento == null || fechanacimiento.isAfter(LocalDate.now())) {
    //         throw new IllegalArgumentException("Fecha de nacimiento no válida");
    //     }
    //     if (equipo < 0) {
    //         throw new IllegalArgumentException("El número de equipo no puede ser negativo");
    //     }
        
    //     this.nombre = nombre;
    //     this.fechanacimiento = fechanacimiento;
    //     this.equipo = equipo;
    //     historial = new ArrayList<>();
    // }

    public Corredor(String codigo,int dorsal, String nombre, LocalDate fechanacimiento, int equipo){
        if (Utilidades.esCodigoValido(codigo)) {
            throw new IllegalArgumentException("El código no tiene un formato válido.");
        }
        if (!Utilidades.esNombreValido(nombre)) {
            throw new IllegalArgumentException("Nombre no válido");
        }
        if (fechanacimiento == null || fechanacimiento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Fecha de nacimiento no válida");
        }
        if (equipo < 0) {
            throw new IllegalArgumentException("El número de equipo no puede ser negativo");
        }
        if (dorsal < 1) {
            throw new IllegalArgumentException("El dorsal debe ser mayor que 0");
        }
        this.codigo = codigo;
        this.dorsal = dorsal;
        this.nombre = nombre;
        this.fechanacimiento = fechanacimiento;
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public int getDorsal() {
        return dorsal;
    }

    public String getCodigo() {
        return codigo;
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

    public void setHistorial(ArrayList<Puntuacion> historial) {
        this.historial = historial;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void addPuntuacion(Puntuacion p) {
        if (p == null || historial.contains(p)) {
            throw new IllegalArgumentException("La puntuación no puede ser nula ni repetida.");
        }
        historial.add(p);
        historial.sort((p1, p2) -> Integer.compare(p1.getAnio(), p2.getAnio())); // Ordena las puntuaciones por año
    }

    public boolean quitarPuntuacion(Puntuacion p) {
        return historial.remove(p);
    }

    public void mostrarInformacion() {
        System.out.println("CORREDOR " + this.getClass().getSimpleName().toUpperCase() + ": " + this);
        System.out.println("NOMBRE: " + this.getNombre() + " - FECHA NACIMIENTO: " + this.getFechanacimiento()
                + " - EQUIPO: " + this.getEquipo() + " - DORSAL: " + this.getDorsal() + " - PUNTUACIONES: "
                + this.historial);
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
