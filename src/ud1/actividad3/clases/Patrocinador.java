package ud1.actividad3.clases;

import java.time.LocalDate;

import ud1.actividad3.servicio.Utilidades;

public class Patrocinador {
    private int id;
    private String nombre;
    private float donacion; // 4 BYTES
    private LocalDate fechaInicio; // 8 BYTES

    public Patrocinador(String nombre, float donacion, LocalDate fechaInicio) {
        this.nombre = nombre;
        this.donacion = donacion;
        this.fechaInicio = fechaInicio;
    }

    public int getBytesAEscribir() {
        int bytesAEscribir = -1;
        try {
            bytesAEscribir = (nombre.getBytes("UTF-8").length + 2) + Float.BYTES + Long.BYTES;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytesAEscribir;
    }

    public long fechaToLong() {
        return this.fechaInicio == null ? 0 : fechaInicio.toEpochDay();
    }

    public static LocalDate longToFecha(long fecha) {
        return fecha == 0 ? null : LocalDate.ofEpochDay(fecha);
    }

    public float getDonacion() {
        return donacion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.toLowerCase().hashCode());
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
        Patrocinador other = (Patrocinador) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equalsIgnoreCase(other.nombre))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s | donación: %2.f | %s", nombre, donacion,
                Utilidades.formatearFecha(fechaToLong(), "dd/MM/yyyy"));
    }

}
