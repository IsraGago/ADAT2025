package ud1.actividad5personas.clases.contactos;

import jakarta.xml.bind.annotation.*;


@XmlAccessorType(XmlAccessType.FIELD)
public class Telefono {

    @XmlValue
    private String telefono;
    @XmlAttribute(name = "tipo",required = true)
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Telefono(){}
    public Telefono(String telefono, String tipo) {
        this.telefono = telefono;
        this.tipo = tipo;
    }
}
