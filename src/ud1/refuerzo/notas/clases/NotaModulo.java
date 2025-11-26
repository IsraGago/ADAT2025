package ud1.refuerzo.notas.clases;

import java.io.Serial;
import java.io.Serializable;

public class NotaModulo implements Serializable {
    @Serial
    private static final long serialVersionUID = 43L;
    private String asignatura;
    private Double nota;

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}