package prettyPrinter;

import syntaxtree.AstNode;
import syntaxtree.AstList;
import syntaxtree.Exp;
import syntaxtree.Type;
import syntaxtree.IntegerType;
import syntaxtree.BooleanType;
import syntaxtree.NullType;
import syntaxtree.VoidType;
import syntaxtree.ArrayType;
import syntaxtree.IdentifierType;
import java.io.PrintStream;

public class PrettyPrinter {
	private boolean printLinks;
	private boolean printTypes;
	private int indent = 0;

	public PrettyPrinter() {
		this(false,false);
	}
	public PrettyPrinter(boolean printLinks) {
		this(printLinks,false);
	}
	public PrettyPrinter(boolean printLinks, boolean printTypes) {
		this.printLinks = printLinks;
		this.printTypes = printTypes;
	}
	
	public boolean printLinks() {
		return printLinks;
	}
	
	public void indent() {
		indent++;
	}
	public void unindent() {
		indent--;
	}
	public String is() {
		String rtnVal = "";
		for (int i = 0; i < indent; i++) {
			rtnVal += "  ";
		}
		return rtnVal;
	}
	public void tab(PrintStream ps) {
		ps.print(is());
	}
	public void print(AstNode node, PrintStream ps) {
		if (node == null) {
			ps.print("??null??");
		}
		else {
			node.prettyPrint(this, ps);
		}
	}

	public void print(AstList list, PrintStream ps) {
		if (list == null) {
			ps.print("??null??");
		}
		else {
			list.prettyPrint(this, ps);
		}
	}
	
	public String typ(Type t) {
		if (!printTypes) {
			return "";
		}
		else {
			return "\\" + typString(t) + "\\";
		}
	}
	public String typString(Type t) {
		if (!printTypes) {
			return "";
		}
		else if (t == null) {
			return "??null??";
		}
		else if (t instanceof NullType) {
			return "n";
		}
		else if (t instanceof IntegerType) {
			return "i";
		}
		else if (t instanceof BooleanType) {
			return "b";
		}
		else if (t instanceof VoidType) {
			return "v";
		}
		else if (t instanceof ArrayType) {
			int count = 1;
			Type baseType = t;
			for (;;) {
				baseType = ((ArrayType)baseType).baseType;
				if (!(baseType instanceof ArrayType)) break;
				count++;
			}
			return count+typString(baseType);
		}
		else if (t instanceof IdentifierType) {
			return t.toString2();
		}
		else {
			return "??unknown??";
		}
	}

}
