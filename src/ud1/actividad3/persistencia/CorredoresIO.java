package ud1.actividad3.persistencia;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import ud1.actividad3.clases.Corredor;
import ud1.actividad3.clases.Puntuacion;
import ud1.actividad3.servicio.ObjectOutputStreamNoHeader;

public class CorredoresIO {
    Fichero fichero;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public CorredoresIO(String ruta) {
        fichero = new Fichero(ruta);
        fichero.crearPadreSiNoExiste();
    }

    public boolean abrirLectura() {
        try {
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)));
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean cerrarLectura() {
        if (ois != null) {
            try {
                ois.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean abrrirEscritrura(){
        try {
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fichero)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean cerrarEscritura() {
        try {
            if (oos != null) {
               oos.close();
               return false; 
            }  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void listarCorredores() {
        try {
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)));
            while (true) {
                Corredor c = (Corredor) ois.readObject();
                c.mostrarInformacion();
            }
        } catch (Exception e) {
            // e.printStackTrace(); // FIN DE FICHERO
        }
    }

    public void escribir(Corredor corredor) {
        try {
            ObjectOutputStream oos;
            if (fichero.exists() && fichero.length() > 0) {
                // SI EXISTE Y NO ESTÁ VACÍO ESCRIBE SIN CABECERA
                oos = new ObjectOutputStreamNoHeader(new BufferedOutputStream(new FileOutputStream(fichero, true)));
            } else {
                oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fichero)));
            }
            getUltimoDorsal();
            corredor.setDorsal(getUltimoDorsal() + 1);
            oos.writeObject(corredor);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getUltimoDorsal() {
        ArrayList<Corredor> lista = getCorredores();
        int maxDorsal = 0;
        for (Corredor corredor : lista) {
            if (corredor.getDorsal() > maxDorsal) {
                maxDorsal = corredor.getDorsal();
            }
        }
        return maxDorsal;
    }

    public void escribir(Corredor[] corredores) {
        try {
            ObjectOutputStream oos;
            if (fichero.exists() && fichero.length() > 0) {
                // SI EXISTE Y NO ESTÁ VACÍO ESCRIBE SIN CABECERA
                oos = new ObjectOutputStreamNoHeader(new BufferedOutputStream(new FileOutputStream(fichero, true)));
            } else {
                oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fichero)));
            }
            for (Corredor corredor : corredores) {
                oos.writeObject(corredor);
            }
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void escribir(ArrayList<Corredor> corredores) {
        escribir(corredores.toArray(new Corredor[0]));
    }

    public Corredor mostrar(int dorsal) {
        ArrayList<Corredor> lista = getCorredores();
        for (Corredor corredor : lista) {
            if (corredor.getDorsal() == dorsal) {
                System.out.println(corredor);
                return corredor;
            }
        }
        return null;
    }

    public ArrayList<Corredor> getCorredores() {
        ArrayList<Corredor> lista = new ArrayList<>();
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)));
            while (true) {
                Corredor c = (Corredor) ois.readObject();
                lista.add(c);
            }
        } catch (Exception e) {
            // e.printStackTrace(); // FIN DE FICHERO
        }
        return lista;
    }

    public void eliminar(int dorsal) {
        // SE PUEDE HACER CON UN FICHERO AUXILIAR, VAS LEYENDO EL ORIGINAL Y ESCRIBIENDO
        // TODOS MENOS EL QUE QUIERES BORRAR
        // SI SE BORRÓ ALGUNO BORRAS CORREDORES.DAT, Y LO CAMBIAS POR EL AUXILIAR.
        // SI NO SE BORRÓ NINGUNO, SIMPLEMENTE BORRAS EL AUXILIAR.
        ArrayList<Corredor> lista = getCorredores();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getDorsal() == dorsal) {
                lista.remove(i);
                break;
            }
        }
        sobreEscribir(lista);
    }

    public void sobreEscribir(ArrayList<Corredor> lista) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fichero)));
            for (Corredor corredor : lista) {
                oos.writeObject(corredor);
            }
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addPuntuacion(int dorsal, Puntuacion puntuacion) {
        ArrayList<Corredor> lista = getCorredores();
        for (Corredor corredor : lista) {
            if (corredor.getDorsal() == dorsal) {
                corredor.addPuntuacion(puntuacion);
                break;
            }
        }
        sobreEscribir(lista);
    }

    // TODO HACER UN ITERATOR

}
