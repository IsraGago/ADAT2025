package ud2.actividad3.dto;

public class DirectorDepartamentoDTO {
    int numDepartamento;
    String nombreDepartamento;
    String nombre;
    String apellido1;
    String apellido2;

    public DirectorDepartamentoDTO(int numDepartamento, String nombreDepartamento, String nombre, String apellido1, String apellido2) {
        this.numDepartamento = numDepartamento;
        this.nombreDepartamento = nombreDepartamento;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }

    @Override
    public String toString() {
        return "DirectorDepartamentoDTO{" +
                "numDepartamento=" + numDepartamento +
                ", nombreDepartamento='" + nombreDepartamento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +
                '}';
    }
}
