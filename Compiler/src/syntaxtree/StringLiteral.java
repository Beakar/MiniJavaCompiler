package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import visitor.Visitor;

/**
 * a string literal, as in '"Hello World"'
 */
public class StringLiteral extends Exp {
	
	// instance variables filled in by constructor
	public String str; // the string denoted by the string literal

	// instance variables filled in during later phases
	public StringLiteral uniqueCgRep; // the expression representing this one during code generation

	/**
	 * constructor
	 * @param pos file position
	 * @param astr the string that the string literal denotes
	 */
	public StringLiteral(int pos, String astr) {
		super(pos);
		str = astr;
		uniqueCgRep = null;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitStringLiteral(this);
	}

	protected String[]stringsInDescr() {
		return strArrayPlus1(str, super.stringsInDescr());
	}

	public AstNode[] links() {
		return new AstNode[]{uniqueCgRep};
	}
	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		ps.print("\"");
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			String ss = ""+ch;
			switch (ch) {
			case '\\': ss = "\\\\"; break;
			case '\"': ss = "\\\""; break;
			case '\n': ss = "\\n"; break;
			case '\t': ss = "\\t"; break;
			case '\r': ss = "\\r"; break;
			case '\f': ss = "\\f"; break;
			}
			ps.print(ss);
		}
		ps.print("\""+typ(pp));
	}
}
