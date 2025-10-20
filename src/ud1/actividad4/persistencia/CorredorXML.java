package ud1.actividad4.persistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import ud1.actividad4.clases.Corredor;
import ud1.actividad4.clases.Fondista;
import ud1.actividad4.clases.Puntuacion;
import ud1.actividad4.clases.Velocista;

public class CorredorXML {
    public Document cargarDocumentoXML(String rutaXML, TipoValidacion validacion) throws ExcepcionXML {
        return XMLDOMUtils.cargarDocumentoXML(rutaXML, validacion);
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

    public Corredor crearCorredor(Element corredorElem) {
        // ATRIBUTOS
        String codigo = corredorElem.getAttribute("codigo");
        int dorsal = Integer.parseInt(corredorElem.getAttribute("dorsal"));
        String equipo = corredorElem.getAttribute("equipo");
        // ETIQUETAS
        String nombre = XMLDOMUtils.obtenerTexto(corredorElem, "nombre");
        LocalDate fecha = LocalDate.parse(XMLDOMUtils.obtenerTexto(corredorElem, "fecha_nacimiento"));

        Corredor corredor = switch (corredorElem.getTagName()) {
            // TODO AÑADIR EL CÓDIGO DE CORREDOR
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
        Element historialElem = (Element) corredorElem.getElementsByTagName("historial").item(0);
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

    private Corredor getCorredor(String codigo, Document doc){
        Element corredorElem = doc.getElementById(codigo);
        return crearCorredor(corredorElem);

        // Corredor corredorABuscar = null;
        // NodeList nodos = raiz.getChildNodes();
        // Element raiz = doc.getDocumentElement();
        // for (int i = 0; i < nodos.getLength(); i++) {
        //     if (nodos.item(i) instanceof Element corredorElem) {
        //         Corredor corredor = crearCorredor(corredorElem);
        //         if (corredor.getCodigo() == codigo) {
        //             corredorABuscar = corredor;
        //             break;
        //         }
        //     }
        // }
        // return corredorABuscar;
    }
}
