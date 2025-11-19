package ud1.actividad5personas.clases.contactos;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlTransient // no se van a crear objetos de esta clase
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Email.class, Telefonos.class})// para que la clase padre tenga conciencia de sus clases hijas
public abstract class Contacto {

}
