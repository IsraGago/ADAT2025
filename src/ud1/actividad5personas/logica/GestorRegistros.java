package ud1.actividad5personas.logica;

import ud1.actividad5personas.clases.Registro;
import ud1.actividad5personas.persistencia.RegistrosJABX;
import ud1.actividad5personas.persistencia.RutasFicheros;

public class GestorRegistros {
    public static void leerRegistros(String ruta) {
        Registro registro = RegistrosJABX.leerRegistro(ruta);
        System.out.println(registro);
    }
}
