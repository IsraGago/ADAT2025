package ud1.actividad5personas.clases.contactos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Telefonos extends Contacto{
    @XmlElement(name = "Telefono", required = true)
    private List<Telefono> telefonos;

    public void setTelefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public Telefonos(){
        telefonos = new ArrayList<>();
    }
    public Telefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }
}
