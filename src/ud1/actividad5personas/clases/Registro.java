package ud1.actividad5personas.clases;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ud1.actividad5personas.clases.contactos.Email;
import ud1.actividad5personas.clases.contactos.Telefonos;
import ud1.actividad5personas.clases.personas.Estudiante;
import ud1.actividad5personas.clases.personas.Persona;
import ud1.actividad5personas.clases.personas.Trabajador;
import ud1.actividad5personas.persistencia.LocalDateAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Registro")
@XmlAccessorType(XmlAccessType.FIELD)
public class Registro {



    @XmlAttribute(name = "version", required = true)
    private double version;
    @XmlAttribute(name = "fechaCreacion",required = true)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaCreacion;
    @XmlElement(name ="Categorias")
    @XmlList
    private List<String> categorias;

    @XmlElements({
            @XmlElement(name = "Trabajador",type = Trabajador.class),
            @XmlElement(name = "Estudiante",type = Estudiante.class)
    })
    @XmlElementWrapper (name = "Personas")
    private List<Persona> personas;

    public Registro() {
        this.personas = new ArrayList<>();
        this.categorias = new ArrayList<>();
    }

    public Registro(double version, LocalDate fechaCreacion, List<String> categorias, List<Persona> personas) {
        this.version = version;
        this.fechaCreacion = fechaCreacion;
        this.categorias = categorias;
        this.personas = personas;
    }

    @Override
    public String toString() {
        return "Registro{" +
                "version=" + version +
                ", fechaCreacion=" + fechaCreacion +
                ", categorias=" + categorias +
                ", personas=" + personas +
                '}';
    }
}
