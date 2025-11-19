package ud1.actividad5personas.clases.personas;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ud1.actividad5personas.clases.contactos.Contacto;
import ud1.actividad5personas.clases.contactos.Email;
import ud1.actividad5personas.clases.contactos.Telefonos;
import ud1.actividad5personas.persistencia.LocalDateAdapter;

import java.time.LocalDate;
@XmlTransient // no se van a crear objetos de esta clase
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Trabajador.class,Estudiante.class})// para que la clase padre tenga conciencia de sus clases hijas
public abstract class Persona {
    @XmlElement(name = "Nombre")
    protected String nombre;
    @XmlElement(name = "FechaNacimiento")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    protected LocalDate fechaNacimiento;
    @XmlElements({
            @XmlElement(name = "Telefonos",type = Telefonos.class),
            @XmlElement(name = "Email",type = Email.class)
    })
    protected Contacto contacto;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Persona(){}

    public Persona(String nombre, LocalDate fechaNacimiento, Contacto contacto) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.contacto = contacto;
    }
}
