package ud1.actividad3.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import ud1.actividad3.servicio.Utilidades;

public class Corredor implements Serializable {
    protected int dorsal;
    protected String nombre;
    protected LocalDate fechanacimiento;
    protected int equipo;
    protected Puntuacion[] puntuaciones;

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
    }

    @Override
    public String toString() {
        return nombre + " (" + fechanacimiento + ") - Equipo " + equipo+" - Dorsal " + dorsal;
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

    public Puntuacion[] getPuntuaciones() {
        return puntuaciones;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }
}
