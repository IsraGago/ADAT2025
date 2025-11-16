package ud1.actividad4.persistencia.persistenciastax.staxcursor;

import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4.persistencia.TipoValidacion;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.util.Map;
import java.util.TreeMap;

public class EquipoStaxCursor {
    public static Map<String,Double> calcularDonaciones(String rutaEntrada) throws ExcepcionXML{
        XMLStreamReader reader = null;
        try {
            reader = XMLStaxUtilsCursor.cargarDocumentos(rutaEntrada, TipoValidacion.NO_VALIDAR);
            Map<String,Double> mapaDonaciones = new TreeMap<>();

            StringBuilder nombrePatrocinador = new StringBuilder();
            double donacionActual = 0.0;
            boolean esPatrocinador = false;

            while(reader.hasNext()){
                int tipo = reader.next();

                switch(tipo){

                    case XMLStreamConstants.START_ELEMENT ->{
                        String nombreEtiqueta = XMLStaxUtilsCursor.obtenerNombreEtiqueta(reader);
                        if (nombreEtiqueta.equals("patrocinador")) {
                            nombrePatrocinador = new StringBuilder();
                            donacionActual = Double.parseDouble(XMLStaxUtilsCursor.leerAtributo(reader, "donacion"));
                            esPatrocinador = true;
                        }
                    }
                    case XMLStreamConstants.CHARACTERS ->{
                        if (esPatrocinador) {
                            nombrePatrocinador.append(XMLStaxUtilsCursor.leerTexto(reader));
                        }
                    }
                    case XMLStreamConstants.END_ELEMENT ->{
                        String nombreEtiqueta = XMLStaxUtilsCursor.obtenerNombreEtiqueta(reader);
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
            }
            return mapaDonaciones;
        } catch (Exception e){
            e.printStackTrace();
        }
        if (reader != null){
            try {
                reader.close();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void escribirDonacionesTotales(String rutaSalida,Map<String,Double> mapaDonaciones){
        XMLStreamWriter writer = null;
        try{
            writer = XMLStaxUtilsCursor.crearWriterStax(rutaSalida);
            XMLStaxUtilsCursor.addDeclaracionXML(writer);
            XMLStaxUtilsCursor.addSaltoDeLinea(writer,0);
            XMLStaxUtilsCursor.addStartElemento(writer,"donaciones");

            for (Map.Entry<String,Double> entrada : mapaDonaciones.entrySet()){
                String nombre = entrada.getKey();
                String total = String.format("%.1f",entrada.getValue());

                XMLStaxUtilsCursor.addSaltoDeLinea(writer,1);
                XMLStaxUtilsCursor.addStartElemento(writer,"Patrocinador");
                XMLStaxUtilsCursor.addAtributo(writer,"totalDonado",total);
                XMLStaxUtilsCursor.addTextoElemento(writer,nombre);
                XMLStaxUtilsCursor.addEndElemento(writer);
            }

            XMLStaxUtilsCursor.addSaltoDeLinea(writer,0);
            XMLStaxUtilsCursor.addEndElemento(writer);

            XMLStaxUtilsCursor.addEndDocumento(writer);
        }catch(Exception e){
            throw new ExcepcionXML("Error inesperado al generar el XML",e);
        }

        if (writer != null){
            try {
                writer.close();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
    }
}
