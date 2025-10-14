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


    public Corredor leer() {
        if (ois == null) {
            return null;
        }
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
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)))) {
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
        }
        return lista;
    }

    public Corredor buscarPorDorsal(int dorsal) {

        Corredor corredor = null;
        Corredor temp;
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)))) {
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
        }
        return corredor;
    }

public int getUltimoDorsal() {
    int dorsal = 0;

    if (!fichero.exists()) {
        return dorsal;
    }

    try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)))) {
        while (true) {
            try {
                Corredor temp = (Corredor) ois.readObject();
                if (temp.getDorsal() > dorsal) {
                    dorsal = temp.getDorsal();
                }
            } catch (EOFException e) {
                break;
            }
        }
    } catch (ClassNotFoundException e) {
        System.out.println("Error al leer un objeto del archivo: Clase no encontrada");
        return -1;
    } catch (IOException e) {
        System.out.println("Error al leer el archivo: " + e.getMessage());
        return -1;
    }

    return dorsal;
}

}
