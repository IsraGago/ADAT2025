package ud1.actividad5.clases.clasesEquipo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ud1.actividad3.servicio.Utilidades;
import ud1.actividad5.clases.LocalDateAdapter;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class Patrocinador {
    @XmlValue
    private String nombre;

    @XmlAttribute(name = "donacion",required = true)
    private float donacion;

    @XmlAttribute(name = "fecha_inicio",required = true)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaInicio;

    public Patrocinador() {
    }

    public Patrocinador(String nombre, float donacion, LocalDate fechaInicio) {
        this.nombre = nombre;
        this.donacion = donacion;
        this.fechaInicio = fechaInicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getDonacion() {
        return donacion;
    }

    public void setDonacion(float donacion) {
        this.donacion = donacion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public long fechaToLong() {
        return this.fechaInicio == null ? 0 : fechaInicio.toEpochDay();
    }

    public static LocalDate longToFecha(long fecha) {
        return fecha == 0 ? null : LocalDate.ofEpochDay(fecha);
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
        return String.format("%s | donación: %.2f | %s", nombre, donacion,
                Utilidades.formatearFecha(fechaToLong(), "dd/MM/yyyy"));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.toLowerCase().hashCode());
        return result;
    }
}
