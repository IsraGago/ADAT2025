package ud2.actividad3.utilidades;


import ud2.actividad3.utilidades.TipoSGBD;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ud2.actividad3.utilidades.TipoSGBD.*;


public class GestorConexiones {
    public static final String PUERTO_SQLSERVER = "1433";
    public static final String PUERTO_MYSQL = "3306";

    public static Connection getConnection(TipoSGBD tipo, String rutaSqlite) throws SQLException {
        if (tipo == TipoSGBD.SQLITE) {
            return getConnection(tipo, rutaSqlite, "", "");
        } else {
            throw new UnsupportedOperationException("Este método solo puede usarse para conexiones SQLITE.");
        }
    }

    public static Connection getConnection(TipoSGBD tipo, String baseDatos, String usuario, String password) throws SQLException {
        String url;
        switch (tipo) {
            case SQLSERVER -> {
                url = "jdbc:sqlserver://localhost:" + PUERTO_SQLSERVER + ";" +
                        "databaseName=" + baseDatos + ";" +
                        "encrypt = true;" +
                        "trustServerCertificate = true;";
            }
            case MYSQL -> {
                url = "jdbc:mysql://localhost:" + PUERTO_MYSQL + "/" + baseDatos + "?serverTimezone=UTC";
            }
            case SQLITE -> {
                // hago un file con la ruta que me pasan para que pueda ser relativa
                File fichero = new File(baseDatos);
                url = "jdbc:sqlite://" + fichero.getAbsolutePath();
            }
            default -> {
                throw new UnsupportedOperationException("Tipo SGBD no soportado: " + tipo);
            }
        }
        if (tipo == SQLITE) {
            Connection con = DriverManager.getConnection(url);
            try (Statement st = con.createStatement()) {
                st.execute("PRAGMA foreign_keys = ON");
            }
            return con;
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

    public static ResultSet ejecutarConsulta(Connection con, String sqlConsulta, Object... parametros) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(sqlConsulta);
        for (int i = 0; i < parametros.length; i++) {
            stmt.setObject(i + 1, parametros[i]);
        }
        return stmt.executeQuery();
    }

    public static ResultSet ejecutarConsulta(Connection con, String sqlConsulta) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(sqlConsulta);
        return stmt.executeQuery();
    }

    // permite sentencias DDL y DML
    public static int ejecutarSentencia(Connection con, String sql, Object... parametros) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            setParametros(stmt, parametros);
            stmt.executeUpdate();
            return stmt.getUpdateCount();
        }
    }

    private static void setParametros(PreparedStatement stmt, Object... parametros) throws SQLException {
        for (int i = 0; i < parametros.length; i++) {
            stmt.setObject(i + 1, parametros[i]);
        }
    }

    public static void cerrarConexion(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void borrarTablas(Connection con, String... tablas) throws SQLException {
        try {
            con.setAutoCommit(false); // iniciar transaccion
            try (Statement st = con.createStatement()) {
                for (String tabla : tablas) {
                    if (tablaExiste(con, tabla)) {
                        st.addBatch("DROP TABLE " + tabla);
                    }
                }
                st.executeBatch();
                con.commit();
            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.setAutoCommit(true);
        }
    }

    public static boolean tablaExiste(Connection con, String tabla) throws SQLException {
        try (ResultSet rs = con.getMetaData().getTables(null, null, tabla, null)) {
            return rs.next();
        }
    }

    public static void ejecutarLoteTransaccional(Connection con, String... SQLs) throws SQLException {
        try {
            con.setAutoCommit(false);
            try (Statement st = con.createStatement()) {
                for (String sql : SQLs) {
                    st.addBatch(sql);
                }
                st.executeBatch();
                con.commit();
            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            con.setAutoCommit(true);
        }
    }

    public static int insertarYRetornarClaveGenerada(Connection con, String sql, Object... params) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParametros(ps, params);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("No se generó clave primaria");
                }
            }
        }
    }

    public static ArrayList<String> obtenerClavesForaneas(Connection con, String nombreTabla) {
        try {
            ArrayList<String> claves = new ArrayList<>();
            DatabaseMetaData meta = con.getMetaData();
            try (ResultSet rs = meta.getPrimaryKeys(null, null, nombreTabla)) {
                while (rs.next()) {
                    String fkCol = rs.getString("FKCOLUMN_NAME");
                    String pkTab = rs.getString("PKTABLE_NAME");
                    String pkCol = rs.getString("PKCOLUMN_NAME");
                    claves.add(fkCol + "-->" + pkTab + "(" + pkCol + ")");
                }
            }
            return claves;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet crearResultSetActualizable(Connection con, String sql) throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return stmt.executeQuery(sql);
    }

    public static ResultSet crearResultSetActualizable(Connection con, String sql, Object ... parametros) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        for (int i = 0; i < parametros.length; i++) {
            stmt.setObject(i + 1, parametros[i]);
        }
        return stmt.executeQuery();
    }

//    public static int obtenerReglaBorradoFK(Connection con, String tablaPadre, String tablaHija) {
//        try {
//            DatabaseMetaData meta = con.getMetaData();
//            String catalog = con.getCatalog();
//            String schema = con.getSchema();
//
//            try(ResultSet rs = meta.getImportedKeys(catalog, schema, tablaHija)) {
//                while (rs.next()) {
//                    String pkTable = rs.getString("PKTABLE_NAME");
//                    if ( pkTable != null && pkTable.equalsIgnoreCase(nombreTabla)) {
//                        return rs.getShort("DELETE_RULE");
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }


}
