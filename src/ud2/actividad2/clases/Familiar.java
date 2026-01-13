package ud2.actividad2.clases;

import java.time.LocalDate;

public class Familiar {
    private int codigo;
    private String nss;
    private String nssEmpleado;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private LocalDate fechaNacimiento;
    private String parentesco;
    private char sexo;

    public Familiar(int codigo, String nss, String nssEmpleado, String nombre, String apellido1, String apellido2, LocalDate fechaNacimiento, String parentesco, char sexo) {
        this.codigo = codigo;
        this.nss = nss;
        this.nssEmpleado = nssEmpleado;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNacimiento = fechaNacimiento;
        this.parentesco = parentesco;
        this.sexo = sexo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getNssEmpleado() {
        return nssEmpleado;
    }

    public void setNssEmpleado(String nssEmpleado) {
        this.nssEmpleado = nssEmpleado;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Familiar{" +
                "codigo=" + codigo +
                ", nss='" + nss + '\'' +
                ", nssEmpleado='" + nssEmpleado + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", parentesco='" + parentesco + '\'' +
                ", sexo=" + sexo +
                '}';
    }
}
