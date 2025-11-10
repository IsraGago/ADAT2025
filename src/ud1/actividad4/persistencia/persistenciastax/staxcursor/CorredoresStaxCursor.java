package ud1.actividad4.persistencia.persistenciastax.staxcursor;

import ud1.actividad4.clases.Corredor;
import ud1.actividad4.persistencia.ExcepcionXML;

import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CorredoresStaxCursor {
    // TODO
    public List<Corredor> leerCorredores(XMLStreamReader reader) throws ExcepcionXML {
        List<Corredor> corredores = new ArrayList<>();
        String contenidoActual = "";
        try {
            int tipo = reader.getEventType();
            switch (tipo){
                case XMLStreamReader.START_ELEMENT->{
                    String nombreEtiqueta = XMLStaxUtilsCursor.obtenerNombre(reader);
                    switch (nombreEtiqueta){

                    }
                }
                case XMLStreamReader.CHARACTERS->{
                    contenidoActual = XMLStaxUtilsCursor.leerTexto(reader);
                }
                case XMLStreamReader.END_ELEMENT->{}
            }


        }catch (Exception e){}
        return  corredores;
    }
}
