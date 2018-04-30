package syntaxtree;

import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import visitor.Visitor;

/**
 * declaration of a formal parameter in a method declaration's
 * parameter list
 */
public class FormalDecl extends VarDecl {

	/**
	 * constructor
	 * @param pos file position
	 * @param atype the type of the variable
	 * @param aname the name being declared
	 */
	public FormalDecl(int pos, Type atype, String aname) {
		super(pos, atype, aname);
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitFormalDecl(this);
	}
	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		pp.print(this.type, ps);
		String xtn = pp.printLinks() ? "#"+this.uniqueId : "";
		ps.print(" "+this.name+xtn);
	}
}
