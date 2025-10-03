package ud1.actividad3.servicio;

import java.io.File;

public class Utilidades {

    public static boolean esNombreValido(String nombre) {
        return nombre != null && !nombre.trim().isEmpty() && nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+");
    }
    
    public static void crearPadreSiNoExiste(File fichero) {
        if (!fichero.getParentFile().exists()) {
            fichero.getParentFile().mkdirs();
        }
    }
}
