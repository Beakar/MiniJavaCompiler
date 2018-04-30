package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import visitor.Visitor;

/**
 * declaration of an instance variable
 */
public class InstVarDecl extends VarDecl {

	/**
	 * constructor
	 * @param pos file position
	 * @param atype the type of the variable
	 * @param aname the name being declared
	 */
	public InstVarDecl(int pos, Type atype, String aname) {
		super(pos, atype, aname);
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitInstVarDecl(this);
	}
	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		pp.tab(ps);
		pp.print(this.type, ps);
 		String xtn = pp.printLinks() ? "#"+this.uniqueId : "";
 		ps.println(" "+this.name+xtn+";");
	}
}
