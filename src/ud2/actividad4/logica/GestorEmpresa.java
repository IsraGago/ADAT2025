package ud2.actividad4.logica;

import ud2.actividad4.clases.Departamento;
import ud2.actividad4.clases.Familiar;
import ud2.actividad4.clases.Vehiculo;
import ud2.actividad4.persistencia.EmpresaDAO;
import ud2.actividad4.utilidades.GestorConexiones;
import ud2.actividad4.utilidades.TipoSGBD;

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


    public void mostrarMetadatos() {
        try {
            System.out.println(GestorConexiones.obtenerMetadatos(con));
        } catch (SQLException e) {
            System.out.println("Error al obtener los metadatos");
        }
    }




}
