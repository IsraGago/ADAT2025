package ud1.actividad4.persistencia.persistenciasax;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ud1.actividad4.clases.Equipo;
import ud1.actividad4.clases.Patrocinador;
import ud1.actividad4.persistencia.persistenciadom.XMLDOMUtils;

public class ActualizacionesSAXHandler extends DefaultHandler  {
    private ArrayList<Patrocinador> patrocinadores = new ArrayList<>();

    public ActualizacionesSAXHandler(Document documentoDOMequipos, String rutaSalida) {
        this.documentoDOMequipos = documentoDOMequipos;
        this.rutaSalida = rutaSalida;
    }

    private final Document documentoDOMequipos;
    private Equipo equipoActual;
    private Patrocinador patrocinadorActual;
    private String contenidoActual = "";
    private final String rutaSalida;
    private String fechaDonacion;

    public ArrayList<Patrocinador> getPatrocinadores() {
        return patrocinadores;
    }

    @Override
    public void endDocument() throws SAXException {
        // TODO Auto-generated method stub
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // SI NO VALIDAS: LOCALNAME VIENE EN BLANCO
        contenidoActual = "";
        
        switch (qName.toLowerCase()) {
            case "patrocinador"->{
                equipoActual = new Equipo();
                equipoActual.setIdEquipo(attributes.getValue("idEquipo"));
                equipoActual.setNombre(attributes.getValue("nombreEquipo"));
                patrocinadorActual = new Patrocinador();
            }
            case "donacion"->{
                fechaDonacion = attributes.getValue("fecha");
            }
            default->{}
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        contenidoActual = String.valueOf(ch, start, length).trim();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName.toLowerCase()) {
            case "patrocinador"->{
                // patrocinadores.add(patrocinadorActual);
                try {
                    actualizar();
                } catch (Exception e) {
                    throw new RuntimeException("Error al actualizar el equipo: " + e.getMessage());
                }
            }
            case "nombre"->{
                patrocinadorActual.setNombre(contenidoActual);
            }
            case "donacion"->{
                patrocinadorActual.setDonacion(Float.parseFloat(contenidoActual));
                patrocinadorActual.setFechaInicio(Patrocinador.longToFecha(Long.parseLong(fechaDonacion)));
            }
            default->{}
        }
    }

    private void actualizar() {
        Element equipoDom = XMLDOMUtils.buscarElementoPorID(documentoDOMequipos, equipoActual.getIdEquipo());
        if (equipoDom == null) {
            equipoDom = XMLDOMUtils.addElement(documentoDOMequipos, "equipo", "", documentoDOMequipos.getDocumentElement());
            XMLDOMUtils.addAtributoId(documentoDOMequipos, "idEquipo", equipoActual.getIdEquipo(), equipoDom);
            XMLDOMUtils.addElement(documentoDOMequipos, "nombre", equipoActual.getNombre(), equipoDom);
            // XMLDOMUtils.addElement(documentoDOMequipos, "patrocinadores","",equipoDom);
        }

        Element patrocinadoresElement = (Element) equipoDom.getElementsByTagName("patrocinadores");
        if (patrocinadoresElement == null) {
            patrocinadoresElement = XMLDOMUtils.addElement(documentoDOMequipos, "patrocinadores","",equipoDom);
        }

        // COMPROBAR SI EL PATROCINADOR EXISTE
        NodeList patrocinadoresNodeList = patrocinadoresElement.getElementsByTagName("patrocinador");
        Node patrocinador = null;
        boolean existe = false;
        for (int i = 0; i < patrocinadoresNodeList.getLength(); i++) {
            patrocinador = patrocinadoresNodeList.item(i);
            existe = patrocinador.getTextContent().equalsIgnoreCase(equipoActual.getNombre());
            if (existe) {
                break;
            }
        }

        // SI EXISTE LO MODIFICAS
        if (existe) {
            XMLDOMUtils.modificarAtributo((Element) patrocinador, "donacion" ,String.valueOf(patrocinadorActual.getDonacion()));
            XMLDOMUtils.modificarAtributo((Element) patrocinador, "fecha_inicio" ,String.valueOf(patrocinadorActual.getFechaInicio().toString()));
        } else{
        // SI NO EXISTE LO AÑADES
            Element nuevo = XMLDOMUtils.addElement(documentoDOMequipos, "patrocinador", patrocinadorActual.getNombre(), patrocinadoresElement);
            XMLDOMUtils.addAtributo(documentoDOMequipos, "donacion", String.valueOf(patrocinadorActual.getDonacion()), nuevo);
            XMLDOMUtils.addAtributo(documentoDOMequipos, "fecha_inicio", String.valueOf(patrocinadorActual.getFechaInicio().toString()), nuevo);

            int total = patrocinadoresElement.getElementsByTagName("patrocinador").getLength();
            XMLDOMUtils.addAtributo(documentoDOMequipos, "numPatrocinadores", Integer.toString(total), patrocinadoresElement);
        }
    }

}
