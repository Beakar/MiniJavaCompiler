package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import visitor.Visitor;

/**
 * a default-label within a switch statement
 */
public class Default extends Label {

	/**
	 * constructor
	 * @param pos file position
	 */
	public Default(int pos) {
		super(pos);
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitDefault(this);
	}
	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		pp.tab(ps);
		String xtn = this.enclosingSwitch == null ? "" : "@"+enclosingSwitch.uniqueId; 
		ps.println("default"+xtn+":");
	}

}

