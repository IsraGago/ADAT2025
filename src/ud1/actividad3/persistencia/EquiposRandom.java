package ud1.actividad3.persistencia;

import java.io.IOException;
import java.io.RandomAccessFile;

import ud1.actividad3.clases.Equipo;
import ud1.actividad3.clases.Patrocinador;

public class EquiposRandom {
    Fichero fichero;
    RandomAccessFile raf;
    public static long TAMANO_REGISTRO = 200;

    public EquiposRandom(String ruta) {
        fichero = new Fichero(ruta);
        fichero.crearPadreSiNoExiste();
    }

    public void abrirArchivo() {
        try {
            raf = new RandomAccessFile(fichero, null);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void cerrarArchivo() {
        try {
            raf.close();
        } catch (Exception e) {

        }
    }
    // A lA HORA DE LEER DE VUELTA HACES UN BUCLE HASTA EL NÚMERO DE PATROCINADORES
    // PARA RECUPERARLOS

    public void escribirEquipo(Equipo e) {
        if (TAMANO_REGISTRO < e.getBytesAEscribir()) {
            throw new IllegalArgumentException("El objeto excede el tamaño máximo: " + TAMANO_REGISTRO);
        }
        try {
            if (raf != null) {
                abrirArchivo();
                // int numRegistros = (int) Math.ceil((double) fichero.length() / TAMANO_REGISTRO);
                long posicion = (e.getIdEquipo() - 1L) * TAMANO_REGISTRO;
                //TODO CALCULAR ID DEL EQUIPO SEGUN SU POSICIÓN PARA DESPUÉS ESCRIBIRLO AHÍ
                raf.seek(posicion);
                raf.writeBoolean(e.isEstaBorrado());
                raf.writeInt(e.getIdEquipo());
                raf.writeUTF(e.getNombre());
                raf.writeInt(e.getNumPatrocinadores());
                for (Patrocinador patrocinador : e.getPatrocinadores()) {
                    raf.writeUTF(patrocinador.getNombre());
                    raf.writeFloat(patrocinador.getDonacion());
                    raf.writeLong(patrocinador.fechaToLong());
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (raf != null) {
                cerrarArchivo();
            }
        }
    }

    public Equipo leerEquipo(Equipo e) {
        try {
            if(raf != null){

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (raf != null) {
                cerrarArchivo();
            }
        }
    }

    public int numeroRegistrosConBorrados(){
        try {
            return (int) Math.ceil((double) raf.length() / TAMANO_REGISTRO);
        } catch (Exception e) {
            return 0;
        }
    }
}
