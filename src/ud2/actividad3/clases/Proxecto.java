package ud2.actividad3.clases;

public class Proxecto {
    private int id;
    private int numDepartamento;
    private String nombre;
    private String lugar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumDepartamento() {
        return numDepartamento;
    }

    public void setNumDepartamento(int numDepartamento) {
        this.numDepartamento = numDepartamento;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Proxecto(int id, int numDepartamento, String nombre, String lugar) {
        this.id = id;
        this.numDepartamento = numDepartamento;
        this.nombre = nombre;
        this.lugar = lugar;
    }
}
