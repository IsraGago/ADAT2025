package ud1.actividad5personas.logica.personas;

import jakarta.xml.bind.annotation.XmlElement;
import ud1.actividad5personas.logica.Empresa;

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
}
