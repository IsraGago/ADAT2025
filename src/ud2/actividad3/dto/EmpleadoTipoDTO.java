package ud2.actividad3.dto;

public class EmpleadoTipoDTO {
    String nombreCompleto;
    String tipo;

    public EmpleadoTipoDTO(String nombreCompleto, String tipo) {
        this.nombreCompleto = nombreCompleto;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "EmpleadoTipoDTO{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
