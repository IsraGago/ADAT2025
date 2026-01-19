package ud2.actividad4.dto;

public class EmpleadosFijosMaxSalarioDepartamentoDTO {
    int numDepartamento;
    String nombreDepartamento;
    String nombre;
    String Apellido1;
    String Apellido2;
    double salario;

    public EmpleadosFijosMaxSalarioDepartamentoDTO(int numDepartamento, String nombreDepartamento, String nombre, String apellido1, String apellido2, double salario) {
        this.numDepartamento = numDepartamento;
        this.nombreDepartamento = nombreDepartamento;
        this.nombre = nombre;
        Apellido1 = apellido1;
        Apellido2 = apellido2;
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "EmpleadosFijosMaxSalarioDepartamentoDTO{" +
                "numDepartamento=" + numDepartamento +
                ", nombreDepartamento='" + nombreDepartamento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", Apellido1='" + Apellido1 + '\'' +
                ", Apellido2='" + Apellido2 + '\'' +
                ", salario=" + salario +
                '}';
    }
}
