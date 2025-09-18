package ud1.actividad1.servicio;

import java.io.File;
import java.io.FilenameFilter;

public class FiltroExtension implements FilenameFilter {
    String extension;
    public FiltroExtension(String extension) {
        if (extension.startsWith(".")) {
            this.extension = extension;
        } else {
            this.extension = "." + extension;
        }
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(extension.toLowerCase());
    }
}
