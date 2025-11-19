package ud1.actividad5personas.clases.personas;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import ud1.actividad5personas.clases.Empresa;
import ud1.actividad5personas.clases.contactos.Contacto;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class Trabajador extends Persona {
    @XmlElement(name = "Empresa",required = true)
    private Empresa empresa;
    @XmlElement(name = "Salario",required = true)
    private double salario;

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return getNombre()+", trabaja en: "+empresa.getNombre()+" con un salario de: "+salario+"\n";
    }

    public Trabajador(){}

    public Trabajador(String nombre, LocalDate fechaNacimiento, Contacto contacto, double salario, Empresa empresa) {
        super(nombre,fechaNacimiento,contacto);
        this.salario = salario;
        this.empresa = empresa;
    }
}
