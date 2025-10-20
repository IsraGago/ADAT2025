package ud1.actividad4.persistencia;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLDOMUtils {

    // Especifica el lenguaje utilizado por el parser en el análisis
    static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    // especifica el espacio de nombres
    static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

    public static Document cargarDocumentoXML(String rutaFichero, TipoValidacion validacion) {

        try {
            // CREAR Y CONFIGURAR FACTORÍA SEGÚN EL TIPO DE VALIDACIÓN
            DocumentBuilderFactory dbf = configurarFactory(validacion);

            // CREAR PARSER
            DocumentBuilder db = dbf.newDocumentBuilder();

            // MANEJADOR DE ERORRES
            if (validacion != TipoValidacion.NO_VALIDAR) {
                db.setErrorHandler(new SimpleErrorHandler());
            }

            // CARGAR DOCUMENTO EN MEMORIA
            Document documento = db.parse(new File(rutaFichero));
            documento.normalize(); // QUITAR LOS ENTERS (?)

            return documento;
        } catch (ParserConfigurationException ex) {
            throw new ExcepcionXML("Error de configuracion del parser: " + ex.getMessage());
        } catch (SAXException ex) {
            throw new ExcepcionXML("Error durante el parsing o validación: " + ex.getMessage());
        } catch (IOException ex) {
            throw new ExcepcionXML("Error de lectura: " + ex.getMessage());
        }
    }

    private static DocumentBuilderFactory configurarFactory(TipoValidacion validacion) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        switch (validacion) {
            case DTD:
                dbf.setValidating(true);
                dbf.setIgnoringElementContentWhitespace(true); // PARA QUE NO CUENTE ENTER NI TABULACIONES
                break;
            case XSD:
                dbf.setNamespaceAware(true);
                dbf.setIgnoringElementContentWhitespace(true);
                dbf.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
                break;
            case NO_VALIDAR:
                dbf.setValidating(false); // POR DEFECTO YA ES FALSE, NO HACE FALTA
                break;
            default:
                break;
        }
        return dbf;
    }

    public static String obtenerTexto(Element padre, String etiqueta) {
        NodeList lista = padre.getElementsByTagName(etiqueta);
        if (lista.getLength() > 0) {
            return lista.item(0).getTextContent(); // DENTRO DEL PADRE SOLO HAY UN ITEM QUE ES EL TEXTO
        }
        return "";
    }
}
