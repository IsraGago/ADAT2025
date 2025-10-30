package ud1.actividad4.persistencia.persistenciadom;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4.persistencia.SimpleErrorHandler;
import ud1.actividad4.persistencia.TipoValidacion;

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
            // documento.normalize(); // QUITAR LOS ENTERS (?)
            documento.getDocumentElement().normalize();
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
                // TIENES QUE TENER EL DOCTYPE EN EL XML PARA QUE FUNCIONE LA VALIDACIÓN DTD
                dbf.setValidating(true);
                dbf.setIgnoringElementContentWhitespace(true); // PARA QUE NO CUENTE ENTER NI TABULACIONES
                break;
            case XSD:
                // TIENES QUE TENER EL ESQUEMA ASOCIADO EN EL XML PARA QUE FUNCIONE LA
                // VALIDACIÓN XSD
                // dbf.setValidating(true);
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

    public static void guardarDocumento(Document doc, String rutaDesitno) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();

            // evitar espacios excesivos
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileWriter(rutaDesitno));
            transformer.transform(source, result);
        } catch (TransformerException | IOException e) {
            throw new ExcepcionXML("Error al guardar el documento XML:" + e.getMessage());
        }
    }

    public static Attr addAtributo(Document documento, String nombre, String valor, Element padre) {
        Attr atributo = documento.createAttribute(nombre);
        atributo.setValue(valor);
        padre.setAttributeNode(atributo);
        // element.setAttribute(nombre, valor);
        return atributo;
    }

    public static Attr addAtributoId(Document documento, String nombre, String valorId, Element padre) {
        Attr atributo = documento.createAttribute(nombre);
        atributo.setValue(valorId);
        padre.setAttributeNode(atributo);
        // element.setAttribute(nombre, valor);
        padre.setIdAttributeNode(atributo, true);
        return atributo;
    }

    public static Element addElement(Document documento, String nombre, String valor, Element padre) {
        Element elemento = documento.createElement(nombre);
        Text texto = documento.createTextNode(valor);
        padre.appendChild(elemento);
        elemento.appendChild(texto);
        return elemento;
    }

    public static boolean eliminarElemento(Element elemento) {
        if (elemento != null && elemento.getParentNode() != null) {
            elemento.getParentNode().removeChild(elemento);
            return true;
        }
        return false;
    }

    public static void modificarAtributo(Element elemento, String nombre, String valor) {
        elemento.setAttribute(nombre, valor); // TODO REVISAR SI FUNCIONA
    }

    public static void updateValorElemento(Element elemento, String valor) {
        elemento.setTextContent(valor);
    }

    public static Element buscarElementoPorID(Document doc, String idValue) {
        return doc.getElementById(idValue);
    }

    private Object evaluarXPath(Object contexto, String expresion, QName tipoResultado) {
        // CONTEXTO ES EL NODO DESDE EL QUE EMPIEZA A BUSCAR, NORMALMENTE EL RAIZ
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            return xPath.evaluate(expresion, xPath, tipoResultado);
        } catch (Exception e) {
            throw new ExcepcionXML("Error al evaluar la expresión XPath: " + e.getMessage());
        }
    }

    private Node evaluarXPathNode(Object contexto, String expresion) {
        return (Node) evaluarXPath(contexto, expresion, XPathConstants.NODE);
    }

    private Boolean evaluarXPathBoolean(Object contexto, String expresion) {
        return (Boolean) evaluarXPath(contexto, expresion, XPathConstants.BOOLEAN);
    }

    private NodeList evaluarXPathNodeList(Object contexto, String expresion) {
        return (NodeList) evaluarXPath(contexto, expresion, XPathConstants.NODESET);
    }

    private Double evaluarXPathNumber(Object contexto, String expresion) {
        return (Double) evaluarXPath(contexto, expresion, XPathConstants.NUMBER);
    }

    private String evaluarXPathString(Object contexto, String expresion) {
        return (String) evaluarXPath(contexto, expresion, XPathConstants.STRING);
    }

    public void guardarDocumentoXML(String rutaSalida, Document documento) {

        TransformerFactory transFact;
        try {
            transFact = TransformerFactory.newInstance();
            documento.normalize();
            // añadimos sangrado cada tres espacios
            transFact.setAttribute("indent-number", 3);

            try {
                Transformer trans;
                trans = transFact.newTransformer();
                trans.setOutputProperty(OutputKeys.INDENT, "yes");
                trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
                DOMSource domSource = new DOMSource(documento);
                // creamos fichero para escribir en modo texto
                FileWriter sw = new FileWriter(rutaSalida);
                // escribimos todo el arbol en el fichero
                StreamResult sr = new StreamResult(sw);
                trans.transform(domSource, sr);
                

            } catch (TransformerConfigurationException ex) {
                System.out.println(" ");
                throw new ExcepcionXML("ERROR al construir el motor de transformación: "+ex.getMessage());
            } catch (IOException ex) {
                throw new ExcepcionXML("ERROR de entrada/salida: " + ex.getMessage());
            } catch(TransformerException ex){
                throw new ExcepcionXML("ERROR al transformar: "+ex.getMessage());
            }
        } catch (TransformerFactoryConfigurationError e) {
            throw new ExcepcionXML("ERROR a la hora de implementar la transformación: "+e.getMessage());
        }

    }
}
