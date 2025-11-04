package ud1.actividad4.persistencia.persistenciasax;

import java.time.LocalDate;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ud1.actividad4.logica.GestorEquipos;
import ud1.actividad4.clases.Corredor;
import ud1.actividad4.clases.Fondista;
import ud1.actividad4.clases.Puntuacion;
import ud1.actividad4.clases.Velocista;

public class CorredorNombreEquipoHandler extends DefaultHandler {
    private final ArrayList<Corredor> corredores = new ArrayList<>();
    private Corredor corredorActual;
    private ArrayList<Puntuacion> historialActual;
    private String contenidoActual = "";
    private String anioActual;
    private GestorEquipos gestor;
    private String nombreEquipoBuscar;

    public CorredorNombreEquipoHandler(String nombreEquipoBuscar,GestorEquipos gestor) {
        this.gestor = gestor;
        this.nombreEquipoBuscar = nombreEquipoBuscar;
    }

    public ArrayList<Corredor> getCorredores() {
        return corredores;
    }

    @Override
    public void endDocument() throws SAXException {
        for (Corredor corredor : corredores) {
            corredor.mostrarInformacion();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // en los elementos tienes sus atributos
        contenidoActual = "";
        switch (qName.toLowerCase()) {
            case "velocista", "fondista" -> {
                corredorActual = crearCorredor(qName);
                asignarAtributosCorredor(attributes);
            }

            case "historial" -> {
                historialActual = new ArrayList<>();
            }

            case "puntuacion" -> {
                anioActual = attributes.getValue("anio");
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        contenidoActual = String.valueOf(ch, start, length).trim();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (corredorActual == null) {
            return;
        }

        switch (qName.toLowerCase()) {
            case "velocista", "fondista" -> {
                String nombreEquipo = gestor.getNombreEquipo(corredorActual.getEquipo());
                if (nombreEquipo != null && nombreEquipo.equalsIgnoreCase(nombreEquipoBuscar)) {
                    corredores.add(corredorActual);
                }
            }

            case "historial" -> {
                corredorActual.setHistorial(historialActual);
            }
            case "nombre" -> {
                corredorActual.setNombre(contenidoActual);
            }
            case "fecha_nacimiento" -> {
                corredorActual.setFechaNacimiento(LocalDate.parse(contenidoActual));
            }
            case "velocidad_media" -> {
                if (corredorActual instanceof Velocista v) {
                    v.setVelocidadMedia(Float.parseFloat(contenidoActual));
                }
            }
            case "distancia_max" -> {
                if (corredorActual instanceof Fondista f) {
                    f.setDistanciaMax(Float.parseFloat(contenidoActual));
                }
            }
            case "puntuacion" -> {
                historialActual.add(new Puntuacion(Integer.parseInt(anioActual), Float.parseFloat(contenidoActual)));
            }
        }
    }

    private void asignarAtributosCorredor(Attributes atributos) {
        corredorActual.setCodigo(atributos.getValue("codigo"));
        corredorActual.setDorsal(Integer.parseInt(atributos.getValue("dorsal")));
        corredorActual.setEquipo(atributos.getValue("equipo"));
    }

    private Corredor crearCorredor(String tipo) {
        if (tipo == null) {
            return null;
        }

        return tipo.equalsIgnoreCase("velocista") ? new Velocista() : new Fondista();
    }
}
