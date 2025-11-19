package ud1.actividad5.clases.clasesEquipo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Equipo {
    @XmlAttribute (name = "id", required = true)
    private String idEquipo;
    @XmlElement (name = "nombre",required = true)
    private String nombre;

    @XmlElement (name = "patrocinadores",required = true)
    private Patrocinadores patrocinadores;
    @XmlElement (name = "numPatrocinadores",required = true)
    private int numPatrocinadores;

    // CONSTRUCTOR

    public Equipo() {
    }

    public Equipo(String id, String nombre) {
        this.idEquipo = id;
        this.nombre = nombre;
    }

    public Equipo(String nombre, Patrocinadores patrocinadores) {
        this.nombre = nombre;
        this.patrocinadores = patrocinadores;
    }


    @Override
    public String toString() {
        return "ID: "+idEquipo+" | "+"Nombre: " + nombre + " | " + "Nº Patrocinadores: " + getNumPatrocinadores();
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Equipo other = (Equipo) obj;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        return true;
    }

    // GETTERS Y SETTERS
    public String getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(String idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumPatrocinadores() {
        return patrocinadores != null ? patrocinadores.getPatrocinadores().size() : 0;
    }


}
