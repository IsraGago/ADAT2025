package ud1.actividad4.persistencia.persistenciastax.staxeventos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4.persistencia.TipoValidacion;

public class XMLStaxUtilsEventos {
    public static XMLEventReader cargarDocumentos(String rutaFichero, TipoValidacion tipoValidacion) {
        try {
            if (rutaFichero == null || rutaFichero.isEmpty()) {
                throw new ExcepcionXML("la ruta al fichero XML no puede estar vacía");
            }

            if (tipoValidacion == null) {
                throw new ExcepcionXML("El tipo de validación no puede ser nulo");
            }

            File file = new File(rutaFichero);
            if (!file.exists()) {
                throw new ExcepcionXML("El fichero XML no existe: " + rutaFichero);
            }

            switch (tipoValidacion) {
                case XSD -> validarConXSD(file);
                case DTD -> validarConDTD(file);
                case NO_VALIDAR -> {
                }
            }

            // crear lector StAX para recorrer el documento
            XMLInputFactory factory = XMLInputFactory.newInstance();
            return factory.createXMLEventReader(new FileInputStream(rutaFichero), "UTF-8");

        } catch (Exception e) {
            throw new ExcepcionXML("Error al cargar el documento XML con StAX con eventos.", e);
        }
    }

    private static void validarConXSD(File xmlFile) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema();
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
        } catch (IOException | SAXException e) {
            throw new ExcepcionXML("Error de validación XSD con StAX con eventos: " + e.getMessage(), e);
        }
    }

    private static void validarConDTD(File xmlFile) {
        // XMLDOMUtils.cargarDocumentoXML(xmlFile.getAbsolutePath(),TipoValidacion.DTD);
        throw new ExcepcionXML("La validación con DTD está deshabilitada en StAX con eventos.");
    }

    public static String obtenerNombreEtiqueta(XMLEvent event) {
        if (event.isStartElement()) {
            return event.asStartElement().getName().getLocalPart();
        } else if (event.isEndElement()) {
            return event.asEndElement().getName().getLocalPart();
        } else {
            return null;
        }
    }

    public static String leerTexto(XMLEvent event) {
        return event.isCharacters() ? event.asCharacters().getData().trim() : "";
    }

    public static String leerAtributo(XMLEvent evento, String nombre) {
        if (evento.isStartElement()) {
            StartElement start = evento.asStartElement();
            QName nombreCalificado = new QName(nombre);
            Attribute atributo = start.getAttributeByName(nombreCalificado);
            return atributo != null ? atributo.getValue() : null;
        }
        return null;
    }

    public static XMLEventWriter crearWriterStax(String rutaSalida) {
        try {
            XMLOutputFactory factory = XMLOutputFactory.newFactory();
            return factory.createXMLEventWriter(new FileWriter(rutaSalida));
        } catch (XMLStreamException e) {
            throw new ExcepcionXML("Error al inicializar la factoría o el Writer.", e);
        } catch (IOException e) {
            throw new ExcepcionXML("Error de I/O al intentar crear el archivo de salida: " + rutaSalida, e);
        }
    }


    public static XMLEventFactory crearFactoryEventos() {
        try {
            return XMLEventFactory.newFactory();
        } catch (FactoryConfigurationError e) {
            throw new RuntimeException(e);
        }
    }

    public static void addDeclaracionXML(XMLEventWriter writer, XMLEventFactory factory) {
        try {
            writer.add(factory.createStartDocument("UTF-8", "1.0"));
        } catch (Exception e) {
            throw new ExcepcionXML("Error al crear la declaracion XML incicial.", e);
        }
    }

    public static void addSaltoDelinea(XMLEventWriter writer, int nivel, XMLEventFactory fabrica) {
        try {
            String identacion = "\n" + "    ".repeat(nivel);
            writer.add(fabrica.createCharacters(identacion));
        } catch (XMLStreamException e) {
            throw new ExcepcionXML("Error al añadir salto de linea o identación.", e);
        }
    }

    public static void addStartElemento(XMLEventWriter writer, String nombre, XMLEventFactory fabrica) {
        try {
            // todo combrobar
            writer.add(fabrica.createStartElement(null,null,nombre));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addAtributo(XMLEventWriter writer, XMLEventFactory fabrica, String nombre, String valor) {
        try {
            writer.add(fabrica.createAttribute(nombre, valor));
        } catch (XMLStreamException e) {
            throw new ExcepcionXML("Error al crear el atributo: " + nombre, e);
        }
    }
}
