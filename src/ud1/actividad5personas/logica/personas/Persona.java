package ud1.actividad5personas.logica.personas;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ud1.actividad5personas.logica.contactos.Contacto;
import ud1.actividad5personas.logica.contactos.Email;
import ud1.actividad5personas.logica.contactos.Telefonos;
import ud1.actividad5personas.persistencia.LocalDateAdapter;

import java.time.LocalDate;
@XmlTransient // no se van a crear objetos de esta clase
@XmlAccessorType(XmlAccessType.FIELD)// para que la clase padre tenga conciencia de sus clases hijas
@XmlSeeAlso({Trabajador.class,Estudiante.class})
public abstract class Persona {
    @XmlElement(name = "Nombre")
    private String nombre;
    @XmlElement(name = "FechaNacimiento")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaNacimiento;
    @XmlElements({
            @XmlElement(name = "Telefono",type = Telefonos.class),
            @XmlElement(name = "Email",type = Email.class)
    })
    private Contacto contacto;

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
}
