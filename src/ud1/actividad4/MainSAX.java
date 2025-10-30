package ud1.actividad4;

import ud1.actividad4.logica.GestorCorredores;
import ud1.actividad4.persistencia.TipoValidacion;

public class MainSAX {
    public static void main(String[] args) {
        final String RUTA_XML_CORREDORES = "./src/ud1/actividad4/archivos/corredores.xml";
        GestorCorredores gestor = new GestorCorredores();
        gestor.cargarDocumento(RUTA_XML_CORREDORES, TipoValidacion.DTD);
        gestor.mostrarCorredoresSax();
        
    }
}
