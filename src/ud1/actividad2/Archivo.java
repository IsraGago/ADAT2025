package ud1.actividad2;

import java.io.File;

public abstract class Archivo {
    protected File ruta;
    protected static final String encoding = "UTF-8";
    protected static final String extension = ".txt";

    public Archivo(String ruta){
        this.ruta = new File(ruta);
        if (!this.comprobarRuta()) {
            throw new IllegalArgumentException("La ruta "+ ruta +"  no existe o no es un archivo.");
        }
    }

    public boolean comprobarRuta() {
        return ruta.exists() && ruta.isFile() && ruta.toString().endsWith(extension);
    }

    //TODO COMPROBAR METODOS

    public boolean borrar(){
        return ruta.delete();
    }

    public boolean renombrar(String nuevoNombre){
        return ruta.renameTo(new File(nuevoNombre));
    }

    public String getNombre(){
        return ruta.getName();
    }

    public abstract void abrirArchivo();

    public abstract void cerrarArchivo();
}
