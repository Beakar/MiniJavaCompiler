package terminal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.text.*;
import javax.swing.event.*;

public class GuiTerminalRaw extends JFrame
        implements DocumentListener, Terminal, WindowListener, CaretListener {
    private JTextArea field;
    private JScrollPane fieldTop;
    private Document document;
    int currentChar;
//     private String oldText;
//     private byte writtenChar;
    private boolean autoWriting;
//     private boolean done;
    private static Font font = new Font("Monospaced",Font.PLAIN,12);
    
    public static SimpleAttributeSet aSet = new SimpleAttributeSet();
    
    public GuiTerminalRaw() {
        this(300,500);
    }
    public GuiTerminalRaw(int width, int height) {
        setSize(width, height);
        setBackground(Color.white);
        setTitle("Raw terminal");
        Container top = getContentPane();
        document = new PlainDocument();
        try {
            document.insertString(0, "\n ", aSet);
        }
        catch (BadLocationException blx) {
        }
        ((AbstractDocument)document).setDocumentFilter(
            (DocumentFilter)(new RawDocumentFilter()));
        field = new JTextArea(document);
        field.addKeyListener(new MyKeyListener());
        field.getCaret().setDot(1);
        fieldTop = new JScrollPane(field);
        top.add(fieldTop);
        document.addDocumentListener(this);
        field.addCaretListener(this);
//         oldText = "";
        field.setFont(font);
        autoWriting = false;
//         done = false;
        reset();
//         try {
//             this.writeByte((byte)'\n');
//         }
//         catch(IOException iox) {
//         }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void closeOnExit() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
        
    
    public int readByte() throws IOException {
        synchronized(this) {
            int temp = currentChar;
            currentChar = -1;
            return temp;
        }
    }
    
    public void writeByte(byte b) throws IOException {
//         currentChar = b;
        autoWriting = true;
        try {
            document.insertString(document.getLength(), ""+(char)b, aSet);
        }
        catch (BadLocationException blx) {
        }
        autoWriting = false;
        field.setCaretPosition(document.getLength()-1);
        toFront();
    }
    
    public void reset() {
        synchronized (this) {
            currentChar = -1;
        }
    }
    
    public static void main(String[] args) {
        GuiTerminalRaw t = new GuiTerminalRaw();
        t.setVisible(true);
        for (int i = 0; i < 3; i++) {
            try {
                for (;;) {
                    int val = t.readByte();
                    if (val >= 0) {
                        System.out.println("val: "+val);
                        t.writeByte((byte)(val+1));
                        break;
                    }
                }
            }
            catch (IOException iox) {
                System.out.println("exception");
            }
        }
    }
    
    public void changedUpdate(DocumentEvent de) {
    }
    
    public void removeUpdate(DocumentEvent de) {
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
    }
    
    public void caretUpdate(CaretEvent e) {
        int end = document.getLength()-1;
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
    
//     public String removeCrOfCrlf(String str) {
//         String rtnVal = "";
//         for (int i = 0; i < str.length()-1; i++) {
//             if (str.charAt(i) == '\r' && str.charAt(i+1) == '\n') {
//             }
//             else {
//                 rtnVal += str.charAt(i);
//             }
//         }
//         if (str.length() > 0) {
//             rtnVal += str.charAt(str.length()-1);
//         }
//         return rtnVal;
//     }
//     
//     private int findSingleInsertion(String orig, String modded) {
//         if (orig.length() + 1 != modded.length()) return -1;
//         int firstDiff = -1;
//         int idx;
//         int bump = 0;
//         for (idx = 0; idx < orig.length(); idx++) {
//             if (orig.charAt(idx) != modded.charAt(idx+bump)) {
//                 if (firstDiff >= 0) return -1;
//                 bump = 1;
//                 firstDiff = idx;
//             }
//         }
//         if (firstDiff == -1) {
//             firstDiff = orig.length();
//         }
//         return firstDiff;
//     }
    
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
    
    // no echo, backspace give backspace character, no add unless in
    // "add mode"
    class RawDocumentFilter extends DocumentFilter {
    public RawDocumentFilter() {
        super();
    }
    public void remove(DocumentFilter.FilterBypass fb,
                   int offset,
                   int length)
            throws BadLocationException {
//          super.insertString(fb, offset+length, "\b", new SimpleAttributeSet());
         if (offset == document.getLength()-1) {
             currentChar = (byte)127;
         }
         else {
             currentChar = '\b';
         }
    }

    public void insertString(DocumentFilter.FilterBypass fb,
                     int offset,
                     String string,
                     AttributeSet attr)
              throws BadLocationException {
        if (autoWriting) {
            super.insertString(fb, offset-1, string, attr);
        }
        else {
            int len = string.length();
            if (len > 0) {
                currentChar = string.charAt(len-1);
            }
        }
    }
    
    public void replace(DocumentFilter.FilterBypass fb,
                    int offset,
                    int length,
                    String text,
                    AttributeSet attrs)
             throws BadLocationException {
        if (autoWriting) {
            super.replace(fb, offset-1, length, text, attrs);
        }
        else {
            int len = text.length();
            if (len > 0) {
                currentChar = text.charAt(len-1);
            }
        }
            
    }
}

}

class MyKeyListener extends KeyAdapter {
    public void keyTyped(KeyEvent e) {
        System.out.println("key: "+(int)e.getKeyChar()+","+
            e.getKeyCode()+","+e.getKeyLocation());
    }
}
