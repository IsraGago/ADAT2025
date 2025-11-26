package ud1.refuerzo.notas.persistencia;
import ud1.refuerzo.notas.clases.NotaAlumno;

import java.io.*;
import java.util.ArrayList;

public class NotasAlumnoRead {
    ObjectInputStream ois;
    Fichero fichero;
    public NotasAlumnoRead(String ruta){
        this.fichero = new Fichero(ruta);
        fichero.crearPadreSiNoExiste();
    }

    // ABRIR Y CERRAR SOLO PARA LECTURA SECUENCIAL
    public void abrir() {
        try {
            if (ois != null) {
                throw new RuntimeException("El fichero ya estaba abierto");
            }
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)));
        } catch (IOException e) {
            throw new RuntimeException("Error al abrir el fichero: " + e.getMessage(), e);
        }
    }

    public void cerrar() {
        if (ois == null)
            return;
        try {
            ois.close();
            ois = null;
        } catch (IOException e) {
            throw new RuntimeException("Error al cerrar el fichero: " + e.getMessage(), e);
        }
    }

    public NotaAlumno leer(){
        if (ois == null) {
            return null;
        }
        try {
            return (NotaAlumno) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public ArrayList<NotaAlumno> leerTodos(){
        ArrayList<NotaAlumno> lista = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)))) {
            while (true) {
                NotaAlumno notaAlumno = (NotaAlumno) ois.readObject();
                lista.add(notaAlumno);
            }
        } catch (EOFException eof) {
            // Fin del archivo alcanzado, devolver la lista
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Error al leer un objeto del archivo: Clase no encontrada", ex);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo", e);
        }
        return lista;
    }
}
