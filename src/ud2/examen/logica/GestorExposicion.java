package ud2.examen.logica;

import ud2.examen.clases.Fotografia;
import ud2.examen.persistencia.ExposicionDAO;
import ud2.examen.utilidades.GestorConexiones;
import ud2.examen.utilidades.TipoSGBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ISRAEL BEJAMÍN GAGO ACUÑA 54321342b
 */

public class GestorExposicion {
    Connection con ;

    public GestorExposicion(TipoSGBD tipo, String baseDatos, String usuario, String password) throws SQLException {
        con = GestorConexiones.getConnection(tipo, baseDatos, usuario, password);
    }

    public void cerrar(){
        GestorConexiones.cerrarConexion(con);
    }

    public void crearTablas() {
        if (ExposicionDAO.crearTablas(con)) {
            System.out.println("Tablas creadas con éxito");
        } else {
            System.out.println("Error al crear las tablas");
        }
    }

    public void insertarFotografia(String nombreFotografo, String nombreExposicion, List<Fotografia> fotos) {
        int resultado = ExposicionDAO.insertarFotografias(con, nombreFotografo, nombreExposicion, fotos);
        switch (resultado) {
            case -1 -> {
                System.out.println("ERROR DE CLAVE FORANEA, NO SE HAN INSERTADO LAS FOTOGRAFIAS");
            }
            case -2 -> {
                System.out.println("ERROR DE RESTRICCIÓN UNIQUE");
            }
            case -3 -> {
                System.out.println("ERROR DE RESTRICCIÓN CHECK");
            }
            case -4 -> {
                System.out.println("ERROR DE CLAVE PRIMARIA");
            }
            case -5 -> {
                System.out.println("EL FOTOGRAFO NO EXISTE");
            }
            case -6 -> {
                System.out.println("LA EXPOSICIÓN NO EXISTE");
            }
            default -> {
                System.out.println("Fotografias insertadas");
            }
        }
    }

    public void transladarYDarDeBaja(String exposicionDarBaja, String exposicionFinal){
        if (!ExposicionDAO.existeExposicion(con,exposicionDarBaja) || !ExposicionDAO.existeExposicion(con,exposicionFinal)){
            System.out.println("ERORR: COMPRUEBA LA EXISTENCIA DE LAS EXPOSICIONES");
            return;
        }
        // MOSTRAR DATOS
        String provinciaYlocalidad = ExposicionDAO.getLocalidadYProvinciaExposicion(con,exposicionDarBaja);
        List<String> fotosATransladar = ExposicionDAO.obtenerFotosExposicion(con,exposicionDarBaja);
        System.out.println("NOMBRE EXPOSICIÓN: "+exposicionDarBaja+"    "+provinciaYlocalidad);
        System.out.println("FOTOGRAFÍAS: ");
        for(String foto : fotosATransladar){
            System.out.println("\t\t"+foto);
        }
        //DAR DE BAJA EXPOSICION Y TRANSLADAR FOTOS
        int resultado = ExposicionDAO.transladarYDarDeBaja(con,exposicionDarBaja,exposicionFinal);
        switch (resultado){
            case -1 -> {System.out.println("ERROR DE CLAVE FORÁNEA");}
            case -2 -> {System.out.println("ERROR DE RESTRICCIÓN UNIQUE");}
            case -3 -> {System.out.println("ERROR DE RESTRICCIÓN CHECK");}
            case -4 -> {System.out.println("ERROR DE CLAVE PRIMARIA");}
            case -5 -> {System.out.println("ERROR DE EXISTENCIA DE COMPOSICIONES");}
            default -> {System.out.println("SE TRANSLADAN "+resultado+
                    " FOTOGRAFÍAS DE LA EXPOSICIÓN "+exposicionDarBaja+
                    " A LA EXPOSICIÓN "+exposicionFinal);}

        }
    }
}
