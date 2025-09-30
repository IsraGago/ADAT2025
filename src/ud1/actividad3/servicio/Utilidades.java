package ud1.actividad3.servicio;

public class Utilidades {

    public static boolean esNombreValido(String nombre) {
        return nombre != null && !nombre.trim().isEmpty() && nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+");
    }
    
}
