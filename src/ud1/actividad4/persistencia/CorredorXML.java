package ud1.actividad4.persistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ud1.actividad4.clases.Corredor;
import ud1.actividad4.clases.Fondista;
import ud1.actividad4.clases.Puntuacion;
import ud1.actividad4.clases.Velocista;

public class CorredorXML {
    Document documentoXML;

    public Document cargarDocumentoXML(String rutaXML, TipoValidacion validacion) throws ExcepcionXML {
        try {
            documentoXML = XMLDOMUtils.cargarDocumentoXML(rutaXML, validacion);
        } catch (Exception e) {
            throw new ExcepcionXML("Error al cargar el documento XML: "+e.getMessage());
        }
        return documentoXML;
    }

    public List<Corredor> cargarCorredores(Document doc) {
        List<Corredor> lista = new ArrayList<>();
        Element raiz = doc.getDocumentElement();
        NodeList nodos = raiz.getChildNodes();
        for (int i = 0; i < nodos.getLength(); i++) {
            if (nodos.item(i) instanceof Element corredorElem) { // guarda la instancia en la variable si da true
                                                                 // instanceof
                Corredor corredor = crearCorredor(corredorElem);
                if (corredor != null) {
                    lista.add(corredor);
                }
            }
        }
        return lista;
    }

    private Corredor crearCorredor(Element corredorElem) {
        // ATRIBUTOS
        String codigo = corredorElem.getAttribute("codigo");
        int dorsal = Integer.parseInt(corredorElem.getAttribute("dorsal"));
        String equipo = corredorElem.getAttribute("equipo");
        // ETIQUETAS
        String nombre = XMLDOMUtils.obtenerTexto(corredorElem, "nombre");
        LocalDate fecha = LocalDate.parse(XMLDOMUtils.obtenerTexto(corredorElem, "fecha_nacimiento"));

        Corredor corredor = switch (corredorElem.getTagName()) {
            case "fondista" -> {
                float distancia = Float.parseFloat(XMLDOMUtils.obtenerTexto(corredorElem, "distancia_max"));
                yield new Fondista(codigo, dorsal, nombre, fecha, equipo, distancia);
            }

            case "velocista" -> {
                float velocidad = Float.parseFloat(XMLDOMUtils.obtenerTexto(corredorElem, "velocidad_media"));
                yield new Velocista(codigo, dorsal, nombre, fecha, equipo, velocidad);
            }

            default -> {
                yield null;
            }
        };
        if (corredor != null) {
            corredor.setHistorial(cargarHistorial(corredorElem));
        }
        return corredor;
    }

    private ArrayList<Puntuacion> cargarHistorial(Element corredorElem) {
        ArrayList<Puntuacion> listaPuntuaciones = new ArrayList<>();
        Element historialElem = (Element) corredorElem.getElementsByTagName("historial").item(0); // DEVUELVE NULO SI EL
                                                                                                  // INDICE NO EXISTE
        if (historialElem != null) {
            NodeList puntuaciones = historialElem.getElementsByTagName("puntuacion");
            for (int i = 0; i < puntuaciones.getLength(); i++) {
                Element puntuacion = (Element) puntuaciones.item(i);
                int anio = Integer.parseInt(puntuacion.getAttribute("anio"));
                float puntos = Float.parseFloat(puntuacion.getTextContent());
                listaPuntuaciones.add(new Puntuacion(anio, puntos));
            }
        }
        return listaPuntuaciones;
    }

    public Corredor getCorredor(String codigo, Document doc) {
        Element corredorElem = doc.getElementById(codigo);
        return crearCorredor(corredorElem);
    }

    public Corredor getCorredor(int dorsal, Document doc) {
        Element raiz = doc.getDocumentElement();
        Corredor corredorABuscar = null;
        NodeList nodos = raiz.getChildNodes();
        for (int i = 0; i < nodos.getLength(); i++) {
            if (nodos.item(i) instanceof Element corredorElem) {
                // Corredor corredor = crearCorredor(corredorElem);
                // if (corredor.getDorsal() == dorsal) {
                // corredorABuscar = corredor;
                // break;
                // }
                if (Integer.parseInt(corredorElem.getAttribute("dorsal")) == dorsal) {
                    corredorABuscar = crearCorredor(corredorElem);
                    break;
                }
            }
        }
        return corredorABuscar;
    }

    public void insertarCorredor(Corredor corredor) {

        Element raiz = documentoXML.getDocumentElement();
        String nombre = corredor.getClass().getSimpleName();

        // CREAR ELEMENTO CORREDOR Y COLGARLO DEL RAIZ
        Element corredorElement = XMLDOMUtils.addElement(documentoXML, nombre, null, raiz);

        // ELEMENTOS COMUNES {nombre y fecha}
        XMLDOMUtils.addElement(documentoXML, "nombre", corredor.getNombre(), corredorElement);
        XMLDOMUtils.addElement(documentoXML, "fecha_nacimiento", corredor.getFechanacimiento().toString(),
                corredorElement);
        // ATRIBUTOS {codigo, dorsal y equipo}
        XMLDOMUtils.addAtributo(documentoXML, "codigo", corredor.getCodigo(), corredorElement);
        XMLDOMUtils.addAtributo(documentoXML, "dorsal", corredor.getDorsal() + "", corredorElement);
        XMLDOMUtils.addAtributo(documentoXML, "equipo", corredor.getEquipo(), corredorElement);

        // ELEMENTO ESPECÍFICO {velocidad_media | distacia_max}
        if (corredor instanceof Velocista v) {
            XMLDOMUtils.addElement(documentoXML, "velocidad_media", v.getVelocidadMedia() + "", corredorElement);
        } else if (corredor instanceof Fondista f) {
            XMLDOMUtils.addElement(documentoXML, "distacia_max", f.getDistanciaMax() + "", corredorElement);
        }

        // HISTORIAL
        Element historial = XMLDOMUtils.addElement(documentoXML, "historial", null, corredorElement);
        for (Puntuacion puntuacion : corredor.getHistorial()) {
            XMLDOMUtils.addElement(documentoXML, "puntuacion", puntuacion.getPuntos()+"", historial);
            XMLDOMUtils.addAtributo(documentoXML, "anio", puntuacion.getAnio()+"", historial);
        }

    }

    public boolean eliminarCorredorPorCodigo(String codigo) {
        Element corredorElem = XMLDOMUtils.buscarElementoPorID(documentoXML, codigo);
        return XMLDOMUtils.eliminarElemento(corredorElem);
    }

    public boolean eliminarCorredorPorDorsal(int dorsal) {
        Corredor corredorAeliminar = getCorredor(dorsal, documentoXML);
        return eliminarCorredorPorCodigo(corredorAeliminar.getCodigo());
    }

    public int getUltimoDorsal(){
        Element raiz = documentoXML.getDocumentElement();
        NodeList hijos = raiz.getChildNodes();

        for (int i = hijos.getLength() -1 ; i >= 0; i--) {
            Node nodo = hijos.item(i);
            if (nodo instanceof Element corredorElement) {
                Corredor corredor = crearCorredor(corredorElement);
                return corredor.getDorsal();
            }
        }
        return -1;
    }

    public void addPuntuacion(String codigo,Puntuacion puntuacion){
        Element historial = (Element) documentoXML.getElementById(codigo).getElementsByTagName("historial").item(0);
        Element puntuacionElem = XMLDOMUtils.addElement(documentoXML, "puntuacion", puntuacion.getPuntos()+"", historial);
        XMLDOMUtils.addAtributo(documentoXML, "anio", puntuacion.getAnio()+"", puntuacionElem);
    }
}
