package ud1.actividad4.logica;

import ud1.actividad4.persistencia.CorredorXML;
import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4.persistencia.TipoValidacion;
import org.w3c.dom.Document;

public class GestorCorredores {
    private final CorredorXML gestor = new CorredorXML();
    private Document documentoXML; 
    public void cargarDocumento(String rutaXML, TipoValidacion validacion){
        try {
            this.documentoXML = gestor.cargarDocumentoXML(rutaXML, validacion);
            System.out.println("Documento XML cargado correctamente");
        } catch (ExcepcionXML e) {
            System.out.println("Error al cargar el documento XML: "+e.getMessage());
        }
    }
}
