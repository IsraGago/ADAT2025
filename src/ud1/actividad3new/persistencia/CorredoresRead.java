package ud1.actividad3new.persistencia;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import ud1.actividad3new.clases.Corredor;

public class CorredoresRead {
    ObjectInputStream ois;
    Fichero fichero;

    CorredoresRead(String ruta) {
        this.fichero = new Fichero(ruta);
    }

    public void abrir() {
        try {
            if (fichero.exists()) {
                ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)));
            } else {
                throw new RuntimeException("El archivo no existe: " + fichero.getAbsolutePath());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al abrir el fichero de corredores: " + e.getMessage(), e);
        }
    }

    public void cerrar() {
        if (ois == null)
            throw new RuntimeException("El fichero no estaba abierto");

        try {
            ois.close();
        } catch (IOException e) {
            throw new RuntimeException("Error al cerrar el archivo de corredores: " + e.getMessage(), e);
        }
    }

    public Corredor leer() {
        if (ois == null)
            throw new RuntimeException("El fichero no estaba abierto");

        try {
            return (Corredor) ois.readObject();
        } catch (EOFException eof) {
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public ArrayList<Corredor> leerTodos() {
        if (ois == null)
            throw new RuntimeException("El fichero no estaba abierto");
        ArrayList<Corredor> lista = new ArrayList<>();
        try {
            while (true) {
                lista.add((Corredor) ois.readObject());
            }
        } catch (EOFException eof) {
            return lista;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public Corredor getCorredor(int dorsal) {
        Corredor corredor = null;
        Corredor temp;
        try {
            while (true) {
                temp = (Corredor) ois.readObject();
                if (temp.getDorsal() == dorsal) {
                    corredor = temp;
                    break;
                }
            }
        } catch (EOFException eof) {
            
        } catch (ClassNotFoundException ex){
            return null;
        } catch(Exception e){
            return null;
        }
        return corredor;
    }

}
