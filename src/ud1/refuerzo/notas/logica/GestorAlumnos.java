package ud1.refuerzo.notas.logica;

import ud1.refuerzo.notas.clases.Alumno;
import ud1.refuerzo.notas.persistencia.AlumnosRandom;

public class GestorAlumnos {
    AlumnosRandom fichero;
    public GestorAlumnos(String ruta) {
        fichero = new AlumnosRandom(ruta);
        fichero.crearPadreSiNoExiste();
    }
    public boolean guardarAlumno(Alumno alumno){
        if (alumno == null){
            throw new IllegalArgumentException("El alumno no puede ser nulo.");
        }
        try {
            if (buscarAlumnoPorNombre(alumno.getNombre()) != null) {
                System.out.printf("El alumno %s ya existe.%n", alumno.getNombre());
                return false;
            }
            int nuevoId = fichero.calcularNuevoID();
            alumno.setNumero(nuevoId);
            fichero.guardarAlumno(alumno);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Datos inválidos: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error al guardar el equipo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
        return false;
    }
    public Alumno buscarAlumnoPorNombre(String nombre){
        try {
            if (!fichero.existe()) {
                return null;
            }
            if (nombre == null || nombre.isBlank()) {
                throw new IllegalArgumentException("El nombre del alumno no puede ser nulo o vacío.");
            }
            int id = 1;
            Alumno alumno;

            while ((alumno = fichero.leerAlumno(id)) != null) {
                if (alumno.getNombre().equalsIgnoreCase(nombre)) {
                    return alumno;
                }
                id++;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean eliminarAlumnoPorID(int id) {
        return fichero.eliminarAlumnoPorID(id);
    }

    public boolean eliminarAlumnoPorNombre(String nombre) {
        Alumno alumno = buscarAlumnoPorNombre(nombre);
        if (alumno == null) {
            System.out.println("El alumno no existe en el fichero.");
            return false;
        }
        return eliminarAlumnoPorID(alumno.getNumero());
    }

}
