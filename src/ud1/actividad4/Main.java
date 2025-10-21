package ud1.actividad4;

import ud1.actividad4.clases.Corredor;
import ud1.actividad4.logica.GestorCorredores;
import ud1.actividad4.persistencia.TipoValidacion;

public class Main {
    public static void main(String[] args) {
        final String RUTA_XML_CORREDORES = "./src/ud1/actividad4/archivos/corredores.xml";
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
    }
}
