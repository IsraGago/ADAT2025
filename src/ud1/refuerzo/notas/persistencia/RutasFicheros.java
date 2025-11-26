package ud1.refuerzo.notas.persistencia;

public enum RutasFicheros {
    RUTA_ALUMNOS("./src/ud1/refuerzo/notas/archivos/alumnos.dat"),
    RUTA_NOTAS_ALUMNOS("./src/ud1/refuerzo/notas/archivos/NotasAlumno.dat");

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
