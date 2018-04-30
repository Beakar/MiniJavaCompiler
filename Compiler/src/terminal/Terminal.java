package terminal;

import java.io.*;

public interface Terminal {
    public int readByte() throws IOException;
    public void writeByte(byte b) throws IOException;
    public void reset();
    public void println(String str);
    public void closeOnExit();
}


