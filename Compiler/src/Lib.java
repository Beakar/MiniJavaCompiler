import java.io.*;

/**
 * @author Steven R. Vegdahl
 * @version 30 December 2008
 *
 * The standard MiniJava library class
 */
public class Lib {

	// keeps track of whether there is an unused buffered character
	// that has been read:
	// -2 => no character
	// -1 => have hit end of file
	// 0 or greater => the buffered character
	private static int buffer = -2;
	
	// reads a line of text from standard input, returning the String
	// read (not including the newline character), or null if at
	// end of file.  Handles both Unix/Linux, Mac and PC end-of-file
	// conventions.
    public String readLine() {
    	
    	// create buffer for building the line
    	StringBuffer buf = new StringBuffer();
    	
    	// read first character, returning null on EOF
    	int ch = readLogicalChar();
    	if (ch == -1) return null;
    	
    	// continually read characters until end of file is reached
    	for (;;) {
    		if (ch == '\r') {
    			// "return" character: if followed by newline, treat
    			// the both end of line
    			ch = readLogicalChar();
    			if (ch != '\n') {
    				putBack(ch);
    			}
    			return buf.toString();
    		}
    		if (ch == '\n' || ch == -1) {
    			// hit end of line or end of file: return line
    			return buf.toString();
    		}
    		else {
    			// hit normal character: buffer it up
    			buf.append((char)ch);
    		}
    		
    		// read next character, in preparation for next loop
    		ch = readLogicalChar();
    	}
    }
	
    // read the next unprocessed character from standard input
    public int readChar() {
    	return readLogicalChar();
    }
    
    // read an integer (base 10) from standard input.  Gives message
    // if not a well-formed integer.  Skips any initial whitespace
    // if finds
    public int readInt() {
    	
    	// create buffer to hold the int's characters
    	StringBuffer buf = new StringBuffer();
    	
    	// skip whitespace
    	skipWhite();
    	
    	// read next unprocessed character
    	int ch = readLogicalChar();
    	
    	// process a '-' sign if present
    	if (ch == '-') {
    		buf.append('-');
    		ch = readLogicalChar();
    	}
    	
    	// continue reading characters until we run out of digits
    	while (Character.isDigit((char)ch)) {
    		buf.append((char)ch);
    		ch = readLogicalChar();
    	}
    	
    	// push back the non-digit character that we did not use
    	putBack(ch);
    	
    	// convert string to integer, giving integer and returning
    	// -1 if not well-formed
    	try {
    		return Integer.parseInt(buf.toString());
    	}
    	catch (NumberFormatException nfx) {
    		// give message and abort if ill-formed number
    		exitError("attempt to read badly formed integer");
    		return -1;
    	}
    }
    
    // print a string to standard output
    public void printStr(String s) {
    	System.out.print(s);
    }
    
    // print an integer to standard output
    public void printInt(int n) {
    	System.out.print(n);
    }
    
    // print a boolean to standard output
    public void printBool(boolean b) {
    	System.out.print(b ? "true" : "false");
    }
    
    // convert an int to a base-10 string
    public String intToString(int n) {
    	return ""+n;
    }
    
    // convert an int to a one-character string denoted by its
    // ASCII encoding
    public String intToChar(int n) {
    	return ""+(char)(n&0xff);
    }
    
    // helper method to read a character, taking into account that
    // there may be an already-read character in the buffer
    private static int readLogicalChar() {
    	
    	if (buffer != -2) {
    		// if character in buffer, return it and set buffer to empty
    		int rtnVal = buffer;
    		buffer = -2;
    		return rtnVal;
    	}
    	else {
    		// if no character in buffer, return it (or return -1 if EOF
    		try {
    			return System.in.read();
    		}
    		catch (EOFException eofx) {
    			return -1;
    		}
    		catch (IOException iox) {
    			// give message and abort if I/O error
    			exitError("IO exception reading standard input");
    			return -1; // dummied up
    		}
    	}
    }
    
    // helper method to skip whitespace
    private static void skipWhite() {
    	
    	// repeat the skip as long as there is whitespace
    	for (;;) {
    		// read character
    		int ch = readLogicalChar();
    		
    		if (ch == ' ' | ch == '\n' || ch == '\t' ||
    				ch == '\r' || ch == '\f') {
        		// whitespace, just ignore
    		}
    		else {
    			// not whitespace: put back so that someone else
    			// will find it
    			putBack(ch);
    			return;
    		}
    	}
    }
    
    // helper method to push a character back into the input buffer
    private static void putBack(int n) {
    	buffer = n;
    }
    
    // helper method to print error message and abort
    private static void exitError(String msg) {
    	System.out.print("ERROR: "+msg+"\n");
    	System.out.print("Program terminated.\n");
    	System.exit(1);
    }
}