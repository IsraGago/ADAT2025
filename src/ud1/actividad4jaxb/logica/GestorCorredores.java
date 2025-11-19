package ud1.actividad4jaxb.logica;


import ud1.actividad4jaxb.clases.clasesCorredor.Corredores;
import ud1.actividad4jaxb.persistencia.CorredoresJAXB;

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
