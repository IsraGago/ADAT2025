package ud2.actividad3.dto;

import java.util.ArrayList;
import java.util.List;

public class ProyectoConEmpleadosDTO {
    public int numProyecto;
    public String nombreProyecto;
    public String lugar;
    public int numDepartamento;
    public List<EmpleadoProyectoDTO> empleados;

    public ProyectoConEmpleadosDTO(int numProyecto, String nombreProyecto, String lugar,int numDepartamento) {
        this.numProyecto = numProyecto;
        this.nombreProyecto = nombreProyecto;
        this.lugar = lugar;
        this.numDepartamento = numDepartamento;
        this.empleados = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ProyectoConEmpleadosDTO{" +
                "numProyecto=" + numProyecto +
                ", nombreProyecto='" + nombreProyecto + '\'' +
                ", lugar='" + lugar + '\'' +
                ", numDepartamento=" + numDepartamento +
                '}';
    }
}
