package ud2.actividad4.logica;

import ud2.actividad4.clases.Departamento;
import ud2.actividad4.dto.ProxectoInfoDTO;
import ud2.actividad4.persistencia.EmpresaDAO;
import ud2.actividad4.utilidades.GestorConexiones;
import ud2.actividad4.utilidades.TipoSGBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestorEmpresa {
    Connection con = null;

    public GestorEmpresa(TipoSGBD tipo, String baseDatos, String usuario, String password) throws SQLException {
        con = GestorConexiones.getConnection(tipo, baseDatos, usuario, password);
    }

    public GestorEmpresa(TipoSGBD tipo, String rutaSQLite) throws SQLException {
        con = GestorConexiones.getConnection(tipo, rutaSQLite);
    }


    public void mostrarMetadatos() {
        try {
            System.out.println(GestorConexiones.obtenerMetadatos(con));
        } catch (SQLException e) {
            System.out.println("Error al obtener los metadatos");
        }
    }

    public void cambiarDomicilio(String nss, String rua, int numero, String piso, String cp, String localidade){
        int numFilas = EmpresaDAO.cambiarDomicilio(con,nss,rua,numero,piso,cp,localidade);
        if (numFilas > 0){
            System.out.println("Domicilio cambiado correctamente.");
        } else {
            System.out.println("No se ha podido cambiar el domicilio.");
        }
    }

    public void obtenerDatosProyecto(int numProyecto){
        ProxectoInfoDTO p = EmpresaDAO.obtenerDatosProyecto(con,numProyecto);
        if (p != null){
            System.out.println(p);
        } else {
            System.out.println("No se han encontrado datos para el proyecto " + numProyecto);
        }
    }

    public void departamentosQueControlan(int numProyectos){
        List<Departamento> lista = EmpresaDAO.departamentosQueControlan(con,numProyectos);
        if (lista.isEmpty()){
            System.out.println("No se han encontrado departamentos que controlen más de " + numProyectos + " proyectos.");
            return;
        }
        for (Departamento d : lista){
            System.out.println(d);
        }
    }

    public void numeroEmpregadoDepartamento(String nombreDepartamento){
        int numEmpleados = EmpresaDAO.numeroEmpregadoDepartamento(con,nombreDepartamento);
        if (numEmpleados >= 0){
            System.out.println("El departamento " + nombreDepartamento + " tiene " + numEmpleados + " empleados.");
        } else {
            System.out.println("comprueba la existencia del departamento " + nombreDepartamento);
        }
    }

    public void getTipoEmpelado(String nss){
        String tipo = EmpresaDAO.getTipoEmpelado(con,nss);
        if (tipo != null){
            System.out.println("El empleado con NSS " + nss + " es de tipo: " + tipo);
        } else {
            System.out.println("No se ha encontrado un empleado con NSS " + nss);
        }
    }




}
