package ud2.actividad1.utilidades;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ud2.actividad1.utilidades.TipoSGBD.*;


public class GestorConexiones {
    public static Connection getConnection(ud2.actividad1.utilidades.TipoSGBD tipo, String baseDatos, String usuario, String password) throws SQLException {
        String url;
        switch (tipo) {
            case SQLSERVER -> {
                url = "jdbc:sqlserver://localhost:1433;" +
                        "databaseName=" + baseDatos + ";" +
                        "encrypt = true;" +
                        "trustServerCertificate = true;";
            }
            case MYSQL -> {
                url = "jdbc:mysql://localhost:3306/" + baseDatos + "?serverTimezone=UTC";
            }
            case SQLITE -> {
                url = "jdbc:sqlite://" + baseDatos;
            }
            default -> {
                throw new UnsupportedOperationException("Tipo SGBD no soportado: " + tipo);
            }
        }
        if (tipo == SQLITE) {
            return DriverManager.getConnection(url);
        } else {
            return DriverManager.getConnection(url, usuario, password);
        }
    }

    public static String obtenerMetadatos(Connection con) throws SQLException {
        // DatabaseMetaData meta  = con.getMetaData();
        var meta = con.getMetaData();
        StringBuilder sb = new StringBuilder();

        sb.append("Driver: ").append(meta.getDriverName()).append("\n");
        sb.append("Versión del driver: ").append(meta.getDriverVersion()).append("\n");
        sb.append("Producto BD: ").append(meta.getDatabaseProductName()).append("\n");
        sb.append("Versión BD: ").append(meta.getDatabaseProductVersion()).append("\n");
        sb.append("Url: ").append(meta.getURL()).append("\n");
        sb.append("Usuario BD: ").append(meta.getUserName()).append("\n");

        return sb.toString();
    }

    public static void cerrarConexion(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
