package ud1.actividad5personas.clases;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Empresa {
    @XmlValue
    private String nombre;
    @XmlAttribute(name = "puesto",required = true)
    private String puesto;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Empresa(){}

    public Empresa(String nombre, String puesto) {
        this.nombre = nombre;
        this.puesto = puesto;
    }
}
