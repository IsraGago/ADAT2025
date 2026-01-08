package ud2.actividad1;

import ud2.actividad1.logica.GestorEmpresa;
import ud2.actividad1.utilidades.TipoSGBD;

import java.io.File;
import java.nio.file.Path;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        String baseDatos = "BDEMPRESA25";
        String rutaSQLite = "./src/ud2/actividad1/persistencia/dbEmpresa.db";
        String usuario = "sa";
        String password = "abc123.";
        boolean borrarTablasSiExsisten = true;

        try{
            GestorEmpresa gestorEmpresa = new GestorEmpresa(TipoSGBD.SQLITE,rutaSQLite,usuario,password);
            gestorEmpresa.mostrarMetadatos();
            gestorEmpresa.mostrarDepartamentos();
            gestorEmpresa.crearTablasExtra(borrarTablasSiExsisten);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
