package ud2.actividad4.dto;

public class DepartamentoNumProyectosDTO {
    int numDepartamento;
    String nombre;
    int numProyectos;

    public DepartamentoNumProyectosDTO(int numDepartamento, String nombre, int numProyectos) {
        this.numDepartamento = numDepartamento;
        this.nombre = nombre;
        this.numProyectos = numProyectos;
    }

    @Override
    public String toString() {
        return "DepartamentoNumProyectosDTO{" +
                "numDepartamento=" + numDepartamento +
                ", nombre='" + nombre + '\'' +
                ", numProyectos=" + numProyectos +
                '}';
    }
}
