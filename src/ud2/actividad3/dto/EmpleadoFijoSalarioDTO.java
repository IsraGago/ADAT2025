package ud2.actividad3.dto;

public class EmpleadoFijoSalarioDTO {
    String nss;
    String nombre;
    String apellido1;
    String apellido2;
    double salario;

    @Override
    public String toString() {
        return "EmpleadoFijoSalarioDTO{" +
                "nss='" + nss + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +
                ", salario=" + salario +
                '}';
    }

    public EmpleadoFijoSalarioDTO(String nss, String nombre, String apellido1, String apellido2, double salario) {
        this.nss = nss;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.salario = salario;
    }
}
