package ud1.actividad2;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class EscribirTexto extends Archivo {

    private BufferedWriter salida = null;

    public EscribirTexto(String ruta) {
        super(ruta);
    }

    @Override
    public void abrirArchivo() {
        try {
            salida = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ruta), encoding));
        } catch (IOException e) {
            System.out.println("Error al abrir archivo: " + e.getMessage());
        }
    }

    @Override
    public void cerrarArchivo() {
        try {
            if (salida != null) {
                salida.close();
            }
        } catch (IOException e) {
            System.out.println("Error al cerrar archivo: " + e.getMessage());
        }
    }

    public void escribirLinea(String linea) {
        try {
            salida.write(linea);
            salida.newLine(); // añade salto de línea
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
