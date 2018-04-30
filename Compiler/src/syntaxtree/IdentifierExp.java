package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import visitor.Visitor;

/**
 * an expression consisting of a variable name
 */
public class IdentifierExp extends Exp {
	
	// instance variables filled in by constructor
	public String name; // the name of the variable
	
	// instance variables filled in during later phases
	public VarDecl link; // the declaration of the variable

	/**
	 * constructor
	 * @param pos file position
	 * @param aname the name of the variable
	 */
	public IdentifierExp(int pos, String aname) {
		super(pos);
		name=aname;
		link=null;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitIdentifierExp(this);
	}

	protected String[]stringsInDescr() {
		return strArrayPlus1(name, super.stringsInDescr());
	}
	public AstNode[] links() {
		return new AstNode[]{link};
	}
	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		String xtn = this.link != null && pp.printLinks() ? "@"+link.uniqueId : "";
		ps.print(this.name+xtn+typ(pp));
	}
}
