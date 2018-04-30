package terminal;

import java.io.*;

public class StdTerminal implements Terminal {
    public StdTerminal()
    {
    }

    public void closeOnExit() {
    }
    
    public int readByte() throws IOException {
        return System.in.read();
    }
    public void writeByte(byte b) throws IOException {
        System.out.write(b);
        System.out.flush();
    }
    public void reset() {
    }
    public void println(String str) {
        if (str == null) str = "(null)";
        try {
            int len = str.length();
            for (int i = 0; i < len; i++) {
                writeByte((byte)str.charAt(i));
            }
            writeByte((byte)'\n');
        }
        catch (IOException iox) {
        }
    }
}
