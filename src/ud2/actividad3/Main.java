package ud2.actividad3;


import ud2.actividad3.logica.GestorEmpresa;
import ud2.actividad3.utilidades.TipoSGBD;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String baseDatos = "BDEMPRESA25";
        String rutaSQLite = "./src/ud2/actividad3/persistencia/dbEmpresa.db";

        try {
            GestorEmpresa gestorEmpresa = new GestorEmpresa(TipoSGBD.SQLITE, rutaSQLite);
            System.out.println("\n1. DEPARTAMENTOS CON PROYECTOS\n");
            gestorEmpresa.mostrarDepartamentosConProyectos();

            System.out.println("\n2. DIRECTORES CON PROYECTOS\n");
            gestorEmpresa.mostrarDirectoresConProyectos();

            System.out.println("\n3. EMPLEADOS Y EDADES\n");
            gestorEmpresa.mostrarEmpleadosConEdad();

            System.out.println("\n4. EMPLEADOS EN DEPARTAMENTO CONCRETO\n");
            gestorEmpresa.mostrarEmpleadoPorDepartamento("INFORMÁTICA");

            System.out.println("\n5. EMPLEADOS FIJOS EN PROYECTO Y LOCALIDAD CONCRETOS\n");
            gestorEmpresa.mostrarEmpleadoFijoPorPorProyectoYLocalidad("MELLORAS SOCIAIS", "Vigo");

            System.out.println("\n6. DEPARTAMENTOS CON SU NUM DE EMPLEADOS POR TIPO\n");
            gestorEmpresa.mostrarNumEpleadosDepartamentosPorTipo();

            System.out.println("\n7. DEPARTAMENTOS CON MAS DE N EMPLEADOS\n");
            gestorEmpresa.mostrarDepartamentosConMasDeN(4);

            System.out.println("\n8. EMPLEADOS FIJOS CON SALARIO > N\n");
            gestorEmpresa.mostrarFijosConSalarioMayorQue(1300.0);

            System.out.println("\n9. EMPLEADOS FIJOS CON MAS SALARIO DE CADA DEPARTAMENTO\n");
            gestorEmpresa.mostrarEmpleadosFijosMaxSalarioDepartamento();

            System.out.println("\n10. DEPARTAMENTOS CON MAX NÚMERO DE PROYECTOS\n");
            gestorEmpresa.mostrarDepartamentosMaxProyectosControlados();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
