package ud2.actividad4.clases;


import java.time.LocalDate;

public class VehiculoPropio extends Vehiculo {
    LocalDate fechaCompra;
    double precio;

    public VehiculoPropio(int codigo, String matricula, String marca, String modelo, char tipo,LocalDate fechaCompra, double precio) {
        super(codigo, matricula, marca, modelo, tipo);
        this.fechaCompra = fechaCompra;
        this.precio = precio;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }


}
