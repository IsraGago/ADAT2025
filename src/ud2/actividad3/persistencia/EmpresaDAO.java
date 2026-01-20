package ud2.actividad3.persistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    // ACTIVIDAD 3: ACTUALIZACIONES

    public static int insertarFamiliar(Connection con, Familiar familiar) throws SQLException {
        // ejercicio1
        try {
            String sql = """
                    INSERT INTO FAMILIAR
                    (Nss,NssEmpregado,Nombre,Apellido1,Apellido2,FechaNac,Parentesco,Sexo)
                    Values(?,?,?,?,?,?,?,?);
                    """;
            GestorConexiones.ejecutarSentencia(con, sql, familiar.getNss(), familiar.getNssEmpleado(), familiar.getNombre(), familiar.getApellido1(), familiar.getApellido2(), java.sql.Date.valueOf(familiar.getFechaNacimiento()), familiar.getParentesco(), String.valueOf(familiar.getSexo()));
            return 0;
        } catch (SQLException e) {
            // e.printStackTrace();
            return getCodError(e.getMessage());
        }
    }

    public static int insertarVehiculo(Connection con, Vehiculo vehiculo) {
        // ejercicio 2
        String sqlVehiculoSQL = """
                        INSERT INTO VEHICULO(Matricula,Marca,Modelo,Tipo)
                        VALUES(?,?,?,?)
                """;
        String sqlVehiculoPropiosSQL = """
                        INSERT INTO VEHICULO_PROPIO(CodVehiculo,FechaCompra,Precio)
                        VALUES(?,?,?)
                """;
        String sqlVehiculoRentingSQL = """
                INSERT INTO VEHICULO_RENTING(CodVehiculo,FechaIni,PrecioMensual,MesesContratados)
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
                        codVehiculo,
                        v.getFechaCompra(),
                        v.getPrecio());
            } else if (vehiculo instanceof VehiculoRenting v) {
                GestorConexiones.ejecutarSentencia(con, sqlVehiculoRentingSQL,
                        codVehiculo, Date.valueOf(v.getFechaCompra()), v.getPrecioMensual(), v.getMesesContratados());
            }
            con.commit();
            return 0;

        } catch (Exception e) {
            try {
                con.rollback();
                return getCodError(e.getMessage());
            } catch (SQLException ex) {
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return -5;
    }


    public static int cambiarDepartamentoProyecto(Connection con, String nombreDepartamento, String nombreProyecto) {
        // ejercicio 3
        try {
            String sql = """
                    UPDATE PROXECTO
                    SET NumDepartControla = (Select NumDepartamento FROM DEPARTAMENTO WHERE NomeDepartamento = ?)
                    WHERE NomeProxecto = ?
                    """;
            int filas = GestorConexiones.ejecutarSentencia(con, sql, nombreDepartamento, nombreProyecto);
            if (filas == 0) {
                return -2;
            }
            return 0;
        } catch (SQLException e) {
            return -1; // no existe el proyecto o departamento
        }
    }

    public static ProyectoConEmpleadosDTO obtenerProyectoConEmpleados(Connection con, int numProyecto) {
        // ejercicio 4
        String sql = """
                Select NumProxecto,NomeProxecto,Lugar,NumDepartControla
                From PROXECTO
                WHERE NumProxecto = ?
                """;
        String sql2 = """
                Select e.NSS,CONCAT(e.Nome,' ',e.Apelido1,' ',COALESCE(e.Apelido2,'')) as NombreCompleto
                FROM EMPREGADO e
                Join EMPREGADO_PROXECTO ep on e.NSS = ep.NSSEmpregado
                where ep.NumProxecto = ?
                """;
        ProyectoConEmpleadosDTO dto = null;

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql, numProyecto)) {
            if (rs.next()) {
                int numero = rs.getInt("NumProxecto");
                String nombre = rs.getString("NomeProxecto");
                String lugar = rs.getString("Lugar");
                int numDepartamento = rs.getInt("NumDepartControla");
                dto = new ProyectoConEmpleadosDTO(numero, nombre, lugar, numDepartamento);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql2, numProyecto)) {
            while (rs.next()) {
                String nss = rs.getString("NSS");
                String nombreCompleto = rs.getString("NombreCompleto");
                dto.empleados.add(new EmpleadoProyectoDTO(nss, nombreCompleto));
            }
            return dto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean borrarProyecto(Connection con, int numProyecto) {
        // ejercicio 4
        String slqBorrarAsignaciones = "DELETE FROM EMPREGADO_PROXECTO WHERE NumProxecto = ?";
        String slqBorrarProyecto = "DELETE FROM PROXECTO WHERE NumProxecto = ?";
        try {
            if (!existeProyecto(con, numProyecto)) {
                return false;
            }

            ProyectoConEmpleadosDTO proyecto = obtenerProyectoConEmpleados(con, numProyecto);
            System.out.println(proyecto);
            System.out.println("empelados");
            for (EmpleadoProyectoDTO e : proyecto.empleados) {
                System.out.println(e);
            }

            con.setAutoCommit(false);
            GestorConexiones.ejecutarSentencia(con, slqBorrarAsignaciones, numProyecto);
            int filas = GestorConexiones.ejecutarSentencia(con, slqBorrarProyecto, numProyecto);
            con.commit();
            return filas == 1;
        } catch (SQLException e) {
            try {
                e.printStackTrace();
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

    private static boolean existeProyecto(Connection con, int numProyecto) {

        String sql = "select count(NumProxecto) from PROXECTO where NumProxecto = ?";
        try {
            ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql, numProyecto);
            if (rs.next()) {
                return rs.getInt(1) > 0;
            } else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                ps.addBatch();
            }
            int[] resultados = ps.executeBatch();
            con.commit();
            int filasCambiadas = 0;
            for (int resultado : resultados) {
                filasCambiadas += resultado;
            }
            return filasCambiadas;
        } catch (SQLException e) {
            try {
                e.printStackTrace();
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
        // ejercicio6
        int codUltimoProyecto = obtenerUltimoNumProyecto(con);

        String sql = "Select * from PROXECTO ";

        try (ResultSet rs = GestorConexiones.crearResultSetActualizable(con, sql)) {
            rs.moveToInsertRow();

            rs.updateInt("NumProxecto", codUltimoProyecto + 1);
            rs.updateString("NomeProxecto", proyecto.getNombre());
            rs.updateString("Lugar", proyecto.getLugar());
            rs.updateInt("NumDepartControla", proyecto.getNumDepartamento());

            rs.insertRow();

            if (!con.getAutoCommit()) {
                con.commit();
            }

            return true;
        } catch (SQLException e) {
            try {
                if (!con.getAutoCommit()) con.rollback();
            } catch (SQLException ex) {
            }
            throw new RuntimeException(e);
        }
    }

    private static int obtenerUltimoNumProyecto(Connection con) {
        String sql = "select MAX(NumProxecto) as max FROM PROXECTO";
        try (ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql)) {
            if (rs.next()) {
                return rs.getInt("max");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int incrementarSalarioDepartamento(Connection con, double incremento, int numDepartamento) {
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
            return afectados;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static List<EmpleadoInfoDTO> obtenerEmpleadosConMasProyectos(Connection con, int valor) {
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

    private static void imprimirFila(ResultSet rs) throws SQLException {
        System.out.printf("NSS: %s | Nome: %s | Localidade: %s | Salario: %.2f%n",
                rs.getString("NSS"),
                rs.getString("NombreCompleto"),
                rs.getString("Localidade"),
                rs.getDouble("Salario"));
    }


    private static int getCodError(String msg) {
        if (msg.contains("FOREIGN")) {
            return -1;
        } else if (msg.contains("UNIQUE")) {
            return -2;
        } else if (msg.contains("CHECK")) {
            return -3;
        } else if (msg.contains("PRIMARY")) {
            return -4;
        }
        return -5;
    }

}
