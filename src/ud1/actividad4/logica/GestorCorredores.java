package ud1.actividad4.logica;

import ud1.actividad4.clases.Corredor;
import ud1.actividad4.persistencia.CorredorXML;
import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4.persistencia.TipoValidacion;
import ud1.actividad4.servicio.Utilidades;

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
    public void mostrarCorredores(){
        if(this.documentoXML == null){
            System.out.println("No se ha cargado ningún documento XML.");
            return;
        }
        gestor.cargarCorredores(this.documentoXML).forEach(System.out::println);
    }
    public Corredor getCorredor(String codCorredor){
        if (!Utilidades.esCodigoValido(codCorredor)) {
            return null;
        }
        return gestor.getCorredor(codCorredor, documentoXML);
    }

    public Corredor getCorredor(int dorsal){
        if (dorsal < 0) {
            return null;
        }
        return gestor.getCorredor(dorsal, documentoXML);
    }
}
