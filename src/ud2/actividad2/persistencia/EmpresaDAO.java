package ud2.actividad2.persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ud2.actividad2.clases.Departamento;
import ud2.actividad2.dto.*;
import ud2.actividad2.utilidades.GestorConexiones;
import ud2.actividad2.utilidades.TipoSGBD;

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

    public static ArrayList<Departamento> getDepartamentos(Connection con) throws SQLException {
        ResultSet rs = GestorConexiones.ejecutarConsulta(con, "SELECT * FROM DEPARTAMENTO");
        ArrayList<ud2.actividad2.clases.Departamento> departamentos = new ArrayList<>();
        while (rs.next()) {
            Departamento departamento = new Departamento(rs.getInt("NumDepartamento"), rs.getString("NomeDepartamento"), rs.getString("NSSDirector"));
            departamentos.add(departamento);
        }
        return departamentos;
    }

    public static Map<Integer, String> getDepartamentosConProyectos(Connection con) throws SQLException {
        String sql = "SELECT *" +
                "From DEPARTAMENTO" +
                "where NumDepartamento in (select distinct NumDepartControla from PROXECTO);";
        ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);
        Map<Integer, String> departamentosConProyectos = new HashMap<>();
        while (rs.next()) {
            departamentosConProyectos.put(rs.getInt("NumDepartamento"), rs.getString("NSSDirector"));
        }
        return departamentosConProyectos;
    }


    // TODO:
    public static boolean existeDepartamento(Connection con, String nombre) throws SQLException {
        String sql = "select count(NomeDepartamento) from DEPARTAMENTO where NomeDepartamento = '" + nombre + "'";
        ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);
        if (rs.next()) {
            return rs.getInt(1) > 0;
        } else return false;
    }

    public static int getUltimoNumDepartamento(Connection con) throws SQLException {
        String sql = "SELECT  max(NumDepartamento) from DEPARTAMENTO";
        ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);
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
        GestorConexiones.ejecutarSentencia(con, sql, getUltimoNumDepartamento(con) + 1, nombre, nssDirector);
        // TODO RETORNAR SI SE EJECUTÓ BIEN
        return false;
    }

    public static void crearTablas(Connection con, boolean borrarSiExsisten) throws SQLException {
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
        if (borrarSiExsisten && GestorConexiones.tablaExiste(con, "FAMILIAR") || GestorConexiones.tablaExiste(con, "VEHICULO")) {
            GestorConexiones.borrarTablas(con, "FAMILIAR", "VEHICULO");
        }
        GestorConexiones.ejecutarLoteTransaccional(con, sqlVehiculos, sqlFamiliares);
    }

    public List<Departamento> obtenerDepartamentosConProyectos() {
        // ejercicio 1
        List<Departamento> lista = new ArrayList<>();
        String sql = "Select * from DEPARTAMENTO where NumDepartamento in (select distinct NumDepartamentoControla From PROXECTO)";
        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);) {
            while (rs.next()) {
                lista.add(new Departamento(rs.getInt("NumDepartamento"), rs.getString("NomeDepartamento")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los departamentos con proyectos: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<DirectorDepartamentoDTO> obtenerDirectoresConProyectos() {
        // ejercicio 2
        List<DirectorDepartamentoDTO> lista = new ArrayList<>();
        String sql = "select d.NumDepartamento, d.NomeDepartamento, Nome, Apelido1, Apelido2\n" +
                "from DEPARTAMENTO d join EMPREGADO e\n" +
                "on d.NSSDirector = e.NSS\n" +
                "where exists( SELECT 1 from EMPREGADO_PROXECTO ep where ep.NSSEmpregado = e.NSS);";

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);) {
            while (rs.next()) {
                lista.add(new DirectorDepartamentoDTO(
                        rs.getInt("NumDepartamento"),
                        rs.getString("NomeDepartamento"),
                        rs.getString("Nome"),
                        rs.getString("Apelido1"),
                        rs.getString("Apelido2")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los directores de departamento con proyectos asignados: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<EmpleadoEdadDTO> obtenerEmpleadosConEdad() {
        // ejercicio 3
        List<EmpleadoEdadDTO> lista = new ArrayList<>();
        String sql = "Select NSS,\n" +
                "       Nome,\n" +
                "       Apelido1,\n" +
                "       Apelido2,\n" +
                "       (strftime('%Y', 'now') - strftime('%Y', DataNacemento))\n" +
                "           - (strftime('%m-%d', 'now') < strftime('%m-%d', DataNacemento)) AS edad\n" +
                "from EMPREGADO;";

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);) {
            while (rs.next()) {
                lista.add(new EmpleadoEdadDTO(
                        rs.getString("NSS"),
                        rs.getString("Nome"),
                        rs.getString("Apelido1"),
                        rs.getString("Apelido2"),
                        rs.getInt("edad")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los empleados y sus edades: " + e.getMessage(), e);
        }
        return lista;

    }

    public List<EmpleadoTipo> obtenerEmpleadoPorDepartamento(String nombreDepartamento) {
        // ejercicio 4
        List<EmpleadoTipo> lista = new ArrayList<>();
        String sql = "SELECT\n" +
                "    e.Nome || ' ' || e.Apelido1 || ' ' || COALESCE(e.Apelido2, '') AS empleado,\n" +
                "    CASE\n" +
                "        WHEN ef.NSS IS NOT NULL THEN 'Fixo'\n" +
                "        WHEN et.NSS IS NOT NULL THEN 'Temporal'\n" +
                "        ELSE 'Sen categoría'\n" +
                "    END AS tipo\n" +
                "FROM EMPREGADO e\n" +
                "JOIN DEPARTAMENTO d\n" +
                "    ON e.NumDepartamentoPertenece = d.NumDepartamento\n" +
                "LEFT JOIN EMPREGADOFIXO ef\n" +
                "    ON e.NSS = ef.NSS\n" +
                "LEFT JOIN EMPREGADOTEMPORAL et\n" +
                "    ON e.NSS = et.NSS\n" +
                "WHERE d.NomeDepartamento = '" + nombreDepartamento + "';";

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);) {
            while (rs.next()) {
                lista.add(new EmpleadoTipo(
                        rs.getString("empleado"),
                        rs.getString("tipo")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los empleados por deparamento: " + e.getMessage(), e);
        }
        return lista;

    }

    public List<EmpleadoFijoProyecto> obtenerEmpleadoFijoPorPorProyectoYLocalidad(String nombreProyecto, String localidad) {
        // ejercicio 5
        List<EmpleadoFijoProyecto> lista = new ArrayList<>();
        String sql = "select e.nss, concat(Nome, \" \", Apelido1, \" \", Apelido2) as nombreCompleto, Salario, NomeDepartamento\n" +
                "from PROXECTO p\n" +
                "         inner join EMPREGADO_PROXECTO ep on ep.NumProxecto = p.NumProxecto\n" +
                "         inner join EMPREGADO e on e.NSS = ep.NSSEmpregado\n" +
                "         inner join EMPREGADOFIXO ef on e.NSS = ef.NSS\n" +
                "         inner join DEPARTAMENTO d on d.NumDepartamento = e.NumDepartamentoPertenece\n" +
                "where e.Localidade = \"" + localidad + "\"\n" +
                "  AND p.NomeProxecto = \"" + nombreProyecto + "\";";

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);) {
            while (rs.next()) {
                lista.add(new EmpleadoFijoProyecto(
                        rs.getString("nombreCompleto"),
                        rs.getDouble("Salario"),
                        rs.getString("NomeDepartamento")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los empleados por proyecto y localidad: " + e.getMessage(), e);
        }
        return lista;

    }

    //TODO: hacer el 6

    public List<DepartamentoConMasDeNDTO> obtenerDepartamentosConMasDeN(int n) {
        // ejercicio 7
        List<DepartamentoConMasDeNDTO> lista = new ArrayList<>();
        String sql = "select NumDepartamento, count(NomeDepartamento) as numEpleados\n" +
                "from DEPARTAMENTO d\n" +
                "         inner join EMPREGADO e\n" +
                "                    on d.NumDepartamento = e.NumDepartamentoPertenece\n" +
                "group by NumDepartamento\n" +
                "having numEpleados > ?;";

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql, n);) {
            while (rs.next()) {
                lista.add(new DepartamentoConMasDeNDTO(
                        rs.getInt("NumDepartamento"),
                        rs.getInt("numEpleados")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los departamentos con mas que x número de empleados: " + e.getMessage(), e);
        }
        return lista;

    }

    public List<EmpleadoFijoSalarioDTO> obtenerFijosConSalarioMayorQue(double salario) {
        // ejercicio 8
        List<EmpleadoFijoSalarioDTO> lista = new ArrayList<>();
        String sql = "select *\n" +
                "from EMPREGADO e\n" +
                "         inner join EMPREGADOFIXO ef on e.nss = ef.NSS\n" +
                "where salario > ?;";

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql, salario);) {
            while (rs.next()) {
                lista.add(new EmpleadoFijoSalarioDTO(
                        rs.getString("NSS"),
                        rs.getString("Nome"),
                        rs.getString("Apelido1"),
                        rs.getString("Apelido2"),
                        rs.getInt("Salario")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los empleados con mas de x salario: " + e.getMessage(), e);
        }
        return lista;

    }
}
