package ud1.actividad4jaxb.persistencia;

public enum RutasFicheros {
    RUTA_XML_CORREDORES("./src/ud1/actividad5/archivos/corredores.xml"),
    RUTA_XML_EQUIPOS("./src/ud1/actividad5/archivos/equipos.xml"),
    RUTA_XML_SALIDA_DONACIONES("./src/ud1/actividad5/archivos/DonacionesTotales.xml"),
    RUTA_SALIDA_CORREDORES("./src/ud1/actividad5/archivos/corredores-dom.xml");

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

    // Uso en Main:
    // gestorEquipos.calcularDonacionTotalPorPatrocinadorCursor(
    //     RutasFicheros.RUTA_XML_EQUIPOS.getRuta(),
    //     RutasFicheros.RUTA_XML_SALIDA_DONACIONES.getRuta()
    // );
}
