package terminal;

import java.io.*;

public class ErrTerminal extends StdTerminal {
    public ErrTerminal() {
        super();
    }

    public void writeByte(byte b) throws IOException {
        System.err.write(b);
        System.err.flush();
    }
}
