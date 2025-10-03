package ud1.actividad3.persistencia;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import ud1.actividad3.modelo.Corredor;
import ud1.actividad3.servicio.ObjectOutputStreamNoHeader;
import ud1.actividad3.servicio.Utilidades;

public class CorredoresIO {
    File fichero;

    public CorredoresIO(String ruta) {
        fichero = new File(ruta);
        Utilidades.crearPadreSiNoExiste(fichero);
    }

    public void listarCorredores() {
        try {
            ObjectInputStream ios = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)));
            while (true) {
                Corredor c = (Corredor) ios.readObject();
                System.out.println(c);
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

    public Corredor mostrar(int dorsal){
        ArrayList<Corredor> lista = getCorredores();
        for (Corredor corredor : lista) {
            if(corredor.getDorsal() == dorsal){
                System.out.println(corredor);
                return corredor;
            }
        }
        return null;
    }

    public ArrayList<Corredor> getCorredores(){
        ArrayList<Corredor> lista = new ArrayList<>();
        try {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichero)));
            while (true) {
                Corredor c = (Corredor) ois.readObject();
                lista.add(c);
            }
        } catch (Exception e) {
            // e.printStackTrace(); // FIN DE FICHERO
        }
        return lista;
    }

    public void eliminar(int dorsal){
        ArrayList<Corredor> lista = getCorredores();
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).getDorsal() == dorsal){
                lista.remove(i);
                break;
            }
        }
        sobreEscribir(lista);
    }

    public void sobreEscribir(ArrayList<Corredor> lista){
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


}
