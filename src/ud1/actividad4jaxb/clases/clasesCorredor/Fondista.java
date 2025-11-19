package ud1.actividad4jaxb.clases.clasesCorredor;

import jakarta.xml.bind.annotation.*;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
// @XmlType(propOrder ={"codigo","dorsal","equipo","nombre","fechaNacimiento","distanciaMax","historial"})
public class Fondista extends Corredor {
    @XmlElement(name = "distacia_max")
    private float distanciaMax; // en KM

    public Fondista(){
        
    }

    public Fondista(String nombre, LocalDate fecha, String equipo, float distanciaMax) {
        super(nombre,fecha,equipo);
        if (distanciaMax <= 0) {
            throw new IllegalArgumentException("La distancia máxima debe ser positiva");
        }
        this.distanciaMax = distanciaMax;
    }

    public float getDistanciaMax() {
        return distanciaMax;
    }

    public void setDistanciaMax(float distanciaMax) {
        this.distanciaMax = distanciaMax;
    }
}
