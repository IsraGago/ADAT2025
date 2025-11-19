package ud1.actividad5personas.logica;

import jakarta.xml.bind.annotation.XmlAttribute;

public class Empresa {
    @XmlAttribute(name = "puesto",required = true)
    private String puesto;
}
