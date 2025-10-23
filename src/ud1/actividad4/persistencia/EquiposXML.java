package ud1.actividad4.persistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import ud1.actividad4.clases.Patrocinador;
import ud1.actividad4.clases.Equipo;

public class EquiposXML {
    Document documentoXML;

    public Document cargarDocumentoXML(String rutaXML, TipoValidacion validacion) throws ExcepcionXML {
        try {
            documentoXML = XMLDOMUtils.cargarDocumentoXML(rutaXML, validacion);
        } catch (Exception e) {
            throw new ExcepcionXML("Error al cargar el documento XML: " + e.getMessage());
        }
        return documentoXML;
    }

    public List<Equipo> cargarEquipos(Document doc) {
        List<Equipo> lista = new ArrayList<>();
        Element raiz = documentoXML.getDocumentElement();
        NodeList nodos = raiz.getElementsByTagName("equipo");
        for (int i = 0; i < nodos.getLength(); i++) {
            if (nodos.item(i) instanceof Element equipoElement) {
                Equipo equipo = crearEquipo(equipoElement);
                if (equipo != null) {
                    lista.add(equipo);
                }
            }
        }
        return lista;
    }

    public Equipo crearEquipo(Element equipoElement) {
        String id = equipoElement.getAttribute("id");
        String nombre = XMLDOMUtils.obtenerTexto(equipoElement, "nombre");
        Set<Patrocinador> patrocinadores = cargarPatrocinadores(equipoElement);
        Equipo equipo = new Equipo(id, nombre);
        equipo.setPatrocinadores(patrocinadores);
        return equipo;
    }

    private Set<Patrocinador> cargarPatrocinadores(Element equipoElement) {
        Set<Patrocinador> patrocinadores = new HashSet<>();
        NodeList nodos = equipoElement.getElementsByTagName("patrocinador");
        for (int i = 0; i < nodos.getLength(); i++) {
            if (nodos.item(i) instanceof Element patrocinadorElement) {
                Patrocinador patrocinador = crearPatrocinador(patrocinadorElement);
                if (patrocinador != null) {
                    patrocinadores.add(patrocinador);
                }
            }
        }
        return patrocinadores;
    }

    private Patrocinador crearPatrocinador(Element patrocinadorElement) {

        String nombre = patrocinadorElement.getTextContent();
        float donacion = Float.parseFloat(patrocinadorElement.getAttribute("donacion"));
        String fechaStr = patrocinadorElement.getAttribute("fecha_inicio");
        LocalDate fechaInicio = LocalDate.parse(fechaStr);
        return new Patrocinador(nombre, donacion, fechaInicio);
    }

    public Equipo getEquipo(String codigo) {
        Element equipoElement = documentoXML.getElementById(codigo);
        return crearEquipo(equipoElement);
    }

    public void addPatrocinador(Patrocinador patrocinador) {

    }
}
