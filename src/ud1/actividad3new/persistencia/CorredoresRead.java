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

    public CorredoresRead(String ruta) {
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
        ArrayList<Corredor> lista = new ArrayList<>();
        try {
            abrir();
            while (true) {
                Corredor corredor = (Corredor) ois.readObject();
                lista.add(corredor);
            }
        } catch (EOFException eof) {
            // Fin del archivo alcanzado, devolver la lista
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Error al leer un objeto del archivo: Clase no encontrada", ex);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo", e);
        } finally {
            cerrar();
        }
        return lista;
    }

    public Corredor buscarPorDorsal(int dorsal) {

        Corredor corredor = null;
        Corredor temp;
        try {
            abrir();
            while (true) {
                temp = (Corredor) ois.readObject();
                if (temp.getDorsal() == dorsal) {
                    corredor = temp;
                    break;
                }
            }
        } catch (EOFException eof) {

        } catch (ClassNotFoundException ex) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            cerrar();
        }
        return corredor;
    }

    public int getUltimoDorsal() {

        int dorsal = 0;
        Corredor temp;
        try {
            abrir();
            while (true) {
                temp = (Corredor) ois.readObject();
                if (temp.getDorsal() > dorsal) {
                    dorsal = temp.getDorsal();
                }
            }
        } catch (EOFException eof) {

        } catch (ClassNotFoundException ex) {
            return -1;
        } catch (Exception e) {
            return -1;
        } finally {
            cerrar();
        }
        return dorsal;
    }

}
