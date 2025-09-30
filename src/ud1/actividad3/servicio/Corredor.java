package ud1.actividad3.servicio;

import java.io.Serializable;
import java.time.LocalDate;

public class Corredor implements Serializable{
    protected int dorsal;
    protected String nombre;
    protected LocalDate fechanacimiento;
    protected int equipo;
    protected Puntuacion[] puntuaciones;

    public Corredor(String nombre,LocalDate fechanacimiento,int equipo) {
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


}
