package ud1.actividad4jaxb.persistencia;

import jakarta.xml.bind.JAXBException;
import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad4jaxb.clases.XMLjabxUtils;
import ud1.actividad4jaxb.clases.clasesEquipo.Equipos;

public class EquiposJAXB {
    public static Equipos leerEquipos(String rutaXML){
        try {
            return XMLjabxUtils.unmarshal(Equipos.class,rutaXML);
        } catch (JAXBException e) {
            throw new ExcepcionXML("Error al leer el XML de equipos.",e);
        }
    }
}
