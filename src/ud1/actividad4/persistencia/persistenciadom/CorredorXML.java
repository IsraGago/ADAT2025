package ud1.actividad4.persistencia.persistenciadom;

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
import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4.persistencia.TipoValidacion;
import ud1.actividad4.servicio.Utilidades;

public class CorredorXML {
    Document documentoXML;

    public Document cargarDocumentoXML(String rutaXML, TipoValidacion validacion) throws ExcepcionXML {
        try {
            documentoXML = XMLDOMUtils.cargarDocumentoXML(rutaXML, validacion);
        } catch (Exception e) {
            throw new ExcepcionXML("Error al cargar el documento XML: " + e.getMessage());
        }
        return documentoXML;
    }

    public List<Corredor> cargarCorredores() {
        List<Corredor> lista = new ArrayList<>();
        Element raiz = documentoXML.getDocumentElement();
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
        String codigo;
        if (corredorElem.getAttribute("codigo").isEmpty()) {
            codigo = calcularSiguienteCodigo(getUltimoCodigo());
        } else {
            codigo = corredorElem.getAttribute("codigo");
        }

        int dorsal;
        if (corredorElem.getAttribute("dorsal").isEmpty()) {
            dorsal = getUltimoDorsal() + 1;
        } else {
            dorsal = Integer.parseInt(corredorElem.getAttribute("dorsal"));
        }
        String equipo = corredorElem.getAttribute("equipo");
        // ETIQUETAS
        String nombre = XMLDOMUtils.obtenerTexto(corredorElem, "nombre");
        LocalDate fecha = LocalDate.parse(XMLDOMUtils.obtenerTexto(corredorElem, "fecha_nacimiento"));

        Corredor corredor = switch (corredorElem.getTagName().toLowerCase()) {
            case "fondista" -> {
                float distancia = Float.parseFloat(XMLDOMUtils.obtenerTexto(corredorElem, "distancia_max"));
                yield new Fondista(nombre, fecha, equipo, distancia);
            }

            case "velocista" -> {
                float velocidad = Float.parseFloat(XMLDOMUtils.obtenerTexto(corredorElem, "velocidad_media"));
                yield new Velocista(nombre, fecha, equipo, velocidad);
            }

            default -> {
                yield null;
            }
        };
        if (corredor != null) {
            corredor.setHistorial(cargarHistorial(corredorElem));
            corredor.setDorsal(dorsal);
            corredor.setCodigo(codigo);
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

    public Corredor getCorredor(String codigo) {
        Element corredorElem = documentoXML.getElementById(codigo);
        return crearCorredor(corredorElem);
    }

    public Corredor getCorredor(int dorsal) {
        Element raiz = documentoXML.getDocumentElement();
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
        // ATRIBUTOS {codigo (ID), dorsal y equipo}
        XMLDOMUtils.addAtributoId(documentoXML, "codigo", calcularSiguienteCodigo(getUltimoCodigo()), corredorElement);
        XMLDOMUtils.addAtributo(documentoXML, "dorsal", (getUltimoDorsal() + 1) + "", corredorElement);
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
            XMLDOMUtils.addElement(documentoXML, "puntuacion", puntuacion.getPuntos() + "", historial);
            XMLDOMUtils.addAtributo(documentoXML, "anio", puntuacion.getAnio() + "", historial);
        }

    }

    public boolean eliminarCorredorPorCodigo(String codigo) {
        Element corredorElem = XMLDOMUtils.buscarElementoPorID(documentoXML, codigo);
        return XMLDOMUtils.eliminarElemento(corredorElem);
    }

    public boolean eliminarCorredorPorDorsal(int dorsal) {
        Corredor corredorAeliminar = getCorredor(dorsal);
        return eliminarCorredorPorCodigo(corredorAeliminar.getCodigo());
    }

    public int getUltimoDorsal() {
        Element raiz = documentoXML.getDocumentElement();
        NodeList hijos = raiz.getChildNodes();

        for (int i = hijos.getLength() - 1; i >= 0; i--) {
            Node nodo = hijos.item(i);
            if (nodo instanceof Element corredorElement) {
                if (!corredorElement.getAttribute("dorsal").isEmpty()) {
                    return Integer.parseInt(corredorElement.getAttribute("dorsal"));
                }
            }
        }
        return -1;
    }

    public String getUltimoCodigo() {
        Element raiz = documentoXML.getDocumentElement();
        NodeList hijos = raiz.getChildNodes();

        for (int i = hijos.getLength() - 1; i >= 0; i--) {
            Node nodo = hijos.item(i);
            if (nodo instanceof Element corredorElement) {

                // String ultimoCodigo =
                // corredorElement.getElementsByTagName("codigo").item(0).getTextContent();
                String ultimoCodigo = corredorElement.getAttribute("codigo");
                if (ultimoCodigo != null && !ultimoCodigo.isEmpty()) {
                    int parteNumerica = Integer.parseInt(ultimoCodigo.substring(1));
                    return "C" + (parteNumerica < 10 ? "0" : "") + parteNumerica;
                }
            }
        }
        return null;
    }

    public String calcularSiguienteCodigo(String codigo) {
        if (!Utilidades.esCodigoValido(codigo)) {
            return null;
        }
        int parteNumerica = Integer.parseInt(codigo.substring(1)) + 1;
        return "C" + (parteNumerica < 10 ? "0" : "") + parteNumerica;
    }

    public void addPuntuacion(String codigoCorredor, Puntuacion puntuacion) {
        Element historial = (Element) documentoXML.getElementById(codigoCorredor).getElementsByTagName("historial").item(0);

        NodeList nodosPuntuaciones = historial.getElementsByTagName("puntuacion");
        boolean existe = false;
        for (int i = 0; i < nodosPuntuaciones.getLength(); i++) {
            Element p = (Element) nodosPuntuaciones.item(i); // instanceof (?)
            if (Integer.parseInt(p.getAttribute("anio")) == puntuacion.getAnio()) {
                existe = true;
                // TODO: COMPROBAR SI EDITA BIEN
                p.setTextContent(puntuacion.getPuntos()+"");

            }
        }
        if (!existe) {
            Element puntuacionElem = XMLDOMUtils.addElement(documentoXML, "puntuacion", puntuacion.getPuntos() + "",
                    historial);
            XMLDOMUtils.addAtributo(documentoXML, "anio", puntuacion.getAnio() + "", puntuacionElem);
        }
    }
}
