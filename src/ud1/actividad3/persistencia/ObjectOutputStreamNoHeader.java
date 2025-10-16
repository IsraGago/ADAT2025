package ud1.actividad3.persistencia;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectOutputStreamNoHeader extends ObjectOutputStream {
    public ObjectOutputStreamNoHeader(OutputStream out) throws IOException {
        super(out);
    }

    protected ObjectOutputStreamNoHeader() throws IOException, SecurityException {
        super();
    }

    @Override
    protected void writeStreamHeader() throws IOException {
    }
}
