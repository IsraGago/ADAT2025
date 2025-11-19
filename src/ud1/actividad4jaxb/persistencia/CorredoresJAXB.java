package ud1.actividad4jaxb.persistencia;

import jakarta.xml.bind.JAXBException;
import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4jaxb.clases.XMLjabxUtils;
import ud1.actividad4jaxb.clases.clasesCorredor.Corredores;

public class CorredoresJAXB {
    public static Corredores leerCorredores(String rutaXML) {
        try {
            return XMLjabxUtils.unmarshal(Corredores.class, rutaXML);
        } catch (JAXBException e) {
            throw new ExcepcionXML("Error al leer el XML de equipos.", e);
        }
    }

    public static void escribirCorredores(Corredores corredores, String rutaXML) {
        try {

        } catch (ExcepcionXML ex) {

        }
    }

}
