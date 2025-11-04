package ud1.actividad4.logica;

import ud1.actividad4.clases.Puntuacion;
import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4.persistencia.TipoValidacion;
import ud1.actividad4.persistencia.persistenciadom.CorredorXML;
import ud1.actividad4.persistencia.persistenciadom.XMLDOMUtils;
import ud1.actividad4.persistencia.persistenciasax.CorredorNombreEquipoHandler;
import ud1.actividad4.persistencia.persistenciasax.CorredorSAXHandler;
import ud1.actividad4.persistencia.persistenciasax.XMLSAXUtils;
import ud1.actividad4.clases.Corredor;
import ud1.actividad4.servicio.Utilidades;

import java.util.ArrayList;

import org.w3c.dom.Document;

public class GestorCorredores {
    private Document documentoXML;
    private final CorredorXML corredorXML = new CorredorXML();
    private String rutaXML = "";
    TipoValidacion tipoValidacion = null;

    public void cargarDocumento(String rutaXML, TipoValidacion tipoValidacion) {
        try {
            this.documentoXML = corredorXML.cargarDocumentoXML(rutaXML, tipoValidacion);
            System.out.println("Documento XML cargado correctamente: " + rutaXML);
            this.rutaXML = rutaXML;
            this.tipoValidacion = tipoValidacion;
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

    public String getUltimoCodigo() {
        return corredorXML.getUltimoCodigo();
    }

    public

    // SAX

    public ArrayList<Corredor> cargarCorredoresSAX() {
        CorredorSAXHandler handler = new CorredorSAXHandler();
        XMLSAXUtils.procesarDocumento(rutaXML, handler, tipoValidacion);
        return handler.getCorredores();
    }

    public ArrayList<Corredor> cargarCorredoresPorEquipoSAX(String nombreEquipo, String rutaXMLEquipos) {
        GestorEquipos gestorEquipos = new GestorEquipos();
        gestorEquipos.cargarDocumento(rutaXMLEquipos, tipoValidacion);
        CorredorNombreEquipoHandler handler = new CorredorNombreEquipoHandler(nombreEquipo, gestorEquipos);
        XMLSAXUtils.procesarDocumento(rutaXML, handler, tipoValidacion);
        return handler.getCorredores();
    }

    public void mostrarCorredoresPorEquipoSAX(String nombreEquipo, String rutaXMLEquipos) {
        ArrayList<Corredor> corredores = cargarCorredoresPorEquipoSAX(nombreEquipo, rutaXMLEquipos);
        if (corredores.size() != 0) {
            for (Corredor corredor : corredores) {
                corredor.mostrarInformacion();
            }
        } else {
            System.out.printf("El equipo %s no existe en el archivo XML.",nombreEquipo);
        }

    }

    public void mostrarTodosLosCorredoresSAX() {
        ArrayList<Corredor> corredores = cargarCorredoresSAX();
        for (Corredor corredor : corredores) {
            corredor.mostrarInformacion();
        }
    }
}
