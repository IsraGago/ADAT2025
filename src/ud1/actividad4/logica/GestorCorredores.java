package ud1.actividad4.logica;

import ud1.actividad4.clases.Puntuacion;
import ud1.actividad4.clases.Corredor;
import ud1.actividad4.persistencia.CorredorXML;
import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4.persistencia.TipoValidacion;
import ud1.actividad4.servicio.Utilidades;

import org.w3c.dom.Document;

public class GestorCorredores {
    private Document documentoXML;
    private final CorredorXML corredorXML = new CorredorXML();

    public void cargarDocumento(String rutaXML, TipoValidacion validacion) {
        try {
            this.documentoXML = corredorXML.cargarDocumentoXML(rutaXML, validacion);
            System.out.println("Documento XML cargado correctamente");
        } catch (ExcepcionXML e) {
            System.out.println("Error al cargar el documento XML: " + e.getMessage());
        }
    }


    public void mostrarCorredores() {
        if (this.documentoXML == null) {
            System.out.println("No se ha cargado ningún documento XML.");
            return;
        }
        corredorXML.cargarCorredores().forEach(System.out::println);
    }

    public Corredor getCorredor(String codCorredor) {
        if (!Utilidades.esCodigoValido(codCorredor)) {
            return null;
        }
        return corredorXML.getCorredor(codCorredor);
    }

    public Corredor getCorredor(int dorsal) {
        if (dorsal < 0) {
            return null;
        }
        return corredorXML.getCorredor(dorsal);
    }

    public void addCorredor(Corredor corredor){
        corredorXML.insertarCorredor(corredor);
    }

    public void eliminarCorredor(String codigo){
        corredorXML.eliminarCorredorPorCodigo(codigo);
    }

    public void addPuntuacion(String codigoCorredor,Puntuacion puntuacion){
        corredorXML.addPuntuacion(codigoCorredor,puntuacion);
    }
}
