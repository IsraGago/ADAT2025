package ud2.actividad1;

import ud2.actividad1.clases.Departamento;
import ud2.actividad1.logica.GestorEmpresa;
import ud2.actividad1.utilidades.GestorConexiones;
import ud2.actividad1.utilidades.TipoSGBD;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        String baseDatos = "BDEMPRESA25";
        String rutaSQLite = "D:\\igagoacun\\ADAT\\ADAT2025\\src\\ud2\\actividad1\\persistencia\\dbEmpresa.db";
        String usuario = "sa";
        String password = "abc123.";
        boolean borrarTablasSiExsisten = true;
        try{
//            Connection con = GestorConexiones.getConnection(TipoSGBD.SQLSERVER,baseDatos,usuario,password);
//            Connection con = GestorConexiones.getConnection(TipoSGBD.MYSQL,baseDatos,usuario,password);
//            Connection con = GestorConexiones.getConnection(TipoSGBD.SQLITE,rutaSQLite,usuario,password);
//            System.out.println(GestorConexiones.obtenerMetadatos(con));
            GestorEmpresa gestorEmpresa = new GestorEmpresa(TipoSGBD.SQLITE,rutaSQLite,usuario,password);
            gestorEmpresa.mostrarMetadatos();
            gestorEmpresa.mostrarDepartamentos();
            // gestorEmpresa.insertarDepartamento("Nuevo departamento","54321342");
            gestorEmpresa.crearTablasExtra(borrarTablasSiExsisten);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
