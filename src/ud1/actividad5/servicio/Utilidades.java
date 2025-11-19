package ud1.actividad5.servicio;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utilidades {

    public static boolean esNombreValido(String nombre) {
        return nombre != null && !nombre.trim().isEmpty() && nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+");
    }

    public static String formatearFecha(long milis, String patronFecha) {
        if (patronFecha == null || patronFecha.trim().isEmpty()) {
            patronFecha = "dd/MM/yyyy HH:mm:ss";            
        }
        LocalDateTime fecha = LocalDateTime.ofInstant(Instant.ofEpochMilli(milis),
                ZoneId.systemDefault());
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(patronFecha);
        return formateador.format(fecha);
    }

    public static boolean esCodigoValido(String codigo) {
        return codigo.matches("[Cc]\\d+");
    }
}

