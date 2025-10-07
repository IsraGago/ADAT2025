package ud1.actividad3;

import java.io.File;
import java.time.LocalDate;

import ud1.actividad3.modelo.Corredor;
import ud1.actividad3.modelo.Fondista;
import ud1.actividad3.modelo.Puntuacion;
import ud1.actividad3.modelo.Velocista;
import ud1.actividad3.persistencia.CorredoresIO;

public class Main {
    public static void main(String[] args) {
        Corredor corredor1 = new Velocista("Juan Pérez", LocalDate.of(2000, 5, 12), 1, 10.34f);
        Corredor corredor2 = new Fondista("Ana Gómez", LocalDate.of(1995, 3, 22), 2, 42.195f);
        Corredor corredor3 = new Velocista("Carlos Ruiz", LocalDate.of(2002, 11, 30), 3, 9.75f);
        Corredor corredor4 = new Fondista("María López", LocalDate.of(2000, 7, 15), 1, 21.097f);
        Corredor corredor5 = new Velocista("Pedro García", LocalDate.of(1995, 8, 5), 1, 11.20f);
        Corredor corredor6 = new Fondista("Laura Martínez", LocalDate.of(2002, 9, 10), 4, 35.00f);

        Puntuacion puntuacion = new Puntuacion(2024,3F);

        try {
            String ruta = "./src/ud1/actividad3/saves/corredores.dat";
            new File(ruta).delete();
            CorredoresIO io = new CorredoresIO(ruta);
            io.escribir(corredor1);
            io.escribir(corredor2);
            io.escribir(corredor3);
            io.escribir(corredor4);
            io.escribir(corredor5);
            io.escribir(corredor6);

            System.out.println("\nCORREDORRES: ");
            io.listarCorredores();
            io.eliminar(1);
            System.out.println("\nCORREDORRES TRAS ELIMINAR DORSAL 1: ");
            io.listarCorredores();

            io.addPuntuacion(2, puntuacion);

            System.out.println("\nLISTADO DE CORREDORES");
            io.listarCorredores();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
