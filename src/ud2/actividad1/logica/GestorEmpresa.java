package ud2.actividad1.logica;

import ud2.actividad1.clases.Departamento;
import ud2.actividad1.utilidades.GestorConexiones;
import ud2.actividad1.utilidades.TipoSGBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class GestorEmpresa {
    Connection con = null;

    public GestorEmpresa(TipoSGBD tipo, String baseDatos, String usuario, String password) throws SQLException {
        con = GestorConexiones.getConnection(tipo,baseDatos,usuario,password);
    }

    public void mostrarDepartamentos(){
        try{
            ArrayList<Departamento> departamentos = GestorConexiones.getDepartamentos(con);
            for (Departamento departamento : departamentos){
                departamento.mostrarDatos();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener los departamentos");
        }
    }

    public void mostrarMetadatos() {
        try {
            System.out.println(GestorConexiones.obtenerMetadatos(con));
        } catch (SQLException e) {
            System.out.println("Error al obtener los metadatos");
        }
    }

    public void insertarDepartamento(String nombre, String nssDirector){
        try {
            if (GestorConexiones.insertarDepartamento(con,nombre,nssDirector)){
               System.out.println("Departamento insertado");
            } else{
                System.out.println("Error al insertar departamento");
            }

        } catch (SQLException e) {
            System.out.println("Error al crear el departamento: "+e.getMessage());
        }
    }

    public void crearTablasExtra(boolean borrarSiExsisten){
        try {
            GestorConexiones.crearTablas(con,borrarSiExsisten);
            System.out.println("Tablas creadas con exito.");
        } catch (SQLException e) {
            System.out.println("Error al crear tablas: "+e.getMessage());
        }
    }
}
