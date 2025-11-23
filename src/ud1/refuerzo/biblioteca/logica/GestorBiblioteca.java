package ud1.refuerzo.biblioteca.logica;


import org.w3c.dom.Document;
import ud1.refuerzo.biblioteca.clases.Libro;
import ud1.refuerzo.biblioteca.clases.Seccion;
import ud1.refuerzo.biblioteca.excepciones.ExcepcionXML;
import ud1.refuerzo.biblioteca.persistencia.BibliotecaXML;
import ud1.refuerzo.biblioteca.persistencia.TipoValidacion;

import java.util.*;

public class GestorBiblioteca {

    private Document documentoXML;
    private final BibliotecaXML bibliotecaXML = new BibliotecaXML();
    private String rutaXML = "";
    private TipoValidacion tipoValidacion = null;

    public void cargarDocumento(String rutaXML, TipoValidacion tipoValidacion) {
        try {
            this.documentoXML = bibliotecaXML.cargarDocumentoXML(rutaXML, tipoValidacion);
            System.out.println("Documento XML cargado correctamente: " + rutaXML);
            this.rutaXML = rutaXML;
            this.tipoValidacion = tipoValidacion;
        } catch (ExcepcionXML e) {
            System.out.println("Error al cargar el documento XML: " + e.getMessage());
        }
    }

    public void mostrarLibrosPorSeccion(String seccion) {
        if (documentoXML == null) {
            System.out.println("No se ha cargado ningún documento XML.");
            return;
        }
        ArrayList<Libro> libros = bibliotecaXML.getLibrosPorSeccion(seccion);
        if (libros == null || libros.isEmpty()) {
            System.out.println("No se encontraron libros en la sección: " + seccion);
            return;
        }
        for (Libro libro : libros) {
            System.out.println(libro);
        }

    }

    public void mostrarNumeroLibrosPorSeccion() {
        if (documentoXML == null) {
            System.out.println("No se ha cargado ningún documento XML.");
            return;
        }
        ArrayList<String> secciones = bibliotecaXML.getSecciones();
        HashMap<String, Integer> mapa = bibliotecaXML.getNumLibrosPorSeccion(secciones);
        for (Map.Entry<String, Integer> entrada : mapa.entrySet()) {
            System.out.println("Sección: " + entrada.getKey() + "\n Número de libros: " + entrada.getValue());
            System.out.println("-------------------------");
        }
    }

    public void addLibro(Libro libro, String seccion) {
        if (documentoXML == null) {
            System.out.println("No se ha cargado ningún documento XML.");
            return;
        }
        bibliotecaXML.addLibro(libro, seccion);
    }

    public void modificarEstadoCopia(String isbn, int numCopia, String nuevoEstado) {
        if (documentoXML == null) {
            System.out.println("No se ha cargado ningún documento XML.");
            return;
        }
        bibliotecaXML.modificarEstadoCopia(isbn, numCopia, nuevoEstado);
        System.out.println("Estado de la copia modificado correctamente.");

    }
}
