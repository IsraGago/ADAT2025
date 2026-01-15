package ud2.actividad2.logica;

import ud2.actividad2.clases.Departamento;
import ud2.actividad2.persistencia.EmpresaDAO;
import ud2.actividad2.utilidades.GestorConexiones;
import ud2.actividad2.utilidades.TipoSGBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestorEmpresa {
    Connection con = null;

    public GestorEmpresa(TipoSGBD tipo, String baseDatos, String usuario, String password) throws SQLException {
        con = GestorConexiones.getConnection(tipo,baseDatos,usuario,password);
    }

    public GestorEmpresa(TipoSGBD tipo, String rutaSQLite) throws SQLException {
        con = GestorConexiones.getConnection(tipo,rutaSQLite);
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

    // ACTIVIDAD2: Consultas
    public void mostrarDepartamentosConProyectos(){
        List<Departamento> lista = EmpresaDAO.obtenerDepartamentosConProyectos(con);
        for (Departamento departamento : lista){
            System.out.println(departamento);
        }
    }

    public void mostrarDirectoresConProyectos(){
        List lista = EmpresaDAO.obtenerDirectoresConProyectos(con);
        mostrar(lista);
    }

    public void mostrarEmpleadosConEdad(){
        List lista = EmpresaDAO.obtenerEmpleadosConEdad(con);
        mostrar(lista);
    }

    public void mostrarEmpleadoPorDepartamento(String departamento){
        List lista = EmpresaDAO.obtenerEmpleadoPorDepartamento(con, departamento);
        mostrar(lista);
    }

    public void mostrarEmpleadoFijoPorPorProyectoYLocalidad( String nombreProyecto,String localidad){
        List lista = EmpresaDAO.obtenerEmpleadoFijoPorPorProyectoYLocalidad(con, nombreProyecto, localidad);
        mostrar(lista);
    }

    public void mostrarNumEpleadosDepartamentosPorTipo(){
        List lista = EmpresaDAO.obtenerNumEpleadosDepartamentosPorTipo(con);
        mostrar(lista);
    }

    public void mostrarDepartamentosConMasDeN(int numEpleados){
        List lista = EmpresaDAO.obtenerDepartamentosConMasDeN(con, numEpleados);
        mostrar(lista);
    }

    public void mostrarFijosConSalarioMayorQue(double salario){
        List lista = EmpresaDAO.obtenerFijosConSalarioMayorQue(con,salario);
        mostrar(lista);
    }

    public void mostrarEmpleadosFijosMaxSalarioDepartamento(){
        List lista = EmpresaDAO.obtenerEmpleadosFijosMaxSalarioDepartamento(con);
        mostrar(lista);
    }

    public void mostrarDepartamentosMaxProyectosControlados(){
        List lista = EmpresaDAO.obtenerDepartamentosMaxProyectosControlados(con);
        mostrar(lista);
    }

    public void mostrar(List<Object> lista){
        for (Object object : lista){
            System.out.println(object);
        }
    }
}
