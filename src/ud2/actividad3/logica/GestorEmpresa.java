package ud2.actividad3.logica;

import ud1.actividad5personas.clases.Empresa;
import ud2.actividad3.clases.*;
import ud2.actividad3.persistencia.EmpresaDAO;
import ud2.actividad3.utilidades.GestorConexiones;
import ud2.actividad3.utilidades.TipoSGBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestorEmpresa {
    Connection con = null;

    public GestorEmpresa(TipoSGBD tipo, String baseDatos, String usuario, String password) throws SQLException {
        con = GestorConexiones.getConnection(tipo, baseDatos, usuario, password);
    }

    public GestorEmpresa(TipoSGBD tipo, String rutaSQLite) throws SQLException {
        con = GestorConexiones.getConnection(tipo, rutaSQLite);
    }

    // ACTIVIDAD 3: Actualizaciones
    public void insertarFamiliar(Familiar familiar) {
        // ejercicio 1
        try {
            int resultado = EmpresaDAO.insertarFamiliar(con, familiar);
            switch (resultado) {
                case 0 -> {
                    System.out.println("Familiar insertado");
                }
                case -1 -> {
                    System.out.println("Error de fk");
                }
                case -2 -> {
                    System.out.println("Error de UQ");
                }
                case -3 -> {
                    System.out.println("Error restricción CHECK");
                }case -4 -> {
                    System.out.println("Error restricción PK");
                }default -> {
                    System.out.println(resultado);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertarVehiculo(Vehiculo vehiculo) {
        // ejercicio 2
        int resultado = EmpresaDAO.insertarVehiculo(con, vehiculo);
        switch (resultado){
            case 0 -> {
                System.out.println("Vehículo insertado");
            }
            case -1 -> {
                System.out.println("Error de FK");
            }
            case -2 -> {
                System.out.println("Error de UQ");
            }
            case -3 -> {
                System.out.println("Error restricción CHECK");
            }case -4 -> {
                System.out.println("Error restricción PK");
            }
        }
    }

    public void cambiarDepartamentoProyecto(String nombreDepartamento, String nombreProyecto) {
        // ejercicio 3
        int filas = EmpresaDAO.cambiarDepartamentoProyecto(con, nombreDepartamento, nombreProyecto);
        if (filas != 1) {
            System.out.println("No han habido cambios. Comprueba la existencia del proyecto.");
        } else {
            System.out.println("Proyecto actualizado");
        }
    }

    public void borrarProyecto(int numProyecto){
        if (EmpresaDAO.borrarProyecto(con,numProyecto)){
            System.out.println("Proyecto eliminado");
        } else {
            System.out.println("No se ha podido eliminar el proyecto. Comprueba su existencia.");
        }
    }

    public void incrementarSalarioEmpleadosFijos(double incremento, List<String> listaNss){
        int numFilas = EmpresaDAO.incrementarSalarioEmpleadosFijos(con,incremento,listaNss);
        System.out.println("Se han actualizado " + numFilas + " empleados fijos.");
    }

    public void insertarProyecto(Proxecto p){
        try {
            if (EmpresaDAO.insertarProyecto(con,p)){
                System.out.println("Proyecto insertado");
            } else {
                System.out.println("No se ha podido insertar el proyecto. Comprueba las restricciones.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(e.getMessage().contains("TYPE_FORWARD_ONLY")){
                System.out.println("SQLite no soporta este tipo de operación.");
            }
        }
    }

    public void incrementarSalarioDepartamento(double incremento, int numDepartamento){
        int afectados = EmpresaDAO.incrementarSalarioDepartamento(con, incremento,  numDepartamento);
        System.out.println("Se han actualizado " + afectados + " empleados del departamento " + numDepartamento);
    }

    public void obtenerEmpleadosConMasProyectos(){
        List<EmpleadoInfoDTO> lista = EmpresaDAO.obtenerEmpleadosConMasProyectos(con,1);
        for (EmpleadoInfoDTO e: lista) {
            System.out.println(e);
        }
    }
}
