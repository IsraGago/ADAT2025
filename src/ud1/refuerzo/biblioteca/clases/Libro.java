/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ud1.refuerzo.biblioteca.clases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author usuario
 */
public class Libro {

    private String ID;
    private String isbn;
    private String titulo;
    private int numero_de_paginas;
    private List<String> autores;
    private LocalDate fechaEdicion;  // Utilizamos LocalDate para la fecha de edición
    private String editorial;
    private double precio;
    private List<Copia> copias;

    public Libro() {
    }

    public Libro(String ID, String isbn, String titulo, int numero_de_paginas, List<String> autores, LocalDate fechaEdicion, String editorial, double precio, List<Copia> copias) {
        this.ID = ID;
        this.isbn = isbn;
        this.titulo = titulo;
        this.numero_de_paginas = numero_de_paginas;
        this.autores = autores;
        this.fechaEdicion = fechaEdicion;
        this.editorial = editorial;
        this.precio = precio;
        this.copias = copias;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumero_de_paginas() {
        return numero_de_paginas;
    }

    public void setNumero_de_paginas(int numero_de_paginas) {
        this.numero_de_paginas = numero_de_paginas;
    }

    public List<String> getAutores() {
        return autores;
    }

    public void setAutores(List<String> autores) {
        this.autores = autores;
    }

    public LocalDate getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(LocalDate fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public List<Copia> getCopias() {
        return copias;
    }

    public void setCopias(List<Copia> copias) {
        this.copias = copias;
    }
    
@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(ID).append("\n");
        sb.append("ISBN: ").append(isbn).append("\n");
        sb.append("Título: ").append(titulo).append("\n");
        sb.append("Número de páginas: ").append(numero_de_paginas).append("\n");
        sb.append("Autores: ").append(autores.toString()).append("\n");
        sb.append("Fecha de Edición: ").append(fechaEdicion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n");
        sb.append("Editorial: ").append(editorial).append("\n");
        sb.append("Precio: ").append(precio).append("\n");
        sb.append("Copias:\n");
        for (Copia copia : copias) {
            sb.append(copia.toString());
        }
        sb.append("\n ------------------------------------------------------------\n");
        return sb.toString();
    }
}
