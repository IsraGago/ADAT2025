package ud1.actividad4jaxb.clases.clasesCorredor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
// @XmlType(propOrder ={"codigo","dorsal","equipo","nombre","fechaNacimiento","velocidadMedia","historial"})
public class Velocista extends Corredor {
    @XmlElement(name = "velocidad_media")
    private float velocidadMedia; // EN KM/H

    public Velocista(){
        
    }

    public Velocista(String nombre, LocalDate fecha, String equipo, float velocidadMedia) {
        super(nombre,fecha,equipo);
        if (velocidadMedia <= 0) {
            throw new IllegalArgumentException("La velocidad media debe ser positiva");
        }
        this.velocidadMedia = velocidadMedia;
    }

    public float getVelocidadMedia() {
        return velocidadMedia;
    }

    public void setVelocidadMedia(float velocidadMedia) {
        this.velocidadMedia = velocidadMedia;
    }
}
