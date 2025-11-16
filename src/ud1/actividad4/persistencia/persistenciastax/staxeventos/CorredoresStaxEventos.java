package ud1.actividad4.persistencia.persistenciastax.staxeventos;

import ud1.actividad4.clases.Corredor;
import ud1.actividad4.clases.Fondista;
import ud1.actividad4.clases.Puntuacion;
import ud1.actividad4.clases.Velocista;
import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4.persistencia.TipoValidacion;
import ud1.actividad4.persistencia.persistenciastax.staxcursor.XMLStaxUtilsCursor;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CorredoresStaxEventos {

    private static List<Corredor> corredores;
    private static String contenidoActual = "";
    private static Corredor corredorActual = null;
    private static ArrayList<Puntuacion> historialActual = null;
    private static String anioActual = "";

    public static List<Corredor> leerCorredores(String rutaXmlCorredores, TipoValidacion validacion) throws ExcepcionXML {
        XMLEventReader reader = XMLStaxUtilsEventos.cargarDocumentos(rutaXmlCorredores, validacion);
        corredores = new ArrayList<>();
        try {
            while (reader.hasNext()) {
                XMLEvent evento = reader.nextEvent();

                if (evento.isStartElement()) {
                    startElement(evento);
                } else if (evento.isEndElement()) {
                    endElement(evento);
                } else if (evento.isCharacters()) {
                    contenidoActual = XMLStaxUtilsEventos.leerTexto(evento);
                }

            }
        } catch (Exception e) {
            throw new ExcepcionXML(e.getMessage());
        }
        return corredores;
    }

    public static List<Corredor> leerCorredoresPorEquipo(String rutaXmlCorredores, TipoValidacion validacion, String equipoBuscado) {
        XMLEventReader reader = XMLStaxUtilsEventos.cargarDocumentos(rutaXmlCorredores, validacion);

        corredores = new ArrayList<>();
        try {
            while (reader.hasNext()) {
                XMLEvent evento = reader.nextEvent();

                if (evento.isStartElement()) {
                    String nombreEtiqueta = XMLStaxUtilsEventos.obtenerNombreEtiqueta(evento);
                    switch (nombreEtiqueta) {
                        case "velocista", "fondista" -> {
                            String equipo = XMLStaxUtilsEventos.leerAtributo(evento, "equipo");

                            if (equipoBuscado.equalsIgnoreCase(equipo)) {
                                corredorActual = crearCorredor(nombreEtiqueta);
                                String codigo = XMLStaxUtilsEventos.leerAtributo(evento, "codigo");
                                String dorsal = XMLStaxUtilsEventos.leerAtributo(evento, "dorsal");
                                corredorActual.setCodigo(codigo);
                                corredorActual.setDorsal(Integer.parseInt(dorsal));
                                corredorActual.setEquipo(equipo);
                            } else {
                                corredorActual = null;
                            }

                        }
                        case "historial" -> {
                            historialActual = new ArrayList<>();
                        }
                        case "puntuacion" -> {
                            anioActual = XMLStaxUtilsEventos.leerAtributo(evento, "anio");
                        }
                    }
                } else if (evento.isEndElement()) {
                    endElement(evento);
                } else if (evento.isCharacters()) {
                    contenidoActual = XMLStaxUtilsEventos.leerTexto(evento);
                }

            }
        } catch (Exception e) {
            throw new ExcepcionXML(e.getMessage());
        }
        return corredores;
    }

    private static void endElement(XMLEvent event) {
        if (corredorActual != null) {
            String nombreEtiqueta = XMLStaxUtilsEventos.obtenerNombreEtiqueta(event).toLowerCase();
            switch (nombreEtiqueta) {
                case "velocista", "fondista" -> {
                    corredores.add(corredorActual);
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
                    historialActual.add(
                            new Puntuacion(Integer.parseInt(anioActual),
                                    Float.parseFloat(contenidoActual)));
                }
            }
        }
    }

    private static void startElement(XMLEvent event) {
        String nombreEtiqueta = XMLStaxUtilsEventos.obtenerNombreEtiqueta(event).toLowerCase();
        switch (nombreEtiqueta) {
            case "velocista", "fondista" -> {
                corredorActual = crearCorredor(nombreEtiqueta);
                String codigo = XMLStaxUtilsEventos.leerAtributo(event, "codigo");
                String dorsal = XMLStaxUtilsEventos.leerAtributo(event, "dorsal");
                String equipo = XMLStaxUtilsEventos.leerAtributo(event, "equipo");
                corredorActual.setCodigo(codigo);
                corredorActual.setDorsal(Integer.parseInt(dorsal));
                corredorActual.setEquipo(equipo);

            }
            case "historial" -> {
                historialActual = new ArrayList<>();
            }
            case "puntuacion" -> {
                anioActual = XMLStaxUtilsEventos.leerAtributo(event, "anio");
            }
        }
    }

    private static Corredor crearCorredor(String tipo) {
        if (tipo == null) {
            return null;
        }

        return tipo.equalsIgnoreCase("velocista") ? new Velocista() : new Fondista();
    }
}
