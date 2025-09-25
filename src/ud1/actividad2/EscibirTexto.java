package ud1.actividad2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EscibirTexto extends Archivo {

    BufferedWriter salida = null; 

    public EscibirTexto(String ruta) {
        super(ruta);
    }

    @Override
    public void abrirArchivo() {
        try  {
            salida = new BufferedWriter(new FileWriter(ruta));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void cerrarArchivo() {
        try {
            salida.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void escribirLinea(String linea){
        try {
            salida.write(linea);
        } catch (IOException e) {
            e.printStackTrace();
        };
    }

}
