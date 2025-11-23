/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ud1.refuerzo.biblioteca.clases;

import java.util.List;

/**
 *
 * @author usuario
 */
public class Seccion {

    private String nombre;
    private List<Libro> libros;

    public Seccion() {
    }

    public Seccion(String nombre, List<Libro> libros) {
        this.nombre = nombre;
        this.libros = libros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
     @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sección: ").append(nombre).append("\n");
        sb.append("Libros:\n");
        for (Libro libro : libros) {
            sb.append(libro.toString());
        }
        return sb.toString();
    }

}
