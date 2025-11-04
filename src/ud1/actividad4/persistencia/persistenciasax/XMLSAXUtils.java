package ud1.actividad4.persistencia.persistenciasax;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.helpers.DefaultHandler;

import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4.persistencia.TipoValidacion;

public class XMLSAXUtils {
    /*
     * MÉTODOS:
     * 1. procesar un documento xml con SAX
     * 2. configurar parser del SAX
     */
    public static void procesarDocumento(String rutaFicheroXML, DefaultHandler handler, TipoValidacion tipoValidacion) {
        /*
         * configurar el SAX
         * crear el parser SAX
         */

        if (rutaFicheroXML == null || rutaFicheroXML.isEmpty()) {
            throw new IllegalArgumentException("Error: la ruta del fichero no puede ser nula o vacía.");
        }
        if (tipoValidacion == null) {
            throw new IllegalArgumentException("Error: el tipo de validación no puede ser nulo");
        }
        File ficheroXML = new File(rutaFicheroXML);
        if (!ficheroXML.exists() || ficheroXML.isDirectory()) {
            throw new IllegalArgumentException("Error: el fichero proporcionado no existe.");
        }

        try {
            SAXParserFactory factory = configurarFactoria(tipoValidacion);
            SAXParser parser = factory.newSAXParser();
            parser.parse(ficheroXML, handler);
        } catch (Exception e) {
            throw new ExcepcionXML(rutaFicheroXML,e);
        }
    }

    public static SAXParserFactory configurarFactoria(TipoValidacion tipoValidacion) {
        SAXParserFactory factoria = SAXParserFactory.newInstance();
        switch (tipoValidacion) {
            case XSD -> {
                factoria.setValidating(true);
                factoria.setNamespaceAware(true);
                try {
                    factoria.setFeature("http://xml.org/sax/features/validation", true);
                    factoria.setFeature("http://apache.org/xml/features/validation/schema", true);
                } catch (ParserConfigurationException | SAXNotRecognizedException | SAXNotSupportedException e) {
                    throw new RuntimeException("Error al configurar validación XSD en SAX. "+e.getMessage());
                }
            }
            case DTD -> {
                factoria.setValidating(true);
                // factoria.setNamespaceAware(false);
            }
            case NO_VALIDAR -> {
                factoria.setValidating(false);
            }

            default -> {
            }
        }
        return factoria;
    }
}
