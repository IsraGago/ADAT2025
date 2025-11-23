package ud1.refuerzo.biblioteca;

import ud1.refuerzo.biblioteca.clases.Copia;
import ud1.refuerzo.biblioteca.clases.Libro;
import ud1.refuerzo.biblioteca.logica.GestorBiblioteca;
import ud1.refuerzo.biblioteca.persistencia.RutasFicheros;
import ud1.refuerzo.biblioteca.persistencia.TipoValidacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main() {
        GestorBiblioteca gestor = new GestorBiblioteca();

        gestor.cargarDocumento(RutasFicheros.RUTA_XML_BIBLIOTECA.getRuta(), TipoValidacion.XSD);
        gestor.mostrarLibrosPorSeccion("Programación");
        gestor.mostrarNumeroLibrosPorSeccion();
        gestor.modificarEstadoCopia("666-1234567890", 3, "Prestado");
        Libro libro = new Libro("A2004",
                "999-9876543210",
                "Aprendiendo POO",
                350,
                new ArrayList<>(List.of("Israel")),
                LocalDate.now(),
                "Editorial POO",
                45.0,
                new ArrayList<>(List.of( new Copia(1,"Disponible"))));
        gestor.addLibro(libro, "POO");
        gestor.mostrarLibrosPorSeccion("POO");
        gestor.guardarCambios(RutasFicheros.RUTA_XML_CAMBIOS.getRuta());
    }
}
