package terminal;

import java.io.IOException;
import java.io.OutputStream;

public class TerminalOutputStream extends OutputStream {
	private Terminal term;
	public TerminalOutputStream(Terminal t) {
		term = t;
	}
	
	public void write(int b) {
		try {
			term.writeByte((byte)b);
		}
		catch (IOException iox) {
		}
	}
}
