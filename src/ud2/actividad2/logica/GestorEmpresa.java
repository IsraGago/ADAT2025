package ud2.actividad2.logica;

import ud2.actividad2.clases.Departamento;
import ud2.actividad2.persistencia.EmpresaDAO;
import ud2.actividad2.utilidades.GestorConexiones;
import ud2.actividad2.utilidades.TipoSGBD;

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
            ArrayList<Departamento> departamentos = EmpresaDAO.getDepartamentos(con);
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
            if (EmpresaDAO.insertarDepartamento(con,nombre,nssDirector)){
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
            EmpresaDAO.crearTablas(con,borrarSiExsisten);
            System.out.println("Tablas creadas con exito.");
        } catch (SQLException e) {
            System.out.println("Error al crear tablas: "+e.getMessage());
        }
    }
}
