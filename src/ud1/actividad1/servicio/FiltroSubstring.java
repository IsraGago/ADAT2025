package ud1.actividad1.servicio;

import java.io.File;
import java.io.FilenameFilter;

public class FiltroSubstring implements FilenameFilter {
    private final String substring;
    public FiltroSubstring(String substring) {
        this.substring = substring;
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.contains(substring);
    }
}
