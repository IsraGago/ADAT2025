package ud2.examen;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import ud2.examen.clases.Artistica;
import ud2.examen.clases.Fotografia;
import ud2.examen.logica.GestorExposicion;
import ud2.examen.utilidades.TipoSGBD;
import ud2.examen.clases.Documental;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * @author ISRAEL BEJAMÍN GAGO ACUÑA 54321342b
 */
public class Main {
    public static void main(String[] args) {
        String baseDatos = "BDEXPOSICION26";
        String usuario = "sa";
        String password = "abc123.";

        try {
            GestorExposicion gestorExposicion = new GestorExposicion(TipoSGBD.SQLSERVER, baseDatos, usuario, password);
            System.out.println("\n1.- CREACIÓN DE TABLAS EXTRA \n");
            gestorExposicion.crearTablas();
            System.out.println("\n2.- INSERCIÓN DE FOTOGRAFIAS \n");
            System.out.println("2.1.- CASO DE ÉXITO MEZCLANDO FOTOGRAFIAS");
            List<Fotografia> fotosMezcladas = List.of(
                    new Documental("documental1","30x60", LocalDate.now(),'N',"tipo1"),
                    new Documental("documental2","500x287", LocalDate.now(),'N',"tipo2"),
                    new Artistica("artistica","530x180", LocalDate.now(),'N',"tipo1","composicion1")
            );
            gestorExposicion.insertarFotografia("PERICO VALDÉS","LA FOSA",fotosMezcladas);

            System.out.println("\n2.2.- CASO DE ERROR POR INEXISTENCIA DE LA EXPOSICIÓN");
            gestorExposicion.insertarFotografia("PERICO VALDÉS","exposicion1",fotosMezcladas);

            System.out.println("\n2.3.- CASO DE ERROR POR INEXISTENCIA DEL FOTOGRAFO");
            gestorExposicion.insertarFotografia("israel gago","exposicion1",fotosMezcladas);

            System.out.println("\n2.3.- CASO DE ERROR (Y ROLLBACK) POR PARAMETROS INVÁLIDOS EN LAS FOTOGRAFÍAS\n");
            List<Fotografia> fotosConUnaInvalida = List.of(
                    new Documental("documental1","30x60", LocalDate.now(),'N',"tipo1"),
                    new Documental("documental2","500x287", LocalDate.now(),'N',"tipo2"),
                    new Artistica("artistica","530x180", LocalDate.now(),'Ñ',"tipo1","composicion1")
            );
            gestorExposicion.insertarFotografia("PERICO VALDÉS","LA FOSA",fotosConUnaInvalida);

            System.out.println("\n3.- TRANSLADAR Y DAR DE BAJA \n");
            gestorExposicion.transladarYDarDeBaja("PLEASE SMILE","INVISIBLE");

            gestorExposicion.cerrar();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
