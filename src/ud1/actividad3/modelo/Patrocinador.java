package ud1.actividad3.modelo;

import java.time.LocalDate;

public class Patrocinador {
    private String nombre;
    private float donacion; // 4 BYTES
    private LocalDate fechaInicio; // 4(año) + 1(mes) + 1(día) BYTES

    public Patrocinador(String nombre, float donacion, LocalDate fechaInicio) {
        this.nombre = nombre;
        this.donacion = donacion;
        this.fechaInicio = fechaInicio;
    }

    public int getBytesAEscribir() {
        int bytesAEscribir = -1;
        try {
            bytesAEscribir = (nombre.getBytes("UTF-8").length + 2) + 4 + 4 + 1 + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytesAEscribir;
    }

    public float getDonacion() {
        return donacion;
    }
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public String getNombre() {
        return nombre;
    }

}
