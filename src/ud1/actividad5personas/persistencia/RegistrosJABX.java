package ud1.actividad5personas.persistencia;

import jakarta.xml.bind.JAXBException;
import ud1.actividad5personas.excepciones.ExcepcionXML;
import ud1.actividad5personas.clases.Registro;

public class RegistrosJABX {
    public static Registro leerRegistro(String rutaXML) {
        try {
            return XMLjabxUtils.unmarshal(Registro.class, rutaXML);
        } catch (JAXBException e) {
            throw new ExcepcionXML("Error al leer el XML de registro.", e);
        }
    }
    public static void guardarRegistro(Registro registro, String rutaXML) {
        try {
            XMLjabxUtils.marshal(registro, rutaXML);
        } catch (JAXBException e) {
            throw new ExcepcionXML("Error al guardar el XML de registro.", e);
        }
    }
}
