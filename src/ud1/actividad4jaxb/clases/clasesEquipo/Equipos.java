package ud1.actividad4jaxb.clases.clasesEquipo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name ="equipos")
@XmlAccessorType(XmlAccessType.FIELD)
public class Equipos {
    @XmlElement(name = "equipo",required = true)
    private List<Equipo> equipos = new ArrayList<>();
    public Equipos(){}
    public Equipos(List<Equipo> equipos){
        this.equipos = equipos;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    @Override
    public String toString() {
        StringBuilder salida = new StringBuilder();
        for (Equipo equipo : equipos){
            salida.append("\n").append(equipo.toString());
        }
        return salida.toString();
    }

    public Equipo getEquipo() {
        return equipos.isEmpty() ? null : equipos.get(0);
    }
}
