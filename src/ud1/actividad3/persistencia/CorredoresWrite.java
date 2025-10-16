package ud1.actividad3.persistencia;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import ud1.actividad3.clases.Corredor;

public class CorredoresWrite {
    Fichero fichero;
    ObjectOutputStream oos;

    public CorredoresWrite(String ruta) {
        this.fichero = new Fichero(ruta);
        fichero.crearPadreSiNoExiste();
    }

    public void abrir() {
        boolean esModoEdicion = fichero.exists() && fichero.length() > 0;
        try {
            if (esModoEdicion) {
                oos = new ObjectOutputStreamNoHeader(
                        new BufferedOutputStream(new FileOutputStream(fichero, esModoEdicion)));
            } else {
                oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fichero, esModoEdicion)));
            }
        } catch (IOException e) {
            throw new RuntimeException("No se ha podido abrir el fichero: " + fichero.getAbsolutePath());
        }
    }

    public void cerrar() {
        if (oos == null)
            throw new RuntimeException("El fichero no estaba abierto");

        try {
            oos.close();
        } catch (Exception e) {
            throw new RuntimeException("No se ha podido cerrar el fichero: " + fichero.getAbsolutePath(), e);
        }
    }

    public void escribir(Corredor corredor) {
        if (oos == null)
            throw new RuntimeException("El fichero no estaba abierto");

        try {
            oos.writeObject(corredor);
            oos.flush(); // vacía el buffer en el archivo;
        } catch (IOException e) {
            throw new RuntimeException("No se ha podido escribir el corredor: " + corredor, e);
        }
    }

    

    

}
