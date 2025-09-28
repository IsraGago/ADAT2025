package ud1.actividad2;

public class Operaciones {
    // NO PUEDE HABER NINGUNA LINEA QUE TRABAJE DIRECTAMENTE CON LOS FICHEROS

    public static int contarLineas(String ruta) {
        int contador = -1;
        try {
            LecturaTexto lectura = new LecturaTexto(ruta);
            lectura.abrirArchivo();
            String linea = "";
            contador = 0;
            while (linea != null) {
                linea = lectura.leerLinea();
                if (linea != null) {
                    contador++;
                }
            }
            return contador;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return contador;
    }

    public static void escribirLineas(String[] lineas, String ruta){
        EscribirTexto escritura = new EscribirTexto(ruta);
        escritura.abrirArchivo();
        for (String linea : lineas) {
            escritura.escribirLinea(linea);
        }
        escritura.cerrarArchivo();
    }
}
