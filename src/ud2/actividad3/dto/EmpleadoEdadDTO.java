package ud2.actividad3.dto;

public class EmpleadoEdadDTO {
    String nss;
    String nome;
    String apelido1;
    String apelido2;
    int edad;

    @Override
    public String toString() {
        return "EmpleadoEdadDTO{" +
                "nss='" + nss + '\'' +
                ", nome='" + nome + '\'' +
                ", apelido1='" + apelido1 + '\'' +
                ", apelido2='" + apelido2 + '\'' +
                ", edad=" + edad +
                '}';
    }

    public EmpleadoEdadDTO(String nss, String nome, String apelido1, String apelido2, int edad) {
        this.nss = nss;
        this.nome = nome;
        this.apelido1 = apelido1;
        this.apelido2 = apelido2;
        this.edad = edad;
    }
}
