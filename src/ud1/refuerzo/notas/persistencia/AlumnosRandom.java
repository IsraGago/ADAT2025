package ud1.refuerzo.notas.persistencia;

import ud1.refuerzo.notas.clases.Alumno;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlumnosRandom extends Fichero {
    public final static int TAMANO_REGISTRO = 200;
    private RandomAccessFile raf;

    public AlumnosRandom(String ruta) {
        super(ruta);
    }

    public void guardarAlumno(Alumno alumno) {
        if (TAMANO_REGISTRO < alumno.getBytesAEscribir()) {
            throw new RuntimeException("El alumno ocupa demasiados bytes");
        }
        try (RandomAccessFile raf = new RandomAccessFile(this, "rw")) {
            int posicion = raf.length() == 0 ? 0 : (alumno.getNumero() - 1) * TAMANO_REGISTRO;
            raf.seek(posicion);
            raf.writeBoolean(alumno.estaBorrado());
            raf.writeInt(alumno.getNumero());
            raf.writeUTF(alumno.getNombre());
            raf.writeLong(alumno.fechaToLong());
            raf.writeInt(alumno.getTelefonos().size());

            for (String numero : alumno.getTelefonos()) {
                raf.writeUTF(numero);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el alumno: " + e.getMessage(), e);
        }
    }

    public Alumno leerAlumno(int numeroAlumno) {
        Alumno alumno = null;

        try (RandomAccessFile raf = new RandomAccessFile(this, "r")) {
            int posicion = (numeroAlumno - 1) * TAMANO_REGISTRO;
            if (posicion >= raf.length()) {
                return null;
            }

            raf.seek(posicion);
            boolean estaBorrado = raf.readBoolean();
            if (estaBorrado) {
                return null;
            }
            // numero , nombre, fecha , numTelefonos
            int numero = raf.readInt();
            String nombre = raf.readUTF();
            Date fechaNac = Alumno.longToFecha(raf.readLong());
            int numTelefonos = raf.readInt();
            alumno = new Alumno(numero, nombre, fechaNac);
            for (int i = 0; i < numTelefonos; i++) {
                String telefono = raf.readUTF();
                alumno.getTelefonos().add(telefono);
            }

        } catch (EOFException e) {
            throw new RuntimeException("Fin de fichero alcanzado inesperadamente: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el Alumno: " + e.getMessage(), e);
        }
        return alumno;
    }

    public List<Alumno> leerTodosAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(this, "r")) {
            for (int i = 0; i < numeroRegistrosConBorrados(); i++) {
                int posicion = i * TAMANO_REGISTRO;
                if (posicion >= raf.length()) {
                    break;
                }

                raf.seek(posicion);
                boolean estaBorrado = raf.readBoolean();
                if (estaBorrado) {
                    continue;
                }

                int numero = raf.readInt();
                String nombre = raf.readUTF();
                Date fechaNac = Alumno.longToFecha(raf.readLong());
                int numTelefonos = raf.readInt();

                Alumno alumno = new Alumno(numero, nombre, fechaNac);
                for (int j = 0; j < numTelefonos; j++) {
                    alumno.getTelefonos().add(raf.readUTF());
                }

                alumnos.add(alumno);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer todos los alumnos: " + e.getMessage(), e);
        }
        return alumnos;
    }


    public int calcularNuevoID() {
        if (!this.existe()) {
            return 1;
        }
        try (RandomAccessFile raf = new RandomAccessFile(this, "r")) {
            return numeroRegistrosConBorrados() + 1;
        } catch (Exception e) {
            System.out.println("Error al acceder al archivo: " + e.getMessage());
        }
        return -1;
    }

    public int numeroRegistrosConBorrados() {
        try (RandomAccessFile raf = new RandomAccessFile(this, "r")) {
            return (int) Math.ceil((double) raf.length() / TAMANO_REGISTRO);
        } catch (IOException e) {
            System.out.println("Error al calcular el número de registros: " + e.getMessage());
            return -1;
        }
    }
    public boolean eliminarAlumnoPorID(int id) {

        try (RandomAccessFile raf = new RandomAccessFile(this, "rw")) {
            Alumno alumno = leerAlumno(id);
            if (alumno == null || alumno.estaBorrado()) {
                System.out.println("El alumno no existe en el fichero.");
                return false;
            }
            alumno.setBorrado(true);
            guardarAlumno(alumno);
            System.out.println("Alumno borrado con éxito.");
            return true;
        } catch (RuntimeException e) {
            System.out.println("Error al acceder al fichero: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
        return false;
    }
}
