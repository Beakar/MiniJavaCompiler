package terminal;

import java.io.*;

public class StreamFilterTerminal implements Terminal {
    private InputStream is;
    private PrintStream os;
    private Terminal baseTerminal;
    
    StreamFilterTerminal(InputStream in, PrintStream out, Terminal base) {
        is = in;
        os = out;
        baseTerminal = base;
    }
        
    public int readByte() throws IOException {
        if (is == null) {
            if (baseTerminal != null) {
                return baseTerminal.readByte();
            }
            else {
                return -1;
            }
        }
        else {
            return is.read();
        }
    }
    
    public void writeByte(byte b) throws IOException {
        if (os == null) {
            if (baseTerminal != null) {
                baseTerminal.writeByte(b);
            }
        }
        else {
            os.write(b);
            os.flush();
        }
    }
            
    public void reset() {
        if (baseTerminal != null) {
            baseTerminal.reset();
        }
    }
    
    public void println(String str) {
        if (os == null) {
            if (baseTerminal != null) {
                baseTerminal.println(str);
            }
        }
        else {
            os.println(str);
            os.flush();
        }
    }
        
    public void closeOnExit() {
        if (baseTerminal != null) {
            baseTerminal.closeOnExit();
        }
    }

}
