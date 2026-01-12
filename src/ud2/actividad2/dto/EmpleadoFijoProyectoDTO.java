package ud2.actividad2.dto;

public class EmpleadoFijoProyectoDTO {
    String nombreCompleto;
    double salario;
    String nombreDepartamento;

    public EmpleadoFijoProyectoDTO(String nombreCompleto, double salario, String nombreDepartamento) {
        this.nombreCompleto = nombreCompleto;
        this.salario = salario;
        this.nombreDepartamento = nombreDepartamento;
    }

    @Override
    public String toString() {
        return "EmpleadoFijoProyectoDTO{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", salario=" + salario +
                ", nombreDepartamento='" + nombreDepartamento + '\'' +
                '}';
    }
}
