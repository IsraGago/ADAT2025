package ud1.actividad5.logica;


import ud1.actividad5.clases.clasesCorredor.Corredores;
import ud1.actividad5.persistencia.CorredoresJAXB;

public class GestorCorredores {
    public void mostrarTodosLosCorredoresJAXB(String ruta) {
        Corredores corredores = CorredoresJAXB.leerCorredores(ruta);
        if (corredores == null) {
            System.out.println("no hay corredores para mostrar");
            return;
        }
        System.out.println(corredores);
    }

    public void asd(){

    }
}
