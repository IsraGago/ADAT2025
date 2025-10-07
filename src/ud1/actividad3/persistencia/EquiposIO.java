package ud1.actividad3.persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;

import ud1.actividad3.modelo.Equipo;
import ud1.actividad3.modelo.Patrocinador;

public class EquiposIO {
    Fichero fichero;
    public EquiposIO(String ruta) {
        fichero = new Fichero(ruta);
        fichero.crearPadreSiNoExiste();
    }
    // A lA HORA DE LEER DE VUELTA HACES UN BUCLE HASTA EL NÚMERO DE PATROCINADORES PARA RECUPERARLOS

     // TODO ACABAR MÉTODO
    // public boolean escribirEquipo(Equipo e){
    //     if (Equipo.TAMANO_REGISTRO < e.getBytesAEscribir()) {
    //         throw new IllegalArgumentException("El objeto excede el tamaño máximo: "+Equipo.TAMANO_REGISTRO);   
    //     }
    //     try {
    //         RandomAccessFile raf = new RandomAccessFile(fichero, null);
    //         int numRegistros = (int) Math.ceil((double) fichero.length() / Equipo.TAMANO_REGISTRO);
    //         if (numRegistros == 0) {
    //             raf.seek(0);
    //         } else {
    //             raf.seek(numRegistros * Equipo.TAMANO_REGISTRO);
    //         }
    //         raf.writeInt(e.getIdEquipo());
    //         raf.writeUTF(e.getNombre());
    //         raf.writeInt(e.getNumPatrocinadores());
    //         for (Patrocinador patrocinador : e.getPatrocinadores()) {
    //             raf.writeUTF(patrocinador.getNombre());
    //             raf.writeFloat(patrocinador.getDonacion());
    //             LocalDate fecha = patrocinador.getFechaInicio();
    //             raf.writeInt(fecha.getYear());
    //             raf.writeByte(fecha.getMonthValue());
    //             raf.writeByte(fecha.getDayOfMonth());
    //         }
    //     } catch (IOException e1) {
    //         e1.printStackTrace();
    //     }
    // }
}
