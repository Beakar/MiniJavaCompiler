package errorMsg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import terminal.*;

public class ErrorMsg {
	
	private LineList linePos = new LineList(-1,null);
	private int lineNum=1;
	private String filename;
	public boolean anyErrors;
	private boolean doFrame;
	GuiTerminal2 terminal;
	
	
	public ErrorMsg(String f) {
		filename=f;
		doFrame = false;
	}
	
	public String getFileName() {
		return filename;
	}
	
	public void doFrame() {
		doFrame = true;
	}
	
	private void createFrameIfNeeded() {
		if (doFrame && terminal == null) {
			terminal = new GuiTerminal2();
			terminal.setVisible(true);
		}
		
	}
	
	public void newline(int pos) {
		lineNum++;
		linePos = new LineList(pos,linePos);
	}
	
	public void error(int pos, String msg) {
		msgOut(filename + "::" + lineAndChar(pos) + ": " + msg);
		anyErrors = true;
	}
	
	public void warning(int pos, String msg) {
		msgOut(filename + "::" + lineAndChar(pos) + "(warning): "
				+ msg);
	}
	
	public void info(String s) {
		msgOut(s);
	}
	
	public String lineAndChar(int pos) {
		int n = lineNum;
		LineList p = linePos;
		while (p!=null) {
			if (p.head<pos) {
				return String.valueOf(n) + "." + String.valueOf(pos-p.head);
			}
			p=p.tail; n--;
		}
		return "0.0";
	}
	
	private void msgOut(String s) {
		createFrameIfNeeded();
		if (terminal == null) {
			System.err.println(s);
		}
		else {
			terminal.println(s);
		}
	}
	
	public GuiTerminal2 getTerminal() {
		createFrameIfNeeded();
		return terminal;
	}
}  

class LineList {
	int head;
	LineList tail;
	LineList(int h, LineList t) {head=h; tail=t;}
}


