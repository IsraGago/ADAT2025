package ud2.examen.persistencia;

import ud2.examen.clases.Artistica;
import ud2.examen.clases.Documental;
import ud2.examen.clases.Fotografia;
import ud2.examen.utilidades.GestorConexiones;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ISRAEL BEJAMÍN GAGO ACUÑA 54321342b
 */

public class ExposicionDAO {
    // CREA LAS TABLAS LABORATORIO Y FOTOGRAFO_LABORATORIO. RETORNA UN BOOLEANO EN FUNCION DE SI TODO HA IDO BIEN
    public static boolean crearTablas(Connection con) {
        String sqlBorrarFotografoLaboratorio = "DROP TABLE IF EXISTS FOTOGRAFO_LABORATORIO";
        String sqlFotografoLaboratorio = """
                CREATE TABLE FOTOGRAFO_LABORATORIO(
                    codFotografoLaboratorio int IDENTITY (1,1),
                    codFotografo int not null,
                    codLaboratorio int not null,
                    fechaInicio DATE not null,
                    fechaFin DATE,
                    CONSTRAINT FK_FOTOGRAFO_LABORATORIO FOREIGN KEY (codFotografo) references FOTOGRAFO(CODIGO),
                    CONSTRAINT FK_LABORATORIO_FOTOGRAFO FOREIGN KEY (codLaboratorio) references Laboratorio(codLaboratorio),
                    CONSTRAINT PK_FOTOGRAFO_LABORATORIO PRIMARY KEY (codFotografoLaboratorio)
                
                )
                """;
        String sqlBorrarLaboratorio = "DROP TABLE IF EXISTS Laboratorio";
        String sqlLaboratorio = """
                DROP TABLE IF EXISTS Laboratorio;
                CREATE TABLE Laboratorio(
                    codLaboratorio int IDENTITY (1,1),
                    nombre varchar(50) not null,
                    anoInauguracion int not null,
                    CONSTRAINT PK_Laboratorio PRIMARY KEY (codLaboratorio),
                    CONSTRAINT UQ_NOMBRE_LABORATORIO UNIQUE(nombre)
                )
                """;

        try {
            GestorConexiones.ejecutarLoteTransaccional(con, sqlBorrarFotografoLaboratorio, sqlBorrarLaboratorio, sqlLaboratorio, sqlFotografoLaboratorio);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    // 1. INSERTA LAS FOTOGRAFÍAS DE UN FOTOGRAFO EN UNA EXPOSICIÓN.
    // 2. RETORNA EL NÚMERO DE FILAS INSERTADAS EN LA TABLA FOTOGRAFÍA. O UN CODIGO DE ERROR
    public static int insertarFotografias(Connection con, String nombreFotografo, String nombreExposicion, List<Fotografia> fotos) {
        String sqlFotografia = """
                INSERT INTO FOTOGRAFIA(NOME,MEDIDAS,DATA,COD_FOTOGRAFO,COD_EXPOSICION,COLOR)
                VALUES (?,?,?,(SELECT CODIGO FROM FOTOGRAFO WHERE NOME = ?),(SELECT CODIGO FROM EXPOSICION WHERE NOME = ?),?)
                """;
        String sqlDocumental = """
                INSERT INTO DOCUMENTAL(CODIGO,TIPO)
                VALUES (?,?)
                """;
        String sqlArtistica = """
                INSERT INTO ARTISTICA(CODIGO,ENCUADRE,COMPOSICION)
                VALUES (?,?,?)
                """;
        String sqlActualizarNumFotos = """
                UPDATE FOTOGRAFO SET NUMFOTOGRAFIAS = ? WHERE CODIGO = (SELECT CODIGO FROM FOTOGRAFO WHERE NOME = ?)
                """;
        try {

            if (!existeFotografo(con, nombreFotografo)) {
                return -5;
            } else if (!existeExposicion(con, nombreExposicion)) {
                return -6;
            }

            int filas = 0;
            con.setAutoCommit(false);
            for (Fotografia foto : fotos) {

                int clave = GestorConexiones.insertarYRetornarClaveGenerada(
                        con,
                        sqlFotografia,
                        foto.getNombre(),
                        foto.getMedidas(),
                        java.sql.Date.valueOf(foto.getFecha()),
                        nombreFotografo,
                        nombreExposicion,
                        String.valueOf(foto.getColor())
                );
                filas++;

                if (foto instanceof Documental d) {
                    filas += GestorConexiones.ejecutarSentencia(con, sqlDocumental, clave, d.getTipo());
                } else if (foto instanceof Artistica a) {
                    filas += GestorConexiones.ejecutarSentencia(con, sqlArtistica, clave, a.getEncuadre(), a.getComposicion());
                }

            }
            String sqlFuncion = "{? = call fn_obtenerNumFotos(?)}";
            int numFotos;
            try (CallableStatement cs = con.prepareCall(sqlFuncion)) {
                cs.registerOutParameter(1, Types.INTEGER);
                cs.setString(2, nombreFotografo);
                cs.execute();
                numFotos = cs.getInt(1);
            } catch (SQLException e) {
                throw new RuntimeException("Error al ejecutar fn_obtenerNumFotos: " + e.getMessage(), e);
            }
            GestorConexiones.ejecutarSentencia(con, sqlActualizarNumFotos, numFotos + fotos.size(), nombreFotografo);
            con.commit();
            return filas;
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
            }
            return getCodError(e.getMessage());
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // RETORNA LA EXISTENCIA DE UN FOTOGRAFO DADO SU NOMBRE
    private static boolean existeFotografo(Connection con, String nombre) {

        String sql = "select count(CODIGO) from FOTOGRAFO where NOME = ?";
        try {
            ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql, nombre);
            if (rs.next()) {
                return rs.getInt(1) > 0;
            } else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // RETORNA LA EXISTENCIA DE UNA EXPOSICIÓN PASANDOLE EL NOMBRE COMO PARÁMETRO
    public static boolean existeExposicion(Connection con, String nombre) {

        String sql = "select count(CODIGO) from EXPOSICION where NOME = ?";
        try {
            ResultSet rs = GestorConexiones.ejecutarConsulta(con, sql, nombre);
            if (rs.next()) {
                return rs.getInt(1) > 0;
            } else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
        DEVUELVE UN CÓDIGO DE ERROR EN FUNCIÓN AL MENSAJE QUE GENERE UNA SQLException
        MIS RESTRICCIONES TIENEN UN FORMATO SEGÚN SU TIPO, POR TANTO, YA QUE EN EL MENSAJE
        DE LA EXCEPCIÓN APARECE DICHO NOMBRE, PUEDO SABER A QUE SE DEBE EL ERROR SIMPLEMENTE
        REVISANDO EL CONTENIDO DEL MENSAJE.
        1. ERROR DE FOREIGN KEY (FK) -> -1
        2. ERROR DE UNIQUE (UQ) -> -2
        3. ERROR DE CHEKC (CK) -> -3
        4. ERROR DE FOREING KEY (FK) -> -4
    */
    private static int getCodError(String msg) {
        if (msg.contains("FK")) {
            return -1;
        } else if (msg.contains("UQ")) {
            return -2;
        } else if (msg.contains("CK")) {
            return -3;
        } else if (msg.contains("PK")) {
            return -4;
        }
        return -5;
    }

    /*
    * 1. TRANSLADA LAS FOTOS DE UNA EXPOSICIÓN A OTRA
    * 2. BORRA LAS ENTRADAS EN EXPOSICIONCOMISARIO QUE TENGAN EL CÓDIGO CORRESPONDIENTE AL NOMBRE DE LA EXPOSICIÓN DADA
    * PARA QUE NO HAYA PROBLEMAS DE CLAVES FORÁNEAS
    * 3. ELIMINA LA EXPOSICIÓN DE LA TABLA EXPOSICIÓN
    * 4. RETORNA EL NÚMERO DE FOTOS QUE HAN SIDO TRANSLADADAS O UN CÓDIGO DE ERROR
    * */
    public static int transladarYDarDeBaja(Connection con, String exposicionDarBaja, String exposicionFinal) {
        if (!existeExposicion(con, exposicionDarBaja) || !existeExposicion(con, exposicionFinal)) {
            return -5;
        }

        String sqlTransladar = """
                UPDATE FOTOGRAFIA SET COD_EXPOSICION = (SELECT CODIGO FROM EXPOSICION WHERE NOME = ?)
                WHERE COD_EXPOSICION = (SELECT CODIGO FROM EXPOSICION WHERE NOME = ?)
                """;
        String sqlDarBaja = """
                DELETE FROM EXPOSICION WHERE NOME = ?
                """;
        String sqlBorrarEposicionComisario = """
                DELETE FROM EXPOSICIONCOMISARIO WHERE CODEXPOSICION = (SELECT CODIGO FROM EXPOSICION WHERE NOME = ?)
                """;
        try {
            con.setAutoCommit(false);
            int numFotosTransladadas = GestorConexiones.ejecutarSentencia(con, sqlTransladar, exposicionFinal, exposicionDarBaja);
            GestorConexiones.ejecutarSentencia(con, sqlBorrarEposicionComisario, exposicionDarBaja);
            GestorConexiones.ejecutarSentencia(con, sqlDarBaja, exposicionDarBaja);
            con.commit();
            return numFotosTransladadas;
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            return getCodError(e.getMessage());
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    // DEVUELVE UNA LISTA DE STRINGS CON LAS FOTOS DE UNA EXPOSICIÓN CUYO NOMBRE SE PASA POR PARÁMETRO
    // FORMATO DE LOS STRINGS: TIPO_DE_FOTO NOMBRE_FOTO -NOMBRE_FOTÓGRAFO-
    // EJEMPLO: DOCUMENTAL LA MADRE -FABIO TASSO-
    public static List<String> obtenerFotosExposicion(Connection con, String nombreExposicion) {
        List<String> lista = new ArrayList<>();
        String sql = "{call sp_getNombreTipoFotografiasExposicion(?)}";
        try (CallableStatement cs = con.prepareCall(sql)) {
            cs.setString(1, nombreExposicion);
            boolean resultado = cs.execute();
            if (resultado) {
                try (ResultSet rs = cs.getResultSet()) {
                    while (rs.next()) {
                        lista.add(rs.getString(1));
                    }
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    * DEVUELVE LA LOCALIDAD Y LA PROVINCIA COMO ÚNICO STRING DE UNA EXPOSICIÓN CUYO NOMBRE SE PASA COMO PARÁMETRO
    * FORMATO SALIDA: LOCALIDAD(PROVINCIA)
    * EJEMPLO: VIGO(PONTEVEDRA)
    * */
    public static String getLocalidadYProvinciaExposicion(Connection con, String nombreExposicion) {
        String sql = "{call sp_getLocalidadYProvincia(?,?)}";
        try (CallableStatement cs = con.prepareCall(sql)) {
            cs.setString(1, nombreExposicion);
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.execute();
            return cs.getString(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
