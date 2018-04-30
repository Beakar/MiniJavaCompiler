package terminal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.text.*;
import javax.swing.event.*;

public class GuiTerminal2 extends JFrame
        implements DocumentListener, Terminal, WindowListener, CaretListener {
    private JTextArea field;
    private JScrollPane fieldTop;
    private Document document;
    private Vector<Character> bufferedInput;
    private Vector<Character> newBufferedInput;
    private String oldText;
    private byte writtenChar;
    private boolean autoWriting;
    private boolean done;
    private static Font font = new Font("Monospaced",Font.PLAIN,14);
    
    public static SimpleAttributeSet aSet = new SimpleAttributeSet();
    
    public GuiTerminal2() {
        this(500,300);
    }
    public GuiTerminal2(int width, int height) {
//        setSize(width, height);
    	setBounds(525,0,width,height);
        setBackground(Color.white);
        setTitle("Terminal");
        Container top = getContentPane();
        document = new PlainDocument();
        ((AbstractDocument)document).setDocumentFilter(
            (DocumentFilter)(new CheckRemoveDocumentFilter()));
        field = new JTextArea(document);
        fieldTop = new JScrollPane(field);
        top.add(fieldTop);
        document.addDocumentListener(this);
        field.addCaretListener(this);
        bufferedInput = new Vector<Character>();
        newBufferedInput = new Vector<Character>();
        oldText = "";
        field.setFont(font);
        autoWriting = false;
        done = false;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void closeOnExit() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
        
    
    public int readByte() throws IOException {
        boolean firstTime = true;
        while (!done) {
            if (bufferedInput.isEmpty()) {
                if (firstTime) {
                    firstTime = false;
                    this.toFront();
                }
                try {
                    Thread.currentThread().sleep(100);
                }
                catch (InterruptedException ix) {
                    return -1;
                }
            }
            else {
                char rtnVal;
                synchronized(bufferedInput) {
                    rtnVal = bufferedInput.elementAt(0);
                    bufferedInput.removeElementAt(0);
                }
                return rtnVal;
            }
        }        
        return -1;
    }
    
    public void writeByte(byte b) throws IOException {
        writtenChar = b;
        autoWriting = true;
        try {
            document.insertString(document.getLength(), ""+(char)b, aSet);
        }
        catch (BadLocationException blx) {
        }
        autoWriting = false;
        field.setCaretPosition(document.getLength());
        toFront();
    }
    
    public void reset() {
        synchronized (bufferedInput) {
            bufferedInput.removeAllElements();
            newBufferedInput.removeAllElements();
        }
    }
    
    public static void main(String[] args) {
        GuiTerminal2 t = new GuiTerminal2();
        t.setVisible(true);
    }
    
    public void changedUpdate(DocumentEvent de) {
    }
    
    public void removeUpdate(DocumentEvent de) {
        if (!autoWriting) {
            synchronized (newBufferedInput) {
                if (!newBufferedInput.isEmpty()) {
                    newBufferedInput.removeElementAt(
                                newBufferedInput.size()-1);
                }
            }
        }
        int end = document.getLength();
        field.setCaretPosition(end);
    }
    
//     public void removeUpdate(DocumentEvent de) {
//         if (!autoWriting) {
//             synchronized (newBufferedInput) {
//                 oldText += "\b";
//                 autoWriting = true;
//                 setText(oldText);
//                 autoWriting = false;
//                 if (!newBufferedInput.isEmpty()) {
//                     newBufferedInput.add('\b');
//                 }
//             }
//         }
//         int end = document.getLength();
//         field.setCaretPosition(end);
//     }
    
    public void insertUpdate(DocumentEvent de) {
        int end = document.getLength();
        if (!autoWriting) {
            synchronized (newBufferedInput) {
                char newChar = getText().charAt(end-1);
                newBufferedInput.add(newChar);
                if (newChar == '\n') {
                    synchronized (bufferedInput) {
                        bufferedInput.addAll(newBufferedInput);
                    }
                    newBufferedInput.removeAllElements();
                }
            }
        }
        field.setCaretPosition(end);
    }
    
    public void caretUpdate(CaretEvent e) {
        int end = document.getLength();
        Caret caret = field.getCaret();
        if (caret.getDot() == end && caret.getMark() == end) {
            return;
        }
        else {
            caret.setDot(end);
        }
    }
    
    public void setText(String s) {
        try {
            document.remove(0, document.getLength());
            document.insertString(0, s, aSet);
        }
        catch (BadLocationException blx) {
        }
    }
    
    public String getText() {
        try {
            return document.getText(0, document.getLength());
        }
        catch (BadLocationException blx) {
            return "";
        }
    }
    
    public String removeCrOfCrlf(String str) {
        String rtnVal = "";
        for (int i = 0; i < str.length()-1; i++) {
            if (str.charAt(i) == '\r' && str.charAt(i+1) == '\n') {
            }
            else {
                rtnVal += str.charAt(i);
            }
        }
        if (str.length() > 0) {
            rtnVal += str.charAt(str.length()-1);
        }
        return rtnVal;
    }
    
    private int findSingleInsertion(String orig, String modded) {
        if (orig.length() + 1 != modded.length()) return -1;
        int firstDiff = -1;
        int idx;
        int bump = 0;
        for (idx = 0; idx < orig.length(); idx++) {
            if (orig.charAt(idx) != modded.charAt(idx+bump)) {
                if (firstDiff >= 0) return -1;
                bump = 1;
                firstDiff = idx;
            }
        }
        if (firstDiff == -1) {
            firstDiff = orig.length();
        }
        return firstDiff;
    }
    
  public void windowClosing(WindowEvent e) {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.dispose();
      System.exit(0);
  }

  public void windowOpened(WindowEvent e) {}
  public void windowClosed(WindowEvent e) {}
  public void windowIconified(WindowEvent e) {}
  public void windowDeiconified(WindowEvent e) {}
  public void windowActivated(WindowEvent e) {}
  public void windowDeactivated(WindowEvent e) {}
  
  
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
    
    
    public void setFontSize(int n) {
    	if (field != null) {
    		field.setFont(new Font("Monospaced",Font.PLAIN,Math.max(1,n)));
    	}
    }
    
    // only remove is something to remove
    class CheckRemoveDocumentFilter extends DocumentFilter {
        public CheckRemoveDocumentFilter() {
            super();
        }
        public void remove(DocumentFilter.FilterBypass fb,
                       int offset,
                       int length)
                throws BadLocationException {
             if (!newBufferedInput.isEmpty()) {
                 super.remove(fb, offset, length); 
             }
        }
    }
}

// inserts backspace character when attempt made to remove
class NoRemoveDocumentFilter extends DocumentFilter {
	public NoRemoveDocumentFilter() {
		super();
	}
	public void remove(DocumentFilter.FilterBypass fb,
			int offset,
			int length)
	throws BadLocationException {
		super.insertString(fb, offset+length, "\b", new SimpleAttributeSet()); 
	}
}
