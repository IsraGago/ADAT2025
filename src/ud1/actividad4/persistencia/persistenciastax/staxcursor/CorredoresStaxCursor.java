package ud1.actividad4.persistencia.persistenciastax.staxcursor;

import ud1.actividad4.clases.Corredor;
import ud1.actividad4.clases.Fondista;
import ud1.actividad4.clases.Puntuacion;
import ud1.actividad4.clases.Velocista;
import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4.persistencia.TipoValidacion;

import javax.xml.stream.XMLStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CorredoresStaxCursor {
    private static List<Corredor> corredores = new ArrayList<>();
    private static String contenidoActual = "";
    private static Corredor corredorActual = null;
    private static ArrayList<Puntuacion> historialActual = null;
    private static String anioActual = "";

    public static List<Corredor> leerCorredores(String rutaXmlCorredores, TipoValidacion validacion) throws ExcepcionXML {
        XMLStreamReader reader = XMLStaxUtilsCursor.cargarDocumentos(rutaXmlCorredores, validacion);

        try {
            while (reader.hasNext()) {

                int tipo = reader.next();

                switch (tipo) {
                    case XMLStreamReader.START_ELEMENT -> {
                        startElement(reader);
                    }
                    case XMLStreamReader.CHARACTERS -> {
                        // contenidoActual += XMLStaxUtilsCursor.leerTexto(reader);
                        contenidoActual = XMLStaxUtilsCursor.leerTexto(reader);
                    }
                    case XMLStreamReader.END_ELEMENT -> {
                        endElement(reader);
                    }

                }
            }

        } catch (Exception e) {
        }
        return corredores;
    }


    public static List<Corredor> leerCorredoresPorEquipo(XMLStreamReader reader, String equipoBuscado) {
        corredores = new ArrayList<>();

        try {
            while (reader.hasNext()) {

                int tipo = reader.next();

                switch (tipo) {
                    case XMLStreamReader.START_ELEMENT -> {
                        String nombreEtiqueta = XMLStaxUtilsCursor.obtenerNombreEtiqueta(reader).toLowerCase();
                        switch (nombreEtiqueta) {
                            case "velocista", "fondista" -> {
                                String equipo = XMLStaxUtilsCursor.leerAtributo(reader, "equipo");

                                if (equipoBuscado.equalsIgnoreCase(equipo)) {
                                    corredorActual = crearCorredor(nombreEtiqueta);
                                    String codigo = XMLStaxUtilsCursor.leerAtributo(reader, "codigo");
                                    String dorsal = XMLStaxUtilsCursor.leerAtributo(reader, "dorsal");
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
                                anioActual = XMLStaxUtilsCursor.leerAtributo(reader, "anio");
                            }
                        }
                    }
                    case XMLStreamReader.CHARACTERS -> {
                        // contenidoActual += XMLStaxUtilsCursor.leerTexto(reader);
                        contenidoActual = XMLStaxUtilsCursor.leerTexto(reader);
                    }
                    case XMLStreamReader.END_ELEMENT -> {
                        // TODO: HAY UN PROBLEMA AQUI, PARECE QUE NO LLEGAN LAS ETIQUETAS DE FINAL DE CORREDOR
                        endElement(reader);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return corredores;
    }

    private static void endElement(XMLStreamReader reader) {
        if (corredorActual != null) {
            String nombreEtiqueta = XMLStaxUtilsCursor.obtenerNombreEtiqueta(reader).toLowerCase();
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

    private static void startElement(XMLStreamReader reader) {
        String nombreEtiqueta = XMLStaxUtilsCursor.obtenerNombreEtiqueta(reader).toLowerCase();
        switch (nombreEtiqueta) {
            case "velocista", "fondista" -> {
                corredorActual = crearCorredor(nombreEtiqueta);
                String codigo = XMLStaxUtilsCursor.leerAtributo(reader, "codigo");
                String dorsal = XMLStaxUtilsCursor.leerAtributo(reader, "dorsal");
                String equipo = XMLStaxUtilsCursor.leerAtributo(reader, "equipo");
                corredorActual.setCodigo(codigo);
                corredorActual.setDorsal(Integer.parseInt(dorsal));
                corredorActual.setEquipo(equipo);

            }
            case "historial" -> {
                historialActual = new ArrayList<>();
            }
            case "puntuacion" -> {
                anioActual = XMLStaxUtilsCursor.leerAtributo(reader, "anio");
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
