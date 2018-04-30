package visitor;

import java.io.PrintStream;
import syntaxtree.AstNode;
import errorMsg.ErrorMsg;

public class CodeStream {
	private PrintStream out;
	private ErrorMsg err;
	String indentStr;
	
	private static boolean RANDOMSPACES = true;
	
	public CodeStream(PrintStream ps, ErrorMsg e) {
		out = ps;
		err = e;
		indentStr = "";
	}
	public void flush() {
		out.flush();
	}
	
	public void emit(AstNode node, String str) {
		int pos = -1;
		String className = "";
		if (node != null) {
			pos = node.pos;
			className = ""+node.getClass();
			int lastDotSpot = className.lastIndexOf(".");
			className = className.substring(lastDotSpot+1);
		}
		out.println(indentStr+str+" # "+className+" at "+err.lineAndChar(pos)+
				randomWhitespace());
	}
	
	public void indent(AstNode n) {
		indentStr += "  ";
		emit(n, "# ENTER NODE");
	}
	public void unindent(AstNode n) {
		emit(n, "# EXIT NODE");
		indentStr = indentStr.substring(Math.min(2,indentStr.length()));
	}
	
	private static String[] whiteSpace = {
		" ",
		"\t",
		"  ",
		" \t",
		"\t ",
		"\t\t",
		"   ",
		"  \t",
		" \t ",
		" \t\t",
		"\t  ",
		"\t \t",
		"\t\t ",
		"\t\t\t",	
	};
	
	private String randomWhitespace() {
		return whiteSpace[(int)(whiteSpace.length*Math.random())];
	}
}