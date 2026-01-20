package ud2.actividad3.dto;

public class EmpleadoProyectoDTO {
    private  String nss;
    private String nombreCompleto;

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public EmpleadoProyectoDTO(String nss, String nombreCompleto) {
        this.nss = nss;
        this.nombreCompleto = nombreCompleto;
    }

    @Override
    public String toString() {
        return "EmpleadoProyectoDTO{" +
                "nss='" + nss + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                '}';
    }
}
