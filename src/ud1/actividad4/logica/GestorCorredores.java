package ud1.actividad4.logica;

import ud1.actividad4.clases.Puntuacion;
import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4.persistencia.TipoValidacion;
import ud1.actividad4.persistencia.persistenciadom.CorredorXML;
import ud1.actividad4.persistencia.persistenciadom.XMLDOMUtils;
import ud1.actividad4.persistencia.persistenciasax.ActualizacionesSAXHandler;
import ud1.actividad4.persistencia.persistenciasax.CorredorNombreEquipoHandler;
import ud1.actividad4.persistencia.persistenciasax.CorredorSAXHandler;
import ud1.actividad4.persistencia.persistenciasax.XMLSAXUtils;
import ud1.actividad4.persistencia.persistenciastax.staxcursor.CorredoresStaxCursor;
import ud1.actividad4.persistencia.persistenciastax.staxcursor.XMLStaxUtilsCursor;
import ud1.actividad4.clases.Corredor;
import ud1.actividad4.persistencia.persistenciastax.staxeventos.CorredoresStaxEventos;
import ud1.actividad4.persistencia.persistenciastax.staxeventos.XMLStaxUtilsEventos;
import ud1.actividad4.servicio.Utilidades;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamReader;

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

    public ArrayList<Corredor> cargarCorredoresPorEquipoDOM(String nombreEquipo, String rutaXMLEquipos) {
        GestorEquipos gestorEquipos = new GestorEquipos();
        gestorEquipos.cargarDocumento(rutaXMLEquipos, TipoValidacion.DTD);
        String codigoEquipo = gestorEquipos.getIdEquipo(nombreEquipo);
        ArrayList<Corredor> corredores = new ArrayList<>();
        for (Corredor corredor : cargarCorredoresSAX()) {
            if (corredor.getEquipo().equalsIgnoreCase(codigoEquipo)) {
                corredores.add(corredor);
            }

        }
        return corredores;
    }

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
        if (!corredores.isEmpty()) {
            for (Corredor corredor : corredores) {
                corredor.mostrarInformacion();
            }
        } else {
            System.out.printf("El equipo %s no existe en el archivo XML.", nombreEquipo);
        }

    }

    public void mostrarTodosLosCorredoresSAX() {
        ArrayList<Corredor> corredores = cargarCorredoresSAX();
        for (Corredor corredor : corredores) {
            corredor.mostrarInformacion();
        }
    }

    public static void aplicarAcualizaciones(String rutaEquipos, String rutaActualizaciones, String rutaSalida) {
        Document documentoDOM = XMLDOMUtils.cargarDocumentoXML(rutaEquipos, TipoValidacion.DTD);
        ActualizacionesSAXHandler handler = new ActualizacionesSAXHandler(documentoDOM, rutaSalida);
        XMLSAXUtils.procesarDocumento(rutaActualizaciones, handler, TipoValidacion.NO_VALIDAR);
    }

    // StAX cursor

    public void mostrarCorredoresStaxCursor(String rutaXML, TipoValidacion validacion) {
        try {
            List<Corredor> corredores = CorredoresStaxCursor.leerCorredores(rutaXML, validacion);
            for (Corredor corredor : corredores) {
                corredor.mostrarInformacion();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarCorredoresPorEquipoCursor(String rutaXML, TipoValidacion validacion, String equipoBuscado) {
        XMLStreamReader reader = XMLStaxUtilsCursor.cargarDocumentos(rutaXML, validacion);
        List<Corredor> corredoresPorEquipo = CorredoresStaxCursor.leerCorredoresPorEquipo(reader, equipoBuscado);
        for (Corredor corredor : corredoresPorEquipo) {
            corredor.mostrarInformacion();
        }
    }

    // StAX eventos
    public void mostrarCorredoresStaxEventos(String rutaXmlCorredores, TipoValidacion validacion) {
        try {
            List<Corredor> corredores = CorredoresStaxEventos.leerCorredores(rutaXmlCorredores, validacion);
            for (Corredor corredor : corredores) {
                corredor.mostrarInformacion();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
