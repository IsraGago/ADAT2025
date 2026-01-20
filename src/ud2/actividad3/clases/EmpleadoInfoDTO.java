package ud2.actividad3.clases;

public class EmpleadoInfoDTO {
    String nss;
    String nombreCompleto;
    String localidade;
    double salario;

    public EmpleadoInfoDTO(String nss, String nombreCompleto, String localidade, double salario) {
        this.nss = nss;
        this.nombreCompleto = nombreCompleto;
        this.localidade = localidade;
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "EmpleadoInfoDTO{" +
                "nss='" + nss + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", localidade='" + localidade + '\'' +
                ", salario=" + salario +
                '}';
    }

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

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}
