package ud1.actividad4;

import java.time.LocalDate;

import ud1.actividad4.clases.Corredor;
import ud1.actividad4.clases.Puntuacion;
import ud1.actividad4.clases.Velocista;
import ud1.actividad4.logica.GestorCorredores;
import ud1.actividad4.persistencia.TipoValidacion;

public class Main {
    public static void main(String[] args) {
        final String RUTA_XML_CORREDORES = "./src/ud1/actividad4/archivos/corredores.xml";
        final String RUTA_SALIDA_CORREDORES = "./src/ud1/actividad4/archivos/corredores-copia.xml";
        GestorCorredores gestor = new GestorCorredores();
        gestor.cargarDocumento(RUTA_XML_CORREDORES,TipoValidacion.DTD);
        System.out.println("------------------\n LISTAR CORREDORES");
        gestor.mostrarCorredores();

        System.out.println("------------------\nMOSTRAR CORREDOR SEGÚN CÓDIGO");
        Corredor corredorBuscarCodigo = gestor.getCorredor("C01");
        corredorBuscarCodigo.mostrarInformacion();

        System.out.println("------------------\nMOSTRAR CORREDOR SEGÚN DORSAL");
        Corredor corredorBuscarDorsal = gestor.getCorredor(2);
        corredorBuscarDorsal.mostrarInformacion();

        gestor.addCorredor(new Velocista( "Israel Gago", LocalDate.now(), "E1",5F));
        gestor.addPuntuacion(gestor.getUltimoCodigo(), new Puntuacion(2020, 10));
        gestor.addPuntuacion(gestor.getUltimoCodigo(), new Puntuacion(2022, 9));
        gestor.addPuntuacion(gestor.getUltimoCodigo(), new Puntuacion(2020, 5));
        gestor.getCorredor(gestor.getUltimoDorsal()).mostrarInformacion();
        gestor.guardarFicheroXML(RUTA_SALIDA_CORREDORES);

        
    }
}
