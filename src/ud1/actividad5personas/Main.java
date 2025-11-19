package ud1.actividad5personas;

import ud1.actividad5personas.clases.Empresa;
import ud1.actividad5personas.clases.Registro;
import ud1.actividad5personas.clases.contactos.Email;
import ud1.actividad5personas.clases.contactos.Telefono;
import ud1.actividad5personas.clases.contactos.Telefonos;
import ud1.actividad5personas.clases.personas.Estudiante;
import ud1.actividad5personas.clases.personas.Persona;
import ud1.actividad5personas.clases.personas.Trabajador;
import ud1.actividad5personas.logica.GestorRegistros;
import ud1.actividad5personas.persistencia.RegistrosJABX;
import ud1.actividad5personas.persistencia.RutasFicheros;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        GestorRegistros.leerRegistros(RutasFicheros.RUTA_XML_PRUEBA.getRuta());
        guardarXML(RutasFicheros.RUTA_XML_SALIDA.getRuta());
    }

    private static void guardarXML(String ruta) {
        List<String> categorias = new ArrayList<>();
        categorias.add("Categoria1");
        categorias.add("Categoria2");
        categorias.add("Categoria3");
        List<Persona> personas = new ArrayList<>();
        Telefonos telefonos = new Telefonos();
        telefonos.getTelefonos().add(new Telefono("622096700","móvil"));
        Trabajador trabajadorConEmail = new Trabajador(
                "Israel Gago",
                LocalDate.now(),
                new Email("isragagoacuna@gmail.com"),
                1800,
                new Empresa("CDM", "Administrador de sistemas"));
        personas.add(trabajadorConEmail);

        Trabajador trabajadorConTelefono = new Trabajador(
                "Delio Gago",
                LocalDate.now(),
                telefonos,
                1800,
                new Empresa("Servicio Movil","Camionero"));
        personas.add(trabajadorConTelefono);

        telefonos.getTelefonos().add(new Telefono("6882937909","fijo"));

        Estudiante estudiante = new Estudiante(
                "Mario Gago",
                LocalDate.now(),
                telefonos, "Universidad de Pontevedra",
                "Ingeniería Informática"
        );

        personas.add(new Estudiante());
        Registro registro = new Registro(1, LocalDate.now(), categorias, personas);
        RegistrosJABX.guardarRegistro(registro,ruta);
    }
}
