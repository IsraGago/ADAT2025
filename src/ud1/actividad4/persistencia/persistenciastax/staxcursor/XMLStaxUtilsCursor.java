package ud1.actividad4.persistencia.persistenciastax.staxcursor;

import org.xml.sax.SAXException;
import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4.persistencia.TipoValidacion;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class XMLStaxUtilsCursor {
    public static XMLStreamReader cargarDocumentos(String rutaFichero, TipoValidacion tipoValidacion){
        try {
            if (rutaFichero==null || rutaFichero.isEmpty()){
                throw new ExcepcionXML("la ruta al fichero XML no puede estar vacía");
            }

            if (tipoValidacion==null){
                throw new ExcepcionXML("El tipo de validación no puede ser nulo");
            }

            File file = new File(rutaFichero);
            if (!file.exists()){
                throw new ExcepcionXML("El fichero XML no existe: "+rutaFichero);
            }

            switch (tipoValidacion){
                case XSD -> validarConXSD(file);
                case DTD -> validarConDTD(file);
                case NO_VALIDAR -> {}
            }

            // crear lector StAX para recorrer el documento
            XMLInputFactory factory = XMLInputFactory.newInstance();
            return factory.createXMLStreamReader(new FileInputStream(file),"UTF-8");

        } catch (Exception e) {
            throw new ExcepcionXML("Error al cargar el documento XML con StAX con cursor.",e);
        }
    }

    private static void validarConXSD(File xmlFile) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema();
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
        } catch (IOException | SAXException e) {
            throw new ExcepcionXML("Error de validación XSD con StAX con cursor: "+e.getMessage(),e);
        }
    }

    private static void validarConDTD(File xmlFile) {
        //XMLDOMUtils.cargarDocumentoXML(xmlFile.getAbsolutePath(),TipoValidacion.DTD);
        throw new ExcepcionXML("La validación con DTD está deshabilitada en StAX cursor.");
    }

    public static String leerTexto(XMLStreamReader lector){
        return lector.getEventType() == XMLStreamConstants.CHARACTERS ? lector.getText().trim() : "";
    }

    public static String leerAtributo(XMLStreamReader lector,String nombre){
        return lector.getAttributeValue(null, nombre);
    }

    public static String obtenerNombre(XMLStreamReader lector){
        if (lector.isStartElement() || lector.isEndElement()){
            return lector.getLocalName();
        } else {
            return null;
        }
    }
}
