package ud1.actividad4jaxb.clases.clasesCorredor;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "corredores")
@XmlAccessorType(XmlAccessType.FIELD)
public class Corredores {

    public Corredores() {
    }

    @XmlElements({
            @XmlElement(name = "velocista", type = Velocista.class),
            @XmlElement(name = "fondista", type = Fondista.class)
    })
    List<Corredor> corredores;

    public void setCorredores(List<Corredor> corredores) {
        this.corredores = corredores;
    }

    public List<Corredor> getCorredores() {
        return corredores;
    }

    @Override
    public String toString() {
        StringBuilder salida = new StringBuilder();
        for (Corredor corredor : corredores) {
            salida.append("\n").append(corredor.toString());
        }
        return salida.toString();
    }


}
