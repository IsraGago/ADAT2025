package ud1.actividad4.persistencia.persistenciastax.staxcursor;

import org.xml.sax.SAXException;
import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4.persistencia.TipoValidacion;

import javax.xml.XMLConstants;
import javax.xml.stream.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class XMLStaxUtilsCursor {
    public static XMLStreamReader cargarDocumentos(String rutaFichero, TipoValidacion tipoValidacion) {
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
            return factory.createXMLStreamReader(new FileInputStream(file), "UTF-8");

        } catch (Exception e) {
            throw new ExcepcionXML("Error al cargar el documento XML con StAX con cursor.", e);
        }
    }

    private static void validarConXSD(File xmlFile) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema();
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
        } catch (IOException | SAXException e) {
            throw new ExcepcionXML("Error de validación XSD con StAX con cursor: " + e.getMessage(), e);
        }
    }

    private static void validarConDTD(File xmlFile) {
        // NO SOPORTA DTD
        //XMLDOMUtils.cargarDocumentoXML(xmlFile.getAbsolutePath(),TipoValidacion.DTD);
        throw new ExcepcionXML("La validación con DTD está deshabilitada en StAX cursor.");
    }

    public static String leerTexto(XMLStreamReader lector) {
        return lector.getEventType() == XMLStreamConstants.CHARACTERS ? lector.getText().trim() : "";
    }

    public static String leerAtributo(XMLStreamReader lector, String nombre) {
        return lector.getAttributeValue(null, nombre);
    }

    public static String obtenerNombreEtiqueta(XMLStreamReader lector) {
        if (lector.isStartElement() || lector.isEndElement()) {
            return lector.getLocalName();
        } else {
            return null;
        }
    }

    public static XMLStreamWriter crearWriterStax(String rutaSalida) {
        try {
            XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
            return outputFactory.createXMLStreamWriter(new FileWriter(rutaSalida));
        } catch (IOException e) {
            throw new ExcepcionXML("No se pudo crear el fichero XML: " + rutaSalida, e);
        } catch (XMLStreamException e) {
            throw new ExcepcionXML("Error al inicializar XMLStreamWriter para: " + rutaSalida, e);
        } catch (Exception e) {
            throw new ExcepcionXML("Error inesperado al crear XMLStreamWriter.", e);
        }
    }

    public static void addDeclaracionXML(XMLStreamWriter writer) {
        try {
            writer.writeStartDocument("UTF-8", "1.0");
        } catch (Exception e) {
            throw new ExcepcionXML("Error al crear la declaracion XML incicial.", e);
        }
    }

    public static void addSaltoDeLinea(XMLStreamWriter writer, int nivel) {
        try {
            String identacion = "\n" + "    ".repeat(nivel);
            writer.writeCharacters(identacion);
        } catch (XMLStreamException e) {
            throw new ExcepcionXML("Error al añadir salto de linea o identación.", e);
        }
    }

    public static void addAtributo(XMLStreamWriter writer, String nombre, String valor) {
        try {
            writer.writeAttribute(nombre, valor);
        } catch (XMLStreamException e) {
            throw new ExcepcionXML("Error al crear el atributo: " + nombre, e);
        }
    }

    public static void addElemento(XMLStreamWriter writer, String nombre, String valor) {
        try {
            writer.writeStartElement(nombre);
            writer.writeCharacters(valor);
            writer.writeEndElement();
        } catch (XMLStreamException e) {
            throw new ExcepcionXML("Error al crear el elemento: " + nombre, e);
        }
    }

    public static void addElementoVacio(XMLStreamWriter writer, String nombre) {
        try {
            writer.writeEmptyElement(nombre);
        } catch (XMLStreamException e) {
            throw new ExcepcionXML("Error al crear el elemento vacío: " + nombre, e);
        }
    }

    public static void addEndDocumento(XMLStreamWriter writer) {
        try {
            writer.writeEndDocument();
            writer.flush();
        } catch (Exception e) {
            throw new ExcepcionXML("Error al crear el cerrar un elemento", e);
        }
    }

    public static void addStartElemento(XMLStreamWriter writer, String nombre) {
        try {
            writer.writeStartElement(nombre);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addTextoElemento(XMLStreamWriter writer, String texto) {
        try {
            if (texto != null) {
                writer.writeCharacters(texto);
            }
        } catch (XMLStreamException e) {
            throw new ExcepcionXML("Error al escribir texto en un elemento", e);
        }
    }

    public static void addEndElemento(XMLStreamWriter writer) {
        try {
            writer.writeEndElement();
        } catch (XMLStreamException e) {
            throw new ExcepcionXML("Error cerrar un elemento.", e);
        }
    }


}
