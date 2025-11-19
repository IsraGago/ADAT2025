package ud1.actividad5personas.logica;

import jakarta.xml.bind.annotation.*;
import ud1.actividad5personas.logica.personas.Persona;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Registro")
@XmlAccessorType(XmlAccessType.FIELD)
public class Registro {

    @XmlAttribute(name = "version", required = true)
    private double version;
    @XmlAttribute(name = "fechaCreacion",required = true)
    private LocalDate fechaCreacion;
    @XmlElement(name ="categorias")
    @XmlList
    private List<String> categorias;

    @XmlElement(name = "Personas")
    private List<Persona> personas;

    public Registro() {
        this.personas = new ArrayList<>();
        this.categorias = new ArrayList<>();
    }
}
