package ud1.actividad3new.persistencia;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Set;

import ud1.actividad3new.clases.Equipo;
import ud1.actividad3new.clases.Patrocinador;

public class EquiposRandom extends Fichero {
    //TODO REVISAR SI CERRAR EN CADA MÉTODO
    public RandomAccessFile raf;
    public final static int TAMANO_REGISTRO = 200;

    public EquiposRandom(String nombreFichero) {
        super(nombreFichero);
    }

    public void abrir() {
        try {
            raf = new RandomAccessFile(this, "rw");
        } catch (Exception e) {
            throw new RuntimeException("Error al abrir el fichero: " + e.getMessage(), e);
        }
    }

    public void cerrar() {
        if (raf == null) {
            throw new RuntimeException("El fichero no está abierto.");
        }
        try {
            raf.close();
        } catch (Exception e) {
            throw new RuntimeException("Error al cerrar el fichero: " + e.getMessage(), e);
        }
    }

    public void guardarEquipo(Equipo equipo) {
        try {
            if (raf == null) {
                abrir();
            }
            int posicion = (equipo.getIdEquipo() - 1) * TAMANO_REGISTRO;
            raf.seek(posicion);
            raf.writeBoolean(equipo.estaBorrado());
            raf.writeInt(equipo.getIdEquipo());
            raf.writeUTF(equipo.getNombre());
            raf.writeInt(equipo.getNumPatrocinadores());

            for (Patrocinador patrocinador : equipo.getPatrocinadores()) {
                raf.writeUTF(patrocinador.getNombre());
                raf.writeFloat(patrocinador.getDonacion());
                raf.writeLong(patrocinador.fechaToLong());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el equipo: " + e.getMessage(), e);
        } finally{
            cerrar();
        }
    }

    public Equipo leerEquipo(int idEquipo) {
        Equipo equipo = null;
        try {
            if (raf == null) {
                abrir();
            }

            int posicion = (idEquipo - 1) * TAMANO_REGISTRO;
            if (posicion >= raf.length()) {
                return equipo;
            }

            raf.seek(posicion);
            boolean estaBorrado = raf.readBoolean();
            if (estaBorrado) {
                return equipo;
            }
            int id = raf.readInt();
            String nombre = raf.readUTF();
            int numPatrocinadores = raf.readInt();
            equipo = new Equipo(id, nombre);

            Set<Patrocinador> patrocinadores = equipo.getPatrocinadores();
            for (int i = 0; i < numPatrocinadores; i++) {
                String nombrePatrocinador = raf.readUTF();
                float donacion = raf.readFloat();
                long fechaLong = raf.readLong();
                Patrocinador patrocinador = new Patrocinador(nombrePatrocinador, donacion,
                        Patrocinador.longToFecha(fechaLong));
                patrocinadores.add(patrocinador);
            }

            equipo.setPatrocinadores(patrocinadores);

        } catch (EOFException e) {
            throw new RuntimeException("Fin de fichero alcanzado inesperadamente: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el equipo: " + e.getMessage(), e);
        }
        return equipo;
    }

    public int calcularNuevoID() {
        if (raf == null) {
            abrir();
        }
        try {
            return numeroRegistrosConBorrados() + 1;
        } catch (Exception e) {
            System.out.println("Error al acceder al archivo: "+e.getMessage());
        } finally {
            cerrar();
        }
        return -1;
    }

    public int numeroRegistrosConBorrados() {
        try {
            return (int) Math.ceil((double) raf.length() / TAMANO_REGISTRO);
        } catch (IOException e) {
            System.out.println("Error al calcular el número de registros: " + e.getMessage());
            return -1;
        }
    }

    public boolean eliminarEquipoPorID(int id){
        if (raf == null) {
            abrir();
        }
        try {
            Equipo equipo = leerEquipo(id);
            if (equipo == null || equipo.estaBorrado()) {
                System.out.println("El equipo no existe en el fichero.");
                return false;
            }
            equipo.setEstaBorrado(true);
            guardarEquipo(equipo);
            System.out.println("Equipo borrado con éxito.");
            return true;
        } catch (RuntimeException e) {
            System.out.println("Error al acceder al fichero: "+e.getMessage());
        } catch (Exception e){
            System.out.println("Error inesperado: "+e.getMessage());
        }
        return false;
    }

    public boolean editarOInsertarPatrocinador(int id, Patrocinador patrocinador){
        if (raf == null) {
            abrir();
        }
        try {
            Equipo equipo = leerEquipo(id);
            if (equipo == null || equipo.estaBorrado()) {
                System.out.println("El equipo no existe en el fichero.");
                return false;
            }
            if (!equipo.editarPatrocinador(patrocinador)) {
                equipo.addPatrocinador(patrocinador);
                System.out.println("patrocinador añadido con éxito");
            } else {
                System.out.println("patrocinador actualizado con éxito");
            }

            guardarEquipo(equipo);            
            return true;

        } catch (RuntimeException e) {
            System.out.println("Error al acceder al archivo: "+e.getMessage());
        } catch (Exception e){
            System.out.println("Error inesperado: "+e.getMessage());
        }
        return false;
    }
}
