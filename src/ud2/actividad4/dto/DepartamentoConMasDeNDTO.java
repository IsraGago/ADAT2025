package ud2.actividad4.dto;

public class DepartamentoConMasDeNDTO {
    int numDepartamento;
    int numEpleados;

    public DepartamentoConMasDeNDTO(int numDepartamento, int numEpleados) {
        this.numDepartamento = numDepartamento;
        this.numEpleados = numEpleados;
    }

    @Override
    public String toString() {
        return "DepartamentoConMasDeNDTO{" +
                "numDepartamento=" + numDepartamento +
                ", numEpleados=" + numEpleados +
                '}';
    }
}
