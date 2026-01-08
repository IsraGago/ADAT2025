package ud2.actividad2.clases;

public class Departamento {
    private int numDepartamento;
    private String nombre;
    private String nss;

    public Departamento(int numDepartamento, String nomeDepartamento) {
        //TODO
    }

    public int getNumDepartamento() {
        return numDepartamento;
    }

    public void setNumDepartamento(int numDepartamento) {
        this.numDepartamento = numDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public Departamento(int numDepartamento, String nombre, String nss) {
        this.numDepartamento = numDepartamento;
        this.nombre = nombre;
        this.nss = nss;
    }

    public void mostrarDatos(){
        System.out.println("número: "+numDepartamento+" | Nombre: "+nombre+" | Director:"+nss);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
