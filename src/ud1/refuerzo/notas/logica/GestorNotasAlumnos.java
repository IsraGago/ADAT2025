package ud1.refuerzo.notas.logica;

import ud1.refuerzo.notas.clases.NotaAlumno;
import ud1.refuerzo.notas.persistencia.NotasAlumnoRead;
import ud1.refuerzo.notas.persistencia.NotasAlumnoWrite;

public class GestorNotasAlumnos {
    String ruta;
    public GestorNotasAlumnos(String ruta) {
        this.ruta = ruta;
    }

    public void guardarNotaAlumno(NotaAlumno notaAlumno){
        if (notaAlumno == null) {
            throw new IllegalArgumentException("El objeto notaAlumno no puede ser nulo");
        }

        NotasAlumnoRead read = new NotasAlumnoRead(ruta);
        NotasAlumnoWrite write = new NotasAlumnoWrite(ruta);
    }
}
