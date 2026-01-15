package ud2.actividad3.dto;

public class DepartamentoNumEpleadosDTO {
    int numDepartamento;
    String nombre;
    int numEmpleadosFijos;
    int numEmpleadosTemporales;

    public DepartamentoNumEpleadosDTO(int numDepartamento, String nombre, int numEmpleadosFijos, int numEmpleadosTemporales) {
        this.numDepartamento = numDepartamento;
        this.nombre = nombre;
        this.numEmpleadosFijos = numEmpleadosFijos;
        this.numEmpleadosTemporales = numEmpleadosTemporales;
    }

    @Override
    public String toString() {
        return "DepartamentoNumEpleadosDTO{" +
                "numDepartamento=" + numDepartamento +
                ", nombre='" + nombre + '\'' +
                ", numEmpleadosFijos=" + numEmpleadosFijos +
                ", numEmpleadosTemporales=" + numEmpleadosTemporales +
                '}';
    }
}
