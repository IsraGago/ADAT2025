package ud1.actividad5personas.persistencia;

public enum RutasFicheros {
    RUTA_XML_PRUEBA("./src/ud1/actividad5personas/archivos/RegistrosPrueba.xml"),
    RUTA_XML_SALIDA("./src/ud1/actividad5personas/archivos/RegistrosSalida.xml");

    private final String ruta;

    RutasFicheros(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }

    @Override
    public String toString() {
        return ruta;
    }
}
