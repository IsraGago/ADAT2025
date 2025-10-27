package ud1.actividad4.logica;

import ud1.actividad4.clases.Puntuacion;
import ud1.actividad4.clases.Corredor;
import ud1.actividad4.persistencia.CorredorXML;
import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4.persistencia.TipoValidacion;
import ud1.actividad4.persistencia.XMLDOMUtils;
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

    public void addCorredor(Corredor corredor) {
        if (corredor != null) {
            corredorXML.insertarCorredor(corredor);
        }
    }

    public void eliminarCorredor(String codigo) {
        if (!Utilidades.esCodigoValido(codigo)) {
            throw new IllegalArgumentException("ERROR: El código no tiene un formato correcto");
        }
        corredorXML.eliminarCorredorPorCodigo(codigo);
    }

    public void addPuntuacion(String codigoCorredor, Puntuacion puntuacion) {
        if (!Utilidades.esCodigoValido(codigoCorredor)) {
            throw new IllegalArgumentException("ERROR: El código no tiene un formato correcto");
        }
        if (puntuacion == null) {
            throw new IllegalArgumentException("ERROR: La puntuación no puede ser nula");
        }
        corredorXML.addPuntuacion(codigoCorredor, puntuacion);
    }

    public void guardarFicheroXML(String rutaFichero) {
        if (rutaFichero == null || rutaFichero.isEmpty()) {
            throw new IllegalArgumentException("ERROR: La ruta no puede estar vacío");
        }
        XMLDOMUtils.guardarDocumento(documentoXML, rutaFichero);
    }

    public int getUltimoDorsal() {
        return corredorXML.getUltimoDorsal();
    }

    public String getUltimoCodigo(){
        return corredorXML.getUltimoCodigo();
    }
}
