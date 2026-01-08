package ud2.actividad2.dto;

public class EmpleadoEdadDTO {
    String nss;
    String nome;
    String apelido1;
    String apelido2;
    int edad;

    public EmpleadoEdadDTO(String nss, String nome, String apelido1, String apelido2, int edad) {
        this.nss = nss;
        this.nome = nome;
        this.apelido1 = apelido1;
        this.apelido2 = apelido2;
        this.edad = edad;
    }
}
