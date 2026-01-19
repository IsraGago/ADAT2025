package ud2.actividad4.persistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ud2.actividad4.clases.*;
import ud2.actividad4.dto.*;
import ud2.actividad4.utilidades.GestorConexiones;
import ud2.actividad4.utilidades.TipoSGBD;

public class EmpresaDAO implements AutoCloseable {

    private Connection con;

    public EmpresaDAO(TipoSGBD tipo, String baseDatos, String usuario, String password) throws SQLException {
        con = GestorConexiones.getConnection(tipo, baseDatos, usuario, password);
    }

    public EmpresaDAO(TipoSGBD tipo, String baseDatos) throws SQLException {
        con = GestorConexiones.getConnection(tipo, baseDatos, "", "");
    }

    @Override
    public void close() throws Exception {
        GestorConexiones.cerrarConexion(con);
    }

    public static boolean existeDepartamento(Connection con, String nombre) throws SQLException {
        String sql = "select count(NomeDepartamento) from DEPARTAMENTO where NomeDepartamento = '" + nombre + "'";
        ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);
        if (rs.next()) {
            return rs.getInt(1) > 0;
        } else return false;
    }

    //ACTIVIDAD 4

    public static int cambiarDomicilio(Connection con, String nss, String rua, int numero, String piso, String cp, String localidade) {
        // ejercicio1
        String sql = "{call sp_CambioDomicilio(?,?,?,?,?,?)}";
        try (CallableStatement cs = con.prepareCall(sql)) {
            cs.setString(1, nss);
            cs.setString(2, rua);
            cs.setInt(3, numero);
            cs.setString(4, piso);
            cs.setString(5, cp);
            cs.setString(6, localidade);

            return cs.executeUpdate(); // DEVUELVE EL NÚMERO DE FILAS
        } catch (SQLException e) {
            throw new RuntimeException("Error: al ejecutar el sp_CambioDomicilio: " + e.getMessage(), e);
        }
    }

    public static ProxectoInfoDTO obtenerDatosProyecto(Connection con, int numProyecto) {
        // ejercicio2
        String sql = "{ call sp_DatosProxectos(?,?,?,?)}";
        try (CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, numProyecto);

            cs.registerOutParameter(2, Types.VARCHAR);
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.registerOutParameter(4, Types.INTEGER);

            cs.execute();

            String nombre = cs.getString(2);
            if (nombre == null) {
                return null;
            }

            String lugar = cs.getString(3);
            int numDepartamento = cs.getInt(4);
            return new ProxectoInfoDTO(nombre, lugar, numDepartamento);

        } catch (SQLException e) {
            throw new RuntimeException("Error: al ejecutar el sp_DatosProxectos: " + e.getMessage(), e);
        }
    }

    public static List<Departamento> departamentosQueControlan(Connection con, int valor) {
        // ejercicio3
        List<Departamento> lista = new ArrayList<>();
        String sql = "{call sp_DepartControlaProxec(?)}";
        try (CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, valor);
            boolean resultado = cs.execute();

            if (resultado) {
                try (ResultSet rs = cs.getResultSet()) {
                    while (rs.next()) {
                        Departamento d = new Departamento(
                                rs.getInt("NumDepartamento"),
                                rs.getString("NomeDepartamento"),
                                rs.getString("NSSDirector"));
                        lista.add(d);
                    }
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al ejecutar el procedimiento sp_DepartControlaProxec: " + e.getMessage(), e);
        }
    }

    public static int numeroEmpregadoDepartamento(Connection con, String nombreDepartamento) throws SQLException {
        // ejercicio4
        String sql = "{? = call fn_nEmpDepart(?)}";
        if (existeDepartamento(con, nombreDepartamento)) {
            try (CallableStatement cs = con.prepareCall(sql)) {
                cs.registerOutParameter(1, Types.INTEGER);
                cs.setString(2, nombreDepartamento);
                cs.execute();
                return cs.getInt(1);
            } catch (SQLException e) {
                throw new RuntimeException("Error al ejecutar fn_nEmpDepart: " + e.getMessage(), e);
            }
        }
        return 0;
    }

    // EXTRAS
    public static String getTipoEmpelado(Connection con, String nss) {
        // ejercicio5
        String sql = "{? = call fn_getTipoEmpelado(?)}";
            try (CallableStatement cs = con.prepareCall(sql)) {
                cs.registerOutParameter(1, Types.VARCHAR);
                cs.setString(2, nss);
                cs.execute();
                return cs.getString(1);
            } catch (SQLException e) {
                throw new RuntimeException("Error al ejecutar fn_getTipoEmpelado: " + e.getMessage(), e);
            }
    }

}
