package ud2.actividad1.utilidades;


import ud2.actividad1.clases.Departamento;

import java.sql.*;
import java.util.ArrayList;

import static ud2.actividad1.utilidades.TipoSGBD.*;


public class GestorConexiones {
    public static final String PUERTO_SQLSERVER = "1433";
    public static final String PUERTO_MYSQL = "3306";

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
    public static void ejecutarSentencia(Connection con, String sql, Object... parametros) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            setParametros(stmt, parametros);
            stmt.executeUpdate();
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
        }
        finally {
            con.setAutoCommit(true);
        }
    }

    public static ArrayList<Departamento> getDepartamentos(Connection con) throws SQLException {
        ResultSet rs = ejecutarConsulta(con, "SELECT * FROM DEPARTAMENTO");
        ArrayList<Departamento> departamentos = new ArrayList<>();
        while (rs.next()) {
            Departamento departamento = new Departamento(rs.getInt("NumDepartamento"), rs.getString("NomeDepartamento"), rs.getString("NSSDirector"));
            departamentos.add(departamento);
        }
        return departamentos;
    }

    public static boolean existeDepartamento(Connection con, String nombre) throws SQLException {
        String sql = "select count(NomeDepartamento) from DEPARTAMENTO where NomeDepartamento = '" + nombre + "'";
        ResultSet rs = ejecutarConsulta(con, sql);
        if (rs.next()) {
            return rs.getInt(1) > 0;
        } else return false;
    }

    public static int getUltimoNumDepartamento(Connection con) throws SQLException {
        String sql = "SELECT  max(NumDepartamento) from DEPARTAMENTO";
        ResultSet rs = ejecutarConsulta(con, sql);
        if (rs.next()) {
            return rs.getInt(1);
        } else return 0;
    }

    public static boolean insertarDepartamento(Connection con, String nombre, String nssDirector) throws SQLException {
        if (existeDepartamento(con, nombre)) {
            return false;
        }
        String sql = "insert into DEPARTAMENTO (NumDepartamento, NomeDepartamento, NSSDirector)VALUES (?,?,?);";
//        PreparedStatement stmt = con.prepareStatement(sql);
//        stmt.setInt(1, getUltimoNumDepartamento(con));
//        stmt.setString(2, nombre);
//        stmt.setString(3, nssDirector);
        ejecutarSentencia(con, sql, getUltimoNumDepartamento(con) + 1, nombre, nssDirector);
        // TODO RETORNAR SI SE EJECUTÓ BIEN
        return false;
    }

    public static void crearTablas(Connection con,boolean borrarSiExsisten) throws SQLException {
        String sqlVehiculos = "Create Table VEHICULO(\n" +
                "    Codigo INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    Matricula TEXT NOT NULL UNIQUE,\n" +
                "    Marca Text NOT NULL,\n" +
                "    Modelo TEXT NOT NULL,\n" +
                "    Tipo TEXT NOT NULL CHECK (Tipo in ('G','D'))\n" +
                ")";

        String sqlFamiliares = "Create table FAMILIAR(\n" +
                "    Codigo INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    Nss TEXT NOT NULL UNIQUE,\n" +
                "    NssEmpregado TEXT NOT NULL,\n" +
                "    Nombre TEXT NOT NULL,\n" +
                "    Apellido1 TEXT NOT NULL,\n" +
                "    Apellido2 TEXT,\n" +
                "    FechaNac TEXT NOT NULL,\n" +
                "    Parentesto TEXT NOT NULL,\n" +
                "    Sexo TEXT NOT NULL CHECK (Sexo in ('H','M')) DEFAULT 'M'\n" +
                ")";
        if ( borrarSiExsisten && tablaExiste(con, "FAMILIAR") && tablaExiste(con, "VEHICULO")) {
            borrarTablas(con,"FAMILIAR","VEHICULO");
        }
        ejecutarLoteTransaccional(con, sqlVehiculos, sqlFamiliares);
    }
}
