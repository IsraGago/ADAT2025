package ud2.actividad3.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ud2.actividad3.clases.*;
import ud2.actividad3.dto.*;
import ud2.actividad3.utilidades.GestorConexiones;
import ud2.actividad3.utilidades.TipoSGBD;

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
        ArrayList<ud2.actividad3.clases.Departamento> departamentos = new ArrayList<>();
        while (rs.next()) {
            Departamento departamento = new Departamento(rs.getInt("NumDepartamento"), rs.getString("NomeDepartamento"), rs.getString("NSSDirector"));
            departamentos.add(departamento);
        }
        return departamentos;
    }

    public static Map<Integer, String> getDepartamentosConProyectos(Connection con) throws SQLException {
        String sql = "SELECT *" + "From DEPARTAMENTO" + "where NumDepartamento in (select distinct NumDepartControla from PROXECTO);";
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
        String sqlVehiculos = "Create Table VEHICULO(\n" + "    Codigo INTEGER PRIMARY KEY AUTOINCREMENT,\n" + "    Matricula TEXT NOT NULL UNIQUE,\n" + "    Marca Text NOT NULL,\n" + "    Modelo TEXT NOT NULL,\n" + "    Tipo TEXT NOT NULL CHECK (Tipo in ('G','D'))\n" + ")";

        String sqlFamiliares = "Create table FAMILIAR(\n" + "    Codigo INTEGER PRIMARY KEY AUTOINCREMENT,\n" + "    Nss TEXT NOT NULL UNIQUE,\n" + "    NssEmpregado TEXT NOT NULL,\n" + "    Nombre TEXT NOT NULL,\n" + "    Apellido1 TEXT NOT NULL,\n" + "    Apellido2 TEXT,\n" + "    FechaNac TEXT NOT NULL,\n" + "    Parentesto TEXT NOT NULL,\n" + "    Sexo TEXT NOT NULL CHECK (Sexo in ('H','M')) DEFAULT 'M'\n" + ")";
        if (borrarSiExsisten && GestorConexiones.tablaExiste(con, "FAMILIAR") || GestorConexiones.tablaExiste(con, "VEHICULO")) {
            GestorConexiones.borrarTablas(con, "FAMILIAR", "VEHICULO");
        }
        GestorConexiones.ejecutarLoteTransaccional(con, sqlVehiculos, sqlFamiliares);
    }

    public static List<Departamento> obtenerDepartamentosConProyectos(Connection con) {
        // ejercicio 1
        List<Departamento> lista = new ArrayList<>();
        String sql = "Select * from DEPARTAMENTO where NumDepartamento in (select distinct NumDepartControla From PROXECTO);";
        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);) {
            while (rs.next()) {
                Departamento departamento = new Departamento(rs.getInt("NumDepartamento"), rs.getString("NomeDepartamento"));
                lista.add(departamento);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los departamentos con proyectos: " + e.getMessage(), e);
        }
        return lista;
    }

    public static List<DirectorDepartamentoDTO> obtenerDirectoresConProyectos(Connection con) {
        // ejercicio 2
        List<DirectorDepartamentoDTO> lista = new ArrayList<>();
        String sql = "select d.NumDepartamento, d.NomeDepartamento, Nome, Apelido1, Apelido2\n" + "from DEPARTAMENTO d join EMPREGADO e\n" + "on d.NSSDirector = e.NSS\n" + "where exists( SELECT 1 from EMPREGADO_PROXECTO ep where ep.NSSEmpregado = e.NSS);";

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);) {
            while (rs.next()) {
                lista.add(new DirectorDepartamentoDTO(rs.getInt("NumDepartamento"), rs.getString("NomeDepartamento"), rs.getString("Nome"), rs.getString("Apelido1"), rs.getString("Apelido2")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los directores de departamento con proyectos asignados: " + e.getMessage(), e);
        }
        return lista;
    }

    public static List<EmpleadoEdadDTO> obtenerEmpleadosConEdad(Connection con) {
        // ejercicio 3
        List<EmpleadoEdadDTO> lista = new ArrayList<>();
        String sql = "Select NSS,\n" + "       Nome,\n" + "       Apelido1,\n" + "       Apelido2,\n" + "       (strftime('%Y', 'now') - strftime('%Y', DataNacemento))\n" + "           - (strftime('%m-%d', 'now') < strftime('%m-%d', DataNacemento)) AS edad\n" + "from EMPREGADO;";

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);) {
            while (rs.next()) {
                lista.add(new EmpleadoEdadDTO(rs.getString("NSS"), rs.getString("Nome"), rs.getString("Apelido1"), rs.getString("Apelido2"), rs.getInt("edad")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los empleados y sus edades: " + e.getMessage(), e);
        }
        return lista;

    }

    public static List<EmpleadoTipoDTO> obtenerEmpleadoPorDepartamento(Connection con, String nombreDepartamento) {
        // ejercicio 4
        List<EmpleadoTipoDTO> lista = new ArrayList<>();
        String sql = "SELECT\n" + "    e.Nome || ' ' || e.Apelido1 || ' ' || COALESCE(e.Apelido2, '') AS empleado,\n" + "    CASE\n" + "        WHEN ef.NSS IS NOT NULL THEN 'Fixo'\n" + "        WHEN et.NSS IS NOT NULL THEN 'Temporal'\n" + "        ELSE 'Sen categoría'\n" + "    END AS tipo\n" + "FROM EMPREGADO e\n" + "JOIN DEPARTAMENTO d\n" + "    ON e.NumDepartamentoPertenece = d.NumDepartamento\n" + "LEFT JOIN EMPREGADOFIXO ef\n" + "    ON e.NSS = ef.NSS\n" + "LEFT JOIN EMPREGADOTEMPORAL et\n" + "    ON e.NSS = et.NSS\n" + "WHERE d.NomeDepartamento = '" + nombreDepartamento + "';";

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);) {
            while (rs.next()) {
                lista.add(new EmpleadoTipoDTO(rs.getString("empleado"), rs.getString("tipo")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los empleados por deparamento: " + e.getMessage(), e);
        }
        return lista;

    }

    public static List<EmpleadoFijoProyectoDTO> obtenerEmpleadoFijoPorPorProyectoYLocalidad(Connection con, String nombreProyecto, String localidad) {
        // ejercicio 5
        List<EmpleadoFijoProyectoDTO> lista = new ArrayList<>();
        String sql = "select e.nss, concat(Nome, \" \", Apelido1, \" \", Apelido2) as nombreCompleto, Salario, NomeDepartamento\n" + "from PROXECTO p\n" + "         inner join EMPREGADO_PROXECTO ep on ep.NumProxecto = p.NumProxecto\n" + "         inner join EMPREGADO e on e.NSS = ep.NSSEmpregado\n" + "         inner join EMPREGADOFIXO ef on e.NSS = ef.NSS\n" + "         inner join DEPARTAMENTO d on d.NumDepartamento = e.NumDepartamentoPertenece\n" + "where e.Localidade = \"" + localidad + "\"\n" + "  AND p.NomeProxecto = \"" + nombreProyecto + "\";";

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);) {
            while (rs.next()) {
                lista.add(new EmpleadoFijoProyectoDTO(rs.getString("nombreCompleto"), rs.getDouble("Salario"), rs.getString("NomeDepartamento")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los empleados por proyecto y localidad: " + e.getMessage(), e);
        }
        return lista;

    }

    public static List<DepartamentoNumEpleadosDTO> obtenerNumEpleadosDepartamentosPorTipo(Connection con) {
        // ejercicio 6
        List<DepartamentoNumEpleadosDTO> lista = new ArrayList<>();
        String sql = "SELECT d.NumDepartamento,\n" + "       d.NomeDepartamento,\n" + "       COUNT(DISTINCT ef.NSS) AS NumEmpregadosFixos,\n" + "       COUNT(DISTINCT et.NSS) AS NumEmpregadosTemporais\n" + "FROM DEPARTAMENTO d\n" + "LEFT JOIN EMPREGADO e\n" + "    ON e.NumDepartamentoPertenece = d.NumDepartamento\n" + "LEFT JOIN EMPREGADOFIXO ef ON ef.NSS = e.NSS\n" + "LEFT JOIN EMPREGADOTEMPORAL et ON et.NSS = e.NSS\n" + "GROUP BY d.NumDepartamento, d.NomeDepartamento;";

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);) {
            while (rs.next()) {
                lista.add(new DepartamentoNumEpleadosDTO(rs.getInt("NumDepartamento"), rs.getString("NomeDepartamento"), rs.getInt("NumEmpregadosFixos"), rs.getInt("NumEmpregadosTemporais")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los departamentos con su número de empleados por tipo: " + e.getMessage(), e);
        }
        return lista;
    }

    public static List<DepartamentoConMasDeNDTO> obtenerDepartamentosConMasDeN(Connection con, int n) {
        // ejercicio 7
        List<DepartamentoConMasDeNDTO> lista = new ArrayList<>();
        String sql = "select NumDepartamento, count(NomeDepartamento) as numEpleados\n" + "from DEPARTAMENTO d\n" + "         inner join EMPREGADO e\n" + "                    on d.NumDepartamento = e.NumDepartamentoPertenece\n" + "group by NumDepartamento\n" + "having numEpleados > ?;";

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql, n);) {
            while (rs.next()) {
                lista.add(new DepartamentoConMasDeNDTO(rs.getInt("NumDepartamento"), rs.getInt("numEpleados")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los departamentos con mas que x número de empleados: " + e.getMessage(), e);
        }
        return lista;

    }

    public static List<EmpleadoFijoSalarioDTO> obtenerFijosConSalarioMayorQue(Connection con, double salario) {
        // ejercicio 8
        List<EmpleadoFijoSalarioDTO> lista = new ArrayList<>();
        String sql = "select *\n" + "from EMPREGADO e\n" + "         inner join EMPREGADOFIXO ef on e.nss = ef.NSS\n" + "where salario > ?;";

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql, salario);) {
            while (rs.next()) {
                lista.add(new EmpleadoFijoSalarioDTO(rs.getString("NSS"), rs.getString("Nome"), rs.getString("Apelido1"), rs.getString("Apelido2"), rs.getInt("Salario")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los empleados con más de x salario: " + e.getMessage(), e);
        }
        return lista;

    }

    public static List<EmpleadosFijosMaxSalarioDepartamentoDTO> obtenerEmpleadosFijosMaxSalarioDepartamento(Connection con) {
        // ejercicio 9
        List<EmpleadosFijosMaxSalarioDepartamentoDTO> lista = new ArrayList<>();
        String sql = "    SELECT\n" + "    d.NumDepartamento,\n" + "    d.NomeDepartamento,\n" + "    e.Nome,\n" + "    e.Apelido1,\n" + "    e.Apelido2,\n" + "    ef.Salario\n" + "FROM EMPREGADO e\n" + "JOIN EMPREGADOFIXO ef\n" + "    ON e.NSS = ef.NSS\n" + "JOIN DEPARTAMENTO d\n" + "    ON e.NumDepartamentoPertenece = d.NumDepartamento\n" + "WHERE ef.Salario = (\n" + "    SELECT MAX(ef2.Salario)\n" + "    FROM EMPREGADO e2\n" + "    JOIN EMPREGADOFIXO ef2\n" + "        ON e2.NSS = ef2.NSS\n" + "    WHERE e2.NumDepartamentoPertenece = e.NumDepartamentoPertenece\n" + ")\n" + "ORDER BY d.NomeDepartamento ASC;";

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);) {
            while (rs.next()) {
                lista.add(new EmpleadosFijosMaxSalarioDepartamentoDTO(rs.getInt("NumDepartamento"), rs.getString("NomeDepartamento"), rs.getString("Nome"), rs.getString("Apelido1"), rs.getString("Apelido2"), rs.getDouble("Salario")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los empleados con más salario de cada departamento: " + e.getMessage(), e);
        }
        return lista;

    }

    public static List<DepartamentoNumProyectosDTO> obtenerDepartamentosMaxProyectosControlados(Connection con) {
        // ejercicio 10
        List<DepartamentoNumProyectosDTO> lista = new ArrayList<>();
        String sql = "SELECT\n" + "    d.NumDepartamento,\n" + "    d.NomeDepartamento,\n" + "    COUNT(p.NumProxecto) AS NumProxectos\n" + "FROM DEPARTAMENTO d\n" + "LEFT JOIN PROXECTO p\n" + "    ON d.NumDepartamento = p.NumDepartControla\n" + "GROUP BY d.NumDepartamento, d.NomeDepartamento\n" + "HAVING COUNT(p.NumProxecto) = (\n" + "    SELECT MAX(NumProxectos)\n" + "    FROM (\n" + "        SELECT COUNT(*) AS NumProxectos\n" + "        FROM PROXECTO\n" + "        GROUP BY NumDepartControla\n" + "    )\n" + ");";

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql);) {
            while (rs.next()) {
                lista.add(new DepartamentoNumProyectosDTO(rs.getInt("NumDepartamento"), rs.getString("NomeDepartamento"), rs.getInt("NumProxectos")));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los departamentos con mas proyectos controlados: " + e.getMessage(), e);
        }
        return lista;

    }

    // ACTIVIDAD 3: ACTUALIZACIONES

    public static int insertarFamiliar(Connection con, Familiar familiar) throws SQLException {
        // ejercicio1
        try {
//            con.setAutoCommit(false);
//            String sqlMax = """
//                    Select COALESCE(MAX(Numero),0)
//                    FROM FAMILIAR
//                    WHERE NSS_empregado = ?
//                    """;
//            int numero = 1;
//
//            try(ResultSet rs = GestorConexiones.ejecutarConsulta(con,sqlMax,familiar.getNssEmpleado())){
//                if (rs.next()) {
//                    numero = rs.getInt(1)+1;
//                }
//                familiar.setN
//            }catch (){
//
//            }

            String sql = """
                    INSERT INTO FAMILIAR
                    (Nss,NssEmpregado,Nombre,Apellido1,Apellido2,FechaNac,Parentesco,Sexo)
                    Values(?,?,?,?,?,?,?,?);
                    """;
            GestorConexiones.ejecutarSentencia(con, sql, familiar.getNss(), familiar.getNssEmpleado(), familiar.getNombre(), familiar.getApellido1(), familiar.getApellido2(), familiar.getFechaNacimiento(), familiar.getParentesco(), familiar.getSexo());
        } catch (SQLException e) {
            try {
                con.rollback();
                /*
                 * error de FK -> -1
                 * PK o unique -> -2
                 * check -> -3
                 * */
            } catch (SQLException ex) {
                String msg = ex.getMessage();
                if (msg.contains("FK")) {
                    return -1;
                } else if (msg.contains("PK|UQ")) {
                    return -2;
                } else if (msg.contains("CK")) {
                    return -3;
                }
                throw new RuntimeException(ex);
            }
        } finally {
            con.setAutoCommit(true);
            return 0;
        }
    }

    public static int insertarVehiculo(Connection con, Vehiculo vehiculo) {
        // ejercicio 2
        String sqlVehiculoSQL = """
                        INSERT INTO VEHICULO(Matricula,Marca,Modelo,Tipo)
                        VALUES(?,?,?,?)
                """;
        String sqlVehiculoPropiosSQL = """
                        INSERT INTO VEHICULO_PROPIOS(CodVehiculo,FechaCompra,Precio)
                        VALUES(?,?,?)
                """;
        String sqlVehiculoRentingSQL = """
                INSERT INTO VEHICULO_RENTINGS(CodVehiculo,FechaIni,PrecioMensual,MesesContratados)
                VALUES(?,?,?,?)
                """;
        try {
            con.setAutoCommit(false);
            int codVehiculo = GestorConexiones.insertarYRetornarClaveGenerada(con, sqlVehiculoSQL,
                    vehiculo.getMatricula(),
                    vehiculo.getMarca(),
                    vehiculo.getModelo(),
                    String.valueOf(vehiculo.getTipo()));

            if (vehiculo instanceof VehiculoPropio v) {
                GestorConexiones.ejecutarSentencia(con, sqlVehiculoPropiosSQL,
                        v.getCodigo(),
                        v.getFechaCompra(),
                        v.getPrecio());
            } else if (vehiculo instanceof VehiculoRenting v) {
                GestorConexiones.ejecutarSentencia(con, sqlVehiculoRentingSQL,
                        v.getCodigo(), java.sql.Date.valueOf(v.getFechaCompra()), v.getPrecioMensual(), v.getMesesContratados());
            }

        } catch (SQLException e) {
            con.rollback();
            throw new RuntimeException(e);
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return 0;
        }
    }

    public static int cambiarDepartamentoProyecto(Connection con, String nombreDepartamento, String nombreProyecto) {
        // ejercicio 3
        try {
            String sql = """
                    UPDATE PROXECTO
                    SET NumDepartamentoControla = (Select NumDepartamento FROM DEPARTAMENTO WHERE NomeDepartamento = ?)
                    WHERE NomeProxecto = ?
                    """;
            int filas = GestorConexiones.ejecutarSentencia(con, sql, nombreDepartamento, nombreProyecto);
            if (filas == 0) {
                return -2;
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ProyectoConEmpleadosDTO obtenerProyectoConEmpleados(Connection con, int numProyecto) {
        // ejercicio 4
        // TODO HACER CON PROCEDIMIENTO
        String sql = """
                Select NumProxecto,NomeProxecto,Lugar,NumDepartControla
                From PROXECTO
                WHERE NumProxecto = ?
                """;
        String sql2 = """
                Select e.NSS,CONCAT(e.Nome,' ',e.Apelido1,' ',COALESCE(e.Apelido2,'')) as NombreCompleto
                FROM EMPREGRADO e
                Join EMPREGADO_PROXECTO ep on e.NSS = ep.NSSEmpregado
                where ep.NumProxecto = ?
                """;
        ProyectoConEmpleadosDTO dto = null;
        return null;
    }

    public static boolean borrarProyecto(Connection con, int numProyecto) {
        // ejercicio 4
        String slqBorrarAsignaciones = "DELETE FROM EMPREGADO_PROXECTO WHERE NumProyecto = ?";
        String slqBorrarProyecto = "DELETE FROM PROXECTO WHERE NumProyecto = ?";
        try {
            con.setAutoCommit(false);
            GestorConexiones.ejecutarSentencia(con, slqBorrarAsignaciones, numProyecto);
            int filas = GestorConexiones.ejecutarSentencia(con, slqBorrarProyecto, numProyecto);
            con.commit();
            return filas == 1;
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public static int incrementarSalarioEmpleadosFijos(Connection con, double incremento, List<String> listaNss) {
        // ejercicio 5
        String sql = "UPDATE EMPREGADOFIXO SET salario = salario + ? WHERE NSS = ?";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setDouble(1, incremento);
            for (String nss : listaNss) {
                ps.setString(2, nss);
                ps.addBatch(nss);
            }
            int[] resultados = ps.executeBatch();
            con.commit();
            int filasCambiadas = 0;
            for (int i = 0; i < resultados.length; i++) {
                filasCambiadas = resultados[i];
            }
            return filasCambiadas;
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }

    public static boolean insertarProyecto(Connection con, Proxecto proyecto) {
        // ejercicio 6
        String sql = "Select * from PROXECTO";
        try (ResultSet rs = GestorConexiones.crearResultSetActualizable(con, sql)) {
            rs.moveToInsertRow();
            rs.updateInt("NumProxecto", obtenerUltimoNumProyecto(con) + 1);
            rs.updateString("NomeProxecto", proyecto.getNombre());
            rs.updateString("Lugar", proyecto.getLugar());
            rs.updateInt("NumDepartControla", proyecto.getNumDepartamento());

            rs.insertRow();
            rs.moveToCurrentRow();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static int obtenerUltimoNumProyecto(Connection con) {
        String sql = "select MAX(NumProxecto) as max FROM PROXECTO";
        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql)) {
            return rs.getInt("max");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int incrementarSalario(Connection con, double incremento, int numDepartamento) {
        // ejercicio 7
        String sql = """
                SELECT EF.NSS, EF.SALARIO
                FROM EMPREGADOFIXO EF INNER JOIN EMPREGADO E
                ON E.NSS = EF.NSS
                WHERE E.NumDepartamentoPertenece = ?
                """;
        int afectados = 0;
        try {
            con.setAutoCommit(false);
            try (ResultSet rs = GestorConexiones.crearResultSetActualizable(con, sql, numDepartamento)) {
                while (rs.next()) {
                    double salarioActual = rs.getDouble("salario");
                    rs.updateDouble("salario", salarioActual + incremento);
                    rs.updateRow();
                    afectados++;
                }
            }
            con.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return afectados;
        }
    }

    public List<EmpleadoInfoDTO> obtenerEmpleadosConMasProyectos(Connection con, int valor) {
        String sql = """
                SELECT E.NSS,
                       CONCAT(E.Nome, ' ', E.Apelido1, ' ', COALESCE(E.Apelido2, '')) AS NombreCompleto,
                       E.Localidade,
                       EF.Salario
                FROM EMPREGADO E
                INNER JOIN EMPREGADOFIXO EF ON E.NSS = EF.NSS
                WHERE (SELECT COUNT(*)
                       FROM EMPREGADO_PROXECTO EP
                       WHERE EP.NSSEmpregado = E.NSS) > ?
                """;

        List<EmpleadoInfoDTO> lista = new ArrayList<>();

        try (PreparedStatement stmt = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {

            stmt.setInt(1, valor);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    return lista;
                }

                if (rs.first()) {
                    imprimirFila(rs);
                }

                if (rs.last()) {
                    imprimirFila(rs);
                    int totalFilas = rs.getRow();

                    if (totalFilas >= 3) {
                        if (rs.absolute(totalFilas - 2)) {
                            imprimirFila(rs);
                        }
                    }
                }

                rs.afterLast();
                while (rs.previous()) {
                    imprimirFila(rs);
                    lista.add(new EmpleadoInfoDTO(
                            rs.getString("NSS"),
                            rs.getString("NombreCompleto"),
                            rs.getString("Localidade"),
                            rs.getDouble("Salario")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    private void imprimirFila(ResultSet rs) throws SQLException {
        System.out.printf("NSS: %s | Nome: %s | Localidade: %s | Salario: %.2f%n",
                rs.getString("NSS"),
                rs.getString("NombreCompleto"),
                rs.getString("Localidade"),
                rs.getDouble("Salario"));
    }

}
