package ud2.actividad4.dto;

public class ProxectoInfoDTO {
    String nombre;
    String lugar;
    int numDepartamento;

    public ProxectoInfoDTO(String nombre, String lugar, int numDepartamento) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.numDepartamento = numDepartamento;
    }

    @Override
    public String toString() {
        return "ProxectoInfoDTO{" +
                "nombre='" + nombre + '\'' +
                ", lugar='" + lugar + '\'' +
                ", numDepartamento=" + numDepartamento +
                '}';
    }
}
