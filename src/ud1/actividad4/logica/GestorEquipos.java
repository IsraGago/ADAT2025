package ud1.actividad4.logica;

import org.w3c.dom.Document;

import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4.persistencia.TipoValidacion;
import ud1.actividad4.persistencia.persistenciadom.EquiposXML;

public class GestorEquipos {
    private Document documentoXML;
    private final EquiposXML gestor = new EquiposXML();
    private String rutaXML;
    TipoValidacion tipoValidacion = null;

    public void cargarDocumento(String rutaXML, TipoValidacion tipoValidacion) {
        try {
            this.documentoXML = gestor.cargarDocumentoXML(rutaXML, tipoValidacion);
            System.out.println("Documento XML cargado correctamente: "+rutaXML);
            this.rutaXML = rutaXML;
            this.tipoValidacion = tipoValidacion;
        } catch (ExcepcionXML e) {
            System.out.println("Error al cargar el documento XML: " + e.getMessage());
        }
    }

    public String getNombreEquipo(String id) {
        if (this.documentoXML == null) {
            System.out.println("No se ha cargado ningún documento XML.");
            return null;
        }
        return gestor.getNombreEquipo(id);
    }

    public String getIdEquipo(String nombre){
        if (this.documentoXML == null) {
            System.out.println("No se ha cargado ningún documento XML.");
            return null;
        }
        return gestor.getIdEquipo(nombre);
    }
}
