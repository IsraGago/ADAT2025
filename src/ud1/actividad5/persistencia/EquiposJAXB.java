package ud1.actividad5.persistencia;

import jakarta.xml.bind.JAXBException;
import ud1.actividad4.persistencia.ExcepcionXML;
import ud1.actividad5.clases.XMLjabxUtils;
import ud1.actividad5.clases.clasesEquipo.Equipos;

public class EquiposJAXB {
    public static Equipos leerEquipos(String rutaXML){
        try {
            return XMLjabxUtils.unmarshal(Equipos.class,rutaXML);
        } catch (JAXBException e) {
            throw new ExcepcionXML("Error al leer el XML de equipos.",e);
        }
    }
}
