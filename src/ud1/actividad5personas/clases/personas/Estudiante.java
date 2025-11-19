package ud1.actividad5personas.clases.personas;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import ud1.actividad5personas.clases.contactos.Contacto;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class Estudiante extends Persona {
    @XmlElement(name = "Universidad",required = true)
    private String universidad;
    @XmlElement(name = "Carrera",required = true)
    private String carrera;

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    @Override
    public String toString() {
        return getNombre()+", estudia en "+getUniversidad()+" la carrera de: "+getCarrera()+"\n";
    }

    public Estudiante(){}
    public Estudiante(String nombre, LocalDate fechaNacimiento, Contacto contacto, String universidad, String carrera) {
        super(nombre,fechaNacimiento,contacto);
        this.universidad = universidad;
        this.carrera = carrera;
    }
}
