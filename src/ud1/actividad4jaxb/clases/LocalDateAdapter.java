package ud1.actividad4jaxb.clases;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        if (v == null || v.isEmpty()) {
            return null;
        }
        return LocalDate.parse(v, formatter);
    }

    @Override
    public String marshal(LocalDate v) {
        if (v == null){
            return null;
        }
        return v.format(formatter);
    }
}
