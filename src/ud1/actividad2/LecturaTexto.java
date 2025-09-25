package ud1.actividad2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LecturaTexto extends Archivo {
    protected BufferedReader entrada = null;

    public LecturaTexto(String ruta) {
        super(ruta);
    }

    @Override
    public void abrirArchivo() {
        try {
            entrada = new BufferedReader(new FileReader(ruta));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String leerLinea() {
        String linea = null;
        try {
            linea = entrada.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return linea;
    }

    @Override
    public void cerrarArchivo() {
        try {
            entrada.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
