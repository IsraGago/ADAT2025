package ud2.actividad1;

import ud2.actividad1.utilidades.GestorConexiones;
import ud2.actividad1.utilidades.TipoSGBD;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        String baseDatos = "BDEMPRESA25";
        String usuario = "sa";
        String password = "abc123.";
        try{
            Connection con = GestorConexiones.getConnection(TipoSGBD.SQLSERVER,baseDatos,usuario,password);
            System.out.println(GestorConexiones.obtenerMetadatos(con));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
