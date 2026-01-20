package ud2.actividad3.clases;

import java.time.LocalDate;

public class VehiculoRenting extends Vehiculo {
    LocalDate fechaCompra;
    double precioMensual;
    int mesesContratados;

    public VehiculoRenting(String matricula, String marca, String modelo, char tipo, LocalDate fechaCompra, double precioMensual, int mesesContratados) {
        super(matricula, marca, modelo, tipo);
        this.fechaCompra = fechaCompra;
        this.precioMensual = precioMensual;
        this.mesesContratados = mesesContratados;
    }


    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getPrecioMensual() {
        return precioMensual;
    }

    public void setPrecioMensual(double precioMensual) {
        this.precioMensual = precioMensual;
    }

    public int getMesesContratados() {
        return mesesContratados;
    }

    public void setMesesContratados(int mesesContratados) {
        this.mesesContratados = mesesContratados;
    }
}
