package ud1.refuerzo.notas.clases;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class NotaAlumno implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;
    private int numAlumno;
    private ArrayList<NotaModulo> notas;

    public ArrayList<NotaModulo> getNotas() {
        return notas;
    }

    public void setNotas(ArrayList<NotaModulo> notas) {
        this.notas = notas;
    }

    public int getNumAlumno() {
        return numAlumno;
    }

    public void setNumAlumno(int numAlumno) {
        this.numAlumno = numAlumno;
    }
}
