package ud1.actividad4.persistencia.persistenciastax.staxeventos;

import ud1.actividad4.persistencia.TipoValidacion;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class EquipoStaxEventos {


    public static Map<String, Double> generarDonacionesTotales(String rutaEntrada, String rutaSalida) {
        XMLEventReader reader = null;
        Map<String, Double> mapaDonaciones = new HashMap<String, Double>();
        try {
            reader = XMLStaxUtilsEventos.cargarDocumentos(rutaEntrada, TipoValidacion.NO_VALIDAR);
            mapaDonaciones = new TreeMap<>();

            StringBuilder nombrePatrocinador = new StringBuilder();
            double donacionActual = 0.0;
            boolean esPatrocinador = false;

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    String nombreEtiqueta = XMLStaxUtilsEventos.obtenerNombreEtiqueta(event);
                    if (nombreEtiqueta.equals("patrocinador")) {
                        nombrePatrocinador = new StringBuilder();
                        donacionActual = Double.parseDouble(XMLStaxUtilsEventos.leerAtributo(event, "donacion"));
                        esPatrocinador = true;
                    }
                } else if (event.isEndElement()) {
                    if (esPatrocinador) {
                        nombrePatrocinador.append(XMLStaxUtilsEventos.leerTexto(event));
                    }
                } else if (event.isCharacters()) {
                    String nombreEtiqueta = XMLStaxUtilsEventos.obtenerNombreEtiqueta(event);
                    if (nombreEtiqueta.equals("patrocinador")) {
                        esPatrocinador = false;
                        nombrePatrocinador = new StringBuilder(nombrePatrocinador.toString().trim());
                        mapaDonaciones.merge(nombrePatrocinador.toString(), donacionActual, Double::sum);
                        // si la clave no existe inserta la clave y el valor
                        // si existe la clave, aplica la función (Double::sum) en el valor
                        // donacionActual = 0.0;
                    }
                }

            }
            return mapaDonaciones;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void escribirDonacionesTotales(String rutaSalida,Map<String, Double> mapaDonaciones) {
        XMLEventWriter writer = null;
        try {
            writer = XMLStaxUtilsEventos.crearWriterStax(rutaSalida);
            XMLEventFactory fabrica = XMLStaxUtilsEventos.crearFactoryEventos();

            XMLStaxUtilsEventos.addDeclaracionXML(writer,fabrica);
            XMLStaxUtilsEventos.addSaltoDelinea(writer,0,fabrica);
            XMLStaxUtilsEventos.addStartElemento(writer,"donaciones",fabrica);

            for (Map.Entry<String, Double> entrada : mapaDonaciones.entrySet()) {
                String nombre = entrada.getKey();
                String total = String.format("%.1f", entrada.getValue());
                XMLStaxUtilsEventos.addSaltoDelinea(writer,1,fabrica);
                XMLStaxUtilsEventos.addStartElemento(writer,"Patrocinador",fabrica);
                XMLStaxUtilsEventos.addAtributo(writer,fabrica,"totalDonado",total);

            }

        }catch (Exception e){}
    }
}

