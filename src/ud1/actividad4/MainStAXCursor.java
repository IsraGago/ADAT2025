package ud1.actividad4;

import ud1.actividad4.logica.GestorCorredores;
import ud1.actividad4.persistencia.TipoValidacion;

public class MainStAXCursor {
    static final String RUTA_XML_CORREDORES = "./src/ud1/actividad4/archivos/corredores.xml";
    public static void main(String[] args) {
        GestorCorredores gestorCorredores = new GestorCorredores();
        gestorCorredores.mostrarCorredoresStaxCursor(RUTA_XML_CORREDORES,TipoValidacion.NO_VALIDAR);
    }
}
