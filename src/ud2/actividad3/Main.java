package ud2.actividad3;


import ud2.actividad3.clases.Familiar;
import ud2.actividad3.clases.Proxecto;
import ud2.actividad3.clases.VehiculoPropio;
import ud2.actividad3.clases.VehiculoRenting;
import ud2.actividad3.logica.GestorEmpresa;
import ud2.actividad3.utilidades.TipoSGBD;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String baseDatos = "BDEMPRESA25";
        String usuario = "sa";
        String password = "abc123.";
        String rutaSQLite = "./src/ud2/actividad3/persistencia/dbEmpresa.db";

        try {
            GestorEmpresa gestorEmpresa = new GestorEmpresa(TipoSGBD.SQLSERVER, baseDatos, usuario, password);
            // EJERCICIO 1
            System.out.println("insertar familiar");
            gestorEmpresa.insertarFamiliar(new Familiar(
                    "54321343","54321342",
                    "Mario","Gago","Acuña",
                    LocalDate.now(),"Hermano",'H'));
            // EJERCICIO 2
            System.out.println("insertar vehiculos");
            gestorEmpresa.insertarVehiculo(new VehiculoRenting("0000AAA","Ford","F1",'G',LocalDate.now(),300.0,24));
            gestorEmpresa.insertarVehiculo(new VehiculoPropio("1111BBB","Mercedes","M1",'D',LocalDate.now(),40000.0));
            // EJERCICIO 3
            System.out.println("Cambiar departamento de proyecto");
            gestorEmpresa.cambiarDepartamentoProyecto("PESROAL","PORTAL");
            //EJERCICIO 4
            System.out.println("Eliminar proyecto");
            gestorEmpresa.borrarProyecto(11);
            //EJERCICIO 5
            System.out.println("Incrementar salario de empleados fijos");
            List<String> NSSs = List.of("0010010","0110010","0999900");
            gestorEmpresa.incrementarSalarioEmpleadosFijos(1.0,NSSs);
            //EJERCICIO 6
            System.out.println("Insertar proyecto");
            gestorEmpresa.insertarProyecto(new Proxecto(5,"4","Vigo"));
            //EJERCICIO 7
            System.out.println("Incrementar salario a un departamento");
            gestorEmpresa.incrementarSalarioDepartamento(1.0,4);
            //EJERCICIO 8
            System.out.println("Empleados con más de X num de proyectos");
            gestorEmpresa.obtenerEmpleadosConMasProyectos();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
