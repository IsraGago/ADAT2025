package ud1.refuerzo.notas.clases;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Alumno {
    private int numero;
    private String nombre;
    private Date fechaNac;
    private ArrayList<String> telefonos;
    private boolean borrado;

    public Alumno(int numero, String nombre, Date fechaNac) {
        this.numero = numero;
        this.nombre = nombre;
        this.fechaNac = fechaNac;
        this.telefonos = new ArrayList<>();
        this.borrado = false;
    }

    public Alumno(String nombre,Date fechaNac){
        this.nombre = nombre;
        this.fechaNac = fechaNac;
        this.telefonos = new ArrayList<>();
        this.borrado = false;
    }

    public int getBytesAEscribir() {
        int bytes = 0;
        try {
            bytes += 1; // boolean borrado
            bytes += 4; // int numero
            bytes += (nombre.getBytes("UTF-8").length + 2);
            bytes += 8; // long fechaNac
            for (String telefono : telefonos){
                bytes+= (telefono.getBytes("UTF-8").length + 2);
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return bytes;
    }

    public long fechaToLong() {
        return this.fechaNac == null ? 0L : this.fechaNac.getTime();
    }

    public static Date longToFecha(long fecha) {
        return fecha == 0 ? null : new Date(fecha);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public boolean estaBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    public ArrayList<String> getTelefonos() {
        return telefonos;
    }

    public void setTelefono(ArrayList<String> telefono) {
        this.telefonos = telefono;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "numero=" + numero +
                ", nombre='" + nombre + '\'' +
                ", fechaNac=" + fechaNac +
                ", telefonos=" + telefonos +
                ", borrado=" + borrado +
                '}';
    }
}
