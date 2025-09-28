package ud1.actividad2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LecturaTexto extends Archivo {
    protected BufferedReader entrada = null;

    public LecturaTexto(String ruta) {
        super(ruta);
        if (!this.comprobarRuta()) {
            throw new IllegalArgumentException("La ruta " + ruta + " no existe o no es un archivo .txt válido.");
        }
    }

    @Override
    public void abrirArchivo() {
        try {
            entrada = new BufferedReader(new InputStreamReader(new FileInputStream(ruta), encoding));
        } catch (IOException e) {
            System.out.println("Error al abrir archivo: " + e.getMessage());
        }
    }

    public String leerLinea() {
        String linea = null;
        try {
            if (entrada != null) {
                linea = entrada.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error al leer línea: " + e.getMessage());
        }
        return linea;
    }

    @Override
    public void cerrarArchivo() {
        try {
            if (entrada != null) {
                entrada.close();
            }
        } catch (IOException e) {
            System.out.println("Error al cerrar archivo: " + e.getMessage());
        }
    }
}