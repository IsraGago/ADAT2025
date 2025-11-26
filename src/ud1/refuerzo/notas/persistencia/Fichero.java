package ud1.refuerzo.notas.persistencia;

import java.io.File;

public class Fichero extends File {
    public Fichero(String ruta){
        super(ruta);   
    }
    
    public boolean crearPadreSiNoExiste(){
        if (!this.getParentFile().exists()) {
            return this.getParentFile().mkdirs();
        } else {
            return false;
        }
    }

    public boolean renombrar(String nuevoNombre){
        Fichero nuevoFichero = new Fichero(nuevoNombre);
        return this.renameTo(nuevoFichero);
    }

    public boolean existe(){
        return this.exists();
    }
}
