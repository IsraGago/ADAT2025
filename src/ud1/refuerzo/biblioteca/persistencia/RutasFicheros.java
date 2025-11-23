package ud1.refuerzo.biblioteca.persistencia;

public enum RutasFicheros {
    RUTA_XML_BIBLIOTECA("./src/ud1/refuerzo/biblioteca/archivos/BibliotecaInformatica.xml"),
    RUTA_XML_CAMBIOS("./src/ud1/refuerzo/biblioteca/archivos/BibliotecaInformatica-cambios.xml");

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
