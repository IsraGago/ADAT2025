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
            gestorEmpresa.mostrarMetadatos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
