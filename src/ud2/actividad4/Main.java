package ud2.actividad4;


import ud2.actividad4.logica.GestorEmpresa;
import ud2.actividad4.utilidades.TipoSGBD;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String baseDatos = "BDEMPRESA25";
        String usuario = "sa";
        String password = "abc123.";

        try {
            GestorEmpresa gestorEmpresa = new GestorEmpresa(TipoSGBD.SQLSERVER,baseDatos,usuario,password);
            //ejercicio1
            System.out.println("Cambiar domicilio");
            gestorEmpresa.cambiarDomicilio("54321342","Calle Falsa",123,"2B","28080","Madrid");
            //ejercicio2
            System.out.println("Datos proyecto");
            gestorEmpresa.obtenerDatosProyecto(4);
            //ejercicio3
            System.out.println("Obtener departamentos con mas de X proyectos");
            gestorEmpresa.departamentosQueControlan(1);
            //ejercicio4
            System.out.println("Número de empleados de departamento");
            gestorEmpresa.numeroEmpregadoDepartamento("INFORMÁTICA");
            //ejercicio5
            System.out.println("Empleado fijo o temporal");
            gestorEmpresa.getTipoEmpelado("54321342");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
